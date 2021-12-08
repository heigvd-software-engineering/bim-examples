import express from "express";
import { ifcWriter } from "../services/ifc/ifcWriter.js";

const router = express.Router();

/* GET home page. */
router.get('/', (req, res, next) => {
  res.send('Hello IFC!');
  const ifcString = ifcWriter.writeIFC();
  res.send(ifcString);
});

export default router;
