package inter.stmt;

import inter.Emitter;
import inter.expr.Id;
import inter.expr.Literal;
import lexer.Tag;

public class Decl extends Stmt {
	private Id id;

	public Decl(Id i) {
		id = i;
		addChild(id);
	}

	@Override
	public void gen() {
		Literal init = (id.type()==Tag.REAL
				? Emitter.LIT_ZERO_REAL 
				: Emitter.LIT_ZERO_INT);
		code.emitAlloca(id);
		code.emitStore(id, init);
	}

	@Override
	public String toString() {
		return "DECL";
	}
}
