package edu.cs3500.spreadsheets.view;

import java.awt.Color;
import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.JScrollPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableColumn;

class DisplayRowNumbers extends JTable implements ChangeListener, PropertyChangeListener {
  private JTable dataTable;

  DisplayRowNumbers(JTable dataTable) {
    if (dataTable == null) {
      throw new IllegalArgumentException("tried to pass in null");
    }
    this.dataTable = dataTable;
    this.dataTable.addPropertyChangeListener(this);
    this.dataTable.getModel().addTableModelListener(this);

    this.setRowHeight(30);
    this.setFocusable(false);
    this.setBackground(new Color(115, 115, 115));
    this.setGridColor(Color.BLACK);
    this.setAutoCreateColumnsFromModel(false);
    this.setSelectionModel(dataTable.getSelectionModel());

    TableColumn col = new TableColumn();
    col.setHeaderValue("");
    this.addColumn(col);

    this.getColumnModel().getColumn(0).setPreferredWidth(50);
    this.setPreferredScrollableViewportSize(getPreferredSize());
  }

  @Override
  public boolean isCellEditable(int row, int column) {
    return false;
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
    return "" + (row + 1);
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
  public void stateChanged(ChangeEvent evt) {
    if (evt == null) {
      throw new IllegalArgumentException("tried to pass in null");
    }
    JViewport viewport = (JViewport) evt.getSource();
    JScrollPane scrollPane = (JScrollPane) viewport.getParent();
    scrollPane.getVerticalScrollBar().setValue(viewport.getViewPosition().y);
  }

  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    if (evt == null) {
      throw new IllegalArgumentException("tried to pass in null");
    }
    switch (evt.getPropertyName()) {
      case "selectionModel":
        this.setSelectionModel(this.dataTable.getSelectionModel());
        break;
      case "rowHeight":
        this.repaint();
        break;
      case "model":
        this.dataTable.getModel().addTableModelListener(this);
        this.revalidate();
        break;
      default:
        break;
    }
  }
}