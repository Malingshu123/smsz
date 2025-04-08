package com.ruoyi.system.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import com.ruoyi.common.annotation.RateLimiter;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.LimitType;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.ip.IpUtils;
import com.ruoyi.system.domain.vo.ValidateCardRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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

    private static final Logger log = LoggerFactory.getLogger(CardKeysServiceImpl.class);

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

    /**
     * 验证卡密
     * @param validateCardRequest
     * @return
     */
    @RateLimiter(key = "card_key_validate_limit:", limitType = LimitType.IP, time = 60, count = 100)
    @Override
    public AjaxResult validateCardKey(ValidateCardRequest validateCardRequest) {

        try {
            // 输入校验
            String code = validateCardRequest.getCode();
            if (code == null || StringUtils.isEmpty(code.trim())) {
                return AjaxResult.error("卡密不能为空");
            }

            code = code.trim();

            // 格式校验
            String regex = "^[a-z0-9]{16}$";
            if (!code.matches(regex)) {
                return AjaxResult.error("卡密格式不正确");
            }

            // 查询卡密
            CardKeys card = cardKeysMapper.findByCode(code);
            if (card == null) {
                return AjaxResult.error("卡密未找到");
            }

            // 状态校验
            if (card.getStatus() != 0) {
                return AjaxResult.error("卡密未找到");
            }

            // 时间相关变量初始化
            LocalDateTime now = LocalDateTime.now();

            // 首次激活处理
            if (card.getActivationDate() == null) {
                LocalDateTime expirationDate = calculateExpirationDate(card.getCardType(), card.getPoints().intValue(), now);
                card.setActivationDate(Date.from(now.atZone(ZoneId.systemDefault()).toInstant()));
                card.setExpirationDate(Date.from(expirationDate.atZone(ZoneId.systemDefault()).toInstant()));
                card.setUseStatus(1L);
            } else {
                // 有效期校验
                LocalDateTime expirationDate = card.getExpirationDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                if (now.isAfter(expirationDate)) {
                    return AjaxResult.error("卡密已过期");
                }
            }

            if ("card_type_count".equals(card.getCardType()) && card.getLoginCount() >= card.getPoints()) {
                // 使用次数校验
                return AjaxResult.error("卡密次数已用完");
            }

            // 计算登录次数
            Integer count = validateCardRequest.getCount();
            if (!"card_type_count".equals(card.getCardType()) && (count == null || count <= 0)) {
                count = 1;
            }

            // 更新登录信息
            card.setLoginCount(card.getLoginCount() + count);
            card.setLoginIp(IpUtils.getIpAddr());
            card.setLoginDate(Date.from(now.atZone(ZoneId.systemDefault()).toInstant()));

            cardKeysMapper.updateCardKeys(card);

            return AjaxResult.success(card.getExpirationDate());
        } catch (IllegalArgumentException e) {
            log.error("参数校验失败: {}", e.getMessage(), e);
            return AjaxResult.error("参数错误");
        } catch (DataAccessException e) {
            log.error("数据库操作失败: {}", e.getMessage(), e);
            return AjaxResult.error("系统繁忙，请稍后再试");
        } catch (Exception e) {
            log.error("未知错误: {}", e.getMessage(), e);
            return AjaxResult.error("系统异常，请联系管理员");
        }
    }

    /**
     * 计算卡密的有效期
     * @param cardType 卡密类型
     * @param points 卡密点数
     * @param activationDate 激活日期
     * @return 有效期
     */
    private LocalDateTime calculateExpirationDate(String cardType, Integer points, LocalDateTime activationDate) {
        switch (cardType) {
            case "card_type_hour":
                return activationDate.plusHours(points.intValue());
            case "card_type_week":
                return activationDate.plusWeeks(points.intValue());
            case "card_type_day":
                return activationDate.plusDays(points.intValue());
            case "card_type_month":
                return activationDate.plusMonths(points.intValue());
            case "card_type_year":
                return activationDate.plusYears(points.intValue());
            case "card_type_count":
                return activationDate.plusYears(99); // 特殊处理
            default:
                throw new IllegalArgumentException("未知的卡密类型: " + cardType);
        }
    }

    // 生成卡密
    private String generateUniqueCode() {
        String code;
        code = UUID.randomUUID().toString().replace("-", "").substring(0, 16);
        return code;
    }
}
