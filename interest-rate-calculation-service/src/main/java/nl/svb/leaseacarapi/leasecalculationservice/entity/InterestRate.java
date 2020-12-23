package nl.svb.leaseacarapi.leasecalculationservice.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * JPA interest Rate Entity.
 *
 * @author HAKKI CABUK
 *
 */
@Entity
@ApiModel(description = "Properties of an interest rate calculation")
public class InterestRate {

  @Id
  @GeneratedValue
  private int id;
  @Min(value = 1L)
  @ApiModelProperty(notes = "The interest rate should be at least 1%")
  private double interestRate;
  @Future
  @ApiModelProperty(notes = "Please enter a date in the future")
  private LocalDate startDate;

  /**
   * Default constructor.
   */
  public InterestRate() {}

  /**
   * Constructor with all fields.
   *
   * @param id ID of interest rate object
   * @param interestRate interest rate
   * @param startDate start date for the interest rate
   */
  public InterestRate(int id, double interestRate, LocalDate startDate) {

    this.id = id;
    this.interestRate = interestRate;
    this.startDate = startDate;
  }

  @Override
  public boolean equals(Object obj) {

    boolean equation = false;

    if (obj instanceof InterestRate) {

      final InterestRate otherEntity = (InterestRate) obj;

      equation = new EqualsBuilder().appendSuper(super.equals(obj))
          .append(this.getId(), otherEntity.getId()).isEquals();
    }

    return equation;
  }

  public int getId() {
    return id;
  }

  public double getInterestRate() {
    return interestRate;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setInterestRate(double interestRate) {
    this.interestRate = interestRate;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  @Override
  public String toString() {
    return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
  }
}
