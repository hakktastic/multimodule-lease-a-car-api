package nl.svb.leaseacarapi.leasecalculationservice.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.Optional;
import nl.svb.leaseacarapi.leasecalculationservice.bean.CarBean;
import nl.svb.leaseacarapi.leasecalculationservice.bean.CustomerBean;
import nl.svb.leaseacarapi.leasecalculationservice.bean.InterestRateBean;
import nl.svb.leaseacarapi.leasecalculationservice.bean.LeaseRateCalculation;
import nl.svb.leaseacarapi.leasecalculationservice.proxy.CarServiceProxy;
import nl.svb.leaseacarapi.leasecalculationservice.proxy.CustomerServiceProxy;
import nl.svb.leaseacarapi.leasecalculationservice.proxy.InterestRateServiceProxy;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest controller for Lease Calculation Service.
 *
 * @author HAKKI CABUK
 *
 */
@RestController
@ApiModel
public class LeaseCalculationController {

  @Autowired
  private CarServiceProxy carServiceProxy;

  @Autowired
  private InterestRateServiceProxy interestRateProxy;

  @Autowired
  private CustomerServiceProxy customerServiceProxy;

  private final Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

  /**
   * Calculate lease rate.
   *
   * @param carId ID of car object <em>(retrieved from car-service)</em>
   * @param mileage annual mileage in kilometers
   * @param duration lease contract duration in months
   * @param interestRateId ID of Interest Rate object <em>(retrieved from
   *        interest-calculation-service)</em>
   * @param customerId ID of the customer
   * @return Returns a {@link ResponseEntity} containing a {@link LeaseRateCalculation} object
   */
  @GetMapping(
      path = "/leaserates/car/{carId}/mileage/{mileage}/duration/{duration}/"
          + "interestrate/{interestRateId}/customer/{customerId}",
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(produces = MediaType.APPLICATION_JSON_VALUE,
      notes = "Calculates the lease rate with provided params", value = "")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully calculated lease rate"),
      @ApiResponse(code = 404,
          message = "Valid request, but no customer/car/interestrate objects not found")})
  @HystrixCommand(fallbackMethod = "calculateLeaseRateFallback")
  public ResponseEntity<?> calculateLeaseRate(@PathVariable int carId, @PathVariable int mileage,
      @PathVariable int duration, @PathVariable int interestRateId, @PathVariable int customerId) {

    final ResponseEntity<LeaseRateCalculation> responseEntity;

    // get car object from car-service
    final Optional<CarBean> optionalCarBean = carServiceProxy.getCarById(carId);

    // get interest rate object from interest-rate-calculation-service
    final Optional<InterestRateBean> optionalInterestRateBean =
        interestRateProxy.getInterestById(interestRateId);

    final Optional<CustomerBean> optionalCustomerBean =
        customerServiceProxy.getCustomerById(customerId);

    // check if containers are present
    if (optionalCarBean.isPresent() && optionalInterestRateBean.isPresent()
        && optionalCustomerBean.isPresent()) {

      // create lease calculation object
      LeaseRateCalculation leaseRateCalculation = new LeaseRateCalculation(optionalCarBean.get(),
          optionalInterestRateBean.get(), optionalCustomerBean.get(), mileage, duration);

      // calculate lease rate
      leaseRateCalculation.calculate();

      responseEntity = new ResponseEntity<>(leaseRateCalculation, HttpStatus.OK);

      logger.info("Calculate Lease Rate: \n --> Response Code -> {} \n --> Response -> \n {} ",
          responseEntity.getStatusCodeValue(), responseEntity.getBody());

    } else {

      responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    return responseEntity;
  }

  /**
   * {@link HystrixCommand} for {@code calculateLeaseRate()} method.
   *
   * @param carId -
   * @param mileage -
   * @param duration -
   * @param interestRateId -
   * @param customerId -
   *
   * @return Returns an empty {@link ResponseEntity} with an {@link HttpStatus} code 404
   */
  public ResponseEntity<?> calculateLeaseRateFallback(int carId, int mileage, int duration,
      int interestRateId, int customerId) {

    // sample implementation
    return new ResponseEntity<>("Hystrix fallback command", HttpStatus.I_AM_A_TEAPOT);
  }

}
