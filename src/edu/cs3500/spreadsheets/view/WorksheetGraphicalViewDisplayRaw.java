package edu.cs3500.spreadsheets.view;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Map;

import javax.swing.*;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.WorksheetModel;
import edu.cs3500.spreadsheets.model.cell.Cell;
import edu.cs3500.spreadsheets.model.cell.Content;

public class WorksheetGraphicalViewDisplayRaw extends WorksheetGraphicalView {

  private BasicTableModel basicTable;
  private BasicTableColumnModel basicColumn;
  private JTable dataTable;

  /**
   * Constructor for the WorksheetGraphicalView. It makes a default, empty table.
   *
   * @param model
   */
  public WorksheetGraphicalViewDisplayRaw(WorksheetModel<Cell> model) {

    super(model);

    this.basicTable = new BasicTableModel(this.model);
    this.basicColumn = new BasicTableColumnModel(this.basicTable);
    this.dataTable = new JTable(this.basicTable, this.basicColumn) {
      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }

      @Override
      public int getSelectedColumn() {
        return super.getSelectedColumn() + 1;
      }
    };
  }

  @Override
  public void render() {
    int length = 1400;
    int height = 600;
    Dimension dimensions = new Dimension(length, height);
    for (Map.Entry<Coord, Content> entry : this.model.getAllCells().entrySet()) {
      this.basicTable.setValueAt(entry.getValue().toString(),
              entry.getKey().row - 1, entry.getKey().col - 1);
    }
    dataTable.getTableHeader().setReorderingAllowed(false);
    dataTable.getTableHeader().setResizingAllowed(false);
    dataTable.setRowHeight(30);
    dataTable.getColumnModel().setColumnMargin(1);
    dataTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    dataTable.setSize(dimensions);
    dataTable.setPreferredScrollableViewportSize(dimensions);
    dataTable.setShowGrid(true);
    dataTable.setGridColor(Color.BLACK);
    dataTable.setCellSelectionEnabled(true);
    if (dataTable.isCellSelected(dataTable.getSelectedRow(), dataTable.getSelectedColumn())) {
      setForeground(Color.red);
    } else if (dataTable.isRowSelected(dataTable.getSelectedRow())) {
      setForeground(Color.green);
    } else if (dataTable.isColumnSelected(dataTable.getSelectedColumn())) {
      setForeground(Color.blue);
    } else {
      setForeground(Color.black);
    }
    JTextField topBar = new JTextField();
    topBar.setPreferredSize(new Dimension(length, 20));
    topBar.setText("");

    topBar.addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
          int r = dataTable.getSelectedRow();
          int c = dataTable.getSelectedColumn();
          String str = topBar.getText();
          basicTable.setValueAt(str, r, c);
          repaint();
        }
      }
    });
    JButton confirm = new JButton("✔");
    confirm.addActionListener(e -> {
      int r = dataTable.getSelectedRow();
      int c = dataTable.getSelectedColumn();
      String str = topBar.getText();
      basicTable.setValueAt(str, r, c);
      repaint();
    });
    JButton undo = new JButton("✗");
    String originalCellContent = topBar.getText();
    undo.addActionListener(e -> topBar.setText(originalCellContent));
    JScrollPane scroll = new JScrollPane(dataTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
    JTable rowNumbers = new DisplayRowNumbers(dataTable);
    scroll.setRowHeaderView(rowNumbers);
    scroll.setCorner(JScrollPane.UPPER_LEFT_CORNER, rowNumbers.getTableHeader());
    JPanel panel = new JPanel();
    panel.add(scroll, BorderLayout.CENTER);
    panel.setSize(dimensions);
    scroll.getHorizontalScrollBar().addAdjustmentListener(new InfiniteScrollH(scroll,
            basicColumn));
    scroll.getVerticalScrollBar().addAdjustmentListener(new InfiniteScrollV(scroll,
            basicTable));
    this.repaint();
    this.setLayout(new BorderLayout());
    this.setBounds(0, 0, length, HEIGHT);
    this.setSize(dimensions);
    this.add(panel, BorderLayout.CENTER);
    JPanel textFieldAndButtons = new JPanel();
    textFieldAndButtons.setLayout(new FlowLayout());
    textFieldAndButtons.add(confirm);
    textFieldAndButtons.add(undo);
    textFieldAndButtons.add(topBar);
    this.add(textFieldAndButtons, BorderLayout.PAGE_START);
    this.setTitle("Jack and Ryan's Spreadsheet");
    this.pack();
    this.setVisible(true);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  public JTable getTable() {
    return this.dataTable;
  }

//  @Override
//  protected void setUpDataTable(JTable dataTable, Dimension dimensions, BasicTableModel t,
//                                BasicTableColumnModel c) {
//    dataTable = getjTable(dimensions, t, c);
//    dataTable.getModel().addTableModelListener(e -> {
//      int row = e.getFirstRow();
//      int col = e.getColumn();
//      Cell c1 = (Cell) e.getSource();
//      String updated = c1.toString();
//      WorksheetGraphicalViewDisplayRaw.super.model.addCell(row, col, updated);
//    });
//  }
}
