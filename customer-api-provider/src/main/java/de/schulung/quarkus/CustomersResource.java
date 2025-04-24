package de.schulung.quarkus;

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

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Path("/customers")
public class CustomersResource {

  @Context
  UriInfo uriInfo;

  // TODO replace it
  private final Map<UUID, Customer> customers = new HashMap<>();

  {
    Customer customer = new Customer();
    customer.setUuid(UUID.randomUUID());
    customer.setName("Tom Mayer");
    customer.setBirthdate(LocalDate.now().minusYears(30));
    customer.setState("active");
    customers.put(customer.getUuid(), customer);
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Collection<Customer> getCustomers() {
    return this
      .customers
      .values();
  }

  @GET
  @Path("/{uuid}")
  @Produces(MediaType.APPLICATION_JSON)
  public Customer getCustomerById(
    @PathParam("uuid") UUID uuid
  ) {
    var result = customers.get(uuid);
    if (null == result) {
      throw new NotFoundException();
    }
    return result;
  }

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  // @Consumes(MediaType.APPLICATION_JSON)
  public Response createCustomer(
    @Valid
    Customer customer
  ) {
    customer.setUuid(UUID.randomUUID());
    this.customers.put(customer.getUuid(), customer);
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
