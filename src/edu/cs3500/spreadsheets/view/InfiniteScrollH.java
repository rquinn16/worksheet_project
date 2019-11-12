package edu.cs3500.spreadsheets.view;

import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import javax.swing.JScrollPane;

/**
 * Class that allows infinite scrolling feature in horizontal direction.
 */
public class InfiniteScrollH implements AdjustmentListener {
  JScrollPane p;
  BasicTableModel t;

  public InfiniteScrollH(JScrollPane p, BasicTableModel t) {
    this.p = p;
    this.t = t;
  }

  @Override
  public void adjustmentValueChanged(AdjustmentEvent e) {
    int extentH = p.getHorizontalScrollBar().getModel().getExtent();
    if (p.getHorizontalScrollBar().getValue() + extentH == p.getHorizontalScrollBar().getMaximum()) {
      this.t.setCol(this.t.getColumnCount() + 10);
    }
  }
}
