<template>
  <footer> 
    <b-row id="top" align-v="center" align-h="center" class="mx-0">
      <b-col>
        <b-row align-v="center" >
          <b-col style="margin-left: auto; margin-right: auto;">
              <img src="../assets/cpp-logo.png" style="height: 3rem;"/>
              <h3 style="display: inline" class="ml-4 align-middle">Semantic Tool</h3>
          </b-col>
        </b-row>
        <b-row>
          <b-col>
            <p>Desarrollado por: Ricardo Andrés Calvo y Diego Felipe López</p>
          </b-col>
        </b-row>
      </b-col>
    </b-row>
    <div id="bottom">
      <b-row align-h="center">
        <b-col class="mt-2 mb-2"><p>&copy; 2021 C++ Semantic Tool. Universidad Nacional de Colombia - Lenguajes de programación.</p></b-col>
      </b-row>
    </div>
  </footer>
</template>

<script>
export default {
    name: 'Footer'
}
</script>