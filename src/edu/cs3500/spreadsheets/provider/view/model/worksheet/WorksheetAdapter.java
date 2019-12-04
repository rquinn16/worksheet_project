package edu.cs3500.spreadsheets.provider.view.model.worksheet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.WorksheetModel;
import edu.cs3500.spreadsheets.model.cell.Blank;
import edu.cs3500.spreadsheets.model.cell.Content;
import edu.cs3500.spreadsheets.model.cell.Cell;
import edu.cs3500.spreadsheets.provider.view.model.cell.ICell;
import edu.cs3500.spreadsheets.provider.view.model.cell.CellAdapter;

/**
 * Adapts the provided Worksheet interface to our model implementation.
 */
public class WorksheetAdapter implements Worksheet {
  private final WorksheetModel<Cell> basicWorksheet;

  public WorksheetAdapter(WorksheetModel<Cell> basicWorksheet) {
    this.basicWorksheet = basicWorksheet;
  }

  @Override
  public ArrayList<ICell> getCellList() {
    HashMap<Coord, Content> cells = basicWorksheet.getAllCells();
    ArrayList<ICell> toAdd = new ArrayList<>();
    for (Map.Entry<Coord, Content> cell : cells.entrySet()) {
      Cell c = new Cell(cell.getValue(), cell.getKey());
      toAdd.add(new CellAdapter(c));
    }
    return toAdd;
  }

  @Override
  public void fillInSheet(int maxCol, int maxRow) {
    // this method is not used so we do not implement it.
  }

  @Override
  public int getLargestCol() {
    return this.basicWorksheet.getMaxCol();
  }

  @Override
  public int getLargestRow() {
    return this.basicWorksheet.getMaxRow();
  }

  @Override
  public ICell getCellAt(int col, int row) {
    Coord coord = new Coord(col, row);
    HashMap<Coord, Content> cells = basicWorksheet.getAllCells();
    for (Map.Entry<Coord, Content> cell : cells.entrySet()) {
      if (coord.equals(cell.getKey())) {
        Cell cell1 = new Cell(cell.getValue(), coord);
        return new CellAdapter(cell1);
      }
    }
    return new CellAdapter(new Cell(new Blank(), coord));
  }

  @Override
  public String evaluate(String cellEval) {
    ArrayList<String> coords = parseSingularReference(cellEval);
    int col = Coord.colNameToIndex(coords.get(0));
    int row = Integer.parseInt(coords.get(1));
    return this.basicWorksheet.displayCell(col, row);
  }

  private ArrayList<String> parseSingularReference(String s) {
    boolean atLetters = true;
    StringBuilder col = new StringBuilder();
    StringBuilder row = new StringBuilder();
    for (int i = 0; i < s.length(); i++) {
      if (Character.isDigit(s.charAt(i))) {
        atLetters = false;
      }
      if (atLetters) {
        col.append(s.charAt(i));
      } else {
        row.append(s.charAt(i));
      }
    }
    return new ArrayList<>(Arrays.asList(col.toString(), row.toString()));
  }

  @Override
  public void changeCell(int col, int row, String contents) {
    basicWorksheet.addCell(col, row, contents);
  }

  @Override
  public void addCell(int col, int row, String contents) {
    basicWorksheet.addCell(col + 1, row + 1, contents);
  }

  @Override
  public boolean isEquals(Worksheet ws) {
    // This method also seems to not unused, so we are not going to implement it.
    return false;
  }
}