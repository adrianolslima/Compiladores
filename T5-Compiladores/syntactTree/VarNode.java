// Programa 6.12

package syntactTree;

import parser.*;

public class VarNode extends ExpreNode {
	
	public int dim;
	public Token atrib;
	ExpreNode exp;

	public VarNode(Token t) {
		super(t);
		dim = 0;
	}

	public VarNode(Token t, int k) {
		super(t);
		dim = k;
	}

	public VarNode(Token t, int k, Token t2, ExpreNode e) {
		super(t);
		dim = k;
		atrib = t2;
		exp = e;
	}
}
