package nl.svb.leaseacarapi.leasecalculationservice.config;

import java.util.ArrayList;
import nl.svb.leaseacarapi.leasecalculationservice.controller.InterestRateController;
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
      new ApiInfo("Api Documentation Interest Rate Calculation Service",
          "Interest Rate Calculation Api Documentation", "-", "-", DEFAULT_CONTACT, "-", "-",
          new ArrayList<>());

  /**
   * Creates and returns a {@link Docket} containing API documentation for the
   * {@link InterestRateController} implementation.
   *
   * @return {@link Docket}
   */
  @Bean
  public Docket api() {

    return new Docket(DocumentationType.SWAGGER_2).apiInfo(API_INFO).select()
        .paths(PathSelectors.regex("/interest-rate-calculation-service/interestrates.*")).build();


  }
}
