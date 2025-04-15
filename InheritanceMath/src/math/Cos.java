package math;

public class Cos extends Node {
    Node arg;

    Cos(Node n) {
        arg = n;
    }

    Cos(double c) {
        arg = new Constant(c);
    }

    @Override
    double evaluate() {
        double argVal = arg.evaluate();
        return Math.cos(argVal);
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
        b.append("cos");
        if(arg.getSign() == -1) b.append("-");
        if (useBracket) b.append("(");
        b.append(arg.toString());
        if (useBracket) b.append(")");
        return b.toString();
    }

    @Override
    Node diff(Variable var) {
        // Derivative of cos(x) is -sin(x) * (derivative of the argument)
        Prod r = new Prod(-1, new Sin(arg));
        r.mul(arg.diff(var));
        return r;
    }

    @Override
    boolean isZero() {
        return false;
    }
}
