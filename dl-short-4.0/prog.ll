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
%format_str_double = getelementptr inbounds [4 x i8], [4 x i8]* @str_scanf_double, i32 0, i32 0
%format_str = getelementptr inbounds [3 x i8], [3 x i8]* @str_scanf_int, i32 0, i32 0
%a = alloca i32
store i32 0, i32* %a
%b = alloca i32
store i32 0, i32* %b
%c = alloca i32
store i32 0, i32* %c
%1 = load i32, i32* %a
%2 = call i32 (i8*, ...) @scanf(i8* %format_str, i32* %a)
%3 = load i32, i32* %b
%4 = call i32 (i8*, ...) @scanf(i8* %format_str, i32* %b)
%5 = load i32, i32* %a
%6 = call i32 (i8*, ...) @printf(i8* getelementptr inbounds([4 x i8], [4 x i8]* @str_print_int, i32 0, i32 0), i32 %5)
%7 = load i32, i32* %b
%8 = call i32 (i8*, ...) @printf(i8* getelementptr inbounds([4 x i8], [4 x i8]* @str_print_int, i32 0, i32 0), i32 %7)
%9 = load i32, i32* %a
%10 = load i32, i32* %b
%11 = sitofp i32 %9 to double
%12 = sitofp i32 %10 to double
%13 = call double @llvm.pow.f64(double %11, double %12)
%14 = fptosi double %13 to i32
store i32 %14, i32* %c
%15 = load i32, i32* %c
%16 = call i32 (i8*, ...) @printf(i8* getelementptr inbounds([4 x i8], [4 x i8]* @str_print_int, i32 0, i32 0), i32 %15)
ret i32 0
}
