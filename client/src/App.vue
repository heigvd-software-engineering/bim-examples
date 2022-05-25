<script setup lang="ts">
import { IFCLoader } from "web-ifc-three/IFCLoader";
import type { Scene } from "three";
import { onMounted, ref } from "vue";
import { createScene } from "@/services/ifcScripts.js";

const ifcLoader = new IFCLoader();
let scene: Scene;
let ifcModel = -1;
const wasmPath = "../files/";

const scene3d = ref(null);

ifcLoader.ifcManager.setWasmPath(wasmPath);

const renderScene = () => {
  scene = createScene(scene3d.value);
};

const loadIfcModel = (newIfcModel) => {
  if (newIfcModel.modelID > -1) {
    scene.remove(newIfcModel);
  }
  scene.add(newIfcModel);
  ifcModel = newIfcModel;
};

const onIfcFileInputChange = (changed) => {
  const [file] = changed.target.files;
  const ifcURL = URL.createObjectURL(file);
  ifcLoader.load(ifcURL, loadIfcModel);
};

onMounted(() => {
  renderScene();
});
</script>

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

<style>
@import "@/assets/base.css";

#app {
  max-width: 1280px;
  margin: 0 auto;
  padding: 2rem;

  font-weight: normal;
}

header {
  line-height: 1.5;
  max-height: 100vh;
}

.logo {
  display: block;
  margin: 0 auto 2rem;
}

a,
.green {
  text-decoration: none;
  color: hsla(160, 100%, 37%, 1);
  transition: 0.4s;
}

@media (hover: hover) {
  a:hover {
    background-color: hsla(160, 100%, 37%, 0.2);
  }
}

nav {
  width: 100%;
  font-size: 12px;
  text-align: center;
  margin-top: 2rem;
}

nav a.router-link-exact-active {
  color: var(--color-text);
}

nav a.router-link-exact-active:hover {
  background-color: transparent;
}

nav a {
  display: inline-block;
  padding: 0 1rem;
  border-left: 1px solid var(--color-border);
}

nav a:first-of-type {
  border: 0;
}

@media (min-width: 1024px) {
  body {
    display: flex;
    place-items: center;
  }

  #app {
    display: grid;
    grid-template-columns: 1fr 1fr;
    padding: 0 2rem;
  }

  header {
    display: flex;
    place-items: center;
    padding-right: calc(var(--section-gap) / 2);
  }

  header .wrapper {
    display: flex;
    place-items: flex-start;
    flex-wrap: wrap;
  }

  .logo {
    margin: 0 2rem 0 0;
  }

  nav {
    text-align: left;
    margin-left: -1rem;
    font-size: 1rem;

    padding: 1rem 0;
    margin-top: 1rem;
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
}
</style>
