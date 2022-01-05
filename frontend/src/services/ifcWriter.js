import * as WebIFC from 'web-ifc';

let EID = 1;

function real(v)
{
    return { type: 4, value: v}
}

function ref(v)
{
    return { type: 5, value: v}
}

function empty()
{
    return { type: 6}
}

function str(v)
{
    return { type: 1, value: v}
}

function enm(v)
{
    return { type: 3, value: v}
}

function Point(model, api, o)
{
    const ID = EID++;
    const pt = new WebIFC.IfcCartesianPoint(ID,
        WebIFC.IFCCARTESIANPOINT,
        [real(o.x), real(o.y), real(o.z)]);
    api.WriteLine(model, pt);
    return ref(ID);
}

function Dir(model, api, o)
{
    const ID = EID++;
    const pt = new WebIFC.IfcDirection(ID,
        WebIFC.IFCDIRECTION,
        [real(o.x), real(o.y), real(o.z)]);
    api.WriteLine(model, pt);
    return ref(ID);
}

function Point2D(model, api, o)
{
    const ID = EID++;
    const pt = new WebIFC.IfcCartesianPoint(ID,
        WebIFC.IFCCARTESIANPOINT,
        [real(o.x), real(o.y)]);
    api.WriteLine(model, pt);
    return ref(ID);
}

function AxisPlacement(model, api, o)
{
    const locationID = Point(model, api, o);
    const ID = EID++;
    const pt = new WebIFC.IfcAxis2Placement3D(ID,
        WebIFC.IFCAXIS2PLACEMENT3D,
        locationID,
        empty(),
        empty());
    api.WriteLine(model, pt);
    return ref(ID);
}

function AxisPlacement2D(model, api, o)
{
    const locationID = Point2D(model, api, o);
    const ID = EID++;
    const pt = new WebIFC.IfcAxis2Placement2D(ID,
        WebIFC.IFCAXIS2PLACEMENT2D,
        locationID,
        empty());
    api.WriteLine(model, pt);
    return ref(ID);
}

function Placement(model, api, o)
{
    const axisID = AxisPlacement(model, api, o);
    const ID = EID++;
    const pt = new WebIFC.IfcLocalPlacement(ID,
        WebIFC.IFCLOCALPLACEMENT,
        empty(),
        axisID);
    api.WriteLine(model, pt);
    return ref(ID);
}

function CircleProfile(model, api, rad, o)
{
    const ID = EID++;
    const pt = new WebIFC.IfcCircleProfileDef(ID,
        WebIFC.IFCCIRCLEPROFILEDEF,
        enm(WebIFC.IfcProfileTypeEnum.AREA),
        str('column-prefab'),
        AxisPlacement2D(model, api, o),
        real(rad));
    api.WriteLine(model, pt);
    return ref(ID);
}

function ExtrudedAreaSolid(model, api, pos, dir, rad, len)
{
    const ID = EID++;
    const pt = new WebIFC.IfcExtrudedAreaSolid(ID,
        WebIFC.IFCEXTRUDEDAREASOLID,
        CircleProfile(model, api, rad, { x: 0, y: 0 }),
        AxisPlacement(model, api, pos),
        Dir(model, api, dir),
        real(len));
    api.WriteLine(model, pt);
    return ref(ID);
}

function StandardColumn(model, api, pos)
{
    const shapeID = ExtrudedAreaSolid(model, api,
        { x: -2, y: 0, z: -1 },
        { x: 0, y: 0, z: 1 },
        0.25,
        2);

    const ID = EID++;
    const pt = new WebIFC.IfcColumn(ID,
        WebIFC.IFCCOLUMN,
        str("GUID"),
        empty(),
        str("name"),
        empty(),
        str("label"),
        Placement(model, api, pos),
        shapeID,
        str("sadf"),
        empty());
    api.WriteLine(model, pt);
    return ref(ID);
}

function BuildModel(model, api)
{
    console.log("Building model " + model);

    const gridSize = 6;

    for (let i = 0; i < gridSize; i++)
    {
        for (let j = 0; j < gridSize; j++)
        {
            StandardColumn(model, api, {x: i, y: j, z: 0});
        }
    }
}

function writeIFC(ifcAPI) {
    console.log('begin');
    // const ifcAPI = new WebIFC.IfcAPI();
    console.log('init');
    // ifcAPI.Init();
    console.log('setwasmpath');
    // ifcAPI.SetWasmPath('./wasm');

    console.log("Creating model...");

    const model = ifcAPI.CreateModel({
        COORDINATE_TO_ORIGIN: true,
        USE_FAST_BOOLS: true,
    });

    console.log("Building model...");
    BuildModel(model, ifcAPI);

    console.log(model)

    const ifcData = ifcAPI.ExportFileAsIFC(model);
    ifcAPI.CloseModel(model);
    const ifcDataString = new TextDecoder().decode(ifcData);

    console.log(ifcDataString);

    return ifcDataString;
}

export const ifcWriter = {
    writeIFC,
};
