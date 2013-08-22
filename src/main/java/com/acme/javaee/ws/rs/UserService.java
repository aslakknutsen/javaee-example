package com.acme.javaee.ws.rs;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.acme.javaee.dao.UserDAO;
import com.acme.javaee.domain.User;

@Path("/user")
@Produces({ "text/xml", "application/json" })
public class UserService
{
  @EJB
  private UserDAO dao;

  @Path("/create")
  @PUT
  public User create(@QueryParam("name") final String name,
      @QueryParam("pwd") final String pwd, @QueryParam("mail") final String mail)
  {
    return dao.create(name, pwd, mail);
  }

  @Path("/delete/{id}")
  @DELETE
  public void delete(@PathParam("id") final long id)
  {
    dao.delete(id);
  }

  @Path("/list")
  @GET
  public List<User> list()
  {
    return dao.findAll();
  }

  @Path("/show/{id}")
  @GET
  public User show(@PathParam("id") final long id)
  {
    return dao.findById(id);
  }

  @Path("/update/{id}")
  @POST
  public User update(@PathParam("id") final long id,
      @QueryParam("name") final String name,
      @QueryParam("pwd") final String pwd, @QueryParam("mail") final String mail)
  {
    return dao.update(id, name, pwd, mail);
  }
}
