package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.ResourceMapper;
import com.ruoyi.system.domain.Resource;
import com.ruoyi.system.service.IResourceService;

/**
 * 应用Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-02-19
 */
@Service
public class ResourceServiceImpl implements IResourceService 
{
    @Autowired
    private ResourceMapper resourceMapper;

    /**
     * 查询应用
     * 
     * @param id 应用主键
     * @return 应用
     */
    @Override
    public Resource selectResourceById(Long id)
    {
        return resourceMapper.selectResourceById(id);
    }

    /**
     * 查询应用列表
     * 
     * @param resource 应用
     * @return 应用
     */
    @Override
    public List<Resource> selectResourceList(Resource resource)
    {
        return resourceMapper.selectResourceList(resource);
    }

    /**
     * 新增应用
     * 
     * @param resource 应用
     * @return 结果
     */
    @Override
    public int insertResource(Resource resource)
    {
        return resourceMapper.insertResource(resource);
    }

    /**
     * 修改应用
     * 
     * @param resource 应用
     * @return 结果
     */
    @Override
    public int updateResource(Resource resource)
    {
        return resourceMapper.updateResource(resource);
    }

    /**
     * 批量删除应用
     * 
     * @param ids 需要删除的应用主键
     * @return 结果
     */
    @Override
    public int deleteResourceByIds(Long[] ids)
    {
        return resourceMapper.deleteResourceByIds(ids);
    }

    /**
     * 删除应用信息
     * 
     * @param id 应用主键
     * @return 结果
     */
    @Override
    public int deleteResourceById(Long id)
    {
        return resourceMapper.deleteResourceById(id);
    }
}
