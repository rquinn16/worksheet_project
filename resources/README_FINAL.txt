For the final submission, we weren't able to get a few things working. Because of what we suspect
to be the absence of a TableColumnModel from our code providers, the "-provider" configuration by
itself crashes the program, stating that the column and row sizes "can't be negative." Also, we
found that passing in our sample file for the view to render renders a graph that displays the same
value over and over again in one column, without performing the action that the formula is supposed
to perform. Lastly, hitting the "yes" button resets the entire view because they do not call the
repaint() method in their view and instead recall render(), thus making their view impossible to
update.s