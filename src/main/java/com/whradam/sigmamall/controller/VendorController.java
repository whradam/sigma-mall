package com.whradam.sigmamall.controller;

import com.whradam.sigmamall.common.exception.CustomException;
import com.whradam.sigmamall.common.utils.UtilsParamsValidator;
import com.whradam.sigmamall.controller.validationGroups.Create;
import com.whradam.sigmamall.controller.validationGroups.Update;
import com.whradam.sigmamall.entity.dto.request.VendorRequest;
import com.whradam.sigmamall.entity.dto.request.PaginationRequest;
import com.whradam.sigmamall.entity.dto.response.VendorResponse;
import com.whradam.sigmamall.service.IVendorService;
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

import static com.whradam.sigmamall.common.response.ResponseCode.ERROR_RUD_FAIL_ID_NULL;
import static com.whradam.sigmamall.common.response.ResponseCode.ERROR_UPDATE_FAIL_PASSWORD_NULL;

@RestController
@RequestMapping("/api/v1/vendor")
public class VendorController {
    @Autowired
    IVendorService vendorService;
    @Autowired
    PasswordEncoder passwordEncoder;

    /**
     * 注册创建卖家信息--公开
     * @param vendorRequest 所需添加的卖家Request DTO
     */
    @RequestMapping(value="/addVendor",method= RequestMethod.POST)
    public void addVendor(@Validated(value = {Create.class}) @NonNull @RequestBody VendorRequest vendorRequest){
        //参数检验与控制
        vendorRequest.setId(null);
        vendorRequest.setBalance("0");
        //密码加密操作
        vendorRequest.setPassword(passwordEncoder.encode(vendorRequest.getPassword()));
        //保存卖家信息
        vendorService.addVendor(vendorRequest);
        //返回成功信息，出错则在service层抛出异常
    }

    /**
     * 暂停/注销账号--admin
     * 一条或多条，与前端约定好，“,”分割
     * 实际为更新卖家状态: 0为正常 1表示删除/注销
     * @param id 需要删除的id
     */
    @RequestMapping(value="/deleteVendors", method =RequestMethod.POST)
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteVendors(@NonNull @RequestBody String id) {
        //参数校验
        if (UtilsParamsValidator.isEmptyString(id)) {
            throw new CustomException(ERROR_RUD_FAIL_ID_NULL);
        }
        //调用service
        vendorService.deleteVendors(id);
    }

    /**
     * 重新激活账号--admin
     * 一条或多条，与前端约定好，“,”分割
     * 实际为更新卖家状态: 0为正常 1表示删除/注销
     * @param id 需要重新激活的id
     */
    @RequestMapping(value="/activateVendors", method =RequestMethod.POST)
    @PreAuthorize("hasRole('ADMIN')")
    public void activateVendors(@NonNull @RequestBody String id) {
        //参数校验
        if (UtilsParamsValidator.isEmptyString(id)) {
            throw new CustomException(ERROR_RUD_FAIL_ID_NULL);
        }
        //调用service
        vendorService.deleteVendors(id);
    }

    /**
     * 修改卖家资料--admin
     * @param vendorRequest 需要更新的卖家id与相应参数
     */
    @RequestMapping(value="/updateVendorInfo", method= RequestMethod.POST)
    @PreAuthorize("hasRole('ADMIN')")
    public void updateVendorInfo(@Validated(value = Update.class) @NonNull @RequestBody VendorRequest vendorRequest){
        //参数检验与控制
        if (vendorRequest.getId()==null) {
            throw new CustomException(ERROR_RUD_FAIL_ID_NULL);
        }
        //调用service
        vendorService.updateVendorInfo(vendorRequest);
    }

    /**
     * 修改卖家密码--admin
     * @param vendorRequest 需要更新的卖家id与密码
     */
    @RequestMapping(value="/updateVendorPassword", method= RequestMethod.POST)
    @PreAuthorize("hasRole('ADMIN')")
    public void updateVendorPassword(@Validated(value = Update.class) @NonNull @RequestBody VendorRequest vendorRequest){
        //参数检验与控制
        if (vendorRequest.getId()==null) {
            throw new CustomException(ERROR_RUD_FAIL_ID_NULL);
        }
        if(UtilsParamsValidator.isEmptyString(vendorRequest.getPassword())){
            throw new CustomException(ERROR_UPDATE_FAIL_PASSWORD_NULL);
        }
        //调用service
        vendorService.updateVendorPassword(vendorRequest);
    }

    /**
     * 根据id查询卖家信息--admin
     * @return 卖家Response DTO
     */
    @RequestMapping(value="/getVendorById", method= RequestMethod.GET)
    @PreAuthorize("hasRole('ADMIN')")
    public VendorResponse getVendorById(@NonNull @RequestBody String id){
        //参数检验与控制
        if (UtilsParamsValidator.isEmptyString(id)) {
            throw new CustomException(ERROR_RUD_FAIL_ID_NULL);
        }
        VendorResponse vendorResponse = vendorService.getVendorById(id);
        return vendorResponse;
    }

    /**
     * 查询所有卖家信息--admin
     * @return 所有卖家
     */
    @RequestMapping(value="/getAllVendors", method= RequestMethod.GET)
    @PreAuthorize("hasRole('ADMIN')")
    public List<VendorResponse> getAllVendors(){
        List<VendorResponse> vendorResponseList = vendorService.getAllVendors();
        return vendorResponseList;
    }


    /**
     * 查询卖家列表(分页排序)--admin
     * 分页, 排序，条件查询
     * @return 卖家列表，页数等咨讯
     */
    @RequestMapping(value="/getVendorList", method=RequestMethod.GET)
    @PreAuthorize("hasRole('ADMIN')")
    public Map<String, Object> getVendorList(@NonNull @RequestBody PaginationRequest paginationRequest){
        //分页+条件查询
        Map<String, Object> map = vendorService.getVendorList(paginationRequest);
        return map;
    }


    /**
     * 查询当前卖家信息--vendor
     * @return 当前卖家Response DTO
     */
    @RequestMapping(value="/getMyAccount", method= RequestMethod.GET)
    @PreAuthorize("hasRole('VENDOR')")
    public VendorResponse getMyAccount(){
        VendorResponse vendorResponse = vendorService.getMyAccount();
        return vendorResponse;
    }


    /**
     * 修改当前卖家资料--vendor
     * @param vendorRequest 需要更新的相应参数, id非必须
     */
    @RequestMapping(value="/updateMyInfo", method= RequestMethod.POST)
    @PreAuthorize("hasRole('VENDOR')")
    public void updateMyInfo(@NonNull @RequestBody VendorRequest vendorRequest){
        vendorService.updateMyInfo(vendorRequest);
    }

    /**
     * 修改当前卖家密码--vendor
     * @param password 新密码
     */
    @RequestMapping(value="/updateMyPassword", method= RequestMethod.POST)
    @PreAuthorize("hasRole('VENDOR')")
    public void updateMyPassword(@NonNull @RequestBody String password){
        if(UtilsParamsValidator.isEmptyString(password)){
            throw new CustomException(ERROR_UPDATE_FAIL_PASSWORD_NULL);
        }
        vendorService.updateMyPassword(password);
    }









}
