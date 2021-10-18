import java.io.*;
import java.util.Scanner;
import java.lang.System;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.io.IOException;
import java.io.FileNotFoundException;

public final class Ex4 {
    private static final int MAXSIZE = 1000000;  // Max nr of words
    private static final int CUTOFF = 20;       // Cut-off for recursive quicksort

    public static void insertionSort (String[] w) {
		int j;
		for(int i = 1; i < w.length; i++){
			Comparable tmp = w[i];
			for (j = i; j > 0 && tmp.compareTo(w[j-1]) < 0; j--){
				w[j] = w[j-1];
			}
			w[j] = (String) tmp;
		}
    }
    
    public static void mergeSort(String[] w) {
		Comparable [] tmpArray = new Comparable[w.length];
		mergeSort(w, tmpArray, 0, w.length - 1);
    }

	private static void mergeSort(Comparable w[], Comparable [] tmpArray, int left, int right){
		if(left < right){
			int center = (left + right) / 2;
			mergeSort(w, tmpArray, left, center);
			mergeSort(w, tmpArray, center + 1, right);
			merge(w, tmpArray, left, center + 1, right);
		}
	}

	public static void merge(Comparable w[], Comparable [] tmpArray, int leftPos, int rightPos, int rightEnd){
		int leftEnd = rightPos - 1;
		int tmpPos = leftPos;
		int numElems = rightEnd - leftPos + 1;
		while(leftPos <= leftEnd && rightPos <= rightEnd){
			if(w[leftPos].compareTo(w[rightPos]) <= 0){
				tmpArray[tmpPos++] = w[leftPos++];
			} else {
				tmpArray[tmpPos++] = w[rightPos++];
			}
		}
		while(leftPos <= leftEnd){
			tmpArray[tmpPos++] = w[leftPos++];
		}
		while(rightPos <= rightEnd){
			tmpArray[tmpPos++] = w[rightPos++];
		}
		for(int i = 0; i < numElems; i++, rightEnd--){
			w[rightEnd] = tmpArray[rightEnd];
		}
	}


    public static void quickSort (String[] w) {

    }

    
    public static String[] readWords(String fileName) {
    	String[] words = new String[MAXSIZE];
    	int rowCount = 0; 
    	int wordCount = 0;
    	try {
	    BufferedReader myReader = new BufferedReader(new FileReader(fileName));
	    String inputLine, thisLine;

	    // Read lines until end of file 
	    while ((inputLine = myReader.readLine()) != null) {
		// Remove punctuation characters and convert to lower case
		//// Note: compound words will have the - removed !!!
		thisLine = inputLine.replaceAll("\\p{Punct}", "").toLowerCase();
		if (thisLine.length() !=0) {         // Skip empty lines
		    // Split the line into separate words on one or more whitespace
		    String[] w = thisLine.split("\\p{IsWhite_Space}+");
		    // Put the words in an array
		    for(String s:w){
			if (!s.isEmpty()) words[wordCount++] = s;  // Skip empty words, count nr of words
		    } 	
		    rowCount++;    // Count number of rows
		}
	    }
	    System.out.println();
	    System.out.println("Read " + rowCount + " rows and " + wordCount + " words");
	    // Return the words in an array of of length wordCound
	    return(java.util.Arrays.copyOfRange(words, 0, wordCount));
	}
	
	catch (IOException e) { // No file 
	    System.out.println("Error: " + e.getMessage());
	    return (null);
	}
    }


    public static void writeWords(String [] words, String fileName) {
	BufferedWriter bw = null;
	try {
	    File outputFile = new File(fileName);
	    outputFile.createNewFile();        // Create the output file
	    FileWriter fw = new FileWriter(outputFile);
	    bw = new BufferedWriter(fw);
	    for (String s:words) {       // Write the words to the file
		bw.write(s + " ");     // 
	    }
	    bw.newLine();	    
	    System.out.println("Wrote file " + fileName);

	} catch (IOException e) {
	    System.out.println ("No file " + fileName);
	    System.exit(0);
	}
	finally {
	    try {
		if (bw != null) bw.close();
	    }
	    catch (Exception ex) {
		System.out.println("Error in closing file " + fileName);
	    }
	}
    }
    
    public static void main(String[] args) {
	// Check that a file name is given as an argument
	if (args.length != 1 ) {
	    System.out.println("Please give the file name");
	    System.exit(0);
	}
	String fileName = args[0];   // Get the file name from the argument
	
	// Read the words from the input file
	String[] words = readWords(fileName);
	if (words == null) System.exit(0);     // Quit if file is not found

	// Make copies of the input for each sorting method
	String[] words1 = Arrays.copyOf(words, words.length);
	String[] words2 = Arrays.copyOf(words, words.length);
	String[] words3 = Arrays.copyOf(words, words.length);
	
	System.out.println();
	System.out.println("Sorting with Insertion sort ");
	// Test the insertion sort method and measure how long it takes
	long startTime = System.nanoTime();
	insertionSort(words1);
	long estimatedTime = System.nanoTime() - startTime;
	System.out.println("Time for InsertionSort: " + estimatedTime/1000000000.0 + " seconds");
	// Write the result of insertion sort to a new file
	writeWords(words1, fileName + ".InsertionSort" );

	System.out.println();
	System.out.println("Sorting with MergeSort ");
	// Test the MergeSort method
	startTime = System.nanoTime();
	mergeSort(words2);
	estimatedTime = System.nanoTime() - startTime;
	System.out.println("Time for MergeSort: " + estimatedTime/1000000000.0 + " seconds");
	writeWords(words2, fileName + ".MergeSort" );

	
	// Test the quicksort method and measure how long it takes
	System.out.println();
	System.out.println("Sorting with Quicksort ");
	startTime = System.nanoTime();
	quickSort(words3);
	estimatedTime = System.nanoTime() - startTime;
	System.out.println("Time for QuickSort: " + estimatedTime/1000000000.0 + " seconds");
	// Write the result of quicksort to a new file
	writeWords(words3, fileName + ".QuickSort" );
	System.out.println();
	System.out.println();
		
    }    
}
