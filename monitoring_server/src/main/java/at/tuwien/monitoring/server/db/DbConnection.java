package at.tuwien.monitoring.server.db;

import org.sql2o.Sql2o;

public enum DbConnection {
	INSTANCE;

	private static Sql2o sql2o;

	private static final String HOST = "jdbc:hsqldb:mem:";
	private static final String DATABASE = "monitoring";
	private static final String USER = "SA";
	private static final String PASSWORD = "";

	static {
		sql2o = new Sql2o(HOST + DATABASE, USER, PASSWORD);
	}

	public Sql2o getConn() {
		return sql2o;
	}
}
