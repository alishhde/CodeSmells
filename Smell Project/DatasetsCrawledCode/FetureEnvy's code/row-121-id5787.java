 @Test
 public void writeRead() throws IOException {
 try (XSSFWorkbook workbook = XSSFTestDataSamples.openSampleWorkbook("WithVariousData.xlsx")) {
 XSSFSheet sheet1 = workbook.getSheetAt(0);
 XSSFSheet sheet2 = workbook.getSheetAt(1);


 assertTrue(sheet1.hasComments());
 assertFalse(sheet2.hasComments());


 // Change on comment on sheet 1, and add another into
 //  sheet 2
 Row r5 = sheet1.getRow(4);
 Comment cc5 = r5.getCell(2).getCellComment();
 cc5.setAuthor("Apache POI");
 cc5.setString(new XSSFRichTextString("Hello!"));


 Row r2s2 = sheet2.createRow(2);
 Cell c1r2s2 = r2s2.createCell(1);
 assertNull(c1r2s2.getCellComment());


 Drawing<?> dg = sheet2.createDrawingPatriarch();
 Comment cc2 = dg.createCellComment(new XSSFClientAnchor());
 cc2.setAuthor("Also POI");
 cc2.setString(new XSSFRichTextString("A new comment"));
 c1r2s2.setCellComment(cc2);


 // Save, and re-load the file
 try (XSSFWorkbook workbookBack = XSSFTestDataSamples.writeOutAndReadBack(workbook)) {
 // Check we still have comments where we should do
 sheet1 = workbookBack.getSheetAt(0);
 sheet2 = workbookBack.getSheetAt(1);
 assertNotNull(sheet1.getRow(4).getCell(2).getCellComment());
 assertNotNull(sheet1.getRow(6).getCell(2).getCellComment());
 assertNotNull(sheet2.getRow(2).getCell(1).getCellComment());


 // And check they still have the contents they should do
 assertEquals("Apache POI",
 sheet1.getRow(4).getCell(2).getCellComment().getAuthor());
 assertEquals("Nick Burch",
 sheet1.getRow(6).getCell(2).getCellComment().getAuthor());
 assertEquals("Also POI",
 sheet2.getRow(2).getCell(1).getCellComment().getAuthor());


 assertEquals("Hello!",
 sheet1.getRow(4).getCell(2).getCellComment().getString().getString());
            }
        }
    }