package KBS;

import java.io.File;

import java.util.ArrayList;

import org.eobjects.metamodel.DataContext;
import org.eobjects.metamodel.DataContextFactory;
import org.eobjects.metamodel.data.DataSet;
import org.eobjects.metamodel.data.Row;
import org.eobjects.metamodel.query.Query;
import org.eobjects.metamodel.schema.Column;
import org.eobjects.metamodel.schema.Schema;
import org.eobjects.metamodel.schema.Table;


public class RefineQuery {

    public void relaxQuery(String inputStringOne, String inputStringTwo) {
        String temp = "";
        String inputStringTwoTemp =
            inputStringTwo.replace(" <= ", " < ").replace(" >= ", " > ");

        // Split the given Input string as required
        String[] parts1 = inputStringOne.split("\\^");
        String[] parts2 = inputStringTwoTemp.split("\\^");
        String permStringArray[] = { "<", ">=", ">", "!=", "<=" };

        String array0[] = new String[parts1.length], array1[] =
            new String[parts1.length], array2[] = new String[parts1.length];

        int lengthOfFirstQuery = parts1.length;
        int lengthOfPermStringArray = permStringArray.length;
        for (int i = 0; i < lengthOfFirstQuery; i++) {
            for (int j = 0; j < lengthOfPermStringArray; j++) {
                if (parts1[i].contains(permStringArray[j])) {
                    String[] testArray = parts1[i].split(permStringArray[j]);
                    array0[i] = testArray[0];
                    array1[i] = permStringArray[j];
                    array2[i] = testArray[1];
                }
            }

        }
        String array00[] = new String[parts2.length], array11[] =
            new String[parts2.length], array22[] = new String[parts2.length];

        for (int i = 0; i < parts2.length; i++) {
            for (int j = 0; j < permStringArray.length; j++) {
                if (parts2[i].contains(permStringArray[j])) { //building arrays for keyword, value and operator for nearest statement

                    String[] test = parts2[i].split(permStringArray[j]);
                    array00[i] = test[0];
                    array11[i] = permStringArray[j];
                    array22[i] = test[1];

                }
            }

        }
        // Refining the input query

        for (int i = 0; i < array0.length; i++) {
            for (int j = 0; j < array00.length; j++) {
                // if the inequality is of same type then go for the
                // maximum value one		
                if (array0[i].trim().equals(array00[j].trim())) {
                    if (array1[i].matches("<") || array1[i].matches(">")) {
                        if (array11[j].matches("<") ||
                            array11[j].matches(">")) {
                            if (array11[j].equalsIgnoreCase(array1[i]))
                                array22[j] = greater(array2[i], array22[j]);

                            else if ((array1[i].matches("<") ||
                                      array1[i].matches(">")) &&
                                     (array11[j].matches(">") ||
                                      array11[j].matches("<"))) {
                                String s4 =
                                    array00[j].trim() + " " + array11[j].trim() +
                                    " " + array22[j].trim() + " " + " ^ " +
                                    array0[i].trim() + " " + array1[i].trim() +
                                    " " + array2[i].trim();
                                if (checkCSV(s4)) {
                                    array22[j] =
                                            greater(array2[i], array22[j]);
                                    array11[j] = "<";
                                    temp = "^" + array00[j] + ">"; 
									temp += lesser(array2[i], array22[j]);
                                } else {
                                    array00[j] = "";
                                    array11[j] = "";
                                    array22[j] = "";
                                }
                            }
                        }

                    }
                }
            }
        }
        String auxString = "";
        for (int iter = 0; iter < array00.length; iter++) {
            if (iter == (array00.length - 1)) {
                auxString += array00[iter] + array11[iter] + array22[iter];
            } else {
                if (!array00[iter].equalsIgnoreCase("")) {
                    auxString += array00[iter] + array11[iter];
                    auxString += array22[iter] + " ^";
                }
            }

        }
        auxString += temp;
        String modifiedRelaxedQuery = auxString.replace("^ ^", "^");
        PrintOutput(modifiedRelaxedQuery);
        QueryServlet.relaxed_query = modifiedRelaxedQuery;
    }

    private static String lesser(String doubleStringOne,
                                 String doubleStringTwo) {
        if (Double.parseDouble(doubleStringOne) <
            Double.parseDouble(doubleStringTwo))
            return doubleStringOne;
        else
            return doubleStringTwo;
    }

    private static String greater(String doubleStringOne,
                                  String doubleStringTwo) {
        if (Double.parseDouble(doubleStringOne) >
            Double.parseDouble(doubleStringTwo))
            return doubleStringOne;
        else
            return doubleStringTwo;
    }

    // Return True/False depending on the number of elements in the dataset of
    // query results

    public static boolean checkCSV(String inputString) {
        String[] parts = inputString.split("\\^");

        //getting the input file, dataser.csv at the given filepath
        File datafile = new File(Filepath.FILEPATH + "dataset.csv");

        DataContext currentDataContext =
            DataContextFactory.createCsvDataContext(datafile);
        Schema sch = currentDataContext.getDefaultSchema();
        Table[] tablearray = sch.getTables();

        assert tablearray.length == 1;
        Table table = tablearray[0];

        ArrayList<Column> columnsArray = new ArrayList<Column>();

        // Initialize the select column. for this dataset  Class:  is the decision attribute.
        String selectColumn = "Class:";
        columnsArray.add(table.getColumnByName(selectColumn));

        // Create a Query Object
        Query queryObj = new Query();

        // Add the table name from where to select
        queryObj.from(table);

        // Add the select Column
        queryObj.select(selectColumn);

        // Add All the where columns to the query
        for (int myIter = 0; myIter < parts.length; myIter++) {
            queryObj.where(parts[myIter]);
        }
        DataSet outputDataSet = currentDataContext.executeQuery(queryObj);

        // If there is atleast One element in the output of the query execution return true
        // Else return false
        if (outputDataSet.next()) {
            return true;
        } else {
            return false;
        }
    }

    public static void PrintOutput(String inputString) {
        String[] parts = inputString.split("\\^");
        File datafile = new File(Filepath.FILEPATH + "dataset.csv");

        DataContext currentDataContext =
            DataContextFactory.createCsvDataContext(datafile);

        Schema sch = currentDataContext.getDefaultSchema();
        Table[] tablearray = sch.getTables();

        assert tablearray.length == 1;
        Table table = tablearray[0];

        ArrayList<Column> columnsArray = new ArrayList<Column>();

        // Initialize the select column. for this dataset  Class:  is the decision attribute.
        String colmn = "Class:";
        columnsArray.add(table.getColumnByName(colmn));

        // Create a Query Object
        Query queryObj = new Query();

        // Add the table name from where to select
        queryObj.from(table);

        // Add the select Column
        queryObj.select(colmn);

        // Add All the where columns to the query
        for (int myIter = 0; myIter < parts.length; myIter++) {
            queryObj.where(parts[myIter]);
        }

        // Execute the query and get the result dataset
        DataSet queryOutput = currentDataContext.executeQuery(queryObj);

        StringBuilder queryOutputString = new StringBuilder();
        while (queryOutput.next()) {
            // Get the current Row from the iterator
            Row row = queryOutput.getRow();

            // Get the value of the column. lass:  is the decision attribute.
            queryOutputString.append(queryOutput.getRow().getValue(table.getColumnByName("Class:")));

            // Add a new line char
            queryOutputString.append("\n");
        }
        QueryServlet.result = queryOutputString.toString();
    }
}
