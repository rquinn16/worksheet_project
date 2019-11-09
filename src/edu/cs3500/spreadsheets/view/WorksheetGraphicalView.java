package edu.cs3500.spreadsheets.view;

import java.awt.*;
import java.io.IOException;

import javax.swing.*;

import edu.cs3500.spreadsheets.model.WorksheetModel;
import edu.cs3500.spreadsheets.model.cell.Cell;

/**
 * Represents the graphical view of a Worksheet.
 */
public class WorksheetGraphicalView extends JFrame implements WorksheetView {

  private static int LENGTH = 1000;
  private static int HEIGHT = 700;
  private WorksheetModel<Cell> model;
  private JPanel panel;d

  /**
   * Constructor for the WorksheetGraphicalView.
   * It makes a default, empty table.
   */
  public WorksheetGraphicalView(WorksheetModel<Cell> model) {
    JTable table = new JTable(25, 25);
    //TODO: what do we do with the new JTable?

    JScrollPane scroll = new JScrollPane(table);
    //TODO: are we sure we want to use a JScrollPane?

    this.add(scroll);
    this.setSize(LENGTH, HEIGHT);
    this.setVisible(true);

    this.model = model;
  }

  @Override
  public void render() throws IOException {

  }

  @Override
  public void paintComponents(Graphics g) {
    super.paintComponents(g);
  }

//  public static void main(String[] args) {
//    WorksheetGraphicalView view = new WorksheetGraphicalView();
//    view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//  }
}
