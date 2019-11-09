package edu.cs3500.spreadsheets.view;

import java.awt.*;
import java.io.IOException;

import javax.swing.*;

/**
 * Represents the graphical view of a Worksheet.
 */
public class WorksheetGraphicalView extends JFrame implements WorksheetView {

  private static int LENGTH = 30;
  private static int HEIGHT = 20;
  private JTable j;

  /**
   * Constructor for the WorksheetGraphicalView.
   */
  public WorksheetGraphicalView() {
    this.j = new JTable();
    //TODO: what do we do with the new JTable?
    JScrollPane scroll = new JScrollPane(this.j);
    //TODO: are we sure we want to use a JScrollPane?
    this.add(scroll);
    this.setSize(2000, 2000);
    this.setVisible(true);
  }

  @Override
  public void render() throws IOException {

  }

  @Override
  public void paintComponents(Graphics g) {
    super.paintComponents(g);
  }
}
