package edu.cs3500.spreadsheets.model.cell;

import edu.cs3500.spreadsheets.model.Coord;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Represents a cell that holds some information content and has a position.
 */
public class Cell {
  private Content content;
  private Coord pos;

  /**
   * Constructs a cell.
   * @param content The content the cell contains.
   * @param pos The position of the cell.
   */
  public Cell(Content content, Coord pos) {
    this.content = content;
    this.pos = pos;
  }

  /**
   * Determines if this cell is valid. A cell is not valid if its contents contain a
   * self reference.
   * @param fromSheet The cells from the worksheet.
   * @return True if the cell is valid, false otherwise.
   */
  public boolean isValid(ArrayList<Cell> fromSheet) {
    return this.content.isValid(this, fromSheet, new ArrayList<>());
  }

  boolean isValidHelp(Cell cell, ArrayList<Cell> fromSheet, ArrayList<Cell> acc) {
    return this.content.isValid(cell, fromSheet, acc);
  }

  public Coord getPos() {
    int row = this.pos.row;
    int col = this.pos.col;
    return new Coord(col, row);
  }

  /**
   * Gets a copy of this cell's content.
   * @return A copy of this cell's content.
   */
  public Content getContent() {
    return this.content.getContent();
  }

  public String toEvaluatedString() {
    return this.content.toEvaluatedString();
  }

  @Override
  public String toString() {
    return this.content.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Cell cell = (Cell) o;
    return this.content.equals(cell.content) && this.pos.equals(cell.pos);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.content, this.pos);
  }
}
