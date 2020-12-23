package nl.svb.leaseacarapi.leasecalculationservice;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import nl.svb.leaseacarapi.leasecalculationservice.controller.CustomerController;
import nl.svb.leaseacarapi.leasecalculationservice.entity.Customer;
import nl.svb.leaseacarapi.leasecalculationservice.repository.CustomerRepository;
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
@WebMvcTest(value = CustomerController.class)
public class CustomerControllerTest {

  @MockBean
  private CustomerRepository customerRepository;

  @Autowired
  private MockMvc mockMvc;

  private Customer mockCustomer = new Customer(1001, "Mock Customer", "Mock Street", 101, "1000OK",
      "Mockingedam", "mock.customer@gmail.com", 0101235476);

  private String expectedResult =
      "{ \"id\": 1001, \"name\": \"Mock Customer\", \"street\": \"Mock Street\", "
          + "\"houseNumber\": 101, \"zipcode\": \"1000OK\", \"place\": \"Mockingedam\", "
          + "\"email\": \"mock.customer@gmail.com\", \"phoneNumber\": 0101235476 }";

  @Test
  public void testCreateCustomer() throws Exception {

    // mock request
    final RequestBuilder requestBuilder =
        MockMvcRequestBuilders.post("/customers").contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(mockCustomer));
    final MvcResult actualResult = mockMvc.perform(requestBuilder).andReturn();

    // check HTTP response
    assertTrue(actualResult.getResponse().getStatus() == 201);
  }

  @Test
  public void testDeleteCustomer() throws Exception {

    // mock request
    final RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/customers/1001")
        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
    final MvcResult actualResult = mockMvc.perform(requestBuilder).andReturn();

    // check HTTP response
    assertTrue(actualResult.getResponse().getStatus() == 202);
  }

  @Test
  public void testGetCustomer() throws Exception {

    // mock data
    when(customerRepository.findById(1001)).thenReturn(Optional.of(mockCustomer));

    // mock request
    final RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/customers/1001");
    final MvcResult actualResult = mockMvc.perform(requestBuilder).andReturn();

    // check response
    JSONAssert.assertEquals(expectedResult, actualResult.getResponse().getContentAsString(), false);

    // check HTTP response
    assertTrue(actualResult.getResponse().getStatus() == 200);

  }
}
