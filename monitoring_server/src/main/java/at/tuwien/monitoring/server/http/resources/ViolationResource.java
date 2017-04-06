package at.tuwien.monitoring.server.http.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.sql2o.Connection;

import at.tuwien.monitoring.server.db.MysqlDao;
import at.tuwien.monitoring.server.db.dto.ViolationDTO;

@Path("violations")
public class ViolationResource {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getViolations(@QueryParam(value = "ipAddress") String ipAddress) {
		boolean queryIP = false;
		if (ipAddress != null && !ipAddress.isEmpty()) {
			queryIP = true;
		}

		String sql = "SELECT id id, service_ip serviceip, service_name servicename, "
				+ "violation_type violationType, violation_time violationTime FROM violation"
				+ (queryIP ? " WHERE service_ip='" + ipAddress + "'" : "");

		try (Connection con = MysqlDao.INSTANCE.getConn().open()) {
			List<ViolationDTO> violationDTOs = con.createQuery(sql).executeAndFetch(ViolationDTO.class);
			return Response.ok(new GenericEntity<List<ViolationDTO>>(violationDTOs) {
			}).build();
		}
	}
}