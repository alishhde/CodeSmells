 @SuppressWarnings( "raw" )
 private static void simpleGenericNameOf( StringBuilder sb, Type type )
    {
 if( type instanceof Class )
        {
 sb.append( ( (Class) type ).getSimpleName() );
        }
 else if( type instanceof ParameterizedType )
        {
 ParameterizedType pt = (ParameterizedType) type;
 simpleGenericNameOf( sb, pt.getRawType() );
 sb.append( "<" );
 boolean atLeastOne = false;
 for( Type typeArgument : pt.getActualTypeArguments() )
            {
 if( atLeastOne )
                {
 sb.append( ", " );
                }
 simpleGenericNameOf( sb, typeArgument );
 atLeastOne = true;
            }
 sb.append( ">" );
        }
 else if( type instanceof GenericArrayType )
        {
 GenericArrayType gat = (GenericArrayType) type;
 simpleGenericNameOf( sb, gat.getGenericComponentType() );
 sb.append( "[]" );
        }
 else if( type instanceof TypeVariable )
        {
 TypeVariable tv = (TypeVariable) type;
 sb.append( tv.getName() );
        }
 else if( type instanceof WildcardType )
        {
 WildcardType wt = (WildcardType) type;
 sb.append( "? extends " );
 boolean atLeastOne = false;
 for( Type typeArgument : wt.getUpperBounds() )
            {
 if( atLeastOne )
                {
 sb.append( ", " );
                }
 simpleGenericNameOf( sb, typeArgument );
 atLeastOne = true;
            }
        }
 else
        {
 throw new IllegalArgumentException( "Don't know how to deal with type:" + type );
        }
    }