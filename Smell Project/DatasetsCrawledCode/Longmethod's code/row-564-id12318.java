 public int next()
    {
 final int startNode = _startNode;
 if (_startNode == NULL) {
 return NULL;
      }


 int node = _currentNode;


 int expType;
 final int nodeType = _nodeType;


 if (nodeType != DTM.ELEMENT_NODE)
      {
 do
        {
 node++;
 expType = _exptype2(node);


 if (NULL == expType || _parent2(node) < startNode && startNode != node) {
 _currentNode = NULL;
 return END;
          }
        }
 while (expType != nodeType);
      }
 // %OPT% If the start node is root (e.g. in the case of //node),
 // we can save the isDescendant() check, because all nodes are
 // descendants of root.
 else if (startNode == DTMDefaultBase.ROOTNODE)
      {
 do
	{
 node++;
 expType = _exptype2(node);


 if (NULL == expType) {
 _currentNode = NULL;
 return END;
	  }
	} while (expType < DTM.NTYPES
	        || m_extendedTypes[expType].getNodeType() != DTM.ELEMENT_NODE);
      }
 else
      {
 do
        {
 node++;
 expType = _exptype2(node);


 if (NULL == expType || _parent2(node) < startNode && startNode != node) {
 _currentNode = NULL;
 return END;
          }
        }
 while (expType < DTM.NTYPES
	       || m_extendedTypes[expType].getNodeType() != DTM.ELEMENT_NODE);
      }


 _currentNode = node;
 return returnNode(makeNodeHandle(node));
    }