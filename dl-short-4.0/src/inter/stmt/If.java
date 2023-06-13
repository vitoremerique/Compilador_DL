package inter.stmt;


import inter.expr.Expr;
import lexer.Tag;

public class If extends Stmt {
	private Expr expr;
	private Stmt stmt;
	
	public If(Expr e, Stmt s) {
		if ( !e.type().isBool() )
			error("esperada uma "
				+ "expressão lógica");
		expr = e;
		stmt = s;
		addChild(expr);
		addChild(stmt);
	}

	@Override
	public void gen() {
		int l1 = code.newLabel();
		int l2 = code.newLabel();
		expr.jumping(l1, l2);
		
		code.emitLabel(l1);
		stmt.gen();
		
		code.emitBreak(l2);
		code.emitLabel(l2);	
	}

	@Override
	public String toString() {
		return Tag.IF.toString();
	}
}
