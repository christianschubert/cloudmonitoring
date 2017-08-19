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

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import at.tuwien.common.GlobalConstants;
import at.tuwien.monitoring.jms.messages.MetricMessage;
import at.tuwien.monitoring.jms.messages.ServerExecutionTimeMessage;

public class ExtensionServer {

	private final static Logger logger = Logger.getLogger(ExtensionServer.class);

	// pool for all clients
	private final ExecutorService pool;

	// socket where the server listens on
	private ServerSocket serverSocket;

	// all open client sockets are stored here
	private List<ClientHandler> clients = new ArrayList<>();

	// for json to object mapping
	private ObjectMapper objectMapper = new ObjectMapper();

	// thread that waits for clients to connect
	private ServerListener serverListener;

	// metrics received by the clients are stored in this queue
	private Queue<MetricMessage> collectedMetrics = new ConcurrentLinkedQueue<MetricMessage>();

	private boolean running = false;

	public ExtensionServer() {
		pool = Executors.newCachedThreadPool();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	public boolean start() {
		try {
			serverSocket = new ServerSocket(GlobalConstants.EXTENSION_SERVER_PORT);
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
			address = socket.getRemoteSocketAddress().toString();

			logger.info("New client: " + address);
		}

		@Override
		public void run() {
			try {
				clientReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String line = null;

				while ((line = clientReader.readLine()) != null) {
					try {
						ServerExecutionTimeMessage message = objectMapper.readValue(line,
								ServerExecutionTimeMessage.class);

						logger.info("New message: " + message);

						collectedMetrics.offer(message);

					} catch (JsonParseException | JsonMappingException e) {
						logger.error("Error mapping json to object. " + e.getMessage());
					}
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

			logger.info("Extension server started listening on port " + GlobalConstants.EXTENSION_SERVER_PORT);

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