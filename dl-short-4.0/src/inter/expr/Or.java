package inter.expr;

import inter.Emitter;
import lexer.Tag;
import lexer.Token;

public class Or extends Expr {
	protected Expr expr1;
	protected Expr expr2;
	
	public Or(Expr e1, Expr e2) {
		super(new Token(Tag.OR, "|"), Tag.BOOL);
		if ( !e1.type().isBool() ||  
			 !e2.type().isBool() )
			error("O operador lógico | só "
					+ "pode ser aplicado entre "
					+ "tipos booleanos");
		expr1 = e1;
		expr2 = e2;
		addChild(expr1);
		addChild(expr2);
	}

	@Override
	public Expr gen() {
		int t = code.newLabel();
		int f = code.newLabel();
		int out = code.newLabel();
		Temp d1 = new Temp(Tag.BOOL);
		code.emitAlloca(d1);
		this.jumping(t, f);
		code.emitLabel(t);
		code.emitStore(d1, Emitter.LIT_TRUE );
		code.emitBreak(out);
		code.emitLabel(f);
		code.emitStore(d1, Emitter.LIT_FALSE);
		code.emitBreak(out);
		code.emitLabel(out);
		Temp d2 = new Temp(Tag.BOOL);
		code.emitLoad(d2, d1);
		return d2;
	}
	
	@Override
	public void jumping(int t, int f) {
		int label = code.newLabel();
		expr1.jumping(t, label);
		code.emitLabel(label);
		expr2.jumping(t, f);
	}	
}
