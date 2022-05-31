<template>
  <div id="app">
    <layout></layout>
  </div>
</template>

<script>
import Layout from '@/views/layout.vue'

export default {
  name: 'app',
  components: {
    Layout
  }
}
</script>

<style>
* {
  box-sizing: border-box;
}
body {
  margin: 0;
}
#app {
}
</style>
