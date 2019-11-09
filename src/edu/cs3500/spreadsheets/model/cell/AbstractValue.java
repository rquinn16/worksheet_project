package edu.cs3500.spreadsheets.model.cell;

import java.util.ArrayList;

/**
 * Represents a value where we don't yet know its type but we can extract some duplicate code.
 * @param <T> The type that the value cell will be parametrized by.
 */
public abstract class AbstractValue<T> implements Value<T> {
  final T val;

  /**
   * Constructs an abstract value.
   * @param val The value that the Content should contain.
   */
  AbstractValue(T val) {
    this.val = val;
  }

  @Override
  public T getValue() {
    return this.val;
  }

  @Override
  public boolean isValid(Cell cell, ArrayList<Cell> fromSheet, ArrayList<Cell> acc) {
    return true;
  }

  @Override
  public Content simplify(ArrayList<Cell> fromSheet, ArrayList<Cell> forReference) {
    return this;
  }

  @Override
  public String toEvaluatedString() {
    return this.toString();
  }

  @Override
  public String toString() {
    return val.toString();
  }
}
