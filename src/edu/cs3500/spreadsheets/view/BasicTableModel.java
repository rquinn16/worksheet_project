package edu.cs3500.spreadsheets.view;

import edu.cs3500.spreadsheets.model.WorksheetModel;
import edu.cs3500.spreadsheets.model.cell.Cell;
import javax.swing.table.AbstractTableModel;

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
  public BasicTableModel(WorksheetModel<Cell> model) {
    this.model = model;
    this.row = this.model.getMaxRow();
    this.col = this.model.getMaxCol();
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
   * @param r the new row count.
   */
  public void setRow(int r) {
    this.row = r;
  }

  /**
   * Sets the number of columns in the table model to the given integer.
   * @param c the new column count.
   */
  public void setCol(int c) {
    this.col = c;
  }

  @Override
  public Object getValueAt(int rowIndex, int columnIndex) {
    return this.model.displayCell(columnIndex + 1, rowIndex + 1);
  }
}