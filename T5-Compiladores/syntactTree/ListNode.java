// Programa 6.5

package syntactTree;

public class ListNode extends GeneralNode {

	public GeneralNode node;
	public ListNode next;

	public ListNode(GeneralNode t2) {
		super(t2.position);
		node = t2;
		next = null;
	}

	public ListNode(GeneralNode t2, ListNode l) {
		super(t2.position);
		node = t2;
		next = l;
	}

	public void add(GeneralNode t2) {
		if (next == null) // Verifica se eh ultimo
			next = new ListNode(t2); // Insere no final
		else
			next.add(t2); // insere após o próximo

	}

}
