//Programa 6.40

package syntactTree;

import parser.*;


public class SuperNode extends StatementNode {
    public ListNode args;

    public SuperNode(Token t, ListNode p) {
        super(t);
        args = p;
    }
}
