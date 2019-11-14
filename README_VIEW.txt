We decided to create a WorksheetModel interface with one void method called render. 
The only conceivable function that every single view of a spreadsheet has in common would be rendering the spreadsheet,
however a view implementation would accomplish that.

We created two classes that implemented our view interface: WorksheetTextualView and WorksheetGraphicalView.

WorksheetTextualView uses a BufferedWriter to write a txt file from the model that it has contained in it, and it produces
a text file that is identical in format to the text files that were created and included in the previous assignment's
submission. This required minor changes to our model. In our model interface, we previously only had a method to get
a cell's evaluated String but we decided to add a String method to get a cell's "raw contents" to output to the text file.

WorksheetGraphicalView is much more complex, as this is the class that actually renders the visible grid in the style of 
Microsoft Excel and Google Sheets. We decided to utilize the JTable and JScrollPane classes in JSwing. Two challenges we faced
were displaying the row numbers as this is not built into JTable, and implementing infinite scrolling. To rememdy these issues,
we created a class DisplayRowNumbers that extended JTable whichis essentially a one column JTable whose purpose is only to 
display row numbers. A ChangeListener and PropertyChangeListener are implemented in this class to listen to the scrolling
in the main JTable with the data and JScrollPane, and these listeners synchronize the scrolling on the spreadsheet with
scrolling down the row numbers. In implementing the actual data JTable, we created a class BasicTableModel which allows the
data from the actual model to communicate with the JTable. To implement infinite scrolling in both directions, we created
two classes, InfiniteScrollH and InfiniteScrollV which just added to the number of rows and columns in the JTable when 
scrolling beyond the bottom right-most cell in the inputted data. This feature will allow the user once the controller is
implemented to go beyond the initial spreadsheet, just like is allowed in Microsoft Excel.
