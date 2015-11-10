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
        ArrayList<String> nameList = null;
        
        int col, count = 0;
        
        try{
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
            in = new FileInputStream(SOURCEFILE);
            inReader = new BufferedReader(new InputStreamReader(in));
            
            //Import and rearange
            line = inReader.readLine();
            line = inReader.readLine();
            count = 0;
            int row = 1;
            while(line != null){
                String[] input = line.split(",");
                System.out.println(input.length);
                for(col = row; col < input.length; col++){
                    float temp = Float.valueOf(input[col]);
                    if(temp != 0.0) {
                        outStream.write( '"' + nameList.get(row-1) + '"');
                        outStream.write(",");
                        outStream.write('"' + nameList.get(col-1) + '"');
                        outStream.write(",");
                        outStream.write(input[col]);
                        outStream.write("\n");
                        System.out.print("Line " + count + " Row " + (row-1) +" Column " + (col-1) + " written.\n");
                    } else {
                        System.out.print("Line " + count + " value 0.\n");
                    }
                    
                    count++;
                }
                row++;
                line = inReader.readLine();
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
