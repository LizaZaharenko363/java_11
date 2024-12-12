package com.example.resource;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.inject.Inject;
import com.example.entity.MyEntity;
import com.example.dao.EntityDAO;

import java.util.List;

@Path("/myentity")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EntityResource {

    @Inject
    private EntityDAO myEntityDAO;

    @POST
    public Response create(MyEntity entity) {
        if (entity == null || entity.getName() == null || entity.getName().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Entity name is required")
                    .build();
        }
        MyEntity created = myEntityDAO.create(entity);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @GET
    @Path("/{id}")
    public Response get(@PathParam("id") Long id) {
        System.out.println("Attempting to retrieve entity with ID: " + id);
        MyEntity entity = myEntityDAO.read(id);
        if (entity != null) {
            return Response.ok(entity).build();
        }
        System.out.println("No entity found with ID: " + id);
        return Response.status(Response.Status.NOT_FOUND).build();
    }
    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, MyEntity entity) {
        if (entity == null || entity.getName() == null || entity.getName().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Entity name is required")
                    .build();
        }
        entity.setId(id);
        MyEntity updated = myEntityDAO.update(entity);
        return Response.ok(updated).build();
    }
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        myEntityDAO.delete(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @GET
    @Path("/search")
    public Response search(@QueryParam("name") String name) {
        List<MyEntity> entities = myEntityDAO.findByName(name);
        return Response.ok(entities).build();
    }
}
