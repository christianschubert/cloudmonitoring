package at.tuwien.monitoring.server.db.dao;

import java.util.List;

import org.sql2o.Connection;

import at.tuwien.monitoring.server.db.MysqlDao;
import at.tuwien.monitoring.server.db.dto.ViolationDTO;

public class ViolationDAO {

	private static final String SQL_SELECT_ALL = "SELECT id, source_ip_address sourceIpAddress, service_name servicename, "
			+ "violation_type violationType, monitored_value monitoredValue, "
			+ "required_desc requiredDesc, violation_timestamp violationTimestamp FROM violation";

	private static final String SQL_INSERT = "INSERT INTO violation(source_ip_address, service_name, "
			+ "violation_type, monitored_value, required_desc) "
			+ "values (:sourceIpAddress, :serviceName, :violationType, :monitoredValue, :requiredDesc)";

	public List<ViolationDTO> findAll() {
		try (Connection con = MysqlDao.INSTANCE.getConn().open()) {
			return con.createQuery(SQL_SELECT_ALL).executeAndFetch(ViolationDTO.class);
		}
	}

	public List<ViolationDTO> findByIp(String ipAddress) {
		String query = SQL_SELECT_ALL + " WHERE source_ip_address='" + ipAddress + "'";
		try (Connection con = MysqlDao.INSTANCE.getConn().open()) {
			return con.createQuery(query).executeAndFetch(ViolationDTO.class);
		}
	}

	public void insert(ViolationDTO violationDTO) {
		try (Connection con = MysqlDao.INSTANCE.getConn().open()) {
			con.createQuery(SQL_INSERT).bind(violationDTO).executeUpdate();
		}
	}
}