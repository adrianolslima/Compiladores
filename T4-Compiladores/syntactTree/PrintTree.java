// Programa 7.2

package syntactTree;

import jdk.nashorn.internal.codegen.CompilerConstants.Call;
import parser.*;

public class PrintTree {

	int kk;

	public PrintTree() {
		
		kk = 1; // inicializa contador de nós
	}
	
	public void printRoot(ListNode x) {
		
		if (x == null)
			System.out.println("Empty syntatic tree. Nothing to be printed.");
		else {
			numberClassDeclListNode(x);
			printClassDeclListNode(x);
		}
		System.out.println();
	}

	/*--- Métodos para numerar os nós ---*/

	/*--- Programa 7.6 ---*/
	public void numberClassDeclListNode(ListNode x) {

		if (x == null) return;
		x.number = kk++;
		numberClassDeclNode((ClassDeclNode) x.node);
		numberClassDeclListNode(x.next);
	}
	
	/*--- Programa 7.4 ---*/
	public void numberClassBodyNode(ClassBodyNode x) {
		
		if (x == null) return;
		x.number = kk++;
		numberClassDeclListNode(x.clist);
		numberVarDeclListNode(x.vlist);
		numberConstructDeclListNode(x.ctlist);
		numberMethodDeclListNode(x.mlist);
	}
	
	/*--- Programa 7.5 ---*/
	public void numberListNode(ListNode x) {
		
		if (x == null) return;
		//numer x.node
		numberListNode(x.next);
	}
	
	/*--- Programa 7.7 ---*/
	public void numberMethodBodyNode(MethodBodyNode x) {
		
		if (x == null) return;
		x.number = kk++;
		numberVarDeclListNode(x.param);
		numberStatementNode(x.stat);
	}

	/*--- Programa 7.8 ---*/	
	public void numberStatementNode(StatementNode x) {

		if (x instanceof BlockNode)
			numberBlockNode((BlockNode) x);
		else if (x instanceof VarDeclNode)
			numberVarDeclNode((VarDeclNode) x);
		else if (x instanceof AtribNode)
			numberAtribNode((AtribNode) x);
		else if (x instanceof IfNode)
			numberIfNode((IfNode) x);
		else if (x instanceof ForNode)
			numberForNode((ForNode)x);
		else if (x instanceof PrintNode)
			numberPrintNode((PrintNode) x);
		else if (x instanceof NopNode)
			numberNopNode((NopNode) x);
		else if (x instanceof ReadNode)
			numberReadNode((ReadNode) x);
		else if (x instanceof ReturnNode)
			numberReturnNode((ReturnNode) x);
		else if (x instanceof SuperNode)
			numberSuperNode((SuperNode) x);
		else if (x instanceof BreakNode)
			numberBreakNode((BreakNode) x);
			
	}
	
	/*--- Programa 7.9 ---*/	
	public void numberIfNode(IfNode x) {

		if (x == null) return;
		x.number = kk++;
		numberExpreNode(x.expr);
		numberStatementNode(x.stat1);
		numberStatementNode(x.stat2);
	}
	
	/*--- Programa 7.10 ---*/	
	public void numberExpreNode(ExpreNode x) {
		
		if (x instanceof NewObjectNode)
			numberNewObjectNode((NewObjectNode) x);
		else if (x instanceof NewArrayNode)
			numberNewArrayNode((NewArrayNode) x);
		else if (x instanceof RelationalNode)
			numberRelationalNode((RelationalNode) x);
		else if (x instanceof AddNode)
			numberAddNode((AddNode) x);
		else if (x instanceof MultNode)
			numberMultNode((MultNode) x);
		else if (x instanceof UnaryNode)
			numberUnaryNode((UnaryNode) x);
		else if (x instanceof CallNode)
			numberCallNode((CallNode) x);
		else if (x instanceof IntConstNode)
			numberIntConstNode((IntConstNode) x);
		else if (x instanceof StringConstNode)
			numberStringConstNode((StringConstNode) x);
		else if (x instanceof FloatConstNode)
			numberFloatConstNode((FloatConstNode) x);
		else if (x instanceof BooleanConstNode)
			numberBooleanConstNode((BooleanConstNode) x);
		else if (x instanceof CharConstNode)
			numberCharConstNode((CharConstNode) x);
		else if (x instanceof NullConstNode)
			numberNullConstNode((NullConstNode) x);
		else if (x instanceof IndexNode)
			numberIndexNode((IndexNode) x);
		else if (x instanceof DotNode)
			numberDotNode((DotNode) x);
		else if (x instanceof VarNode)
			numberVarNode((VarNode) x);
	}

	/*--- (Métodos para exibir os nós) ---*/
	
	public void printClassDeclListNode(ListNode x) {
		// TODO Auto-generated method stub
		
	}

}
