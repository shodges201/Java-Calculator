import java.util.*;


class Calculator{
    Map<String, Integer> ops = new HashMap<String, Integer>();
    String regex = "(?<=op)|(?=op)".replace("op", "[-+*/()]");
    
    Calculator(){
	ops.put("+", 1);
        ops.put("-", 2);
        ops.put("*", 3);
        ops.put("/", 4);
    }

    private ArrayList ShuntYard(String infix){
	
    Deque<String> stack = new LinkedList<String>();

	infix = infix.replaceAll("\\s+", "");
	String last = "";
	boolean negative = false;

	ArrayList <String> tokenList = new ArrayList <String>(Arrays.asList(infix.split(regex)));
	ArrayList <String> postfix = new ArrayList<String>();

	System.out.println(tokenList);

	if(tokenList.get(0).equals("-")){
		System.out.println("0 is negative");
		if(tokenList.get(1).matches("\\d+")){
			System.out.println("1 is a number");
			tokenList.set(1, "-" + tokenList.get(1));
			tokenList.remove(0);
		}
	}

	System.out.println(tokenList);

	for(int i = 0; i < tokenList.size(); i ++){
	    String token = tokenList.get(i);
	    System.out.println("preprocess token: " + token);
	    if(negative){
		if(token.matches("\\d+")){
		    String replaceToken = "-" + token;
		    System.out.println("replace: " + replaceToken);
		    tokenList.set(i, replaceToken);
		    tokenList.remove(i-1);
		    negative = false;
		}
	    }
	    if(token.equals("-") && ops.containsKey(last)){
		System.out.println("negative " + last + " " + token);
		negative = true;
	    }
	    last = token;
	}

	System.out.println(tokenList);
	
	for (String token : tokenList) {
	    System.out.println(token);
            // operator
            if (ops.containsKey(token)) {
                while (!stack.isEmpty() && ops.get(token) > ops.get(stack.peek()))
                    postfix.add(stack.pop());
                stack.push(token);
	    }
                // left parenthesis
           else if (token.equals("(")) {
                stack.push(token);
	   }
                // right parenthesis
           else if (token.equals(")")) {
                while (!stack.peek().equals("("))
                    postfix.add(stack.pop());
                stack.pop();

                // digit
            } else {
                postfix.add(token);
            }
	}
	while(!stack.isEmpty()){
	    postfix.add(stack.pop());
	}
	System.out.println(postfix);
	return postfix;
    
    }
    
    public int calculate(String calc)
    {
	ArrayList <String> exp = ShuntYard(calc);
	System.out.println(exp);
        //create a stack
        Stack<Integer> stack=new Stack<>();

        // Scan all characters one by one
        for(String token: exp)
        {
            // If the scanned character is an operand (number here),
            // push it to the stack.
            if(token.matches("-?\\d+")){
                stack.push(Integer.valueOf(token));
	    }

                //  If the scanned character is an operator, pop two
                // elements from stack apply the operator
            else
            {
                int val1 = stack.pop();
                int val2 = stack.pop();

                switch(token)
                {
                    case "+":
                        stack.push(val2+val1);
                        break;

                    case "-":
                        stack.push(val2- val1);
                        break;

                    case "/":
                        stack.push(val2/val1);
                        break;

                    case "*":
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
	    if(ops.containsKey(last) && ops.containsKey(token)){ //if second operand isn't negative then two operators are in a row
		if(!token.equals("-")){
		    return false;
		}
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
			scan.close();
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
