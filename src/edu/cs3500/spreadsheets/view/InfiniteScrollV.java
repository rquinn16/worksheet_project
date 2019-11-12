package edu.cs3500.spreadsheets.view;

import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import javax.swing.JScrollPane;

/**
 * Class that allows infinite scrolling feature in vertical direction.
 */
class InfiniteScrollV implements AdjustmentListener {
  JScrollPane p;
  BasicTableModel t;

  InfiniteScrollV(JScrollPane p, BasicTableModel t) {
    if (p  == null || t == null) {
      throw new IllegalArgumentException("tried to pass in null");
    }
    this.p = p;
    this.t = t;
  }

  @Override
  public void adjustmentValueChanged(AdjustmentEvent e) {
    int extentV = p.getVerticalScrollBar().getModel().getExtent();
    if (p.getVerticalScrollBar().getValue() + extentV == p.getVerticalScrollBar().getMaximum()) {
      this.t.setRow(this.t.getRowCount() + 10);
    }
  }
}
