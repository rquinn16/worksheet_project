package edu.cs3500.spreadsheets.model.contentvisitor;

import java.util.ArrayList;

import edu.cs3500.spreadsheets.model.cell.Blank;
import edu.cs3500.spreadsheets.model.cell.BooleanValue;
import edu.cs3500.spreadsheets.model.cell.Content;
import edu.cs3500.spreadsheets.model.cell.DoubleValue;
import edu.cs3500.spreadsheets.model.cell.Function;
import edu.cs3500.spreadsheets.model.cell.Reference;
import edu.cs3500.spreadsheets.model.cell.StringValue;
import edu.cs3500.spreadsheets.model.cell.Value;
import edu.cs3500.spreadsheets.model.cell.ValueFormula;

/**
 * Represents a class for summing numbers together.
 */
public class Sum implements ContentVisitor<Double> {
  private double sum;

  /**
   * Default constructor.
   */
  public Sum() {
    this.sum = 0.0;
  }

  @Override
  public void visit(Content c) {
    c.accept(this);
  }

  @Override
  public void visitBlank(Blank b) {
    // add nothing if blank is visited
  }

  @Override
  public void visitDouble(DoubleValue d) {
    this.sum += d.getValue();
  }

  @Override
  public void visitBoolean(BooleanValue b) {
    // add nothing if boolean is visited
  }

  @Override
  public void visitString(StringValue s) {
    // add nothing if string is visited
  }

  @Override
  public void visitValueFormula(ValueFormula v) {
    v.accept(this);
  }

  @Override
  public void visitReference(Reference r) {
    ArrayList<Content> refs = r.getSimplifiedReferences();
    for (Content c : refs) {
      c.accept(this);
    }
  }

  @Override
  public void visitFunction(Function f) {
    f.simplify(new ArrayList<>(), new ArrayList<>()).accept(this);
  }

  @Override
  public Value<Double> getResult() {
    return new DoubleValue(this.sum);
  }

  @Override
  public void reset() {
    this.sum = 0.0;
  }

  @Override
  public String toString() {
    return "SUM";
  }
}
