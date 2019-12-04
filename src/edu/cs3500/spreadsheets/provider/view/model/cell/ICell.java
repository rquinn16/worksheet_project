package edu.cs3500.spreadsheets.provider.view.model.cell;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.cell.Cell;
import edu.cs3500.spreadsheets.provider.view.model.worksheet.Worksheet;
import java.util.ArrayList;

/**
 * The parent interface of all Cells.
 */
public interface ICell {

  /**
   * Evaluates the contents of the Cell.
   *
   * @param cell The Cells that have already been referenced.
   * @return The value of the Cell.
   */
  String evaluateCell(ArrayList<Cell> cell, Worksheet ws);

  /**
   * Allows for the changing of the value of a Cell.
   *
   * @param contents the new contents of the cell.
   */
  void changeCell(String contents);


  /**
   * A Getter for the String content of the cell.
   * @return the string contents of the cell
   */
  String getContent();

  /**
   * A Getter for the Coord object of the Specific Cell.
   *
   */
  Coord getCoord();
}
