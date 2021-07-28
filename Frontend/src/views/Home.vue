<template>
  <div>
    <!-- Form -->
    <b-form @submit.prevent="Evaluate">
      <b-container id="content">
        <b-overlay :show="fields.loading" variant="secondary" spinner-variant="primary">
          <b-row class="my-4">
            <b-col cols="8">
              <b-row align-h="center" align-v="center" class="mb-4">
                <b-col class="mb-4">
                  <h3>Código</h3>
                </b-col>
              </b-row>
              <b-row align-h="center" align-v="center">
                <b-col>
                  <prism-editor v-model="content" :highlight="highlighter" line-numbers class="editor"></prism-editor>
                </b-col>
              </b-row>
              <b-row align-h="center" align-v="center">
                <b-col>
                  <b-form-file v-model="file" type="file" accept=".cpp" placeholder="Seleciona el archivo" class="mt-4 text-left"/>
                </b-col>
              </b-row>
            </b-col>
            <b-col cols="4">
              <b-row align-h="center" align-v="center" class="mb-4">
                <b-col class="mb-4">
                  <h3>Variables</h3>
                </b-col>
              </b-row>
              <b-row>
                <b-col class="mb-4">
                  <b-button variant="primary" block v-b-modal.var-modal>Añadir variable</b-button>
                </b-col>
              </b-row>
              <b-row>
                <b-col>
                  <div class="variables">
                    <b-card v-for="(v, index) in vars" :key="index" class="mb-2">
                      <b-card-title style="color: #212529;"><vue-mathjax :formula="'$$' + v.min + ' \\leq ' + v.name + ' \\leq ' + v.max + '$$'"/></b-card-title>
                      <b-button variant="primary" size="sm" @click="vars.splice(index, 1)">Eliminar</b-button>
                    </b-card>
                  </div>
                </b-col>
              </b-row>
            </b-col>
          </b-row>
          <b-row align-h="center" align-v="center">
            <b-col cols="12">
              <b-button block variant="primary" type="submit">Analizar código</b-button>
            </b-col>
          </b-row>
        </b-overlay>
      </b-container>
    </b-form>
    <!-- Results -->
    <h1 class="mb-3">Resultados</h1>
    <b-container class="mb-5">
      <b-row v-if="detectedErrors">
        <b-col v-for="(error, index) in detectedErrors" :key="index" xl="3" md="3" sm="6" class="pr-4">
          <b-card :title="error.type" img-width="370rm" title-text-variant="secondary" class="mb-3">
            <p>{{error.message}}</p>
            <b-row>
              <b-col md="6" sm="6">
                <p> <strong>Fila:</strong>  {{error.row}}</p>
              </b-col>
              <b-col md="6" sm="6">
                <p> <strong>Columna:</strong>  {{error.col}}</p>
              </b-col>
            </b-row>

          </b-card>
        </b-col>
      </b-row>
      <b-row v-if="detectedErrors.length === 0 && view && synErrors.length === 0">
        <b-col  :key="index" xl="12" md="12" sm="12" class="pr-4">
          <b-card :title="'No se encontraron errores'" img-width="370rm" title-text-variant="secondary">

          </b-card>
        </b-col>
      </b-row>
      <b-row v-if="synErrors.length !== 0">
        <h4>Se encontraron los siguientes errores en su sintáxis: </h4>
      </b-row>
      <b-row v-if="synErrors">
        <b-col v-for="(error, index) in synErrors" :key="index" xl="3" md="3" sm="6" class="pr-4">
          <b-card :title="error.type" img-width="370rm" title-text-variant="secondary" class="mb-3">
            <p>{{error.message}}</p>
            <b-row>
              <b-col md="6" sm="6">
                <p> <strong>Fila:</strong>  {{error.row}}</p>
              </b-col>
              <b-col md="6" sm="6">
                <p> <strong>Columna:</strong>  {{error.col}}</p>
              </b-col>
            </b-row>

          </b-card>
        </b-col>
      </b-row>
    </b-container>

    <!--Variable Modal-->
    <b-modal ref="modal" id="var-modal" title="Crea una variable" no-close-on-esc hide-header-close @ok="OkCreateVariable">
      <form ref="form" class="m-4" @submit.stop.prevent="CreateVariable">
        <b-form-group label="Nombre de la variable" invalid-feedback="El nombre es requerido" :state="states.name">
          <b-form-input ref="name" required placeholder="Nombre" v-model="newVar.name" :state="states.name"></b-form-input>
        </b-form-group>
        <b-form-group label="Valor mínimo" invalid-feedback="El mínimo es requerido y debe ser menor al maximo" :state="states.min">
          <b-form-input ref="min" v-bind:max="newVar.max" required placeholder="Mínimo" v-model="newVar.min" :state="states.min" type="number"></b-form-input>
        </b-form-group>
        <b-form-group label="Valor máximo" invalid-feedback="El máximo es requerido y debe ser mayor al mínimo" :state="states.max">
          <b-form-input ref="max" v-bind:min="newVar.min" required placeholder="Máximo" v-model="newVar.max" :state="states.max" type="number"></b-form-input>
        </b-form-group>
      </form>
    </b-modal>
  </div>
</template>

<script>
import { PrismEditor } from 'vue-prism-editor';
import 'vue-prism-editor/dist/prismeditor.min.css';
import { highlight, languages } from 'prismjs/components/prism-core';
import 'prismjs/components/prism-clike';
import 'prismjs/components/prism-c';
import 'prismjs/components/prism-cpp';
import 'prismjs/themes/prism-tomorrow.css';
import { VueMathjax } from 'vue-mathjax';
import axios from 'axios';
export default {
  name: 'Home',
  data(){
    return {
      content: "#include <iostream>\n\nint main(){\n\tstd::cout << \"Hello world!\" << std::endl;\n\treturn 0;\n}",
      vars: [],
      file: File,
      states: {
        name: null,
        min: null,
        max: null
      },
      newVar: {
        name: "",
        min: 0,
        max: 0
      },
      fields:{
        loading: false
      },
      detectedErrors: [],
      view: false,
      synErrors: []
    }
  },
  components: {
    PrismEditor,
    'vue-mathjax': VueMathjax
  },
  methods: {
    highlighter(code) {
      return highlight(code, languages.cpp);
    },
    CreateVariable(){
      if (!this.checkFormValidity()) {
        return;
      }
      let copy = Object.assign({}, this.newVar);
      this.vars.push(copy);
      console.log(this.vars);
      this.$nextTick(() => {
        this.$bvModal.hide('var-modal');
      });
    },
    OkCreateVariable(bvModalEvt){
      bvModalEvt.preventDefault();
      this.CreateVariable();
    },
    checkFormValidity(){
      let flag = true;
      this.states.name = this.$refs.name.checkValidity();
      flag &= this.states.name;
      this.states.min = this.$refs.min.checkValidity();
      flag &= this.states.min;
      this.states.max = this.$refs.max.checkValidity();
      flag &= this.states.max;
      return flag;
    },
    Evaluate(){
      const self = this;
      this.fields.loading = true;
      const request = {
        "code": this.content,
        "variables": this.vars
      };
      this.detectedErrors = [];
      axios.post('https://cpp-semantic-tool.azurewebsites.net/evaluate', request)
          .then(function(response){
            let semanticErrors = response.data;
            if(semanticErrors.length > 0){
              // Mostrar Errores Semánticos
              console.log(semanticErrors);
              self.detectedErrors = semanticErrors;
            }else{
              // Mostrar No hay Errores Semáticos
              console.log("No hay errores semánticos");

            }
            self.fields.loading = false;
            self.view = true
          }).catch(function(error){
            if(error.response.status == '400'){
              self.synErrors = error.response.data;
            }else if(error.response){
              // Hay Error interno en el servidor.
            }else if(error.request){
              // El servidor no ha respondido.
            }
            self.fields.loading = false;
          }
      );
    }
  },
  created(){
    /*this.vars.push({
      name: "A",
      min: 1,
      max: 2
    });
    this.vars.push({
      name: "B",
      min: 3,
      max: 4
    });
    this.vars.push({
      name: "C",
      min: 5,
      max: 6
    });
    this.vars.push({
      name: "D",
      min: 7,
      max: 8
    });
    this.vars.push({
      name: "E",
      min: 9,
      max: 10
    });
    this.vars.push({
      name: "F",
      min: 11,
      max: 12
    });
    console.log(this.vars);*/
  },
  watch: {
    file(newfile, oldfile){
      if(newfile !== oldfile){
        const reader = new FileReader();
        reader.onload = e => this.content = e.target.result;
        reader.readAsText(newfile);
      }else{
        console.log(newfile);
      }
    }
  }
}
</script>

<style>
.navbar{
  margin: 2em;
  white-space: pre;
}
.navbar .navbar-nav .nav-link {
  letter-spacing: 0.5px;
  padding-right: 1.5rem;
  padding-left: 1.5rem;
}
#top {
  background:  #004871;
  padding: 1rem;
  color: white;
}
#top p {
  text-align: center;
  padding-left: 1rem;
  padding-right: 1rem;
  padding-top: 1rem;
  margin-top: 1rem;
  margin-bottom: 1rem;
}
#bottom {
  background: rgb(0, 0, 0);
  color: white;
  padding: 1rem;
}
#bottom a{
  white-space: pre;
  color: var(--secondary);
}
.editor {
  background: #2d2d2d;
  color: #ccc !important;
  font-family: Fira code, Fira Mono, Consolas, Menlo, Courier, monospace;
  font-size: 14px;
  line-height: 1.5;
  padding: 5px;
  height: 28rem;
  overflow: scroll;
}
.variables {
  overflow-y: scroll;
  padding: 5px;
  height: 28rem;
}
.modal-title{
  color: #212529 !important;
}
.card {
  border-radius: 1rem !important;
  color: black;
}
.card-title {
  color: black;
}
pre{
  color: #ccc !important;
}
#content{
  height: 45rem;
  font-family: 'Dosis', sans-serif;
}
.modal-dialog > .modal-content{
  background-color: #ffffff !important;
  color: black;
  border-radius: 20px;
}
</style>