package edu.cs3500.spreadsheets.provider.view.view;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JList;
import javax.swing.ListModel;
import javax.swing.AbstractListModel;
import javax.swing.BoxLayout;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.provider.view.model.worksheet.Worksheet;

/**
 * View class for gui when the user wants the cells to be evaluated.
 */
public class NewViewEvaluated extends JFrame implements IView {
  public JFrame jf = new JFrame();
  public JTable table;

  /**
   * This sets up the Gui for all spreadsheet views using tables, JList and Scroll Panel.
   *
   * @param worksheet the Worksheet that the gui is being built off of
   */
  public NewViewEvaluated(Worksheet worksheet) {
    super("THIS IS THE PICTURE FOR build-Value02.txt");

    DefaultTableModel tableModel = new DefaultTableModel();
    ArrayList<ArrayList<String>> data = new ArrayList<>();
    for (int c = 1; c <= worksheet.getLargestCol(); c++) {
      data.add(new ArrayList<>());
      for (int r = 1; r <= worksheet.getLargestRow(); r++) {
        data.get(c - 1).add(worksheet.getCellAt(c, r).evaluateCell(new ArrayList<>(), worksheet));
        worksheet.evaluate(Coord.colIndexToName(c)+r);
      }
      tableModel.addColumn(Coord.colIndexToName(c)
              , data.get(c - 1).toArray());
    }

    this.table = new JTable(tableModel);
    this.table.setPreferredScrollableViewportSize(new Dimension(500, 500));
    this.table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    this.table.setFillsViewportHeight(true);
    this.table.setOpaque(true);
    this.table.setRowHeight(15);
    this.table.getModel().addTableModelListener(new TableModelListener() {
      @Override
      public void tableChanged(TableModelEvent e) {
        new NewViewEvaluated(worksheet);
      }
    });

    JScrollPane scrollBar = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS
            , JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    scrollBar.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
      @Override
      public void adjustmentValueChanged(AdjustmentEvent e) {
        JScrollBar scrollBarTemp = (JScrollBar) e.getAdjustable();
        int extent = scrollBarTemp.getModel().getExtent();
        int maximum = scrollBarTemp.getModel().getMaximum();
        if (extent + e.getValue() == maximum) {
          ArrayList<String> newRow = new ArrayList<>();
          for (int i = 1; i <= worksheet.getLargestCol(); i++) {
            newRow.add(" ");
          }
          tableModel.addRow(newRow.toArray());
          worksheet.addCell(worksheet.getLargestCol(), worksheet.getLargestRow(), "\" \"");
          scrollBar.setRowHeaderView(buildRowHeader(table));

        }
      }
    });
    scrollBar.getHorizontalScrollBar().addAdjustmentListener(new AdjustmentListener() {
      @Override
      public void adjustmentValueChanged(AdjustmentEvent e) {
        JScrollBar scrollBarTemp = (JScrollBar) e.getAdjustable();
        int extent = scrollBarTemp.getModel().getExtent();
        int maximum = scrollBarTemp.getModel().getMaximum();
        if (extent + e.getValue() == maximum) {
          ArrayList<String> newCol = new ArrayList<>();
          for (int i = 1; i <= worksheet.getLargestRow(); i++) {
            newCol.add(" ");
          }
          worksheet.addCell(worksheet.getLargestCol() + 1, 1, "\"\"");
          tableModel.addColumn(Coord.colIndexToName(worksheet.getLargestCol()), newCol.toArray());
          worksheet.addCell(worksheet.getLargestCol(), worksheet.getLargestRow(), "\"\"");
        }
      }
    });
    scrollBar.setRowHeaderView(buildRowHeader(table));
    this.jf.add(scrollBar);
    this.jf.setPreferredSize(new Dimension(600, 300));
    this.jf.setLocation(200, 200);
    this.jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.jf.setResizable(true);
    this.jf.setLayout(new BoxLayout(jf.getContentPane(), BoxLayout.Y_AXIS));
  }

  //creates a Jlist that acts a row head name for the table.
  private static JList buildRowHeader(JTable table) {
    final Vector<String> headers = new Vector<>();
    for (int i = 1; i <= table.getRowCount(); i++) {
      headers.add(i + "");
    }
    ListModel lm = new AbstractListModel() {

      /**
       * Returns the length of the list.
       *
       * @return the length of the list
       */
      @Override
      public int getSize() {
        return headers.size();
      }

      /**
       * Returns the value at the specified index.
       *
       * @param index the requested index
       * @return the value at <code>index</code>
       */
      @Override
      public Object getElementAt(int index) {
        return headers.get(index);
      }
    };

    final JList rowHeader = new JList(lm);
    rowHeader.setOpaque(false);
    rowHeader.setBackground(Color.LIGHT_GRAY);
    rowHeader.setForeground(table.getForeground());
    rowHeader.setFixedCellHeight(15);
    rowHeader.setBorder(new LineBorder(Color.BLACK));
    return rowHeader;
  }

  /**
   * Sets the Visibility of the table to be on so it displays.
   */
  @Override
  public void display() {
    this.jf.pack();
    this.jf.setVisible(true);
  }

}
