package recovery;

import parser.*;

import java.util.*;

import java.util.*;

public class First { // implementa os conjuntos first p/ alguns n.terminais

	static public final RecoverySet methoddecl = new RecoverySet();
	static public final RecoverySet vardecl = new RecoverySet();
	static public final RecoverySet classlist = new RecoverySet();
	static public final RecoverySet constructdecl = new RecoverySet();
	static public final RecoverySet statlist = new RecoverySet();
	static public final RecoverySet classbodystatement = new RecoverySet();
	static public final RecoverySet classbodyprestatement = new RecoverySet();
	static public final RecoverySet program = classlist;
	// static public final RecoverySet switchcasestat = new RecoverySet();

	static {
		methoddecl.add(new Integer(langXConstants.INT));
		methoddecl.add(new Integer(langXConstants.STRING));
		methoddecl.add(new Integer(langXConstants.IDENT));
		methoddecl.add(new Integer(langXConstants.BOOLEAN));
		methoddecl.add(new Integer(langXConstants.CHAR));
		methoddecl.add(new Integer(langXConstants.FLOAT));

		vardecl.add(new Integer(langXConstants.INT));
		vardecl.add(new Integer(langXConstants.STRING));
		vardecl.add(new Integer(langXConstants.IDENT));
		vardecl.add(new Integer(langXConstants.BOOLEAN));
		vardecl.add(new Integer(langXConstants.CHAR));
		vardecl.add(new Integer(langXConstants.FLOAT));

		classlist.add(new Integer(langXConstants.CLASS));

		constructdecl.add(new Integer(langXConstants.CONSTRUCTOR));

		statlist.addAll(vardecl);
		statlist.add(new Integer(langXConstants.IDENT)); // first do atribstat
		statlist.add(new Integer(langXConstants.PRINT));
		statlist.add(new Integer(langXConstants.READ));
		statlist.add(new Integer(langXConstants.RETURN));
		statlist.add(new Integer(langXConstants.SUPER));
		statlist.add(new Integer(langXConstants.IF));
		statlist.add(new Integer(langXConstants.FOR));
		statlist.add(new Integer(langXConstants.LBRACE));
		statlist.add(new Integer(langXConstants.BREAK));
		statlist.add(new Integer(langXConstants.SEMICOLON));
		
		classbodystatement.addAll(classbodyprestatement);  
	    classbodystatement.addAll(methoddecl); 
	    classbodystatement.addAll(constructdecl);

	}
}
