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

        <h1>Demo BIM</h1>
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
            <v-form>
              <v-text-field
                  v-model="width"
                  label="Width"
                  type="number"
                  dark
                  required
              ></v-text-field>
              <v-text-field
                  v-model="length"
                  label="Length"
                  type="number"
                  dark
                  required
              ></v-text-field>
              <v-text-field
                  v-model="height"
                  label="Height"
                  type="number"
                  dark
                  required
              ></v-text-field>
            </v-form>
          </v-col>
          <v-col
              cols="12"
              md="9"
              style="background-color: #333333"
          >
            <IFCViewer />
          </v-col>
        </v-row>
      </v-container>
    </v-main>
  </v-app>
</template>

<script>
import IFCViewer from './components/IFCViewer.vue'

export default {
  name: 'App',
  components: {
    IFCViewer,
  },
  data: () => ({
    width: 300,
    length: 400,
    height: 50,
  }),
  methods: {
    onFormSubmit() {
      console.log('submitted');
    },
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
</style>
