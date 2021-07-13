<template>
  <b-navbar class="mt-0">
    <b-container>
      <b-navbar-brand style="margin: auto;">
        <b-row align-v="center">
          <b-col>
            <img src="../assets/cpp-logo.png" style="height: 5rem;"/>
          </b-col>
          <b-col>
            <h1> <strong>Semantic Tool</strong> </h1>
          </b-col>
        </b-row>
        <b-row align-v="center" class="mt-4">
          <b-col>
            <h6>Herramienta para revisar tu sintaxis.</h6>
          </b-col>
        </b-row>
      </b-navbar-brand>
    </b-container>
  </b-navbar>
</template>

<script>
export default {
  name: 'Header'
}
</script>
<style>
.navbar {
 background: black;
 color: white;
 margin-left: 0;
 margin-right: 0;
}
</style>