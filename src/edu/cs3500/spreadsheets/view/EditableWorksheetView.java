package edu.cs3500.spreadsheets.view;

import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

/**
 * Represents a view of the spreadsheet that the allows for user interaction.
 */
public interface EditableWorksheetView extends WorksheetView {

  /**
   * Updates the cell at the given row and column.
   * @param col The column of the cell.
   * @param row The row of the cell.
   */
  void updateCell(String content, int col, int row);

  /**
   * Sets the mouse listener that the view will use for interaction purposes.
   * @param mouseListener The MouseListener object to be used.
   */
  void setMouseListener(MouseListener mouseListener);

  /**
   * Sets the action listener that the view will use for interaction purposes.
   * @param actionListener The ActionListener object to be used.
   */
  void setActionListener(ActionListener actionListener);

  /**
   * Gets the currently selected column in the view.
   * @return The column index selected.
   */
  int getSelectedColumn();

  /**
   * Gets the currently selected row in the view.
   * @return The row index selected.
   */
  int getSelectedRow();

  /**
   * Gets what String is currently entered that user may use to update a cell
   * or view its raw contents.
   * @return The String that is currently entered.
   */
  String getCurrentText();

  /**
   * Gets the number of columns in the table model if the view has one.
   * @return The number of columns in the table model.
   */
  int getTableModelColumns();

  /**
   * Refreshes the information in the table to dynamically update cell references.
   */
  void refresh();

  /**
   * Updates the text in the text field the user sees.
   */
  void updateText(String content);

  /**
   * Gets the column where the mouse currently is.
   * @param point Where the mouse is.
   * @return The column index.
   */
  int getRowAtPoint(Point point);

  /**
   * Gets the row where the mouse currently is.
   * @param point Where the mouse is.
   * @return The row index.
   */
  int getColumnAtPoint(Point point);

  /**
   * Gets the raw value of a cell.
   * @param col The column of the cell.
   * @param row The row of the cell.
   * @return The string representing the raw value in the cell.
   */
  String getRawValueAt(int col, int row);
}
