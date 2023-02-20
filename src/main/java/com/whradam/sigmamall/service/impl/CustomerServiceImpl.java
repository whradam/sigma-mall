package com.whradam.sigmamall.service.impl;

import com.whradam.sigmamall.common.exception.CustomException;
import com.whradam.sigmamall.common.utils.UtilsEntityConverter;
import com.whradam.sigmamall.common.utils.UtilsIdSplitter;
import com.whradam.sigmamall.dao.CustomerDao;
import com.whradam.sigmamall.entity.dto.request.CustomerRequest;
import com.whradam.sigmamall.entity.dto.request.PaginationRequest;
import com.whradam.sigmamall.entity.dto.response.CustomerResponse;
import com.whradam.sigmamall.entity.entityGenerated.Customer;
import com.whradam.sigmamall.security.user.service.IApplicationUserService;
import com.whradam.sigmamall.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.whradam.sigmamall.common.response.ResponseCode.*;

@Service
public class CustomerServiceImpl implements ICustomerService {
    final int ZERO = 0;

    @Autowired
    CustomerDao customerDao;

    @Autowired
    IApplicationUserService applicationUserService;


    /**
     * 注册创建用户信息--公开
     * @param customerRequest 所需添加的客户Request DTO
     */
    @Override
    public void addCustomer(CustomerRequest customerRequest) {
        //Entity转换
        Customer customer = UtilsEntityConverter.dtoToEntity(Customer.class,customerRequest);
        //判断username是否存在
        Customer existingCustomer = customerDao.selectByAccount(customerRequest.getAccount());
        if (existingCustomer!=null){
            throw new CustomException(ERROR_ADD_FAIL_USERNAME_EXISTS);
        }
        //写入数据库
        customerDao.insertSelective(customer);
    }

    /**
     * 暂停/注销账号--admin
     * 一条或多条，与前端约定好，“,”分割
     * 实际为更新客户状态: 0为正常 1表示删除/注销
     * @param id 需要删除的id
     */
    @Override
    @Transactional
    public void deleteCustomers(String id) {
        int status = 1;
        updateCustomersStatus(id,status);
    }

    /**
     * 重新激活账号--admin
     * 一条或多条，与前端约定好，“,”分割
     * 实际为更新客户状态: 0为正常 1表示删除/注销
     * @param id 需要重新激活的id
     */
    @Override
    @Transactional
    public void activateCustomers(String id) {
        int status = 0;
        updateCustomersStatus(id,status);
    }

    /**
     * 修改客户资料--admin
     * @param customerRequest 需要更新的客户id与相应参数
     */
    @Override
    public void updateCustomerInfo(CustomerRequest customerRequest) {
        //数据库中查询要修改的客户
        Customer customer = findCustomerIdFromDb(customerRequest.getId());
        //如果参数不为空则需要修改
        //仅允许修改姓名，电话，描述
        //todo：参数检验配置优化
        if (customerRequest.getName()!=null){
            customer.setName(customerRequest.getName());
        }
        if (customerRequest.getPhone()!=null){
            customer.setPhone(customerRequest.getPhone());
        }
        if (customerRequest.getDescription()!=null){
            customer.setDescription(customerRequest.getDescription());
        }
        //更新数据库
        customerDao.updateByPrimaryKeySelective(customer);
    }

    /**
     * 修改客户密码--admin
     * @param customerRequest 需要更新的客户id与密码
     */
    @Override
    public void updateCustomerPassword(CustomerRequest customerRequest) {
        //数据库中查询要修改的客户
        Customer customer = findCustomerIdFromDb(customerRequest.getId());
        customer.setPassword(customerRequest.getPassword());
        customerDao.updateByPrimaryKeySelective(customer);
    }

    /**
     * 根据id查询客户信息--admin
     * @return 客户
     */
    @Override
    public CustomerResponse getCustomerById(String id) {
        Customer customer = customerDao.selectByPrimaryKey(Integer.parseInt(id));
        return UtilsEntityConverter.entityToDto(CustomerResponse.class, customer);
    }

    /**
     * 查询所有客户信息--admin
     * @return 所有客户
     */
    @Override
    public List<CustomerResponse> getAllCustomers() {
        List<Customer> customerList = customerDao.selectAll();
        return UtilsEntityConverter.entityToDtoList(CustomerResponse.class, customerList);
    }

    /**
     * 查询客户列表(分页排序)--admin
     * 分页, 排序，条件查询
     * @return 用户列表，页数等咨讯
     */
    @Override
    public Map<String, Object> getCustomerList(PaginationRequest paginationRequest) {
        int startRow = (paginationRequest.getCurrentPage()-1)*paginationRequest.getPageSize();
        int endRow = startRow+paginationRequest.getPageSize();
        List<Customer> customerList= customerDao.selectList(paginationRequest.getSort(),paginationRequest.getSortType(),startRow,endRow);

        Map<String, Object> map = new HashMap<>();
        map.put("customers", UtilsEntityConverter.entityToDtoList(CustomerResponse.class, customerList));//查询到的列表信息
        map.put("totalRecords", customerList.size());
        map.put("currentPage",paginationRequest.getCurrentPage());
        map.put("pageSize",paginationRequest.getPageSize());
        return map;
    }

    /**
     * 查询当前客户信息--customer
     * @return 当前客户Response DTO
     */
    @Override
    public CustomerResponse getMyAccount() {
        String username = applicationUserService.getCurrentUserAccount();
        return UtilsEntityConverter.entityToDto(CustomerResponse.class, customerDao.selectByAccount(username));
    }

    /**
     * 修改当前客户资料--customer
     * @param customerRequest 需要更新的相应参数, id非必须
     */
    @Override
    public void updateMyInfo(CustomerRequest customerRequest) {
        Customer customer = UtilsEntityConverter.dtoToEntity(Customer.class,customerRequest);      //如果参数不为空则需要修改
        //如果参数不为空则需要修改
        //仅允许修改姓名，电话，描述
        if (customerRequest.getName()!=null){
            customer.setName(customerRequest.getName());
        }
        if (customerRequest.getPhone()!=null){
            customer.setPhone(customerRequest.getPhone());
        }
        if (customerRequest.getDescription()!=null){
            customer.setDescription(customerRequest.getDescription());
        }
        //更新数据库
        customerDao.updateByPrimaryKeySelective(customer);
    }

    /**
     * 修改当前客户密码--customer
     * @param password 新密码
     */
    @Override
    public void updateMyPassword(String password) {
        String username = applicationUserService.getCurrentUserAccount();
        Customer customer = customerDao.selectByAccount(username);
        customer.setPassword(password);
    }


    /**
     * 删改工具：删改前先查询，查不到则抛出异常
     * @param id 需要删改的客户id
     */
    public Customer findCustomerIdFromDb(Integer id){
        //查询该客户
        Customer customer = customerDao.selectByPrimaryKey(id);
        //如果查询不到则抛出异常
        if(customer == null){
            throw new CustomException(ERROR_DELETE_UPDATE_FAIL_ID_NOT_FOUND);
        }
        return customer;
    }

    /**
     * 删除/恢复账号工具
     * 一条或多条，与前端约定好，“,”分割
     * 实际为更新用户状态
     * @param id 需要删除/恢复的id
     * @param status 客户状态: 0为正常 1表示删除/注销
     */
    public void updateCustomersStatus(String id, int status){
        Set<Integer> convertedIds = UtilsIdSplitter.splitIds(id);
        //逐条删除/恢复
        for (Integer convertedId : convertedIds){
            //查询该客户
            Customer customer = findCustomerIdFromDb(convertedId);
            //更新客户状态
            customer.setStatus((byte) status);
            customerDao.updateByPrimaryKeySelective(customer);
        }
    }


}
