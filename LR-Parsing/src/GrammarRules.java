import java.util.Stack;
//@author Micwi
public class GrammarRules extends MainClass {
	public String number = "";
	int stackSize = 0;
	public void ruleStart() {

	}

	// For rules 1 to 4
	public String rule(Stack stack, String searchingChar, String returnChar) {

		int firstAppearance = 0;

		stackSize = stack.size() - 1; //value set to keep the exact size of the stack (since it keeps changing when you remove elements)
		int count = stack.size() - 1; // total length starting from 1 is 13, 0 is 12 otherwise
		for (int i = 0; i < stack.size() - 1; i++) {
			if (stack.get(i).equals(searchingChar) && searchingChar != returnChar) {
				stack.removeElementAt(i);
				stack.pop();
				stack.push(returnChar);
				System.out.println("Stack in  method: " + stack);
				break;
			} else if (searchingChar == returnChar && stack.get(count).equals("E")) {
				firstAppearance = count;
				stack.removeElementAt(count);
				for (int j = count; j < stackSize; j++) {
					stack.pop();
				}
				stack.push(returnChar);
				System.out.println("Stack after pop: " + stack); // prints stack after loop is done (with pop())
				break;
			}
			count--; 

		}
		//gets the correct number value from the table based on the returnChar value (E,T,F)
		if (returnChar.equals("E")) {
			number = (String) parsingTableClass.table.getValueAt(Integer.parseInt((String) stack.get(stack.size() - 2)),
					eInt);
		} else if (returnChar.equals("T")) {
			number = (String) parsingTableClass.table.getValueAt(Integer.parseInt((String) stack.get(stack.size() - 2)),
					tInt);
		} else if (returnChar.equals("F")) {
			number = (String) parsingTableClass.table.getValueAt(Integer.parseInt((String) stack.get(stack.size() - 2)),
					fInt);
		}

		System.out.println("Number to add to stack after reduce: " + number); //prints out number

		return number;

	}

	public String ruleFive(Stack stack) { // F -> (E)
		int indexOfValBeforeopenParIndex = 0;// variable for index of element before '('
		int openParIndex = 0; // variable for index of '('
		int eIndex = 0;// variable for index of 'E'
		int closeParIndex = 0;// variable for index of ')'
		stackSize = stack.size() - 1;
		
		//searching for chars and setting index = to variables
		for (int i = 0; i < stack.size() - 1; i++) {
			if (stack.get(i).equals("(")) {
				openParIndex = i;
			} else if (stack.get(i).equals("E")) {
				eIndex = i;
			} else if (stack.get(i).equals(")")) {
				closeParIndex = i;
			}
		}
		/*System.out.println("open Par index: " + openParIndex);
		System.out.println("E index: " + eIndex);
		System.out.println("close Par index: " + closeParIndex);
		
		**** Used for testing
		*/
		
		indexOfValBeforeopenParIndex = openParIndex - 1; //setting variable to index before '('
		//loop to pop all stack elements, including '(', after the first parenthesis '('
		for(int j = indexOfValBeforeopenParIndex; j < stackSize; j++) { 
			stack.pop();
		}
		System.out.println("Stack contents rule 5 pop loop: " + stack); // used to see the contents of stack after popping. 
		if(stack.get(indexOfValBeforeopenParIndex).equals(stack.peek())) { //makes sure the element at the top of stack is = to element before '('
			stack.push("F"); //pushes correct substitute value to new stack
			number = (String) parsingTableClass.table.getValueAt(Integer.parseInt((String) stack.get(stack.size() - 2)),
					fInt); //gets values using the value at the top of stack with the value of fInt
		} else {
			stack.pop(); //if stack has more values on top of stack that arent the element before '(', pops them out 
		}
		return number;
	}

	public String ruleSix(Stack stack) { // F -> id
		//loop to find "id" and remove it, then removes top element in stack and pushes "F" (since thats the rule)
		for (int i = 0; i < stack.size() - 1; i++) {
			if (stack.get(i).equals("id")) {
				stack.removeElementAt(i);
				stack.pop();
				stack.push("F");
				break;
			}
		}
		number = (String) parsingTableClass.table.getValueAt(Integer.parseInt((String) stack.get(stack.size() - 2)),
				fInt); //finds the correct value for number

		System.out.println("Number to add to stack after reduce: " + number);

		return number;

	}
}
