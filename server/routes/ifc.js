const express = require("express");
const {ifcWriter} = require("../services/ifc/ifcWriter");

const router = express.Router();

/* GET home page. */
router.get('/', (req, res, next) => {
  res.send('Hello IFC!');
  const ifcString = ifcWriter.writeIFC();
  res.send(ifcString);
});

module.exports = router;
