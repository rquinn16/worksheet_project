package edu.cs3500.spreadsheets.model.cell;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.contentvisitor.ContentVisitor;

/**
 * Represents a reference to a rectangular region of cells on a given Spreadsheet.
 */
public class Reference implements Formula {
  private final Coord topLeft;
  private final Coord bottomRight;
  private final ArrayList<Cell> referenced;

  /**
   * Constructor for the Reference class.
   *
   * @param topLeft     The top left Cell coordinate.
   * @param bottomRight The bottom right Cell coordinate.
   * @param referenced  The Cells referenced by this reference.
   */
  public Reference(Coord topLeft, Coord bottomRight, ArrayList<Cell> referenced) {
    this.topLeft = topLeft;
    this.bottomRight = bottomRight;
    this.referenced = referenced;
  }

  /**
   * Determines if this reference is valid.
   *
   * @return boolean stating if the reference is valid.
   */
  @Override
  public boolean isValid(Cell cell, ArrayList<Cell> fromSheet, ArrayList<Cell> acc) {
    boolean properBounds = topLeft.col <= bottomRight.col && topLeft.row <= bottomRight.row;
    boolean selfReference = this.hasSelfReference(cell, fromSheet, acc);
    return properBounds && !selfReference;
  }

  private boolean hasSelfReference(Cell cell, ArrayList<Cell> fromSheet,
                                   ArrayList<Cell> acc) {
    List<Cell> allCells = this.generateAllCells(fromSheet);
    for (Cell c : allCells) {
      if (c.equals(cell)) {
        return true;
      }
      if (!acc.contains(c)) {
        acc.add(c);
        if (!c.isValidHelp(cell, fromSheet, acc)) {
          return true;
        }
      }
    }
    return false;
  }

  private List<Cell> generateAllCells(ArrayList<Cell> fromSheet) {
    ArrayList<Cell> res = new ArrayList<>();
    for (Cell c : fromSheet) {
      if (cellIncluded(c.getPos())) {
        res.add(c);
      }
    }
    return res;
  }

  private boolean cellIncluded(Coord c) {
    boolean validCols = this.topLeft.col <= c.col && c.col <= this.bottomRight.col;
    boolean validRows = this.topLeft.row <= c.row && c.row <= this.bottomRight.row;
    return validCols && validRows;
  }

  @Override
  public Content getContent() {
    int c1 = topLeft.col;
    int r1 = topLeft.row;
    int c2 = bottomRight.col;
    int r2 = bottomRight.row;
    Coord topLeftCopy = new Coord(c1, r1);
    Coord bottomRightCopy = new Coord(c2, r2);
    return new Reference(topLeftCopy, bottomRightCopy, new ArrayList<>(referenced));
  }

  @Override
  public Content simplify(ArrayList<Cell> fromSheet, ArrayList<Cell> forReference) {
    List<Cell> allCells = this.generateAllCells(fromSheet);
    for (Cell c : allCells) {
      forReference.add(new Cell(c.getContent(), c.getPos()));
    }
    return new Reference(new Coord(this.topLeft.col, this.topLeft.row),
            new Coord(this.bottomRight.col, this.bottomRight.row),
            forReference);
  }

  @Override
  public String toEvaluatedString() {
    if (this.referenced.size() == 1) {
      return this.referenced.get(0).toEvaluatedString();
    } else if (this.referenced.size() > 1) {
      return "#REF!";
    } else {
      return "";
    }
  }

  @Override
  public <T> void accept(ContentVisitor<T> visitor) {
    visitor.visitReference(this);
  }

  /**
   * Gets copies of the references this reference refers to.
   *
   * @return A list of the references in simplified form.
   */
  public ArrayList<Content> getSimplifiedReferences() {
    ArrayList<Content> simplifiedReferences = new ArrayList<>();
    for (Cell c : this.referenced) {
      simplifiedReferences.add(c.getContent());
    }
    return simplifiedReferences;
  }

  @Override
  public String toString() {
    if (this.topLeft.equals(this.bottomRight)) {
      return "=" + this.topLeft.toString();
    } else {
      return "=" + this.topLeft.toString() + ":" + this.bottomRight;
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Reference reference = (Reference) o;
    return this.topLeft.equals(reference.topLeft)
            && this.bottomRight.equals(reference.bottomRight);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.topLeft, this.bottomRight);
  }
}
