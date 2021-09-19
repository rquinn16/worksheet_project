package edu.cs3500.spreadsheets.provider.view.view;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;

import edu.cs3500.spreadsheets.provider.view.model.worksheet.Worksheet;

/**
 * Adds in new features to the already existing ViewEvualted class.
 */
public class ViewEvaluatedExpanded extends JFrame implements IView {
  public NewViewEvaluated orgEvalatedView;
  public Worksheet ws;
  public JTextField jtf;

  /**
   * Creates a new overlay over the already given viewEvaluated object.
   *
   * @param worksheet The model foe the gui to read and create the interface for.
   */
  public ViewEvaluatedExpanded(Worksheet worksheet) {
    this.orgEvalatedView = new NewViewEvaluated(worksheet);
    this.ws = worksheet;
    JPanel panel = new JPanel();
    this.jtf = new JTextField(20);
    JLabel label = new JLabel("Press Button To evaluate");
    JButton buttonEvaluate = new JButton("yes");
    JButton buttonForget = new JButton("no");
    buttonEvaluate.setFont(new Font(Font.SERIF, Font.BOLD, 10));
    buttonEvaluate.setPreferredSize(new Dimension(50, 50));
    buttonForget.setFont(new Font(Font.SERIF, Font.BOLD, 10));
    buttonForget.setPreferredSize(new Dimension(50, 50));
    panel.add(label);
    panel.add(buttonEvaluate);
    panel.add(buttonForget);
    panel.add(this.jtf);

    new ButtonListeners(this, buttonEvaluate);
    new ButtonListeners(this, buttonForget);
    new MouseListeners(this);

    this.orgEvalatedView.jf.add(panel, 0);

  }

  /**
   * Does the last step need to render what ever edu.cs3500.spreadsheets.view is using it.
   */
  @Override
  public void display() {
    this.orgEvalatedView.display();
  }

}
