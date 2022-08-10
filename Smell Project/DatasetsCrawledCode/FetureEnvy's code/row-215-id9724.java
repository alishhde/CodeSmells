 private void handleHeaderFooter(Range[] ranges, String type, HWPFDocument document,
 PicturesSource pictures, PicturesTable pictureTable, XHTMLContentHandler xhtml)
 throws SAXException, IOException, TikaException {
 if (countParagraphs(ranges) > 0) {
 xhtml.startElement("div", "class", type);
 ListManager listManager = new ListManager(document);
 for (Range r : ranges) {
 if (r != null) {
 for (int i = 0; i < r.numParagraphs(); i++) {
 Paragraph p = r.getParagraph(i);


 i += handleParagraph(p, 0, r, document,
 FieldsDocumentPart.HEADER, pictures, pictureTable, listManager, xhtml);
                    }
                }
            }
 xhtml.endElement("div");
        }
    }