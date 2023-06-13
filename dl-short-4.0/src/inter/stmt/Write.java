package inter.stmt;

import inter.expr.Expr;
import inter.expr.Id;
import lexer.Tag;

public class Write extends Stmt {
	private Id id;

	public Write(Id i) {
		id = i;
		addChild(id);
	}

	@Override
	public void gen() {
		Expr e = id.gen();
		code.emitWrite(e);
	}

	@Override
	public String toString() {
		return Tag.WRITE.toString();
	}
}
