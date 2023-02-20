package com.whradam.sigmamall.service;

import com.whradam.sigmamall.entity.dto.request.CustomerRequest;
import com.whradam.sigmamall.entity.dto.request.PaginationRequest;
import com.whradam.sigmamall.entity.dto.response.CustomerResponse;

import java.util.List;
import java.util.Map;

public interface ICustomerService {
    /**
     * 注册创建客户信息--公开
     * @param customerRequest 所需添加的客户Request DTO
     */
    public void addCustomer(CustomerRequest customerRequest);

    /**
     * 暂停/注销账号--admin
     * 一条或多条，与前端约定好，“,”分割
     * 实际为更新客户状态: 0为正常 1表示删除/注销
     * @param id 需要删除的id
     */
    public void deleteCustomers(String id);

    /**
     * 重新激活账号--admin
     * 一条或多条，与前端约定好，“,”分割
     * 实际为更新客户状态: 0为正常 1表示删除/注销
     * @param id 需要重新激活的id
     */
    public void activateCustomers(String id);

    /**
     * 修改客户资料--admin
     * @param customerRequest 需要更新的客户id与相应参数
     */
    public void updateCustomerInfo(CustomerRequest customerRequest);

    /**
     * 修改客户密码--admin
     * @param customerRequest 需要更新的客户id与密码
     */
    public void updateCustomerPassword(CustomerRequest customerRequest);

    /**
     * 根据id查询客户信息--admin
     * @return 客户Response DTO
     */
    public CustomerResponse getCustomerById(String id);

    /**
     * 查询所有客户信息--admin
     * @return 所有客户
     */
    public List<CustomerResponse> getAllCustomers();

    /**
     * 查询客户列表(分页排序)--admin
     * 分页, 排序，条件查询
     * @return 客户列表，页数等咨讯
     */
    public Map<String, Object> getCustomerList(PaginationRequest paginationRequest);

    /**
     * 查询当前客户信息--customer
     * @return 当前客户Response DTO
     */
    public CustomerResponse getMyAccount();


    /**
     * 修改当前客户资料--customer
     * @param customerRequest 需要更新的相应参数, id非必须
     */
    public void updateMyInfo(CustomerRequest customerRequest);

    /**
     * 修改当前客户密码--customer
     * @param password 新密码
     */
    public void updateMyPassword(String password);





}
