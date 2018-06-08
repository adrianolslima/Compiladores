package symtable;

//classe geral para as possiveis entradas na tabela de simbolos
abstract public class EntryTable {
 public String name; // nome do simbolo (var., metodo ou classe)
 public EntryTable next; // apontador para proximo dentro da tabela 
 public int scope; // numero do aninhamento corrente
 public Symtable mytable; // entrada aponta para a tabela da qual ela e parte
 
 //Programa 9.4 do livro
 
 EntryTable p = top;
 
 //Para cada elemento da tabela corrente
 
 public EntryTable classFindUp (String x)
 {
	 while (p != null)
	 {
		 //verifica se é uma entrada de classe ou tipo simples e então compara o nome
		 if(((p instanceof EntryClass) || (p instanceof EntrySimple) &&
				 p.name.equals(x)))
			 return p;
		 
		 p = p.next; //Próxima entrada
	 }
	 if (levelup == null) // se não achou e é o npivel mais externo
		 return null;
	 
	 return levelup.mytable.classFindUp(x);
 } 
}

//Fim Programa 9.4 do livro
