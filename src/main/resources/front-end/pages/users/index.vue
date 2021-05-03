<template>
  <section>
    <h1>{{ pageTitle }}</h1>
    <p>Count: {{ count }}</p>
    <ul>
      <li v-for="user of users" :key="user.id">
        <a href="#" @click.prevent="openUser(user)">{{ user.fullName }}</a>
      </li>
    </ul>
  </section>
</template>

<script>
import 'rxjs/add/observable/interval'
import {webSocket} from 'rxjs/webSocket'

export default {
  middleware: ['auth-user'],
  async fetch({store}) {
    if (store.getters['users/users'].length === 0) {
      await store.dispatch('users/fetch')
    }
  },
  data: () => ({
    messages: [],
    count: 0,
    pageTitle: 'Users page'
  }),
  computed: {
    users() {
      return this.$store.getters['users/users']
    }
  },
  methods: {
    openUser(user) {
      this.$router.push('/users/' + user.id)
    }
  },
  created() {
    const subject = webSocket('ws://localhost:8080/push')

    subject.next({message: 'Connected'})
    subject.subscribe(user => {
      console.log(user)
      !user.isDeleted ?
        this.$store.dispatch('users/pushUser', user) :
        this.$store.dispatch('users/removeUser', user)
    });
  }
}
</script>

