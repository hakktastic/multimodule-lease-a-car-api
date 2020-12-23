package nl.svb.leaseacarapi.leasecalculationservice.proxy;

import java.util.Optional;
import nl.svb.leaseacarapi.leasecalculationservice.bean.CarBean;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


/**
 * Proxy for retrieving Car Entities from Car-Service through Feign.
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
@FeignClient(contextId = "car-service", name = "netflix-zuul-api-gateway-server")
@RibbonClient(name = "car-service")
public interface CarServiceProxy {

  /**
   * Get Car object.
   *
   * @param id Id of the Car object
   * @return Returns a {@link CarBean} object containing car data
   */
  @GetMapping("/car-service/cars/{id}")
  Optional<CarBean> getCarById(@PathVariable int id);
}
