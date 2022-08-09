 private static class MultiPointList extends AbstractList<Point> {
 private final MultiPoint mp;


 public MultiPointList(MultiPoint mp) {
 this.mp = mp;
    }


 @Override
 public Point get(int index) {
 return mp.getPoint(index);
    }


 @Override
 public int size() {
 return mp.getPointCount();
    }
  }