 protected void buildContent( )
	{


 // Defines provider.


 IDescriptorProvider nameProvider = new TextPropertyDescriptorProvider( IDesignElementModel.NAME_PROP,
 ReportDesignConstants.VARIABLE_ELEMENT );


 // Defines section.


 TextSection nameSection = new TextSection( nameProvider.getDisplayName( ),
 container,
 true );


 nameSection.setProvider( nameProvider );
 nameSection.setLayoutNum( 6 );
 nameSection.setWidth( 500 );
 addSection( PageSectionId.VARIABLE_NAME, nameSection ); //$NON-NLS-1$


 ComboPropertyDescriptorProvider variableTypeProvider = new ComboPropertyDescriptorProvider( IVariableElementModel.TYPE_PROP,
 ReportDesignConstants.VARIABLE_ELEMENT );
 variableTypeProvider.enableReset( true );


 ComboSection variableTypeSection = new ComboSection( variableTypeProvider.getDisplayName( ),
 container,
 true );
 variableTypeSection.setProvider( variableTypeProvider );
 variableTypeSection.setLayoutNum( 6 );
 variableTypeSection.setWidth( 500 );
 addSection( PageSectionId.VARIABLE_TYPE, variableTypeSection );


 ExpressionPropertyDescriptorProvider variableValueProvider = new ExpressionPropertyDescriptorProvider( IVariableElementModel.VALUE_PROP,
 ReportDesignConstants.VARIABLE_ELEMENT );
 ExpressionSection variableValueSection = new ExpressionSection( variableValueProvider.getDisplayName( ),
 container,
 true );
 variableValueSection.setMulti(false);
 variableValueSection.setProvider( variableValueProvider );
 variableValueSection.setWidth( 500 );
 variableValueSection.setLayoutNum( 6 );
 addSection( PageSectionId.VARIABLE_VALUE, variableValueSection );


	}