package com.whradam.sigmamall.service.impl;

import com.whradam.sigmamall.common.exception.CustomException;
import com.whradam.sigmamall.common.utils.UtilsEntityConverter;
import com.whradam.sigmamall.common.utils.UtilsIdSplitter;
import com.whradam.sigmamall.dao.VendorDao;
import com.whradam.sigmamall.entity.dto.request.VendorRequest;
import com.whradam.sigmamall.entity.dto.request.PaginationRequest;
import com.whradam.sigmamall.entity.dto.response.VendorResponse;
import com.whradam.sigmamall.entity.entityGenerated.Vendor;
import com.whradam.sigmamall.security.user.service.IApplicationUserService;
import com.whradam.sigmamall.service.IVendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.whradam.sigmamall.common.response.ResponseCode.ERROR_ADD_FAIL_USERNAME_EXISTS;
import static com.whradam.sigmamall.common.response.ResponseCode.ERROR_DELETE_UPDATE_FAIL_ID_NOT_FOUND;

@Service
public class VendorServiceImpl implements IVendorService {
    final int ZERO = 0;

    @Autowired
    VendorDao vendorDao;

    @Autowired
    IApplicationUserService applicationUserService;


    /**
     * 注册创建用户信息--公开
     * @param vendorRequest 所需添加的卖家Request DTO
     */
    @Override
    public void addVendor(VendorRequest vendorRequest) {
        //Entity转换
        Vendor vendor = UtilsEntityConverter.dtoToEntity(Vendor.class,vendorRequest);
        //判断username是否存在
        Vendor existingVendor = vendorDao.selectByAccount(vendorRequest.getAccount());
        if (existingVendor!=null){
            throw new CustomException(ERROR_ADD_FAIL_USERNAME_EXISTS);
        }
        //写入数据库
        vendorDao.insertSelective(vendor);
    }

    /**
     * 暂停/注销账号--admin
     * 一条或多条，与前端约定好，“,”分割
     * 实际为更新卖家状态: 0为正常 1表示删除/注销
     * @param id 需要删除的id
     */
    @Override
    @Transactional
    public void deleteVendors(String id) {
        int status = 1;
        updateVendorsStatus(id,status);
    }

    /**
     * 重新激活账号--admin
     * 一条或多条，与前端约定好，“,”分割
     * 实际为更新卖家状态: 0为正常 1表示删除/注销
     * @param id 需要重新激活的id
     */
    @Override
    @Transactional
    public void activateVendors(String id) {
        int status = 0;
        updateVendorsStatus(id,status);
    }

    /**
     * 修改卖家资料--admin
     * @param vendorRequest 需要更新的卖家id与相应参数
     */
    @Override
    public void updateVendorInfo(VendorRequest vendorRequest) {
        //数据库中查询要修改的卖家
        Vendor vendor = findVendorIdFromDb(vendorRequest.getId());
        //如果参数不为空则需要修改
        //仅允许修改姓名，电话，描述
        if (vendorRequest.getName()!=null){
            vendor.setName(vendorRequest.getName());
        }
        if (vendorRequest.getPhone()!=null){
            vendor.setPhone(vendorRequest.getPhone());
        }
        if (vendorRequest.getDescription()!=null){
            vendor.setDescription(vendorRequest.getDescription());
        }
        //更新数据库
        vendorDao.updateByPrimaryKeySelective(vendor);
    }

    /**
     * 修改卖家密码--admin
     * @param vendorRequest 需要更新的卖家id与密码
     */
    @Override
    public void updateVendorPassword(VendorRequest vendorRequest) {
        //数据库中查询要修改的卖家
        Vendor vendor = findVendorIdFromDb(vendorRequest.getId());
        vendor.setPassword(vendorRequest.getPassword());
        vendorDao.updateByPrimaryKeySelective(vendor);
    }

    /**
     * 根据id查询卖家信息--admin
     * @return 卖家
     */
    @Override
    public VendorResponse getVendorById(String id) {
        Vendor vendor = vendorDao.selectByPrimaryKey(Integer.parseInt(id));
        return UtilsEntityConverter.entityToDto(VendorResponse.class,vendor);
    }

    /**
     * 查询所有卖家信息--admin
     * @return 所有卖家
     */
    @Override
    public List<VendorResponse> getAllVendors() {
        List<Vendor> vendorList = vendorDao.selectAll();
        return UtilsEntityConverter.entityToDtoList(VendorResponse.class,vendorList);
    }

    /**
     * 查询卖家列表(分页排序)--admin
     * 分页, 排序，条件查询
     * @return 用户列表，页数等咨讯
     */
    @Override
    public Map<String, Object> getVendorList(PaginationRequest paginationRequest) {
        int startRow = (paginationRequest.getCurrentPage()-1)*paginationRequest.getPageSize();
        int endRow = startRow+paginationRequest.getPageSize();
        List<Vendor> vendorList= vendorDao.selectList(paginationRequest.getSort(),paginationRequest.getSortType(),startRow,endRow);

        Map<String, Object> map = new HashMap<>();
        map.put("vendors", UtilsEntityConverter.entityToDtoList(VendorResponse.class,vendorList));//查询到的列表信息
        map.put("totalRecords", vendorList.size());
        map.put("currentPage",paginationRequest.getCurrentPage());
        map.put("pageSize",paginationRequest.getPageSize());
        return map;
    }

    /**
     * 查询当前卖家信息--vendor
     * @return 当前卖家Response DTO
     */
    @Override
    public VendorResponse getMyAccount() {
        String username = applicationUserService.getCurrentUserAccount();
        return UtilsEntityConverter.entityToDto(VendorResponse.class,vendorDao.selectByAccount(username));
    }

    /**
     * 修改当前卖家资料--vendor
     * @param vendorRequest 需要更新的相应参数, id非必须
     */
    @Override
    public void updateMyInfo(VendorRequest vendorRequest) {
        Vendor vendor = UtilsEntityConverter.dtoToEntity(Vendor.class,vendorRequest);      //如果参数不为空则需要修改
        //如果参数不为空则需要修改
        //仅允许修改姓名，电话，描述
        if (vendorRequest.getName()!=null){
            vendor.setName(vendorRequest.getName());
        }
        if (vendorRequest.getPhone()!=null){
            vendor.setPhone(vendorRequest.getPhone());
        }
        if (vendorRequest.getDescription()!=null){
            vendor.setDescription(vendorRequest.getDescription());
        }
        //更新数据库
        vendorDao.updateByPrimaryKeySelective(vendor);
    }

    /**
     * 修改当前卖家密码--vendor
     * @param password 新密码
     */
    @Override
    public void updateMyPassword(String password) {
        String username = applicationUserService.getCurrentUserAccount();
        Vendor vendor = vendorDao.selectByAccount(username);
        vendor.setPassword(password);
    }


    /**
     * 删改工具：删改前先查询，查不到则抛出异常
     * @param id 需要删改的卖家id
     */
    public Vendor findVendorIdFromDb(Integer id){
        //查询该卖家
        Vendor vendor = vendorDao.selectByPrimaryKey(id);
        //如果查询不到则抛出异常
        if(vendor == null){
            throw new CustomException(ERROR_DELETE_UPDATE_FAIL_ID_NOT_FOUND);
        }
        return vendor;
    }

    /**
     * 删除/恢复账号工具
     * 一条或多条，与前端约定好，“,”分割
     * 实际为更新用户状态
     * @param id 需要删除/恢复的id
     * @param status 卖家状态: 0为正常 1表示删除/注销
     */
    public void updateVendorsStatus(String id, int status){
        Set<Integer> convertedIds = UtilsIdSplitter.splitIds(id);
        //逐条删除/恢复
        for (Integer convertedId : convertedIds){
            //查询该卖家
            Vendor vendor = findVendorIdFromDb(convertedId);
            //更新卖家状态
            vendor.setStatus((byte) status);
            vendorDao.updateByPrimaryKeySelective(vendor);
        }
    }


}
