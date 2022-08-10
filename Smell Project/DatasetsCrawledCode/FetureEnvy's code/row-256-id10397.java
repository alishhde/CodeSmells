 public static Dataset[] generateCoordinates(Dataset angles, final double[] geometricParameters) {
 if (geometricParameters.length != PARAMETERS)
 throw new IllegalArgumentException("Need " + PARAMETERS + " parameters");


 Dataset[] coords = new Dataset[2];


 DoubleDataset x = DatasetFactory.zeros(DoubleDataset.class, angles.getShape());
 DoubleDataset y = DatasetFactory.zeros(DoubleDataset.class, angles.getShape());
 coords[0] = x;
 coords[1] = y;


 final double ca = Math.cos(geometricParameters[2]);
 final double sa = Math.sin(geometricParameters[2]);
 final IndexIterator it = angles.getIterator();


 int i = 0;
 
 while (it.hasNext()) {
 final double t = angles.getElementDoubleAbs(it.index);
 final double ct = Math.cos(t);
 final double st = Math.sin(t);
 x.setAbs(i, geometricParameters[3] + geometricParameters[0]*ca*ct - geometricParameters[1]*sa*st);
 y.setAbs(i, geometricParameters[4] + geometricParameters[0]*sa*ct + geometricParameters[1]*ca*st);
 i++;
		}
 return coords;
	}