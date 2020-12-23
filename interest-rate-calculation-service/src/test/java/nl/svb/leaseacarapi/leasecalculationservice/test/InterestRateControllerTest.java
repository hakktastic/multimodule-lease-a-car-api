package nl.svb.leaseacarapi.leasecalculationservice.test;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import nl.svb.leaseacarapi.leasecalculationservice.controller.InterestRateController;
import nl.svb.leaseacarapi.leasecalculationservice.entity.InterestRate;
import nl.svb.leaseacarapi.leasecalculationservice.repository.InterestRateRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@WebMvcTest(value = InterestRateController.class)
public class InterestRateControllerTest {

  @MockBean
  private InterestRateRepository interestRateRepository;

  @Autowired
  private MockMvc mockMvc;

  private InterestRate mockInterestRateEntity = new InterestRate(1001, 12.15,
      LocalDate.from(DateTimeFormatter.ofPattern("yyyy-MM-dd").parse("2014-05-01")));

  private String expectedSingleResult =
      "{\"id\": 1001,\"interestRate\": 12.15,\"startDate\": \"2014-05-01\"}";

  private String expectedAllResult =
      "[{\"id\":1001,\"interestRate\":12.15,\"startDate\":\"2014-05-01\"}]";

  @Test
  public void testGetAllInterestRates() throws Exception {

    // mock data
    final List<InterestRate> mockResponseList = new ArrayList<>();
    mockResponseList.add(mockInterestRateEntity);

    when(interestRateRepository.findAll()).thenReturn(mockResponseList);

    // mock request
    final RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/interestrates");
    final MvcResult actualResult = mockMvc.perform(requestBuilder).andReturn();

    // Check response
    JSONAssert.assertEquals(expectedAllResult, actualResult.getResponse().getContentAsString(),
        false);

    // check HTTP response
    assertTrue(actualResult.getResponse().getStatus() == 200);
  }

  @Test
  public void testGetInterestRateById() throws Exception {

    // mock data
    when(interestRateRepository.findById(1001)).thenReturn(Optional.of(mockInterestRateEntity));

    // mock request
    final RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/interestrates/1001");
    final MvcResult actualResult = mockMvc.perform(requestBuilder).andReturn();

    // Check response
    JSONAssert.assertEquals(expectedSingleResult, actualResult.getResponse().getContentAsString(),
        false);

    // check HTTP response
    assertTrue(actualResult.getResponse().getStatus() == 200);

  }

  @Test
  public void testGetInterestRateByStartDate() throws Exception {

    // mock data
    when(interestRateRepository.findByStartDate(mockInterestRateEntity.getStartDate()))
        .thenReturn(Optional.of(mockInterestRateEntity));

    // mock request
    final RequestBuilder requestBuilder = MockMvcRequestBuilders
        .get("/interestrates/startdate/" + mockInterestRateEntity.getStartDate());
    final MvcResult actualResult = mockMvc.perform(requestBuilder).andReturn();

    // Check response
    JSONAssert.assertEquals(expectedSingleResult, actualResult.getResponse().getContentAsString(),
        false);

    // check HTTP response
    assertTrue(actualResult.getResponse().getStatus() == 200);

  }

}
