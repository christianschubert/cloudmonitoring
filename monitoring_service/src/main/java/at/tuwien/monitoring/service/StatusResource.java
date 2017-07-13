package at.tuwien.monitoring.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("status")
public class StatusResource {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getStatus() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "Online";
	}
}