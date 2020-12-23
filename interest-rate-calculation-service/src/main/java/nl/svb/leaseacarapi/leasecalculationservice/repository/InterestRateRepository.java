package nl.svb.leaseacarapi.leasecalculationservice.repository;

import java.time.LocalDate;
import java.util.Optional;
import nl.svb.leaseacarapi.leasecalculationservice.entity.InterestRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * JPA Repository for Interest Rate Entities.
 *
 * @author HAKKI CABUK
 *
 */
@Repository
public interface InterestRateRepository extends JpaRepository<InterestRate, Integer> {

  /**
   * Custom interface method to find an {@link InterestRate} object by provided start date.
   *
   * @param startDate Start date of interest rate
   * @return Returns the {@link InterestRate}
   */
  Optional<InterestRate> findByStartDate(LocalDate startDate);

}
