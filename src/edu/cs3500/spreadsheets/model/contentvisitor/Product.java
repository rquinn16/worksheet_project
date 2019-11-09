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
 * Represents a class for multiplying numbers together.
 */
public class Product implements ContentVisitor<Double> {
  private ArrayList<Double> numerics;

  /**
   * Default constructor with empty list of numbers to add to.
   */
  public Product() {
    this.numerics = new ArrayList<>();
  }

  @Override
  public void visit(Content c) {
    c.accept(this);
  }

  @Override
  public void visitBlank(Blank b) {
    // multiply nothing if blank is visited
  }

  @Override
  public void visitDouble(DoubleValue d) {
    this.numerics.add(d.getValue());
  }

  @Override
  public void visitBoolean(BooleanValue d) {
    // multiply nothing if boolean is visited
  }

  @Override
  public void visitString(StringValue d) {
    // multiply nothing if string is visited
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
    for (Content c : refs) {
      c.accept(this);
    }
  }

  @Override
  public Value<Double> getResult() {
    if (this.numerics.size() == 0) {
      return new DoubleValue(0.);
    } else {
      double product = 1.;
      for (Double d : this.numerics) {
        product *= d;
      }
      return new DoubleValue(product);
    }
  }

  @Override
  public void reset() {
    this.numerics = new ArrayList<>();
  }

  @Override
  public String toString() {
    return "PRODUCT";
  }
}
