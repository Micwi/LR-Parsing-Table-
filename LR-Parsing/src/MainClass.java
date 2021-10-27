import java.util.*;
import javax.swing.*;
/*
 * @author Louie Patrizi
 * 
 * CSCI-621 Midterm Assignment - Prof. Li
 */
public class MainClass {
	public static Stack<String> stack = new Stack<String>(); //initialize stack of type String
	public static parsingTableClass pTableClass = new parsingTableClass(); //object for table in other class
	private static GrammarRules grammarRules = new GrammarRules(); //object for grammar rules in other class
	
	//numerical values for all column headers in table, respectively
	public static int idInt = 1; 
	public static int plusInt = 2;
	public static int multiplyInt = 3;
	public static int openPar = 4;
	public static int closePar = 5;
	public static int dollarSign = 6;
	public static int eInt = 7;
	public static int tInt = 8;
	public static int fInt = 9;
	
	private static String action = ""; // variable for action (shift or reduce)

	public static void main(String[] args) {

		pTableClass.start(); // to load up the table, must be executed for code to work because code is referencing this 
		
		// **********UNDO comment below to view the Parsing Table***********
		// parsingTableClass.frame.setVisible(true);

		calculate("id+(id+id)$"); // This input WORKS -> ends with "accept" as action

		// **********UNDO comment below for input string that doesn't work ***********
		//calculate("id*(id+id)$");
	}

	public static void calculate(String input) {
		stack.push("0"); // pushes initial value to stack
		String currentString = ""; // variable that is equal to each increment of input
		String shiftNum = ""; // stores the number at the end shift action
		String reduceNum = "";// stores the number at the end reduce action

		// prints original values of everything
		System.out.println("Original input: " + input);
		System.out.println("Stack contents: " + stack);
		System.out.println("Current String: " + currentString);
		System.out.println("------------------------------");

		boolean shiftFound = false; // boolean for when there are multiple reduces in a row

		for (int i = 0; i < input.length() - 1; i++) {

			if (input.charAt(0) == 'i') { // searches for 'i' and 'd'
				currentString = input.substring(input.indexOf('i'), input.indexOf('d') + 1);

				System.out.println("Current String: " + currentString); // prints current string
				System.out.println("Current Input Expression: " + input); // prints current input expression
				stack.push(currentString); // pushes "id" to the stack
				System.out.println("Current Stack Contents: " + stack); // prints the current elements in stack
				action = (String) parsingTableClass.table.getValueAt(Integer.parseInt(stack.get(stack.size() - 2)),
						idInt); // taking the values from stack and using it to get value in table (row,column)
				System.out.println("Current Action: " + action); //prints the current action (shift or reduce)
				System.out.println("------------------------------");
				
				//seeing if action is shift or reduce
				//action = shift
				if (action.charAt(0) == 'S') {
					shiftNum = action.charAt(1) + ""; //setting shiftNum = to value after 'S'
					input = input.replaceFirst("id", ""); //removes first "id" in input
					System.out.println("Current Input Expression: " + input); // prints current input expression
					stack.push(shiftNum); //pushes shiftNum to stack
					System.out.println("Current Stack Contents: " + stack); // prints the current elements in stack
					if (i > 0) { // makes it so 'i' is always 0 when searching input string with for loop
						i--;
					}
					//action = reduce
				} else if (action.charAt(0) == 'R') {
					reduceNum = action.charAt(1) + ""; //setting reduce num = to value after 'R'
					stack.push(reduceNumMethod(reduceNum)); // method to get specific rules with reduceNum

				}
			}
			
			if (input.charAt(i) == '+') {

				currentString = input.charAt(i) + "";
				System.out.println("Current String: " + currentString);
				System.out.println("Current Input Expression: " + input); // prints current input expression
				action = (String) parsingTableClass.table.getValueAt(Integer.parseInt(shiftNum), plusInt); // uses shiftNum and the plusInt value to get correct action
				System.out.println("Current Action: " + action); //prints the current action (shift or reduce)
				
				//for when multiple reduces are present
				while (shiftFound == false) {
					
					if (action.charAt(0) == 'S') {
						//checks to see if the value following the action is more than 1 digit
						if (action.length() > 2) {
							shiftNum = action.charAt(1) + "" + action.charAt(2); //if yes, sets shiftNum = to it
						} else {
							shiftNum = action.charAt(1) + "";
						}
						stack.push(currentString);
						stack.push(shiftNum);
						System.out.println("Current Stack Contents: " + stack); // prints the current elements in stack
						input = input.replaceFirst("\\+", ""); //removes '+' from input expression
						if (i > 0) {// makes it so 'i' is always 0 when searching input string with for loop
							i--;
						}
						shiftFound = true;//end while loop since action was a shift and not a reduce

					} else if (action.charAt(0) == 'R') {
						reduceNum = action.charAt(1) + "";
						System.out.println("------------------------------");
						System.out.println("Current Stack Contents: " + stack); // prints the current elements in stack
						System.out.println("Current Action: " + action); //prints the current action (shift or reduce)
						System.out.println("Reduce Number: " + reduceNum);
						stack.push(reduceNumMethod(reduceNum)); // method to get specific rules with reduceNum
						action = actionAfterReduction(stack, plusInt);
						System.out.println("Current Stack Contents: " + stack); // prints the current elements in stack

					}

				}

			}

			if (input.charAt(i) == '(') {
				currentString = input.charAt(i) + "";
				System.out.println("Current String: " + currentString);
				System.out.println("Current Input Expression: " + input); // prints current input expression
				action = (String) parsingTableClass.table.getValueAt(Integer.parseInt(shiftNum), openPar);
				System.out.println("Current Action: " + action); //prints the current action (shift or reduce)

				if (action.charAt(0) == 'S') {
					shiftFound = true;
					if (action.length() > 2) {
						shiftNum = action.charAt(1) + "" + action.charAt(2);
					} else {
						shiftNum = action.charAt(1) + "";
					}
					stack.push(currentString);
					stack.push(shiftNum);
					System.out.println("Current Stack Contents: " + stack); // prints the current elements in stack
					input = input.replaceFirst("\\(", "");
					System.out.println("Current Input Expression: " + input); // prints current input expression
					if (i > 0) {// makes it so 'i' is always 0 when searching input string with for loop
						i--;
					}
				} else if (action.charAt(0) == 'R') {
					reduceNum = action.charAt(1) + "";
					System.out.println("------------------------------");
					System.out.println("Current Stack Contents: " + stack); // prints the current elements in stack
					System.out.println("Current Action: " + action); //prints the current action (shift or reduce)
					System.out.println("Reduce Number: " + reduceNum);
					stack.push(reduceNumMethod(reduceNum)); // method to get specific rules with reduceNum
					action = actionAfterReduction(stack, openPar);
					System.out.println("Current Stack Contents: " + stack); // prints the current elements in stack

				}
			}
			if (input.charAt(i) == ')') {
				currentString = input.charAt(i) + "";
				System.out.println("Current String: " + currentString);
				System.out.println("Current Input Expression: " + input); // prints current input expression
				action = (String) parsingTableClass.table.getValueAt(Integer.parseInt(shiftNum), closePar);
				System.out.println("Current Action: " + action); //prints the current action (shift or reduce)
				while (shiftFound == false) {
					if (action.charAt(0) == 'S') {
						shiftFound = true;
						if (action.length() > 2) {
							shiftNum = action.charAt(1) + "" + action.charAt(2);
						} else {
							shiftNum = action.charAt(1) + "";
						}
						stack.push(currentString);
						stack.push(shiftNum);
						System.out.println("Current Stack Contents: " + stack); // prints the current elements in stack
						input = input.replaceFirst("\\)", "");
						System.out.println("Current Input Expression: " + input); // prints current input expression
						if (i > 0) {// makes it so 'i' is always 0 when searching input string with for loop
							i--;
						}
					} else if (action.charAt(0) == 'R') {
						reduceNum = action.charAt(1) + "";
						System.out.println("------------------------------");
						System.out.println("Current Stack Contents: " + stack); // prints the current elements in stack
						System.out.println("Current Action: " + action); //prints the current action (shift or reduce)
						System.out.println("Reduce Number: " + reduceNum);
						stack.push(reduceNumMethod(reduceNum)); // method to get specific rules with reduceNum
						action = actionAfterReduction(stack, closePar);
						System.out.println("Current Stack Contents: " + stack); // prints the current elements in stack

					}
				}

			}
			shiftFound = false;
			if (input.charAt(i) == '$') {
				currentString = input.charAt(i) + "";
				System.out.println("Current String: " + currentString);
				System.out.println("Current Input Expression: " + input); // prints current input expression
				action = (String) parsingTableClass.table.getValueAt(Integer.parseInt(shiftNum), dollarSign);
				System.out.println("Current Action: " + action); //prints the current action (shift or reduce)
				while (shiftFound == false) {
					if (action.charAt(0) == 'S') {
						shiftFound = true;
						if (action.length() > 2) {
							shiftNum = action.charAt(1) + "" + action.charAt(2);
						} else {
							shiftNum = action.charAt(1) + "";
						}
						stack.push(currentString);
						stack.push(shiftNum);
						System.out.println("Current Stack Contents: " + stack); // prints the current elements in stack
						input = input.replaceFirst("\\)", "");
						System.out.println("Current Input Expression: " + input); // prints current input expression
						if (i > 0) {
							i--;
						}
					} else if (action.charAt(0) == 'R') {
						reduceNum = action.charAt(1) + "";
						System.out.println("------------------------------");
						System.out.println("Current Stack Contents: " + stack); // prints the current elements in stack
						System.out.println("Current Action: " + action); //prints the current action (shift or reduce)
						System.out.println("Reduce Number: " + reduceNum);
						stack.push(reduceNumMethod(reduceNum)); // method to get specific rules with reduceNum
						action = actionAfterReduction(stack, dollarSign);
						System.out.println("Current Stack Contents: " + stack); // prints the current elements in stack

					} else {
						System.out.println("===========================================");
						System.out.println("PROGRAM FINISHED!");
						System.out.println("Current Action: " + action); //prints the current action (shift or reduce)
						System.out.println("===========================================");
						shiftFound = true;
					}
				}

			}

		}

	}
	//method for getting the correct grammar rule based on the reduce num value
	public static String reduceNumMethod(String reduceNum) {
		String numberAfterReduction = "";
		if (Integer.parseInt(reduceNum) == 1) {// E -> E + T
			System.out.println("Grammar rule one is used (E -> E + T)");
			numberAfterReduction = grammarRules.rule(stack, "E", "E");
		} else if (Integer.parseInt(reduceNum) == 2) { // E -> T
			System.out.println("Grammar rule two is used (E -> T)");
			numberAfterReduction = grammarRules.rule(stack, "T", "E");
		} else if (Integer.parseInt(reduceNum) == 3) { // T-> T * F
			System.out.println("Grammar rule three is used (T-> T * F)");
			numberAfterReduction = grammarRules.rule(stack, "T", "T");
		} else if (Integer.parseInt(reduceNum) == 4) { // T -> F
			System.out.println("Grammar rule four is used (T -> F)");
			numberAfterReduction = grammarRules.rule(stack, "F", "T");
		} else if (Integer.parseInt(reduceNum) == 5) { // F -> (E)
			System.out.println("Grammar rule five is used (F -> (E))");
			numberAfterReduction = grammarRules.ruleFive(stack);// FIX
		}

		else if (Integer.parseInt(reduceNum) == 6) { // F -> id
			System.out.println("Grammar rule six is used (F -> id)");
			numberAfterReduction = grammarRules.ruleSix(stack);
		}
		return numberAfterReduction;
	}
	//method to get the action after reducing the stack
	public static String actionAfterReduction(Stack stack, int columnNum) {
		action = (String) parsingTableClass.table.getValueAt(Integer.parseInt((String) stack.get(stack.size() - 1)),
				columnNum);

		System.out.println("Current Action: " + action); //prints the current action (shift or reduce)
		return action;
	}

}
