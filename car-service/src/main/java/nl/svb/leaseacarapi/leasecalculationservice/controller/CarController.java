package nl.svb.leaseacarapi.leasecalculationservice.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import java.util.Optional;
import nl.svb.leaseacarapi.leasecalculationservice.entity.Car;
import nl.svb.leaseacarapi.leasecalculationservice.repository.CarRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
 * Rest Controller for Car Service.
 *
 * @author HAKKI CABUK
 *
 */
@RestController
public class CarController {

  @Autowired
  private CarRepository repository;

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  /**
   * Create Car Entity.
   *
   * @param car {@link Car} data
   * @return Returns a {@link Car} Entity
   */
  @ApiOperation(produces = MediaType.APPLICATION_JSON_VALUE, notes = "Create Car", value = "")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully created Car entity")})
  @PostMapping(path = "/cars", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> createCar(@RequestBody Car car) {

    final Car carEntity = repository.save(car);
    return new ResponseEntity<>(carEntity, HttpStatus.CREATED);
  }

  /**
   * Delete Car Entity.
   *
   * @param id ID of Car Entity
   * @return Returns HTTP Response Code 202 Accepted if Car is deleted
   */
  @ApiOperation(produces = MediaType.APPLICATION_JSON_VALUE,
      notes = "Deletes Car entity for provided ID..", value = "")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully deleted Car entity")})
  @DeleteMapping(path = "/cars/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> deleteCar(@PathVariable int id) {

    final Car carEntity = repository.getOne(id);
    repository.delete(carEntity);
    return new ResponseEntity<>(HttpStatus.ACCEPTED);
  }

  /**
   * Get Car Entity by ID.
   *
   * @param id ID of car
   * @return Returns a {@link Car} entity
   */
  @ApiOperation(produces = MediaType.APPLICATION_JSON_VALUE,
      notes = "Returns Car entity for provided ID..", value = "")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully returned Car entity"),
      @ApiResponse(code = 404, message = "Valid request, but not object found.")})
  @GetMapping(path = "/cars/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @HystrixCommand(fallbackMethod = "getCarFallback")
  public ResponseEntity<?> getCar(@PathVariable int id) {

    final ResponseEntity<Car> responseEntity;
    final Optional<Car> optionalCarEntity = repository.findById(id);

    if (optionalCarEntity.isPresent()) {

      responseEntity = new ResponseEntity<>(optionalCarEntity.get(), HttpStatus.OK);

      logger.info("Get Customer by ID --> Response Code -> {} - Response -> {} ",
          responseEntity.getStatusCodeValue(), responseEntity.getBody());

    } else {

      responseEntity = new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    return responseEntity;
  }

  /**
   * {@link HystrixCommand} for {@code getCar*()} methods.
   *
   * @return Returns an empty {@link ResponseEntity} with an {@link HttpStatus} code 404
   */
  public ResponseEntity<?> getCarFallback(int id) {

    // sample implementation
    return new ResponseEntity<>("Hystrix fallback command", HttpStatus.I_AM_A_TEAPOT);
  }

  /**
   * Get all Car Entities.
   *
   * @return Returns a {@link List} with Car Entities.
   */
  @ApiOperation(produces = MediaType.APPLICATION_JSON_VALUE, notes = "Returns all Car entities.",
      value = "")
  @ApiResponses(
      value = {@ApiResponse(code = 200, message = "Successfully returned all Car entities"),
          @ApiResponse(code = 404, message = "Valid request, but not objects found.")})
  @GetMapping(path = "/cars", produces = MediaType.APPLICATION_JSON_VALUE)
  @HystrixCommand(fallbackMethod = "getCarFallback")
  public ResponseEntity<?> getCars() {

    final List<Car> careEntityList = repository.findAll();

    if (!careEntityList.isEmpty()) {

      return new ResponseEntity<>(careEntityList, HttpStatus.OK);

    } else {

      return new ResponseEntity<>("No Car Entities found", HttpStatus.NO_CONTENT);
    }
  }

}
