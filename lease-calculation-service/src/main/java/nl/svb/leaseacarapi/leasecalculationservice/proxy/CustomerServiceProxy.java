package nl.svb.leaseacarapi.leasecalculationservice.proxy;

import java.util.Optional;
import nl.svb.leaseacarapi.leasecalculationservice.bean.CustomerBean;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Proxy for retrieving Customer Entities from Customer-Service through Feign.
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
@FeignClient(contextId = "customer-service", name = "netflix-zuul-api-gateway-server")
@RibbonClient(name = "customer-service")
public interface CustomerServiceProxy {

  /**
   * Get Customer Entity by ID.
   *
   * @param id ID of Customer Entity
   * @return Returns a {@link CustomerBean} object
   */
  @GetMapping("/customer-service/customers/{id}")
  Optional<CustomerBean> getCustomerById(@PathVariable int id);

}
