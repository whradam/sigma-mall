package com.whradam.sigmamall.service;

import com.whradam.sigmamall.entity.dto.request.PaginationRequest;
import com.whradam.sigmamall.entity.dto.response.AdminResponse;

import java.util.List;
import java.util.Map;

public interface IAdminService {
    /**
     * 根据id查询管理员信息--admin
     * @return 管理员Response DTO
     */
    public AdminResponse getAdminById(String id);

    /**
     *查询全部管理员账号资料--admin
     * @return 所有管理员
     */
    public List<AdminResponse> getAllAdmins();

    /**
     *查询管理员列表(分页排序)--admin
     * @return 管理员列表，页数等咨讯
     */
    public Map<String, Object> getAdminList(PaginationRequest paginationRequest);

    /**
     *查询当前管理员账号资料--admin
     * @return 当前管理员Response DTO
     */
    public AdminResponse getMyAccount();

}
