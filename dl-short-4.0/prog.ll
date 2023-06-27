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
store i32 10, i32* %a
br label %L1
L1:
br i1 true, label %L2, label %L3
L2:
%1 = load i32, i32* %a
%2 = load i32, i32* %b
%3 = add i32 %1, %2
store i32 %3, i32* %b
%4 = load i32, i32* %b
%5 = call i32 (i8*, ...) @printf(i8* getelementptr inbounds([4 x i8], [4 x i8]* @str_print_int, i32 0, i32 0), i32 %4)
br label %L1
L3:
ret i32 0
}
