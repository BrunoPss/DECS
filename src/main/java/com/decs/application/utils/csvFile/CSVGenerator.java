package com.decs.application.utils.csvFile;

import com.decs.application.data.generation.Generation;
import com.decs.application.utils.constants.FilePathConstants;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class CSVGenerator {
    //Internal Data
    private static final String GENERATION_CSV_HEADER = "Generation,Fitness\n";

    //Constructor
    private CSVGenerator() {}

    //Get Methods


    //Set Methods


    //Methods
    public static String generation2CSVFormat(ArrayList<Generation> list) {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append(GENERATION_CSV_HEADER);

        for (Generation generation : list) {
            strBuilder.append(generation.getGeneration()).append(",")
                      .append(generation.getFitness()).append("\n");
        }

        return strBuilder.toString();
    }
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