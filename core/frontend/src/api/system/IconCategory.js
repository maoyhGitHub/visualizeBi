import request from '@/utils/request'

export function save(data) {
  return request({
    url: '/iconCategory/save',
    data: {data},
    method: 'post',
    loading: true
  })
}
export function categoryDelete(id) {
  return request({
    url: '/iconCategory/delete/' + id,
    method: 'delete'
  })
}

export function find(data) {
  return request({
    url: '/iconCategory/find',
    data: data,
    loading: true,
    method: 'post'
  })
}

export function findCategory(data) {
  return request({
    url: '/iconCategory/findCategory',
    data: {...data},
    loading: true,
    method: 'post'
  })
}
