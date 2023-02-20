package com.whradam.sigmamall.controller;

import com.whradam.sigmamall.common.exception.CustomException;
import com.whradam.sigmamall.common.utils.UtilsParamsValidator;
import com.whradam.sigmamall.entity.dto.request.PaginationRequest;
import com.whradam.sigmamall.entity.dto.response.AdminResponse;
import com.whradam.sigmamall.service.IAdminService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.whradam.sigmamall.common.response.ResponseCode.ERROR_RUD_FAIL_ID_NULL;

/**
 * Admin只能从数据库中创建
 * 所有管理员对自己与其他管理员的账号只有读取权
 */

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {
    @Autowired
    IAdminService adminService;

    /**
     * 根据id查询管理员信息--admin
     * @return 管理员Response DTO
     */
    @GetMapping("/getAdminById")
    @PreAuthorize("hasRole('ADMIN')")
    public AdminResponse getAdminById(@NonNull @RequestBody String id){
        //参数检验与控制
        if (UtilsParamsValidator.isEmptyString(id)) {
            throw new CustomException(ERROR_RUD_FAIL_ID_NULL);
        }
        return adminService.getAdminById(id);
    }
    /**
     *查询全部管理员账号资料--admin
     */
    @GetMapping("/getAllAdmins")
    @PreAuthorize("hasRole('ADMIN')")
    public List<AdminResponse> getAllAdmins(){
        return adminService.getAllAdmins();
    }
    /**
     *查询管理员列表(分页排序)--admin
     */
    @GetMapping("/getAdminList")
    @PreAuthorize("hasRole('ADMIN')")
    public Map<String, Object> getAdminList(@NotNull @RequestBody PaginationRequest paginationRequest){
        return adminService.getAdminList(paginationRequest);
    }

    /**
     *查询当前管理员账号资料--admin
     */
    @GetMapping(value="/getMyAccount")
    @PreAuthorize("hasRole('ADMIN')")
    public AdminResponse getMyAccount(){
        return adminService.getMyAccount();
    }

}
