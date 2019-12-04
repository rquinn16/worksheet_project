package edu.cs3500.spreadsheets.provider.view.model.cell;

import java.util.ArrayList;

import edu.cs3500.spreadsheets.model.cell.Cell;
import edu.cs3500.spreadsheets.provider.view.model.worksheet.Worksheet;

/**
 * Represents an adapted implementation of the cell class.
 */
public class CellAdapter implements ICell {

  private edu.cs3500.spreadsheets.model.cell.Cell c;

  /**
   * Constructor for the cell.
   * @param c The cell being passed in.
   */
  public CellAdapter(Cell c) {
    this.c = c;
  }

  @Override
  public String evaluateCell(ArrayList<Cell> cell, Worksheet ws) {
    return c.toEvaluatedString();
  }

  @Override
  public void changeCell(String contents) {
    //
  }

  @Override
  public String getContent() {
    return c.toString();
  }
}
