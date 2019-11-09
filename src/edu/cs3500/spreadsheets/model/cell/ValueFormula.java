package edu.cs3500.spreadsheets.model.cell;

import java.util.ArrayList;
import java.util.Objects;

import edu.cs3500.spreadsheets.model.contentvisitor.ContentVisitor;

/**
 * A formula that only contains a value.
 */
public class ValueFormula implements Formula {
  private final Value val;

  /**
   * Constructs a value formula.
   * @param val The value to be contained in this value formula.
   */
  public ValueFormula(Value val) {
    this.val = val;
  }

  @Override
  public <T> void accept(ContentVisitor<T> visitor) {
    visitor.visit(this.val);
  }

  @Override
  public boolean isValid(Cell cell, ArrayList<Cell> fromSheet, ArrayList<Cell> acc) {
    return val.isValid(cell, fromSheet, acc);
  }

  @Override
  public Content getContent() {
    return new ValueFormula(this.val);
  }

  @Override
  public Content simplify(ArrayList<Cell> fromSheet, ArrayList<Cell> forReference) {
    return this;
  }

  @Override
  public String toEvaluatedString() {
    return "=" + this.val.toString();
  }

  @Override
  public String toString() {
    return "(" + this.val.toString() + ")";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ValueFormula valueFormula = (ValueFormula) o;
    return this.val.equals(valueFormula.val);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.val);
  }
}
