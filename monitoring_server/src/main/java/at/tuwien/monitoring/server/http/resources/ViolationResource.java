package at.tuwien.monitoring.server.http.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import at.tuwien.monitoring.server.db.dao.ViolationDAO;
import at.tuwien.monitoring.server.db.dto.ViolationDTO;

@Path("violations")
public class ViolationResource {

	private ViolationDAO violationDAO = new ViolationDAO();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getViolations(@QueryParam(value = "sourceIpAddress") String sourceIpAddress) {
		List<ViolationDTO> violationDTOs;
		if (sourceIpAddress != null && !sourceIpAddress.isEmpty()) {
			violationDTOs = violationDAO.findByIp(sourceIpAddress);
		} else {
			violationDTOs = violationDAO.findAll();
		}

		return Response.ok(new GenericEntity<List<ViolationDTO>>(violationDTOs) {
		}).build();
	}
}