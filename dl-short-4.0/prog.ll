;LLVM version 3.8.0 (http://llvm.org/)
;program teste
declare i32 @printf(i8*, ...) nounwind
@str_print_int = private unnamed_addr constant [4 x i8] c"%d\0A\00", align 1
@str_print_double = private unnamed_addr constant [7 x i8] c"%.2lf\0A\00", align 1
define i32 @main() nounwind {
%a = alloca i32
store i32 0, i32* %a
%b = alloca i32
store i32 0, i32* %b
%c = alloca double
store double 0.0, double* %c
store i32 2, i32* %a
store i32 3, i32* %b
%1 = load i32, i32* %a
%2 = load i32, i32* %b
%3 = add i32 %1, %2
%4 = sitofp i32 %3 to double
%5 = null double %4, 2.0
store double %5, double* %c
%6 = load double, double* %c
%7 = call i32 (i8*, ...) @printf(i8* getelementptr inbounds([7 x i8], [7 x i8]* @str_print_double, i32 0, i32 0), double %6)
ret i32 0
}
