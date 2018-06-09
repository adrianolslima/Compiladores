// Programa 6.11

package syntactTree;

import parser.*;

public class VarDeclNode extends StatementNode {
	public ListNode vars;

	public VarDeclNode(Token t, ListNode p) {
		super(t);
		vars = p;
	}

}