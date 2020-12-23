package nl.svb.leaseacarapi.leasecalculationservice.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * JPA Customer entity.
 *
 * @author HAKKI
 *
 */
@Entity
@ApiModel(description = "Properties of a customer.")
public class Customer {

  @Id
  @GeneratedValue
  @ApiModelProperty(notes = "The ID is auto generated")
  private int id;
  private String name;
  private String street;
  private int houseNumber;
  private String zipcode;
  private String place;
  @Pattern(regexp = "\"^(.+)@(.+)$\"")
  @NotNull
  @ApiModelProperty(notes = "Please enter a valid email address")
  private String email;
  private int phoneNumber;


  /**
   * Default Constructor.
   */
  public Customer() {}

  /**
   * Constructor.
   *
   * @param id ID of customer
   * @param name first and last name of customer
   * @param street the street of the customer
   * @param houseNumber the house number of the customer
   * @param zipcode the zip code of the customer
   * @param place the place of residence of the ucustomerser
   * @param email the email address of the customer
   * @param phoneNumber the phone number of the customer
   */
  public Customer(int id, String name, String street, int houseNumber, String zipcode, String place,
      String email, int phoneNumber) {

    this.id = id;
    this.name = name;
    this.street = street;
    this.houseNumber = houseNumber;
    this.zipcode = zipcode;
    this.place = place;
    this.email = email;
    this.phoneNumber = phoneNumber;
  }

  @Override
  public boolean equals(Object obj) {

    boolean equation = false;

    if (obj instanceof Customer) {

      final Customer otherEntity = (Customer) obj;

      equation = new EqualsBuilder().appendSuper(super.equals(obj))
          .append(this.getId(), otherEntity.getId()).isEquals();
    }

    return equation;
  }

  public String getEmail() {
    return email;
  }

  public int getHouseNumber() {
    return houseNumber;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public int getPhoneNumber() {
    return phoneNumber;
  }

  public String getPlace() {
    return place;
  }

  public String getStreet() {
    return street;
  }

  public String getZipcode() {
    return zipcode;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setHouseNumber(int houseNumber) {
    this.houseNumber = houseNumber;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setPhoneNumber(int phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public void setPlace(String place) {
    this.place = place;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public void setZipcode(String zipcode) {
    this.zipcode = zipcode;
  }

  @Override
  public String toString() {
    return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
  }
}
