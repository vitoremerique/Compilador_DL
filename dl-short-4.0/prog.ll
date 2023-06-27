;LLVM version 3.8.0 (http://llvm.org/)
;program trabalho
declare i32 @printf(i8*, ...) nounwind
declare i32 @scanf(i8*, ...)
declare double @llvm.pow.f64(double, double)
@str_print_int = private unnamed_addr constant [4 x i8] c"%d\0A\00", align 1
@str_print_double = private unnamed_addr constant [7 x i8] c"%.2lf\0A\00", align 1
@str_scanf_int = private unnamed_addr constant [3 x i8] c"%d\00", align 1
@str_scanf_double = private unnamed_addr constant [4 x i8] c"%lf\00", align 1
define i32 @main() nounwind {
%a = alloca i32
store i32 0, i32* %a
%b = alloca double
store double 0.0, double* %b
%1 = load i32, i32* %a
%format_str = getelementptr inbounds [3 x i8], [3 x i8]* @str_scanf_int, i32 0, i32 0
%2 = call i32 (i8*, ...) @scanf(i8* %format_str, i32* %a)
%3 = load double, double* %b
%format_str_double = getelementptr inbounds [4 x i8], [4 x i8]* @str_scanf_double, i32 0, i32 0
%4 = call i32 (i8*, ...) @scanf(i8* %format_str_double, double* %b)
%5 = load i32, i32* %a
%6 = call i32 (i8*, ...) @printf(i8* getelementptr inbounds([4 x i8], [4 x i8]* @str_print_int, i32 0, i32 0), i32 %5)
%7 = load double, double* %b
%8 = call i32 (i8*, ...) @printf(i8* getelementptr inbounds([7 x i8], [7 x i8]* @str_print_double, i32 0, i32 0), double %7)
ret i32 0
}
