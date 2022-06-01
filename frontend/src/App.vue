<template>
  <v-app id="app">
    <v-app-bar
      app
      color="primary"
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
              cols="4"
          >
            <v-file-input
                show-size
                accept=".ifc"
                label="Select an IFC file to visualize"
                @change="onIfcFileInputChange"
            >
              Upload
            </v-file-input>
            <div>
              <v-text-field
                  v-model="fileName"
                  placeholder="What's the name of your uploaded file?"
              ></v-text-field>
              <v-btn
                  color="primary"
                  :disabled="fileToUpload == null"
                  @click="onUploadButtonClick"
              >
                Upload
              </v-btn>
              <v-progress-circular
                  v-show="uploadingFile"
                  indeterminate
                  color="primary"
              ></v-progress-circular>
            </div>
            <div>
              <v-skeleton-loader
                  v-if="loadingFilesList"
                  type="table"
              ></v-skeleton-loader>
              <v-simple-table
                  v-else
                  fixed-header
                  height="500px"
              >
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
                    <td><span @click="onItemNameClick(item.id)" style="cursor: pointer">{{ item.name }}</span></td>
                  </tr>
                  </tbody>
                </template>
              </v-simple-table>
            </div>
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
          <v-col
            cols="8"
          >
            <canvas id="scene3d" ref="scene3d"></canvas>
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
  create,
  updateFile,
  findFileBlobById,
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
    uploadingFile: false,
    fileToUpload: null,
    fileName: '',
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
      this.uploadingFile = true;
      let uploadedFileName = this.trimIfcFileExtension(file.name);
      if (this.fileName.trim() !== '') {
        uploadedFileName = this.fileName;
      }
      const { data } = await create(new FileDto(uploadedFileName));
      await updateFile(data.id, file);
      await this.findAllFiles();
      this.uploadingFile = false;
    },
    async findAllFiles() {
      return findAllSummaries()
          .then(({ data }) => {
            this.filesList = data;
            this.loadingFilesList = false;
          });
    },
    async downloadFile(fileId) {
      const { data } = await findFileBlobById(fileId);
      return new File([data], 'temp');
    },
    onIfcFileInputChange(file) {
      this.fileToUpload = file;
      if (file) {
        this.renderFile(file);
      }
    },
    onUploadButtonClick() {
      if (this.fileToUpload == null) {
        return;
      }
      this.uploadFile(this.fileToUpload);
    },
    onItemNameClick(itemId) {
      // this.dialogShown = true;
      this.downloadFile(itemId)
        .then(this.renderFile);
    },
    /**
     * @param {string} fileName
     */
    trimIfcFileExtension(fileName) {
      if (!fileName.endsWith('.ifc')) {
        throw new Error('File should be an .ifc file!');
      }
      const extensionIndex = fileName.lastIndexOf('.ifc');
      const nameWithoutExtension = fileName.slice(0, extensionIndex);
      if (nameWithoutExtension.trim() === '') {
        throw new Error('File name should not be empty!');
      }
      return nameWithoutExtension;
    },
  },
  created() {
    this.findAllFiles();
  },
  mounted() {
    const wasmPath = '../files/';
    this.ifcLoader.ifcManager.setWasmPath(wasmPath)
    this.createScene();
  },
}
</script>

<style>
#scene3d {
  width: 100%;
  height: 100%;
  display: block;
}
</style>
