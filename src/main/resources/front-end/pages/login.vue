<template>
  <section>
    <div v-if="$route.query.message" class="alert alert-danger mb-3">
      {{ $route.query.message }}
    </div>
    <form class="mt-8 space-y-6" id="loginForm" @submit.prevent="login">

      <div>
        <img class="mx-auto h-40 w-auto" src="https://medialeaks.ru/wp-content/uploads/2020/01/mem-600x338.jpg" alt="Workflow">
        <h2 class="mt-6 text-center text-3xl font-extrabold text-gray-900">Войдите в свой аккаунт</h2>
        <p class="mt-2 text-center text-sm text-gray-600">или ты не пройдешь</p>
      </div>

      <div class="rounded-md shadow-sm -space-y-px">
        <div class="form-group">
          <input type="text" class="form-control" v-model="user.username">
        </div>
        <div class="form-group">
          <input type="password" class="form-control" v-model="user.password">
        </div>
      </div>

      <div class="flex items-center justify-between">
        <div class="flex items-center">
          <input id="remember_me" name="remember_me" type="checkbox"
                 class="h-4 w-4 text-indigo-600 focus:ring-indigo-500 border-gray-300 rounded">
          <label for="remember_me" class="ml-2 block text-sm text-gray-900">
            Запомни меня
          </label>
        </div>

        <div class="text-sm">
          <a href="#" class="font-medium text-indigo-600 hover:text-indigo-500">
            Забыли Ваш пароль?
          </a>
        </div>
      </div>

      <div>
        <button type="submit"
                class="group relative w-full flex justify-center py-2 px-4 border border-transparent text-sm font-medium rounded-md text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500">
          <span class="absolute left-0 inset-y-0 flex items-center pl-3">
            <!-- Heroicon name: solid/lock-closed -->
            <svg class="h-5 w-5 text-indigo-500 group-hover:text-indigo-400" xmlns="http://www.w3.org/2000/svg"
                 viewBox="0 0 20 20" fill="currentColor" aria-hidden="true">
              <path fill-rule="evenodd"
                    d="M5 9V7a5 5 0 0110 0v2a2 2 0 012 2v5a2 2 0 01-2 2H5a2 2 0 01-2-2v-5a2 2 0 012-2zm8-2v2H7V7a3 3 0 016 0z"
                    clip-rule="evenodd"/>
            </svg>
          </span>
          Sign in
        </button>
      </div>
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
