<template>
<div class="fill-height">
  <input
      type="file"
      name="load"
      class="file-input"
      @change="onIfcFileInputChange"
  />
  <canvas id="scene3d" ref="scene3d"></canvas>
</div>
</template>

<script>
import { IFCLoader } from 'web-ifc-three/IFCLoader';
import { createScene } from '@/components/ifcScripts';
import { ifcWriter } from '@/services/ifcWriter';
import WebIFC from 'web-ifc';

export default {
  name: "IFCViewer",
  data: () => ({
    ifcLoader: new IFCLoader(),
    scene: null,
  }),
  methods: {
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
  },
  async mounted() {
    this.ifcLoader.ifcManager.setWasmPath('../files/')
    this.createScene();

    const ifcApi = new WebIFC.IfcAPI();

    // initialize the library
    await ifcApi.Init();
    ifcWriter.writeIFC(ifcApi);
  },
}
</script>

<style scoped>
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
