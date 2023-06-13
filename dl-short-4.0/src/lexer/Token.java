package lexer;

public class Token {
	private Tag tag;
	private String lexeme;

	public Token(Tag t, String l) {
		tag = t;
		lexeme = l;
	}
	
	public Tag tag() {
		return tag;
	}

	public String lexeme() {
		return lexeme;
	}

	@Override
	public String toString() {
		return "<" + tag + 
				", '" + lexeme + "'>";
	}
}