// Programa 6.39

package syntactTree;

import parser.*;

public class ReturnNode extends StatementNode {

	public ExpreNode expr;

	public ReturnNode(Token t, ExpreNode e) {
		super(t);
		expr = e;
	}
}