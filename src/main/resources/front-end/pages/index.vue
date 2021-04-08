<template>
  <div>
    <h1 @click="getData">Hello</h1>
  </div>
</template>

<script>
export default {

  methods: {
    async getData() {
      await fetch('/hello')
        .then((res) => {
          if (res.status === 401) {
            this.$router.push('/auth/login')
          }
        })
    }
  }
}
</script>

<style>

</style>
