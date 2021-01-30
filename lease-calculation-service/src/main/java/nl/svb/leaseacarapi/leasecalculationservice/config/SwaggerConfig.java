package nl.svb.leaseacarapi.leasecalculationservice.config;

import java.util.ArrayList;
import nl.svb.leaseacarapi.leasecalculationservice.controller.LeaseCalculationController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Enable Swagger API Documentation for this service.
 *
 * @author HAKKI CABUK
 *
 */
@Configuration
public class SwaggerConfig {

  private static final Contact DEFAULT_CONTACT =
      new Contact("Hakki Cabuk", "-", "hakktastic@gmail.com");

  private static final ApiInfo API_INFO =
      new ApiInfo("Api Documentation Lease Rate Calculation Service",
          "Lease Rate Calculation Api Documentation", "-", "-", DEFAULT_CONTACT, "-", "-",
          new ArrayList<>());

  /**
   * Creates and returns a {@link Docket} containing API documentation for the
   * {@link LeaseCalculationController} implementation.
   *
   * @return {@link Docket}
   */
  @Bean
  public Docket api() {

    return new Docket(DocumentationType.SWAGGER_2).apiInfo(API_INFO).select()
        .paths(PathSelectors.regex("/lease-calculation-service/leaserates.*")).build();

  }
}
