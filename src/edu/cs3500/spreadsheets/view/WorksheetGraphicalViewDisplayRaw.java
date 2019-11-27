package edu.cs3500.spreadsheets.view;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

import javax.swing.*;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.WorksheetModel;
import edu.cs3500.spreadsheets.model.cell.Cell;
import edu.cs3500.spreadsheets.model.cell.Content;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

public class WorksheetGraphicalViewDisplayRaw extends WorksheetGraphicalView {

  /**
   * Constructor for the WorksheetGraphicalView. It makes a default, empty table.
   *
   * @param model
   */
  public WorksheetGraphicalViewDisplayRaw(WorksheetModel<Cell> model) {
    super(model);
  }

  @Override
  public void render() {
    int length = 1400;
    int height = 600;
    Dimension dimensions = new Dimension(length, height);
    BasicTableModel basicTableModel = new BasicTableModel(this.model);
    BasicTableColumnModel basicTableColumnModel = new BasicTableColumnModel(basicTableModel);
    configureJTable(dimensions, basicTableModel, basicTableColumnModel);
    fillFromModel(basicTableModel);
    JTextField textField = new JTextField();
    configureTextField(textField, basicTableModel, length);
    dataTable.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        int r = dataTable.rowAtPoint(e.getPoint());
        int c = dataTable.columnAtPoint(e.getPoint());
        int cols = basicTableModel.getColumnCount();
        if (c >= cols) {
          textField.setText(basicTableModel.getRawValue(r, c + 1));
        } else {
          textField.setText(basicTableModel.getRawValue(r, c));
        }
        dataTable.repaint();
      }
    });
    JButton confirm = new JButton("✔");
    confirm.addActionListener(e -> {
      int r = dataTable.getSelectedRow();
      int c = dataTable.getSelectedColumn();
      String str = textField.getText();
      try {
        int cols = basicTableModel.getColumnCount();
        if (c >= cols) {
          basicTableModel.setValueAt(str, r, c + 1);
          this.updateAll(basicTableModel);
        } else {
          basicTableModel.setValueAt(str, r, c);
          this.updateAll(basicTableModel);
        }
        dataTable.repaint();
      } catch (Exception ex) {
        textField.setText(ex.getMessage());
      }
    });
    JButton undo = new JButton("✗");
    String originalCellContent = textField.getText();
    undo.addActionListener(e -> textField.setText(originalCellContent));
    JScrollPane scroll = new JScrollPane(dataTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
    JTable rowNumbers = new DisplayRowNumbers(dataTable);
    scroll.setRowHeaderView(rowNumbers);
    scroll.setCorner(JScrollPane.UPPER_LEFT_CORNER, rowNumbers.getTableHeader());
    JPanel panel = new JPanel();
    panel.add(scroll, BorderLayout.CENTER);
    panel.setSize(dimensions);
    scroll.getHorizontalScrollBar().addAdjustmentListener(new InfiniteScrollH(scroll,
        basicTableColumnModel));
    scroll.getVerticalScrollBar().addAdjustmentListener(new InfiniteScrollV(scroll,
        basicTableModel));
    basicTableModel.addTableModelListener(e -> dataTable.repaint());
    basicTableModel.fireTableDataChanged();
    this.repaint();
    this.setLayout(new BorderLayout());
    this.setBounds(0, 0, length, HEIGHT);
    this.setSize(dimensions);
    this.add(panel, BorderLayout.CENTER);
    JPanel textFieldAndButtons = new JPanel();
    textFieldAndButtons.setLayout(new FlowLayout());
    textFieldAndButtons.add(confirm);
    textFieldAndButtons.add(undo);
    textFieldAndButtons.add(textField);
    this.add(textFieldAndButtons, BorderLayout.PAGE_START);
    this.setTitle("Jack and Ryan's Spreadsheet");
    this.pack();
    this.setVisible(true);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  @Override
  protected void configureJTable(Dimension dimensions, BasicTableModel t, BasicTableColumnModel c) {
    dataTable = new JTable(t, c) {
      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }

      @Override
      public int getSelectedColumn() {
        return super.getSelectedColumn();
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
    dataTable.setCellSelectionEnabled(true);
  }

  private void fillFromModel(BasicTableModel basicTableModel) {
    for (Map.Entry<Coord, Content> entry : this.model.getAllCells().entrySet()) {
      basicTableModel.setValueAt(entry.getValue().toString(),
          entry.getKey().row - 1, entry.getKey().col - 1);
    }
  }

  private void configureTextField(JTextField textField, BasicTableModel basicTableModel,
      int length) {
    textField.setPreferredSize(new Dimension(length, 20));
    textField.setText("");

    textField.addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
          int r = dataTable.getSelectedRow();
          int c = dataTable.getSelectedColumn();
          String str = textField.getText();
          basicTableModel.setValueAt(str, r, c);
          repaint();
        }
      }
    });
  }

  private void updateAll(BasicTableModel basic) {
    for (Map.Entry<Coord, Content> e : this.model.getAllCells().entrySet()) {
      basic.setValueAt(e.getValue().toString(), e.getKey().row - 1, e.getKey().col - 1);
    }
  }
}
