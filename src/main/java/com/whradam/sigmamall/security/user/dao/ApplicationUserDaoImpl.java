package com.whradam.sigmamall.security.user.dao;


import com.whradam.sigmamall.dao.VendorDao;
import com.whradam.sigmamall.dao.CustomerDao;
import com.whradam.sigmamall.dao.AdminDao;
import com.whradam.sigmamall.entity.entityGenerated.Admin;
import com.whradam.sigmamall.entity.entityGenerated.Vendor;
import com.whradam.sigmamall.entity.entityGenerated.Customer;
import com.whradam.sigmamall.security.user.entity.ApplicationUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.whradam.sigmamall.security.user.entity.ApplicationUserRole.*;

/**
 * 调用对应的dao，
 * 根据用户名调出相应的用户(customer/vendor/admin)
 * 转为ApplicationUser返回
 */
@Repository("ApplicationUserDaoImpl")
public class ApplicationUserDaoImpl implements ApplicationUserDao {


    @Autowired
    CustomerDao customerDao;
    @Autowired
    VendorDao vendorDao;
    @Autowired
    AdminDao adminDao;

    @Override
    public Optional<ApplicationUser> findCustomerByUsername(String username) {
        return getApplicationCustomers(username)
                .stream()
                .filter(applicationUser -> username.equals(applicationUser.getUsername()))
                .findFirst();
    }

    @Override
    public Optional<ApplicationUser> findVendorByUsername(String username) {
        return getApplicationVendors(username)
                .stream()
                .filter(applicationUser -> username.equals(applicationUser.getUsername()))
                .findFirst();
    }


    @Override
    public Optional<ApplicationUser> findAdminByUsername(String username) {
        return getApplicationAdmins(username)
                .stream()
                .filter(applicationUser -> username.equals(applicationUser.getUsername()))
                .findFirst();
    }


    private List<ApplicationUser> getApplicationCustomers(String username){
        List<ApplicationUser> applicationUsers = new ArrayList<>();
        Customer customer = new Customer();
        customer.setAccount(username);
        customer = customerDao.selectByAccount(username);
        ApplicationUser applicationUser = null;
        if (customer.getStatus()==0){
             applicationUser = new ApplicationUser(username, customer.getPassword(),CUSTOMER.getGrantedAuthorities(),true,true,true,true);
        }
        applicationUsers.add(applicationUser);
        return applicationUsers;
    }

    private List<ApplicationUser> getApplicationVendors(String username) {
        List<ApplicationUser> applicationUsers = new ArrayList<>();
        Vendor vendor = new Vendor();
        vendor.setAccount(username);
        vendor = vendorDao.selectByAccount(username);
        ApplicationUser applicationUser = null;
        if (vendor.getStatus()==0){
            applicationUser = new ApplicationUser(username, vendor.getPassword(),VENDOR.getGrantedAuthorities(),true,true,true,true);
        }
        applicationUsers.add(applicationUser);
        return applicationUsers;
    }

    private List<ApplicationUser> getApplicationAdmins(String username) {
        List<ApplicationUser> applicationUsers = new ArrayList<>();
        Admin admin = new Admin();
        admin.setAccount(username);
        admin = adminDao.selectByAccount(username);
        ApplicationUser applicationUser = null;
        if (admin.getStatus()==0){
            applicationUser = new ApplicationUser(username, admin.getPassword(),ADMIN.getGrantedAuthorities(),true,true,true,true);
        }
        applicationUsers.add(applicationUser);
        return applicationUsers;
    }

}

