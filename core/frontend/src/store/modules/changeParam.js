const getDefaultState = () => {
  return {
    showStaticDataConfig: true,
    excelCustomData: [],
    linkStaticDataShow: false
  }
}

const state = getDefaultState()

const mutations = {
  setShowStaticDataConfig: (state, data) => {
    state.showStaticDataConfig = data
  },
  setExcelCustomData: (state, data) => {
    state.excelCustomData = data
  },
  setLinkStaticDataShow: (state, data) => {
    state.linkStaticDataShow = data
  }
}

const actions = {
  setShowStaticDataConfig({ commit }, data) {
    commit('setShowStaticDataConfig', data)
  },
  setExcelCustomData({ commit }, data) {
    commit('setExcelCustomData', data)
  },
  setLinkStaticDataShow({ commit }, data) {
    commit('setLinkStaticDataShow', data)
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
