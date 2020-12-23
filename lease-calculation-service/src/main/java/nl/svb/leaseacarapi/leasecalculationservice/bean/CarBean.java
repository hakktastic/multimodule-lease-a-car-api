package nl.svb.leaseacarapi.leasecalculationservice.bean;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Car Bean.
 *
 * @author HAKKI CABUK
 *
 */
public class CarBean {

  private int id;
  private String make;
  private String model;
  private String version;
  private int numberOfDoors;
  private double grossPrice;
  private double nettPrice;
  private int hp;

  /**
   * Default Constructor.
   */
  public CarBean() {}

  /**
   * Constructor with all fields.
   *
   * @param id ID of the car object
   * @param make make of the car object
   * @param model model of the car object
   * @param version version of the car object
   * @param numberOfDoors # doors of the car object
   * @param grossPrice gross price of the car object
   * @param nettPrice nett price of the car object
   * @param hp horse power of the car object
   */
  public CarBean(int id, String make, String model, String version, int numberOfDoors,
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

    if (obj instanceof CarBean) {

      final CarBean otherEntity = (CarBean) obj;

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
