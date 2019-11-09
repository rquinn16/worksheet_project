package edu.cs3500.spreadsheets.model.cell;

import java.util.Objects;

import edu.cs3500.spreadsheets.model.contentvisitor.ContentVisitor;

/**
 * Represents a Content that contains only a Double.
 */
public class DoubleValue extends AbstractValue<Double> {

  /**
   * Constructs an instance of a DoubleValue.
   * @param val the Double that should be contained by this Content.
   */
  public DoubleValue(double val) {
    super(val);
  }

  @Override
  public Content getContent() {
    return new DoubleValue(this.val);
  }

  @Override
  public <T> void accept(ContentVisitor<T> visitor) {
    visitor.visitDouble(this);
  }

  @Override
  public String toString() {
    return String.format("%f", this.val);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DoubleValue doubleValue = (DoubleValue) o;
    return val.equals(doubleValue.val);
  }

  @Override
  public int hashCode() {
    return Objects.hash(val);
  }
}