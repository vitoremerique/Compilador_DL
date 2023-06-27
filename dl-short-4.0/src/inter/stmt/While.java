package inter.stmt;

import inter.expr.Expr;
import lexer.Tag;



public class While extends Stmt{
    private Expr expr;
	
    private Block block;
	
	public While(Expr e, Block s) {
		if ( !e.type().isBool() ){
			error("esperada uma "
				+ "expressão lógica");
        }
		expr = e;
		block = s;
		addChild(expr);
		addChild(block);
	}

	@Override
	public void gen() {
		int l1 = code.newLabel();
		int l2 = code.newLabel();
        int l3 = code.newLabel();
		code.emitBreak(l1);
        code.emitLabel(l1);
		
        
		
        expr.jumping(l2, l3);
		
		code.emitLabel(l2);	
        block.gen();
        code.emitBreak(l1);

        code.emitLabel(l3);

      
            
	}

	@Override
	public String toString() {
		return Tag.WHILE.toString();
	}
}
