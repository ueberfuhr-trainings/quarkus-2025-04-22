package de.schulung.quarkus;

import io.quarkus.jsonb.JsonbConfigCustomizer;
import jakarta.inject.Singleton;
import jakarta.json.bind.JsonbConfig;
import org.eclipse.yasson.YassonConfig;

@Singleton
public class JsonbConfiguration
  implements JsonbConfigCustomizer {

  @Override
  public void customize(JsonbConfig jsonbConfig) {
    jsonbConfig
      .setProperty(YassonConfig.FAIL_ON_UNKNOWN_PROPERTIES, true);
  }

}
