package KBS;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eobjects.metamodel.DataContext;
import org.eobjects.metamodel.DataContextFactory;
import org.eobjects.metamodel.data.DataSet;
import org.eobjects.metamodel.query.Query;
import org.eobjects.metamodel.schema.Column;
import org.eobjects.metamodel.schema.Schema;
import org.eobjects.metamodel.schema.Table;

import weka.classifiers.rules.PART;
import weka.classifiers.trees.J48;

import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;

import QASPackage.Equals;
import QASPackage.Greaterthan;
import QASPackage.GreaterthanEquals;
import QASPackage.Lessthan;
import QASPackage.LessthanEquals;
import QASPackage.NotEquals;
import QASPackage.Operator;


public class QueryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public static String result = "";
    public static String relaxed_query = "";
    public static String rulesoutput = "";
    public static String inputquery = "";

    /**
     * Default constructor.
     */
    public QueryServlet() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException,
                                                              IOException {
        // TODO Auto-generated method stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException,
                                                               IOException {

        String temp = request.getParameter("query");
        String inputquery = temp.replace(">=", ">").replace("<=", "<");
        String auxStringArray[] = { "<", ">=", ">", "<=", "<>", "=" }, s1;

        String[] parts = inputquery.split("\\^");
        String keyword[] = new String[parts.length];
        String operator[] = new String[parts.length];
        String value[] = new String[parts.length];
		
        //splitting the input query into three parts 
		// 1) Attribute name
		// 2) The operatot and 
		// 3) The values specified for each attribute in the query
		int lengthOfPartsArray = parts.length;
		int lengthOfAuxStringArray = auxStringArray.length;
        for (int i = 0; i < lengthOfPartsArray; i++) {
            for (int j = 0; j < lengthOfAuxStringArray; j++) {
                if (parts[i].contains(auxStringArray[j])) {
                    String[] tempArray = parts[i].split(auxStringArray[j]);
                    keyword[i] = tempArray[0].trim();
                    operator[i] = auxStringArray[j].trim();
                    value[i] = tempArray[1].trim();
                }
            }
        }

		// Get the instance of the QASystem
        QASystem qas = new QASystem();
        for (int iter = 0; iter < keyword.length; iter++) {
            try {
                qas.createduplicatedataset(keyword[iter], keyword, value[iter],
                                           operator[iter], inputquery);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        try {
            qas.rulesFromWeka(keyword, value, operator, inputquery);
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("result", result);
        request.setAttribute("relaxed_query", relaxed_query);
        request.setAttribute("rulesoutput", rulesoutput);
        RequestDispatcher dispatcher =
            getServletContext().getRequestDispatcher("/output.jsp");
        dispatcher.forward(request, response);

    }
}
