package symtable;

//classe corresponde a uma declaracao de classe na tab. de simbolos
public class EntryClass extends EntryTable {
 public Symtable nested; // tabela para declaracao de elementos aninhados
 public EntryClass parent; // entrada correspondente a superclasse

 public EntryClass(String n, Symtable t) {
     name = n; // nome da classe declarada
     nested = new Symtable(this); // tabela onde inserir vars, metodos ou classes
     parent = null; // sua superclasse
 }
}