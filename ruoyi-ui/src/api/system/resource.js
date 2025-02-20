import request from '@/utils/request'

// 查询应用列表
export function listResource(query) {
  return request({
    url: '/system/resource/list',
    method: 'get',
    params: query
  })
}

// 查询应用列表
export function listResourceByType(type) {
  return request({
    url: '/system/resource/listByType/' + type,
    method: 'get'
  })
}

// 查询应用详细
export function getResource(id) {
  return request({
    url: '/system/resource/' + id,
    method: 'get'
  })
}

// 新增应用
export function addResource(data) {
  return request({
    url: '/system/resource',
    method: 'post',
    data: data
  })
}

// 修改应用
export function updateResource(data) {
  return request({
    url: '/system/resource',
    method: 'put',
    data: data
  })
}

// 删除应用
export function delResource(id) {
  return request({
    url: '/system/resource/' + id,
    method: 'delete'
  })
}
