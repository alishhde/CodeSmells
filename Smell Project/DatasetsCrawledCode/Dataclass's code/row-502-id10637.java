public class UpdateEntityResponse<V extends RecordTemplate> extends UpdateResponse
{
 private final V _entity;


 public UpdateEntityResponse(final HttpStatus status, final V entity)
  {
 super(status);
 _entity = entity;
  }


 public boolean hasEntity()
  {
 return _entity != null;
  }


 public V getEntity()
  {
 return _entity;
  }
}