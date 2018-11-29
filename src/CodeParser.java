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
import java.io.IOException;
import java.util.TreeMap;


public class CodeParser {

	Tokenizer _myTokenizer;
	AVLTree<String> _reserveWordsTree;
	
	// constructor
	/**
	 * 
	 */
	public CodeParser(BufferedReader inputStream) {
		super();
		// TODO Auto-generated constructor stub
		this._myTokenizer = new Tokenizer(inputStream);
	}
	 
    void setInputStream (BufferedReader input) {
    	_myTokenizer.setInputStream(input);
    }

    BufferedReader getInputStream() {
    	return _myTokenizer.getInputStream();
    }
    
    void setReserveWordsTree (AVLTree<String> aTree) {
    	_reserveWordsTree = aTree;
    }
    
    AVLTree<String> getReserveWordsTree() {
		return _reserveWordsTree;
    	
    }
	
	// parse
    TreeMap<String, Integer> parse() throws IOException {
		
		String token = null;
		TreeMap<String, Integer> identifiersTable = new TreeMap<String, Integer>();

		
		while((token = _myTokenizer.getToken())!= null) {
			//determine if the token is a number
			//skip if a number
			try {
				Integer.valueOf(token);
				//System.out.println("We think " + token + " is a number.");
				continue;
			} catch(NumberFormatException e) {
				//swallow NumberFormatException if the token is not a number
			}
			
			//System.out.println("The token is: " + token);
			//decide whether tokens are reserved words in the Java language, or identifiers
			if(_reserveWordsTree.find(token) == null) {
				//increment of this.token in the identifiersTable
				Integer frequency = identifiersTable.get(token);
				if (frequency == null) { 				// add new word to table       
					identifiersTable.put(token, new Integer(1));     
				}
				else { 									// increment count of existing word; replace wordTable entry       
					frequency++;
					identifiersTable.put(token, frequency);
				}
			} //else {
			//	System.out.println("Found reserve word: " + token);
			//}
			
		}
		
		return identifiersTable;
		
	}
}
