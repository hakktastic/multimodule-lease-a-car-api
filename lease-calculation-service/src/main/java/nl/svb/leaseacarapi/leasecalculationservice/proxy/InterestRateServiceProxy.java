package nl.svb.leaseacarapi.leasecalculationservice.proxy;

import java.util.Optional;
import nl.svb.leaseacarapi.leasecalculationservice.bean.InterestRateBean;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Proxy for retrieving Interest Rate Entities from Interest-Rate-Calculation-Service through Feign.
 *
 * <p>
 * The request is executed through the Zuul API Gateway by pointing the Feign client to the gateway
 * <b>{@code netflix-zuul-api-gateway-server}</b> instead of the service itself.
 * </p>
 *
 * @author HAKKI CABUK
 *
 */

// Multiple Feign clients require a {@code contextId} annotation attribute
@FeignClient(contextId = "interest-rate-calculation-service",
    name = "netflix-zuul-api-gateway-server")
@RibbonClient(name = "interest-rate-calculation-service")
public interface InterestRateServiceProxy {

  /**
   * Get Interest object based on the ID.
   *
   * @param id ID of the Interest Object
   * @return Returns a {@link InterestRateBean} containing interest rate data
   */
  @GetMapping("/interest-rate-calculation-service/interestrates/{id}")
  Optional<InterestRateBean> getInterestById(@PathVariable int id);

}
