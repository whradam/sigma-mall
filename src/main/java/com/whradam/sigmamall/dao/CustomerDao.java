package com.whradam.sigmamall.dao;

import com.whradam.sigmamall.dao.mapperGenerated.CustomerMapper;
import com.whradam.sigmamall.entity.entityGenerated.Customer;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CustomerDao extends CustomerMapper {

    List<Customer> selectAll();
    List<Customer> selectList(String sort, String sortType, int startRow, int endRow);
    Customer selectByAccount(String account);

}
