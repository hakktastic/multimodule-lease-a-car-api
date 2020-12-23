package nl.svb.leaseacarapi.leasecalculationservice.bean;

import java.time.LocalDate;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Interest Rate Bean.
 *
 * @author HAKKI CABUK
 *
 */
public class InterestRateBean {

  private int id;
  private double interestRate;
  private LocalDate startDate;

  /**
   * Default constructor.
   */
  public InterestRateBean() {}

  /**
   * Constructor with all fields.
   *
   * @param id ID of interest rate object
   * @param interestRate interest rate
   * @param startDate start date for the interest rate
   */
  public InterestRateBean(int id, double interestRate, LocalDate startDate) {

    this.id = id;
    this.interestRate = interestRate;
    this.startDate = startDate;
  }

  @Override
  public boolean equals(Object obj) {

    boolean equation = false;

    if (obj instanceof InterestRateBean) {

      final InterestRateBean otherEntity = (InterestRateBean) obj;

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
