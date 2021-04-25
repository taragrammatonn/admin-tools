<template>
  <section>
    <h1>{{user.fullName}}</h1>

    <hr>

    <h3>{{user.userGroup}}</h3>
  </section>
</template>

<script>
export default {
  middleware: ['auth-user'],
  validate({params}) {
    return /^\d+$/.test(params.id)
  },
  async fetch({store, params}) {
    store.dispatch('users/getUser', params.id)
  },
  computed: {
    user() {
      console.log(this.$store.getters['users/user'])
      return this.$store.getters['users/user']
    }
  },
}
</script>

