<template>
  <v-app id="app">
    <v-app-bar
      app
      color="primary"
      dark
    >
      <div class="d-flex align-center">
        <v-img
          alt="Vuetify Logo"
          class="shrink mr-2"
          contain
          src="https://cdn.vuetifyjs.com/images/logos/vuetify-logo-dark.png"
          transition="scale-transition"
          width="40"
        />

        <h1 class="white--text">Demo BIM</h1>
      </div>
    </v-app-bar>

    <v-main>
      <v-container class="fill-height">
        <v-row class="fill-height">
          <v-col
              cols="12"
              md="3"
          >
            <h2>Slab dimensions</h2>
            <v-form
              @submit.prevent="onFormSubmit"
            >
              <v-text-field
                  v-model.number="width"
                  label="Width"
                  type="number"
                  dark
                  required
                  @focus="onTextFieldNumberFocus"
              ></v-text-field>
              <v-text-field
                  v-model.number="length"
                  label="Length"
                  type="number"
                  dark
                  required
                  @click="onTextFieldNumberFocus"
              ></v-text-field>
              <v-text-field
                  v-model.number="height"
                  label="Height"
                  type="number"
                  dark
                  required
                  @click="onTextFieldNumberFocus"
              ></v-text-field>
              <v-btn
                type="submit"
                color="primary"
              >
                Generate
              </v-btn>
            </v-form>
          </v-col>
          <v-col
              cols="12"
              md="9"
              style="background-color: #333333"
          >
            <div class="fill-height">
              <input
                  type="file"
                  name="load"
                  class="file-input"
                  @change="onIfcFileInputChange"
              />
              <canvas id="scene3d" ref="scene3d"></canvas>
            </div>
          </v-col>
        </v-row>
      </v-container>
    </v-main>
  </v-app>
</template>

<script>
import { IFCLoader } from "web-ifc-three/IFCLoader";
import { createScene } from "@/services/ifcScripts";
import { ifcWriter } from "@/services/ifcWriter";

export default {
  name: 'App',
  data: () => ({
    ifcLoader: new IFCLoader(),
    scene: null,
    ifcString: '',
    width: 300,
    length: 400,
    height: 50,
  }),
  methods: {
    onFormSubmit() {
      this.renderIfcStructure();
    },
    onTextFieldNumberFocus(event) {
      this.selectInputText(event);
    },
    selectInputText(event) {
      event.target.select();
    },
    createScene() {
      this.scene = createScene(this.$refs.scene3d);
    },
    onIfcFileInputChange(changed) {
      const [ file ] = changed.target.files;
      const ifcURL = URL.createObjectURL(file);
      this.ifcLoader.load(
          ifcURL,
          (ifcModel) => this.scene.add(ifcModel.mesh));
    },
    async generateIfcStructure() {
      const ifcApi = new IFCLoader().ifcManager.ifcAPI;
      ifcApi.SetWasmPath('../files/');

      // initialize the library
      await ifcApi.Init();
      this.ifcString = ifcWriter.writeIFC(ifcApi);
    },
    renderIfcStructure() {
      const ifcBlob = new Blob([this.ifcString], {type: 'text/plain'});
      const ifcURL = URL.createObjectURL(ifcBlob);
      this.ifcLoader.load(
          ifcURL,
          (ifcModel) => this.scene.add(ifcModel.mesh)
      );
    },
  },
  async mounted() {
    this.ifcLoader.ifcManager.setWasmPath('../files/')
    this.createScene();
    await this.generateIfcStructure();
  },
}
</script>

<style>
#app {
  background-color: #121212;
}

h1, h2, h3, h4, h5, h6 {
  color: rgba(255, 255, 255, 0.7);
}

#scene3d {
  width: 100%;
  height: 100%;
  display: block;
}

.file-input {
  z-index: 1;
  position: absolute;
}
</style>
