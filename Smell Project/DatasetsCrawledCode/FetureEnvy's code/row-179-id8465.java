 public ResultSet getUpdateVTIResultSet(NoPutResultSet source)
 throws StandardException
	{
 Activation activation = source.getActivation();
 getAuthorizer(activation).authorize(activation, Authorizer.SQL_WRITE_OP);
 return new UpdateVTIResultSet(source, activation);
	}