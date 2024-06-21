package eu.europa.eudi.utils.config;

import io.cucumber.datatable.DataTable;

import java.util.ArrayList;
import java.util.List;

public class TestDataConfig {

    public static int getColumn(DataTable dTable, String label) {
        int column = 0;
        int k = 0;
        boolean flagNotFound = true;
        List<List<String>> rows = dTable.asLists();
        for (List<String> cell : rows) {
            for (int i = 0; i < cell.size(); i++) {
                if (rows.get(k).get(i).equalsIgnoreCase(label)) {
                    column = i;
                    flagNotFound = false;
                    break;
                }
            }
            k++;
        }
        if (flagNotFound)
            column = -1;
        return column;
    }

    /*
     * |Label |Label2|
     * |Value |Value2|
     *
     * returns the value under every label
     */

    public String getValue(DataTable dTable, String label) {
        List<List<String>> rows = dTable.asLists();
        int column = getColumn(dTable, label);
        if (column >= 0) {
            return rows.get(1).get(column);
        } else
            return null;
    }

    /*

     * |Label |Value |
     * |Label2|Value2|
     * |Label3|Value3|
     *
     * returns the value to the right of each label.
     */

    public static String getValueLeftToRight(DataTable dt, String label) {
        String value = "";
        List<String> dataTable = dt.asList();
        int k = 0;
        for (String s : dataTable) {
            if (s.equalsIgnoreCase(label)) {
                value = dataTable.get(k + 1);
            }
            k++;
        }
        return value;
    }


    /**
     * @param arguments contains a datatable which contains a line per argument with 2 columns (variable, value) e.g.
     * @return
     */
    public static String getArguments(DataTable arguments) {
        String argument = "";
        int k = 0;
        List<List<String>> rows = arguments.asLists();
        for (List<String> cell : rows) {
            if (k == 0) {
                argument += "?";
            } else if (k > 0) {
                argument += "&";
            }
            argument += rows.get(k).get(0);
            argument += "=";
            argument += rows.get(k).get(1);
            k++;
        }
        return argument;
    }

    public static List<String> getDataTable(DataTable dataTable) {
        List<String> dataTableRows = new ArrayList<>();
        int k = 0;
        String row = "";
        List<List<String>> rows = dataTable.asLists();
        ;
        for (List<String> cell : rows) {
            for (int i = 0; i < cell.size(); i++) {
                row += rows.get(k).get(i);
                if (i < cell.size() - 1) {
                    row += "|";
                }
            }
            k++;
            dataTableRows.add(row);
            row = "";
        }
        return dataTableRows;
    }

}
