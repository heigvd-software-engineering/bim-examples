# BIMDEMO

This project is a proof of concept for a data to visualization for the building domain.

We start from simple data such as:

- width
- height
- length

From this data, we want to generate a very simple IFC structure (such as a slab) that we can then give to an IFC viewer.

To recap the targetted workflow:

data ==> IFC string ==> give string to IFC viewer.

## Folder structure

Quick explanation of the folder content.

- frontend folder: Vue app containing the 3D viewer and a client side writer attempt.
- server folder: attempt to have an IFC writer on the server side. Currently on pause due to a bug in the `web-ifc` library.

## Current state

Trying to log a simple IFC structure created from scratch with IFCjs in the frontend.

## How to run it

From this folder, run the following commands:

```shell
cd frontend
npm install
npm run preserve
npm run serve
```

The frontend should now run on `localhost:8080`.
