#include <stdio.h>
#include "Task2.h"


int main() {
    double input = getInput();
    approximate(input);
}

double getInput(){
    double size;
    printf("Enter the value of x: ");
    scanf("%lf", &size);
    return size;

}


double factorial(int number){
    double sum =  1;

    if(number != 0 || number != 1){
        for (int i = number; i > 0; i--) {
            sum*= (double)i;
        }
    }
    return sum;

}


double power(double base, int exponent){
    double sum = 1;

    for (int i = exponent; i > 0 ; i--) {
        sum = (base < 0)? (1/(sum*base)) : (sum*base);
    }
    return sum;

}


double fabs(double value){
    return (value < 0)? value*(-1) : value;
}


void approximate(double input){
    double sum = 0;
    int range = 0;
    double MINIMUM_TERM_VALUE = 0.0000001;

    printf("\n\t%s\t%s\n", "terms", "value");
    for (int i = 0; i <= range; i++) {
        for (int j= 0; j < i; j++) {
            sum= sum + (power(input, j)/factorial(j));
        }

        if(i >= 1){
            printf("\t%d\t%.7f\n", i, sum);
        }
        if(fabs(power(input, range)/factorial(range)) < MINIMUM_TERM_VALUE){
            break;
        }else{
            sum = 0;
            range++;
        }

    }
}
