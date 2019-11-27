package edu.cs3500.spreadsheets.controller;

import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import javax.swing.Action;

/**
 * Represents a controller interface for our controllers.
 */
public interface WorksheetController extends ActionListener, MouseListener {

  /**
   * Runs the program and displays the worksheet.
   */
  void start();
}
