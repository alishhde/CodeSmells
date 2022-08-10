 private TtmlRegion parseRegionAttributes(
 XmlPullParser xmlParser, CellResolution cellResolution, TtsExtent ttsExtent) {
 String regionId = XmlPullParserUtil.getAttributeValue(xmlParser, TtmlNode.ATTR_ID);
 if (regionId == null) {
 return null;
    }


 float position;
 float line;


 String regionOrigin = XmlPullParserUtil.getAttributeValue(xmlParser, TtmlNode.ATTR_TTS_ORIGIN);
 if (regionOrigin != null) {
 Matcher originPercentageMatcher = PERCENTAGE_COORDINATES.matcher(regionOrigin);
 Matcher originPixelMatcher = PIXEL_COORDINATES.matcher(regionOrigin);
 if (originPercentageMatcher.matches()) {
 try {
 position = Float.parseFloat(originPercentageMatcher.group(1)) / 100f;
 line = Float.parseFloat(originPercentageMatcher.group(2)) / 100f;
        } catch (NumberFormatException e) {
 Log.w(TAG, "Ignoring region with malformed origin: " + regionOrigin);
 return null;
        }
      } else if (originPixelMatcher.matches()) {
 if (ttsExtent == null) {
 Log.w(TAG, "Ignoring region with missing tts:extent: " + regionOrigin);
 return null;
        }
 try {
 int width = Integer.parseInt(originPixelMatcher.group(1));
 int height = Integer.parseInt(originPixelMatcher.group(2));
 // Convert pixel values to fractions.
 position = width / (float) ttsExtent.width;
 line = height / (float) ttsExtent.height;
        } catch (NumberFormatException e) {
 Log.w(TAG, "Ignoring region with malformed origin: " + regionOrigin);
 return null;
        }
      } else {
 Log.w(TAG, "Ignoring region with unsupported origin: " + regionOrigin);
 return null;
      }
    } else {
 Log.w(TAG, "Ignoring region without an origin");
 return null;
 // TODO: Should default to top left as below in this case, but need to fix
 // https://github.com/google/ExoPlayer/issues/2953 first.
 // Origin is omitted. Default to top left.
 // position = 0;
 // line = 0;
    }


 float width;
 float height;
 String regionExtent = XmlPullParserUtil.getAttributeValue(xmlParser, TtmlNode.ATTR_TTS_EXTENT);
 if (regionExtent != null) {
 Matcher extentPercentageMatcher = PERCENTAGE_COORDINATES.matcher(regionExtent);
 Matcher extentPixelMatcher = PIXEL_COORDINATES.matcher(regionExtent);
 if (extentPercentageMatcher.matches()) {
 try {
 width = Float.parseFloat(extentPercentageMatcher.group(1)) / 100f;
 height = Float.parseFloat(extentPercentageMatcher.group(2)) / 100f;
        } catch (NumberFormatException e) {
 Log.w(TAG, "Ignoring region with malformed extent: " + regionOrigin);
 return null;
        }
      } else if (extentPixelMatcher.matches()) {
 if (ttsExtent == null) {
 Log.w(TAG, "Ignoring region with missing tts:extent: " + regionOrigin);
 return null;
        }
 try {
 int extentWidth = Integer.parseInt(extentPixelMatcher.group(1));
 int extentHeight = Integer.parseInt(extentPixelMatcher.group(2));
 // Convert pixel values to fractions.
 width = extentWidth / (float) ttsExtent.width;
 height = extentHeight / (float) ttsExtent.height;
        } catch (NumberFormatException e) {
 Log.w(TAG, "Ignoring region with malformed extent: " + regionOrigin);
 return null;
        }
      } else {
 Log.w(TAG, "Ignoring region with unsupported extent: " + regionOrigin);
 return null;
      }
    } else {
 Log.w(TAG, "Ignoring region without an extent");
 return null;
 // TODO: Should default to extent of parent as below in this case, but need to fix
 // https://github.com/google/ExoPlayer/issues/2953 first.
 // Extent is omitted. Default to extent of parent.
 // width = 1;
 // height = 1;
    }


 @Cue.AnchorType int lineAnchor = Cue.ANCHOR_TYPE_START;
 String displayAlign = XmlPullParserUtil.getAttributeValue(xmlParser,
 TtmlNode.ATTR_TTS_DISPLAY_ALIGN);
 if (displayAlign != null) {
 switch (Util.toLowerInvariant(displayAlign)) {
 case "center":
 lineAnchor = Cue.ANCHOR_TYPE_MIDDLE;
 line += height / 2;
 break;
 case "after":
 lineAnchor = Cue.ANCHOR_TYPE_END;
 line += height;
 break;
 default:
 // Default "before" case. Do nothing.
 break;
      }
    }


 float regionTextHeight = 1.0f / cellResolution.rows;
 return new TtmlRegion(
 regionId,
 position,
 line,
 /* lineType= */ Cue.LINE_TYPE_FRACTION,
 lineAnchor,
 width,
 /* textSizeType= */ Cue.TEXT_SIZE_TYPE_FRACTIONAL_IGNORE_PADDING,
 /* textSize= */ regionTextHeight);
  }