package edu.cs3500.spreadsheets.controller;

/**
 * Represents a controller interface for our controllers.
 */
public interface Features {

  /**
   * Runs the program and displays the worksheet.
   */
  void start();

  /**
   * Displays the raw value of what is inputted in the cell at the col and row.
   * @param col The col of the cell.
   * @param row The row of the cell.
   */
  void displayCellContents(int col, int row);

  /**
   * Updates the cell to what is inputted into the text field.
   */
  void updateCellContents();
}
