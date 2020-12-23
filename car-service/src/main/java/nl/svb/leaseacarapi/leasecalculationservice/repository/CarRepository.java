package nl.svb.leaseacarapi.leasecalculationservice.repository;

import nl.svb.leaseacarapi.leasecalculationservice.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * JPA Repository for Car Entities.
 *
 * @author HAKKI CABUK
 *
 */
@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {

}
