package de.schulung.quarkus.boundary;


import de.schulung.quarkus.domain.Customer;
import de.schulung.quarkus.domain.CustomerState;
import jakarta.ws.rs.BadRequestException;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface CustomerDtoMapper {

  CustomerDto map(Customer source);

  default String mapState(CustomerState state) {
    return null == state ? null : switch (state) {
      case ACTIVE -> "active";
      case LOCKED -> "locked";
      case DISABLED -> "disabled";
    };
  }

  Customer map(CustomerDto source);

  default CustomerState mapState(String state) {
    return null == state ? null : switch (state) {
      case "active" -> CustomerState.ACTIVE;
      case "locked" -> CustomerState.LOCKED;
      case "disabled" -> CustomerState.DISABLED;
      default -> throw new BadRequestException();
    };
  }

}
