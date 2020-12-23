package nl.svb.leaseacarapi.leasecalculationservice;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import nl.svb.leaseacarapi.leasecalculationservice.bean.CarBean;
import nl.svb.leaseacarapi.leasecalculationservice.bean.CustomerBean;
import nl.svb.leaseacarapi.leasecalculationservice.bean.InterestRateBean;
import nl.svb.leaseacarapi.leasecalculationservice.bean.LeaseRateCalculation;
import nl.svb.leaseacarapi.leasecalculationservice.controller.LeaseCalculationController;
import nl.svb.leaseacarapi.leasecalculationservice.proxy.CarServiceProxy;
import nl.svb.leaseacarapi.leasecalculationservice.proxy.CustomerServiceProxy;
import nl.svb.leaseacarapi.leasecalculationservice.proxy.InterestRateServiceProxy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@WebMvcTest(value = LeaseCalculationController.class)
public class TestLeaseCalculationController {

  @MockBean
  private CarServiceProxy carServiceProxy;

  @MockBean
  private CustomerServiceProxy customerServiceProxy;

  @MockBean
  private InterestRateServiceProxy interestRateServiceProxy;

  @MockBean
  LeaseRateCalculation leaseRateCalculation;

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void testCalculateLeaseRate() throws Exception {

    final CarBean mockCarBean = new CarBean(1001, "Abarth", "500",
        "1.4 T-Jet 595 Esseesse 70th Ann.", 3, 41555.0, 25131.0, 180);

    final CustomerBean mockCustomerBean = new CustomerBean(1001, "Harry Snel", "Sumatrastraat", 10,
        "1094TV", "Amsterdam", "harry.snel@gmail.com", 104612325);

    final InterestRateBean mockInterestRateBean = new InterestRateBean(1021, 4.35,
        LocalDate.from(DateTimeFormatter.ofPattern("yyyy-MM-dd").parse("2019-08-15")));

    final String expectedResult =
        "{\"leaseRate\":96.67,\"mileage\":35000,\"duration\":48,\"car\":{\"id\":1001,"
            + "\"make\":\"Abarth\",\"model\":\"500\",\"version\":\"1.4 T-Jet 595 Esseesse 70th Ann."
            + "\",\"numberOfDoors\":3,\"grossPrice\":41555.0,\"nettPrice\":25131.0,\"hp\":180},"
            + "\"interestRate\":{\"id\":1021,\"interestRate\":4.35,\"startDate\":\"2019-08-15\"},"
            + "\"customer\":{\"id\":1001,\"name\":\"Harry Snel\",\"street\":\"Sumatrastraat\","
            + "\"houseNumber\":10,\"zipcode\":\"1094TV\",\"place\":\"Amsterdam\","
            + "\"email\":\"harry.snel@gmail.com\",\"phoneNumber\":104612325}}";

    // mock data
    when(leaseRateCalculation.calculate()).thenReturn(96.67);
    when(carServiceProxy.getCarById(1001)).thenReturn(Optional.of(mockCarBean));
    when(customerServiceProxy.getCustomerById(mockCustomerBean.getId()))
        .thenReturn(Optional.of(mockCustomerBean));
    when(interestRateServiceProxy.getInterestById(mockInterestRateBean.getId()))
        .thenReturn(Optional.of(mockInterestRateBean));

    // mock request
    final RequestBuilder requestBuilder = MockMvcRequestBuilders
        .get("/leaserates/car/1001/mileage/35000/duration/48/interestrate/1021/customer/1001");
    final MvcResult actualResult = mockMvc.perform(requestBuilder).andReturn();

    // Check response
    JSONAssert.assertEquals(expectedResult, actualResult.getResponse().getContentAsString(), false);

    // check HTTP response
    assertTrue(actualResult.getResponse().getStatus() == HttpStatus.OK.value());
  }
}
