 private ICompletionProposal[] getRelevantProposals( ITextViewer viewer,
 int offset ) throws BadLocationException
	{
 if ( lastProposals != null )
		{
 ArrayList relevantProposals = new ArrayList( 10 );


 String word = ( findWord( viewer, offset - 1 ) ).toLowerCase( );
 //Search for this word in the list


 for ( int n = 0; n < lastProposals.length; n++ )
			{
 if ( stripQuotes( lastProposals[n].getDisplayString( )
						.toLowerCase( ) ).startsWith( word ) )
				{
 CompletionProposal proposal = new CompletionProposal( lastProposals[n].getDisplayString( ),
 offset - word.length( ),
 word.length( ),
 lastProposals[n].getDisplayString( ).length( ) );
 relevantProposals.add( proposal );
				}
			}


 if ( relevantProposals.size( ) > 0 )
			{
 return (ICompletionProposal[]) relevantProposals.toArray( new ICompletionProposal[]{} );
			}
		}


 return null;
	}