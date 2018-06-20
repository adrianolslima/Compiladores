// Programa 7.2

package syntactTree;

import jdk.nashorn.internal.codegen.CompilerConstants.Call;
import parser.*;

public class PrintTree {

	int kk;

	public PrintTree() {
		
		kk = 1; // inicializa contador de nós
	}
	
	public void printRoot(ListNode x) {
		
		if (x == null)
			System.out.println("Empty syntatic tree. Nothing to be printed.");
		else {
			numberClassDeclListNode(x);
			printClassDeclListNode(x);
		}
		System.out.println();
	}
	
/*--- AddNode ---*/
	/*--- Programa 7.11 ---*/	
	public void numberAddNode(AddNode x) {
		
		if (x == null) return;
		x.number = kk++;
		numberExpreNode(x.expr1);
		numberExpreNode(x.expr2);
	}
	
    public void printAddNode(AddNode x) {
        if (x == null) {
            return;
        }

        System.out.println();
        System.out.print(x.number + ": AddNode ===> " + x.expr1.number + " " +
            x.position.image + " " + x.expr2.number);
        printExpreNode(x.expr1);
        printExpreNode(x.expr2);
    }

/*--- AtribNode ---*/
    public void numberAtribNode(AtribNode x) {
        if (x == null) {
            return;
        }

        x.number = kk++;
        numberExpreNode(x.expr1);
        numberExpreNode(x.expr2);
    }

    public void printAtribNode(AtribNode x) {
        if (x == null) {
            return;
        }

        System.out.println();
        System.out.print(x.number + ": AtribNode ===> " + x.expr1.number + " "
            + x.position.image + " " + x.expr2.number);
        printExpreNode(x.expr1);
        printExpreNode(x.expr2);
    }

/*--- BlockNode ---*/
    public void numberBlockNode(BlockNode x) {
        if (x == null) {
            return;
        }

        x.number = kk++;
        numberStatementListNode(x.stats);
    }

    public void printBlockNode(BlockNode x) {
        if (x == null) {
            return;
        }

        System.out.println();
        System.out.print(x.number + ": BlockNode ===> " + x.stats.number);
        printStatementListNode(x.stats);
    }

/*--- BooleanConstNode ---*/
    public void numberBooleanConstNode(BooleanConstNode x) {
        if (x == null) {
            return;
        }

        x.number = kk++;
    }

    public void printBooleanConstNode(BooleanConstNode x) {
        if (x == null) {
            return;
        }

        System.out.println();
        System.out.print(x.number + ": BooleanConstNode ===> " + x.position.image);
    }

/*--- BreakNode ---*/
    public void numberBreakNode(BreakNode x) {
        if (x == null) {
            return;
        }

        x.number = kk++;
    }

    public void printBreakNode(BreakNode x) {
        if (x == null) {
            return;
        }

        System.out.println();
        System.out.print(x.number + ": BreakNode");
    }

/*--- CallNode ---*/
    public void numberCallNode(CallNode x) {
        if (x == null) {
            return;
        }

        x.number = kk++;
        numberExpreNode(x.expr);
        numberExpreListNode(x.args);
    }

    public void printCallNode(CallNode x) {
        if (x == null) {
            return;
        }

        System.out.println();
        System.out.print(x.number + ": CallNode ===> " + x.expr.number + " " +
            x.meth.image + " " +
            ((x.args == null) ? "null" : String.valueOf(x.args.number)));
        printExpreNode(x.expr);
        printExpreListNode(x.args);
    }

/*--- CharConstNode ---*/
    public void numberCharConstNode(CharConstNode x) {
        if (x == null) {
            return;
        }

        x.number = kk++;
    }

    public void printCharConstNode(CharConstNode x) {
        if (x == null) {
            return;
        }

        System.out.println();
        System.out.print(x.number + ": CharConstNode ===> " + x.position.image);
    }

/*--- ClassBodyNode ---*/
	/*--- Programa 7.4 ---*/
	public void numberClassBodyNode(ClassBodyNode x) {
		
		if (x == null) return;
		x.number = kk++;
		numberClassDeclListNode(x.clist);
		numberVarDeclListNode(x.vlist);
		numberAtribListNode(x.aslist);
		numberConstructDeclListNode(x.ctlist);
		numberMethodDeclListNode(x.mlist);
	}
	
	/*--- Programa 7.12 ---*/	
	public void printClassBodyNode(ClassBodyNode x) {
		
		if (x == null) return;
		System.out.println();
		System.out.print(x.number + ": ClassBodyNode ===> " +
				(x.clist == null ? "null" : String.valueOf(x.clist.number))
				+ " " +
				(x.vlist == null ? "null" : String.valueOf(x.vlist.number))
				+ " " +
				(x.aslist == null ? "null" : String.valueOf(x.aslist.number))
				+ " " +
				(x.ctlist == null ? "null" : String.valueOf(x.ctlist.number))
				+ " " +
				(x.mlist == null ? "null" : String.valueOf(x.mlist.number)) );
		
		printClassDeclListNode(x.clist);
		printVarDeclListNode(x.vlist);
		printAtribListNode(x.aslist);
		printConstructDeclListNode(x.ctlist);
		printMethodDeclListNode(x.mlist);
	}
	
/*--- ClassDeclNode ---*/	
	public void numberClassDeclNode(ClassDeclNode x) {
		if (x == null) {
			return;
		}

		x.number = kk++;
		numberClassBodyNode(x.body);
	}

	/*--- Programa 7.13 ---*/	
	public void printClassDeclNode(ClassDeclNode x) {
		
		if (x == null) return;
		System.out.println();
		System.out.print(x.number + ": ClassDeclNode ===> " +
				x.name.image + " " +
				(x.supername == null ? "null" : x.supername.image)
				+ " " +
				(x.body == null ? "null" : String.valueOf(x.body.number)) );
		
		printClassBodyNode(x.body);
	}
	
/*--- constructDeclList ---*/
	public void numberConstructDeclNode(ConstructDeclNode x) {
		if (x == null) {
			return;
		}

		x.number = kk++;
		numberMethodBodyNode(x.body);
	}

	public void printConstructDeclNode(ConstructDeclNode x) {
		if (x == null) {
			return;
		}

		System.out.println();
		System.out.print(x.number + ": ConstructDeclNode ===> " + 
				x.position.image + " " + x.body.number);

		printMethodBodyNode(x.body);
	}

/*--- DotNode ---*/
	public void numberDotNode(DotNode x) {
		
        if (x == null) return;

        x.number = kk++;
        numberExpreNode(x.expr);
    }

    public void printDotNode(DotNode x) {

    	if (x == null) return;

        System.out.println();
        System.out.print(x.number + ": DotNode ===> " + x.expr.number + " " +
            x.field.image);
        printExpreNode(x.expr);
    }

/*--- ExpreNode ---*/
    /*--- Programa 7.10 ---*/	
	public void numberExpreNode(ExpreNode x) {
		
		if (x instanceof NewObjectNode)
			numberNewObjectNode((NewObjectNode) x);
		else if (x instanceof NewArrayNode)
			numberNewArrayNode((NewArrayNode) x);
		else if (x instanceof RelationalAndNode)
			numberRelationalAndNode((RelationalAndNode) x);
		else if (x instanceof RelationalLogicNode)
			numberRelationalLogicNode((RelationalLogicNode) x);
		else if (x instanceof RelationalNode)
			numberRelationalNode((RelationalNode) x);
		else if (x instanceof RelationalNotNode)
			numberRelationalNotNode((RelationalNotNode) x);
		else if (x instanceof AddNode)
			numberAddNode((AddNode) x);
		else if (x instanceof MultNode)
			numberMultNode((MultNode) x);
		else if (x instanceof UnaryNode)
			numberUnaryNode((UnaryNode) x);
		else if (x instanceof CallNode)
			numberCallNode((CallNode) x);
		else if (x instanceof IntConstNode)
			numberIntConstNode((IntConstNode) x);
		else if (x instanceof StringConstNode)
			numberStringConstNode((StringConstNode) x);
		else if (x instanceof FloatConstNode)
			numberFloatConstNode((FloatConstNode) x);
		else if (x instanceof BooleanConstNode)
			numberBooleanConstNode((BooleanConstNode) x);
		else if (x instanceof CharConstNode)
			numberCharConstNode((CharConstNode) x);
		else if (x instanceof NullConstNode)
			numberNullConstNode((NullConstNode) x);
		else if (x instanceof IndexNode)
			numberIndexNode((IndexNode) x);
		else if (x instanceof DotNode)
			numberDotNode((DotNode) x);
		else if (x instanceof VarNode)
			numberVarNode((VarNode) x);
	}
	
	public void printExpreNode(ExpreNode x) {
        if (x instanceof NewObjectNode) {
            printNewObjectNode((NewObjectNode) x);
        } else if (x instanceof NewArrayNode) {
            printNewArrayNode((NewArrayNode) x);
        } else if (x instanceof RelationalAndNode) {
            printRelationalAndNode((RelationalAndNode) x);
        } else if (x instanceof RelationalLogicNode) {
            printRelationalLogicNode((RelationalLogicNode) x);
        } else if (x instanceof RelationalNode) {
            printRelationalNode((RelationalNode) x);
        } else if (x instanceof RelationalNotNode) {
            printRelationalNotNode((RelationalNotNode) x);
        } else if (x instanceof AddNode) {
            printAddNode((AddNode) x);
        } else if (x instanceof MultNode) {
            printMultNode((MultNode) x);
        } else if (x instanceof UnaryNode) {
            printUnaryNode((UnaryNode) x);
        } else if (x instanceof CallNode) {
            printCallNode((CallNode) x);
        } else if (x instanceof IntConstNode) {
            printIntConstNode((IntConstNode) x);
        } else if (x instanceof StringConstNode) {
            printStringConstNode((StringConstNode) x);
        } else if (x instanceof FloatConstNode) {
            printFloatConstNode((FloatConstNode) x);
        } else if (x instanceof BooleanConstNode) {
            printBooleanConstNode((BooleanConstNode) x);
        } else if (x instanceof CharConstNode) {
            printCharConstNode((CharConstNode) x);
        } else if (x instanceof NullConstNode) {
            printNullConstNode((NullConstNode) x);
        } else if (x instanceof IndexNode) {
            printIndexNode((IndexNode) x);
        } else if (x instanceof DotNode) {
            printDotNode((DotNode) x);
        } else if (x instanceof VarNode) {
            printVarNode((VarNode) x);
        }
    }
    
/*--- FloatConstNode ---*/
	public void numberFloatConstNode(FloatConstNode x) {
        
		if (x == null) return;
        x.number = kk++;
    }

    public void printFloatConstNode(FloatConstNode x) {
        
    	if (x == null) return;

        System.out.println();
        System.out.print(x.number + ": FloatConstNode ===> " + x.position.image);
    }

/*--- ForNode ---*/
    public void numberForNode(ForNode x) {
        
    	if (x == null) return;

        x.number = kk++;
        numberAtribNode(x.init);
        numberExpreNode(x.expr);
        numberAtribNode(x.incr);
        numberStatementNode(x.stat);
    }

    public void printForNode(ForNode x) {
        
    	if (x == null) return;

        System.out.println();
        System.out.print(x.number + ": ForNode ===> " +
            ((x.init == null) ? "null" : String.valueOf(x.init.number)) + " " +
            ((x.expr == null) ? "null" : String.valueOf(x.expr.number)) + " " +
            ((x.incr == null) ? "null" : String.valueOf(x.incr.number)) + " " +
            x.stat.number);

        printAtribNode(x.init);
        printExpreNode(x.expr);
        printAtribNode(x.incr);
        printStatementNode(x.stat);
    }

/*--- if ---*/	
	/*--- Programa 7.9 ---*/	
	public void numberIfNode(IfNode x) {

		if (x == null) return;
		x.number = kk++;
		numberExpreNode(x.expr);
		numberStatementNode(x.stat1);
		numberStatementNode(x.stat2);
	}
	
    public void printIfNode(IfNode x) {
        
    	if (x == null) return;

        System.out.println();
        System.out.print(x.number + ": IfNode ===> " + x.expr.number + " " +
            x.stat1.number + " " +
            ((x.stat2 == null) ? "null" : String.valueOf(x.stat2.number)));

        printExpreNode(x.expr);
        printStatementNode(x.stat1);
        printStatementNode(x.stat2);
    }

/*--- IndexNode ---*/
    public void numberIndexNode(IndexNode x) {
        if (x == null) {
            return;
        }

        x.number = kk++;
        numberExpreNode(x.expr1);
        numberExpreNode(x.expr2);
    }

    public void printIndexNode(IndexNode x) {
        if (x == null) {
            return;
        }

        System.out.println();
        System.out.print(x.number + ": IndexNode ===> " + x.expr1.number + " " +
            x.expr2.number);
        printExpreNode(x.expr1);
        printExpreNode(x.expr2);
    }

/*--- IntConstNode ---*/
    public void numberIntConstNode(IntConstNode x) {
        
    	if (x == null) return;
        x.number = kk++;
    }

    public void printIntConstNode(IntConstNode x) {
        if (x == null) {
            return;
        }

        System.out.println();
        System.out.print(x.number + ": IntConstNode ===> " + x.position.image);
    }

///*--- ListNode ---*/
//    /*--- Programa 7.5 ---*/
//	public void numberListNode(ListNode x) {
//		
//		if (x == null) return;
//		// numera x.node
//		numberListNode(x.next);
//	}
    
/*--- MethodBodyNode ---*/
    /*--- Programa 7.7 ---*/
	public void numberMethodBodyNode(MethodBodyNode x) {
		
		if (x == null) return;
		x.number = kk++;
		numberVarDeclListNode(x.param);
		numberStatementNode(x.stat);
	}

    public void printMethodBodyNode(MethodBodyNode x) {
        
    	if (x == null) return;

        System.out.println();
        System.out.print(x.number + ": MethodBodyNode ===> " +
            ((x.param == null) ? "null" : String.valueOf(x.param.number)) +
            " " + x.stat.number);
        printVarDeclListNode(x.param);
        printStatementNode(x.stat);
    }

/*--- MethodDeclNode ---*/
    public void numberMethodDeclNode(MethodDeclNode x) {
        
    	if (x == null) return;
        x.number = kk++;
        numberMethodBodyNode(x.body);
    }

    public void printMethodDeclNode(MethodDeclNode x) {
        
    	if (x == null) return;

        System.out.println();
        System.out.print(x.number + ": MethodDeclNode ===> " +  
        		x.position.image + " " + ((x.dim == 0) ? "" : ("[" + x.dim + "] ")) +
        		x.name.image + " " + x.body.number);
        printMethodBodyNode(x.body);
    }

/*--- MultNode ---*/
    public void numberMultNode(MultNode x) {
        
    	if (x == null) return;
    	
        x.number = kk++;
        numberExpreNode(x.expr1);
        numberExpreNode(x.expr2);
    }

    public void printMultNode(MultNode x) {
        
    	if (x == null) return;

        System.out.println();
        System.out.print(x.number + ": MultNode ===> " + x.expr1.number + " " +
            x.position.image + " " + x.expr2.number);
        printExpreNode(x.expr1);
        printExpreNode(x.expr2);
    }

/*--- NewArrayNode ---*/
    public void numberNewArrayNode(NewArrayNode x) {
        
    	if (x == null) return;

        x.number = kk++;
        numberExpreListNode(x.dims);
    }
    
    public void printNewArrayNode(NewArrayNode x) {
        if (x == null) {
            return;
        }

        System.out.println();
        System.out.print(x.number + ": NewArrayNode ===> " + x.tipo.image +
            " " + ((x.dims == null) ? "null" : String.valueOf(x.dims.number)));

        printExpreListNode(x.dims);
    }

/*--- NewObjectNode ---*/
    public void numberNewObjectNode(NewObjectNode x) {
        
    	if (x == null) return;

        x.number = kk++;
        numberExpreListNode(x.args);
    }

    public void printNewObjectNode(NewObjectNode x) {
        
    	if (x == null) return;

        System.out.println();
        System.out.print(x.number + ": NewObjectNode ===> " + x.name.image +
            " " + ((x.args == null) ? "null" : String.valueOf(x.args.number)));

        printExpreListNode(x.args);
    }

/*--- NopNode ---*/
    public void numberNopNode(NopNode x) {
        if (x == null) {
            return;
        }

        x.number = kk++;
    }

    public void printNopNode(NopNode x) {
        if (x == null) {
            return;
        }

        System.out.println();
        System.out.print(x.number + ": NopNode");
    }

/*--- NullConstNode ---*/
    public void numberNullConstNode(NullConstNode x) {
        if (x == null) {
            return;
        }

        x.number = kk++;
    }

    public void printNullConstNode(NullConstNode x) {
        if (x == null) {
            return;
        }

        System.out.println();
        System.out.print(x.number + ": NullConstNode ===> " + x.position.image);
    }
    
/*--- PrintNode ---*/
    public void numberPrintNode(PrintNode x) {
        if (x == null) {
            return;
        }

        x.number = kk++;
        numberExpreNode(x.expr);
    }

    public void printPrintNode(PrintNode x) {
        if (x == null) {
            return;
        }

        System.out.println();
        System.out.print(x.number + ": PrintNode ===> " + x.expr.number);
        printExpreNode(x.expr);
    }

/*--- ReadNode ---*/
    public void numberReadNode(ReadNode x) {
        if (x == null) {
            return;
        }

        x.number = kk++;
        numberExpreNode(x.expr);
    }

    public void printReadNode(ReadNode x) {
        if (x == null) {
            return;
        }

        System.out.println();
        System.out.print(x.number + ": ReadNode ===> " + x.expr.number);
        printExpreNode(x.expr);
    }

/*--- RelationalAndNode ---*/
    public void numberRelationalAndNode(RelationalAndNode x) {
        
    	if (x == null) return;

        x.number = kk++;

        numberExpreNode(x.expr1);
        numberExpreNode(x.expr2);
    }

    public void printRelationalAndNode(RelationalAndNode x) {
        
    	if (x == null) return;
        
        System.out.println();
        System.out.print(x.number + ": RelationalAndNode ===> " + 
        		(x.expr1 == null ? "null" : String.valueOf(x.expr1.number)) 
        		+ " " + 
        		(x.position == null ? "null" : String.valueOf(x.position.image))
        		+ " " + 
        		(x.expr2 == null ? "null" : String.valueOf(x.expr2.number)));

        printExpreNode(x.expr1);
        printExpreNode(x.expr2);
    }
    
    /*--- RelationalNotNode ---*/
    public void numberRelationalNotNode(RelationalNotNode x) {
        
    	if (x == null) return;

        x.number = kk++;

        numberExpreNode(x.expr1);
//        numberExpreNode(x.expr2);
    }

    public void printRelationalNotNode(RelationalNotNode x) {
        
    	if (x == null) return;
        
        System.out.println();
        System.out.print(x.number + ": RelationalNotNode ===> " + 
        		(x.position == null ? "null" : String.valueOf(x.position.image))
        		+ " " + 
        		(x.expr1 == null ? "null" : String.valueOf(x.expr1.number)));

        printExpreNode(x.expr1);
//        printExpreNode(x.expr2);
    }
    
/*--- RelationalLogicNode ---*/
    public void numberRelationalLogicNode(RelationalLogicNode x) {
        
    	if (x == null) return;

        x.number = kk++;

        numberExpreNode(x.expr1);
        numberExpreNode(x.expr2);
    }

    public void printRelationalLogicNode(RelationalLogicNode x) {
        
    	if (x == null) return;
        
        System.out.println();
        System.out.print(x.number + ": RelationalLogicNode ===> " + 
        		(x.expr1 == null ? "null" : String.valueOf(x.expr1.number)) 
        		+ " " + 
        		(x.position == null ? "null" : String.valueOf(x.position.image))
        		+ " " + 
        		(x.expr2 == null ? "null" : String.valueOf(x.expr2.number)));

        printExpreNode(x.expr1);
        printExpreNode(x.expr2);
    }
    
/*--- RelationalNode ---*/
    public void numberRelationalNode(RelationalNode x) {
        
    	if (x == null) return;

        x.number = kk++;

        numberExpreNode(x.expr1);
        numberExpreNode(x.expr2);
    }

    public void printRelationalNode(RelationalNode x) {
        
    	if (x == null) return;
        
        System.out.println();
        System.out.print(x.number + ": RelationalNode ===> " + 
        		(x.expr1 == null ? "null" : String.valueOf(x.expr1.number)) 
        		+ " " + 
        		(x.position == null ? "null" : String.valueOf(x.position.image))
        		+ " " + 
        		(x.expr2 == null ? "null" : String.valueOf(x.expr2.number)));

        printExpreNode(x.expr1);
        printExpreNode(x.expr2);
    }

    
/*--- VarNode ---*/
    public void numberVarNode(VarNode x) {
        
    	if (x == null) return;
        x.number = kk++;
    }

    public void printVarNode(VarNode x) {
    	
        if (x == null) return;

        System.out.println();
        System.out.print(x.number + ": VarNode ===> " + x.position.image + " " +
        		(x.dim == 0 ? "null" : "[" + x.dim + "]")
        		+ " " +
        		(x.atrib == null ? "null" : x.atrib.image)
        		+ " " +
        		(x.exp == null ? "null" : String.valueOf(x.exp.number))
        		);
        printExpreNode(x.exp);
    }

    
    
/*--- classList ---*/

	/*--- Programa 7.6 ---*/
	public void numberClassDeclListNode(ListNode x) {

		if (x == null) return;
		x.number = kk++;
		numberClassDeclNode((ClassDeclNode) x.node);
		numberClassDeclListNode(x.next);
	}

	public void printClassDeclListNode(ListNode x) {
		if (x == null) {
			return;
		}

		System.out.println();
		System.out.print(x.number + ": ListNode (ClassDeclNode)  ===> " + x.node.number + " "
				+ ((x.next == null) ? "null" : String.valueOf(x.next.number)));

		printClassDeclNode((ClassDeclNode) x.node);
		printClassDeclListNode(x.next);
	}
	
/*--- varDeclList ---*/	
	 
	public void numberVarDeclListNode(ListNode x) {
		if (x == null) {
			return;
		}

		x.number = kk++;
		numberVarDeclNode((VarDeclNode) x.node);
		numberVarDeclListNode(x.next);
	}

	public void printVarDeclListNode(ListNode x) {
		if (x == null) {
			return;
		}

		System.out.println();
		System.out.print(x.number + ": ListNode (VarDeclNode) ===> " + x.node.number + " "
				+ ((x.next == null) ? "null" : String.valueOf(x.next.number)));

		printVarDeclNode((VarDeclNode) x.node);
		printVarDeclListNode(x.next);
	}
	
/*--- varDecl ---*/	

	public void numberVarDeclNode(VarDeclNode x) {
		if (x == null) {
			return;
		}

		x.number = kk++;
		numbervarListNode(x.vars);
	}

	public void printVarDeclNode(VarDeclNode x) {
		if (x == null) {
			return;
		}

		System.out.println();
		System.out.print(x.number + ": VarDeclNode ===> " + 
				x.position.image + " " + x.vars.number);

		printvarListNode(x.vars);
	}
	
/*--- varList ---*/

	public void numbervarListNode(ListNode x) {
		if (x == null) {
			return;
		}

		x.number = kk++;
		numberVarNode((VarNode) x.node);
		numbervarListNode(x.next);
	}

	public void printvarListNode(ListNode x) {
		if (x == null) {
			return;
		}

		System.out.println();
		System.out.print(x.number + ": ListNode (VarNode) ===> " + x.node.number + " "
				+ ((x.next == null) ? "null" : String.valueOf(x.next.number)));

		printVarNode((VarNode) x.node);
		printvarListNode(x.next);
	}
	
/*--- atribList ---*/
	
	public void numberAtribListNode(ListNode x) {
		if (x == null) {
			return;
		}

		x.number = kk++;
		numberAtribNode((AtribNode) x.node);
		numberAtribListNode(x.next);
	}

	public void printAtribListNode(ListNode x) {
		if (x == null) {
			return;
		}

		System.out.println();
		System.out.print(x.number + ": ListNode (AtribNode) ===> " + x.node.number + " "
				+ ((x.next == null) ? "null" : String.valueOf(x.next.number)));

		printAtribNode((AtribNode) x.node);
		printAtribListNode(x.next);
	}

/*--- constructList ---*/
	public void numberConstructDeclListNode(ListNode x) {
		if (x == null) {
			return;
		}

		x.number = kk++;
		numberConstructDeclNode((ConstructDeclNode) x.node);
		numberConstructDeclListNode(x.next);
	}

	public void printConstructDeclListNode(ListNode x) {
		if (x == null) {
			return;
		}

		System.out.println();
		System.out.print(x.number + ": ListNode (ConstructDeclNode) ===> " + x.node.number + " "
				+ ((x.next == null) ? "null" : String.valueOf(x.next.number)));

		printConstructDeclNode((ConstructDeclNode) x.node);
		  printConstructDeclListNode(x.next);
    }

/*--- methodList ---*/
	public void numberMethodDeclListNode(ListNode x) {
		if (x == null) {
			return;
		}

		x.number = kk++;
		numberMethodDeclNode((MethodDeclNode) x.node);
		numberMethodDeclListNode(x.next);
	}

	public void printMethodDeclListNode(ListNode x) {
		if (x == null) {
			return;
		}

		System.out.println();
		System.out.print(x.number + ": ListNode (MethodDeclNode) ===> " + x.node.number + " "
				+ ((x.next == null) ? "null" : String.valueOf(x.next.number)));
		printMethodDeclNode((MethodDeclNode) x.node);
		printMethodDeclListNode(x.next);
	}

    public void numberStatementListNode(ListNode x) {
        if (x == null) {
            return;
        }

        x.number = kk++;
        numberStatementNode((StatementNode) x.node);
        numberStatementListNode(x.next);
    }

    public void printStatementListNode(ListNode x) {
        if (x == null) {
            return;
        }

        System.out.println();
        System.out.print(x.number + ": ListNode (StatementNode) ===> " +
            x.node.number + " " +
            ((x.next == null) ? "null" : String.valueOf(x.next.number)));

        printStatementNode((StatementNode) x.node);
        printStatementListNode(x.next);
    }

    public void numberReturnNode(ReturnNode x) {
        if (x == null) {
            return;
        }

        x.number = kk++;
        numberExpreNode(x.expr);
    }

    public void printReturnNode(ReturnNode x) {
        if (x == null) {
            return;
        }

        System.out.println();
        System.out.print(x.number + ": ReturnNode ===> " +
            ((x.expr == null) ? "null" : String.valueOf(x.expr.number)));
        printExpreNode(x.expr);
    }

    // ------------------------ Comando super --------------------------
    public void numberSuperNode(SuperNode x) {
        if (x == null) {
            return;
        }

        x.number = kk++;
        numberExpreListNode(x.args);
    }

    public void printSuperNode(SuperNode x) {
        if (x == null) {
            return;
        }

        System.out.println();
        System.out.print(x.number + ": SuperNode ===> " +
            ((x.args == null) ? "null" : String.valueOf(x.args.number)));
        printExpreListNode(x.args);
    }

    public void numberExpreListNode(ListNode x) {
        if (x == null) {
            return;
        }

        x.number = kk++;
        numberExpreNode((ExpreNode) x.node);
        numberExpreListNode(x.next);
    }

    public void printExpreListNode(ListNode x) {
        if (x == null) {
            return;
        }

        System.out.println();
        System.out.print(x.number + ": ListNode (ExpreNode) ===> " +
            x.node.number + " " +
            ((x.next == null) ? "null" : String.valueOf(x.next.number)));
        printExpreNode((ExpreNode) x.node);
        printExpreListNode(x.next);
    }

    public void numberUnaryNode(UnaryNode x) {
        if (x == null) {
            return;
        }

        x.number = kk++;
        numberExpreNode(x.expr);
    }

    public void printUnaryNode(UnaryNode x) {
        if (x == null) {
            return;
        }

        System.out.println();
        System.out.print(x.number + ": UnaryNode ===> " + x.position.image +
            " " + x.expr.number);
        printExpreNode(x.expr);
    }

    public void numberStringConstNode(StringConstNode x) {
        if (x == null) {
            return;
        }

        x.number = kk++;
    }

    public void printStringConstNode(StringConstNode x) {
        if (x == null) {
            return;
        }

        System.out.println();
        System.out.print(x.number + ": StringConstNode ===> " +
            x.position.image);
    }

    public void printStatementNode(StatementNode x) {
        if (x instanceof BlockNode) {
            printBlockNode((BlockNode) x);
        } else if (x instanceof VarDeclNode) {
            printVarDeclNode((VarDeclNode) x);
        } else if (x instanceof AtribNode) {
            printAtribNode((AtribNode) x);
        } else if (x instanceof IfNode) {
            printIfNode((IfNode) x);
        } else if (x instanceof ForNode) {
            printForNode((ForNode) x);
        } else if (x instanceof PrintNode) {
            printPrintNode((PrintNode) x);
        } else if (x instanceof NopNode) {
            printNopNode((NopNode) x);
        } else if (x instanceof ReadNode) {
            printReadNode((ReadNode) x);
        } else if (x instanceof ReturnNode) {
            printReturnNode((ReturnNode) x);
        } else if (x instanceof SuperNode) {
            printSuperNode((SuperNode) x);
        } else if (x instanceof BreakNode) {
            printBreakNode((BreakNode) x);
        }
    }

	/*--- Programa 7.8 ---*/	
	public void numberStatementNode(StatementNode x) {

		if (x instanceof BlockNode)
			numberBlockNode((BlockNode) x);
		else if (x instanceof VarDeclNode)
			numberVarDeclNode((VarDeclNode) x);
		else if (x instanceof AtribNode)
			numberAtribNode((AtribNode) x);
		else if (x instanceof IfNode)
			numberIfNode((IfNode) x);
		else if (x instanceof ForNode)
			numberForNode((ForNode)x);
		else if (x instanceof PrintNode)
			numberPrintNode((PrintNode) x);
		else if (x instanceof NopNode)
			numberNopNode((NopNode) x);
		else if (x instanceof ReadNode)
			numberReadNode((ReadNode) x);
		else if (x instanceof ReturnNode)
			numberReturnNode((ReturnNode) x);
		else if (x instanceof SuperNode)
			numberSuperNode((SuperNode) x);
		else if (x instanceof BreakNode)
			numberBreakNode((BreakNode) x);
	}


	
	
	

}
