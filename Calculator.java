import java.util.*;


class Calculator{
    Map<String, Integer> ops = new HashMap<String, Integer>();

    Calculator(){
	ops.put("+", 1);
        ops.put("-", 2);
        ops.put("*", 3);
        ops.put("/", 4);
    }

    private String ShuntYard(String infix){
	
        StringBuilder output = new StringBuilder();
        Deque<String> stack = new LinkedList<String>();

        for (String token : infix.split("\\s+")) {
            // operator
            if (ops.containsKey(token)) {
                while (!stack.isEmpty() && ops.get(token) > ops.get(stack.peek()))
                    output.append(stack.pop());
                stack.push(token);

                // left parenthesis
            } else if (token.equals("(")) {
                stack.push(token);

                // right parenthesis
            } else if (token.equals(")")) {
                while (!stack.peek().equals("("))
                    output.append(stack.pop());
                stack.pop();

                // digit
            } else {
                output.append(token);
            }
        }
	while(!stack.isEmpty()){
	    output.append(stack.pop());
	}
	return output.toString();
    
    }
    
    public int calculate(String calc)
    {
	String exp = ShuntYard(calc);
	
        //create a stack
        Stack<Integer> stack=new Stack<>();

        // Scan all characters one by one
        for(int i=0;i<exp.length();i++)
        {
            char c=exp.charAt(i);

            // If the scanned character is an operand (number here),
            // push it to the stack.
            if(Character.isDigit(c))
                stack.push(c - '0');

                //  If the scanned character is an operator, pop two
                // elements from stack apply the operator
            else
            {
                int val1 = stack.pop();
                int val2 = stack.pop();

                switch(c)
                {
                    case '+':
                        stack.push(val2+val1);
                        break;

                    case '-':
                        stack.push(val2- val1);
                        break;

                    case '/':
                        stack.push(val2/val1);
                        break;

                    case '*':
                        stack.push(val2*val1);
                        break;
                }
            }
        }
        return stack.pop();
    }

    private boolean checkExpression(String exp){
	int parens = 0;
	String last = "";
	if(exp.equals("")){
	    return false;
	}
	for (String token : exp.split("\\s+")) {
	    if(token.equals("(") || token.equals(")")){
		parens += 1;
	    }
	    if(ops.containsKey(last) && ops.containsKey(token)){
		return false;
	    }
	    if(last.matches("-?\\d+") && token.matches("-?\\d+")){
		return false;
	    }
	    last = token;
	}
	System.out.print("num parens: ");
	System.out.println(parens);
	if(parens % 2 != 0){
	    return false;
	}
	return true;
    }

    public static void main(String[] args){
	Calculator c = new Calculator();
	Scanner scan = new Scanner(System.in);
	System.out.println("When Prompted, enter an expression to be calculated or type 'exit' to quit. Please seperate each item in the expression by a space");
	int sum = 0;
	while(true){
	    sum = 0;
	    System.out.println("What would you like to evaluate: ");
	    String exp = scan.nextLine();
	    boolean check = c.checkExpression(exp);
	    if(exp.equals("exit")){
		System.exit(1);
		}
	    else if(!check){
		System.out.println("Please enter an expression to be evaluated");
	    }
	    else{
		sum = c.calculate(exp);
		System.out.println(sum);
	    }
	}
	
    }

}
