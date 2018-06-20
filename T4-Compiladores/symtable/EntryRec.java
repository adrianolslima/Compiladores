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
    public EntryRec(EntryTable p, int d, int c, boolean op) {
        type = p;
        cont = c;
        dim = d;
        next = null;
        opcional = op;
    }

    // cria elemento e poe no inicio da lista
    public EntryRec(EntryTable p, int d, int c, EntryRec t, boolean op) {
        type = p;
        cont = c;
        dim = d;
        next = t;
        opcional = op;
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
}
