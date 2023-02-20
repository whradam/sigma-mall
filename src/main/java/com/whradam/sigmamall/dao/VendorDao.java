package com.whradam.sigmamall.dao;

import com.whradam.sigmamall.dao.mapperGenerated.VendorMapper;
import com.whradam.sigmamall.entity.entityGenerated.Vendor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface VendorDao extends VendorMapper {
    List<Vendor> selectAll();
    List<Vendor> selectList(String sort, String sortType, int startRow, int endRow);
    Vendor selectByAccount(String account);
}

