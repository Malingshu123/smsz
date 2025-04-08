package com.ruoyi.system.service.impl;

import java.time.LocalDateTime;
import java.util.*;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.vo.ValidateCardRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.CardKeysMapper;
import com.ruoyi.system.domain.CardKeys;
import com.ruoyi.system.service.ICardKeysService;

import static com.ruoyi.common.utils.SecurityUtils.getUsername;

/**
 * 卡密Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-02-19
 */
@Service
public class CardKeysServiceImpl implements ICardKeysService 
{
    @Autowired
    private CardKeysMapper cardKeysMapper;

    /**
     * 查询卡密
     * 
     * @param id 卡密主键
     * @return 卡密
     */
    @Override
    public CardKeys selectCardKeysById(Long id)
    {
        return cardKeysMapper.selectCardKeysById(id);
    }

    /**
     * 查询卡密列表
     * 
     * @param cardKeys 卡密
     * @return 卡密
     */
    @Override
    public List<CardKeys> selectCardKeysList(CardKeys cardKeys)
    {
        return cardKeysMapper.selectCardKeysList(cardKeys);
    }

    /**
     * 新增卡密
     * 
     * @param cardKeys 卡密
     * @return 结果
     */
    @Override
    public int insertCardKeys(CardKeys cardKeys)
    {
        int row = 0;
        cardKeys.setCreatBy(getUsername());
        cardKeys.setCreatedDate(new Date());
        for (int i = 0; i < cardKeys.getCount(); i++) {
            String code = generateUniqueCode();
            cardKeys.setCode(code);
            row = cardKeysMapper.insertCardKeys(cardKeys);
        }
        return row;
    }

    /**
     * 修改卡密
     * 
     * @param cardKeys 卡密
     * @return 结果
     */
    @Override
    public int updateCardKeys(CardKeys cardKeys)
    {
        cardKeys.setUpdatedDate(new Date());
        return cardKeysMapper.updateCardKeys(cardKeys);
    }

    /**
     * 批量删除卡密
     * 
     * @param ids 需要删除的卡密主键
     * @return 结果
     */
    @Override
    public int deleteCardKeysByIds(Long[] ids)
    {
        return cardKeysMapper.deleteCardKeysByIds(ids);
    }

    /**
     * 删除卡密信息
     * 
     * @param id 卡密主键
     * @return 结果
     */
    @Override
    public int deleteCardKeysById(Long id)
    {
        return cardKeysMapper.deleteCardKeysById(id);
    }

    // 校验卡密
    @Override
    public AjaxResult validateCardKey(ValidateCardRequest validateCardRequest) {

        try {
            String code = validateCardRequest.getCode().trim();

            if (StringUtils.isEmpty(code)) {
                return AjaxResult.error("卡密不能为空");
            }

            String regex = "^[a-z0-9]{16}$";
            if (!code.matches(regex)) {
                return AjaxResult.error("卡密不存在");
            }

            // 查询卡密
            CardKeys card = cardKeysMapper.findByCode(code);
            if (card == null) {
                return AjaxResult.error("卡密不存在");
            }

            // 基础校验
            if (card.getStatus() != 0) {
                return AjaxResult.error("卡密不存在");
            }

            Date now = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(now);

            // 首次激活处理
            if (card.getActivationDate() == null) {
                if ("card_type_hour".equals(card.getCardType())) {
                    calendar.add(Calendar.HOUR, card.getPoints().intValue());
                } else if ("card_type_week".equals(card.getCardType())) {
                    calendar.add(Calendar.WEEK_OF_MONTH, card.getPoints().intValue());
                } else if ("card_type_day".equals(card.getCardType())) {
                    calendar.add(Calendar.DAY_OF_MONTH, card.getPoints().intValue());
                } else if ("card_type_month".equals(card.getCardType())) {
                    calendar.add(Calendar.MONTH, card.getPoints().intValue());
                } else if ("card_type_year".equals(card.getCardType())) {
                    calendar.add(Calendar.YEAR, card.getPoints().intValue());
                } else if ("card_type_count".equals(card.getCardType())) {
                    calendar.add(Calendar.YEAR, 99);
                }

                card.setActivationDate(now);
                card.setExpirationDate(calendar.getTime());
                card.setUseStatus(1L);
            } else if (now.compareTo(card.getExpirationDate()) > 0) {
                // 有效期校验
                return AjaxResult.error("卡密已过期");
            } else if (card.getLoginCount() > card.getPoints()) {
                // 使用次数校验
                return AjaxResult.error("卡密次数已用完");
            }

            // 更新登录信息
            card.setLoginCount(card.getLoginCount() + 1);
            card.setLoginIp(validateCardRequest.getClientIP());
            card.setLoginDate(now);

            cardKeysMapper.updateCardKeys(card);
            return AjaxResult.success(card.getExpirationDate());
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error();
        }
    }

    // 生成卡密
    private String generateUniqueCode() {
        String code;
        code = UUID.randomUUID().toString().replace("-", "").substring(0, 16);
        return code;
    }
}
