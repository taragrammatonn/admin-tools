export const state = () => ({
  users: [],
  user: null
})

export const mutations = {
  setUsers(state, users) {
    state.users = users
  },

  setUser(state, user) {
    state.user = user
  },

  addUser(state, user) {
    state.users.push(user)
  }
}

export const actions = {
  async fetch({commit}) {
    await this.$axios.$get('/users')
      .then(response => {
        commit('setUsers', response)
    })
  },

  async getUser({commit}, id) {
    await this.$axios.$get('/user/'+ id)
      .then(response => {
        console.log(response)
        commit('setUser', response)
      })
  },

  pushUser({commit}, user) {
    console.log(user)
    commit('addUser', user)
  }
}

export const getters = {
  users: s => s.users,
  user: s => s.user
}
