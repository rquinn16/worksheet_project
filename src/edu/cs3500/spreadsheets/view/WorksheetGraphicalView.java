package edu.cs3500.spreadsheets.view;

import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.*;

import edu.cs3500.spreadsheets.model.Worksheet;
import edu.cs3500.spreadsheets.model.WorksheetModel;
import edu.cs3500.spreadsheets.model.WorksheetReader;
import edu.cs3500.spreadsheets.model.cell.Cell;

/**
 * Represents the graphical view of a Worksheet in the style of Excel's spreadsheet view.
 * We allow here for infinite scrolling past the bottom right-most cell in both the horizontal
 * and vertical directions.
 */
public class WorksheetGraphicalView extends JFrame implements WorksheetView {

  private WorksheetModel<Cell> model;

  /**
   * Constructor for the WorksheetGraphicalView.
   * It makes a default, empty table.
   */
  public WorksheetGraphicalView(WorksheetModel<Cell> model) {
    this.model = model;
  }

  @Override
  public void render() {
    int LENGTH = 1600;
    int HEIGHT = 900;
    Dimension DIMENSIONS = new Dimension(LENGTH, HEIGHT);

    BasicTableModel t = new BasicTableModel(this.model);
    BasicTableColumnModel c = new BasicTableColumnModel(t);
    JTable dataTable = new JTable(t, c) {
      @Override
      public boolean isCellEditable(int row, int column) {
        return true;
      }
    };
    dataTable.getTableHeader().setReorderingAllowed(false);
    dataTable.getTableHeader().setResizingAllowed(false);
    dataTable.setRowHeight(30);
    dataTable.getColumnModel().setColumnMargin(1);
    dataTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    dataTable.setSize(DIMENSIONS);
    dataTable.setPreferredScrollableViewportSize(DIMENSIONS);
    dataTable.setShowGrid(true);
    dataTable.setGridColor(Color.BLACK);
    JTable rowNumbers = new DisplayRowNumbers(dataTable);
    JScrollPane scroll = new JScrollPane(dataTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
    scroll.setRowHeaderView(rowNumbers);
    scroll.setCorner(JScrollPane.UPPER_LEFT_CORNER, rowNumbers.getTableHeader());
    JPanel panel = new JPanel();
    panel.add(scroll, BorderLayout.CENTER);
    panel.setSize(DIMENSIONS);
    scroll.getHorizontalScrollBar().addAdjustmentListener(new InfiniteScrollH(scroll, c));
    scroll.getVerticalScrollBar().addAdjustmentListener(new InfiniteScrollV(scroll, t));
    this.setLayout(new BorderLayout());
    this.setBounds(0, 0, LENGTH, HEIGHT);
    this.setSize(DIMENSIONS);
    this.add(panel, BorderLayout.CENTER);
    this.setTitle("Jack and Ryan's Spreadsheet");
    this.pack();
    this.setVisible(true);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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