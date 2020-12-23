package nl.svb.leaseacarapi.leasecalculationservice.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import nl.svb.leaseacarapi.leasecalculationservice.entity.InterestRate;
import nl.svb.leaseacarapi.leasecalculationservice.repository.InterestRateRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


/**
 * Rest Controller for Interest Rate Calculation Service.
 *
 * @author HAKKI CABUK
 *
 */
@RestController
public class InterestRateController {

  @Autowired
  private InterestRateRepository repository;

  private final Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());


  /**
   * Get Interest Rate Entity based on the ID.
   *
   * @param id ID of the Interest Rate Entity
   * @return Returns {@link InterestRate} Entity
   */
  @GetMapping(path = "/interestrates/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(produces = MediaType.APPLICATION_JSON_VALUE,
      notes = "Returns the interest rate object for provided ID.", value = "")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Successfully returned interest rate object"),
      @ApiResponse(code = 404, message = "Valid request, but not object found for provided ID.")})
  @HystrixCommand(fallbackMethod = "getInterestRateByIdFallback")
  public ResponseEntity<?> getInterestById(@PathVariable int id) {

    final ResponseEntity<InterestRate> responseEntity;
    final Optional<InterestRate> optionalInterestRateEntity = repository.findById(id);

    if (optionalInterestRateEntity.isPresent()) {

      responseEntity = new ResponseEntity<>(optionalInterestRateEntity.get(), HttpStatus.OK);

      logger.info("Get Interest Rate by ID --> Response Code -> {} - Response -> {} ",
          responseEntity.getStatusCodeValue(), responseEntity.getBody());

    } else {

      responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    return responseEntity;
  }

  /**
   * {@link HystrixCommand} for {@code getInterestRateById()} methods.
   *
   * @param id -
   * @return Returns an empty {@link ResponseEntity} with an {@link HttpStatus} code 404
   */
  public ResponseEntity<?> getInterestRateByIdFallback(int id) {

    // sample implementation
    return new ResponseEntity<>("Hystrix fallback command", HttpStatus.I_AM_A_TEAPOT);
  }

  /**
   * Get interest rate based on start date.
   *
   * @param startdate start date of interest rate
   * @return Returns a {@link ResponseEntity} containing a {@link InterestRate} container object
   */
  @GetMapping(path = "/interestrates/startdate/{startdate}",
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(produces = MediaType.APPLICATION_JSON_VALUE,
      notes = "Returns the interest rate object for provided start date.", value = "")
  @ApiResponses(
      value = {@ApiResponse(code = 200, message = "Successfully returned interest rate object"),
          @ApiResponse(code = 404,
              message = "Valid request, but not object found for provided start date.")})
  public ResponseEntity<?> getInterestRateByStartDate(@PathVariable String startdate) {

    final LocalDate date = LocalDate.parse(startdate);

    final Optional<InterestRate> optionalInterestRate = repository.findByStartDate(date);

    if (optionalInterestRate.isPresent()) {

      return new ResponseEntity<>(optionalInterestRate.get(), HttpStatus.OK);

    } else {

      return new ResponseEntity<>("Interest Rate Entity not found for start date -> " + startdate,
          HttpStatus.NO_CONTENT);
    }
  }

  /**
   * Get all interest rates.
   *
   * @return Returns a {@link List} with all the interest rate objects.
   */
  @ApiOperation(produces = MediaType.APPLICATION_JSON_VALUE,
      notes = "Returns the interest rate object for provided ID.", value = "")
  @ApiResponses(
      value = {@ApiResponse(code = 200, message = "Successfully returned interest rate object(s)"),
          @ApiResponse(code = 404, message = "Valid request, but not object(s) found.")})
  @GetMapping(path = "/interestrates", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> getIterestRates() {

    final List<InterestRate> responseList = repository.findAll();

    if (responseList != null && !responseList.isEmpty()) {

      return new ResponseEntity<>(responseList, HttpStatus.OK);

    } else {

      return new ResponseEntity<>("No Interest Rate Entities found", HttpStatus.NO_CONTENT);
    }

  }

}
