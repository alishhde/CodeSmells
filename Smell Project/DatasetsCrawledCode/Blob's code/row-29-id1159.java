 public static class Builder {
 /**
         * Copy method to populate the builder with values from the given instance.
         * @return this builder instance
         */
 public Builder copy(ListBootVolumeAttachmentsResponse o) {
 opcNextPage(o.getOpcNextPage());
 opcRequestId(o.getOpcRequestId());
 items(o.getItems());


 return this;
        }
    }