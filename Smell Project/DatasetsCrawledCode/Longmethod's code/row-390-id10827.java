 public boolean readFont(FontFileReader in, String header, String name) throws IOException {
 initializeFont(in);
 /*
         * Check if TrueType collection, and that the name
         * exists in the collection
         */
 if (!checkTTC(header, name)) {
 if (name == null) {
 throw new IllegalArgumentException(
 "For TrueType collection you must specify which font "
                    + "to select (-ttcname)");
            } else {
 throw new IOException(
 "Name does not exist in the TrueType collection: " + name);
            }
        }


 readDirTabs();
 readFontHeader();
 getNumGlyphs();
 if (log.isDebugEnabled()) {
 log.debug("Number of glyphs in font: " + numberOfGlyphs);
        }
 readHorizontalHeader();
 readHorizontalMetrics();
 initAnsiWidths();
 readPostScript();
 readOS2();
 determineAscDesc();


 readName();
 boolean pcltFound = readPCLT();
 // Read cmap table and fill in ansiwidths
 boolean valid = readCMAP();
 if (!valid) {
 return false;
        }


 // Create cmaps for bfentries
 createCMaps();
 updateBBoxAndOffset();


 if (useKerning) {
 readKerning();
        }
 handleCharacterSpacing(in);


 guessVerticalMetricsFromGlyphBBox();
 return true;
    }