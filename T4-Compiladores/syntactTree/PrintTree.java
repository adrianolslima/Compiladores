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

	private void numberClassDeclListNode(ListNode x) {
		// TODO Auto-generated method stub
		
	}
	
	
	/*--- (Métodos para exibir os nós) ---*/
	
	private void printClassDeclListNode(ListNode x) {
		// TODO Auto-generated method stub
		
	}

}
