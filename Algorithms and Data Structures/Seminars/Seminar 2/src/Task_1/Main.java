package Task_1;

import java.util.Scanner;
import java.util.Stack;

public class Main {


    public static void main(String[] args){

        System.out.println(checkBalanceInCPlusPlus("/{}"));

    }





    private static boolean checkBalanceInC(String statement){
        Stack<Character> symbols = new Stack<>();
        char character;

        for (int i = 0; i < statement.length(); i++) {
            character = statement.charAt(i);


            if (character == '{' || character == '[' || character == '(') {
                symbols.push(character); // Opening symbols are added to stack.

            } else if (character == '/' && statement.charAt(i + 1) == '/' && i + 1 < statement.length()) {
                break; // If a comment, then we return the status of stack until the comment symbol.

            } else if (symbols.isEmpty()) { // Always returns true if the statement contains data types... and not just symbols.
                return false; // If there are no opening symbols.

            } else if (getInverse(symbols.peek()) != character) {
                return false; // If the inverse of the bracket is not the current character.

            } else {
                symbols.pop(); // If the inverse of the bracket == character, remove it from stack.
            }
        }

        return symbols.isEmpty(); // empty == balanced.


    }


    private static boolean checkBalanceInCPlusPlus(String statement){
        Stack<Character> symbols = new Stack<>();
        char character;
        for (int i = 0; i < statement.length(); i++) {
            character = statement.charAt(i);

            if(character == '{' || character == '[' || character == '('){
                symbols.push(character);

            }else if(character == '/' && statement.charAt(i + 1) == '/' && i + 1 < statement.length()){
                return symbols.isEmpty();

            }else if(character == '/' && statement.charAt(i + 1) == '*' && i + 1 < statement.length()){
                symbols.push('<');


            }else if(symbols.isEmpty()){
                return false;

            }else if(getInverse(symbols.peek()) != character){
                return false;

            }else{
                symbols.pop();
            }

        }


        return symbols.isEmpty();


    }


    private static char getInverse(char openingSymbol){
        if(openingSymbol == '{'){
            return '}';
        }else if(openingSymbol == '('){
            return ')';
        }else if(openingSymbol == '['){
            return ']';
        }

        return '<'; // > represents "/*."
    }

}
