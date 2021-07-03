<template>
  <div id="app">
    <Header/>
    <Home/>
    <Footer/>
  </div>
</template>

<script>
import Home from './views/Home.vue'
import Header from './components/Header.vue'
import Footer from './components/Footer.vue'

export default {
  name: 'App',
  components: {
    Header,
    Home,
    Footer
  }
}
</script>

<style>
#app {
  font-family: "jost";
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
}
</style>
