import org.junit.Test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.cs3500.spreadsheets.model.Worksheet;
import edu.cs3500.spreadsheets.model.WorksheetModel;
import edu.cs3500.spreadsheets.model.cell.Cell;
import edu.cs3500.spreadsheets.view.WorksheetTextualView;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the textual view where the model's information is output to a text file.
 */
public class TestTextBasedView {
  @Test
  public void testOutput() {
    try {
      File actual = new File("resources/actualoutput.txt");
      File expected = new File("resources/expectedoutput.txt");

      BufferedWriter expectedWriter = new BufferedWriter(new FileWriter(expected));

      BufferedReader actualReader = new BufferedReader(new FileReader(actual));
      BufferedReader expectedReader = new BufferedReader(new FileReader(expected));

      expectedWriter.append("A1 1.000000\n");
      expectedWriter.write("A2 1.000000\n");
      expectedWriter.write("A3 =(SUM A1:A2)\n");
      expectedWriter.write("A4 =(SUM A1:A2 A4)\n");
      expectedWriter.write("AA555 =A1\n");
      expectedWriter.write("B1 =A1:A4\n");
      expectedWriter.write("C1 true\n");
      expectedWriter.write("C2 \"string\"\n");
      expectedWriter.close();

      WorksheetModel<Cell> sheet = new Worksheet().createWorksheet();
      WorksheetTextualView output = new WorksheetTextualView(sheet,
              "resources/actualoutput.txt");

      sheet.addCell(1, 1, "1");
      sheet.addCell(1, 2, "1");
      sheet.addCell(1, 3, "(SUM A1:A2)");
      sheet.addCell(1, 4, "(SUM A1:A2 A4)");
      sheet.addCell(27, 555, "A1");
      sheet.addCell(2, 1, "A1:A4");
      sheet.addCell(3, 1, "true");
      sheet.addCell(3, 2, "\"string\"");

      output.render();

      List<String> expectedLines = new ArrayList<>();
      boolean endOfExpected = true;
      while (endOfExpected) {
        String next = expectedReader.readLine();
        if (next == null) {
          endOfExpected = false;
        } else {
          expectedLines.add(next);
        }
      }

      List<String> actualLines = new ArrayList<>();
      boolean endOfActual = true;
      while (endOfActual) {
        String next = actualReader.readLine();
        if (next == null) {
          endOfActual = false;
        } else {
          actualLines.add(next);
        }
      }

      Collections.sort(expectedLines);
      Collections.sort(actualLines);

      assertEquals(expectedLines.size(), actualLines.size());

      for (int i = 0; i < expectedLines.size(); i++) {
        assertEquals(expectedLines.get(i), actualLines.get(i));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}