export const state = () => ({
  token: null,
  error: null
})

export const mutations = {
  setToken(state, token) {
    state.token = token
  },
  clearToken(state) {
    state.token = null
  },

  setError(state, error) {
    state.error = error
  },

  getToken(state) {
    return state.token
  }
}

export const actions = {
  async authorization({commit, store}, login) {
    await this.$auth.loginWith('local', {
      data: login.data
    }).then(result => {
      if (result.status && result.status === 200) {
        commit('setToken', result.data)
        this.$router.push('/')
      }
    }).catch(error => {
      if (error.response) {
        switch (error.response.status) {
          case 401:
            this.$router.push('/login?message=Unauthorized')
            break
          case 403:
            this.$router.push('/login?message=Invalid Login or Password')
            break
        }
      } else console.log(error)
    })
  },

  logout({commit}) {
    commit('clearToken')
  },
}

export const getters = {
  hasToken: s => !!s.token,
  getError: s => s.error,
  getToken: s => s.token
}
