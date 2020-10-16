package com.oneday.demo.algorithm.calculator;

import java.util.Stack;

/**
 * Desc:手写计算器
 * Company: XueHai
 * Copyright: Copyright (c) 2016
 *
 * @author JiLin
 * @version 1.0
 * @since 2020/9/27 0027 10:14
 */
public class CalculatorTest {
    public static void main(String[] args) {
        string2Int("458");
        string2Int("-458");
        string2Int("2147483647");
        string2Int("2147483648");
        string2Int("102");
        string2Int("001");
        System.out.println(Integer.parseInt("001"));


        System.out.println(addAndSubtractCalculate("3+5-4") == 4);
        System.out.println(addAndSubtractCalculate("-6-5-4") == -15);
        System.out.println(addAndSubtractCalculate("-100  +    500  -4") == 396);


        System.out.println(arithmeticCalculate("-100  *    500  -4") == -50004);
        System.out.println(arithmeticCalculate("4  *    7  +4") == 32);
        System.out.println(arithmeticCalculate("4  - 7 * 4") == -24);

        System.out.println(calculate("(-20+2*9)") == -2);
        System.out.println(calculate("4+(-20+2*9)-2") == 0);
        System.out.println(calculate("3*(4-5/2)-6") == 0);
        System.out.println(calculate("(3*(4-5/2) + 6)-6") == 6);
        System.out.println(calculate("(3*(4-5/2) + 6)*(8-2*2)-6") == 42);
        System.out.println(calculate("((3*(4-5/2) + 6)*(8-2*2)-6)") == 42);
    }

    /*将String转为数字（不带符号）*/
    private static int string2Int(String str) {
        if (str == null || str.length() == 0) {
            return -1;
        }

        int n = 0;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            n = 10 * n + (c - '0');
        }

        printResult("string2Int(" + str + ")", n);
        return n;
    }

    /*加减法计算器*/
    private static int addAndSubtractCalculate(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        Stack<Integer> stack = new Stack<>();
        // 记录算式中的数字
        int num = 0;
        // 记录num前的符号，初始化为'+'
        char sign = '+';
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (isDigit(c)) {
                // 如果是数字，连续读取到num
                num = 10 * num + (c - '0');
            }

            if ((!isDigit(c) && c != ' ') || i == chars.length - 1) {
                // 如果不是数字并且也不是空格，就是遇到了下一个符号；之前的数字和符号就要存进栈中
                switch (sign) {
                    case '+':
                        stack.push(num);
                        break;
                    case '-':
                        stack.push(-num);
                        break;
                }

                //更新符号为当前符号，数字清零
                sign = c;
                num = 0;
            }
        }

        //将栈中所有结果求和就是答案
        int result = getResult(stack);

        printResult("addAndSubtractCalculate(" + s + ")", result);
        return result;
    }

    /*加减乘除四则运算计算器(不包含括号)*/
    private static int arithmeticCalculate(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        Stack<Integer> stack = new Stack<>();
        // 记录算式中的数字
        int num = 0;
        // 记录num前的符号，初始化为'+'
        char sign = '+';
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (isDigit(c)) {
                // 如果是数字，连续读取到num
                num = 10 * num + (c - '0');
            }

            if ((!isDigit(c) && c != ' ') || i == chars.length - 1) {
                // 如果不是数字并且也不是空格，就是遇到了下一个符号；之前的数字和符号就要存进栈中
                int pre;
                switch (sign) {
                    case '+':
                        stack.push(num);
                        break;
                    case '-':
                        stack.push(-num);
                        break;
                    case '*':
                        // 拿出前一个数做运算即可
                        pre = stack.pop();
                        stack.push(pre * num);
                        break;
                    case '/':
                        pre = stack.pop();
                        stack.push(pre / num);
                        break;
                }

                //更新符号为当前符号，数字清零
                sign = c;
                num = 0;
            }
        }

        //将栈中所有结果求和就是答案
        int result = getResult(stack);

        printResult("arithmeticCalculate(" + s + ")", result);
        return result;
    }

    private static int calculate(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        return calculate(s, 0)[1];
    }

    /*加减乘除四则运算计算器（包含括号）*/
    private static int[] calculate(String s, int start) {
        int[] arr = new int[2];

        if (start < 0 || start >= s.length()) {
            return arr;
        }

        Stack<Integer> stack = new Stack<>();
        // 记录算式中的数字
        int num = 0;
        // 记录num前的符号，初始化为'+'
        char sign = '+';
        char[] chars = s.toCharArray();
        for (int i = start; i < chars.length; i++) {
            char c = chars[i];
            if (isDigit(c)) {
                // 如果是数字，连续读取到num
                num = 10 * num + (c - '0');
            }

            if (isSign(c) || i == chars.length - 1) {
                // 如果是符号；之前的数字和符号就要存进栈中
                pushStack(stack, num, sign);

                //更新符号为当前符号，数字清零
                sign = c;
                num = 0;
            }

            if (c == '(') {
                // 如果是括号开始，把括号内当成一个整体递归计算结果
                int[] calculate = calculate(s, i + 1);

                // 处理表达式整体被括号包裹的情况
                if (calculate[0] == chars.length - 1) {
                    printResult("calculate(" + s + ")", calculate[1]);
                    return calculate;
                }

                i = calculate[0];
                num = calculate[1];
            } else if (c == ')') {
                pushStack(stack, num, sign);
                // 如果是括号结束，返回括号内计算结果
                arr[0] = i;
                arr[1] = getResult(stack);
                return arr;
            }
        }

        //将栈中所有结果求和就是答案
        int result = getResult(stack);
        printResult("calculate(" + s + ")", result);

        arr[0] = s.length() - 1;
        arr[1] = result;
        return arr;
    }

    private static void pushStack(Stack<Integer> stack, int num, char sign) {
        int pre;
        switch (sign) {
            case '+':
                stack.push(num);
                break;
            case '-':
                stack.push(-num);
                break;
            case '*':
                // 拿出前一个数做运算即可
                pre = stack.pop();
                stack.push(pre * num);
                break;
            case '/':
                pre = stack.pop();
                stack.push(pre / num);
                break;
        }
    }

    private static void printResult(String s, int result) {
        System.out.println(s + " = " + result);
    }

    private static int getResult(Stack<Integer> stack) {
        int result = 0;
        while (!stack.empty()) {
            result += stack.pop();
        }

        return result;
    }

    /*判断给定字符是否是数字*/
    private static boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    /*判断给定字符是否是运算符号*/
    private static boolean isSign(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }
}
