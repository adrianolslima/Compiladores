//Programa 6.15

package syntactTree;

import parser.*;


public class MethodDeclNode extends GeneralNode {
    public int dim;
    public Token name;
    public MethodBodyNode body;

    public MethodDeclNode(Token t, int k, Token t2, MethodBodyNode m) {
        super(t);
        dim = k;
        name = t2;
        body = m;
    }
}
