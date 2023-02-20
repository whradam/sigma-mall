package com.whradam.sigmamall.service.impl;

import com.whradam.sigmamall.common.utils.UtilsEntityConverter;
import com.whradam.sigmamall.dao.AdminDao;
import com.whradam.sigmamall.entity.dto.response.AdminResponse;
import com.whradam.sigmamall.entity.entityGenerated.Admin;
import com.whradam.sigmamall.entity.dto.request.PaginationRequest;
import com.whradam.sigmamall.security.user.service.IApplicationUserService;
import com.whradam.sigmamall.service.IAdminService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminServiceImpl implements IAdminService {
    @Autowired
    AdminDao adminDao;
    @Autowired
    IApplicationUserService applicationUserService;

    @Override
    public AdminResponse getAdminById(String id) {
        Admin admin = adminDao.selectByPrimaryKey(Integer.parseInt(id));
        return UtilsEntityConverter.entityToDto(AdminResponse.class,admin);
    }

    @Override
    public List<AdminResponse> getAllAdmins() {
        List<Admin> adminList = adminDao.selectAll();
        return UtilsEntityConverter.entityToDtoList(AdminResponse.class,adminList);
    }

    @Override
    public Map<String, Object> getAdminList(PaginationRequest paginationRequest) {
        int startRow = (paginationRequest.getCurrentPage()-1)*paginationRequest.getPageSize();
        int endRow = startRow+paginationRequest.getPageSize();
        List<Admin> adminList = adminDao.selectList(paginationRequest.getSort(),paginationRequest.getSortType(),startRow,endRow);
        //通过页内容，构建数据
        Map<String, Object> map = new HashMap<>();
        map.put("admins", UtilsEntityConverter.entityToDtoList(AdminResponse.class,adminList));//查询到的列表信息
        map.put("totalRecords", adminList.size());
        map.put("currentPage",paginationRequest.getCurrentPage());
        map.put("pageSize",paginationRequest.getPageSize());
        return map;
    }

    @Override
    public AdminResponse getMyAccount() {
        String username = applicationUserService.getCurrentUserAccount();
        Admin admin = adminDao.selectByAccount(username);
        return UtilsEntityConverter.entityToDto(AdminResponse.class,admin);
    }
}
