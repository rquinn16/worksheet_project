package edu.cs3500.spreadsheets.model.cell;

import java.util.ArrayList;

import edu.cs3500.spreadsheets.model.contentvisitor.ContentVisitor;

/**
 * Represents some type of information.
 */
public interface Content {
  /**
   * Determines if a cell is valid by checking this content for reference to the passed in Cell.
   * @param cell The cell that the original content comes from.
   * @param fromSheet The cells from the worksheet.
   * @param acc The cells that we have already checked for self reference.
   * @return False if this content has a reference to cell, true otherwise.
   */
  boolean isValid(Cell cell, ArrayList<Cell> fromSheet, ArrayList<Cell> acc);

  /**
   * Gets a copy of this content.
   * @return A copy of the content.
   */
  Content getContent();

  /**
   * Gets a simplified version of this content.
   * @param fromSheet The cells from the entire grid from the model.
   * @param forReference Cells to build up so that a reference content
   *                     can store the cells it refers to.
   * @return The simplified content of the cell.
   */
  Content simplify(ArrayList<Cell> fromSheet, ArrayList<Cell> forReference);

  String toEvaluatedString();

  /**
   * Accepts a visitor to perform some function on this Content.
   * @param visitor The function object to be applied.
   * @param <T> The type that the function object will return.
   */
  <T> void accept(ContentVisitor<T> visitor);
}