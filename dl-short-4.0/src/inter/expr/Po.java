package inter.expr;


import lexer.Tag;
import lexer.Token;

public class Po extends Expr{


    protected Expr expr1;
	protected Expr expr2;

	public Po( Token op, Expr e1, Expr e2 ) {
		super(op, null);
		type = maxType( e1.type(), e2.type() );
		if ( this.type == null ) 
			error("tipos incompatíveis");
		expr1 = e1;
		expr2 = e2;
		addChild(expr1);
		addChild(expr2);
	}

	private static Tag maxType( Tag t1, Tag t2 ) {
		if ( !t1.isNum() || !t2.isNum() )
			return null;
		else if ( t1.isReal() || t2.isReal() )
			return Tag.REAL;
		else
			return Tag.INT;
	}
	

	// code += "  %base = sitofp i32 " + base + " to double\n";
    // code += "  %exponent = sitofp i32 " + exponent + " to double\n";
    // code += "  %result = call double @power(double %base, double %exponent)\n";
    // code += "  %result_integer = fptosi double %result to i32\n";
    // code += "  ; Código intermediário gerado para potenciação de inteiros\n";
    // code += "  ; %result_integer contém o resultado convertido para inteiro\n";

	@Override
	public Expr gen() {
		Expr e1 = expr1.gen();
		Expr e2 = expr2.gen();
		Expr op1 = widenPo(e1, e2.type() );
		Expr op2 = widenPo(e2, e2.type() );
		Temp d = new Temp(type);
		code.emitPo(d, op1, op2, op.tag());
		
		if(e1.type().isInt() && e2.type().isInt()){
			Temp f = new Temp(Tag.INT);
			code.emitConvertToInt(f ,d);
			
			 return f;
		}
		return d;
		}
    

    @Override
	public String toString() {
		return Tag.EXP.toString();
	}
}
