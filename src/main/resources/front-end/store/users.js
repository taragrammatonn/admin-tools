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
    console.log(this.getters.getToken)
    await this.$axios.$get('/users')
      .then(response => {
      console.log(response.status)
      if (response.status === 200) {
        commit('setUsers', response.data)
      }
    })
  }
}

export const getters = {
  users: s => s.users
}
