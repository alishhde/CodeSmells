public class RealRowResultSetStatistics 
 extends RealNoPutResultSetStatistics
{


 /* Leave these fields public for object inspectors */
 public int rowsReturned;


 // CONSTRUCTORS
 /**
	 * 
	 *
	 */
 public RealRowResultSetStatistics(
 int numOpens,
 int rowsSeen,
 int rowsFiltered,
 long constructorTime,
 long openTime,
 long nextTime,
 long closeTime,
 int resultSetNumber,
 int rowsReturned,
 double optimizerEstimatedRowCount,
 double optimizerEstimatedCost
									)
	{
 super(
 numOpens,
 rowsSeen,
 rowsFiltered,
 constructorTime,
 openTime,
 nextTime,
 closeTime,
 resultSetNumber,
 optimizerEstimatedRowCount,
 optimizerEstimatedCost
			);
 this.rowsReturned = rowsReturned;
	}


 // ResultSetStatistics methods


 /**
	 * Return the statement execution plan as a String.
	 *
	 * @param depth	Indentation level.
	 *
	 * @return String	The statement execution plan as a String.
	 */
 public String getStatementExecutionPlanText(int depth)
	{
 initFormatInfo(depth);


 return
 indent + MessageService.getTextMessage(SQLState.RTS_ROW_RS) +
 ":\n" +
 indent + MessageService.getTextMessage(SQLState.RTS_NUM_OPENS) +
 " = " + numOpens + "\n" +
 indent + MessageService.getTextMessage(
 SQLState.RTS_ROWS_RETURNED) +
 " = " + rowsReturned + "\n" +
 dumpTimeStats(indent, subIndent) + "\n" +
 dumpEstimatedCosts(subIndent) + "\n";
	}


 /**
	 * Return information on the scan nodes from the statement execution 
	 * plan as a String.
	 *
	 * @param depth	Indentation level.
	 * @param tableName if not NULL then print information for this table only
	 *
	 * @return String	The information on the scan nodes from the 
	 *					statement execution plan as a String.
	 */
 public String getScanStatisticsText(String tableName, int depth)
	{
 return "";
	}






 // Class implementation
 
 public String toString()
	{
 return getStatementExecutionPlanText(0);
	}
 /**
   * Format for display, a name for this node.
	 *
	 */
 public String getNodeName(){
 return MessageService.getTextMessage(SQLState.RTS_ROW_RS);
  }
 
 // -----------------------------------------------------
 // XPLAINable Implementation
 // -----------------------------------------------------
 
 public void accept(XPLAINVisitor visitor) {
 
 // I have no children, inform my visitor about that
 visitor.setNumberOfChildren(0);
 // pre-order, depth-first traversal
 // me first
 visitor.visit(this);
 // I'm a leaf node, I have no children ...
 
    }


 public String getRSXplainType() { return XPLAINUtil.OP_ROW; }
 public Object getResultSetDescriptor(Object rsID, Object parentID,
 Object scanID, Object sortID, Object stmtID, Object timingID)
    {
 return new XPLAINResultSetDescriptor(
           (UUID)rsID,
 getRSXplainType(),
 getRSXplainDetails(),
 this.numOpens,
 null,                              // the number of index updates 
 null,                           // lock mode
 null,                           // lock granularity
           (UUID)parentID,
 this.optimizerEstimatedRowCount,
 this.optimizerEstimatedCost,
 null,                              // the affected rows
 null,                              // the deferred rows
 null,                              // the input rows
 this.rowsSeen,
 null,                              // the seen rows right
 this.rowsFiltered,
 this.rowsReturned,
 null,                              // the empty right rows
 null,                           // index key optimization
           (UUID)scanID,
           (UUID)sortID,
           (UUID)stmtID,
           (UUID)timingID);
    }
}