/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clrcoalator;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Gargarot
 */
public class CLRCoalator {

    static final String NAMEFILE = "C:/Users/Gargarot/Documents/8hCo2.csv";
    static final String SOURCEFILE = "C:/Users/Gargarot/Documents/8hCLR.csv";
    static final String OUTPUTFILE = "C:/Users/Gargarot/Documents/CLR8h1Col.csv";
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException, Exception {
        //Declerations
        FileInputStream in = null;
        PrintWriter outStream = null;
        BufferedReader inReader = null;
        
        FileInputStream names = null;
        BufferedReader namesReader = null;
        
        String line = null;
        ArrayList<ArrayList<String>> inTable = null;
        ArrayList<String> nameList = null;
        
        int r, c = 0;
        ArrayList<ArrayList<String>> outTable = null;
        
        try{
            //Import file
            in = new FileInputStream(SOURCEFILE);
            inReader = new BufferedReader(new InputStreamReader(in));
            
            //Parse to ArrayList table
            inTable = new ArrayList();
            //Remove first line
            line = inReader.readLine();
            line = inReader.readLine();
            //Cycle through every line
            int count = 0;
            while(line != null) {
                //Split Line
                String[] tempArray = line.split(",");
                //Convert to ArrayList
                ArrayList<String> tempArrayList = new ArrayList(Arrays.asList(tempArray));
                //Add to table
                inTable.add(tempArrayList);
                System.out.print("Line " + count + " added.\n");
                count++;
                line = inReader.readLine();
            }
            
            inReader.close();
            names = new FileInputStream(NAMEFILE);
            namesReader = new BufferedReader(new InputStreamReader(names));
            
            //Create list of gene names
            line = namesReader.readLine();
            line = namesReader.readLine();
            nameList = new ArrayList();
            count = 0;
            while(line != null) {
                //Split line
                String[] tempArray = line.split(",");
                //Add gene to list
                nameList.add(tempArray[0]);
                System.out.print("Name " + count +" added.\n");
                count++;
                line = namesReader.readLine();
            }
            
            namesReader.close();
            outStream = new PrintWriter(OUTPUTFILE, "UTF-8");
            outStream.print("");
            
            //Make sure the correct number of items are in tables
            if(nameList.size() != inTable.size()) {
                throw new Exception("Table sizes don't match.");
            }
            
            //Rearange
            count = 0;
            int start = 0;
            for(c = 1; c < inTable.get(0).size(); c++){
                for(r = start; r < inTable.size(); r++){
                    float temp = Float.valueOf(inTable.get(r).get(c));
                    if(temp != 0.0) {
                        outStream.write( '"' + nameList.get(c-1) + '"');
                        outStream.write(",");
                        outStream.write('"' + nameList.get(r) + '"');
                        outStream.write(",");
                        outStream.write(inTable.get(r).get(c));
                        outStream.write("\n");
                        System.out.print("Row " + r + " Column " + c +" Line " + count + " written.\n");
                    } else {
                        System.out.print("Line " + count + " value 0.\n");
                    }
                    
                    count++;
                }
                start++;
            }
            
        }catch(Exception e){
            System.err.print(e.getMessage());
        }finally{
            //Close files
            if (inReader != null) inReader.close();
            if (in != null) in.close();
            if (outStream != null) outStream.close();
        }
    }
    
}
