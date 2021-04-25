export const state = () => ({
  users: [],
  user: null
})

export const mutations = {
  setUsers(thisState, users) {
    thisState.users = users
  },

  setUser(thisState, user) {
    thisState.user = user
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
  }
}

export const getters = {
  users: s => s.users,
  user: s => s.user
}
