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

/**
 * Represents an interface for visiting all types of Contents.
 * @param <R> the type that will be returned if getResult() is called on this ContentVisitor.
 */
public interface ContentVisitor<R> {

  /**
   * Visits and is accepted by all forms of content.
   * @param c Any content.
   */
  void visit(Content c);

  /**
   * Visits and is accepted only by blank values.
   * @param b A blank content.
   */
  void visitBlank(Blank b);

  /**
   * Visits and is accepted only by DoubleValues.
   * @param d A DoubleValue content.
   */
  void visitDouble(DoubleValue d);

  /**
   * Visits and is accepted only by BooleanValues.
   * @param d A BooleanValue content.
   */
  void visitBoolean(BooleanValue d);

  /**
   * Visits and is accepted only by StringValues.
   * @param d A StringValue content.
   */
  void visitString(StringValue d);

  /**
   * Visits and is accepted only by ValueFormulas.
   * @param v A ValueFormula content.
   */
  void visitValueFormula(ValueFormula v);

  /**
   * Visits and is accepted only by References.
   * @param r A Reference content.
   */
  void visitReference(Reference r);

  /**
   * Visits and is accepted only by Functions.
   * @param f A Function content.
   */
  void visitFunction(Function f);

  /**
   * Returns the Value of type R that this Visitor expects.
   * @return Value of type R.
   */
  Value<R> getResult();

  /**
   * Resets this Visitor.
   */
  void reset();
}
