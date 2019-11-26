package edu.cs3500.spreadsheets.view;

import javax.swing.table.AbstractTableModel;

import edu.cs3500.spreadsheets.model.WorksheetModel;
import edu.cs3500.spreadsheets.model.cell.Cell;

/**
 * Represents a custom model for a JTable so that it can interact with our WorksheetModel.
 */
public class BasicTableModel extends AbstractTableModel {

  private WorksheetModel<Cell> model;
  private int row;
  private int col;

  /**
   * Constructor only takes in a WorksheetModel.
   */
  BasicTableModel(WorksheetModel<Cell> model) {
    if (model == null) {
      throw new IllegalArgumentException("tried to pass in null model");
    }
    this.model = model;
    this.row = this.model.getMaxRow();
    this.col = this.model.getMaxCol() + 1;
  }

  @Override
  public int getRowCount() {
    return this.row;
  }

  @Override
  public int getColumnCount() {
    return this.col;
  }

  /**
   * Sets the number of rows in the table model to the given integer.
   *
   * @param r the new row count.
   */
  public void setRow(int r) {
    this.row = r;
  }

  /**
   * Sets the number of columns in the table model to the given integer.
   *
   * @param c the new column count.
   */
  public void setCol(int c) {
    this.col = c;
  }

  @Override
  public Object getValueAt(int rowIndex, int columnIndex) {
    return this.model.displayCell(columnIndex + 1, rowIndex + 1);
  }

  String getRawValue(int rowIndex, int columnIndex) {
    return this.model.getRawContents(columnIndex + 1, rowIndex + 1);
  }

  @Override
  public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
    String str = (String) aValue;
    if (str.contains("=")) {
      str = str.substring(1);
      this.model.addCell(columnIndex + 1, rowIndex + 1, str);
    } else if (str.equals("")) {
      // do nothing
    } else {
      this.model.addCell(columnIndex + 1, rowIndex + 1, str);
    }
  }
}