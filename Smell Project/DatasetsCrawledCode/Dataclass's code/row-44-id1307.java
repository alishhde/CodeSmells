 private static class CountPageable implements Pageable {


 private Pageable delegate;


 public CountPageable(Pageable delegate) {
 this.delegate = delegate;
		}


 public int getPageNumber() {
 return delegate.getPageNumber();
		}


 public int getPageSize() {
 return delegate.getPageSize();
		}


 public long getOffset() {
 return delegate.getOffset();
		}


 public Sort getSort() {
 // Sorting is not allowed on aggregate count queries.
 return Sort.unsorted();
		}


 public Pageable next() {
 return delegate.next();
		}


 public Pageable previousOrFirst() {
 return delegate.previousOrFirst();
		}


 public Pageable first() {
 return delegate.first();
		}


 public boolean hasPrevious() {
 return delegate.hasPrevious();
		}


	}