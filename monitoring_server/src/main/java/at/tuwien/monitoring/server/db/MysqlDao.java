package at.tuwien.monitoring.server.db;

import org.sql2o.Sql2o;

public enum MysqlDao {
	INSTANCE;

	private static Sql2o sql2o;

	private static final String HOST = "jdbc:mysql://localhost:3306/";
	private static final String DATABASE = "monitoring";
	private static final String USER = "monitoring";
	private static final String PASSWORD = "12345";

	private static final String TIMEZONE_SETTING = "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

	static {
		sql2o = new Sql2o(HOST + DATABASE + TIMEZONE_SETTING, USER, PASSWORD);
	}

	public Sql2o getConn() {
		return sql2o;
	}
}
