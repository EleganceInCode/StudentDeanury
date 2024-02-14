package org.test;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * StudentDto
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-02-14T17:35:50.357722200+03:00[Europe/Moscow]")
public class StudentDto {

  private Long id;

  private String login;

  private String firstName;

  private String middleName;

  private String lastName;

  private Integer age;

  public StudentDto() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public StudentDto(String login, String firstName, String middleName, Integer age) {
    this.login = login;
    this.firstName = firstName;
    this.middleName = middleName;
    this.age = age;
  }

  public StudentDto id(Long id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
  */
  
  @Schema(name = "id", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("id")
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public StudentDto login(String login) {
    this.login = login;
    return this;
  }

  /**
   * Get login
   * @return login
  */
  @NotNull 
  @Schema(name = "login", example = "Ivan123", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("login")
  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public StudentDto firstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  /**
   * Get firstName
   * @return firstName
  */
  @NotNull 
  @Schema(name = "firstName", example = "Ivan", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("firstName")
  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public StudentDto middleName(String middleName) {
    this.middleName = middleName;
    return this;
  }

  /**
   * Get middleName
   * @return middleName
  */
  @NotNull 
  @Schema(name = "middleName", example = "Ivanovich", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("middleName")
  public String getMiddleName() {
    return middleName;
  }

  public void setMiddleName(String middleName) {
    this.middleName = middleName;
  }

  public StudentDto lastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  /**
   * Get lastName
   * @return lastName
  */
  
  @Schema(name = "lastName", example = "Ivanov", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("lastName")
  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public StudentDto age(Integer age) {
    this.age = age;
    return this;
  }

  /**
   * Get age
   * @return age
  */
  @NotNull 
  @Schema(name = "age", example = "23", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("age")
  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    StudentDto studentDto = (StudentDto) o;
    return Objects.equals(this.id, studentDto.id) &&
        Objects.equals(this.login, studentDto.login) &&
        Objects.equals(this.firstName, studentDto.firstName) &&
        Objects.equals(this.middleName, studentDto.middleName) &&
        Objects.equals(this.lastName, studentDto.lastName) &&
        Objects.equals(this.age, studentDto.age);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, login, firstName, middleName, lastName, age);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class StudentDto {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    login: ").append(toIndentedString(login)).append("\n");
    sb.append("    firstName: ").append(toIndentedString(firstName)).append("\n");
    sb.append("    middleName: ").append(toIndentedString(middleName)).append("\n");
    sb.append("    lastName: ").append(toIndentedString(lastName)).append("\n");
    sb.append("    age: ").append(toIndentedString(age)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

