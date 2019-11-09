package edu.cs3500.spreadsheets.model.cell;

import java.util.Objects;

import edu.cs3500.spreadsheets.model.contentvisitor.ContentVisitor;

/**
 * Represents a content that has only a boolean.
 */
public class BooleanValue extends AbstractValue<Boolean> {

  /**
   * Constructs a BooleanValue object.
   * @param val The boolean that should be contained by this content.
   */
  public BooleanValue(Boolean val) {
    super(val);
  }

  @Override
  public Content getContent() {
    return new BooleanValue(this.val);
  }

  @Override
  public <T> void accept(ContentVisitor<T> visitor) {
    visitor.visitBoolean(this);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BooleanValue booleanValue = (BooleanValue) o;
    return val == booleanValue.val;
  }

  @Override
  public int hashCode() {
    return Objects.hash(val);
  }
}
