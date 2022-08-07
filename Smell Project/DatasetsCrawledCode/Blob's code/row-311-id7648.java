public interface IContentEmitter
{


 String getOutputFormat( );


 void initialize( IEmitterServices service ) throws BirtException;


 void start( IReportContent report ) throws BirtException;


 void end( IReportContent report ) throws BirtException;


 /**
	 * start a page
	 * 
	 * @param page
	 */
 void startPage( IPageContent page ) throws BirtException;
 
 /**
	 * page end
	 * 
	 * @param page
	 */
 void endPage( IPageContent page ) throws BirtException;


 /**
	 * table started
	 * 
	 * @param table
	 */
 void startTable( ITableContent table ) throws BirtException;


 /**
	 * table end
	 */
 void endTable( ITableContent table ) throws BirtException;


 void startTableBand( ITableBandContent band ) throws BirtException;


 void endTableBand( ITableBandContent band ) throws BirtException;


 void startRow( IRowContent row ) throws BirtException;


 void endRow( IRowContent row ) throws BirtException;


 void startCell( ICellContent cell ) throws BirtException;


 void endCell( ICellContent cell ) throws BirtException;
 
 void startList( IListContent list ) throws BirtException;


 void endList( IListContent list ) throws BirtException;


 void startListBand( IListBandContent listBand ) throws BirtException;


 void endListBand( IListBandContent listBand ) throws BirtException;


 void startContainer( IContainerContent container ) throws BirtException;


 void endContainer( IContainerContent container ) throws BirtException;


 void startText( ITextContent text ) throws BirtException;


 void startData( IDataContent data ) throws BirtException;


 void startLabel( ILabelContent label ) throws BirtException;
 
 void startAutoText ( IAutoTextContent autoText ) throws BirtException;


 void startForeign( IForeignContent foreign ) throws BirtException;


 void startImage( IImageContent image ) throws BirtException;


 void startContent( IContent content ) throws BirtException;
 void endContent( IContent content) throws BirtException;
 
 void startGroup( IGroupContent group ) throws BirtException;


 void endGroup( IGroupContent group ) throws BirtException;


 void startTableGroup( ITableGroupContent group ) throws BirtException;


 void endTableGroup( ITableGroupContent group ) throws BirtException;


 void startListGroup( IListGroupContent group ) throws BirtException;


 void endListGroup( IListGroupContent group ) throws BirtException;
}