 private static class FontDelegate {
 private static int[] defaultMapping = {
 CONTROL_TEXT_FONT, SYSTEM_TEXT_FONT,
 USER_TEXT_FONT, CONTROL_TEXT_FONT,
 CONTROL_TEXT_FONT, SUB_TEXT_FONT
        };
 FontUIResource[] fonts;


 // menu and window are mapped to controlFont
 public FontDelegate() {
 fonts = new FontUIResource[6];
        }


 public FontUIResource getFont(int type) {
 int mappedType = defaultMapping[type];
 if (fonts[type] == null) {
 Font f = getPrivilegedFont(mappedType);


 if (f == null) {
 f = new Font(getDefaultFontName(type),
 getDefaultFontStyle(type),
 getDefaultFontSize(type));
                }
 fonts[type] = new FontUIResource(f);
            }
 return fonts[type];
        }


 /**
         * This is the same as invoking
         * <code>Font.getFont(key)</code>, with the exception
         * that it is wrapped inside a <code>doPrivileged</code> call.
         */
 protected Font getPrivilegedFont(final int key) {
 return java.security.AccessController.doPrivileged(
 new java.security.PrivilegedAction<Font>() {
 public Font run() {
 return Font.getFont(getDefaultPropertyName(key));
                    }
                }
                );
        }
    }