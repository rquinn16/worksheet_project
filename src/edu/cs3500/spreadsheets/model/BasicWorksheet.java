package edu.cs3500.spreadsheets.model;

import edu.cs3500.spreadsheets.model.cell.Blank;
import edu.cs3500.spreadsheets.model.cell.Cell;
import edu.cs3500.spreadsheets.model.cell.Content;
import edu.cs3500.spreadsheets.sexp.CreateContent;
import edu.cs3500.spreadsheets.sexp.Parser;
import edu.cs3500.spreadsheets.sexp.Sexp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Class representing a basic Worksheet.
 */
public class BasicWorksheet implements WorksheetModel<Cell> {
  private ArrayList<Cell> grid;
  private HashMap<Coord, Content> toBecomeGrid;

  public BasicWorksheet() {
    this.grid = new ArrayList<>();
    this.toBecomeGrid = new HashMap<>();
  }

  public BasicWorksheet(ArrayList<Cell> cells) {
    this.grid = cells;
    this.toBecomeGrid = new HashMap<>();
  }

  @Override
  public String displayCell(int col, int row) {
    Coord key = new Coord(col, row);
    Cell c = new Cell(this.toBecomeGrid.getOrDefault(key, new Blank()).simplify(
            grid, new ArrayList<>()), key);
    if (c.isValid(this.grid)) {
      return c.toString();
    } else {
      return "#REF!";
    }
  }

  @Override
  public String getRawContents(int col, int row) {
    return null;
  }

  @Override
  public void addCell(int col, int row, String contents) {
    Sexp sexp = Parser.parse(contents);
    // CREATE CONTENT THROWS AN EXCEPTION
    Content content = sexp.accept(new CreateContent(this.toBecomeGrid));
    Coord coord = new Coord(col, row);
    Cell c = new Cell(content, new Coord(col, row));
    this.toBecomeGrid.put(coord, content);
    this.grid.add(c);
  }

  @Override
  public HashMap<Coord, Content> getAllCells() {
    return new HashMap<>(this.toBecomeGrid);
  }

  @Override
  public int getMaxCol() {
    int max = 0;
    for (Map.Entry<Coord, Content> e : this.getAllCells().entrySet()) {
      if (e.getKey().col > max) {
        max = e.getKey().col;
      }
    }
    return max;
  }

  @Override
  public int getMaxRow() {
    int max = 0;
    for (Map.Entry<Coord, Content> e : this.getAllCells().entrySet()) {
      if (e.getKey().row > max) {
        max = e.getKey().row;
      }
    }
    return max;
  }
}
