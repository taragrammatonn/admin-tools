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
  },

  removeUser(state, deletedUser) {
    console.log(deletedUser)
    state.users.splice(state.users.findIndex(user => user.id === deletedUser.id), 1)
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
        commit('setUser', response)
      })
  },

  pushUser({commit}, user) {
    commit('addUser', user)
  },

  removeUser({commit}, user) {
    commit('removeUser', user)
  }
}

export const getters = {
  users: s => s.users,
  user: s => s.user
}
