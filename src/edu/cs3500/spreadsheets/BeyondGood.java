package edu.cs3500.spreadsheets;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.Worksheet;
import edu.cs3500.spreadsheets.model.WorksheetModel;
import edu.cs3500.spreadsheets.model.WorksheetReader;
import edu.cs3500.spreadsheets.model.cell.Cell;

/**
 * The main class for our program.
 */
public class BeyondGood {
  /**
   * The main entry point.
   * @param args any command-line arguments
   */
  public static void main(String[] args) {
    /*
      TODO: For now, look in the args array to obtain a filename and a cell name,
      - read the file and build a model from it, 
      - evaluate all the cells, and
      - report any errors, or print the evaluated value of the requested cell.
    */
    try {
      if (!args[0].equals("-in")) {
        System.out.print("Must indicate a file with -in");
      }
      FileReader f = new FileReader(new File(args[1]));
      Worksheet builder = new Worksheet();
      WorksheetReader.read(builder, f);
      WorksheetModel<Cell> model = builder.createWorksheet();
      if (!args[2].equals("-eval")) {
        System.out.print("Must evaluate a cell with -eval");
      }
      ArrayList<String> cellCoords = parseSingularReference(args[3]);
      System.out.print(model.displayCell(Coord.colNameToIndex(cellCoords.get(0)),
              Integer.parseInt(cellCoords.get(1))));
    } catch (FileNotFoundException e) {
      System.out.print("Something went wrong.");
    }
  }

  static private ArrayList<String> parseSingularReference(String s) {
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
}
