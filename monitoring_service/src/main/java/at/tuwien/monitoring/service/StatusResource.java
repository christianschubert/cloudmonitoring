package at.tuwien.monitoring.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import at.tuwien.monitoring.service.message.Message;

@Path("status")
public class StatusResource {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStatus() {
		return Response.status(Response.Status.OK).entity(new Message("Online")).build();
	}
}