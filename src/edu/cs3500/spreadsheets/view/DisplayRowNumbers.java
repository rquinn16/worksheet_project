package edu.cs3500.spreadsheets.view;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableColumn;

class DisplayRowNumbers extends JTable implements ChangeListener, PropertyChangeListener {
  private JTable dataTable;

  DisplayRowNumbers(JTable dataTable) {
    this.dataTable = dataTable;
    this.dataTable.addPropertyChangeListener(this);
    this.dataTable.getModel().addTableModelListener(this);

    this.setRowHeight(30);
    this.setFocusable(false);
    this.setShowGrid(true);
    this.setGridColor(Color.BLACK);
    this.setAutoCreateColumnsFromModel(false);
    this.setSelectionModel(dataTable.getSelectionModel());

    TableColumn col = new TableColumn();
    col.setHeaderValue("");
    addColumn(col);

    this.getColumnModel().getColumn(0).setPreferredWidth(50);
    this.setPreferredScrollableViewportSize(getPreferredSize());
  }

  @Override
  public void addNotify() {
    super.addNotify();

    Component c = this.getParent();

    if (c instanceof JViewport) {
      JViewport viewport = (JViewport) c;
      viewport.addChangeListener(this);
    }
  }

  @Override
  public Object getValueAt(int row, int column) {
    return Integer.toString(row + 1);
  }

  @Override
  public int getRowCount() {
    return dataTable.getRowCount();
  }

  @Override
  public int getColumnCount() {
    return 1;
  }

  @Override
  public void stateChanged(ChangeEvent e) {
    JViewport viewport = (JViewport) e.getSource();
    JScrollPane scrollPane = (JScrollPane) viewport.getParent();
    scrollPane.getVerticalScrollBar().setValue(viewport.getViewPosition().y);
  }

  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    if ("selectionModel".equals(evt.getPropertyName())) {
      this.setSelectionModel(this.dataTable.getSelectionModel());
    }

    if ("rowHeight".equals(evt.getPropertyName())) {
      this.repaint();
    }

    if ("model".equals(evt.getPropertyName())) {
      this.dataTable.getModel().addTableModelListener(this);
      this.revalidate();
    }
  }
}
