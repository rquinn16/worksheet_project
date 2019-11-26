package edu.cs3500.spreadsheets.view;

import edu.cs3500.spreadsheets.model.Coord;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import javax.swing.JScrollPane;
import javax.swing.table.TableColumn;

/**
 * Class that allows infinite scrolling feature in horizontal direction.
 */
class InfiniteScrollH implements AdjustmentListener {
  JScrollPane p;
  BasicTableColumnModel t;

  InfiniteScrollH(JScrollPane p, BasicTableColumnModel t) {
    if (p  == null || t == null) {
      throw new IllegalArgumentException("tried to pass in null");
    }
    this.p = p;
    this.t = t;
  }

  @Override
  public void adjustmentValueChanged(AdjustmentEvent e) {
    int extentH = p.getHorizontalScrollBar().getModel().getExtent();
    if (p.getHorizontalScrollBar().getValue() + extentH
            == p.getHorizontalScrollBar().getMaximum()) {
      TableColumn toAdd = new TableColumn(t.addToCol());
      toAdd.setHeaderValue(Coord.colIndexToName(t.getColumnCount() + 1));
      this.t.addColumn(toAdd);
    }
  }
}
