package syntactTree;

import parser.*;


public class RelationalNotNode extends ExpreNode {
    public ExpreNode expr1;

    public RelationalNotNode(Token t, ExpreNode e1) {
        super(t);
        expr1 = e1;
    }

}