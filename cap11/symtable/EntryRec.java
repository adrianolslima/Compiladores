/*--- Programa 8.6 ---*/

package symtable;

// lista de EntryClass, usada para representar os tipos de uma lista
// de parametros
public class EntryRec extends EntryTable {

	public EntryTable type; // tipo de um objeto
	public int dim; // dimensao
	public EntryRec next; // apontador para o resto da lista
	public int cont; // numero de elementos a partir daquele elemento
	public boolean opcional;

	// cria elemento
	public EntryRec(EntryTable p, int d, int c) {
		type = p;
		cont = c;
		dim = d;
		next = null;
	}

//	// cria elemento
//	public EntryRec(EntryTable p, int d, int c, boolean op) {
//		type = p;
//		cont = c;
//		dim = d;
//		next = null;
//		opcional = op;
//	}

	// cria elemento e poe no inicio da lista
	public EntryRec(EntryTable p, int d, int c, EntryRec t, boolean op) {
		type = p;
		cont = c;
		dim = d;
		next = t;
		opcional = op;
	}

	// cria elemento e p�e no in�cio da lista
	public EntryRec(EntryTable p, int d, int c, EntryRec t) {
		type = p;
		cont = c;
		dim = d;
		next = t;
	}

	// devolve descritor da EntryRec
	public String dscJava() {
		String s;

		s = strDim(dim);
		s += type.dscJava();

		if (next != null) {
			s += next.dscJava();
		}

		return s;
	}

	// devolve a representação da EntryRec na forma de string
	public String toStr() {
		String s;

		s = type.name;

		for (int i = 0; i < dim; i++)
			s += "[]";

		if (next != null) {
			s += (", " + next.toStr());
		}

		return s;
	}

	public EntryRec inverte() {
		EntryRec r = this;

		cont = 1;

		if (next != null) {
			r = next.inverte(this);
		}

		next = null;

		return r;
	}

	// inverte a lista de EntryRec
	public EntryRec inverte(EntryRec ant) {
		EntryRec r = this;

		if (next != null) {
			r = next.inverte(this);
		}

		cont = ant.cont + 1;
		next = ant;

		return r;
	}
}
