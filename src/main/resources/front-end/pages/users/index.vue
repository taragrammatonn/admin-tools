<template>
  <section>
    <h1>{{pageTitle}}</h1>

    <ul>
      <li v-for="user of users" :key="user.id">
        <a href="#" @click.prevent="openUser(user)">{{user.fullName}}</a>
      </li>
    </ul>
  </section>
</template>

<script>
export default {
  async fetch({store}) {
    if (store.getters['users/users'].length === 0) {
      await store.dispatch('users/fetch')
    }
  },
  data: () => ({
    pageTitle: 'Users page'
  }),
  computed: {
    users() {
      console.log(this.$store.getters)
      return this.$store.getters['users/users']
    }
  },
  methods: {
    openUser(user) {
      console.log(user)
      this.$router.push('/users/' + user.id)
    }
  }
}
</script>

