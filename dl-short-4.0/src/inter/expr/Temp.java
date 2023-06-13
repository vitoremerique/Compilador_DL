package inter.expr;

import lexer.Tag;
import lexer.Token;

public class Temp extends Expr {
	private static int count = 1;
	private int number;
	private static Token TOKEN_TEMP = 
			new Token(Tag.TEMP, "");
	
	public Temp(Tag type) {
		super(TOKEN_TEMP, type);
		number = count++;
	}
	
	public int getNumber() {
		return number;
	}
	
	@Override	
	public Expr gen() {
		return this;
	}
	
	@Override
	public String toString() {
		return "%" + number;
	}
}
