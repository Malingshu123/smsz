package com.ruoyi.system.controller;

import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.Resource;
import com.ruoyi.system.service.IResourceService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 应用Controller
 * 
 * @author ruoyi
 * @date 2025-02-19
 */
@RestController
@RequestMapping("/system/resource")
public class ResourceController extends BaseController
{
    @Autowired
    private IResourceService resourceService;

    /**
     * 查询应用列表
     */
    @PreAuthorize("@ss.hasPermi('system:resource:list')")
    @GetMapping("/list")
    public TableDataInfo list(Resource resource)
    {
        startPage();
        List<Resource> list = resourceService.selectResourceList(resource);
        return getDataTable(list);
    }

    /**
     * 查询应用列表By类型
     */
    @PreAuthorize("@ss.hasPermi('system:resource:list')")
    @GetMapping("/listByType/{type}")
    public TableDataInfo listByType(@PathVariable("type") Long type)
    {
        startPage();
        Resource resource = new Resource();
        resource.setType(type);
        List<Resource> list = resourceService.selectResourceList(resource);
        return getDataTable(list);
    }

    /**
     * 导出应用列表
     */
    @PreAuthorize("@ss.hasPermi('system:resource:export')")
    @Log(title = "应用", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Resource resource)
    {
        List<Resource> list = resourceService.selectResourceList(resource);
        ExcelUtil<Resource> util = new ExcelUtil<Resource>(Resource.class);
        util.exportExcel(response, list, "应用数据");
    }

    /**
     * 获取应用详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:resource:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(resourceService.selectResourceById(id));
    }

    /**
     * 新增应用
     */
    @PreAuthorize("@ss.hasPermi('system:resource:add')")
    @Log(title = "应用", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Resource resource)
    {
        resource.setCreatedAt(new Date());
        return toAjax(resourceService.insertResource(resource));
    }

    /**
     * 修改应用
     */
    @PreAuthorize("@ss.hasPermi('system:resource:edit')")
    @Log(title = "应用", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Resource resource)
    {
        resource.setUpdatedAt(new Date());
        return toAjax(resourceService.updateResource(resource));
    }

    /**
     * 删除应用
     */
    @PreAuthorize("@ss.hasPermi('system:resource:remove')")
    @Log(title = "应用", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(resourceService.deleteResourceByIds(ids));
    }
}
