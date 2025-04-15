package math;

public class Sin extends Node {
    Node arg;

    Sin(Node n) {
        arg = n;
    }

    @Override
    double evaluate() {
        double argVal = arg.evaluate();
        return Math.sin(argVal);
    }

    private boolean checkIfParenthesesNeeded(){
        boolean result = false;
        if(arg instanceof Sum s){
            if (s.args.size() > 1) result = true;
        }
        if(arg instanceof Prod p){
            if (p.args.size() > 1) result = true;
        }
        if(arg.getSign() == -1 ) result = true;
        return result;
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        if (sign < 0) b.append("-");
        boolean useBracket = checkIfParenthesesNeeded();
        b.append("sin");
        if(arg.getSign() == -1) b.append("-");
        if (useBracket) b.append("(");
        b.append(arg.toString());
        if (useBracket) b.append(")");
        return b.toString();
    }

    @Override
    Node diff(Variable var) {
        // Derivative of sin(x) is cos(x) * (derivative of the argument)
        Prod r = new Prod(new Cos(arg));
        r.mul(arg.diff(var));
        return r;
    }

    @Override
    boolean isZero() {
        return false;
    }
}