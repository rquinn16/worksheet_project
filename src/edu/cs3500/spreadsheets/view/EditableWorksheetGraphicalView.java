package edu.cs3500.spreadsheets.view;

import edu.cs3500.spreadsheets.controller.Features;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.WorksheetModel;
import edu.cs3500.spreadsheets.model.cell.Cell;
import edu.cs3500.spreadsheets.model.cell.Content;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

/**
 * Class representing a view for our graph.
 */
public class EditableWorksheetGraphicalView extends WorksheetGraphicalView
    implements EditableWorksheetView {

  private int length;
  private Dimension dimensions;
  private BasicTableModel basicTableModel;
  private BasicTableColumnModel basicTableColumnModel;
  private JTextField textField;
  private JButton confirm;
  private JButton undo;
  private Features features;


  /**
   * Constructor for the WorksheetGraphicalView. It makes a default, empty table.
   * @param model The view will be rendered based on the information in the model.
   */
  public EditableWorksheetGraphicalView(WorksheetModel<Cell> model) {
    super(model);
    this.length = 1400;
    int height = 600;
    this.dimensions = new Dimension(length, height);
    this.basicTableModel = new BasicTableModel(this.model);
    this.basicTableColumnModel = new BasicTableColumnModel(basicTableModel);
    this.configureJTable(dimensions, basicTableModel, basicTableColumnModel);
    this.textField = new JTextField();
    this.confirm = new JButton("✔");
    confirm.addActionListener(e -> features.updateCellContents());
    this.undo = new JButton("✗");

  }

  @Override
  public void render() {
    fillFromModel(basicTableModel);
    configureTextField(textField, basicTableModel, length);
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
  public void addFeatures(Features features) {
    this.features = features;
  }

  @Override
  public void updateCell(String content, int col, int row) {
    basicTableModel.setValueAt(content, row, col);
  }

  @Override
  public void setMouseListener(MouseListener mouseListener) {
    dataTable.addMouseListener(mouseListener);
  }

  @Override
  public void setActionListener(ActionListener actionListener) {
    confirm.addActionListener(actionListener);
  }

  @Override
  public int getSelectedColumn() {
    return dataTable.getSelectedColumn();
  }

  @Override
  public int getSelectedRow() {
    return dataTable.getSelectedRow();
  }

  @Override
  public String getCurrentText() {
    return textField.getText();
  }

  @Override
  public int getTableModelColumns() {
    return basicTableModel.getColumnCount();
  }

  @Override
  public void refresh() {
    for (Map.Entry<Coord, Content> e : model.getAllCells().entrySet()) {
      basicTableModel.setValueAt(e.getValue().toString(), e.getKey().row - 1,
          e.getKey().col - 1);
    }
    dataTable.repaint();
  }

  @Override
  public void updateText(String content) {
    textField.setText(content);
  }

  @Override
  public int getRowAtPoint(Point point) {
    return dataTable.rowAtPoint(point);
  }

  @Override
  public int getColumnAtPoint(Point point) {
    return dataTable.columnAtPoint(point);
  }

  @Override
  public String getRawValueAt(int col, int row) {
    return basicTableModel.getRawValue(row, col);
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
    dataTable.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        Point point = e.getPoint();
        int r = getRowAtPoint(point);
        int c = getColumnAtPoint(point);
        features.displayCellContents(c, r);
      }
    });
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
  }
}
