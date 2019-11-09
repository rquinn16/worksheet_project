package edu.cs3500.spreadsheets.view;

import edu.cs3500.spreadsheets.model.Worksheet;
import edu.cs3500.spreadsheets.model.WorksheetModel;
import edu.cs3500.spreadsheets.model.WorksheetReader;
import edu.cs3500.spreadsheets.model.cell.Cell;
import java.awt.Graphics;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 * Represents the graphical view of a Worksheet.
 */
public class WorksheetGraphicalView extends JFrame implements WorksheetView {

  private static int LENGTH = 1000;
  private static int HEIGHT = 700;
  private WorksheetModel<Cell> model;
  private JTable table;

  /**
   * Constructor for the WorksheetGraphicalView.
   * It makes a default, empty table.
   */
  public WorksheetGraphicalView(WorksheetModel<Cell> model) {
    this.model = model;
  }

  @Override
  public void render() throws IOException {
    JScrollPane scroll = new JScrollPane(table);
    this.add(scroll);
    this.setSize(LENGTH, HEIGHT);
    this.table = new JTable(new BasicTableModel(this.model));
    this.setVisible(true);
  }

  @Override
  public void paintComponents(Graphics g) {
    super.paintComponents(g);
  }

  public static void main(String[] args) {
    try {
      FileReader f = new FileReader(
          new File("C:\\Users\\jfri9\\OneDrive\\Desktop\\WorksheetExample.txt"));
      Worksheet builder = new Worksheet();
      WorksheetReader.read(builder, f);
      WorksheetModel<Cell> model = builder.createWorksheet();
      new WorksheetGraphicalView(model).render();
    }
    catch(IOException e) {
      System.out.println("Something went wrong.");
    }
  }
}
