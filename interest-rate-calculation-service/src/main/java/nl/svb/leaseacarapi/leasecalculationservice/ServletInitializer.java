package nl.svb.leaseacarapi.leasecalculationservice;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Main initializer.
 * 
 * @author HAKKI
 *
 */
public class ServletInitializer extends SpringBootServletInitializer {

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(InterestRateCalculationServiceApplication.class);
  }

}
