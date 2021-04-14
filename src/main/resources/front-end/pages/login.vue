<template>
  <section>
    <div v-if="$route.query.message" class="alert alert-danger mb-3">
      {{ $route.query.message }}
    </div>
    <form id="loginForm" @submit.prevent="login">
      <h1>Login page </h1>
      <div class="form-group">
        <input type="text" class="form-control" v-model="user.username">
      </div>
      <div class="form-group">
        <input type="password" class="form-control" v-model="user.password">
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
      user: {
      username: '',
      password: ''
      },
      error: null,
    }
  },

  methods: {
    login() {
      this.$store.dispatch('login', {data: this.user})
      .then(() => {
        console.log(this)
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
