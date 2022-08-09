public class NodeValueString extends NodeValue
{
 // A plain string, with no language tag, or an xsd:string.
 
 private String string ; 
 
 public NodeValueString(String str)         { string = str ; } 
 public NodeValueString(String str, Node n) { super(n) ; string = str ; }
 
 @Override
 public boolean isString() { return true ; }
 
 @Override
 public String getString() { return string ; }


 @Override
 public String asString() { return string ; }
 
 @Override
 public String toString()
    { 
 if ( getNode() != null )
        {
 // Can be a plain string or an xsd:string.
 return FmtUtils.stringForNode(getNode()) ;
        }
 return '"'+string+'"'  ;
    }
 
 @Override
 protected Node makeNode()
    { return NodeFactory.createLiteral(string) ; }
 
 @Override
 public void visit(NodeValueVisitor visitor) { visitor.visit(this) ; }
}