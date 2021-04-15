export const state = () => ({
  users: []
})

export const mutations = {
  setUsers(state, users) {
    state.users = users
  }
}

export const actions = {
  async fetch({commit}) {
    await this.$axios.$get('/users')
      .then(response => {
        commit('setUsers', response)
    })
  }
}

export const getters = {
  users: s => s.users
}
