package nl.svb.leaseacarapi.leasecalculationservice.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Size;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * JPA Car Entity.
 *
 * @author HAKKI CABUK
 *
 */
@Entity
@ApiModel
public class Car {

  @Id
  @GeneratedValue
  @ApiModelProperty(notes = "The ID is auto generated")
  private int id;
  private String make;
  private String model;
  private String version;
  @Size(min = 2, message = "A car should have at least two doors")
  @ApiModelProperty(notes = "A car should have at least two doors")
  private int numberOfDoors;
  private double grossPrice;
  private double nettPrice;
  private int hp;

  /**
   * Default Constructor.
   */
  public Car() {}

  /**
   * Constructor.
   *
   * @param id ID of Car
   * @param make Make of Car
   * @param model Model of Car
   * @param version Version of Car
   * @param numberOfDoors Number of Doors of Car
   * @param grossPrice Gross Price of Car
   * @param nettPrice Nett Price of Car
   * @param hp HP of Car
   */
  public Car(int id, String make, String model, String version, int numberOfDoors,
      double grossPrice, double nettPrice, int hp) {
    super();
    this.id = id;
    this.make = make;
    this.model = model;
    this.version = version;
    this.numberOfDoors = numberOfDoors;
    this.grossPrice = grossPrice;
    this.nettPrice = nettPrice;
    this.hp = hp;
  }

  @Override
  public boolean equals(Object obj) {

    boolean equation = false;

    if (obj instanceof Car) {

      final Car otherEntity = (Car) obj;

      equation = new EqualsBuilder().appendSuper(super.equals(obj))
          .append(this.getId(), otherEntity.getId()).isEquals();
    }

    return equation;
  }

  public double getGrossPrice() {
    return grossPrice;
  }

  public int getHp() {
    return hp;
  }

  public int getId() {
    return id;
  }

  public String getMake() {
    return make;
  }

  public String getModel() {
    return model;
  }

  public double getNettPrice() {
    return nettPrice;
  }

  public int getNumberOfDoors() {
    return numberOfDoors;
  }

  public String getVersion() {
    return version;
  }

  public void setGrossPrice(double grossPrice) {
    this.grossPrice = grossPrice;
  }

  public void setHp(int hp) {
    this.hp = hp;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setMake(String make) {
    this.make = make;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public void setNettPrice(double nettPrice) {
    this.nettPrice = nettPrice;
  }

  public void setNumberOfDoors(int numberOfDoors) {
    this.numberOfDoors = numberOfDoors;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  @Override
  public String toString() {
    return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
  }
}
