package at.tuwien.monitoring.server.db.dao;

import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.sql2o.Connection;
import org.sql2o.Sql2oException;

import at.tuwien.monitoring.server.db.MysqlDao;
import at.tuwien.monitoring.server.db.dto.ViolationDTO;

public class ViolationDAO {

	private final static Logger logger = Logger.getLogger(ViolationDAO.class);

	private static final String SQL_SELECT_ALL = "SELECT id, source_ip_address sourceIpAddress, service_name servicename, "
			+ "violation_type violationType, monitored_value monitoredValue, "
			+ "required_desc requiredDesc, violation_timestamp violationTimestamp FROM violation";

	private static final String SQL_INSERT = "INSERT INTO violation(source_ip_address, service_name, "
			+ "violation_type, monitored_value, required_desc) "
			+ "values (:sourceIpAddress, :serviceName, :violationType, :monitoredValue, :requiredDesc)";

	private static final String SQL_DELETE = "DELETE FROM violation";
	private static final String SQL_RESET_ID = "ALTER TABLE violation AUTO_INCREMENT=0";

	public List<ViolationDTO> findAll() {
		try (Connection con = MysqlDao.INSTANCE.getConn().open()) {
			return con.createQuery(SQL_SELECT_ALL).executeAndFetch(ViolationDTO.class);
		} catch (Sql2oException e) {
			logger.error("Error querying violations from DB.");
			return Collections.emptyList();
		}
	}

	public List<ViolationDTO> findByIp(String ipAddress) {
		String query = SQL_SELECT_ALL + " WHERE source_ip_address='" + ipAddress + "'";
		try (Connection con = MysqlDao.INSTANCE.getConn().open()) {
			return con.createQuery(query).executeAndFetch(ViolationDTO.class);
		} catch (Sql2oException e) {
			logger.error("Error querying violations from DB.");
			return Collections.emptyList();
		}
	}

	public void insert(ViolationDTO violationDTO) {
		try (Connection con = MysqlDao.INSTANCE.getConn().open()) {
			con.createQuery(SQL_INSERT).bind(violationDTO).executeUpdate();
		} catch (Sql2oException e) {
			logger.error("Error inserting violation into DB.");
		}
	}

	public void deleteAll() {
		try (Connection con = MysqlDao.INSTANCE.getConn().open()) {
			con.createQuery(SQL_DELETE).executeUpdate();
			con.createQuery(SQL_RESET_ID).executeUpdate();
		} catch (Sql2oException e) {
			logger.error("Error deleting violations from DB.");
		}
	}
}