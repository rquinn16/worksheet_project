package edu.cs3500.spreadsheets.controller;

import edu.cs3500.spreadsheets.model.WorksheetModel;
import edu.cs3500.spreadsheets.model.cell.Cell;
import edu.cs3500.spreadsheets.view.EditableWorksheetGraphicalView;
import edu.cs3500.spreadsheets.view.EditableWorksheetView;

/**
 * Represents a simple controller for our Worksheet class. Takes in a WorksheetModel and creates a
 * WorksheetGraphicalView with it.
 */
public class EditableWorksheetController implements Features {
  private WorksheetModel<Cell> model;
  private EditableWorksheetView view;

  /**
   * Constructor for a worksheet controller. Takes in only a WorksheetModel and constructs the
   * WorksheetGraphicalView with it.
   *
   * @param m The model.
   */
  public EditableWorksheetController(WorksheetModel<Cell> m) {
    this.model = m;
    this.view = new EditableWorksheetGraphicalView(model);
  }

  /**
   * A constructor that can specify the input view.
   *
   * @param m    The model.
   * @param view The view.
   */
  public EditableWorksheetController(WorksheetModel<Cell> m, EditableWorksheetView view) {
    this.model = m;
    this.view = view;
  }

  @Override
  public void start() {
    this.view.addFeatures(this);
    this.view.render();
  }

  @Override
  public void displayCellContents(int col, int row) {
    int cols = view.getTableModelColumns();
    if (col >= cols) {

      String str = view.getRawValueAt(col + 1, row);
      parserChecker(str);
    } else {
      String str = view.getRawValueAt(col, row);
      parserChecker(str);
    }
    view.refresh();
  }

  @Override
  public void updateCellContents() {
    int r = view.getSelectedRow();
    int c = view.getSelectedColumn();
    String str = view.getCurrentText();
    parserChecker(str);
    try {
      int cols = view.getTableModelColumns();
      if (c >= cols) {
        view.updateCell(str, c + 1, r);
        view.refresh();
      } else {
        view.updateCell(str, c, r);
        view.refresh();
      }
    } catch (Exception ex) {
      view.updateText(ex.getMessage());
    }
  }

  private void parserChecker(String str) {
    if (str.length() >= 2 && str.substring(str.length() - 2).equals("))")) {
      str = str.substring(0, str.length() - 1);
      str = str.replaceAll("\\)", "");
      str = str + ")";
    }
    view.updateText(str);
  }
}