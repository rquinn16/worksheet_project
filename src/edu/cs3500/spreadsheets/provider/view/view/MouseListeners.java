package edu.cs3500.spreadsheets.provider.view.view;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Mouse Controller that Waits to the mouse is pressed than display the cell into the JTextField of
 * the main frame.
 */
public class MouseListeners implements MouseListener {
  public ViewEvaluatedExpanded view;
  protected TableController table;

  /**
   * Controller that waits for the table to be clicked my the mouse.
   *
   * @param view the given view of what needs to be watched.
   */
  public MouseListeners(ViewEvaluatedExpanded view) {
    this.view = view;
    this.table = new TableController(view);
    view.orgEvalatedView.table.addMouseListener(this);
  }

  /**
   * Invoked when the mouse button has been clicked (pressed and released) on a component.
   *
   * @param e the event to be processed
   */
  @Override
  public void mouseClicked(MouseEvent e) {
    final int row = this.view.orgEvalatedView.table.getSelectedRow();
    final int column = this.view.orgEvalatedView.table.getSelectedColumn();
    final String tempStr = (String) this.table.getValueAt(row + 1,
            column + 1);
    this.view.jtf.setText(tempStr);
  }

  /**
   * Invoked when a mouse button has been pressed on a component.
   *
   * @param e the event to be processed
   */
  @Override
  public void mousePressed(MouseEvent e) {
    //Dont need it
  }

  /**
   * Invoked when a mouse button has been released on a component.
   *
   * @param e the event to be processed
   */
  @Override
  public void mouseReleased(MouseEvent e) {
    //Dont need it
  }

  /**
   * Invoked when the mouse enters a component.
   *
   * @param e the event to be processed
   */
  @Override
  public void mouseEntered(MouseEvent e) {
    //Dont need it
  }

  /**
   * Invoked when the mouse exits a component.
   *
   * @param e the event to be processed
   */
  @Override
  public void mouseExited(MouseEvent e) {
    //Dont need this
  }
}
