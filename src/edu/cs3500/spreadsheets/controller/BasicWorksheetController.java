package edu.cs3500.spreadsheets.controller;

import edu.cs3500.spreadsheets.model.WorksheetModel;
import edu.cs3500.spreadsheets.model.cell.Cell;
import edu.cs3500.spreadsheets.view.BasicTableModel;
import edu.cs3500.spreadsheets.view.WorksheetGraphicalViewDisplayRaw;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 * Represents a simple controller for our Worksheet class.
 * Takes in a WorksheetModel<Cell> and creates a WorksheetGraphicalView with it.
 */
public class BasicWorksheetController implements WorksheetController {

  private WorksheetModel<Cell> model;

  /**
   * Constructor for a worksheet controller.
   * Takes in only a WorksheetModel and constructs
   * the WorksheetGraphicalView with it.
   * @param m The model.
   */
  public BasicWorksheetController(WorksheetModel<Cell> m) {
    this.model = m;
  }

  @Override
  public void start() {
    WorksheetGraphicalViewDisplayRaw view = new WorksheetGraphicalViewDisplayRaw(this.model);
    view.render();
  }

  /**
   * Displays the given cell in the model.
   * @param row The row of the cell.
   * @param col The column of the cell.
   */
  public void displayCell(int row, int col) {
    this.model.displayCell(row, col);
  }

  /**
   * Adds a cell to the model.
   * @param row The row of the cell.
   * @param col The column of the cell.
   * @param contents The contents that will be put into the cell.
   */
  public void addCell(int row, int col, String contents) {
    this.model.addCell(row, col, contents);
  }

  public void updateValue(BasicTableModel basic, JTable table, JTextField txt) {
    basic.setValueAt(txt.getText(), table.getSelectedRow(), table.getSelectedColumn());
  }
}
