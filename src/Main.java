import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        Stack<Stack<String>> supplyStacks = new Stack<>();
        supplyStacks.add(new Stack<>());
//    Stack<String> testStack = supplyStacks.get(0);
//
//    testStack.add(0, "A");
//
//    String top = testStack.get(0);
//
//    testStack.remove(0);

        try {
            final int ROWS = getNumberOfRows();
            setNumberOfRows(ROWS, supplyStacks);
            int nextLineToScan = populateRows(supplyStacks);
            System.out.println(nextLineToScan);
            completeMoves(supplyStacks, nextLineToScan);
            System.out.println("\n\n");
            for (int i = 1; i < supplyStacks.size(); i++) {
                System.out.print(supplyStacks.get(i).peek());
            }
            System.out.println("\n\n");
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public static int getNumberOfRows() throws FileNotFoundException {
        FileInputStream fis = new FileInputStream("input.txt");
        Scanner scan = new Scanner(fis);
        String line = scan.nextLine();
        scan.close();
        return (line.length() + 1) / 4;
    }

    public static void setNumberOfRows(int rows, Stack<Stack<String>> supplyStacks) {
        for (int i = 0; i < rows; i++) {
            supplyStacks.add((new Stack<>()));
        }
    }

    public static int populateRows(Stack<Stack<String>> supplyStacks) throws FileNotFoundException {
        int rowLeftOffAt = 0;
        FileInputStream fis = new FileInputStream("input.txt");
        Scanner scan = new Scanner(fis);
        String line = scan.nextLine();
        final int CHARS = (line.length() + 1) / 4;

        for (int i = 1; i < 9; i++) {
            int incrementer = 0;
            for (int j = 0; j < CHARS; j++) {
                String string = line.substring(incrementer + 1, incrementer + 2);
                if (!(string.equals(" "))) {
                    int counter = 0;
                    supplyStacks.get(j + 1).add(counter, string);
                }
                incrementer += 4;
            }
            line = scan.nextLine();
            rowLeftOffAt++;
        }
        scan.close();
        return rowLeftOffAt + 2;
    }

    public static void completeMoves(Stack<Stack<String>> supplyStacks, int nextLineToScan) throws FileNotFoundException {
        FileInputStream fis = new FileInputStream("input.txt");
        Scanner scan = new Scanner(fis);
        for (int i = 0; i < nextLineToScan - 1; i++) {
            scan.nextLine();
        }
        while (scan.hasNextLine()) {
            int[] importantNumbers = new int[3];
            for (int i = 0; i < 3; i++) {
                String garbageBits = scan.next();
                importantNumbers[i] = scan.nextInt();
            }
            System.out.println(importantNumbers[0] + " " + importantNumbers[1] + " " + importantNumbers[2]);
            if (importantNumbers[0] == 1) {
                String stack = "";
                stack = stack + supplyStacks.get(importantNumbers[1]);
                System.out.println(stack);
                stack = String.valueOf(supplyStacks.get(importantNumbers[1])).replaceAll(" ", "").replaceAll(",", "").replaceAll("\\[|\\]", "");
                //string = string.substring(string.length() - 1);
                System.out.println(stack.length());
                stack = stack.substring(stack.length() - importantNumbers[0], stack.length() - importantNumbers[0] + 1);
                supplyStacks.get(importantNumbers[2]).push(stack);
                supplyStacks.get(importantNumbers[1]).remove(stack);
            }else {
                for (int i = 1; i < importantNumbers[0] + 1; i++) {
                    String stack = "";
                    stack = stack + supplyStacks.get(importantNumbers[1]);
                    System.out.println(stack);
                    stack = String.valueOf(supplyStacks.get(importantNumbers[1])).replaceAll(" ", "").replaceAll(",", "").replaceAll("\\[|\\]", "");
                    //string = string.substring(string.length() - 1);
                    System.out.println(stack.length());
                    stack = stack.substring(stack.length() - importantNumbers[0] + (i - 1), stack.length() - importantNumbers[0] + 1 + (i - 1));
                    supplyStacks.get(importantNumbers[2]).push(stack);
                    supplyStacks.get(importantNumbers[1]).remove(stack);
                }
            }

        }
        scan.close();
    }
}