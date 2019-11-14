import org.junit.Test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

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
      BufferedReader expectedReader = new BufferedReader(new FileReader(actual));

      expectedWriter.append("A1 1.000000\n");
      expectedWriter.write("A2 =1.000000\n");
      expectedWriter.write("A3 =(SUM A1:A2)\n");
      expectedWriter.close();

      WorksheetModel<Cell> sheet = new Worksheet().createWorksheet();
      WorksheetTextualView output = new WorksheetTextualView(sheet,
              "resources/actualoutput.txt");

      sheet.addCell(1, 1, "1");
      sheet.addCell(1, 2 , "1");
      sheet.addCell(1, 3, "(SUM (SUM A1:A2) A1)");

      output.render();

      assertEquals(expectedReader.readLine(), actualReader.readLine());
      assertEquals(expectedReader.readLine(), actualReader.readLine());
      assertEquals(expectedReader.readLine(), actualReader.readLine());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}