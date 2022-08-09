@Value
@Wither(AccessLevel.PACKAGE)
class CollectionJson<T> {


 private String version;
 private @Nullable String href;


 private @JsonInclude(Include.NON_EMPTY) Links links;
 private @JsonInclude(Include.NON_EMPTY) List<CollectionJsonItem<T>> items;
 private @JsonInclude(Include.NON_EMPTY) List<CollectionJsonQuery> queries;
 private @JsonInclude(Include.NON_NULL) @Nullable CollectionJsonTemplate template;
 private @JsonInclude(Include.NON_NULL) @Nullable CollectionJsonError error;


 @JsonCreator
 CollectionJson(@JsonProperty("version") String version, //
 @JsonProperty("href") @Nullable String href, //
 @JsonProperty("links") @Nullable Links links, //
 @JsonProperty("items") @Nullable List<CollectionJsonItem<T>> items, //
 @JsonProperty("queries") @Nullable List<CollectionJsonQuery> queries, //
 @JsonProperty("template") @Nullable CollectionJsonTemplate template, //
 @JsonProperty("error") @Nullable CollectionJsonError error) {


 this.version = version;
 this.href = href;
 this.links = links == null ? Links.NONE : links;
 this.items = items == null ? Collections.emptyList() : items;
 this.queries = queries == null ? Collections.emptyList() : queries;
 this.template = template;
 this.error = error;
	}


 CollectionJson() {
 this("1.0", null, Links.NONE, Collections.emptyList(), null, null, null);
	}


 @SafeVarargs
 final CollectionJson<T> withItems(CollectionJsonItem<T>... items) {
 return withItems(Arrays.asList(items));
	}


 CollectionJson<T> withItems(List<CollectionJsonItem<T>> items) {
 return new CollectionJson<>(version, href, links, items, queries, template, error);
	}


 CollectionJson<T> withLinks(Link... links) {
 return withLinks(Links.of(links));
	}


 CollectionJson<T> withLinks(Links links) {
 return new CollectionJson<>(version, href, links, items, queries, template, error);
	}


 CollectionJson<T> withOwnSelfLink() {


 String href = this.href;


 if (href == null) {
 return this;
		}


 return withLinks(Links.of(new Link(href)).merge(MergeMode.SKIP_BY_REL, links));
	}


 boolean hasItems() {
 return !items.isEmpty();
	}
}