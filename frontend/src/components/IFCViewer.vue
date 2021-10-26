<template>
<div>
  <input
      type="file"
      name="load"
      id="file-input"
      @change="onIfcFileInputChange"
  />
  <canvas id="three-canvas"></canvas>
</div>
</template>

<script>
import { IFCLoader } from 'web-ifc-three/IFCLoader';
import { createScene } from '@/components/ifcScripts';

export default {
  name: "IFCViewer",
  data: () => ({
    ifcLoader: new IFCLoader(),
    scene: null,
  }),
  methods: {
    createScene() {
      this.scene = createScene();
    },
    onIfcFileInputChange(changed) {
      const [ file ] = changed.target.files;
      const ifcURL = URL.createObjectURL(file);
      this.ifcLoader.load(
          ifcURL,
          (ifcModel) => this.scene.add(ifcModel.mesh));
    },
  },
  mounted() {
    this.ifcLoader.ifcManager.setWasmPath('../files/')
    this.createScene();
  },
}
</script>

<style scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

html,
body {
  overflow: hidden;
}

#three-canvas {
  position: fixed;
  top: 0;
  left: 0;
  outline: none;
}

#file-input {
  z-index: 1;
  position: absolute;
}
</style>
