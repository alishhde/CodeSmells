 @Override
 public int setPath(Path2D path) {
 Rectangle2D bounds = path.getBounds2D();
 PathIterator it = path.getPathIterator(null);


 List<byte[]> segInfo = new ArrayList<>();
 List<Point2D.Double> pntInfo = new ArrayList<>();
 boolean isClosed = false;
 int numPoints = 0;
 while (!it.isDone()) {
 double[] vals = new double[6];
 int type = it.currentSegment(vals);
 switch (type) {
 case PathIterator.SEG_MOVETO:
 pntInfo.add(new Point2D.Double(vals[0], vals[1]));
 segInfo.add(SEGMENTINFO_MOVETO);
 numPoints++;
 break;
 case PathIterator.SEG_LINETO:
 pntInfo.add(new Point2D.Double(vals[0], vals[1]));
 segInfo.add(SEGMENTINFO_LINETO);
 segInfo.add(SEGMENTINFO_ESCAPE);
 numPoints++;
 break;
 case PathIterator.SEG_CUBICTO:
 pntInfo.add(new Point2D.Double(vals[0], vals[1]));
 pntInfo.add(new Point2D.Double(vals[2], vals[3]));
 pntInfo.add(new Point2D.Double(vals[4], vals[5]));
 segInfo.add(SEGMENTINFO_CUBICTO);
 segInfo.add(SEGMENTINFO_ESCAPE2);
 numPoints++;
 break;
 case PathIterator.SEG_QUADTO:
 //TODO: figure out how to convert SEG_QUADTO into SEG_CUBICTO
 LOG.log(POILogger.WARN, "SEG_QUADTO is not supported");
 break;
 case PathIterator.SEG_CLOSE:
 pntInfo.add(pntInfo.get(0));
 segInfo.add(SEGMENTINFO_LINETO);
 segInfo.add(SEGMENTINFO_ESCAPE);
 segInfo.add(SEGMENTINFO_LINETO);
 segInfo.add(SEGMENTINFO_CLOSE);
 isClosed = true;
 numPoints++;
 break;
 default:
 LOG.log(POILogger.WARN, "Ignoring invalid segment type "+type);
 break;
            }


 it.next();
        }
 if(!isClosed) {
 segInfo.add(SEGMENTINFO_LINETO);
        }
 segInfo.add(SEGMENTINFO_END);


 AbstractEscherOptRecord opt = getEscherOptRecord();
 opt.addEscherProperty(new EscherSimpleProperty(EscherProperties.GEOMETRY__SHAPEPATH, 0x4));


 EscherArrayProperty verticesProp = new EscherArrayProperty((short)(EscherProperties.GEOMETRY__VERTICES + 0x4000), false, null);
 verticesProp.setNumberOfElementsInArray(pntInfo.size());
 verticesProp.setNumberOfElementsInMemory(pntInfo.size());
 verticesProp.setSizeOfElements(8);
 for (int i = 0; i < pntInfo.size(); i++) {
 Point2D.Double pnt = pntInfo.get(i);
 byte[] data = new byte[8];
 LittleEndian.putInt(data, 0, Units.pointsToMaster(pnt.getX() - bounds.getX()));
 LittleEndian.putInt(data, 4, Units.pointsToMaster(pnt.getY() - bounds.getY()));
 verticesProp.setElement(i, data);
        }
 opt.addEscherProperty(verticesProp);


 EscherArrayProperty segmentsProp = new EscherArrayProperty((short)(EscherProperties.GEOMETRY__SEGMENTINFO + 0x4000), false, null);
 segmentsProp.setNumberOfElementsInArray(segInfo.size());
 segmentsProp.setNumberOfElementsInMemory(segInfo.size());
 segmentsProp.setSizeOfElements(0x2);
 for (int i = 0; i < segInfo.size(); i++) {
 byte[] seg = segInfo.get(i);
 segmentsProp.setElement(i, seg);
        }
 opt.addEscherProperty(segmentsProp);


 opt.addEscherProperty(new EscherSimpleProperty(EscherProperties.GEOMETRY__RIGHT, Units.pointsToMaster(bounds.getWidth())));
 opt.addEscherProperty(new EscherSimpleProperty(EscherProperties.GEOMETRY__BOTTOM, Units.pointsToMaster(bounds.getHeight())));


 opt.sortProperties();


 setAnchor(bounds);


 return numPoints;
    }