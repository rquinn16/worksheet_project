package edu.cs3500.spreadsheets.model.cell;

import java.util.ArrayList;
import java.util.Objects;

import edu.cs3500.spreadsheets.model.contentvisitor.ContentVisitor;

/**
 * Class representing a function.
 */
public class Function implements Formula {
  private ContentVisitor name;
  private ArrayList<Formula> args;

  /**
   * For returning content.
   * @param name a function object representing what function the cell should execute.
   * @param args a list of arguments.
   */
  public Function(ContentVisitor name, ArrayList<Formula> args) {
    this.name = name;
    this.args = args;
  }

  @Override
  public boolean isValid(Cell cell, ArrayList<Cell> fromSheet, ArrayList<Cell> acc) {
    boolean res = true;
    for (Formula f : this.args) {
      res &= f.isValid(cell, fromSheet, acc);
    }
    return res;
  }

  @Override
  public Content getContent() {
    return new Function(this.name, new ArrayList<>(this.args));
  }

  @Override
  public Content simplify(ArrayList<Cell> fromSheet, ArrayList<Cell> forReference) {
    this.name.reset();
    for (Formula f : this.args) {
      this.name.visit(f);
    }
    return this.name.getResult();
  }

  @Override
  public String toEvaluatedString() {
    try {
      return this.name.getResult().toString();
    } catch (IllegalStateException e) {
      return "#EVAL!";
    }
  }

  @Override
  public <T> void accept(ContentVisitor<T> visitor) {
    visitor.visitFunction(this);
  }

  @Override
  public String toString() {
    StringBuilder result = new StringBuilder("=(");
    result.append(this.name.toString()).append(" ");
    for (Formula f : this.args) {
      result.append(f.toString().substring(1)).append(" ");
    }
    String toReturn = result.toString();
    toReturn = toReturn.substring(0, result.length() - 1);
    toReturn += ")";
    return toReturn;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Function function = (Function) o;
    return this.name.getClass() == function.name.getClass() && this.args.equals(function.args);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.name, this.args);
  }
}
