 public void afterValue( K key, V value ) throws LdapException, CursorException
    {
 checkNotClosed();
 /*
         * There is a subtle difference between after and before handling
         * with duplicate key values.  Say we have the following tuples:
         *
         * (0, 0)
         * (1, 1)
         * (1, 2)
         * (1, 3)
         * (2, 2)
         *
         * If we request an after cursor on (1, 2).  We must make sure that
         * the container cursor does not advance after the entry with key 1
         * since this would result in us skip returning (1. 3) on the call to
         * next which will incorrectly return (2, 2) instead.
         *
         * So if the value is null in the element then we don't care about
         * this obviously since we just want to advance past the duplicate key
         * values all together.  But when it is not null, then we want to
         * go right before this key instead of after it.
         */


 if ( value == null )
        {
 containerCursor.after( new Tuple<K, DupsContainer<V>>( key, null ) );
        }
 else
        {
 containerCursor.before( new Tuple<K, DupsContainer<V>>( key, null ) );
        }


 if ( containerCursor.next() )
        {
 containerTuple.setBoth( containerCursor.get() );
 DupsContainer<V> values = containerTuple.getValue();


 if ( values.isArrayTree() )
            {
 ArrayTree<V> set = values.getArrayTree();
 dupsCursor = new ArrayTreeCursor<>( set );
            }
 else
            {
 try
                {
 BTree tree = table.getBTree( values.getBTreeRedirect() );
 dupsCursor = new KeyBTreeCursor<>( tree, table.getValueComparator() );
                }
 catch ( IOException e )
                {
 throw new CursorException( e );
                }
            }


 if ( value == null )
            {
 return;
            }


 // only advance the dupsCursor if we're on same key
 if ( table.getKeyComparator().compare( containerTuple.getKey(), key ) == 0 )
            {
 dupsCursor.after( value );
            }


 return;
        }


 clearValue();
 containerTuple.setKey( null );
 containerTuple.setValue( null );
    }