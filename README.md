# bim-examples

This project is a proof of concept.

It's goal is to have an application to visualize IFC files in a 3D viewer and upload them to a database.

## Folder structure

You can find the following folders in this repository's root folder:

- client: a Vue 3 + Typescrypt example of a webapp that can show an IFC file 3D model in a 3D scene. It's not as complete as "frontend" for development time reasons. It was created to have a basis for a Vue 3 project.
- frontend: Vue app allowing a user to visualize IFC files in a 3D view and upload them to a database.
- server: Simple API to store and access IFC files.

## How to run it

From this folder, run the following commands:

```shell
# Run database container
cd docker/database
docker-compose up -d

# Run API server
cd ../../server
mvn clean install
quarkus dev

# Run web app
cd ../frontend
npm install
npm run serve
```

You should be able to access the frontend at `localhost:8081`.
