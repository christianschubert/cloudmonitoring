package at.tuwien.common;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Properties;

import org.apache.log4j.Logger;

public class Utils {

	private final static Logger logger = Logger.getLogger(Utils.class);

	private Utils() {
	}

	public static String lookupPublicIPAddress() {
		BufferedReader reader = null;
		try {
			URL checkIP = new URL("http://checkip.amazonaws.com");
			reader = new BufferedReader(new InputStreamReader(checkIP.openStream()));
			return reader.readLine();
		} catch (IOException e) {
			logger.error("Error looking up public IP address.");
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public static Settings readProperties(String configPath) {
		Settings settings = null;

		Properties prop = new Properties();
		InputStream in = null;
		try {
			in = new FileInputStream(configPath);
			prop.load(in);

			String brokerUrl = prop.getProperty("broker.url");
			String serviceUrl = prop.getProperty("service.url");
			settings = new Settings();
			settings.brokerUrl = brokerUrl;
			settings.serviceUrl = serviceUrl;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return settings;
	}
}