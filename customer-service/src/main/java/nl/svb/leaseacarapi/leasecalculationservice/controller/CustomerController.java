package nl.svb.leaseacarapi.leasecalculationservice.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import java.util.Optional;
import nl.svb.leaseacarapi.leasecalculationservice.entity.Customer;
import nl.svb.leaseacarapi.leasecalculationservice.repository.CustomerRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest controller for Customer Service.
 *
 * @author HAKKI CABUK
 *
 */
@RestController
public class CustomerController {

  @Autowired
  private CustomerRepository repository;

  // This is for debugging purposes of Ribbon.
  // FIXME delete this code after implementation
  @Autowired
  Environment environment;

  private final Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

  /**
   * Create Customer.
   *
   * @param customer {@link Customer} data
   * @return Returns a {@link Customer} Entity
   */
  @ApiOperation(produces = MediaType.APPLICATION_JSON_VALUE,
      notes = "Returns the created Customer object for provided values.", value = "")
  @ApiResponses(
      value = {@ApiResponse(code = 200, message = "Successfully created Customer object")})
  @PostMapping(path = "/customers", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> createCustomer(@RequestBody Customer customer) {

    final Customer customerEntity = repository.save(customer);
    return new ResponseEntity<>(customerEntity, HttpStatus.CREATED);
  }

  /**
   * Delete Customer Entity.
   *
   * @param id ID of Customer Entity
   * @return Returns HTTP Response Code 202 Accepted if Customer is deleted
   */
  @ApiOperation(produces = MediaType.APPLICATION_JSON_VALUE,
      notes = "Delete Customer entity for provided ID.", value = "")
  @ApiResponses(
      value = {@ApiResponse(code = 200, message = "Customer entity successfully deleted")})
  @DeleteMapping(path = "/customers/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> deleteCustomer(@PathVariable int id) {

    final Customer customerEntity = repository.getOne(id);
    repository.delete(customerEntity);
    return new ResponseEntity<>(HttpStatus.ACCEPTED);
  }

  /**
   * Get all Customer Entities.
   *
   * @return Returns a {@link List} with all Customer Entities
   *
   */
  @ApiOperation(produces = MediaType.APPLICATION_JSON_VALUE,
      notes = "Returns all Customer entitites.", value = "")
  @ApiResponses(
      value = {@ApiResponse(code = 200, message = "Successfully returned all Customer entities"),
          @ApiResponse(code = 404, message = "Valid request, but not object(s) found.")})
  @GetMapping(path = "/customers", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> getAllCustomers() {

    final List<Customer> customerEntityList = repository.findAll();

    if (!customerEntityList.isEmpty()) {

      return new ResponseEntity<>(customerEntityList, HttpStatus.OK);

    } else {

      return new ResponseEntity<>("No customer Entities found", HttpStatus.NO_CONTENT);
    }
  }

  /**
   * Get Customer Entity by ID.
   *
   * @param id Object ID of Customer Entity
   *
   * @return Returns a {@link Customer} Entity
   */
  @ApiOperation(produces = MediaType.APPLICATION_JSON_VALUE,
      notes = "Returns Customer entity for provided ID..", value = "")
  @ApiResponses(
      value = {@ApiResponse(code = 200, message = "Successfully returned Customer entity"),
          @ApiResponse(code = 404, message = "Valid request, but not object found.")})
  @GetMapping(path = "/customers/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @HystrixCommand(fallbackMethod = "getCustomerByIdFallback")
  public ResponseEntity<?> getCustomerById(@PathVariable int id) {

    // This is for debugging purposes of Ribbon.
    // FIXME delete this code after implementation
    System.out.println("CUSTOMER SERVICE PORT -->" + environment.getProperty("local.server.port"));

    final ResponseEntity<Customer> responseEntity;
    final Optional<Customer> optionalCustomerEntity = repository.findById(id);

    if (optionalCustomerEntity.isPresent()) {

      responseEntity = new ResponseEntity<>(optionalCustomerEntity.get(), HttpStatus.OK);

      logger.info("Get Customer by ID --> Response Code -> {} - Response -> {} ",
          responseEntity.getStatusCodeValue(), responseEntity.getBody());

    } else {

      responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    return responseEntity;
  }

  /**
   * {@link HystrixCommand} for {@code getCustomerById()} methods.
   *
   * @param id -
   * @return Returns an empty {@link ResponseEntity} with an {@link HttpStatus} code 404
   */
  public ResponseEntity<?> getCustomerByIdFallback(int id) {

    // sample implementation
    return new ResponseEntity<>("Hystrix fallback command", HttpStatus.I_AM_A_TEAPOT);

  }

  /**
   * Get Customer Entity by name.
   *
   * @param name First- and last name of Customer Entity
   * @return Returns a {@link Customer} Entity
   */
  @ApiOperation(produces = MediaType.APPLICATION_JSON_VALUE,
      notes = "Returns Customer entity for provided name..", value = "")
  @ApiResponses(
      value = {@ApiResponse(code = 200, message = "Successfully returned Customer entity"),
          @ApiResponse(code = 404, message = "Valid request, but not object found.")})
  @GetMapping(path = "/customers/name/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> getCustomerByName(@PathVariable String name) {


    final Optional<Customer> optionalCustomerEntity = repository.findByName(name);

    if (optionalCustomerEntity.isPresent()) {

      return new ResponseEntity<>(optionalCustomerEntity.get(), HttpStatus.OK);

    } else {

      return new ResponseEntity<>("Customer Entity not found for name -> " + name,
          HttpStatus.NO_CONTENT);
    }
  }

}
