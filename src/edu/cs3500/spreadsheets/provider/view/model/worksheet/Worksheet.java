package edu.cs3500.spreadsheets.provider.view.model.worksheet;

import java.util.ArrayList;

import edu.cs3500.spreadsheets.provider.view.model.cell.ICell;

/**
 * The Parent interface for all WorkSheets.
 */
public interface Worksheet {
  /**
   * Getter for the list of all the cells in thw WorkSheet.
   *
   * @return the list of cells of the WorkSheet
   */
  ArrayList<ICell> getCellList();

  /**
   * fills in the sheet with empty cells.
   *
   * @param maxCol largest col of worksheet
   * @param maxRow largest row of the worksheet
   */
  void fillInSheet(int maxCol, int maxRow);

  /**
   * Getter for the Largest Col of the Worksheet so far.
   *
   * @return the largest col number of the worksheet
   */
  int getLargestCol();

  /**
   * Getter for the largest row of the worksheet so far.
   *
   * @return the largest row number of the worksheet.
   */
  int getLargestRow();

  /**
   * Gets a specific card a row and column of the Worksheet.
   *
   * @param row The Row of the cell in question
   * @param col The Columns of the cell in question
   * @return the Cell at that Specific cordinents in the SpreadSheet
   * @throws IllegalArgumentException if specific cell does not exist;
   */
  ICell getCellAt(int col, int row);

  /**
   * evulates the given Cell.
   *
   * @param cellEval The sting of the thing to be evluated
   * @return The final string value
   */
  String evaluate(String cellEval);

  /**
   * Allows for the changing of a speific cell.
   *
   * @param col      The Coulmn of the Coord of the Cell
   * @param row      The Row of the Coord of the cell
   * @param contents The new contents of that Cell
   */
  void changeCell(int col, int row, String contents);

  /**
   * Allows for the creation of a new cell for the worksheet.
   *
   * @param col      the new of the coord
   * @param row      the row of the coord
   * @param contents the contents of the cell
   */
  void addCell(int col, int row, String contents);

  /**
   * Compares to worksheets to see if they are equal.
   *
   * @param ws The other workshet that is being compared to this
   * @return true if they are equal to eachother
   */
  boolean isEquals(Worksheet ws);
}
