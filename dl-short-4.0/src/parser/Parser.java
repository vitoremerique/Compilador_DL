package parser;

import java.util.Hashtable;

import inter.Node;
import inter.expr.Bin;
import inter.expr.Expr;
import inter.expr.Id;
import inter.expr.Literal;
import inter.expr.Or;
import inter.expr.Po;
import inter.expr.Rel;
import inter.stmt.Assign;
import inter.stmt.Block;
import inter.stmt.Decl;
import inter.stmt.If;
import inter.stmt.Program;
import inter.stmt.Read;
import inter.stmt.Stmt;
import inter.stmt.While;
import inter.stmt.Write;
import lexer.Lexer;
import lexer.Tag;
import lexer.Token;

public class Parser {
	private Lexer lexer;
	private Token look;
	private	Node root;
	private Hashtable<String, Id> table;

	public Parser(Lexer lex) {
		lexer = lex;
		table = new Hashtable<String, Id>(); 
		move();
	}

	public String parseTree() {
		return root.strTree();
	}

	public String code() {
		return Node.code();
	}

	private void error(String s) {
		System.err.println("linha " 
				+ Lexer.line() 
				+ ": " + s);
		System.exit(0);
	}

	private Token move()  {
		Token save = look;
		look = lexer.nextToken();
		return save;
	}

	private Token match(Tag t) {
		if ( look.tag() == t )
			return move();
		error("'" + look.lexeme() 
				+ "' inesperado");
		return null;
	}

	private Id findId( Token tokId ) {
		Id id = table.get(tokId.lexeme());
		if ( id == null )
			error("a variável '" 
				+ tokId.lexeme() 
				+ "' não foi declarada");
		return id;
	}

	public void parse() {
		Stmt p = program();
		root = p;
		p.gen();
	}

	private Stmt program() {
		match(Tag.PROGRAM);
		Token tokId = match(Tag.ID);
		Stmt b = block();
		match(Tag.DOT);
		match(Tag.EOF);
		return new Program(tokId, (Block)b);
	}

	private Stmt block() {
		Block b = new Block();
		match(Tag.BEGIN);
		while( look.tag() != Tag.END) {
			Stmt s = stmt();
			b.addStmt(s);
			match(Tag.SEMI);
		}
		match(Tag.END);
		return b;
	}

	private Stmt stmt() {
		switch ( look.tag() ) {
		case BEGIN: return block();
		case INT: case REAL: 
			case BOOL: return decl();
		case WRITE: return writeStmt();
		case WHILE: return whileStmt();
		case READ: return ReadStmt();
		case ID: return assign();
		case IF: return ifStmt();
		default: error("comando inválido");
		}
		return null;
	}

	private Stmt decl() {
		Token type = move();
		Token tokId = match(Tag.ID);
		if ( table.get(tokId.lexeme()) == null ) {
			Id id = new Id(tokId, type.tag());
			table.put(tokId.lexeme(), id);
			return new Decl(id);
		}
		error("a variável '" 
				+ tokId.lexeme() 
				+ "' já foi declarada");
		return null;
	}

	private Stmt writeStmt() {
		move();
		match(Tag.LPAREN);
		Id id = findId( match(Tag.ID) );
		match(Tag.RPAREN);
		return new Write(id);
	}
	
	

	private Stmt whileStmt() {
		match(Tag.WHILE);
		match(Tag.LPAREN);
		Expr e = expr();
		match(Tag.RPAREN);
		Block s1 = (Block) block();
		return new While(e, s1);
	}

	private Stmt ReadStmt() {
		move();
		match(Tag.LPAREN);
		Id id = findId( match(Tag.ID) );
		match(Tag.RPAREN);
		return new Read(id);
	}

	private Stmt assign() {
		Id id = findId( match(Tag.ID) );
		match(Tag.ASSIGN);
		Expr e = expr();
		return new Assign(id, e);
	}

	private Stmt ifStmt() {
		match(Tag.IF);
		match(Tag.LPAREN);
		Expr e = expr();
		match(Tag.RPAREN);
		Stmt s1 = stmt();
		return new If(e, s1);
	}

	private Expr expr() {
		Expr e = rel();
		while( look.tag() == Tag.OR ) {
			move();  
			e = new Or(e, rel());
		}
		return e;
	}

	private Expr rel() {
		Expr e = arith();
		while ( look.tag() == Tag.LT || 
				look.tag() == Tag.LE ||
				look.tag() == Tag.GT) {
			Token op = move();
			e = new Rel(op, e, arith());
		}
		return e;
	}

	private Expr arith() {
		Expr e = term();
		while(	look.tag() == Tag.SUM || 
				look.tag() == Tag.SUB ) {
			Token op = move();
			e = new Bin(op, e, term());
		}
		return e;
	}

	private Expr term() {
		Expr e = po();
		while(	look.tag() == Tag.MUL ) {
			Token op = move();
			e = new Bin(op, e, po());
		}
		return e;
	}

	private Expr po() {
		Expr e = factor();
		while(	 look.tag() == Tag.EXP) {
			Token op = move();
			e = new Po(op, e, factor());
		}
		return e;
	}

	



	private Expr factor() {
		Expr e = null;
		switch( look.tag() ) {
		case LPAREN:
			move();
			e = expr();
			match(Tag.RPAREN);
			break;
		case LIT_INT:
			e = new Literal(move(), Tag.INT);
			break;
		case LIT_REAL:
			e = new Literal(move(), Tag.REAL);
			break;
		case TRUE: case FALSE:
			e = new Literal(move(), Tag.BOOL);
			break;
		case ID:
			e = findId( match(Tag.ID) );
			break;
		default:
			error("expressão inválida");
		}
		return e;
	}
}
