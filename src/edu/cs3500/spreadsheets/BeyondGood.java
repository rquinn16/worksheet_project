package edu.cs3500.spreadsheets;

import edu.cs3500.spreadsheets.controller.EditableWorksheetController;
import edu.cs3500.spreadsheets.model.BasicWorksheet;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.Worksheet;
import edu.cs3500.spreadsheets.model.WorksheetModel;
import edu.cs3500.spreadsheets.model.WorksheetReader;
import edu.cs3500.spreadsheets.model.cell.Cell;
import edu.cs3500.spreadsheets.provider.view.model.worksheet.WorksheetAdapter;
import edu.cs3500.spreadsheets.provider.view.view.ViewEvaluatedExpanded;
import edu.cs3500.spreadsheets.view.WorksheetGraphicalView;
import edu.cs3500.spreadsheets.view.WorksheetTextualView;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * The main class for our program.
 */
public class BeyondGood {

  /**
   * The main entry point.
   *
   * @param args any command-line arguments
   */
  public static void main(String[] args) {
    switch (args.length) {
      case 1:
        BeyondGood.caseOne(args);
        break;
      case 3:
        BeyondGood.caseThree(args);
        break;
      case 4:
        BeyondGood.caseFour(args);
        break;
      default:
        System.out.println("Incorrect arguments");
        break;
    }
  }

  private static void caseOne(String[] args) {
    if (args[0].equals("-gui")) {
      new WorksheetGraphicalView(new BasicWorksheet()).render();
    } else if (args[0].equals("-edit")) {
      new EditableWorksheetController(new BasicWorksheet()).start();
    } else if (args[0].equals("-provider")) {
      new ViewEvaluatedExpanded(new WorksheetAdapter(new BasicWorksheet())).display();
    } else {
      System.out.println("Incorrect argument.");
    }
  }

  private static void caseThree(String[] args) {
    try {
      if (!args[0].equals("-in")) {
        System.out.print("Incorrect argument.");
      }
      FileReader f = new FileReader(new File(args[1]));
      Worksheet builder = new Worksheet();
      WorksheetReader.read(builder, f);
      WorksheetModel<Cell> model = builder.createWorksheet();
      if (args[2].equals("-gui")) {
        new WorksheetGraphicalView(model).render();
      } else if (args[2].equals("-edit")) {
        new EditableWorksheetController(model).start();
      } else if (args[2].equals("-provider")) {
        new ViewEvaluatedExpanded(new WorksheetAdapter(model)).display();
      } else {
        System.out.print("Incorrect argument.");
      }
    } catch (FileNotFoundException e) {
      System.out.println("File not found");
    }
  }

  private static void caseFour(String[] args) {
    try {
      if (!args[0].equals("-in")) {
        System.out.print("Must indicate a file with -in.");
      }
      FileReader f = new FileReader(new File(args[1]));
      Worksheet builder = new Worksheet();
      WorksheetReader.read(builder, f);
      WorksheetModel<Cell> model = builder.createWorksheet();
      if (args[2].equals("-eval")) {
        BeyondGood.cellEval(args, model);
      } else if (args[2].equals("-save")) {
        BeyondGood.save(args, model);
      } else {
        System.out.println("Incorrect arguments.");
      }
    } catch (FileNotFoundException e) {
      System.out.println("File not found");
    }
  }

  private static void cellEval(String[] args, WorksheetModel<Cell> model) {
    ArrayList<String> cellCoords = parseSingularReference(args[3]);
    System.out.print(model.displayCell(Coord.colNameToIndex(cellCoords.get(0)),
        Integer.parseInt(cellCoords.get(1))));
  }

  private static void save(String[] args, WorksheetModel<Cell> model) {
    try {
      WorksheetTextualView view = new WorksheetTextualView(model, args[3]);
      view.render();
    } catch (IOException e) {
      System.out.println("File not found");
    }
  }

  static ArrayList<String> parseSingularReference(String s) {
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
