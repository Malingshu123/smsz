package com.ruoyi.system.service;

import java.util.List;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.domain.CardKeys;
import com.ruoyi.system.domain.vo.ValidateCardRequest;

/**
 * 卡密Service接口
 * 
 * @author ruoyi
 * @date 2025-02-19
 */
public interface ICardKeysService 
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
     * 批量删除卡密
     * 
     * @param ids 需要删除的卡密主键集合
     * @return 结果
     */
    public int deleteCardKeysByIds(Long[] ids);

    /**
     * 删除卡密信息
     * 
     * @param id 卡密主键
     * @return 结果
     */
    public int deleteCardKeysById(Long id);

    public AjaxResult validateCardKey(ValidateCardRequest validateCardRequest);
}
