package edu.cs3500.spreadsheets.view;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.WorksheetModel;
import edu.cs3500.spreadsheets.model.cell.Content;

public class WorksheetTextualView implements WorksheetView {
  private final WorksheetModel<?> model;
  private final BufferedWriter appendable;

  public WorksheetTextualView(WorksheetModel<?> model) throws IOException {
    this.model = model;
    this.appendable = new BufferedWriter(
            new FileWriter("/Users/ryanquinn/Desktop/sheet.txt"));
  }

  @Override
  public void render() throws IOException {
    for (Map.Entry<Coord, Content> e : this.model.getAllCells().entrySet()) {
      Coord coord = e.getKey();
      Content content = e.getValue();
      appendable.append(coord.toString()).append(" ").append(content.toString()).append("\n");
    }
    appendable.close();
  }
}
