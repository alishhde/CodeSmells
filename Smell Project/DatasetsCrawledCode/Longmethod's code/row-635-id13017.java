 public void Blit(SurfaceData src, SurfaceData dst,
 Composite comp, Region clip,
 int srcx, int srcy, int dstx, int dsty, int w, int h)
    {
 Raster srcRast = src.getRaster(srcx, srcy, w, h);
 IntegerComponentRaster icr = (IntegerComponentRaster) srcRast;
 int[] srcPix = icr.getDataStorage();


 WritableRaster dstRast =
            (WritableRaster) dst.getRaster(dstx, dsty, w, h);
 ColorModel dstCM = dst.getColorModel();


 Region roi = CustomComponent.getRegionOfInterest(src, dst, clip,
 srcx, srcy,
 dstx, dsty, w, h);
 SpanIterator si = roi.getSpanIterator();


 Object dstPix = null;


 int srcScan = icr.getScanlineStride();
 // assert(icr.getPixelStride() == 1);
 srcx -= dstx;
 srcy -= dsty;
 int[] span = new int[4];
 while (si.nextSpan(span)) {
 int rowoff = (icr.getDataOffset(0) +
                          (srcy + span[1]) * srcScan +
                          (srcx + span[0]));
 for (int y = span[1]; y < span[3]; y++) {
 int off = rowoff;
 for (int x = span[0]; x < span[2]; x++) {
 dstPix = dstCM.getDataElements(srcPix[off++], dstPix);
 dstRast.setDataElements(x, y, dstPix);
                }
 rowoff += srcScan;
            }
        }
 // REMIND: We need to do something to make sure that dstRast
 // is put back to the destination (as in the native Release
 // function)
 // src.releaseRaster(srcRast);  // NOP?
 // dst.releaseRaster(dstRast);
    }