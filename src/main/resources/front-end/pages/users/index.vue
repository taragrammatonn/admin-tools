<template>
  <section>
    <h1>{{pageTitle}}</h1>
    <p>Count: {{count}}</p>
    <ul>
      <li v-for="user of users" :key="user.id">
        <a href="#" @click.prevent="openUser(user)">{{user.fullName}}</a>
      </li>
    </ul>
  </section>
</template>

<script>
import {Observable} from 'rxjs/Observable'
import 'rxjs/add/observable/interval'
import { webSocket } from 'rxjs/webSocket'

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

    subject.next({message: 'message'}) // <- ping first message
    subject.subscribe(message => {       // <- listen messages from server
      this.$store.dispatch('users/pushUser', message)
    });

    const obs = Observable.interval(1000)
    obs.subscribe(
      (value => this.count = value)
    )
  }
}
</script>

