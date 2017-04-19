package at.tuwien.monitoring.agent.extension;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import at.tuwien.monitoring.agent.constants.Constants;
import at.tuwien.monitoring.jms.messages.MetricMessage;

public class ExtensionServer {

	private final static Logger logger = Logger.getLogger(ExtensionServer.class);

	private final ExecutorService pool;

	private ServerSocket serverSocket;
	private List<ClientHandler> clients = new ArrayList<>();

	private ServerListener serverListener;

	private boolean running = false;

	private Queue<MetricMessage> collectedMetrics = new ConcurrentLinkedQueue<MetricMessage>();

	public ExtensionServer() {
		pool = Executors.newCachedThreadPool();
	}

	public boolean start() {
		try {
			serverSocket = new ServerSocket(Constants.EXTENSION_SERVER_PORT);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		serverListener = new ServerListener();
		pool.submit(serverListener);

		running = true;

		return true;
	}

	public void stop() {
		running = false;

		for (ClientHandler client : clients) {
			client.close();
		}

		if (serverSocket != null) {
			try {
				serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (pool != null) {
			pool.shutdown();
			try {
				pool.awaitTermination(1, TimeUnit.SECONDS);
			} catch (InterruptedException e) {
				pool.shutdownNow();
			}
		}
	}

	public Queue<MetricMessage> getCollectedMetrics() {
		return collectedMetrics;
	}

	public class ClientHandler implements Runnable {

		private Socket socket;
		private BufferedReader clientReader;
		private String address;

		public ClientHandler(Socket socket) {
			this.socket = socket;
			this.address = socket.getRemoteSocketAddress().toString();

			logger.info("New client: " + address);
		}

		@Override
		public void run() {
			try {
				clientReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String line = null;

				while ((line = clientReader.readLine()) != null) {
					System.out.println(line);
				}
			} catch (SocketException e) {
				logger.info("Client " + address + " closed.");
			} catch (IOException e) {
				logger.error("Error while reading from input stream of client.");
			} finally {
				close();
			}
		}

		public void close() {
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (clientReader != null) {
				try {
					clientReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public class ServerListener implements Runnable {
		@Override
		public void run() {

			logger.info("Extension server started listening on port " + Constants.EXTENSION_SERVER_PORT);

			while (running) {
				try {
					Socket client = serverSocket.accept();
					ClientHandler handler = new ClientHandler(client);
					clients.add(handler);
					pool.submit(handler);

				} catch (IOException e) {
					if (running) {
						e.printStackTrace();
						stop();
					}
				}
			}
		}
	}
}