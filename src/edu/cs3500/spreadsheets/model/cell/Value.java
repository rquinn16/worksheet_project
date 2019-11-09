package edu.cs3500.spreadsheets.model.cell;

/**
 * Interface representing a content that contains a value.
 * @param <T> The type of value that the content is.
 */
public interface Value<T> extends Content {

  /**
   * Gets the value of this Content.
   * @return the value, of type T, of this content.
   */
  T getValue();
}
