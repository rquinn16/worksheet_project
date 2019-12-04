package edu.cs3500.spreadsheets.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.cs3500.spreadsheets.model.cell.Blank;
import edu.cs3500.spreadsheets.model.cell.Cell;
import edu.cs3500.spreadsheets.model.cell.Content;
import edu.cs3500.spreadsheets.sexp.CreateContent;
import edu.cs3500.spreadsheets.sexp.Parser;
import edu.cs3500.spreadsheets.sexp.Sexp;

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

  @Override
  public String displayCell(int col, int row) {
    Coord key = new Coord(col, row);
    Cell c = new Cell(this.toBecomeGrid.getOrDefault(key, new Blank()).simplify(
            grid, new ArrayList<>()), key);
    if (c.isValid(this.grid)) {
      return c.toEvaluatedString();
    } else {
      return "#REF!";
    }
  }

  @Override
  public String getRawContents(int col, int row) {
    Coord key = new Coord(col, row);
    Cell c = new Cell(this.toBecomeGrid.getOrDefault(key, new Blank()), key);
    return c.toString();
  }

  @Override
  public void addCell(int col, int row, String contents) {
    Sexp sexp;
    if (contents.length() >= 2 && contents.substring(contents.length() - 2).equals("))")) {
      String str = contents.substring(0, contents.length() - 1);
      str = str.replaceAll("\\)", "");
      str = str + ")";
      sexp = Parser.parse(str);
    } else {
      sexp = Parser.parse(contents);
    }
    Content content = sexp.accept(new CreateContent(this.toBecomeGrid));
    Coord coord = new Coord(col, row);
    Cell c = new Cell(content, new Coord(col, row));
    if (!coordExist(coord)) {
      this.grid.add(c);
    } else {
      this.update(c);
    }
    this.toBecomeGrid.put(coord, content);
  }

  private void update(Cell c) {
    int toRemove = 0;
    for (int i = 0; i < this.grid.size(); i++) {
      if (this.grid.get(i).getPos().equals(c.getPos())) {
        toRemove = i;
      }
    }
    this.grid.remove(toRemove);
    this.grid.add(c);
  }


  private boolean coordExist(Coord c) {
    for (Cell cell : this.grid) {
      if (cell.getPos().equals(c)) {
        return true;
      }
    }
    return false;
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
