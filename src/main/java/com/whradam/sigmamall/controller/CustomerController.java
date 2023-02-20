package com.whradam.sigmamall.controller;

import com.whradam.sigmamall.common.exception.CustomException;
import com.whradam.sigmamall.common.utils.UtilsParamsValidator;
import com.whradam.sigmamall.controller.validationGroups.Create;
import com.whradam.sigmamall.controller.validationGroups.Update;
import com.whradam.sigmamall.entity.dto.request.CustomerRequest;
import com.whradam.sigmamall.entity.dto.request.PaginationRequest;
import com.whradam.sigmamall.entity.dto.response.CustomerResponse;
import com.whradam.sigmamall.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;

import static com.whradam.sigmamall.common.response.ResponseCode.*;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {
    @Autowired
    ICustomerService customerService;
    @Autowired
    PasswordEncoder passwordEncoder;

    /**
     * 注册创建客户信息--公开
     * @param customerRequest 所需添加的客户Request DTO
     */
    @RequestMapping(value="/addCustomer",method= RequestMethod.POST)
    public void addCustomer(@Validated(value = {Create.class}) @NonNull @RequestBody CustomerRequest customerRequest){
        //参数检验与控制
        customerRequest.setId(null);
        customerRequest.setBalance("0");
        //密码加密操作
        customerRequest.setPassword(passwordEncoder.encode(customerRequest.getPassword()));
        //保存客户信息
        customerService.addCustomer(customerRequest);
    }

    /**
     * 暂停/注销账号--admin
     * 一条或多条，与前端约定好，“,”分割
     * 实际为更新客户状态: 0为正常 1表示删除/注销
     * @param id 需要删除的id
     */
    @RequestMapping(value="/deleteCustomers", method =RequestMethod.POST)
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteCustomers(@NonNull @RequestBody String id) {
        //参数校验
        if (UtilsParamsValidator.isEmptyString(id)) {
            throw new CustomException(ERROR_RUD_FAIL_ID_NULL);
        }
        //调用service
        customerService.deleteCustomers(id);
    }

    /**
     * 重新激活账号--admin
     * 一条或多条，与前端约定好，“,”分割
     * 实际为更新客户状态: 0为正常 1表示删除/注销
     * @param id 需要重新激活的id
     */
    @RequestMapping(value="/activateCustomers", method =RequestMethod.POST)
    @PreAuthorize("hasRole('ADMIN')")
    public void activateCustomers(@NonNull @RequestBody String id) {
        //参数校验
        if (UtilsParamsValidator.isEmptyString(id)) {
            throw new CustomException(ERROR_RUD_FAIL_ID_NULL);
        }
        //调用service
        customerService.deleteCustomers(id);
    }

    /**
     * 修改客户资料--admin
     * @param customerRequest 需要更新的客户id与相应参数
     */
    @RequestMapping(value="/updateCustomerInfo", method= RequestMethod.POST)
    @PreAuthorize("hasRole('ADMIN')")
    public void updateCustomerInfo(@Validated(value = Update.class) @NonNull @RequestBody CustomerRequest customerRequest){
        //参数检验与控制
        if (customerRequest.getId()==null) {
            throw new CustomException(ERROR_RUD_FAIL_ID_NULL);
        }
        //调用service
        customerService.updateCustomerInfo(customerRequest);
    }

    /**
     * 修改客户密码--admin
     * @param customerRequest 需要更新的客户id与密码
     */
    @RequestMapping(value="/updateCustomerPassword", method= RequestMethod.POST)
    @PreAuthorize("hasRole('ADMIN')")
    public void updateCustomerPassword(@Validated(value = Update.class) @NonNull @RequestBody CustomerRequest customerRequest){
        //参数检验与控制
        if (customerRequest.getId()==null) {
            throw new CustomException(ERROR_RUD_FAIL_ID_NULL);
        }
        if(UtilsParamsValidator.isEmptyString(customerRequest.getPassword())){
            throw new CustomException(ERROR_UPDATE_FAIL_PASSWORD_NULL);
        }
        //调用service
        customerService.updateCustomerPassword(customerRequest);
    }

    /**
     * 根据id查询客户信息--admin
     * @return 客户Response DTO
     */
    @RequestMapping(value="/getCustomerById", method= RequestMethod.GET)
    @PreAuthorize("hasRole('ADMIN')")
    public CustomerResponse getCustomerById(@NonNull @RequestBody String id){
        //参数检验与控制
        if (UtilsParamsValidator.isEmptyString(id)) {
            throw new CustomException(ERROR_RUD_FAIL_ID_NULL);
        }
        CustomerResponse customerResponse = customerService.getCustomerById(id);
        return customerResponse;
    }

    /**
     * 查询所有客户信息--admin
     * @return 所有客户
     */
    @RequestMapping(value="/getAllCustomers", method= RequestMethod.GET)
    @PreAuthorize("hasRole('ADMIN')")
    public List<CustomerResponse> getAllCustomers(){
        List<CustomerResponse> customerResponseList = customerService.getAllCustomers();
        return customerResponseList;
    }


    /**
     * 查询客户列表(分页排序)--admin
     * 分页, 排序，条件查询
     * @return 客户列表，页数等咨讯
     */
    @RequestMapping(value="/getCustomerList", method=RequestMethod.GET)
    @PreAuthorize("hasRole('ADMIN')")
    public Map<String, Object> getCustomerList(@NonNull @RequestBody PaginationRequest paginationRequest){
        //分页+条件查询
        Map<String, Object> map = customerService.getCustomerList(paginationRequest);
        return map;
    }


    /**
     * 查询当前客户信息--customer
     * @return 当前客户Response DTO
     */
    @RequestMapping(value="/getMyAccount", method= RequestMethod.GET)
    @PreAuthorize("hasRole('CUSTOMER')")
    public CustomerResponse getMyAccount(){
        CustomerResponse customerResponse = customerService.getMyAccount();
        return customerResponse;
    }


    /**
     * 修改当前客户资料--customer
     * @param customerRequest 需要更新的相应参数, id非必须
     */
    @RequestMapping(value="/updateMyInfo", method= RequestMethod.POST)
    @PreAuthorize("hasRole('CUSTOMER')")
    public void updateMyInfo(@NonNull @RequestBody CustomerRequest customerRequest){
        customerService.updateMyInfo(customerRequest);
    }

    /**
     * 修改当前客户密码--customer
     * @param password 新密码
     */
    @RequestMapping(value="/updateMyPassword", method= RequestMethod.POST)
    @PreAuthorize("hasRole('CUSTOMER')")
    public void updateMyPassword(@NonNull @RequestBody String password){
        if(UtilsParamsValidator.isEmptyString(password)){
            throw new CustomException(ERROR_UPDATE_FAIL_PASSWORD_NULL);
        }
        customerService.updateMyPassword(password);
    }









}
