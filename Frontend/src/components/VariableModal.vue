<template>
  <b-modal ref="modal" id="var-modal" title="Crea una variable" no-close-on-esc hide-header-close @ok="OkCreateVariable">
    <form ref="form" class="m-4" @submit.stop.prevent="CreateVariable">
      <b-form-group label="Nombre de la variable" invalid-feedback="El nombre es requerido" :state="states.name">
        <b-form-input ref="name" required placeholder="Nombre" v-model="newVar.name" :state="states.name"></b-form-input>
      </b-form-group>
      <b-form-group label="Valor mínimo" invalid-feedback="El mínimo es requerido" :state="states.min">
        <b-form-input ref="min" required placeholder="Mínimo" v-model="newVar.min" :state="states.min" type="number"></b-form-input>
      </b-form-group>
      <b-form-group label="Valor máximo" invalid-feedback="El máximo es requerido" :state="states.max">
        <b-form-input ref="max" required placeholder="Máximo" v-model="newVar.max" :state="states.max" type="number"></b-form-input>
      </b-form-group>
    </form>
  </b-modal>
</template>

<script>
export default {
    name: 'VariableModal'    
}
</script>