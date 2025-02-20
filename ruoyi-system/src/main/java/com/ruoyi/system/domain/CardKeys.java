package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 卡密对象 card_keys
 * 
 * @author ruoyi
 * @date 2025-02-19
 */
public class CardKeys extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 卡密 */
    @Excel(name = "卡密")
    private String code;

    /** 卡密类型 */
    @Excel(name = "卡密类型")
    private String cardType;

    /** 点数 */
    @Excel(name = "点数")
    private Long points;

    /** 创建人 */
    @Excel(name = "创建人")
    private String creatBy;

    /** 激活时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "激活时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date activationDate;

    /** 到期时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "到期时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date expirationDate;

    /** 使用状态 */
    @Excel(name = "使用状态")
    private Long useStatus;

    /** 状态 */
    @Excel(name = "状态")
    private Long status;

    /** 登录次数 */
    @Excel(name = "登录次数")
    private Long loginCount;

    /** 登录IP */
    @Excel(name = "登录IP")
    private String loginIp;

    /** 登录时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "登录时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date loginDate;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date createdDate;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date updatedDate;

    private Long count;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setCode(String code) 
    {
        this.code = code;
    }

    public String getCode() 
    {
        return code;
    }
    public void setCardType(String cardType) 
    {
        this.cardType = cardType;
    }

    public String getCardType() 
    {
        return cardType;
    }
    public void setPoints(Long points) 
    {
        this.points = points;
    }

    public Long getPoints() 
    {
        return points;
    }
    public void setCreatBy(String creatBy) 
    {
        this.creatBy = creatBy;
    }

    public String getCreatBy() 
    {
        return creatBy;
    }
    public void setActivationDate(Date activationDate) 
    {
        this.activationDate = activationDate;
    }

    public Date getActivationDate() 
    {
        return activationDate;
    }
    public void setExpirationDate(Date expirationDate) 
    {
        this.expirationDate = expirationDate;
    }

    public Date getExpirationDate() 
    {
        return expirationDate;
    }
    public void setUseStatus(Long useStatus) 
    {
        this.useStatus = useStatus;
    }

    public Long getUseStatus() 
    {
        return useStatus;
    }
    public void setStatus(Long status) 
    {
        this.status = status;
    }

    public Long getStatus() 
    {
        return status;
    }
    public void setLoginCount(Long loginCount) 
    {
        this.loginCount = loginCount;
    }

    public Long getLoginCount() 
    {
        return loginCount;
    }
    public void setLoginIp(String loginIp) 
    {
        this.loginIp = loginIp;
    }

    public String getLoginIp() 
    {
        return loginIp;
    }
    public void setLoginDate(Date loginDate) 
    {
        this.loginDate = loginDate;
    }

    public Date getLoginDate() 
    {
        return loginDate;
    }
    public void setCreatedDate(Date createdDate) 
    {
        this.createdDate = createdDate;
    }

    public Date getCreatedDate() 
    {
        return createdDate;
    }
    public void setUpdatedDate(Date updatedDate) 
    {
        this.updatedDate = updatedDate;
    }

    public Date getUpdatedDate() 
    {
        return updatedDate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("code", getCode())
            .append("cardType", getCardType())
            .append("points", getPoints())
            .append("creatBy", getCreatBy())
            .append("activationDate", getActivationDate())
            .append("expirationDate", getExpirationDate())
            .append("useStatus", getUseStatus())
            .append("status", getStatus())
            .append("loginCount", getLoginCount())
            .append("loginIp", getLoginIp())
            .append("loginDate", getLoginDate())
            .append("createdDate", getCreatedDate())
            .append("updatedDate", getUpdatedDate())
            .toString();
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
