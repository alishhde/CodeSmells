 NormalizeResultSetNode(ResultSetNode chldRes,
 ResultColumnList targetResultColumnList,
 Properties tableProperties,
 boolean forUpdate,
 ContextManager cm) throws StandardException
	{
 super(chldRes, tableProperties, cm);
 this.forUpdate = forUpdate;


 ResultColumnList rcl = chldRes.getResultColumns();
 ResultColumnList targetRCL = targetResultColumnList;
 
 /* We get a shallow copy of the ResultColumnList and its 
		 * ResultColumns.  (Copy maintains ResultColumn.expression for now.)
		 * 
		 * Setting this.resultColumns to the modified child result column list,
		 * and making a new copy for the child result set node
		 * ensures that the ProjectRestrictNode restrictions still points to 
		 * the same list.  See d3494_npe_writeup-4.html in DERBY-3494 for a
		 * detailed explanation of how this works.
		 */
 ResultColumnList prRCList = rcl;
 chldRes.setResultColumns(rcl.copyListAndObjects());
 // Remove any columns that were generated.
 prRCList.removeGeneratedGroupingColumns();
 // And also columns that were added for ORDER BY (DERBY-6006).
 prRCList.removeOrderByColumns();


 /* Replace ResultColumn.expression with new VirtualColumnNodes
		 * in the NormalizeResultSetNode's ResultColumnList.  (VirtualColumnNodes include
		 * pointers to source ResultSetNode, rsn, and source ResultColumn.)
		 */
 prRCList.genVirtualColumnNodes(chldRes, chldRes.getResultColumns());
 
 setResultColumns( prRCList );
 // Propagate the referenced table map if it's already been created
 if (chldRes.getReferencedTableMap() != null)
		    {
 setReferencedTableMap((JBitSet) getReferencedTableMap().clone());
		    }
 
 
 if (targetResultColumnList != null) {
 int size = Math.min(targetRCL.size(), getResultColumns().size());


 for (int index = 0; index < size; index++) {
 ResultColumn sourceRC = getResultColumns().elementAt(index);
 ResultColumn resultColumn = targetRCL.elementAt(index);
 sourceRC.setType(resultColumn.getTypeServices());
		    }
		}
	}