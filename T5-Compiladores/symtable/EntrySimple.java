/*--- Programa 8.7 ---*/

package symtable;

// entrada utilizada para declarar os tipos b�sicos da linguagem
public class EntrySimple extends EntryTable {

	public EntrySimple(String n) {
		name = n;
	}

	public String dscJava() // devolve descritor de tipo
	{
		if (name.equals("int")) {
			return "I";
		} else if (name.equals("char")) {
			return "char";
		} else if (name.equals("float")) {
			return "float";
		} else if (name.equals("boolean")) {
			return "boolean";
		} else {
			return "Ljava/lang/String;"; // classe String JAVA
		}
	}
}
