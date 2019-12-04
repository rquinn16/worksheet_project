package edu.cs3500.spreadsheets.provider.view.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

/**
 * Button Controller that waits for its button to be pressed that either re-evaluates the table or
 * leaves it be.
 */
public class ButtonListeners implements ActionListener {
  private ViewEvaluatedExpanded view;
  private JButton button;
  private TableController table;

  /**
   * Waits for when the button is pressed and then does what action its suppose to do.
   *
   * @param view   The view that the button controller is controlling.
   * @param button the specific button that the buttonListener is listening to.
   */
  public ButtonListeners(ViewEvaluatedExpanded view, JButton button) {
    this.view = view;
    this.button = button;
    this.button.addActionListener(this);
    this.table = new TableController(view);
  }

  /**
   * Invoked when an action occurs.
   *
   * @param e the event to be processed
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    JButton button = (JButton) (e.getSource());
    System.out.println(this.view.orgEvalatedView.table.getSelectedRow());
    if (button.getText().equals("yes")) {
      this.table.setValueAt(this.view.jtf.getText()
              , this.view.orgEvalatedView.table.getSelectedRow() + 1
              , this.view.orgEvalatedView.table.getSelectedColumn() + 1);
      this.view.orgEvalatedView.jf.setVisible(false);
      ViewEvaluatedExpanded vee = new ViewEvaluatedExpanded(this.view.ws);
      vee.display();
    } else {
      this.view.orgEvalatedView.jf.setVisible(false);
      ViewEvaluatedExpanded vee = new ViewEvaluatedExpanded(this.view.ws);
      vee.display();
    }
  }
}
