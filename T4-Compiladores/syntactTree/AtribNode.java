//Programa 6.25

package syntactTree;

import parser.*;


public class AtribNode extends StatementNode {
    public ExpreNode expr1, expr2;


    public AtribNode(Token t, ExpreNode e1, ExpreNode e2) {
        super(t);
        expr1 = e1;
        expr2 = e2;
    }
}
