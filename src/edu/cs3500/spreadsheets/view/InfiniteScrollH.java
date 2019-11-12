package edu.cs3500.spreadsheets.view;

import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import javax.swing.JScrollPane;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 * Class that allows infinite scrolling feature in horizontal direction.
 */
public class InfiniteScrollH implements AdjustmentListener {
  JScrollPane p;
  TableColumnModel t;

  public InfiniteScrollH(JScrollPane p, TableColumnModel t) {
    this.p = p;
    this.t = t;
  }

  @Override
  public void adjustmentValueChanged(AdjustmentEvent e) {
    int extentH = p.getHorizontalScrollBar().getModel().getExtent();
    if (p.getHorizontalScrollBar().getValue() + extentH == p.getHorizontalScrollBar().getMaximum()) {
      this.t.addColumn(new TableColumn());
    }
  }
}
