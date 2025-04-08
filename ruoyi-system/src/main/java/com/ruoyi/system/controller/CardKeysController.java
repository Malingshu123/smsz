package com.ruoyi.system.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.system.domain.vo.ValidateCardRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.CardKeys;
import com.ruoyi.system.service.ICardKeysService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 卡密Controller
 * 
 * @author ruoyi
 * @date 2025-02-19
 */
@RestController
@RequestMapping("/system/card")
public class CardKeysController extends BaseController
{
    @Autowired
    private ICardKeysService cardKeysService;

    /**
     * 查询卡密列表
     */
    @PreAuthorize("@ss.hasPermi('system:card:list')")
    @GetMapping("/list")
    public TableDataInfo list(CardKeys cardKeys)
    {
        startPage();
        List<CardKeys> list = cardKeysService.selectCardKeysList(cardKeys);
        return getDataTable(list);
    }

    /**
     * 导出卡密列表
     */
    @PreAuthorize("@ss.hasPermi('system:card:export')")
    @Log(title = "卡密", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CardKeys cardKeys)
    {
        List<CardKeys> list = cardKeysService.selectCardKeysList(cardKeys);
        ExcelUtil<CardKeys> util = new ExcelUtil<CardKeys>(CardKeys.class);
        util.exportExcel(response, list, "卡密数据");
    }

    /**
     * 获取卡密详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:card:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(cardKeysService.selectCardKeysById(id));
    }

    /**
     * 新增卡密
     */
    @PreAuthorize("@ss.hasPermi('system:card:add')")
    @Log(title = "卡密", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CardKeys cardKeys)
    {
        return toAjax(cardKeysService.insertCardKeys(cardKeys));
    }

    /**
     * 修改卡密
     */
    @PreAuthorize("@ss.hasPermi('system:card:edit')")
    @Log(title = "卡密", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CardKeys cardKeys)
    {
        return toAjax(cardKeysService.updateCardKeys(cardKeys));
    }

    /**
     * 删除卡密
     */
    @PreAuthorize("@ss.hasPermi('system:card:remove')")
    @Log(title = "卡密", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(cardKeysService.deleteCardKeysByIds(ids));
    }

    @PostMapping("/validate")
    public AjaxResult validateCard(@RequestBody ValidateCardRequest validateCardRequest, HttpServletRequest request) {
        return cardKeysService.validateCardKey(validateCardRequest);
    }

    @GetMapping("/getCardInfo/{code}")
    public AjaxResult getCardInfo(@PathVariable("code") String code)
    {
        CardKeys cardKeys = new CardKeys();
        cardKeys.setCode(code);
        CardKeys result = cardKeysService.selectCardKeysList(cardKeys).get(0);
        result.setId(null);
        return success(result);
    }
}
