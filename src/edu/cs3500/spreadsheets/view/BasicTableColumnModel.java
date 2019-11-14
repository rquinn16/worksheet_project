package edu.cs3500.spreadsheets.view;

import edu.cs3500.spreadsheets.model.Coord;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

/**
 * Class representing a model for the columns in our spreadsheet.
 */
class BasicTableColumnModel extends DefaultTableColumnModel {

  private TableModel t;
  private int col;

  /**
   * Constructor for BasicTableColumnModel.
   * @param t
   */
  BasicTableColumnModel(TableModel t) {
    this.t = t;
    this.col = t.getColumnCount();
    this.addAllColumns();
  }

  private void addAllColumns() {
    for (int i = 0; i < this.t.getColumnCount(); i++) {
      TableColumn toAdd = new TableColumn(i);
      toAdd.setHeaderValue(Coord.colIndexToName(i + 1));
      this.addColumn(toAdd);
    }
  }

  int addToCol() {
    this.col++;
    return col;
  }
}
