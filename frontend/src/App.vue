<template>
  <v-app>
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
      <v-form>
        <v-container>
          <v-row>
            <v-col
                cols="12"
                md="4"
            >
              <v-text-field
                  v-model="width"
                  label="Width"
                  type="number"
                  required
              ></v-text-field>
            </v-col>

            <v-col
                cols="12"
                md="4"
            >
              <v-text-field
                  v-model="length"
                  label="Length"
                  type="number"
                  required
              ></v-text-field>
            </v-col>

            <v-col
                cols="12"
                md="4"
            >
              <v-text-field
                  v-model="height"
                  label="Height"
                  type="number"
                  required
              ></v-text-field>
            </v-col>
          </v-row>
        </v-container>
      </v-form>
      <IFCViewer />
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
