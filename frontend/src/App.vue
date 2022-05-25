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

        <h1 class="white--text">bim-examples</h1>
      </div>
    </v-app-bar>

    <v-main>
      <v-container class="fill-height">
        <v-row class="fill-height">
          <v-col
              cols="12"
              md="9"
              style="background-color: #333333"
          >
            <v-btn
                type="submit"
                color="primary"
            >
              Upload
            </v-btn>
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

export default {
  name: 'App',
  data: () => ({
    ifcLoader: new IFCLoader(),
    scene: null,
    ifcModel: -1,
  }),
  methods: {
    createScene() {
      this.scene = createScene(this.$refs.scene3d);
    },
    loadIfcModel(ifcModel) {
      if (this.ifcModel.modelID > -1) {
        this.scene.remove(this.ifcModel);
      }
      this.scene.add(ifcModel);
      this.ifcModel = ifcModel;
    },
    onIfcFileInputChange(changed) {
      const [ file ] = changed.target.files;
      const ifcURL = URL.createObjectURL(file);
      this.ifcLoader.load(
          ifcURL,
          this.loadIfcModel
      );
    },
  },
  mounted() {
    const wasmPath = '../files/';
    this.ifcLoader.ifcManager.setWasmPath(wasmPath)
    this.createScene();
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
