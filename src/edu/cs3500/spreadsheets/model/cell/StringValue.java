package edu.cs3500.spreadsheets.model.cell;

import java.util.Objects;

import edu.cs3500.spreadsheets.model.contentvisitor.ContentVisitor;

/**
 * Represents a content that only has a String in it.
 */
public class StringValue extends AbstractValue<String> {

  /**
   * Constructs a StringValue object.
   * @param val The String that should be contained by this content.
   */
  public StringValue(String val) {
    super(val);
  }

  @Override
  public Content getContent() {
    return new StringValue(this.val);
  }

  @Override
  public <T> void accept(ContentVisitor<T> visitor) {
    visitor.visitString(this);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    StringValue stringValue = (StringValue) o;
    return val.equals(stringValue.val);
  }

  @Override
  public int hashCode() {
    return Objects.hash(val);
  }

  @Override
  public String toString() {
    return "\"" + this.val + "\"";
  }
}