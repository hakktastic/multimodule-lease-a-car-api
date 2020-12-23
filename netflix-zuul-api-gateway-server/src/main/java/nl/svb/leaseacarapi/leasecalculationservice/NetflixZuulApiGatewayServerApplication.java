package nl.svb.leaseacarapi.leasecalculationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import brave.sampler.Sampler;

@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
public class NetflixZuulApiGatewayServerApplication {

  public static void main(String[] args) {
    SpringApplication.run(NetflixZuulApiGatewayServerApplication.class, args);
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
