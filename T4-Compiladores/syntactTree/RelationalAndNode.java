package syntactTree;

import parser.*;


public class RelationalAndNode extends ExpreNode {
    public ExpreNode expr1;

    public RelationalAndNode(Token t, ExpreNode e1) {
        super(t);
        expr1 = e1;
    }

}