package edu.cs3500.spreadsheets.model;

import edu.cs3500.spreadsheets.model.WorksheetReader.WorksheetBuilder;
import edu.cs3500.spreadsheets.model.cell.Cell;

/**
 * The builder for our worksheet model.
 */
public class Worksheet implements WorksheetBuilder<WorksheetModel<Cell>> {
  private WorksheetModel<Cell> w;

  /**
   * Constructs the builder.
   */
  public Worksheet() {
    this.w = new BasicWorksheet();
  }

  @Override
  public WorksheetBuilder<WorksheetModel<Cell>> createCell(int col, int row, String contents) {
    if (contents.charAt(0) == '=') {
      this.w.addCell(col, row, contents.substring(1));
    } else {
      this.w.addCell(col, row, contents);
    }
    return this;
  }

  @Override
  public WorksheetModel<Cell> createWorksheet() {
    return this.w;
  }
}