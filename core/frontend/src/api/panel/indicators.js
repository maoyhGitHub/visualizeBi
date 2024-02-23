import request from '@/utils/request'
import { panelInit } from '@/components/canvas/utils/utils'
import { getPanelAllLinkageInfo } from '@/api/panel/linkage'
import { queryPanelJumpInfo } from '@/api/panel/linkJump'
import store from '@/store'
import { $error } from '@/utils/message'
import i18n from '@/lang'


//查询指标树
export function findIndicatorsTree() {
  return request({
    url: 'panel/indicators/tree/',
    method: 'post',
    loading: false
  })
}
//查询指标项List
export function getTableList(data) {
  return request({
    url: 'panel/indicators/getTableList/',
    method: 'post',
    loading: false,
    data:data
  })
}
//保存指标项
export function save(data) {
  return request({
    url: 'panel/indicators/save/' ,
    method: 'post',
    loading: false,
    data
  })
}

//删除指标项
export function deleteByIds(data) {
  return request({
    url: 'panel/indicators/deleteByIds/' ,
    method: 'post',
    loading: false,
    data:{'ids':data}
  })
}

//指标唯一性校验
export function checkName(data) {
  return request({
    url: 'panel/indicators/checkName/' ,
    method: 'post',
    loading: false,
    data:data
  })
}
