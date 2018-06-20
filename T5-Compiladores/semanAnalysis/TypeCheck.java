/*--- Programa 11.4 ---*/

package semanAnalysis;

import parser.*;
import symtable.*;
import syntactTree.*;

public class TypeCheck extends VarCheck {
	int nesting; // controla o nivel de aninhamento em comandos repetitivos
	protected int Nlocals; // conta número de variáveis locais num método
	Type Returntype; // tipo de retorno de um método
	protected final EntrySimple STRING_TYPE;
	protected final EntrySimple INT_TYPE;
	protected final EntrySimple CHAR_TYPE; // Atualização
	protected final EntrySimple FLOAT_TYPE; // Atualização
	protected final EntrySimple BOOLEAN_TYPE; // Atualização
	protected final EntrySimple NULL_TYPE;
	protected EntryMethod CurMethod; // método sendo analisado
	boolean cansuper; // indica se chamada super é permitida

	public TypeCheck() {
		super();
		nesting = 0;
		Nlocals = 0;
		STRING_TYPE = (EntrySimple) Maintable.classFindUp("string");
		INT_TYPE = (EntrySimple) Maintable.classFindUp("int");
		CHAR_TYPE = (EntrySimple) Maintable.classFindUp("char");
		FLOAT_TYPE = (EntrySimple) Maintable.classFindUp("float");
		BOOLEAN_TYPE = (EntrySimple) Maintable.classFindUp("boolean");
		NULL_TYPE = new EntrySimple("$NULL$");
		Maintable.add(NULL_TYPE);
	}

	public void TypeCheckRoot(ListNode x) throws SemanticException {
		VarCheckRoot(x); // faz análise das variáveis e métodos
		TypeCheckClassDeclListNode(x); // faz análise do corpo dos métodos

		if (foundSemanticError != 0) { // se houve erro, lança exceção
			throw new SemanticException(foundSemanticError + " Semantic Errors found (phase 3)");
		}
	}

	/*--- Programa 11.5 ---*/
	// ------------- lista de classes --------------------------
	public void TypeCheckClassDeclListNode(ListNode x) {
		
		if (x == null) return;

		try {
			TypeCheckClassDeclNode((ClassDeclNode) x.node);
		} catch (SemanticException e) { // se um erro ocorreu na classe, da a msg mas faz a análise p/ próxima
			System.out.println(e.getMessage());
			foundSemanticError++;
		}

		TypeCheckClassDeclListNode(x.next);
	}

	/*--- Programa 11.5 ---*/
	// verifica se existe referência circular de superclasses
	private boolean circularSuperclass(EntryClass orig, EntryClass e) {
		if (e == null) {
			return false;
		}

		if (orig == e) {
			return true;
		}

		return circularSuperclass(orig, e.parent);
	}

	/*--- Programa 11.5 ---*/
	// ------------- declaracão de classe -------------------------
	public void TypeCheckClassDeclNode(ClassDeclNode x) throws SemanticException {
		Symtable temphold = Curtable; // salva tabela corrente
		EntryClass nc;

		if (x == null) {
			return;
		}

		nc = (EntryClass) Curtable.classFindUp(x.name.image);

		if (circularSuperclass(nc, nc.parent)) { // se existe declaracão circular, ERRO
			nc.parent = null;
			throw new SemanticException(x.position, "Circular inheritance");
		}

		Curtable = nc.nested; // tabela corrente = tabela da classe
		TypeCheckClassBodyNode(x.body);
		Curtable = temphold; // recupera tabela corrente
	}

	/*--- Programa 11.6 ---*/
	// ------------- corpo da classe -------------------------
	public void TypeCheckClassBodyNode(ClassBodyNode x) {
		if (x == null) {
			return;
		}

		TypeCheckClassDeclListNode(x.clist);
		TypeCheckVarDeclListNode(x.vlist);
		TypeCheckConstructDeclListNode(x.ctlist);
		TypeCheckMethodDeclListNode(x.mlist);
	}

	// ---------------- Lista de declarac�es de vari�veis ----------------
	public void TypeCheckVarDeclListNode(ListNode x) {
		if (x == null) {
			return;
		}

		try {
			TypeCheckVarDeclNode((VarDeclNode) x.node);
		} catch (SemanticException e) {
			System.out.println(e.getMessage());
			foundSemanticError++;
		}

		TypeCheckVarDeclListNode(x.next);
	}

	/*--- Programa 11.7 ---*/
	// -------------------- Declaracão de variável --------------------
	public void TypeCheckVarDeclNode(VarDeclNode x) throws SemanticException {
		ListNode p;
		EntryVar l;

		if (x == null) {
			return;
		}

		for (p = x.vars; p != null; p = p.next) {
			VarNode q = (VarNode) p.node;

			// tenta pegar 2a. ocorrência da variável na tabela
			l = Curtable.varFind(q.position.image, 2);

			// se conseguiu a variável foi definida 2 vezes, ERRO
			if (l != null) {
				throw new SemanticException(q.position, "Variable " + q.position.image + " already declared");
			}
		}
	}

	// -------------- Lista de construtores ---------------------
	public void TypeCheckConstructDeclListNode(ListNode x) {
		if (x == null) {
			return;
		}

		try {
			TypeCheckConstructDeclNode((ConstructDeclNode) x.node);
		} catch (SemanticException e) {
			System.out.println(e.getMessage());
			foundSemanticError++;
		}

		TypeCheckConstructDeclListNode(x.next);
	}

	// -------------------------- Lista de m�todos -----------------
	public void TypeCheckMethodDeclListNode(ListNode x) {
		if (x == null) {
			return;
		}

		try {
			TypeCheckMethodDeclNode((MethodDeclNode) x.node);
		} catch (SemanticException e) {
			System.out.println(e.getMessage());
			foundSemanticError++;
		}

		TypeCheckMethodDeclListNode(x.next);
	}

	/*--- Programa 11.9 ---*/
	// ------------------ Declaracão de construtor -----------------
	public void TypeCheckConstructDeclNode(ConstructDeclNode x) throws SemanticException {
		EntryMethod t;
		EntryRec r = null;
		EntryTable e;
		EntryClass thisclass;
		EntryVar thisvar;
		ListNode p;
		VarDeclNode q;
		VarNode u;
		int n;

		if (x == null) {
			return;
		}

		p = x.body.param;
		n = 0;

		// monta a lista com os tipos dos parâmetros
		while (p != null) {
			q = (VarDeclNode) p.node; // q = nó com a declaracão do parâmetro
			u = (VarNode) q.vars.node; // u = nó com o nome e dimensão
			n++;

			// acha a entrada do tipo na tabela
			e = Curtable.classFindUp(q.position.image);

			// constrói a lista com os tipos dos parâmetros
			r = new EntryRec(e, u.dim, n, r);
			p = p.next;
		}

		if (r != null) {
			r = r.inverte(); // inverte a lista
		}

		// acha a entrada do construtor na tabela
		t = Curtable.methodFind("constructor", r);
		CurMethod = t; // guarda método corrente

		// inicia um novo escopo na tabela corrente
		Curtable.beginScope();

		// pega a entrada da classe corrente na tabela
		thisclass = (EntryClass) Curtable.levelup;

		thisvar = new EntryVar("this", thisclass, 0, 0);
		Curtable.add(thisvar); // inclui variável local "this" com número 0
		Returntype = null; // tipo de retorno do método = nenhum
		nesting = 0; // nível de aninhamento de comandos for
		Nlocals = 1; // inicializa numero de variáveis locais
		TypeCheckMethodBodyNode(x.body);
		t.totallocals = Nlocals; // número de variávamos locais do método
		Curtable.endScope(); // retira variáveis locais da tabela
	}

	public void TypeCheckMethodDeclNode(MethodDeclNode x) throws SemanticException {
		EntryMethod t;
		EntryRec r = null;
		EntryTable e;
		EntryClass thisclass;
		EntryVar thisvar;
		ListNode p;
		VarDeclNode q;
		VarNode u;
		int n;

		if (x == null) {
			return;
		}

		p = x.body.param;
		n = 0;

		// monta a lista com os tipos dos parâmetros
		while (p != null) {
			q = (VarDeclNode) p.node; // q = nó com a declaracão do parâmetro
			u = (VarNode) q.vars.node; // u = nó com o nome e dimensão
			n++;

			// acha a entrada do tipo na tabela
			e = Curtable.classFindUp(q.position.image);

			// constrói a lista com os tipos dos parâmetros
			r = new EntryRec(e, u.dim, n, r, false);
			p = p.next;
		}

		if (r != null) {
			r = r.inverte();
		}

		// acha a entrada do método na tabela
		t = Curtable.methodFind(x.name.image, r);
		CurMethod = t; // guarda método corrente

		// Returntype = tipo de retorno do método
		Returntype = new Type(t.type, t.dim);

		// inicia um novo escopo na tabela corrente
		Curtable.beginScope();

		// pega a entrada da classe corrente na tabela
		thisclass = (EntryClass) Curtable.levelup;

		thisvar = new EntryVar("this", thisclass, 0, 0);
		Curtable.add(thisvar); // inclui variável local "this" na tabela

		nesting = 0; // nível de aninhamento de comandos for
		Nlocals = 1; // inicializa número de variáveis locais
		TypeCheckMethodBodyNode(x.body);
		t.totallocals = Nlocals; // número de variáveis locais declaradas
		Curtable.endScope(); // retira variáveis locais da tabela corrente
	}

	// --------------------------- Comando composto ----------------------
	public void TypeCheckBlockNode(BlockNode x) {
		Curtable.beginScope(); // início de um escopo
		TypeCheckStatementListNode(x.stats);
		Curtable.endScope(); // final do escopo, libera vars locais
	}

	// --------------------- Lista de comandos --------------------
	public void TypeCheckStatementListNode(ListNode x) {
		if (x == null) {
			return;
		}

		try {
			TypeCheckStatementNode((StatementNode) x.node);
		} catch (SemanticException e) {
			System.out.println(e.getMessage());
			foundSemanticError++;
		}

		TypeCheckStatementListNode(x.next);
	}

	// ---------------------- Declaracão de variáveis locais ----------------------
	public void TypeCheckLocalVarDeclNode(VarDeclNode x) throws SemanticException {
		ListNode p;
		VarNode q;
		EntryVar l;
		EntryVar u;
		EntryTable c;

		if (x == null) {
			return;
		}

		// procura tipo da declaracão na tabela de símbolos
		c = Curtable.classFindUp(x.position.image);

		// se não achou, ERRO
		if (c == null) {
			throw new SemanticException(x.position, "Class " + x.position.image + " not found.");
		}

		for (p = x.vars; p != null; p = p.next) {
			q = (VarNode) p.node;
			l = Curtable.varFind(q.position.image);

			// se variável já existe é preciso saber que tipo de variável é
			if (l != null) {
				// primeiro verifica se é local, definida no escopo corrente
				if (l.scope == Curtable.scptr) { // se for, ERRO
					throw new SemanticException(q.position, "Variable " + p.position.image + " already declared");
				}

				// c.c. verifica se é uma variável de classe
				if (l.localcount < 0) { // se for dá uma advertência
					System.out.println("Line " + q.position.beginLine + " Column " + q.position.beginColumn
							+ " Warning: Variable " + q.position.image + " hides a class variable");
				} else { // senão, é uma variável local em outro escopo
					System.out.println("Line " + q.position.beginLine + " Column " + q.position.beginColumn
							+ " Warning: Variable " + q.position.image + " hides a parameter or a local variable");
				}
			}

			// insere a variável local na tabela corrente
			Curtable.add(new EntryVar(q.position.image, c, q.dim, Nlocals++));
		}
	}

	// --------------------------- Comando print ---------------------
	public void TypeCheckPrintNode(PrintNode x) throws SemanticException {
		Type t;

		if (x == null) {
			return;
		}

		// t = tipo e dimensão do resultado da expressão
		t = TypeCheckExpreNode(x.expr);

		// tipo tem que ser string e dimensão tem que ser 0
		if ((t.ty != STRING_TYPE) || (t.dim != 0)) {
			throw new SemanticException(x.position, "string expression required");
		}
	}

	// ---------------------- Comando read --------------------------
	public void TypeCheckReadNode(ReadNode x) throws SemanticException {
		Type t;

		if (x == null) {
			return;
		}

		// verifica se o nó filho tem um tipo válido
		if (!(x.expr instanceof DotNode || x.expr instanceof IndexNode || x.expr instanceof VarNode)) {
			throw new SemanticException(x.position, "Invalid expression in read statement");
		}

		// verifica se e uma atribuição para "this"
		if (x.expr instanceof VarNode) {
			EntryVar v = Curtable.varFind(x.expr.position.image);

			if ((v != null) && (v.localcount == 0)) // é a variável local 0?
			{
				throw new SemanticException(x.position, "Reading into variable " + " \"this\" is not legal");
			}
		}

		// verifica se o tipo é string ou int
		t = TypeCheckExpreNode(x.expr);

		if ((t.ty != STRING_TYPE) && (t.ty != INT_TYPE)) {
			throw new SemanticException(x.position, "Invalid type. Must be int or string");
		}

		// verifica se não é array
		if (t.dim != 0) {
			throw new SemanticException(x.position, "Cannot read array");
		}
	}

	// --------------------- Comando return -------------------------
	public void TypeCheckReturnNode(ReturnNode x) throws SemanticException {
		Type t;

		if (x == null) {
			return;
		}

		// t = tipo e dimensão do resultado da expressão
		t = TypeCheckExpreNode(x.expr);

		// verifica se é igual ao tipo do método corrente
		if (t == null) { // t == null não tem expressão no return

			if (Returntype == null) {
				return;
			} else { // se Returntype != null e um método, então ERRO
				throw new SemanticException(x.position, "Return expression required");
			}
		} else {
			if (Returntype == null) { // retorno num construtor, ERRO
				throw new SemanticException(x.position, "Constructor cannot return a value");
			}
		}

		// compara tipo e dimensão
		if ((t.ty != Returntype.ty) || (t.dim != Returntype.dim)) {
			throw new SemanticException(x.position, "Invalid return type");
		}
	}

	// -------------------------- Corpo de m�todo ----------------------
	public void TypeCheckMethodBodyNode(MethodBodyNode x) {
		if (x == null) {
			return;
		}

		TypeCheckLocalVarDeclListNode(x.param); // trata par�metro como var. local

		cansuper = false;

		if (Curtable.levelup.parent != null) { // existe uma superclasse para a classe corrente ?
												// acha primeiro comando do m�todo

			StatementNode p = x.stat;

			while (p instanceof BlockNode)
				p = (StatementNode) ((BlockNode) p).stats.node;

			cansuper = p instanceof SuperNode; // verifica se � chamada super
		}

		try {
			TypeCheckStatementNode(x.stat);
		} catch (SemanticException e) {
			System.out.println(e.getMessage());
			foundSemanticError++;
		}
	}

	// ------------------------ lista de vari�veis locais ----------------------
	public void TypeCheckLocalVarDeclListNode(ListNode x) {
		if (x == null) {
			return;
		}

		try {
			TypeCheckLocalVarDeclNode((VarDeclNode) x.node);
		} catch (SemanticException e) {
			System.out.println(e.getMessage());
			foundSemanticError++;
		}

		TypeCheckLocalVarDeclListNode(x.next);
	}

	// ------------------------- Comando de atribuição -------------------
	public void TypeCheckAtribNode(AtribNode x) throws SemanticException {
		Type t1;
		Type t2;
		EntryVar v;

		if (x == null) {
			return;
		}

		// verifica se o nó filho tem um tipo válido
		if (!(x.expr1 instanceof DotNode || x.expr1 instanceof IndexNode || x.expr1 instanceof VarNode)) {
			throw new SemanticException(x.position, "Invalid left side of assignment");
		}

		// verifica se é uma atribuição para "this"
		if (x.expr1 instanceof VarNode) {
			v = Curtable.varFind(x.expr1.position.image);

			if ((v != null) && (v.localcount == 0)) // é a variável local 0?
			{
				throw new SemanticException(x.position, "Assigning to variable " + " \"this\" is not legal");
			}
		}

		t1 = TypeCheckExpreNode(x.expr1);
		t2 = TypeCheckExpreNode(x.expr2);

		// verifica tipos das expressões
		// verifica dimensões
		if (t1.dim != t2.dim) {
			throw new SemanticException(x.position, "Invalid dimensions in assignment");
		}

		// verifica se lado esquerdo é uma classe e direito é null, OK
		if (t1.ty instanceof EntryClass && (t2.ty == NULL_TYPE)) {
			return;
		}

		// verifica se t2 e subclasse de t1
		if (!(isSubClass(t2.ty, t1.ty) || isSubClass(t1.ty, t2.ty))) {
			throw new SemanticException(x.position, "Incompatible types for assignment ");
		}
	}

	protected boolean isSubClass(EntryTable t1, EntryTable t2) {
		// verifica se são o mesmo tipo (vale para tipos simples)
		if (t1 == t2) {
			return true;
		}

		// verifica se são classes
		if (!(t1 instanceof EntryClass && t2 instanceof EntryClass)) {
			return false;
		}

		// procura t2 nas superclasses de t1
		for (EntryClass p = ((EntryClass) t1).parent; p != null; p = p.parent)
			if (p == t2) {
				return true;
			}

		return false;
	}

	public void TypeCheckSuperNode(SuperNode x) throws SemanticException {
		Type t;

		if (x == null) {
			return;
		}

		if (Returntype != null) {
			throw new SemanticException(x.position, "super is only allowed in constructors");
		}

		if (!cansuper) {
			throw new SemanticException(x.position, "super must be first statement in the constructor");
		}

		cansuper = false; // não permite outro super

		// p aponta para a entrada da superclasse da classe corrente
		EntryClass p = Curtable.levelup.parent;

		if (p == null) {
			throw new SemanticException(x.position, "No superclass for this class");
		}

		// t.ty possui um EntryRec com os tipos dos parâmetros
		t = TypeCheckExpreListNode(x.args);

		// procura o construtor na tabela da superclasse
		EntryMethod m = p.nested.methodFindInclass("constructor", (EntryRec) t.ty);

		// se não achou, ERRO
		if (m == null) {
			throw new SemanticException(x.position,
					"Constructor " + p.name + "(" + ((t.ty == null) ? "" : ((EntryRec) t.ty).toStr()) + ") not found");
		}

		CurMethod.hassuper = true; // indica que existe chamada a super no método
	}

	// ------------------------- comando for -----------------------
	public void TypeCheckForNode(ForNode x) {
		Type t;

		if (x == null) {
			return;
		}

		// analisa inicialização
		try {
			TypeCheckStatementNode(x.init);
		} catch (SemanticException e) {
			System.out.println(e.getMessage());
			foundSemanticError++;
		}

		// analisa expressão de controle
		try {
			t = TypeCheckExpreNode(x.expr);

			if ((t.ty != INT_TYPE) || (t.dim != 0)) {
				throw new SemanticException(x.expr.position, "Integer expression expected");
			}
		} catch (SemanticException e) {
			System.out.println(e.getMessage());
			foundSemanticError++;
		}

		// analisa expressão de incremento
		try {
			TypeCheckStatementNode(x.incr);
		} catch (SemanticException e) {
			System.out.println(e.getMessage());
			foundSemanticError++;
		}

		// analisa comando a ser repetido
		try {
			nesting++; // incrementa o aninhamento
			TypeCheckStatementNode(x.stat);
		} catch (SemanticException e) {
			System.out.println(e.getMessage());
			foundSemanticError++;
		}

		nesting--; // decrementa o aninhamento
	}

	// --------------------------- Comando break --------------------
	public void TypeCheckBreakNode(BreakNode x) throws SemanticException {
		if (x == null) {
			return;
		}

		// verifica se está dentro de um for. Se não, ERRO
		if (nesting <= 0) {
			throw new SemanticException(x.position, "break not in a for statement");
		}
	}

	// -------------------------- Constante inteira ----------------------
	public Type TypeCheckIntConstNode(IntConstNode x) throws SemanticException {
		int k;

		if (x == null) {
			return null;
		}

		// tenta transformar imagem em n�mero inteiro
		try {
			k = Integer.parseInt(x.position.image);
		} catch (NumberFormatException e) { // se deu erro, formato e inv�lido (possivelmente fora dos limites)
			throw new SemanticException(x.position, "Invalid int constant");
		}

		return new Type(INT_TYPE, 0);
	}

	// ------------------------ Constante string ----------------------------
	public Type TypeCheckStringConstNode(StringConstNode x) {
		if (x == null) {
			return null;
		}

		return new Type(STRING_TYPE, 0);
	}

	/*--- Atualização ---*/
	// ------------------------ Constante char ----------------------------
	public Type TypeCheckCharConstNode(StringConstNode x) {
		if (x == null) {
			return null;
		}

		return new Type(CHAR_TYPE, 0);
	}

	/*--- Atualização ---*/
	// ------------------------ Constante float ----------------------------
	public Type TypeCheckStringFloatNode(StringConstNode x) {
		if (x == null) {
			return null;
		}

		return new Type(FLOAT_TYPE, 0);
	}

	/*--- Atualização ---*/
	// ------------------------ Constante Boolean ----------------------------
	public Type TypeCheckBooleanConstNode(StringConstNode x) {
		if (x == null) {
			return null;
		}

		return new Type(BOOLEAN_TYPE, 0);
	}

	// ------------------------------ Constante null --------------------------
	public Type TypeCheckNullConstNode(NullConstNode x) {
		if (x == null) {
			return null;
		}

		return new Type(NULL_TYPE, 0);
	}

	// -------------------------------- Nome de vari�vel ------------------
	public Type TypeCheckVarNode(VarNode x) throws SemanticException {
		EntryVar p;

		if (x == null) {
			return null;
		}

		// procura vari�vel na tabela
		p = Curtable.varFind(x.position.image);

		// se n�o achou, ERRO
		if (p == null) {
			throw new SemanticException(x.position, "Variable " + x.position.image + " not found");
		}

		return new Type(p.type, p.dim);
	}

	// ---------------------------- Chamada de m�todo ------------------------
	public Type TypeCheckCallNode(CallNode x) throws SemanticException {
		EntryClass c;
		EntryMethod m;
		Type t1;
		Type t2;

		if (x == null) {
			return null;
		}

		// calcula tipo do primeiro filho
		t1 = TypeCheckExpreNode(x.expr);

		// se for array, ERRO
		if (t1.dim > 0) {
			throw new SemanticException(x.position, "Arrays do not have methods");
		}

		// se n�o for uma classe, ERRO
		if (!(t1.ty instanceof EntryClass)) {
			throw new SemanticException(x.position, "Type " + t1.ty.name + " does not have methods");
		}

		// pega tipos dos argumentos
		t2 = TypeCheckExpreListNode(x.args);

		// procura o m�todo desejado na classe t1.ty
		c = (EntryClass) t1.ty;
		m = c.nested.methodFind(x.meth.image, (EntryRec) t2.ty);

		// se n�o achou, ERRO
		if (m == null) {
			throw new SemanticException(x.position, "Method " + x.meth.image + "("
					+ ((t2.ty == null) ? "" : ((EntryRec) t2.ty).toStr()) + ") not found in class " + c.name);
		}

		return new Type(m.type, m.dim);
	}

	// --------------------------- Lista de express�es ---------------
	public Type TypeCheckExpreListNode(ListNode x) {
		Type t;
		Type t1;
		EntryRec r;
		int n;

		if (x == null) {
			return new Type(null, 0);
		}

		try {
			// pega tipo do primeiro n� da lista
			t = TypeCheckExpreNode((ExpreNode) x.node);
		} catch (SemanticException e) {
			System.out.println(e.getMessage());
			foundSemanticError++;
			t = new Type(NULL_TYPE, 0);
		}

		// pega tipo do restante da lista. t1.ty cont�m um EntryRec
		t1 = TypeCheckExpreListNode(x.next);

		// n = tamanho da lista em t1
		n = (t1.ty == null) ? 0 : ((EntryRec) t1.ty).cont;

		// cria novo EntryRec com t.ty como 1.o elemento
		r = new EntryRec(t.ty, t.dim, n + 1, (EntryRec) t1.ty);

		// cria type com r como vari�vel ty
		t = new Type(r, 0);

		return t;
	}

	// --------------------------- Indexa��o de vari�vel ---------------
	public Type TypeCheckIndexNode(IndexNode x) throws SemanticException {
		EntryClass c;
		Type t1;
		Type t2;

		if (x == null) {
			return null;
		}

		// calcula tipo do primeiro filho
		t1 = TypeCheckExpreNode(x.expr1);

		// se n�o for array, ERRO
		if (t1.dim <= 0) {
			throw new SemanticException(x.position, "Can not index non array variables");
		}

		// pega tipo do �ndice
		t2 = TypeCheckExpreNode(x.expr2);

		// se n�o for int, ERRO
		if ((t2.ty != INT_TYPE) || (t2.dim > 0)) {
			throw new SemanticException(x.position, "Invalid type. Index must be int");
		}

		return new Type(t1.ty, t1.dim - 1);
	}

	// ------------------ Express�es ----------------------------------------
	// -------------------------- Aloca��o de objeto ------------------------
	public Type TypeCheckNewObjectNode(NewObjectNode x) throws SemanticException {
		Type t;
		EntryMethod p;
		EntryTable c;

		if (x == null) {
			return null;
		}

		// procura a classe da qual se deseja criar um objeto
		c = Curtable.classFindUp(x.name.image);

		// se n�o achou, ERRO
		if (c == null) {
			throw new SemanticException(x.position, "Class " + x.name.image + " not found");
		}

		// t.ty recebe a lista de tipos dos argumentos
		t = TypeCheckExpreListNode(x.args);

		// procura um construtor com essa assinatura
		Symtable s = ((EntryClass) c).nested;
		p = s.methodFindInclass("constructor", (EntryRec) t.ty);

		// se n�o achou, ERRO
		if (p == null) {
			throw new SemanticException(x.position, "Constructor " + x.name.image + "("
					+ ((t.ty == null) ? "" : ((EntryRec) t.ty).toStr()) + ") not found");
		}

		// retorna c como tipo, dimens�o = 0, local = -1 (n�o local)
		t = new Type(c, 0);

		return t;
	}

	// -------------------------- Aloca��o de array ------------------------
	public Type TypeCheckNewArrayNode(NewArrayNode x) throws SemanticException {
		Type t;
		EntryTable c;
		ListNode p;
		ExpreNode q;
		int k;

		if (x == null) {
			return null;
		}

		// procura o tipo da qual se deseja criar um array
		c = Curtable.classFindUp(x.tipo.image); // Originalmente era name, mudei para tipo

		// se n�o achou, ERRO
		if (c == null) {
			throw new SemanticException(x.position, "Type " + x.tipo.image + " not found"); // Originalmente era name,
																							// mudei para tipo
		}

		// para cada express�o das dimens�es, verifica se tipo e int
		for (k = 0, p = x.dims; p != null; p = p.next) {
			t = TypeCheckExpreNode((ExpreNode) p.node);

			if ((t.ty != INT_TYPE) || (t.dim != 0)) {
				throw new SemanticException(p.position, "Invalid expression for an array dimension");
			}

			k++;
		}

		return new Type(c, k);
	}

	/*--- Programa 11.30 ---*/
	// ------------------------- Express�o unaria ------------------------
	public Type TypeCheckUnaryNode(UnaryNode x) throws SemanticException {
		Type t;

		if (x == null) {
			return null;
		}

		t = TypeCheckExpreNode(x.expr);

		// se dimensão > 0, ERRO
		if (t.dim > 0) {
			throw new SemanticException(x.position, "Can not use unary " + x.position.image + " for arrays");
		}

		// só int e float são aceitos
		if (t.ty != INT_TYPE && t.ty != FLOAT_TYPE) {
			throw new SemanticException(x.position, "Incompatible type for unary " + x.position.image);
		}

		if (t.ty == INT_TYPE)
			return new Type(INT_TYPE, 0);
		else
			return new Type(FLOAT_TYPE, 0);
	}

	/*--- Programa 11.31 ---*/
	// ------------------------ Soma ou subtração -------------------
	public Type TypeCheckAddNode(AddNode x) throws SemanticException {
		Type t1;
		Type t2;
		int op; // operação
		int i, j, k, l;

		if (x == null) {
			return null;
		}

		op = x.position.kind;
		t1 = TypeCheckExpreNode(x.expr1);
		t2 = TypeCheckExpreNode(x.expr2);

		// se dimensão > 0, ERRO
		if ((t1.dim > 0) || (t2.dim > 0)) {
			throw new SemanticException(x.position, "Can not use " + x.position.image + " for arrays");
		}

		i = j = k = l = 0;

		if (t1.ty == INT_TYPE) {
			i++;
		} else if (t1.ty == STRING_TYPE) {
			j++;
		} else if (t1.ty == CHAR_TYPE) {
			k++;
		} else if (t1.ty == FLOAT_TYPE) {
			l++;
		}

		if (t2.ty == INT_TYPE) {
			i++;
		} else if (t2.ty == STRING_TYPE) {
			j++;
		} else if (t2.ty == CHAR_TYPE) {
			k++;
		} else if (t2.ty == FLOAT_TYPE) {
			l++;
		}

		// 2 operadores inteiros, OK
		if (i == 2) {
			return new Type(INT_TYPE, 0);
		}
		
		// 2 operadores float, OK
		if (l == 2) {
			return new Type(FLOAT_TYPE, 0);
		}
		
		// 1 int e 1 float, OK
		if ((i + l) == 2) {
			return new Type(FLOAT_TYPE, 0);
		}

		if ((op == langXConstants.PLUS) && ((i + j) == 2)) { // um inteiro e um string só pode somar
			return new Type(STRING_TYPE, 0);
		} else if ((op == langXConstants.PLUS) && ((i + k) == 2)) { // um inteiro e um char só pode somar
			return new Type(STRING_TYPE, 0);
		} else if ((op == langXConstants.PLUS) && ((j + l) == 2)) { // um string e um float só pode somar
			return new Type(STRING_TYPE, 0);
		} else if ((op == langXConstants.PLUS) && ((k + l) == 2)) { // um char e um float só pode somar
			return new Type(STRING_TYPE, 0);
		} else if ((op == langXConstants.PLUS) && ((j + k) == 2)) { // um string e um char só pode somar
			return new Type(STRING_TYPE, 0);
		}

		throw new SemanticException(x.position, "Invalid types for " + x.position.image);
	}

	/*--- Atualização ---*/
	// ------------------------ Multiplicação ou Divisão -------------------
	public Type TypeCheckMultNode(AddNode x) throws SemanticException {
		Type t1;
		Type t2;
		int op; // operação
		int i, j, k, l;

		if (x == null) {
			return null;
		}

		op = x.position.kind;
		t1 = TypeCheckExpreNode(x.expr1);
		t2 = TypeCheckExpreNode(x.expr2);

		// se dimensão > 0, ERRO
		if ((t1.dim > 0) || (t2.dim > 0)) {
			throw new SemanticException(x.position, "Can not use " + x.position.image + " for arrays");
		}

		i = j = k = l = 0;

		if (t1.ty == INT_TYPE) {
			i++;
		} else if (t1.ty == STRING_TYPE) {
			j++;
		} else if (t1.ty == CHAR_TYPE) {
			k++;
		} else if (t1.ty == FLOAT_TYPE) {
			l++;
		}

		if (t2.ty == INT_TYPE) {
			i++;
		} else if (t2.ty == STRING_TYPE) {
			j++;
		} else if (t2.ty == CHAR_TYPE) {
			k++;
		} else if (t2.ty == FLOAT_TYPE) {
			l++;
		}

		// 2 operadores inteiros, OK
		if (i == 2) {
			return new Type(INT_TYPE, 0);
		}
		
		// 2 operadores float, OK
		if (l == 2) {
			return new Type(FLOAT_TYPE, 0);
		}
		
		// 1 int e 1 float, OK
		if ((i + l) == 2) {
			return new Type(FLOAT_TYPE, 0);
		}

//		if ((op == langXConstants.PLUS) && ((i + j) == 2)) { // um inteiro e um string só pode somar
//			return new Type(STRING_TYPE, 0);
//		} else if ((op == langXConstants.PLUS) && ((i + k) == 2)) { // um inteiro e um char só pode somar
//			return new Type(STRING_TYPE, 0);
//		} else if ((op == langXConstants.PLUS) && ((j + l) == 2)) { // um string e um float só pode somar
//			return new Type(STRING_TYPE, 0);
//		} else if ((op == langXConstants.PLUS) && ((k + l) == 2)) { // um char e um float só pode somar
//			return new Type(STRING_TYPE, 0);
//		} else if ((op == langXConstants.PLUS) && ((j + k) == 2)) { // um string e um char só pode somar
//			return new Type(STRING_TYPE, 0);
//		}

		throw new SemanticException(x.position, "Invalid types for " + x.position.image);
	}

	/*--- Progama 11.32 ---*/
	// --------------------- Express�o relacional -------------------
	public Type TypeCheckRelationalNode(RelationalNode x) throws SemanticException {
		Type t1;
		Type t2;
		int op; // operação

		if (x == null) {
			return null;
		}

		op = x.position.kind;
		t1 = TypeCheckExpreNode(x.expr1);
		t2 = TypeCheckExpreNode(x.expr2);

		/*--- Atualização ---*/
		if (t1.ty == INT_TYPE) {
			if (t2.ty == INT_TYPE) {
				return new Type(INT_TYPE, 0); // se ambos são int, retorna OK
			} else if (t2.ty == FLOAT_TYPE) {
				return new Type(FLOAT_TYPE, 0);  // se int e float, retorna OK
			}
		} else if (t2.ty == FLOAT_TYPE) {
			if (t1.ty == FLOAT_TYPE) {
				return new Type(FLOAT_TYPE, 0); // se ambos são float, retorna OK
			} else if (t1.ty == INT_TYPE) {
				return new Type(FLOAT_TYPE, 0); // se float e int, retorna OK
			}
		}	
		
		if (t1.ty == BOOLEAN_TYPE && t2.ty == BOOLEAN_TYPE)
			return new Type(BOOLEAN_TYPE, 0);

		// se a dimensão é diferente, ERRO
		if (t1.dim != t2.dim) {
			throw new SemanticException(x.position, "Can not compare objects with different dimensions");
		}

		// se dimensão > 0 só pode comparar igualdade
		if ((op != langXConstants.EQ) && (op != langXConstants.NEQ) && (t1.dim > 0)) {
			throw new SemanticException(x.position, "Can not use " + x.position.image + " for arrays");
		}

		// se dois são objetos do mesmo tipo pode comparar igualdade
		// isso inclui 2 strings
		if ((isSubClass(t2.ty, t1.ty) || isSubClass(t1.ty, t2.ty))
				&& ((op == langXConstants.NEQ) || (op == langXConstants.EQ))) {
			return new Type(INT_TYPE, 0);
		}

		// se um é objeto e outro null, pode comparar igualdade
		if (((t1.ty instanceof EntryClass && (t2.ty == NULL_TYPE))
				|| (t2.ty instanceof EntryClass && (t1.ty == NULL_TYPE)))
				&& ((op == langXConstants.NEQ) || (op == langXConstants.EQ))) {
			return new Type(INT_TYPE, 0);
		}

		throw new SemanticException(x.position, "Invalid types for " + x.position.image);
	}

	public void TypeCheckNopNode(NopNode x) {
		// COMPLETAR PARA T5
	}

	public Type TypeCheckDotNode(DotNode x) throws SemanticException {
		EntryClass c;
		EntryVar v;
		Type t;

		if (x == null) {
			return null;
		}

		// calcula tipo do primeiro filho
		t = TypeCheckExpreNode(x.expr);

		// se for array, ERRO
		if (t.dim > 0) {
			throw new SemanticException(x.position, "Arrays do not have fields");
		}

		// se n�o for uma classe, ERRO
		if (!(t.ty instanceof EntryClass)) {
			throw new SemanticException(x.position, "Type " + t.ty.name + " does not have fields");
		}

		// procura a vari�vel desejada na classe t.ty
		c = (EntryClass) t.ty;
		v = c.nested.varFind(x.field.image);

		// se n�o achou, ERRO
		if (v == null) {
			throw new SemanticException(x.position, "Variable " + x.field.image + " not found in class " + c.name);
		}

		return new Type(v.type, v.dim);
	}

	// ---------------------------------- comando if --------------------
	public void TypeCheckIfNode(IfNode x) {
		Type t;

		if (x == null) {
			return;
		}

		try {
			t = TypeCheckExpreNode(x.expr);

			if ((t.ty != INT_TYPE) || (t.dim != 0)) {
				throw new SemanticException(x.expr.position, "Integer expression expected");
			}
		} catch (SemanticException e) {
			System.out.println(e.getMessage());
			foundSemanticError++;
		}

		try {
			TypeCheckStatementNode(x.stat1);
		} catch (SemanticException e) {
			System.out.println(e.getMessage());
			foundSemanticError++;
		}

		try {
			TypeCheckStatementNode(x.stat2);
		} catch (SemanticException e) {
			System.out.println(e.getMessage());
			foundSemanticError++;
		}
	}

	// --------------------------- Comando em geral -------------------
	public void TypeCheckStatementNode(StatementNode x) throws SemanticException {
		if (x instanceof BlockNode) {
			TypeCheckBlockNode((BlockNode) x);
		} else if (x instanceof VarDeclNode) {
			TypeCheckLocalVarDeclNode((VarDeclNode) x);
		} else if (x instanceof AtribNode) {
			TypeCheckAtribNode((AtribNode) x);
		} else if (x instanceof IfNode) {
			TypeCheckIfNode((IfNode) x);
		} else if (x instanceof ForNode) {
			TypeCheckForNode((ForNode) x);
		} else if (x instanceof PrintNode) {
			TypeCheckPrintNode((PrintNode) x);
		} else if (x instanceof NopNode) {
			TypeCheckNopNode((NopNode) x);
		} else if (x instanceof ReadNode) {
			TypeCheckReadNode((ReadNode) x);
		} else if (x instanceof ReturnNode) {
			TypeCheckReturnNode((ReturnNode) x);
		} else if (x instanceof SuperNode) {
			TypeCheckSuperNode((SuperNode) x);
		} else if (x instanceof BreakNode) {
			TypeCheckBreakNode((BreakNode) x);
		}
	}

	public Type TypeCheckMultNode(MultNode x) throws SemanticException {
		Type t1;
		Type t2;
		int op; // opera��o
		int i;
		int j;

		if (x == null) {
			return null;
		}

		op = x.position.kind;
		t1 = TypeCheckExpreNode(x.expr1);
		t2 = TypeCheckExpreNode(x.expr2);

		// se dimens�o > 0, ERRO
		if ((t1.dim > 0) || (t2.dim > 0)) {
			throw new SemanticException(x.position, "Can not use " + x.position.image + " for arrays");
		}

		// s� dois int's s�o aceitos
		if ((t1.ty != INT_TYPE) || (t2.ty != INT_TYPE)) {
			throw new SemanticException(x.position, "Invalid types for " + x.position.image);
		}

		return new Type(INT_TYPE, 0);
	}

	// --------------------------- Expressão em geral --------------------------
	public Type TypeCheckExpreNode(ExpreNode x) throws SemanticException {
		if (x instanceof NewObjectNode) {
			return TypeCheckNewObjectNode((NewObjectNode) x);
		} else if (x instanceof NewArrayNode) {
			return TypeCheckNewArrayNode((NewArrayNode) x);
		} else if (x instanceof RelationalNode) {
			return TypeCheckRelationalNode((RelationalNode) x);
		} else if (x instanceof AddNode) {
			return TypeCheckAddNode((AddNode) x);
		} else if (x instanceof MultNode) {
			return TypeCheckMultNode((MultNode) x);
		} else if (x instanceof UnaryNode) {
			return TypeCheckUnaryNode((UnaryNode) x);
		} else if (x instanceof CallNode) {
			return TypeCheckCallNode((CallNode) x);
		} else if (x instanceof IntConstNode) {
			return TypeCheckIntConstNode((IntConstNode) x);
		} else if (x instanceof StringConstNode) {
			return TypeCheckStringConstNode((StringConstNode) x);
		} else if (x instanceof NullConstNode) {
			return TypeCheckNullConstNode((NullConstNode) x);
		} else if (x instanceof IndexNode) {
			return TypeCheckIndexNode((IndexNode) x);
		} else if (x instanceof DotNode) {
			return TypeCheckDotNode((DotNode) x);
		} else if (x instanceof VarNode) {
			return TypeCheckVarNode((VarNode) x);
		} else {
			return null;
		}
	}
}