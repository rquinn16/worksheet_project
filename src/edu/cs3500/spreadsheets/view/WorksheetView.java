package edu.cs3500.spreadsheets.view;

/**
 * Represents an interface which has a render method that each implementation will implement to
 * display the model's information in some way.
 */
public interface WorksheetView {
  /**
   * Renders a model in some manner (e.g. as text, or as graphics, etc.).
   */
  void render();
}
