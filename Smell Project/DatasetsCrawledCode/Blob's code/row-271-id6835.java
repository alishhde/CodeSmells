 private static class ConvertToBinaryBlockFunction implements PairFlatMapFunction<Iterator<Tuple2<Double,Long>>,MatrixIndexes,MatrixBlock> 
	{
 private static final long serialVersionUID = 5000298196472931653L;
 
 private long _rlen = -1;
 private int _brlen = -1;
 
 public ConvertToBinaryBlockFunction(long rlen, int brlen)
		{
 _rlen = rlen;
 _brlen = brlen;
		}
 
 public Iterator<Tuple2<MatrixIndexes, MatrixBlock>> call(Iterator<Tuple2<Double,Long>> arg0) 
 throws Exception 
		{
 ArrayList<Tuple2<MatrixIndexes,MatrixBlock>> ret = new ArrayList<>();
 MatrixIndexes ix = null;
 MatrixBlock mb = null;
 
 while( arg0.hasNext() ) 
			{
 Tuple2<Double,Long> val = arg0.next();
 long valix = val._2 + 1;
 long rix = UtilFunctions.computeBlockIndex(valix, _brlen);
 int pos = UtilFunctions.computeCellInBlock(valix, _brlen);
 
 if( ix == null || ix.getRowIndex() != rix )
				{
 if( ix !=null )
 ret.add(new Tuple2<>(ix,mb));
 long len = UtilFunctions.computeBlockSize(_rlen, rix, _brlen);
 ix = new MatrixIndexes(rix,1);
 mb = new MatrixBlock((int)len, 1, false);	
				}
 
 mb.quickSetValue(pos, 0, val._1);
			}
 
 //flush last block
 if( mb!=null && mb.getNonZeros() != 0 )
 ret.add(new Tuple2<>(ix,mb));
 return ret.iterator();
		}
	}