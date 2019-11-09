import org.junit.Test;

import edu.cs3500.spreadsheets.model.Worksheet;
import edu.cs3500.spreadsheets.model.WorksheetModel;
import edu.cs3500.spreadsheets.model.cell.Cell;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the model, constructed through the builder.
 */
public class TestThroughBuilder {
  @Test
  public void testModelAddingAndDisplaying() {
    WorksheetModel<Cell> sheet = new Worksheet().createWorksheet();

    assertEquals(0, sheet.getAllCells().size());

    sheet.addCell(1, 1, "1.1");
    assertEquals("1.1", sheet.displayCell(1, 1));

    assertEquals(1, sheet.getAllCells().size());

    sheet.addCell(1, 2, "2.2");
    assertEquals("2.2", sheet.displayCell(1, 2));

    assertEquals(2, sheet.getAllCells().size());

    sheet.addCell(1, 3, "(SUM A1:A2)");
    assertEquals("3.3", sheet.displayCell(1, 3));

    sheet.addCell(1, 4, "(PRODUCT A1:A3)");
    assertEquals("7.986", sheet.displayCell(1, 4));

    sheet.addCell(1, 5, "(PRODUCT (SUM A1:A2) (SUM (PRODUCT A1 A2 A3) A1:A4) A2)");
    assertEquals("163.87272", sheet.displayCell(1, 5));

    sheet.addCell(1, 100, "A1:A4");
    assertEquals("#REF!", sheet.displayCell(1, 100));
    // NEED TO FIX THE MULTI REFERENCE

    sheet.addCell(1, 101, "A1");
    assertEquals("1.1", sheet.displayCell(1, 101));

    sheet.addCell(2, 1, "\"R\"");
    assertEquals("R", sheet.displayCell(2, 1));

    sheet.addCell(2, 2, "\"y\"");
    assertEquals("y", sheet.displayCell(2, 2));

    sheet.addCell(2, 3, "\"a\"");
    assertEquals("a", sheet.displayCell(2, 3));

    sheet.addCell(2, 4, "\"n\"");
    assertEquals("n", sheet.displayCell(2, 4));

    sheet.addCell(2, 5, "(CONCATENATE B1:B4)");
    assertEquals("Ryan", sheet.displayCell(2, 5));

    sheet.addCell(2, 6, "(SUM B1:B5)");
    assertEquals("0", sheet.displayCell(2, 6));

    sheet.addCell(2, 7, "(SUM A1:B4)");
    assertEquals("14.586", sheet.displayCell(2, 7));

    sheet.addCell(2, 8, "(< 1 2)");
    assertEquals("true", sheet.displayCell(2, 8));

    sheet.addCell(2, 9, "(< A1 A2)");
    assertEquals("true", sheet.displayCell(2, 9));

    sheet.addCell(2, 10, "(< A1 A1)");
    assertEquals("false", sheet.displayCell(2, 10));

    sheet.addCell(2, 11, "(< A2 A1)");
    assertEquals("false", sheet.displayCell(2, 11));

    sheet.addCell(2, 12, "(CONCATENATE A1:B4)");
    assertEquals("Ryan", sheet.displayCell(2, 12));

    sheet.addCell(2, 13, "B12");
    assertEquals("Ryan", sheet.displayCell(2, 13));

    sheet.addCell(3, 1, "true");
    assertEquals("true", sheet.displayCell(3, 1));

    sheet.addCell(3, 2, "false");
    assertEquals("false", sheet.displayCell(3, 2));

    sheet.addCell(3, 1, "1.1");
    assertEquals("1.1", sheet.displayCell(3, 1));

    sheet.addCell(3, 1, "true");
    assertEquals("true", sheet.displayCell(3, 1));

    sheet.addCell(3, 3, "(SUM C1:C2)");
    assertEquals("0", sheet.displayCell(3, 3));

    sheet.addCell(3, 4, "(PRODUCT C1:C2)");
    assertEquals("0", sheet.displayCell(3, 4));

    sheet.addCell(4, 1, "D2");
    assertEquals("", sheet.displayCell(4, 1));

    sheet.addCell(4, 2, "D3");
    assertEquals("", sheet.displayCell(4, 1));
    assertEquals("", sheet.displayCell(4, 2));

    sheet.addCell(4, 3, "D1");
    assertEquals("#REF!", sheet.displayCell(4, 1));
    assertEquals("#REF!", sheet.displayCell(4, 2));
    assertEquals("#REF!", sheet.displayCell(4, 3));

    assertEquals("", sheet.displayCell(1000000, 100000));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidFunctionInitialization1() {
    WorksheetModel<Cell> sheet = new Worksheet().createWorksheet();
    sheet.addCell(1, 1, "(BAD_NAME)");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidFunctionInitialization2() {
    WorksheetModel<Cell> sheet = new Worksheet().createWorksheet();
    sheet.addCell(1, 1, "1");
    sheet.addCell(1, 2, "(BAD_NAME A1)");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidFunctionInitializationLessThanBadInput() {
    WorksheetModel<Cell> sheet = new Worksheet().createWorksheet();
    sheet.addCell(1, 1, "false");
    sheet.addCell(1, 2, "true");
    sheet.addCell(1, 3, "(< A1 A2)");
  }
}