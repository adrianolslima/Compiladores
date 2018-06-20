// Programa 6.35

package syntactTree;

import parser.*;

public class NewArrayNode extends ExpreNode {

	public Token tipo;
	public ListNode dims;

	public NewArrayNode(Token t, Token t2, ListNode d) {
		super(t);
		tipo = t2;
		dims = d;
	}
}
