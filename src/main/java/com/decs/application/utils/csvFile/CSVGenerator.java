package com.decs.application.utils.csvFile;

import com.decs.application.data.generation.Generation;
import com.decs.application.utils.constants.FilePathConstants;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * <b>CSV Generator Class</b>
 * <p>
 *     This class handles the generation of CSV files.
 * </p>
 * @author Bruno Guiomar
 * @version 1.0
 */
public class CSVGenerator {
    //Internal Data
    private static final String GENERATION_CSV_HEADER = "Generation,Fitness\n";

    /**
     * Class Private Constructor
     * <p>This class cannot be instantiated.</p>
     */
    private CSVGenerator() {}

    //Get Methods


    //Set Methods


    //Methods
    /**
     * Converts a list of Generation objects to the textual CSV format
     * @param list List of Generation objects
     * @return Textual representation following the CSV format
     */
    public static String generation2CSVFormat(ArrayList<Generation> list) {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append(GENERATION_CSV_HEADER);

        for (Generation generation : list) {
            strBuilder.append(generation.getGeneration()).append(",")
                      .append(generation.getFitness()).append("\n");
        }

        return strBuilder.toString();
    }

    /**
     * Generates a CSV file from a list of Generation objects
     * @param list List of Generation objects
     * @param filename Name of the output file
     */
    public static void generateCSVFile(ArrayList<Generation> list, String filename) {
        try {
            File csvFile = new File(FilePathConstants.JOB_TABLES_FOLDER + "/" + filename);
            PrintWriter pWriter = new PrintWriter(csvFile);

            pWriter.write(generation2CSVFormat(list));

            pWriter.close();
        } catch (IOException e) {
            System.err.println("IO Excpetion");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Excpetion in generateCSVFile");
            e.printStackTrace();
        }
    }

    //Overrides


    //Internal Functions


}