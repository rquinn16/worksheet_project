package edu.cs3500.spreadsheets.view;

import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.WorksheetModel;
import edu.cs3500.spreadsheets.model.cell.Content;

/**
 * A view implementation that writes the cells from the model to a text file.
 */
public class WorksheetTextualView implements WorksheetView {
  private final WorksheetModel<?> model;
  private final BufferedWriter appendable;

  /**
   * Constructs an instance of this view.
   * @param model The model that this view will extract information from.
   * @param filepath Where in the file system the outputted text file will be located.
   * @throws IOException If something goes wrong with the filepath.
   */
  public WorksheetTextualView(WorksheetModel<?> model, String filepath) throws IOException {
    if (model == null) {
      throw new IllegalArgumentException("tried to pass in null");
    }
    this.model = model;
    this.appendable = new BufferedWriter(new FileWriter(filepath));
  }

  @Override
  public void render() {
    try {
      for (Map.Entry<Coord, Content> e : this.model.getAllCells().entrySet()) {
        Coord coord = e.getKey();
        Content content = e.getValue();
        appendable.append(coord.toString()).append(" ").append(content.toString()).append("\n");
      }
      appendable.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
