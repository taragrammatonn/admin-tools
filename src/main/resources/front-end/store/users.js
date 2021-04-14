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
    await this.$axios({
      method: 'GET',
      url: 'api/users',
      headers: {
        'Access-Control-Allow-Origin': '*',
        'Authorization': `Bearer ${this.getters.getToken}`,
      }
    }).then(response => {
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
