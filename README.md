# LR-Parsing-Table-

This was an assignment for one of my classes. There are two actions: Shift and Reduce. There were also 6 grammar rules that needed to be followed. We had to use a LR Parse table to find the shift or reduce values to edit the stack with each iteration. 

Here is the input that works for this code: id+(id+id)$

First, the stack starts with the value "0"
To get the first action (either shift or reduce), you must take the value "0" and the first piece of the input and use it as an (X,Y) value to find the correct value in the table. 

Here is the table used: (Just paste link in browser)
![image](https://user-images.githubusercontent.com/68085511/139118112-2388835e-1e36-483f-a5fa-3991462aea16.png)

It would take the two values (from stack and input) and use it to find the value in the table above. 
If the value returned is a Shift, which is shown as "S" in the table, then it pushes the input value that was used and the number that was next to the "S" in the table. (Example: Taking value "0" from stack and input value "id" and using it in table, S5 will be returned. The new stack contents would be [0,id,5]) 

then it would use the "5" from the stack and the next input value, which is "+" and find the next action value. This process will repeat until you reach a reduce action.

Reduce means to reduce the contents of the stack based on the grammar rule specified. In the table above, you will see values with "R", this means reduce and the number following it will be the grammar rule number that needs to be used. 

Here are the grammar rules for this assignment: (Just paste link in browser)
![image](https://user-images.githubusercontent.com/68085511/139118962-46b5b060-7987-48db-a814-cfa0096c849d.png)

If the table returns a value "R4" then its saying to use the grammar rule #4 and edit the stack by substituting. 

In the example above, the stack contained [0,id,5]. Lets say the table returned "R6", this means to edit the stack by using grammar rule 6. Grammar rule 6 says to replace "id" with "F". When reducing, if there are other values in between or on top, they must be removed as well. In this case the "5" would be removed as well. So the new stack would contain: [0,F]. Once this is done, the program would take the "0" and the "F" and use it to find another value from the table.

In the end, the two values need to map to "accept" for the program to finish. Thats when you know its correct. 

If there are any questions, email me at: lpatrizi81399@gmail.com
