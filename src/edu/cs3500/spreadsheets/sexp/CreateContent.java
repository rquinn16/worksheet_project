package edu.cs3500.spreadsheets.sexp;

import edu.cs3500.spreadsheets.model.contentvisitor.Concatenate;
import edu.cs3500.spreadsheets.model.contentvisitor.LessThan;
import edu.cs3500.spreadsheets.model.contentvisitor.Product;
import edu.cs3500.spreadsheets.model.contentvisitor.Sum;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.cell.Blank;
import edu.cs3500.spreadsheets.model.cell.BooleanValue;
import edu.cs3500.spreadsheets.model.cell.Cell;
import edu.cs3500.spreadsheets.model.cell.Content;
import edu.cs3500.spreadsheets.model.cell.DoubleValue;
import edu.cs3500.spreadsheets.model.cell.Formula;
import edu.cs3500.spreadsheets.model.cell.Function;
import edu.cs3500.spreadsheets.model.cell.Reference;
import edu.cs3500.spreadsheets.model.cell.StringValue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Breaks down a Sexp to create the corresponding content.
 */
public class CreateContent implements SexpVisitor<Content> {
  private final HashMap<Coord, Content> fromSheet;

  /**
   * Constructs this visitor.
   * @param fromSheet The cells from the model.
   */
  public CreateContent(HashMap<Coord, Content> fromSheet) {
    this.fromSheet = fromSheet;
  }

  @Override
  public Content visitBoolean(boolean b) {
    return new BooleanValue(b);
  }

  @Override
  public Content visitNumber(double d) {
    return new DoubleValue(d);
  }

  @Override
  public Content visitSList(List<Sexp> l) {
    ArrayList<Formula> args = new ArrayList<>();
    for (int i = 1; i < l.size(); i++) {
      args.add(l.get(i).accept(new CreateFormula(fromSheet)));
    }
    switch (l.get(0).toString().toLowerCase()) {
      case "sum":
        return new Function(new Sum(), args);
      case "product":
        return new Function(new Product(), args);
      case "<":
        return new Function(new LessThan(), args);
      case "concatenate":
        return new Function(new Concatenate(), args);
      default:
        throw new IllegalArgumentException("Not a function");
    }
  }

  @Override
  public Content visitSymbol(String s) {
    if (this.isReference(s)) {
      if (this.isSingularReference(s)) {
        ArrayList<String> coords = parseSingularReference(s);
        Coord tl = new Coord(Coord.colNameToIndex(coords.get(0)), Integer.parseInt(coords.get(1)));
        ArrayList<Cell> refs = new ArrayList<>();
        refs.add(new Cell(fromSheet.getOrDefault(tl, new Blank()), tl));
        return new Reference(tl, tl, refs);
      } else {
        ArrayList<String> coords = parseMultipleReference(s);
        Coord tl = new Coord(Coord.colNameToIndex(coords.get(0)), Integer.parseInt(coords.get(1)));
        Coord br = new Coord(Coord.colNameToIndex(coords.get(2)), Integer.parseInt(coords.get(3)));
        ArrayList<Cell> refs = new ArrayList<>();
        for (int i = tl.col; i <= br.col; i++) {
          for (int j = tl.row; j <= br.row; j++) {
            refs.add(new Cell(fromSheet.getOrDefault(
                    new Coord(i, j), new Blank()), new Coord(i, j)));
          }
        }
        return new Reference(tl, br, refs);
      }
    } else {
      throw new IllegalArgumentException("Symbol can't be by itself!");
    }
  }

  private ArrayList<String> parseReference(String s) {
    if (isSingularReference(s)) {
      return parseSingularReference(s);
    } else {
      return parseMultipleReference(s);
    }
  }

  private ArrayList<String> parseSingularReference(String s) {
    boolean atLetters = true;
    StringBuilder col = new StringBuilder();
    StringBuilder row = new StringBuilder();
    for (int i = 0; i < s.length(); i++) {
      if (Character.isDigit(s.charAt(i))) {
        atLetters = false;
      }
      if (atLetters) {
        col.append(s.charAt(i));
      } else {
        row.append(s.charAt(i));
      }
    }
    return new ArrayList<>(Arrays.asList(col.toString(), row.toString()));
  }

  private ArrayList<String> parseMultipleReference(String s) {
    String tl = s.substring(0, s.indexOf(':'));
    String br = s.substring(s.indexOf(':') + 1);
    ArrayList<String> list = new ArrayList<>();
    list.addAll(parseReference(tl));
    list.addAll(parseSingularReference(br));
    return list;
  }

  private boolean isReference(String s) {
    if (s.contains(":")) {
      String str1 = s.substring(0, s.indexOf(":"));
      String str2 = s.substring(s.indexOf(":") + 1);
      return this.isSingularReference(str1) && this.isSingularReference(str2);
    } else {
      return this.isSingularReference(s);
    }
  }

  private boolean isSingularReference(String s) {
    if (Character.isAlphabetic(s.charAt(0)) && Character.isUpperCase(s.charAt(0))
        && s.length() > 1) {
      boolean toNum = false;
      int i = 1;
      while (i < s.length()) {
        if (Character.isAlphabetic(s.charAt(i)) && Character.isUpperCase(s.charAt(i)) && !toNum) {
          i++;
        } else if (Character.isDigit(s.charAt(i))) {
          toNum = true;
          i++;
        } else {
          return false;
        }
      }
      return true;
    } else {
      return false;
    }
  }

  @Override
  public Content visitString(String s) {
    return new StringValue(s);
  }
}
