 @Element(endTag=false)
 public interface LINK extends Attrs, _Child {
 // $charset omitted
 /** URI for linked resource
     * @param uri the URI
     * @return the current element builder
     */
 LINK $href(String uri);


 /** language code
     * @param cdata the code
     * @return the current element builder
     */
 LINK $hreflang(String cdata);


 /** advisory content type
     * @param cdata the type
     * @return the current element builder
     */
 LINK $type(String cdata);


 /** forward link types
     * @param linkTypes the types
     * @return the current element builder
     */
 LINK $rel(EnumSet<LinkType> linkTypes);


 /**
     * forward link types.
     * @param linkTypes space-separated link types
     * @return the current element builder
     */
 LINK $rel(String linkTypes);


 // $rev omitted. Instead of rev="made", use rel="author"


 /** for rendering on these media
     * @param mediaTypes the media types
     * @return the current element builder
     */
 LINK $media(EnumSet<Media> mediaTypes);


 /**
     * for rendering on these media.
     * @param mediaTypes comma-separated list of media
     * @return the current element builder
     */
 LINK $media(String mediaTypes);
  }