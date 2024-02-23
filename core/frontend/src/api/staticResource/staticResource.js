import request from '@/utils/request'
import { uuid } from 'vue-uuid'
import store from '@/store'

export function uploadFile(fileId, param, categoryType) {
  return request({
    url: '/staticResource/upload/' + fileId + '/' + categoryType,
    method: 'post',
    headers: { 'Content-Type': 'multipart/form-data' },
    data: param,
    loading: false
  })
}

export function uploadFileResult(file, callback, fileCategory) {
  const fileId = uuid.v1()
  const fileName = file.name
  const newFileName = fileId + fileName.substr(fileName.lastIndexOf('.'), fileName.length)
  const fileUrl = store.state.staticResourcePath + newFileName
  const categoryType = fileCategory
  const param = new FormData()
  param.append('file', file)
  uploadFile(fileId, param, categoryType).then(() => {
    callback(fileUrl)
  })
}

export function uploadPictureResult(fileName, callback) {
  const newFileName = fileName
  const fileUrl = store.state.staticResourcePath + newFileName
  callback(fileUrl)
}

export function findResourceAsBase64(params) {
  return request({
    url: '/staticResource/findResourceAsBase64',
    method: 'post',
    data: params,
    loading: false
  })
}

export function findAllResourceAsBase64() {
  return request({
    url: '/staticResource/findAllResourceAsBase64',
    method: 'post',
    loading: false
  })
}

export function findAllResourceName() {
  return request({
    url: '/staticResource/findAllResourceName',
    method: 'post',
    loading: false
  })
}

export function findResourceNameByCondition(nodeId) {
  return request({
    url: '/staticResource/findResourceNameByCondition',
    method: 'post',
    data: nodeId,
    loading: false
  })
}

export function delImg(imgNames) {
  return request({
    url: '/staticResource/deleteImgByName',
    method: 'post',
    data: imgNames,
    loading: true
  })
}

