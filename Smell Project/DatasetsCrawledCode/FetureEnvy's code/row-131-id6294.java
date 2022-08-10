 @Override
 public int hashCode()
    {
 int hash = 37;


 if ( baseDn != null )
        {
 hash = hash * 17 + baseDn.hashCode();
        }


 hash = hash * 17 + aliasDerefMode.hashCode();
 hash = hash * 17 + scope.hashCode();
 hash = hash * 17 + Long.valueOf( sizeLimit ).hashCode();
 hash = hash * 17 + timeLimit;
 hash = hash * 17 + ( typesOnly ? 0 : 1 );


 if ( attributes != null )
        {
 hash = hash * 17 + attributes.size();


 // Order doesn't matter, thus just add hashCode
 for ( String attr : attributes )
            {
 if ( attr != null )
                {
 hash = hash + attr.hashCode();
                }
            }
        }


 BranchNormalizedVisitor visitor = new BranchNormalizedVisitor();
 filterNode.accept( visitor );
 hash = hash * 17 + filterNode.toString().hashCode();
 hash = hash * 17 + super.hashCode();


 return hash;
    }