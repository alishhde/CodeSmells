public class ChartReportItemHelper
{
 private static ChartReportItemHelper instance = new ChartReportItemHelper( );


 protected ChartReportItemHelper( )
	{


	}


 public static void initInstance( ChartReportItemHelper newInstance )
	{
 instance = newInstance;
	}


 public static ChartReportItemHelper instance( )
	{
 return instance;
	}
 
 public CubeHandle getBindingCubeHandle( ReportItemHandle itemHandle )
	{
 return ChartCubeUtil.getBindingCube( itemHandle );
	}
 
 public DataSetHandle getBindingDataSetHandle(ReportItemHandle itemHandle )
	{
 return ChartCubeUtil.getBindingDataSet( itemHandle );
	}
 
 public boolean checkCubeBindings( ExtendedItemHandle handle, Iterator<ComputedColumnHandle> columnBindings )
	{
 return ChartCubeUtil.checkColumnbindingForCube( columnBindings );
	}
 
 public ChartExpressionUtil.ExpressionCodec createExpressionCodec(
 ExtendedItemHandle handle )
	{
 return ChartModelHelper.instance( ).createExpressionCodec( );
	}
 
 public boolean loadExpression( ExpressionCodec exprCodec,
 ComputedColumnHandle cch )
	{
 return ChartItemUtil.loadExpression( exprCodec, cch );
	}
 
 public ComputedColumnHandle findDimensionBinding(
 ExpressionCodec exprCodec, String dimName, String levelName,
 Collection<ComputedColumnHandle> bindings, ReportItemHandle itemHandle )
	{
 for ( ComputedColumnHandle cch : bindings )
		{
 ChartReportItemHelper.instance( ).loadExpression( exprCodec, cch );
 String[] levelNames = exprCodec.getLevelNames( );
 if ( levelNames != null
					&& levelNames[0].equals( dimName )
					&& levelNames[1].equals( levelName ) )
			{
 return cch;
			}
		}
 return null;
	}
 
 /**
	 * Returns all bindings used by chart.
	 * 
	 * @param cm
	 * @param handle
	 * @param validExtensionNames
	 * @return all bindings used by chart.
	 */
 public Iterator<?> getAllUsedBindings( Chart cm, ReportItemHandle handle, List<String> validExtensionNames )
	{
 return handle.columnBindingsIterator( );
	}
 
 /**
	 * Returns all bindings used by chart.
	 * 
	 * @param cm
	 * @param handle
	 * @return all bindings used by chart.
	 */
 public Iterator<?> getAllUsedBindings( Chart cm, ReportItemHandle handle )
	{
 return handle.columnBindingsIterator( );
	}


 public String getMeasureExprIndicator( CubeHandle cubeHandle )
	{
 return ExpressionUtil.MEASURE_INDICATOR;
	}
 
 public List<String> getLevelBindingNamesOfCrosstab(
 CrosstabViewHandle viewHandle, ReportItemHandle chartHandle )
	{
 ArrayList<String> names = new ArrayList<String>( );
 for ( int i = 0; i < viewHandle.getDimensionCount( ); i++ )
		{
 DimensionViewHandle dimensionHandle = viewHandle.getDimension( i );
 dimensionHandle.availableBindings( );
 for ( int k = 0; k < dimensionHandle.getLevelCount( ); k++ )
			{
 names.add( dimensionHandle.getLevel( k )
						.getCubeLevel( )
						.getName( ) );
			}
		}


 return names;
	}
}