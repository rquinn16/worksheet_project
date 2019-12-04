package edu.cs3500.spreadsheets.provider.view.view;

import edu.cs3500.spreadsheets.model.Coord;
import javax.swing.JTable;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 * Control that is called when data from the model wants to be change in the table.
 */
public class TableController implements TableModel {
  public ViewEvaluatedExpanded view;
  public JTable table;

  /**
   * Constructor the build the table that is to be changed with the model.
   *
   * @param view The certain view of the moment
   */
  public TableController(ViewEvaluatedExpanded view) {
    this.view = view;
    this.table = view.orgEvalatedView.table;
  }

  /**
   * Returns the number of rows in the model. A
   * <code>JTable</code> uses this method to determine how many rows it
   * should display.  This method should be quick, as it is called frequently during rendering.
   *
   * @return the number of rows in the model
   * @see #getColumnCount
   */
  @Override
  public int getRowCount() {
    return this.view.ws.getLargestRow();
  }

  /**
   * Returns the number of columns in the model. A
   * <code>JTable</code> uses this method to determine how many columns it
   * should create and display by default.
   *
   * @return the number of columns in the model
   * @see #getRowCount
   */
  @Override
  public int getColumnCount() {
    return this.view.ws.getLargestCol();
  }

  /**
   * Returns the name of the column at <code>columnIndex</code>.  This is used to initialize the
   * table's column header name.  Note: this name does not need to be unique; two columns in a table
   * can have the same name.
   *
   * @param columnIndex the index of the column
   * @return the name of the column
   */
  @Override
  public String getColumnName(int columnIndex) {
    return Coord.colIndexToName(columnIndex + 1);
  }

  /**
   * Returns the most specific superclass for all the cell values in the column.  This is used by
   * the <code>JTable</code> to set up a default renderer and editor for the column.
   *
   * @param columnIndex the index of the column
   * @return the common ancestor class of the object values in the model.
   */
  @Override
  public Class<?> getColumnClass(int columnIndex) {
    return null;
  }

  /**
   * Returns true if the cell at <code>rowIndex</code> and
   * <code>columnIndex</code>
   * is editable.  Otherwise, <code>setValueAt</code> on the cell will not change the value of that
   * cell.
   *
   * @param rowIndex    the row whose value to be queried
   * @param columnIndex the column whose value to be queried
   * @return true if the cell is editable
   * @see #setValueAt
   */
  @Override
  public boolean isCellEditable(int rowIndex, int columnIndex) {
    return true;
  }

  /**
   * Returns the value for the cell at <code>columnIndex</code> and
   * <code>rowIndex</code>.
   *
   * @param rowIndex    the row whose value is to be queried
   * @param columnIndex the column whose value is to be queried
   * @return the value Object at the specified cell
   */
  @Override
  public Object getValueAt(int rowIndex, int columnIndex) {
    return this.view.ws.getCellAt(columnIndex, rowIndex).getContent();
  }

  /**
   * Sets the value in the cell at <code>columnIndex</code> and
   * <code>rowIndex</code> to <code>aValue</code>.
   *
   * @param aValue      the new value
   * @param rowIndex    the row whose value is to be changed
   * @param columnIndex the column whose value is to be changed
   */
  @Override
  public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
    if (rowIndex <= 0 || columnIndex <= 0) {
      //error when Double clicked
    } else {
      this.view.ws.getCellAt(columnIndex, rowIndex).changeCell((String) aValue);
    }
  }


  /**
   * Adds a listener to the list that is notified each time a change to the data model occurs.
   *
   * @param l the TableModelListener
   */
  @Override
  public void addTableModelListener(TableModelListener l) {
    //not needed
  }

  /**
   * Removes a listener from the list that is notified each time a change to the data model occurs.
   *
   * @param l the TableModelListener
   */
  @Override
  public void removeTableModelListener(TableModelListener l) {
    //not needed
  }
}
