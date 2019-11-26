package edu.cs3500.spreadsheets.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.*;

import edu.cs3500.spreadsheets.model.WorksheetModel;
import edu.cs3500.spreadsheets.model.cell.Cell;

/**
 * Represents the graphical view of a Worksheet in the style of Excel's spreadsheet view.
 * We allow here for infinite scrolling past the bottom right-most cell in both the horizontal
 * and vertical directions.
 */
public class WorksheetGraphicalView extends JFrame implements WorksheetView {

  protected WorksheetModel<Cell> model;

  /**
   * Constructor for the WorksheetGraphicalView.
   * It makes a default, empty table.
   */
  public WorksheetGraphicalView(WorksheetModel<Cell> model) {
    this.model = model;
  }

  @Override
  public void render() {
    int length = 1400;
    int height = 600;
    Dimension dimensions = new Dimension(length, height);
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
    dataTable.setSize(dimensions);
    dataTable.setPreferredScrollableViewportSize(dimensions);
    dataTable.setShowGrid(true);
    dataTable.setGridColor(Color.BLACK);
    JTable rowNumbers = new DisplayRowNumbers(dataTable);
    JScrollPane scroll = new JScrollPane(dataTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
    scroll.setRowHeaderView(rowNumbers);
    scroll.setCorner(JScrollPane.UPPER_LEFT_CORNER, rowNumbers.getTableHeader());
    JPanel panel = new JPanel();
    panel.add(scroll, BorderLayout.CENTER);
    panel.setSize(dimensions);
    scroll.getHorizontalScrollBar().addAdjustmentListener(new InfiniteScrollH(scroll, c));
    scroll.getVerticalScrollBar().addAdjustmentListener(new InfiniteScrollV(scroll, t));
    this.setLayout(new BorderLayout());
    this.setBounds(0, 0, length, HEIGHT);
    this.setSize(dimensions);
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

  protected void setUpDataTable(JTable dataTable, Dimension dimensions, BasicTableModel t,
                                BasicTableColumnModel c) {
    dataTable = getjTable(dimensions, t, c);
  }



  static JTable getjTable(Dimension dimensions, BasicTableModel t, BasicTableColumnModel c) {
    JTable dataTable;
    dataTable = new JTable(t, c) {
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
    dataTable.setSize(dimensions);
    dataTable.setPreferredScrollableViewportSize(dimensions);
    dataTable.setShowGrid(true);
    dataTable.setGridColor(Color.BLACK);
    return dataTable;
  }
}