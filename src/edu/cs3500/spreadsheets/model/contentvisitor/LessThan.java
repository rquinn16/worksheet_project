package edu.cs3500.spreadsheets.model.contentvisitor;

import edu.cs3500.spreadsheets.model.cell.Blank;
import edu.cs3500.spreadsheets.model.cell.BooleanValue;
import edu.cs3500.spreadsheets.model.cell.Content;
import edu.cs3500.spreadsheets.model.cell.DoubleValue;
import edu.cs3500.spreadsheets.model.cell.Function;
import edu.cs3500.spreadsheets.model.cell.Reference;
import edu.cs3500.spreadsheets.model.cell.StringValue;
import edu.cs3500.spreadsheets.model.cell.Value;
import edu.cs3500.spreadsheets.model.cell.ValueFormula;
import java.util.ArrayList;

/**
 * Represents a function that determines whether or not the content is less than a given value.
 */
public class LessThan implements ContentVisitor<Boolean> {
  private double[] toCompare;
  private int index;

  /**
   * Constructs an instance of a less than function object.
   */
  public LessThan() {
    this.toCompare = new double[2];
    this.index = 0;
  }

  @Override
  public void visit(Content c) {
    c.accept(this);
  }

  @Override
  public void visitBlank(Blank b) {
    throw new IllegalArgumentException("Not a number.");
  }

  @Override
  public void visitDouble(DoubleValue d) {
    if (this.index < 2) {
      this.toCompare[this.index] = d.getValue();
      this.index++;
    }
  }

  @Override
  public void visitBoolean(BooleanValue b) {
    throw new IllegalArgumentException("Not a number.");
  }

  @Override
  public void visitString(StringValue s) {
    throw new IllegalArgumentException("Not a number.");
  }

  @Override
  public void visitValueFormula(ValueFormula v) {
    v.accept(this);
  }

  @Override
  public void visitFunction(Function f) {
    f.simplify(new ArrayList<>(), new ArrayList<>()).accept(this);
  }

  @Override
  public void visitReference(Reference r) {
    ArrayList<Content> refs = r.getSimplifiedReferences();
    if (refs.size() == 1) {
      refs.get(0).accept(this);
    } else {
      throw new IllegalArgumentException("Can't run this method on multi-celled references.");
    }
  }

  @Override
  public Value<Boolean> getResult() {
    if (this.index == 2) {
      return new BooleanValue(this.lessThan());
    } else {
      throw new IllegalArgumentException("Wrong number of arguments.");
    }
  }

  private boolean lessThan() {
    return this.toCompare[0] < this.toCompare[1];
  }

  @Override
  public void reset() {
    this.toCompare = new double[2];
    this.index = 0;
  }

  @Override
  public String toString() {
    return "<";
  }
}
