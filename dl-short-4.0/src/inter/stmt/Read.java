package inter.stmt;

import inter.expr.Expr;
import inter.expr.Id;
import inter.expr.Temp;
import lexer.Tag;

public class Read extends Stmt{
    	private Id id;

	public Read(Id i) {
		id = i;
		addChild(id);
	}

	@Override
	public void gen() {

		 Expr e = id.gen();
      if(e.type().isInt())
         code.emitReadInt(e, id);
      else
         code.emitReadDouble(e, id);
		
	}

	@Override
	public String toString() {
		return Tag.READ.toString();
	}
}
