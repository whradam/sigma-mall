package com.whradam.sigmamall.entity.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.whradam.sigmamall.controller.validationGroups.Create;
import com.whradam.sigmamall.controller.validationGroups.Update;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import java.util.Date;

/**
 * 前端数据请求载体
 * 与Entity一对一字段赋值
 */
//todo：参数检验配置优化
@Validated
public class CustomerRequest {
  //控制新建客户信息的字段长度
  final int NAME_LENGTH = 4;
  final int ACCOUNT_LENGTH_MIN = 4;
  final int ACCOUNT_LENGTH_MAX = 20;
  final int PASSWORD_LENGTH_MIN = 8;
  final int PASSWORD_LENGTH_MAX = 50;

  //Integer 不能用NotBlank
  @NotNull(message = "id must not be null",groups = {Update.class})
  private Integer id;

  @JsonIgnore
  private Date timeCreated;

  @JsonIgnore
  private Date timeModified;

  @Length(min=ACCOUNT_LENGTH_MIN,max=ACCOUNT_LENGTH_MAX, groups = {Create.class})
  private String account;

  @Length(min=PASSWORD_LENGTH_MIN,max=PASSWORD_LENGTH_MAX,groups = {Create.class})
  private String password;

  @Length(min=NAME_LENGTH,groups = {Create.class})
  private String name;

  private String phone;

  @JsonIgnore
  private Byte type;

  @JsonIgnore
  private Byte status;

  @JsonIgnore
  private String balance;

  private String image;

  private String description;

  //隐藏敏感词password, 使之不记录于日志
  @Override
  public String toString() {
    return "CustomerRequest{" +
            "NAME_LENGTH=" + NAME_LENGTH +
            ", ACCOUNT_LENGTH_MIN=" + ACCOUNT_LENGTH_MIN +
            ", ACCOUNT_LENGTH_MAX=" + ACCOUNT_LENGTH_MAX +
            ", PASSWORD_LENGTH_MIN=" + PASSWORD_LENGTH_MIN +
            ", PASSWORD_LENGTH_MAX=" + PASSWORD_LENGTH_MAX +
            ", id=" + id +
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
