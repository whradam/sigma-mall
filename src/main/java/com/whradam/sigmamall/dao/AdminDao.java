package com.whradam.sigmamall.dao;

import com.whradam.sigmamall.dao.mapperGenerated.AdminMapper;
import com.whradam.sigmamall.entity.entityGenerated.Admin;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminDao extends AdminMapper {
    List<Admin> selectAll();
    List<Admin> selectList(String sort, String sortType, int startRow, int endRow);
    Admin selectByAccount(String account);

}
