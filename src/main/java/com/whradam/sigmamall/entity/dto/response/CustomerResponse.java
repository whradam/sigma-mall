package com.whradam.sigmamall.entity.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.whradam.sigmamall.controller.validationGroups.Create;
import com.whradam.sigmamall.controller.validationGroups.Update;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import java.util.Date;

/**
 * 前端返回数据载体
 * 与Entity一对一字段赋值
 */

@Validated
public class CustomerResponse {

  private Integer id;

  private Date timeCreated;

  private Date timeModified;

  private String account;

  @JsonIgnore
  private String password;

  private String name;

  private String phone;

  private Byte type;

  private Byte status;

  private String balance;

  private String image;

  private String description;

  //隐藏敏感词password, 使之不记录于日志
  @Override
  public String toString() {
    return "CustomerResponse{" +
            "id=" + id +
            ", timeCreated=" + timeCreated +
            ", timeModified=" + timeModified +
            ", account='" + account + '\'' +
//            ", password='" + password + '\'' +
            ", name='" + name + '\'' +
            ", phone='" + phone + '\'' +
            ", type=" + type +
            ", status=" + status +
            ", balance='" + balance + '\'' +
            ", image='" + image + '\'' +
            ", description='" + description + '\'' +
            '}';
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Date getTimeCreated() {
    return timeCreated;
  }

  public void setTimeCreated(Date timeCreated) {
    this.timeCreated = timeCreated;
  }

  public Date getTimeModified() {
    return timeModified;
  }

  public void setTimeModified(Date timeModified) {
    this.timeModified = timeModified;
  }

  public String getAccount() {
    return account;
  }

  public void setAccount(String account) {
    this.account = account;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public Byte getType() {
    return type;
  }

  public void setType(Byte type) {
    this.type = type;
  }

  public Byte getStatus() {
    return status;
  }

  public void setStatus(Byte status) {
    this.status = status;
  }

  public String getBalance() {
    return balance;
  }

  public void setBalance(String balance) {
    this.balance = balance;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

}
