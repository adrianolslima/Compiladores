// Programa 7.3

package syntactTree;

import parser.*;

abstract public class GeneralNode {

	public Token position;
	public int number;

	public GeneralNode(Token x) {
		
		position = x;
		number = 0;
	}

}
