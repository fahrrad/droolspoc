package com.gb.cropdesign.droolspoc.gui;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

/**
 * Created with IntelliJ IDEA.
 * User: CoesseW
 * Date: 4/10/13
 * Time: 18:18
 * To change this template use File | Settings | File Templates.
 */
public class FormattedSummaryTableCellFactory<S,T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {
    @Override
    public TableCell<S, T> call(TableColumn<S, T> stTableColumn) {
        TableCell<S, T> cell = new TableCell<S, T>()    {
            @Override
            protected void updateItem(T t, boolean b) {
                // Boolean b = t.getValue().getResult(columnName);

                String v = t == null ? "?" : b ? "+" : "-";

                // CSS Styles
                String positiveResultStyle = "positiveResultStyle";
                String negativeResultStyle = "negativeResultStyle";
                String unknownResultStyle = "unknownResultStyle";
                String cssStyle = "";
                String plantSummary = null;


                if (getTableRow() != null){
                    plantSummary = (String) getTableRow().getItem();
                }

                getStyleClass().removeAll();

                super.updateItem((T) t, b);

                if(plantSummary == null || plantSummary.equals("?")){
                    cssStyle = unknownResultStyle;
                }else if (plantSummary.equals("+")) {
                    cssStyle = positiveResultStyle;
                } else if(plantSummary.equals("-")){
                    cssStyle = negativeResultStyle;
                }

                getStyleClass().add(cssStyle);
                if(t != null){
                    setText( t.toString());
                }else{
                    setText( "");
                }

            }
        };

        return cell;
    }
}
