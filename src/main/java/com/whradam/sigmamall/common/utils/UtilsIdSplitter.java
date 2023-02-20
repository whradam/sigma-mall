package com.whradam.sigmamall.common.utils;

import com.whradam.sigmamall.common.exception.CustomException;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashSet;
import java.util.Set;

import static com.whradam.sigmamall.common.response.ResponseCode.ERROR_DELETE_FAIL_ID_FORMAT_INVALID;

/**
 * 与前端约定的分隔符
 * 用于批量修改/删除的方法中
 */
@ConfigurationProperties("mvc.controller.id-splitter")
public class UtilsIdSplitter {
    String splitter;

    public UtilsIdSplitter() {
    }

    public UtilsIdSplitter(String splitter) {
        this.splitter = splitter;
    }

    public String getSplitter() {
        return splitter;
    }

    public void setSplitter(String splitter) {
        this.splitter = splitter;
    }

    public static Set<Integer> splitIds(String id){
        //把id转化为数组
        String[] ids =id.split(",");
        //把String id 转为Integer以便查询
        Set<Integer> convertedIds = new HashSet<>();
        try{
            for (String rawId: ids){
                convertedIds.add(Integer.parseInt(rawId));
            }
        }catch (NumberFormatException exception){ //如果无法转化，则为格式问题，抛出异常
            throw new CustomException(ERROR_DELETE_FAIL_ID_FORMAT_INVALID);
        }
        return convertedIds;
    }

}
