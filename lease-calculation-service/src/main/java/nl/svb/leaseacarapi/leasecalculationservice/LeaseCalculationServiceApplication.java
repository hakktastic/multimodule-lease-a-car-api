package nl.svb.leaseacarapi.leasecalculationservice;

// import brave.sampler.Sampler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
// import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients("nl.svb.leaseacarapi.leasecalculationservice")
@EnableDiscoveryClient
@EnableHystrix
public class LeaseCalculationServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(LeaseCalculationServiceApplication.class, args);
  }

  /**
   * Spring Sleuth for tracing all requests.
   *
   * @return Returns a Sampler object
   */
  // @Bean
  // public Sampler defaultSampler() {
  //
  // // TODO implement Sleuth for only one request
  // return Sampler.ALWAYS_SAMPLE;
  // }
}
