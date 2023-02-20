package com.whradam.sigmamall.service;

import com.whradam.sigmamall.entity.dto.request.VendorRequest;
import com.whradam.sigmamall.entity.dto.request.PaginationRequest;
import com.whradam.sigmamall.entity.dto.response.VendorResponse;

import java.util.List;
import java.util.Map;

public interface IVendorService {
    /**
     * 注册创建卖家信息--公开
     * @param vendorRequest 所需添加的卖家Request DTO
     */
    public void addVendor(VendorRequest vendorRequest);

    /**
     * 暂停/注销账号--admin
     * 一条或多条，与前端约定好，“,”分割
     * 实际为更新卖家状态: 0为正常 1表示删除/注销
     * @param id 需要删除的id
     */
    public void deleteVendors(String id);

    /**
     * 重新激活账号--admin
     * 一条或多条，与前端约定好，“,”分割
     * 实际为更新卖家状态: 0为正常 1表示删除/注销
     * @param id 需要重新激活的id
     */
    public void activateVendors(String id);

    /**
     * 修改卖家资料--admin
     * @param vendorRequest 需要更新的卖家id与相应参数
     */
    public void updateVendorInfo(VendorRequest vendorRequest);

    /**
     * 修改卖家密码--admin
     * @param vendorRequest 需要更新的卖家id与密码
     */
    public void updateVendorPassword(VendorRequest vendorRequest);

    /**
     * 根据id查询卖家信息--admin
     * @return 卖家Response DTO
     */
    public VendorResponse getVendorById(String id);

    /**
     * 查询所有卖家信息--admin
     * @return 所有卖家
     */
    public List<VendorResponse> getAllVendors();

    /**
     * 查询卖家列表(分页排序)--admin
     * 分页, 排序，条件查询
     * @return 卖家列表，页数等咨讯
     */
    public Map<String, Object> getVendorList(PaginationRequest paginationRequest);

    /**
     * 查询当前卖家信息--vendor
     * @return 当前卖家Response DTO
     */
    public VendorResponse getMyAccount();


    /**
     * 修改当前卖家资料--vendor
     * @param vendorRequest 需要更新的相应参数, id非必须
     */
    public void updateMyInfo(VendorRequest vendorRequest);

    /**
     * 修改当前卖家密码--vendor
     * @param password 新密码
     */
    public void updateMyPassword(String password);





}
