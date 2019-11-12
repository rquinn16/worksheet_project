package edu.cs3500.spreadsheets.view;

import edu.cs3500.spreadsheets.model.Worksheet;
import edu.cs3500.spreadsheets.model.WorksheetModel;
import edu.cs3500.spreadsheets.model.WorksheetReader;
import edu.cs3500.spreadsheets.model.cell.Cell;

import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.*;
import javax.swing.table.TableColumn;

/**
 * Represents the graphical view of a Worksheet.
 */
public class WorksheetGraphicalView extends JFrame implements WorksheetView {

  private static int LENGTH = 1000;
  private static int HEIGHT = 700;
  private WorksheetModel<Cell> model;
  //private JTable table;
  private JPanel panel;

  /**
   * Constructor for the WorksheetGraphicalView.
   * It makes a default, empty table.
   */
  public WorksheetGraphicalView(WorksheetModel<Cell> model) {
    this.model = model;
  }

  @Override
  public void render() throws IOException {
    this.panel = new JPanel();
    JTable dataTable = new JTable(new BasicTableModel(this.model)) {
      @Override
      public boolean isCellEditable(int row, int column) {
        return true;
      }
    };
    dataTable.getTableHeader().setReorderingAllowed(false);
    dataTable.getTableHeader().setResizingAllowed(false);
    dataTable.setSize(LENGTH, HEIGHT);
    dataTable.setRowHeight(30);
    dataTable.getColumnModel().setColumnMargin(1);
    dataTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    dataTable.setSize(LENGTH, HEIGHT);
    JTable rowNumbers = new DisplayRowNumbers(dataTable);
    JScrollPane scroll = new JScrollPane(dataTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
    scroll.setRowHeaderView(rowNumbers);
    scroll.setCorner(JScrollPane.UPPER_LEFT_CORNER, rowNumbers.getTableHeader());
    this.panel.add(scroll);
    this.panel.setSize(LENGTH, HEIGHT);
    this.setSize(LENGTH, HEIGHT);

    this.add(panel);
    dataTable.setShowGrid(true);
    dataTable.setGridColor(Color.BLACK);
    this.setVisible(true);
  }

  @Override
  public void paintComponents(Graphics g) {
    super.paintComponents(g);
    Graphics2D g2d = (Graphics2D) g;

    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
  }

  public static void main(String[] args) {
    try {
      FileReader f = new FileReader(
              new File("/Users/ryanquinn/Desktop/worksheet 2/WorksheetExample.txt"));
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