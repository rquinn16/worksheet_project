package edu.cs3500.spreadsheets.controller;

import edu.cs3500.spreadsheets.model.WorksheetModel;
import edu.cs3500.spreadsheets.model.cell.Cell;
import edu.cs3500.spreadsheets.view.EditableWorksheetView;
import edu.cs3500.spreadsheets.view.EditableWorksheetGraphicalView;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Represents a simple controller for our Worksheet class.
 * Takes in a WorksheetModel<Cell> and creates a WorksheetGraphicalView with it.
 */
public class EditableWorksheetController extends MouseAdapter implements WorksheetController {

  private WorksheetModel<Cell> model;
  private EditableWorksheetView view;

  /**
   * Constructor for a worksheet controller.
   * Takes in only a WorksheetModel and constructs
   * the WorksheetGraphicalView with it.
   * @param m The model.
   */
  public EditableWorksheetController(WorksheetModel<Cell> m) {
    this.model = m;
    this.view = new EditableWorksheetGraphicalView(model);
  }

  /**
   * A constructor for testing purposes: this allows for us to see that the controller is actually
   * affecting aspects of the view.
   * @param m The model.
   * @param view The view.
   */
  public EditableWorksheetController(WorksheetModel<Cell> m, EditableWorksheetView view) {
    this.model = m;
    this.view = view;
  }

  @Override
  public void start() {
    this.view.setActionListener(this);
    this.view.setMouseListener(this);
    this.view.render();
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    Point point = e.getPoint();
    int r = view.getRowAtPoint(point);
    int c = view.getColumnAtPoint(point);
    int cols = view.getTableModelColumns();
    if (c >= cols) {
      view.updateText(view.getRawValueAt(c + 1, r));
    } else {
      view.updateText(view.getRawValueAt(c, r));
    }
    view.refresh();
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    int r = view.getSelectedRow();
    int c = view.getSelectedColumn();
    String str = view.getCurrentText();
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
}
