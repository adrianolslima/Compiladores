/*--- Programa 8.1 ---*/

package symtable;

public class Symtable {
	
    public EntryTable top; // apontador para o topo da tabela (mais recente)
    public int scptr; // numero que controla o escopo (aninhamento) corrente
    public EntryClass levelup; // apontador para a entrada EntryClass de nivel sup.

	/*--- Programa 8.1 ---*/
	public Symtable() // cria uma tabela vazia
	{
		top = null;
		scptr = 0;
		levelup = null;
	}

	/*--- Programa 8.1 ---*/
	public Symtable(EntryClass up) // cria tabela vazia apontando para nivel sup.
	{
		top = null;
		scptr = 0;
		levelup = up;
	}

	/*--- Programa 8.1 ---*/
	public void add(EntryTable x) // adiciona uma entrada a tabela
	{
		x.next = top; // inclui nova entrada no topo
		top = x;
		x.scope = scptr; // atribui para a entrada o numero do escopo
		x.mytable = this; // faz a entrada apontar para a tabela a qual pertence
	}

	/*--- Programa 8.1 ---*/
	public void beginScope() {
		scptr++; // inicia novo aninhamento de variaveis
	}

	/*--- Programa 8.1 ---*/
	public void endScope() {
		while ((top != null) && (top.scope == scptr))
			top = top.next; // retira todas as variaveis do aninhamento corrente

		scptr--; // finaliza aninhamento corrente
	}
    
    /*--- Programa 9.4 ---*/
	public EntryTable classFindUp(String x) {
		
		EntryTable p = top;

		// para cada elemento da tabela corrente
		while (p != null) {
			// verifica se é uma entrada de classe ou tipo simples e compara o nome
			if (((p instanceof EntryClass) || (p instanceof EntrySimple)) && p.name.equals(x)) {
				return p;
			}

			p = p.next; // pr�xima entrada
		}

		if (levelup == null) { // se não achou e é o nível mais externo

			return null; // retorna null
		}

		// procura no nível mais externo
		return levelup.mytable.classFindUp(x);
	}

	/*--- Programa 10.7 ---*/
	/*
	 * Esse metodo procura o simbolo x com uma lista de parametros igual a r apenas
	 * na tabela, nao na(s) tabela(s) da(s) superclasse(s), apontada por
	 * levelup.parent. Procura por uma entrada do tipo EntryMethod
	 */
	public EntryMethod methodFindInclass(String x, EntryRec r) {
		EntryTable p = top;
		EntryClass q;

		// para cada entrada da tabela
		while (p != null) {
			// verifica se tipo é EntryMethod e compara o nome
			if (p instanceof EntryMethod && p.name.equals(x)) {
				EntryMethod t = (EntryMethod) p;

				// compara os parâmetros
				if (t.param == null) {
					
					if (r == null) return t;
					
				} else {
					if (t.param.equals(r)) {
						return t;
					}
				}
			}

			p = p.next; // próxima entrada
		}

		return null; // não achou
	}
    
    /* Esse metodo procura o simbolo x na tabela e tambem na(s) tabela(s) da(s)
    superclasse(s), apontada por levelup.parent. Procura por uma entrada do
    tipo EntryVar */
    public EntryVar varFind(String x) {
        return varFind(x, 1);
    }

	/*--- Programa 11.8 ---*/
	/*
	 * Esse metodo procura a n-esima ocorrencia do simbolo x na tabela e tambem
	 * na(s) tabela(s) da(s) superclasse(s), apontada por levelup.parent. Procura
	 * por uma entrada do tipo EntryVar
	 */
	public EntryVar varFind(String x, int n) {
		EntryTable p = top;
		EntryClass q;

		while (p != null) {
			if (p instanceof EntryVar && p.name.equals(x)) {
				if (--n == 0) {
					return (EntryVar) p;
				}
			}

			p = p.next;
		}

		q = levelup;

		if (q.parent == null) {
			return null;
		}

		return q.parent.nested.varFind(x, n);
	}

    /*--- Program 11.25 ---*/
    /* Esse metodo procura o simbolo x com uma lista de parametros igual a r na
    tabela e tambem na(s) tabela(s) da(s)  superclasse(s), apontada por
    levelup.parent. Procura por uma entrada do  tipo EntryMethod */
    public EntryMethod methodFind(String x, EntryRec r) {
    	
        EntryTable p = top;
        EntryClass q;

        while (p != null) {
            if (p instanceof EntryMethod && p.name.equals(x)) {
                EntryMethod t = (EntryMethod) p;

                if (t.param == null) {
                    if (r == null) {
                        return t;
                    }
                } else {
                    if (t.param.equals(r)) {
                        return t;
                    }
                }
            }

            p = p.next;
        }

        q = levelup;

        if (q.parent == null) {
            return null;
        }

        return q.parent.nested.methodFind(x, r);
    }

}
