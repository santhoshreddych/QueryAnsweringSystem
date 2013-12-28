package KBS;
import java.util.*;

import java.io.*;

public class FindNearest {

    // Find Distance accepts a Vecotor and a String
    // It return the index of the minimum distance

    public static int findDistance(Vector v,
                                   String inputString) throws IOException,
                                                              FileNotFoundException {
        String s3[] = { "<", ">=", ">", "!=", "<=" }, s1;

        // Initializing minimDistance with a very Large initial Value
        double minimumDistanceValue = 999999;

        // Generate the iparts String Array
        // by splitting the Input String
        String[] iparts = inputString.split("\\^");
        String keyword[] = new String[iparts.length];
        String operator[] = new String[iparts.length];
        String value[] = new String[iparts.length];

        for (int i = 0; i < iparts.length; i++) {
            for (int j = 0; j < s3.length; j++) {
                if (iparts[i].contains(s3[j])) {
                    // partitioning the rules into different arrays
                    // Creating a temp String Array
                    String[] tempStrinArray = iparts[i].split(s3[j]);
                    keyword[i] = tempStrinArray[0].trim();
                    operator[i] = s3[j].trim();
                    value[i] = tempStrinArray[1].trim();
                }
            }
        }

        // Generating the auxilary Distance Array
        double[] distancearray = new double[v.size()];

        double temp;
        String nextLine;
        int i = 0;

        //building hash map for keyword

        i = 0;
        Enumeration<String> en = v.elements();

        // Initializing the enumeration iterator with value 1
        int init = -1;
        while (en.hasMoreElements()) {
            init++;

            //getting the rule string from vector
            s1 = en.nextElement().toString().replace("AND", "^");
            String[] parts = s1.split("\\^");
            String k_array[] = new String[parts.length], o_array[] =
                new String[parts.length], v_array[] = new String[parts.length];


            for (i = 0; i < parts.length; i++) {
                for (int j = 0; j < s3.length; j++) {
                    if (parts[i].contains(s3[j])) // partitioning the rules into different arrays
                    {
                        String[] test = parts[i].split(s3[j]);
                        k_array[i] = test[0];
                        o_array[i] = s3[j];
                        v_array[i] = test[1];
                    }
                }
            }
            double dist;

            String key;

            // Declare the keywords Hashmap
            HashMap<Integer, String> keywords = new HashMap();

            // Read the keywords.txt file
            BufferedReader br =
                new BufferedReader(new FileReader(Filepath.FILEPATH +
                                                  "Keywords.txt"));

            // Loop through all the lines from Buffered Reader
            while ((nextLine = br.readLine()) != null) {
                keywords.put(i, nextLine);
                i++;
            }

            i = 0;

            //Build the max value HashMap
            HashMap<String, Double> maxvalue = new HashMap();

            // Read the maxvalues.txt file
            BufferedReader br1 =
                new BufferedReader(new FileReader(Filepath.FILEPATH +
                                                  "Maxvalues.txt"));
            // Loop through all the lines from Buffered Reader
            while ((nextLine = br1.readLine()) != null) {
                maxvalue.put(keywords.get(i), Double.parseDouble(nextLine));
                i++;
            }

            i = 0;

            //Build the min value HashMap
            HashMap<String, Double> minvalue = new HashMap();

            // Read the minvalues.txt file
            BufferedReader br2 =
                new BufferedReader(new FileReader(Filepath.FILEPATH +
                                                  "Minvalues.txt"));
            // Loop through all the lines from Buffered Reader
            while ((nextLine = br2.readLine()) != null) {
                minvalue.put(keywords.get(i), Double.parseDouble(nextLine));
                i++;
            }

            // Iterate through all the kewords in the keyword array
            for (i = 0; i < keyword.length; i++) {
                key = keyword[i];

                // Calculating the distance using the formula
                // |Value(ConstrCk(Aj))-Value(ConstrS(Aj))|/(MaxAj-MinAj)
                // || represents the mod of the value

                for (int j = 0; j < k_array.length; j++) {
                    if (k_array[j].trim().equalsIgnoreCase(key)) {
                        temp = Double.parseDouble(v_array[j]);
                        dist =
(Math.abs(Double.parseDouble(value[i]) - temp)) /
 (maxvalue.get(key) - minvalue.get(key));
                        distancearray[init] = distancearray[init] + dist;
                    }
                }
            }
        }

        // Declare the integer value to be returned
        int indexToBeReturned = 0;

        // Loop through the entire distance array populated earlier
        // Calculate the mimimum distance among along values populated
        // Return the index of the minimDistance
        for (int iter = 0; iter < distancearray.length; iter++) {
            if ((distancearray[iter] < minimumDistanceValue)) {
                minimumDistanceValue = distancearray[iter];

                //Store the index of the minimum distance
                indexToBeReturned = iter;
            }
        }
        return indexToBeReturned;
    }
}
