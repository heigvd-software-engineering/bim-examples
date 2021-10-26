<template>
  <div id="app">
    <img alt="Vue logo" src="./assets/logo.png">
    <IFCViewer />
  </div>
</template>

<script>
import IFCViewer from './components/IFCViewer.vue'

export default {
  name: 'App',
  components: {
    IFCViewer,
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
