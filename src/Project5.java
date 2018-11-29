//////////////////////////////////////////////////////////////////////////////////////////////
//CMSC-256 Spring 2016
//
//Name: Brandon Chin
//
//(Project/Example): Programming Assignment 5, Program Parser
//
/*Description:
 * Due date: April 23, 2016
 * Purpose: To parse a given program and to remove all the keywords and punctuations and return the identifiers
 * in the program with their attached frequency
 * input: is a keywords file that is put into an AVLtree and also a .java file
 * output: an AVLtree that is sorted inorder. The height, the root, and the node count. Also included is the identifiers with attached frequency
*/
//
//
/////////////////////////////////////////////////////////////////////////////////////////////



import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;


public class Project5 {

	public static Scanner stdinScanner = null;

	public static void main(String[] args) throws IOException {
		printHeader();

		stdinScanner = new Scanner(System.in);

		//tree created
		AVLTree<String> reserveWordsTree = new AVLTree<String>();

		//		if (args.length != 2) {
		//
		//			System.out.println("Project 5 usage: [keywordfile] [javafile]\nPlease provide a [keywordfile] of reserved words and a [javafile] source code.\n");
		//			return;
		//		}

		Scanner in = null;
		String fileOneName = null;
		if(args.length > 0) {
			fileOneName = args[0];
		}
		Boolean file1Open = false;
		while(file1Open == false) {
			while (fileOneName == null || fileOneName.length() <= 0) {
				fileOneName = askForFile("keywordFile");
			}
			try{
				File newFile = new File(fileOneName);
				FileReader keyWords = new FileReader (newFile);
				in = new Scanner(keyWords);
				file1Open = true;
			} catch(FileNotFoundException e){
				System.out.println("Error: Unable to open [keywordfile].");
				fileOneName = null;
			}
		}

		//Adding reserve words to Reserve words tree
		while (in.hasNextLine()){

			String keyword = in.nextLine().trim();

			if (!keyword.equals("")) {
				reserveWordsTree.insert(keyword);
			}
		}

		in.close();

		System.out.println("\nThe tree contains " +  reserveWordsTree.count() 
				+ " unique items, as follows:");
		reserveWordsTree.printTree();

		System.out.println("\nThe node count is: " + reserveWordsTree.count());
		System.out.println("Root of the tree is: " + reserveWordsTree.rootNode());
		System.out.println("Height of the tree is (measured as the length of a path to the furthest leaf): " 
				+ reserveWordsTree.getHeight() + "\n");

		String fileTwoName = null;
		if(args.length > 1) {
			fileTwoName = args[1];
		}
		//For [javafile]
		//Process the [javafile]
		Boolean file2Open = false;
		BufferedReader inputStream = null;
		while(file2Open == false) {
			while (fileTwoName == null || fileTwoName.length() <= 0) {
				fileTwoName = askForFile("javaFile");
			}
			try{
				inputStream = new BufferedReader(new FileReader(fileTwoName));
				file2Open = true;

			}  catch(FileNotFoundException e){
				System.out.println("Error: Unable to open [javaFile].");
				fileTwoName = null;
			}
		}

		CodeParser parser = new CodeParser(inputStream);
		parser.setReserveWordsTree(reserveWordsTree);


		TreeMap<String, Integer> identifierFrequencyTable = parser.parse();

		//Print out the identifier frequency table

		Set<String> identifierSet = identifierFrequencyTable.keySet();
		Iterator<String> identifierIterator = identifierSet.iterator();

		System.out.println("The following tokens are not Java keywords in the file, " + fileTwoName + ":");

		while(identifierIterator.hasNext()) {
			String identifierString = identifierIterator.next();
			Integer identifierFrequency = identifierFrequencyTable.get(identifierString);

			System.out.println(identifierString + ": " + identifierFrequency);

		}

		stdinScanner.close();
	}

	public static String askForFile( String kindOfFile ) {
		System.out.println("Please provide a file name for " + kindOfFile + ": ");
		String fileName = stdinScanner.next();
		return fileName;
	}

	public static void printHeader() {
		System.out.println("Name: Brandon Chin\n" + "Project Number: 5\n" +
				"Course identifier: CMSC 256-001\n" + "Current Semester: Spring 2016\n");
	}
}
