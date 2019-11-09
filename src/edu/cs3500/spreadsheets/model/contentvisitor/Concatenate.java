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
 * Represents a function finding the square root of a given value.
 */
public class Concatenate implements ContentVisitor<String> {
  private StringBuilder str;

  /**
   * Constructor for the Concatenate class.
   * @param str the current String being concatenated.
   */
  public Concatenate(String str) {
    this.str = new StringBuilder(str);
  }

  public Concatenate() {
    this.str = new StringBuilder();
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
    // add nothing if double is visited
  }

  @Override
  public void visitBoolean(BooleanValue d) {
    // add nothing if boolean is visited
  }

  @Override
  public void visitString(StringValue d) {
    this.str.append(d.getValue());
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
    f.simplify(new ArrayList<>(),
            new ArrayList<>()).accept(this);
  }

  @Override
  public Value<String> getResult() {
    return new StringValue(this.str.toString());
  }

  @Override
  public void reset() {
    this.str = new StringBuilder();
  }

  @Override
  public String toString() {
    return "CONCATENATE";
  }
}
