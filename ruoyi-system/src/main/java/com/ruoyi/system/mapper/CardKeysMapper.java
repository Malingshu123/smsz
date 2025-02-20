package com.ruoyi.system.mapper;

import java.time.LocalDateTime;
import java.util.List;
import com.ruoyi.system.domain.CardKeys;
import org.apache.ibatis.annotations.Param;

/**
 * 卡密Mapper接口
 * 
 * @author ruoyi
 * @date 2025-02-19
 */
public interface CardKeysMapper
{
    /**
     * 查询卡密
     * 
     * @param id 卡密主键
     * @return 卡密
     */
    public CardKeys selectCardKeysById(Long id);

    /**
     * 查询卡密列表
     * 
     * @param cardKeys 卡密
     * @return 卡密集合
     */
    public List<CardKeys> selectCardKeysList(CardKeys cardKeys);

    /**
     * 新增卡密
     * 
     * @param cardKeys 卡密
     * @return 结果
     */
    public int insertCardKeys(CardKeys cardKeys);

    /**
     * 修改卡密
     * 
     * @param cardKeys 卡密
     * @return 结果
     */
    public int updateCardKeys(CardKeys cardKeys);

    /**
     * 删除卡密
     * 
     * @param id 卡密主键
     * @return 结果
     */
    public int deleteCardKeysById(Long id);

    /**
     * 批量删除卡密
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCardKeysByIds(Long[] ids);

    /**
     * 根据卡密查询
     * @param code
     * @return
     */
    public CardKeys findByCode(String code);

}
