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
      <v-container>
        <v-row>
          <v-col
              cols="12"
              md="9"
              style="background-color: #333333"
          >
            <v-file-input
                dark
                show-size
                accept=".ifc"
                @change="onIfcFileInputChange"
            >
              Upload
            </v-file-input>
            <div>
              <v-skeleton-loader
                  v-if="loadingFilesList"
                  type="table"
              ></v-skeleton-loader>
              <v-simple-table v-else>
                <template v-slot:default>
                  <thead>
                  <tr>
                    <th class="text-left">
                      Name
                    </th>
                  </tr>
                  </thead>
                  <tbody>
                  <tr
                      v-for="item in filesList"
                      :key="item.id"
                  >
                    <td><span @click="onItemNameClick" style="cursor: pointer">{{ item.name }}</span></td>
                  </tr>
                  </tbody>
                </template>
              </v-simple-table>
            </div>
            <canvas id="scene3d" ref="scene3d"></canvas>
            <v-dialog
                v-model="dialogShown"
                width="500"
            >
              <v-card>
                <v-card-title class="text-h5 grey lighten-2">
                  Hola!
                </v-card-title>

                <v-card-text>
                  Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
                </v-card-text>

                <v-divider></v-divider>

                <v-card-actions>
                  <v-spacer></v-spacer>
                  <v-btn
                      color="primary"
                      text
                      @click="dialogShown = false"
                  >
                    I accept
                  </v-btn>
                </v-card-actions>
              </v-card>
            </v-dialog>
          </v-col>
        </v-row>
      </v-container>
    </v-main>
  </v-app>
</template>

<script>
import { IFCLoader } from "web-ifc-three/IFCLoader";
import { createScene } from "@/services/ifcScripts";
import {
  findAllSummaries,
  findFileBlobById,
  create,
  updateFile,
} from "@/services/ifcResourceService";
import { FileDto } from "@/dtos/FileDto";

export default {
  name: 'App',
  data: () => ({
    ifcLoader: new IFCLoader(),
    scene: null,
    ifcModel: -1,
    loadingFilesList: true,
    filesList: [],
    dialogShown: false,
    loadingIfcFile: true,
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
    renderFile(file) {
      const ifcURL = URL.createObjectURL(file);
      this.ifcLoader.load(
          ifcURL,
          this.loadIfcModel
      );
      URL.revokeObjectURL(ifcURL);
    },
    async uploadFile(file) {
      console.log(file)
      const { data } = await create(new FileDto(file.name));
      return updateFile(data.id, file);
    },
    onIfcFileInputChange(file) {
      this.uploadFile(file);
      this.renderFile(file);
    },
    onItemNameClick() {
      this.dialogShown = true;
    },
  },
  created() {
    findAllSummaries()
      .then(({ data }) => {
        this.filesList = data;
        this.loadingFilesList = false;
      });

    findFileBlobById(1).then(({ data }) => {
      this.loadingIfcFile = false;
      const ifcBlob = new Blob([data], {type: 'text/plain'});
      const ifcUrl = URL.createObjectURL(ifcBlob);
      this.ifcLoader.load(
          ifcUrl,
          this.loadIfcModel
      );
      URL.revokeObjectURL(ifcUrl);
    });
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
</style>
