package edu.cs3500.spreadsheets.model;

import java.util.HashMap;

import edu.cs3500.spreadsheets.model.cell.Content;

/**
 * Interface with all of the methods that can be performed on the worksheet model.
 */
public interface WorksheetModel<K> {

  /**
   * Returns the string representation of the cell in this WorksheetModel
   * at the specified coordinates.
   * @param col The column number.
   * @param row The row number.
   * @return The cell at the given coordinates.
   */
  String displayCell(int col, int row);

  String getRawContents(int col, int row);

  /**
   * Adds a a cell to this WorksheetModel at the specified coordinates.
   * The data to be put in this cell is rendered after parsing the String.
   * Replaces a cell if one already exists at the given coordinates.
   * @param col The column number.
   * @param row The row number.
   * @param contents The String to be parsed determining the contents of the cell.
   */
  void addCell(int col, int row, String contents);

  /**
   * Returns a list of every cell in the WorksheetModel.
   * @return A list of all cells in the WorksheetModel.
   */
  HashMap<Coord, Content> getAllCells();

  /**
   * Gets the number of the highest column in this table.
   * @return Integer representing the number of columns in the table.
   */
  int getMaxCol();

  /**
   * Gets the number of the highest row in this table.
   * @return Integer representing the number of rows in the table.
   */
  int getMaxRow();


}
