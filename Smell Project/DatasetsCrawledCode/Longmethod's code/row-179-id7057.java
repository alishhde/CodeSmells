 @Override
 protected GraphicsNode createImageGraphicsNode(
 BridgeContext ctx, Element imageElement, ParsedURL purl) {
 AbstractFOPBridgeContext bridgeCtx = (AbstractFOPBridgeContext)ctx;


 ImageManager manager = bridgeCtx.getImageManager();
 ImageSessionContext sessionContext = bridgeCtx.getImageSessionContext();
 try {
 ImageInfo info = manager.getImageInfo(purl.toString(), sessionContext);
 ImageFlavor[] supportedFlavors = getSupportedFlavours();
 Image image = manager.getImage(info, supportedFlavors, sessionContext);


 //TODO color profile overrides aren't handled, yet!
 //ICCColorSpaceExt colorspaceOverride = extractColorSpace(e, ctx);
 AbstractGraphicsNode specializedNode = null;
 if (image instanceof ImageXMLDOM) {
 ImageXMLDOM xmlImage = (ImageXMLDOM)image;
 if (xmlImage.getDocument() instanceof SVGDocument) {
 //Clone DOM because the Batik's CSS Parser attaches to the DOM and is therefore
 //not thread-safe.
 SVGDocument clonedDoc = (SVGDocument)BatikUtil.cloneSVGDocument(
 xmlImage.getDocument());
 return createSVGImageNode(ctx, imageElement, clonedDoc);
                } else {
 //Convert image to Graphics2D
 image = manager.convertImage(xmlImage,
 new ImageFlavor[] {ImageFlavor.GRAPHICS2D});
                }
            }
 if (image instanceof ImageRawJPEG) {
 specializedNode = createLoaderImageNode(image, ctx, imageElement, purl);
            } else if (image instanceof ImageRawCCITTFax) {
 specializedNode = createLoaderImageNode(image, ctx, imageElement, purl);
            } else if (image instanceof ImageGraphics2D) {
 ImageGraphics2D g2dImage = (ImageGraphics2D)image;
 specializedNode = new Graphics2DNode(g2dImage);
            } else {
 ctx.getUserAgent().displayError(
 new ImageException("Cannot convert an image to a usable format: " + purl));
            }


 if (specializedNode != null) {
 Rectangle2D imgBounds = getImageBounds(ctx, imageElement);
 Rectangle2D bounds = specializedNode.getPrimitiveBounds();
 float [] vb = new float[4];
 vb[0] = 0; // x
 vb[1] = 0; // y
 vb[2] = (float) bounds.getWidth(); // width
 vb[3] = (float) bounds.getHeight(); // height


 // handles the 'preserveAspectRatio', 'overflow' and 'clip'
 // and sets the appropriate AffineTransform to the image node
 initializeViewport(ctx, imageElement, specializedNode, vb, imgBounds);
 return specializedNode;
            }
        } catch (Exception e) {
 ctx.getUserAgent().displayError(e);
        }


 //Fallback
 return superCreateGraphicsNode(ctx, imageElement, purl);
    }