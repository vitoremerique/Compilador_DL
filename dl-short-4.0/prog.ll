;LLVM version 3.8.0 (http://llvm.org/)
;program trabalho
declare i32 @printf(i8*, ...) nounwind
declare double @llvm.pow.f64(double, double)
@str_print_int = private unnamed_addr constant [4 x i8] c"%d\0A\00", align 1
@str_print_double = private unnamed_addr constant [7 x i8] c"%.2lf\0A\00", align 1
define i32 @main() nounwind {
%a = alloca double
store double 0.0, double* %a
%b = alloca i32
store i32 0, i32* %b
%c = alloca double
store double 0.0, double* %c
%1 = sitofp i32 2 to double
store double %1, double* %a
store i32 3, i32* %b
%2 = load double, double* %a
%3 = load double, double* %a
%4 = call double @llvm.pow.f64 (double %2, double %3)
store double %4, double* %c
%5 = load double, double* %c
%6 = call i32 (i8*, ...) @printf(i8* getelementptr inbounds([7 x i8], [7 x i8]* @str_print_double, i32 0, i32 0), double %5)
ret i32 0
}
