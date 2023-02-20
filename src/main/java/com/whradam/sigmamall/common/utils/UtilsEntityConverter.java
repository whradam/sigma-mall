package com.whradam.sigmamall.common.utils;

import com.whradam.sigmamall.common.exception.CustomException;
import org.springframework.beans.BeanUtils;
import java.util.LinkedList;
import java.util.List;
import static com.whradam.sigmamall.common.response.ResponseCode.ERROR_UTILS_ENTITY_CONVERT_FAIL;

/**
 * Service层
 * Dto & Entity 互转
 */
public class UtilsEntityConverter<T,K> {
    /**
     * 转换dto为entity
     */
    public static <T,K> T dtoToEntity(Class<T> entityClass, K dto) {
        T entity;
        try {
            entity = entityClass.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(dto, entity);
        }catch (Exception exception){
            throw new CustomException(ERROR_UTILS_ENTITY_CONVERT_FAIL);
        }
        return entity;
    }

    /**
     * 转换entity为dto
     */
    public static <T,K> T entityToDto(Class <T> dtoClass, K entity){
        T dto;
        try {
            dto = dtoClass.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(entity, dto);
        }catch (Exception exception){
            throw new CustomException(ERROR_UTILS_ENTITY_CONVERT_FAIL);
        }
        return dto;
    }

    /**
     * 批量转换entity为dto
     */
    public static <T,K> List<T> entityToDtoList(Class<T> dtoClass, List<K> entityList){
        List<T> dtoList = new LinkedList<>();
        T dto;
        for (K entity : entityList){
            dto = entityToDto(dtoClass,entity);
            dtoList.add(dto);
        }
        return dtoList;
    }

}
