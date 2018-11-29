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


public class Tokenizer {

	String _currentLineString;//To hold the line that it reads in
	int _currentCharacterIndex;//To hold which character the counter is at
	Boolean _inComment;
	Boolean _inLineComment;
	Boolean _inString;
	BufferedReader _inputStream;

	//constructor
	/**
	 * @param inputStream
	 */
	public Tokenizer(BufferedReader inputStream) {
		super();
		setInputStream(inputStream);
	}
	
	private void setInitialState(){
		this._currentLineString = null;
		this._inComment = false;
		this._inLineComment = false;
		this._inString = false;
		this._currentCharacterIndex = -1;
	}
	
	// setter/getter for inputstream
	
	 
    void setInputStream (BufferedReader input) {
       if (_inputStream != input){
    	   _inputStream = input;
           setInitialState();
       }
    }

    BufferedReader getInputStream() {
    return _inputStream;
    }
	
    
	String getToken() throws IOException {
		String tokenString = "";
		Boolean tokenAccepted = false;
		char character;
		
		while (tokenAccepted == false) {
			character = getNextCharacter();
			//System.out.println("Char " + character + " " + (int)character);
			
			switch (character) {
			case 0:
				return null;
				
			case '"':
				_inString = !_inString;
				break;
				
			case '\n':
				//System.out.println("new line");
				_inLineComment = false;
				break;
				
			case '{':
			case '}':
			case '[':
			case ']':
			case ';':
			case '.':
			case '(':
			case ')':
			case '+':
			case '-':
			case '%':
			case '&':
			case '<':
			case '>':
			case '^':
			case '=':
			case '|':
			case '!':
			
				
				if (_inString == true || _inComment == true || _inLineComment == true) {
					break;
				}
				if (tokenString.length() > 0) {
					//System.out.println("Punctuation " + character + " Accepting " + tokenString);
					// we have a valid token
					tokenAccepted = true;
				} else {
					//System.out.println("no tokenString to accept");
				}
				break;
				
			case '/':
				// check that this is a comment
				if (_inString == true || _inComment == true || _inLineComment == true) {
					break;
				}
				character = getNextCharacter();
				switch (character) {
				case '*':
					_inComment = true;
					break; 

				case '/':
					_inLineComment = true;
					break;
					
				default:
					//call a method to back up
					goBackOneCharacter();
					if (tokenString.length() > 0) {
						tokenAccepted = true;
					}
				}// check if comment
				break;
				
			case '*':
				// check that this is a comment
				if (_inString == true || _inLineComment == true) {
					break;
				}
				character = getNextCharacter();
				switch (character) {
			

				case '/':
					_inComment = false;
					break;
					
				default:
					//call a method to back up
					goBackOneCharacter();
					if (_inComment == true) {
						break;
					}
					if (tokenString.length() > 0) {
						tokenAccepted = true;
					}
				}// check if comment
				break;
				
				
		

			default:

				if(_inComment == false && _inLineComment == false && _inString == false){
					if(!Character.isWhitespace(character)) {
						tokenString = tokenString.concat(String.valueOf(character));
					} else {
						if (tokenString.length() > 0) {
							tokenAccepted = true;
						}
					}
					
				}
					break;

			
			}//end of switch character

		}
		return tokenString;
	}
	
	// get next character
	
	private char getNextCharacter() throws IOException {
		char nextCharacter = 0;
		if(_inputStream != null) {
			//no currentStringLine, go get next line
			if(_currentLineString == null) {
				_inputStream.mark(4096);
				_currentLineString = _inputStream.readLine();
				if(_currentLineString != null) {
					//Once you have a line, set characterCounter to the first character, 0
					_currentCharacterIndex = 0;
					
				} else {
					return 0;
				}
				
			}
			
			if (_currentCharacterIndex > _currentLineString.length()) {
				//Assumption that maximum length of a line is 4096 characters
				_inputStream.mark(4096);
				_currentLineString = _inputStream.readLine();
				if(_currentLineString != null) {
					//Once you have a line, set characterCounter to the first character, 0
					_currentCharacterIndex = 0;
				} else {
					return 0;
				}
			}
			//detect end of line and if the new line is just a blank line
			//when the index = length of string you know that the line ends
			if (_currentCharacterIndex == _currentLineString.length()) {
				nextCharacter = '\n';
			} else {
				
				nextCharacter = _currentLineString.charAt(_currentCharacterIndex);
			}
			
			_currentCharacterIndex++;
		}
		
		return nextCharacter;
	}
	
	private void goBackOneCharacter() throws IOException {
		if(_inputStream != null || _currentLineString != null || _currentCharacterIndex >= 0) {
			if(_currentCharacterIndex == 0) {//At the beginning of the line
				_inputStream.reset();//Has to go back a line
				_currentCharacterIndex = _currentLineString.length();
				
			} else {
				_currentCharacterIndex--;
			}
		}
		
	}
}
