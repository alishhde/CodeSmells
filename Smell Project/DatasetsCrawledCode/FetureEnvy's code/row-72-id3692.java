 @Test
 public void testBug56655a() throws IOException {
 try (Workbook wb = _testDataProvider.createWorkbook()) {
 Sheet sheet = wb.createSheet();


 setCellFormula(sheet, 0, 0, "B1*C1");
 sheet.getRow(0).createCell(1).setCellValue("A");
 setCellFormula(sheet, 1, 0, "B1*C1");
 sheet.getRow(1).createCell(1).setCellValue("A");
 setCellFormula(sheet, 0, 3, "SUMIFS(A:A,A:A,A2)");


 wb.getCreationHelper().createFormulaEvaluator().evaluateAll();


 assertEquals(CellType.ERROR, getCell(sheet, 0, 0).getCachedFormulaResultType());
 assertEquals(FormulaError.VALUE.getCode(), getCell(sheet, 0, 0).getErrorCellValue());
 assertEquals(CellType.ERROR, getCell(sheet, 1, 0).getCachedFormulaResultType());
 assertEquals(FormulaError.VALUE.getCode(), getCell(sheet, 1, 0).getErrorCellValue());
 assertEquals(CellType.ERROR, getCell(sheet, 0, 3).getCachedFormulaResultType());
 assertEquals(FormulaError.VALUE.getCode(), getCell(sheet, 0, 3).getErrorCellValue());
        }
    }