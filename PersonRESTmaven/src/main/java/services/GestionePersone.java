package services;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import beans.Person;
import db.FakeRepository;

@Path("/gestione")
public class GestionePersone {

	@Path("xml")
	@GET
	@Produces("application/xml")
	public List<Person> getListaPersoneXML() {
		return FakeRepository.pers;
	}
	@GET
	@Produces("application/json")
	public List<Person> getListaPersoneJSON() {
		return FakeRepository.pers;
	}

	@POST
	@Consumes("application/json")
	public Response addNew(Person p) {
		FakeRepository.add(p);
		return Response.status(Response.Status.CREATED).build();
	}
	
}
