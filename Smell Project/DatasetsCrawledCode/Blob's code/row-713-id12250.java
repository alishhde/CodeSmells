@XmlRootElement(name = "pagedResult")
@XmlType
public class PagedResult<T extends BaseBean> implements Serializable {


 private static final long serialVersionUID = 3472875885259250934L;


 private URI prev;


 private URI next;


 private final List<T> result = new ArrayList<>();


 private int page;


 private int size;


 private int totalCount;


 public URI getPrev() {
 return prev;
    }


 public void setPrev(final URI prev) {
 this.prev = prev;
    }


 public URI getNext() {
 return next;
    }


 public void setNext(final URI next) {
 this.next = next;
    }


 @XmlElementWrapper(name = "result")
 @XmlElement(name = "item")
 @JsonProperty("result")
 @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
 public List<T> getResult() {
 return result;
    }


 public int getPage() {
 return page;
    }


 public void setPage(final int page) {
 this.page = page;
    }


 public int getSize() {
 return size;
    }


 public void setSize(final int size) {
 this.size = size;
    }


 public int getTotalCount() {
 return totalCount;
    }


 public void setTotalCount(final int totalCount) {
 this.totalCount = totalCount;
    }


 @Override
 public int hashCode() {
 return new HashCodeBuilder().
 append(prev).
 append(next).
 append(result).
 append(page).
 append(size).
 append(totalCount).
 build();
    }


 @Override
 public boolean equals(final Object obj) {
 if (this == obj) {
 return true;
        }
 if (obj == null) {
 return false;
        }
 if (getClass() != obj.getClass()) {
 return false;
        }
 @SuppressWarnings("unchecked")
 final PagedResult<T> other = (PagedResult<T>) obj;
 return new EqualsBuilder().
 append(prev, other.prev).
 append(next, other.next).
 append(result, other.result).
 append(page, other.page).
 append(size, other.size).
 append(totalCount, other.totalCount).
 build();
    }
}