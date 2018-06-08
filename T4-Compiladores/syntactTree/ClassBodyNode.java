//Programa 6.9

package syntactTree;

import parser.*;


public class ClassBodyNode extends GeneralNode {
    public ListNode clist; // lista de classes aninhadas
    public ListNode vlist; // lista de variaveis da classe
    public ListNode aslist; //lista de atribuicoes de valores a variaveis
    public ListNode ctlist; // lista de construtores
    public ListNode mlist; // lista de metodos

    public ClassBodyNode(Token t1, ListNode c, ListNode v, ListNode as, ListNode ct,
        ListNode m) {
        super(t1); // passa token de referencia para construtor da superclasse
        clist = c;
        vlist = v;
        aslist = as;
        ctlist = ct;
        mlist = m;
    }
}