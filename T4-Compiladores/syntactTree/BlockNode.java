// Programa 6.22

package syntactTree;

import parser.*;

public class BlockNode extends StatementNode {

	public ListNode stats;

	public BlockNode(Token t, ListNode l) {
		super(t);
		stats = l;
	}
}
