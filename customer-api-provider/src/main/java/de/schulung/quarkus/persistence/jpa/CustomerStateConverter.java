package de.schulung.quarkus.persistence.jpa;

import de.schulung.quarkus.domain.CustomerState;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import jakarta.persistence.PersistenceException;

@Converter(autoApply = true)
public class CustomerStateConverter
  implements AttributeConverter<CustomerState, String> {

  @Override
  public String convertToDatabaseColumn(CustomerState attribute) {
    if (null == attribute) {
      return null;
    } else {
      return switch (attribute) {
        case ACTIVE -> "a";
        case DISABLED -> "d";
        case LOCKED -> "l";
      };
    }
  }

  @Override
  public CustomerState convertToEntityAttribute(String dbData) {
    if (null == dbData) {
      return null;
    } else {
      return switch (dbData) {
        case "a" -> CustomerState.ACTIVE;
        case "d" -> CustomerState.DISABLED;
        case "l" -> CustomerState.LOCKED;
        default -> throw new PersistenceException();
      };
    }
  }
}
