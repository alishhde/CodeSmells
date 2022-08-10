 @Override
 ValueNode preprocess(int numTables,
 FromList outerFromList,
 SubqueryList outerSubqueryList,
 PredicateList outerPredicateList) 
 throws StandardException
	{
 /* Only preprocess this node once.  We may get called multiple times
		 * due to tree transformations.
		 */
 if (preprocessed)
		{
 return this;
		}
 preprocessed = true;


 boolean flattenable;
 ValueNode topNode = this;


 final boolean haveOrderBy; // need to remember for flattening decision


 // Push the order by list down to the ResultSet
 if (orderByList != null) {
 haveOrderBy = true;
 // If we have more than 1 ORDERBY columns, we may be able to
 // remove duplicate columns, e.g., "ORDER BY 1, 1, 2".
 if (orderByList.size() > 1)
            {
 orderByList.removeDupColumns();
            }


 resultSet.pushOrderByList(orderByList);
 orderByList = null;
        } else {
 haveOrderBy = false;
        }


 resultSet = resultSet.preprocess(numTables, null, (FromList) null);


 if (leftOperand != null)
        {
 leftOperand = leftOperand.preprocess(numTables,
 outerFromList, outerSubqueryList, outerPredicateList);
        }


 // Eliminate any unnecessary DISTINCTs
 if (resultSet instanceof SelectNode)
		{
 if (((SelectNode) resultSet).hasDistinct())
			{
				((SelectNode) resultSet).clearDistinct();
 /* We need to remember to check for single unique value
				 * at execution time for expression subqueries.
				 */
 if  (subqueryType == EXPRESSION_SUBQUERY)
				{
 distinctExpression = true;
				}
			}
		}


 /* Lame transformation - For IN/ANY subqueries, if
		 * result set is guaranteed to return at most 1 row
		 * and it is not correlated
		 * then convert the subquery into the matching expression
		 * subquery type.  For example:
		 *	c1 in (select min(c1) from t2)
		 * becomes:
		 *	c1 = (select min(c1) from t2)
		 * (This actually showed up in an app that a potential customer
		 * was porting from SQL Server.)
		 * The transformed query can then be flattened if appropriate.
		 */
 if ((isIN() || isANY()) &&
 resultSet.returnsAtMostOneRow())
		{
 if (! hasCorrelatedCRs())
			{
 changeToCorrespondingExpressionType();
			}
		}


 /* NOTE: Flattening occurs before the pushing of
		 * the predicate, since the pushing will add a node 
		 * above the SubqueryNode.
		 */


 /* Values subquery is flattenable if:
		 *  o It is not under an OR.
         *  o It is not a subquery in a having clause (DERBY-3257)
		 *  o It is an expression subquery on the right side
		 *	  of a BinaryComparisonOperatorNode.
		 *  o Either a) it does not appear within a WHERE clause, or 
		 *           b) it appears within a WHERE clause but does not itself 
		 *              contain a WHERE clause with other subqueries in it. 
		 *          (DERBY-3301)
		 */
 flattenable = (resultSet instanceof RowResultSetNode) &&
 underTopAndNode && !havingSubquery &&
                      !haveOrderBy &&
 offset == null &&
 fetchFirst == null &&
					  !isWhereExistsAnyInWithWhereSubquery() &&
 parentComparisonOperator != null;


 if (flattenable)
		{
 /* If we got this far and we are an expression subquery
			 * then we want to set leftOperand to be the left side
			 * of the comparison in case we pull the comparison into
			 * the flattened subquery.
			 */
 leftOperand = parentComparisonOperator.getLeftOperand();
 // Flatten the subquery
 RowResultSetNode rrsn = (RowResultSetNode) resultSet;
 FromList fl = new FromList(getContextManager());


 // Remove ourselves from the outer subquery list
 outerSubqueryList.removeElement(this);


 /* We only need to add the table from the subquery into 
			 * the outer from list if the subquery itself contains
			 * another subquery.  Otherwise, it just becomes a constant.
			 */
 if (rrsn.subquerys.size() != 0)
			{
 fl.addElement(rrsn);
 outerFromList.destructiveAppend(fl);
			}


 /* Append the subquery's subquery list to the 
			 * outer subquery list.
			 */
 outerSubqueryList.destructiveAppend(rrsn.subquerys);


 /* return the new join condition 
			 * If we are flattening an EXISTS then there is no new join
			 * condition since there is no leftOperand.  Simply return
			 * TRUE.
			 *
			 * NOTE: The outer where clause, etc. has already been normalized,
			 * so we simply return the BinaryComparisonOperatorNode above
			 * the new join condition.
			 */
 return getNewJoinCondition(leftOperand, getRightOperand());
		}


 /* Select subquery is flattenable if:
		 *  o It is not under an OR.
		 *  o The subquery type is IN, ANY or EXISTS or
		 *    an expression subquery on the right side
		 *	  of a BinaryComparisonOperatorNode.
		 *  o There are no aggregates in the select list
		 *  o There is no group by clause or having clause.
		 *  o There is a uniqueness condition that ensures
		 *	  that the flattening of the subquery will not
		 *	  introduce duplicates into the result set.
         *  o The subquery is not part of a having clause (DERBY-3257)
		 *  o There are no windows defined on it
		 *
		 *	OR,
		 *  o The subquery is NOT EXISTS, NOT IN, ALL (beetle 5173).
		 *  o Either a) it does not appear within a WHERE clause, or 
		 *           b) it appears within a WHERE clause but does not itself 
		 *              contain a WHERE clause with other subqueries in it. 
		 *          (DERBY-3301)
		 */
 boolean flattenableNotExists = (isNOT_EXISTS() || canAllBeFlattened());


 flattenable = (resultSet instanceof SelectNode) &&
 			          !((SelectNode)resultSet).hasWindows() &&
                      !haveOrderBy &&
 offset == null &&
 fetchFirst == null &&
 underTopAndNode && !havingSubquery &&
					  !isWhereExistsAnyInWithWhereSubquery() &&
					  (isIN() || isANY() || isEXISTS() || flattenableNotExists ||
 parentComparisonOperator != null);


 if (flattenable)
		{
 SelectNode select = (SelectNode) resultSet;
 if ((!select.hasAggregatesInSelectList()) &&
			    (select.havingClause == null))
			{
 ValueNode origLeftOperand = leftOperand;


 /* Check for uniqueness condition. */
 /* Is the column being returned by the subquery
				 * a candidate for an = condition?
				 */
 boolean additionalEQ =
							(subqueryType == IN_SUBQUERY) ||
							(subqueryType == EQ_ANY_SUBQUERY);




 additionalEQ = additionalEQ &&
								((leftOperand instanceof ConstantNode) ||
								 (leftOperand instanceof ColumnReference) ||
								 (leftOperand.requiresTypeFromContext()));
 /* If we got this far and we are an expression subquery
				 * then we want to set leftOperand to be the left side
				 * of the comparison in case we pull the comparison into
				 * the flattened subquery.
				 */
 if (parentComparisonOperator != null)
				{
 leftOperand = parentComparisonOperator.getLeftOperand();
				}
 /* Never flatten to normal join for NOT EXISTS.
				 */


 if ((! flattenableNotExists) && select.uniqueSubquery(additionalEQ))
				{
 // Flatten the subquery
 return flattenToNormalJoin(numTables,
 outerFromList, outerSubqueryList,
 outerPredicateList);
				}
 /* We can flatten into an EXISTS join if all of the above
				 * conditions except for a uniqueness condition are true
				 * and:
				 *	o Subquery only has a single entry in its from list
				 *	  and that entry is a FromBaseTable
				 *	o All predicates in the subquery's where clause are
				 *	  pushable.
				 *  o The leftOperand, if non-null, is pushable.
				 * If the subquery meets these conditions then we will flatten
				 * the FBT into an EXISTS FBT, pushd the subquery's
				 * predicates down to the PRN above the EBT and
				 * mark the predicates to say that they cannot be pulled 
				 * above the PRN. (The only way that we can guarantee correctness
				 * is if the predicates do not get pulled up.  If they get pulled
				 * up then the single next logic for an EXISTS join does not work
				 * because that row may get disqualified at a higher level.)
                 * DERBY-4001: Extra conditions to allow flattening to a NOT
                 * EXISTS join (in a NOT EXISTS join it does matter on which
                 * side of the join predicates/restrictions are applied):
                 *  o All the predicates must reference the FBT, otherwise
                 *    predicates meant for the right side of the join may be
                 *    applied to the left side of the join.
                 *  o The right operand (in ALL and NOT IN) must reference the
                 *    FBT, otherwise the generated join condition may be used
                 *    to restrict the left side of the join.
				 */
 else if ( (isIN() || isANY() || isEXISTS() || flattenableNotExists) &&
						  ((leftOperand == null) ? true :
 leftOperand.categorize(new JBitSet(numTables), false)) &&
 select.getWherePredicates().allPushable())
				{
 FromBaseTable fbt =
 singleFromBaseTable(select.getFromList());


 if (fbt != null && (!flattenableNotExists ||
                         (select.getWherePredicates().allReference(fbt) &&
 rightOperandFlattenableToNotExists(numTables, fbt))))
                    {
 return flattenToExistsJoin(numTables,
 outerFromList, outerSubqueryList,
 outerPredicateList, flattenableNotExists);
                    }
				}


 // restore leftOperand to its original value
 leftOperand = origLeftOperand;
			}
		}


 resultSet.pushQueryExpressionSuffix();


 resultSet.pushOffsetFetchFirst( offset, fetchFirst, hasJDBClimitClause );


 /* We transform the leftOperand and the select list for quantified 
		 * predicates that have a leftOperand into a new predicate and push it
		 * down to the subquery after we preprocess the subquery's resultSet.
		 * We must do this after preprocessing the underlying subquery so that
		 * we know where to attach the new predicate.
		 * NOTE - If we pushed the predicate before preprocessing the underlying
		 * subquery, then the point of attachment would depend on the form of
		 * that subquery.  (Where clause?  Having clause?)
		 */
 if (leftOperand != null)
		{
 topNode = pushNewPredicate(numTables);
 pushedNewPredicate = true;
		}
 /* EXISTS and NOT EXISTS subqueries that haven't been flattened, need
         * an IS [NOT] NULL node on top so that they return a BOOLEAN. Other
         * cases are taken care of in pushNewPredicate.
		 */
 else if (isEXISTS() || isNOT_EXISTS())
		{
 topNode = genIsNullTree(isEXISTS());
 subqueryType = EXISTS_SUBQUERY;
		}


 /*
		** Do inVariant and correlated checks now.  We
		** aren't going to use the results here, but they
		** have been stashed away by isInvariant() and hasCorrelatedCRs()
		*/
 isInvariant();
 hasCorrelatedCRs();


 /* If parentComparisonOperator is non-null then we are an
		 * expression subquery that was considered to be a candidate 
		 * for flattening, but we didn't get flattened.  In that case
		 * we are the rightOperand of the parent.  We need to update
		 * the parent's rightOperand with the new topNode and return
		 * the parent because the parent is letting us decide whether
		 * or not to replace the entire comparison, which we can do
		 * if we flatten.  Otherwise we simply return the new top node.
		 */
 if (parentComparisonOperator != null)
		{
 parentComparisonOperator.setRightOperand(topNode);
 return parentComparisonOperator;
		}


 return topNode;
	}