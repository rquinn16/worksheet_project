import static org.junit.Assert.assertEquals;

import edu.cs3500.spreadsheets.controller.EditableWorksheetController;
import edu.cs3500.spreadsheets.controller.WorksheetController;
import edu.cs3500.spreadsheets.model.BasicWorksheet;
import edu.cs3500.spreadsheets.model.WorksheetModel;
import edu.cs3500.spreadsheets.model.cell.Cell;
import edu.cs3500.spreadsheets.view.EditableWorksheetGraphicalView;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JTable;
import org.junit.Test;

/**
 * Tests for the controller for the spreadsheet.
 */
public class TestController {

  @Test
  public void testMouseListener() {
    JTable dataTable = new JTable();
    MouseEvent mouseEvent = new MouseEvent(dataTable, MouseEvent.MOUSE_CLICKED,
        MouseEvent.ACTION_EVENT_MASK,
        InputEvent.CTRL_MASK | InputEvent.META_MASK | InputEvent.ALT_GRAPH_MASK
            | InputEvent.SHIFT_DOWN_MASK, 5, 30, 1, true,
        MouseEvent.BUTTON1);
    WorksheetModel<Cell> model = new BasicWorksheet();
    model.addCell(1, 2, "\"TEST\"");
    EditableWorksheetGraphicalView view = new EditableWorksheetGraphicalView(model);
    WorksheetController controller = new EditableWorksheetController(model, view);
    controller.start();
    assertEquals("", view.getCurrentText());
    controller.mouseClicked(mouseEvent);
    assertEquals("\"TEST\"", view.getCurrentText());
  }

  @Test
  public void testActionListener() {
    JTable dataTable = new JTable();
    MouseEvent mouseEvent = new MouseEvent(dataTable, MouseEvent.MOUSE_CLICKED,
        MouseEvent.ACTION_EVENT_MASK,
        InputEvent.CTRL_MASK | InputEvent.META_MASK | InputEvent.ALT_GRAPH_MASK
            | InputEvent.SHIFT_DOWN_MASK, 5, 30, 1, true,
        MouseEvent.BUTTON1);
    WorksheetModel<Cell> model = new BasicWorksheet();
    EditableWorksheetGraphicalView view = new EditableWorksheetGraphicalView(model);
    WorksheetController controller = new EditableWorksheetController(model, view);
    JButton confirm = new JButton();
    ActionEvent buttonPressed = new ActionEvent(confirm, 1, "");
    confirm.addActionListener(controller);
    controller.start();
    controller.mouseClicked(mouseEvent);
    assertEquals("", view.getCurrentText());
    view.updateText("\"TEST\"");
    assertEquals("\"TEST\"", view.getCurrentText());
    controller.actionPerformed(buttonPressed);
    assertEquals("\"TEST\"", view.getRawValueAt(1, 2));
  }
}