package nl.svb.leaseacarapi.leasecalculationservice;

import brave.sampler.Sampler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
@EnableHystrix
public class InterestRateCalculationServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(InterestRateCalculationServiceApplication.class, args);
  }

  /**
   * Spring Sleuth for tracing all requests.
   *
   * @return Returns a Sampler object
   */
  @Bean
  public Sampler defaultSampler() {

    // TODO implement Sleuth for only one request
    return Sampler.ALWAYS_SAMPLE;
  }

}
