package edu.cs3500.spreadsheets.model.cell;

import java.util.ArrayList;
import java.util.Objects;

import edu.cs3500.spreadsheets.model.contentvisitor.ContentVisitor;

/**
 * Represents a blank content with no information.
 */
public class Blank implements Content {
  @Override
  public boolean isValid(Cell cell, ArrayList<Cell> fromSheet, ArrayList<Cell> acc) {
    return true;
  }

  @Override
  public Content getContent() {
    return new Blank();
  }

  @Override
  public Content simplify(ArrayList<Cell> fromSheet, ArrayList<Cell> forReference) {
    return this.getContent();
  }

  @Override
  public String toEvaluatedString() {
    return this.toString();
  }

  @Override
  public <T> void accept(ContentVisitor<T> visitor) {
    visitor.visitBlank(this);
  }

  @Override
  public String toString() {
    return "";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    return o != null && getClass() == o.getClass();
  }

  @Override
  public int hashCode() {
    return Objects.hash(this);
  }
}
