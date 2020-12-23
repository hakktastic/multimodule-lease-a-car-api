package nl.svb.leaseacarapi.leasecalculationservice.controller;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import nl.svb.leaseacarapi.leasecalculationservice.entity.Car;
import nl.svb.leaseacarapi.leasecalculationservice.repository.CarRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


@RunWith(SpringRunner.class)
@WebMvcTest(value = CarController.class)
public class CarControllerTest {

  @MockBean
  private CarRepository carRepository;

  @Autowired
  private MockMvc mockMvc;

  Car mockCar =
      new Car(1001, "Audi", "A8", "50 TFSI quattro S tronic Pro Line", 4, 85000, 75000, 325);

  final String expectedResult = "{\"id\":1001,\"make\":\"Audi\",\"model\":\"A8\",\"version\":"
      + "\"50 TFSI quattro S tronic Pro Line\",\"numberOfDoors\":4,"
      + "\"grossPrice\":85000.0,\"nettPrice\":75000.0,\"hp\":325}";

  @Test
  public void testCreateCar() throws Exception {

    // mock request
    final RequestBuilder requestBuilder =
        MockMvcRequestBuilders.post("/cars").contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(mockCar));
    final MvcResult result = mockMvc.perform(requestBuilder).andReturn();

    // check http response
    assertTrue(result.getResponse().getStatus() == 201);
  }

  @Test
  public void testDeleteCar() throws Exception {

    // mock request
    final RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/cars/1001")
        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
    final MvcResult actualResult = mockMvc.perform(requestBuilder).andReturn();

    // check HTTP response
    assertTrue(actualResult.getResponse().getStatus() == 202);
  }

  @Test
  public void testGetCarById() throws Exception {

    // mock data
    when(carRepository.findById(1001)).thenReturn(Optional.of(mockCar));

    // mock request
    final RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/cars/1001");
    final MvcResult actualResult = mockMvc.perform(requestBuilder).andReturn();

    // check response
    JSONAssert.assertEquals(expectedResult, actualResult.getResponse().getContentAsString(), false);

    // check HTTP response
    assertTrue(actualResult.getResponse().getStatus() == 200);

  }

}
