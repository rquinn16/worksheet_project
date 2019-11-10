package edu.cs3500.spreadsheets.view;

import edu.cs3500.spreadsheets.model.WorksheetModel;
import edu.cs3500.spreadsheets.model.cell.Cell;
import javax.swing.table.AbstractTableModel;

/**
 * Represents a custom model for a JTable so that it can interact with our WorksheetModel.
 */
public class BasicTableModel extends AbstractTableModel {

  private WorksheetModel<Cell> model;

  /**
   * Constructor only takes in a WorksheetModel.
   */
  public BasicTableModel(WorksheetModel<Cell> model) {
    this.model = model;
  }

  @Override
  public int getRowCount() {
    return this.model.getMaxRow();
  }

  @Override
  public int getColumnCount() {
    return this.model.getMaxCol();
  }

  @Override
  public Object getValueAt(int rowIndex, int columnIndex) {
    return this.model.displayCell(columnIndex + 1, rowIndex + 1);
  }
}
