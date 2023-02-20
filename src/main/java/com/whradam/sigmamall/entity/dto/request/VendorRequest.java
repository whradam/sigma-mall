package com.whradam.sigmamall.entity.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.whradam.sigmamall.controller.validationGroups.Create;
import com.whradam.sigmamall.controller.validationGroups.Update;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

import java.util.Date;
/**
 * 前端数据请求载体
 * 与Entity一对一字段赋值
 */
@Validated
public class VendorRequest {
    //Integer 不能用NotBlank
    @NotNull(message = "id must not be null",groups = {Update.class})
    private Integer id;

    @JsonIgnore
    private Date timeCreated;

    @JsonIgnore
    private Date timeModified;

    @NotNull(message = "username must not be null", groups = {Create.class})
    private String account;

    @NotNull(message = "password must not be null", groups = {Create.class})
    private String password;

    @NotNull(message = "name must not be null", groups = {Create.class})
    private String name;

    private String phone;

    @JsonIgnore
    private Byte type;

    @JsonIgnore
    private Byte status;

    private String balance;

    private String image;

    private String description;

    //隐藏敏感词password, 使之不记录于日志
    @Override
    public String toString() {
        return "Vendor{" +
                "id=" + id +
                ", timeCreated=" + timeCreated +
                ", timeModified=" + timeModified +
                ", account='" + account + '\'' +
//                ", password='" + password + '\'' +
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
        this.account = account == null ? null : account.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
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
        this.balance = balance == null ? null : balance.trim();
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image == null ? null : image.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }
}