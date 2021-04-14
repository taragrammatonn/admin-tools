<template>
  <section>
    <div v-if="$route.query.message" class="alert alert-danger mb-3">
      {{ $route.query.message }}
    </div>
    <form id="loginForm" @submit.prevent="authorization">
      <h1>Login page </h1>
      <div class="form-group">
        <input type="text" class="form-control" v-model="login.user">
      </div>
      <div class="form-group">
        <input type="password" class="form-control" v-model="login.password">
      </div>
      <p>
        <nuxt-link to="/">To home page</nuxt-link>
      </p>

      <button class="btn btn-primary" type="submit">Login</button>
    </form>
  </section>
</template>

<script>
export default {
  layout: 'empty',
  data() {
    return {
      login: {
        user: '',
        password: ''
      },
      error: null,
    }
  },

  methods: {
    authorization() {
      this.$store.dispatch('authorization', {data: this.login})
        .then(() => {
          console.log(this.$auth.loggedIn)
          if (this.$auth.loggedIn) {

          }
        })
    }
  }
}
</script>

<style scoped>
form {
  width: 500px;
  margin: 0 auto;
}
</style>
