package semanAnalysis;

import symtable.*;

public class Type {
    public EntryTable ty; // entrada na tabela do tipo
    public int dim; // dimensão

    public Type(EntryTable t, int d) {
        ty = t;
        dim = d;
    }

    public String dscJava() {
        return EntryTable.strDim(dim) + ty.dscJava();
    }
}