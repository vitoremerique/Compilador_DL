package inter;

import inter.expr.Expr;
import inter.expr.Literal;
import inter.expr.Temp;
import lexer.Tag;
import lexer.Token;

public final class Emitter {
	public static final Literal LIT_TRUE = new Literal(new Token(Tag.TRUE, "verdadeiro"), Tag.BOOL);
	public static final Literal LIT_FALSE = new Literal( new Token(Tag.FALSE, "falso"), Tag.BOOL);
	public static final Literal LIT_ZERO_INT = new Literal( new Token(Tag.LIT_INT, "0"), Tag.INT);
	public static final Literal LIT_ZERO_REAL = new Literal( new Token(Tag.LIT_REAL, "0.0"), Tag.REAL);
	
	private StringBuffer code;
	private int label;

	public Emitter() {
		code = new StringBuffer();
		label = 0;
	}

	@Override
	public String toString() {
		return code.toString();
	}

	public int newLabel() {
		return ++label;
	}

	public void emit(String s) {
		code.append(s + "\n");
	}

	//L6:
	public void emitLabel(int label) {
		emit("L" + label + ":");
	}

	//%id = alloca i32, align 4
	public void emitAlloca(Expr var) {
		emit( var + " = alloca " 
				+ codeType(var.type()));
	}

	//store i32 %4, i32* %1, align 4
	public void emitStore(Expr dest, Expr value) {
		emit( "store " 
				+ codeType(dest.type()) 
				+ " " + value + ", " 
				+ codeType(dest.type()) 
				+ "* " + dest);
	}

	//%1 = load i32, i32* %id, align 4
	public void emitLoad(Expr dest, Expr value) {
		emit( dest + " = load " 
				+ codeType(dest.type()) + ", "
				+ codeType(dest.type()) + "* " 
				+ value);
	}

	/*
	 * Uso o tipo do operando e não do destino, pois no comando
	 * %3 = fcmp olt double 0.0, %2
	 * o destino é booleano, mas os operandos double
	 */
	//%6 = add i32 %4, %5
	public void emitOperation(Expr dest, Expr op1, Expr op2, Tag op) {
		emit( dest + " = " 
				+ codeOperation(op, op1.type()) 
				+ " " + codeType(op1.type()) 
				+ " " + op1 + ", " + op2 ); 
	}

	//%26 = sitofp i32 1 to double
	public void emitConvert(Expr dest, Expr op) {
		emit( dest + " = " 
				+ "sitofp i32 " 
				+ op + " to double" );
	}

	//br i1 %22, label %L6, label %L7
	public void emitBreak(Expr cond, int lt, int lf) {
		emit("br i1 " + cond 
				+ ", label %L" + lt 
				+ ", label %L" + lf);
	}

	//br label %L10
	public void emitBreak(int label) {
		emit("br label %L" + label);
	}

	//%24 = call i32 (i8*, ...) @printf(i8* getelementptr inbounds([7 x i8], [7 x i8]* @str_print_double, i32 0, i32 0), double %23) ; var %23
	public void emitWrite(Expr id) {
		String str = "[4 x i8], [4 x i8]* @str_print_int";
		if ( id.type().isReal() )
			str = "[7 x i8], [7 x i8]* @str_print_double";
		Temp tPrint = new Temp(id.type());
		emit( tPrint + " = call i32 (i8*, ...) "
				+ "@printf(i8* getelementptr inbounds"
				+ "(" + str + ", i32 0, i32 0), "
				+ codeType(id.type()) + " "
				+ id + ")" );
	}

	public static String codeType(Tag type) {
		switch (type) {
		case BOOL: return "i1";
		case INT: return "i32";
		case REAL: return "double";
		default: return "";
		}
	}

	public static String codeOperation(Tag op, Tag type) {
		if ( type.isReal() ) {
			switch( op ) {
			case SUM: return "fadd";
			case SUB: return "fsub";
			case MUL: return "fmul";
			case LT:  return "fcmp olt";
			case LE:  return "fcmp ole";
			case GT:  return "fcmp ogt";
			default:  return null;
			}
		} else {
			switch( op ) {
			case SUM: return "add";
			case SUB: return "sub";
			case MUL: return "mul";
			case LT:  return "icmp slt";
			case LE:  return "icmp sle";
			case GT:  return "icmp sgt";
			default: return null;
			}	
		}
	}

	public void emitHead(Token name) {
		emit(";LLVM version 3.8.0 (http://llvm.org/)");
		emit(";program " + name.lexeme());
		emit("declare i32 @printf(i8*, ...) nounwind");
		emit("@str_print_int = private unnamed_addr constant [4 x i8] c\"%d\\0A\\00\", align 1");
		emit("@str_print_double = private unnamed_addr constant [7 x i8] c\"%.2lf\\0A\\00\", align 1");
		emit("define i32 @main() nounwind {");
	}

	public void emitFoot() {
		emit("ret i32 0");
		emit("}");
	}


}
