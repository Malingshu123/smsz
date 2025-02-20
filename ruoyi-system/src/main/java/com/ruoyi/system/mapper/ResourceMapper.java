package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.Resource;

/**
 * 应用Mapper接口
 * 
 * @author ruoyi
 * @date 2025-02-19
 */
public interface ResourceMapper 
{
    /**
     * 查询应用
     * 
     * @param id 应用主键
     * @return 应用
     */
    public Resource selectResourceById(Long id);

    /**
     * 查询应用列表
     * 
     * @param resource 应用
     * @return 应用集合
     */
    public List<Resource> selectResourceList(Resource resource);

    /**
     * 新增应用
     * 
     * @param resource 应用
     * @return 结果
     */
    public int insertResource(Resource resource);

    /**
     * 修改应用
     * 
     * @param resource 应用
     * @return 结果
     */
    public int updateResource(Resource resource);

    /**
     * 删除应用
     * 
     * @param id 应用主键
     * @return 结果
     */
    public int deleteResourceById(Long id);

    /**
     * 批量删除应用
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteResourceByIds(Long[] ids);
}
