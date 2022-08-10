 private int encode0(byte[] src, int off, int end, byte[] dst) {
 char[] base64 = isURL ? toBase64URL : toBase64;
 int sp = off;
 int slen = (end - off) / 3 * 3;
 int sl = off + slen;
 if (linemax > 0 && slen  > linemax / 4 * 3)
 slen = linemax / 4 * 3;
 int dp = 0;
 while (sp < sl) {
 int sl0 = Math.min(sp + slen, sl);
 for (int sp0 = sp, dp0 = dp ; sp0 < sl0; ) {
 int bits = (src[sp0++] & 0xff) << 16 |
                               (src[sp0++] & 0xff) <<  8 |
                               (src[sp0++] & 0xff);
 dst[dp0++] = (byte)base64[(bits >>> 18) & 0x3f];
 dst[dp0++] = (byte)base64[(bits >>> 12) & 0x3f];
 dst[dp0++] = (byte)base64[(bits >>> 6)  & 0x3f];
 dst[dp0++] = (byte)base64[bits & 0x3f];
                }
 int dlen = (sl0 - sp) / 3 * 4;
 dp += dlen;
 sp = sl0;
 if (dlen == linemax && sp < end) {
 for (byte b : newline){
 dst[dp++] = b;
                    }
                }
            }
 if (sp < end) {               // 1 or 2 leftover bytes
 int b0 = src[sp++] & 0xff;
 dst[dp++] = (byte)base64[b0 >> 2];
 if (sp == end) {
 dst[dp++] = (byte)base64[(b0 << 4) & 0x3f];
 if (doPadding) {
 dst[dp++] = '=';
 dst[dp++] = '=';
                    }
                } else {
 int b1 = src[sp++] & 0xff;
 dst[dp++] = (byte)base64[(b0 << 4) & 0x3f | (b1 >> 4)];
 dst[dp++] = (byte)base64[(b1 << 2) & 0x3f];
 if (doPadding) {
 dst[dp++] = '=';
                    }
                }
            }
 return dp;
        }