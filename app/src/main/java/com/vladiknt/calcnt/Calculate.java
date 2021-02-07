package com.vladiknt.calcnt;

import java.util.ArrayList;

public class Calculate {

    public static String result(String input) {
        String result;
        try {
            result = calcFunc(new String[]{"0", input})[1];

            if (result.equals("NaN"))
                result = "error";
                // Rounding to int if result is something like 4.0
            else if ((long)(Double.parseDouble(result) * 100000000) == (long)Double.parseDouble(result) * 100000000)
                result = String.valueOf((int)Double.parseDouble(result));
                // To void situations like "0.1 + 0.2 = 0.3000000000004"
            else if (((long)(Double.parseDouble(result) * 100000000))/100000000.0 != Double.parseDouble(result))
                result = String.valueOf(((long)(Double.parseDouble(result) * 100000000))/100000000.0);

            if (result.equals(String.valueOf(9.223372036854776E10)))
                result = "very much";
        } catch (Exception math) {
            return "error";
        }

        return result;
    }

    private static String[] /* first - i, second - result*/ calcFunc(String[] input /*first - i, second - expression*/) {
        ArrayList<String> list = new ArrayList<String>();
        String number = ""; // if we have a part of number, we`ll add it there and get a full number after all
        int i = Integer.parseInt(input[0]);
        String str = input[1];
        while (i < str.length()) {
            if (str.charAt(i) == ')') {
                i++;
                break;
            }
            if (str.charAt(i) != '(') {
                switch (str.charAt(i)) {
                    case '+':
                    case '-':
                    case '×':
                    case '÷':
                    case '^':
                        list.add("" + str.charAt(i));
                        break;
                    default:
                        try {
                            if (str.charAt(i + 1) != '.')
                                Integer.parseInt("" + str.charAt(i + 1));
                            // if next char is digit
                            number += str.charAt(i);
                        } catch (Exception e) {
                            // if next char isn`t digit
                            number += str.charAt(i);
                            list.add(number);
                            number = "";
                        }
                        break;
                }
                i++;
            } else {
                String[] output = calcFunc(new String[]{String.valueOf(i + 1), str});
                i = Integer.parseInt(output[0]);
                list.add(output[1]);
            }
        }

        // calculating from list
        int iterator = 0;
        double a, b;
        // ^ firstly
        while (true) {
            if (iterator == list.size()) {
                iterator = 0;
                break;
            }
            if (list.get(iterator).equals("^")) {
                b = Double.parseDouble(list.remove(iterator + 1));
                list.remove(iterator);
                a = Double.parseDouble(list.remove(iterator - 1));
                list.add(iterator - 1, String.valueOf(Math.pow(a, b)));
                iterator = 0;
            }
            iterator++;
        }
        // × and ÷
        while (true) {
            if (iterator == list.size()) {
                iterator = 0;
                break;
            }
            if (list.get(iterator).equals("×")) {
                b = Double.parseDouble(list.remove(iterator + 1));
                list.remove(iterator);
                a = Double.parseDouble(list.remove(iterator - 1));
                list.add(iterator - 1, String.valueOf(a * b));
                iterator = 0;
            }
            if (list.get(iterator).equals("÷")) {
                b = Double.parseDouble(list.remove(iterator + 1));
                list.remove(iterator);
                a = Double.parseDouble(list.remove(iterator - 1));
                list.add(iterator - 1, String.valueOf(a / b));
                iterator = 0;
            }
            iterator++;
        }
        // + and -
        while (true) {
            if (list.size() == 1)
                break;
            if (list.get(iterator).equals("+")) {
                b = Double.parseDouble(list.remove(iterator + 1));
                list.remove(iterator);
                a = Double.parseDouble(list.remove(iterator - 1));
                list.add(iterator - 1, String.valueOf(a + b));
                iterator = 0;
            }
            if (list.get(iterator).equals("-")) {
                b = Double.parseDouble(list.remove(iterator + 1));
                list.remove(iterator);
                a = Double.parseDouble(list.remove(iterator - 1));
                list.add(iterator - 1, String.valueOf(a - b));
                iterator = 0;
            }
            iterator++;
        }
        return new String[]{String.valueOf(i), list.remove(0)};
    }

}
