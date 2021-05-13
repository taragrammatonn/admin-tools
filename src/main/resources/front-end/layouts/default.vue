<template>
  <div>
    <Navbar/>
    <div class="relative" style="padding-top: 1rem;padding-bottom: 1rem;">
      <header-stats/>
    </div>
    <main>
      <div class="container">
        <nuxt/>
      </div>
    </main>
  </div>
</template>

<script>
import Navbar from '@/components/navbars/Navbar'
import HeaderStats from "@/components/headers/HeaderStats";

export default {
  components: {
    Navbar,
    HeaderStats
  }
}
</script>


<style>

</style>
