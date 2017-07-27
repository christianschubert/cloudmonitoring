package at.tuwien.monitoring.client.constants;

public class Constants {
	private Constants() {
	}

	private static final String URI_LOCAL = "http://127.0.0.1:8080";
	private static final String URI_AWS = "http://ec2-35-161-114-130.us-west-2.compute.amazonaws.com";
	private static final String APP_PATH = "/imageresizer/shrink";

	public static final String SERVICE_URI_LOCAL = URI_LOCAL + APP_PATH;
	public static final String SERVICE_URI_AWS = URI_AWS + APP_PATH;

	public static final String UPLOAD_PATH = "upload/";
	public static final String DOWNLOAD_PATH = "download/";

	public static final String IMAGE_SMALL = "image_small.jpg";
	public static final String IMAGE_MEDIUM = "image_medium.jpg";
	public static final String IMAGE_BIG = "image_big.jpg";
	public static final String IMAGE_VERY_BIG = "image_very_big.jpg";
}