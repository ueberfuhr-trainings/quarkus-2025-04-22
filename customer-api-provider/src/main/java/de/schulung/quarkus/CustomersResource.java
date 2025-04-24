package de.schulung.quarkus;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.util.Collection;
import java.util.UUID;

@Path("/customers")
public class CustomersResource {

  @Context
  UriInfo uriInfo;

  @Inject
  CustomersService customersService;


  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Collection<Customer> getCustomers() {
    return this
      .customersService
      .findAll()
      .toList();
  }

  @GET
  @Path("/{uuid}")
  @Produces(MediaType.APPLICATION_JSON)
  public Customer getCustomerById(
    @PathParam("uuid") UUID uuid
  ) {
    return this
      .customersService
      .findById(uuid)
      .orElseThrow(NotFoundException::new);
  }

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  // @Consumes(MediaType.APPLICATION_JSON)
  public Response createCustomer(
    @Valid
    Customer customer
  ) {
    this
      .customersService
      .create(customer);
    return Response
      .created(
        uriInfo
          .getAbsolutePathBuilder()
          .path(customer.getUuid().toString())
          .build()
      )
      .entity(customer)
      .build();
  }

}
