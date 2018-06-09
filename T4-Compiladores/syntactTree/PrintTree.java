// Programa 7.2

package syntactTree;

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

	/*--- Programa 7.4 ---*/
	public void numberClassBodyNode(ClassBodyNode x) {
		
		if (x == null) return;
		x.number = kk++;
		numberClassDeclListNode(x.clist);
		numberVarDeclListNode(x.vlist);
		numberConstructDeclListNode(x.ctlist);
		numberMethodDeclListNode(x.mlist);
	}
	
	/*--- (Métodos para exibir os nós) ---*/
	
	public void printClassDeclListNode(ListNode x) {
		// TODO Auto-generated method stub
		
	}

}