import {
  createStore
} from 'vuex'

export default createStore({
  state: {
    role: {},
  },
  mutations: {
    SET_ROLE(state, params) {
      state.role = params.role;
    }
  },
  actions: {},
  modules: {}
})