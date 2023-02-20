package com.whradam.sigmamall.dao.mapperGenerated;

import com.whradam.sigmamall.entity.entityGenerated.Vendor;
import com.whradam.sigmamall.entity.entityGenerated.VendorExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface VendorMapper {
    long countByExample(VendorExample example);

    int deleteByExample(VendorExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Vendor row);

    int insertSelective(Vendor row);

    List<Vendor> selectByExample(VendorExample example);

    Vendor selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("row") Vendor row, @Param("example") VendorExample example);

    int updateByExample(@Param("row") Vendor row, @Param("example") VendorExample example);

    int updateByPrimaryKeySelective(Vendor row);

    int updateByPrimaryKey(Vendor row);
}