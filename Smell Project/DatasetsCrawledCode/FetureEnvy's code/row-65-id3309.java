 @Override
 public MessageReference copy(final Queue queue) {
 return new MessageReferenceImpl(this, queue);
   }