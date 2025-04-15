package math;

import java.util.ArrayList;
import java.util.List;

public class Prod extends Node {

    public List<Node> args = new ArrayList<>();

    Prod(){}

    Prod(Node n1){
        args.add(n1);
    }

    Prod(double c){
//        wywołaj konstruktor jednoargumentowy przekazując new Constant(c)
        this.mul(new Constant(c));
    }

    Prod(Node n1, Node n2){
        this.mul(n1);
        this.mul(n2);
    }

    Prod(double c, Node n){
//        wywołaj konstruktor dwuargumentowy
        this.mul(new Constant(c));
        this.mul(n);
    }

    Prod mul(Node n){
//        handling multiplication by constant
        if(n instanceof Constant){
//            if there is 1.0, and list is not empty don't add it
            if(!args.isEmpty() && n.evaluate() == 1.0){
                return this;
            }

//            if there is another constant in the list, multiply them
            for(Node arg:args){
                if(arg instanceof Constant){
                    double newArg = arg.evaluate()*n.evaluate();
                    args.remove(arg);
                    if(newArg != 1.0 || args.isEmpty()){
                        args.add(new Constant(newArg));
                    }
                    return this
                ;}
            }
        }

        if(n instanceof Prod p){
            for(Node arg : p.args){
                this.mul(arg);
            }
            return this;
        }

        if(n instanceof Sum s){
            if(s.args.size() == 1){
                this.mul(s.args.getFirst());
                return this;
            }
        }

//        hanlde all other scenarios
        args.add(n);
        return this;
    }

    Prod mul(double c){
//        this handles multiplication by constant
        return this.mul(new Constant(c));
    }

    @Override
    double evaluate() {
        double result =1;
        // oblicz iloczyn czynników wołąjąc ich metodę evaluate
        for(Node n : args){
           result *= n.evaluate();
        }
        return sign*result;
    }

    int getArgumentsCount(){return args.size();}

    private boolean checkIfParenthesesNeeded(Node n){
        boolean result = false;
        if(n instanceof Sum s){
            if (s.args.size() > 1) result = true;
        }
        if(n.getSign() == -1) result = true;
        return result;
    }

    @Override
    public String toString(){
        if(isZero())return "0";
        StringBuilder b =  new StringBuilder();
        if(sign<0)b.append("-(");
        for(int i = 0; i < args.size(); i++){
            Node n = args.get(i);
            if(i != 0) b.append("*");

            boolean addParentheses = checkIfParenthesesNeeded(n);
            if(addParentheses) b.append("(");
            b.append(n.toString());
            if(addParentheses) b.append(")");
        }
        if(sign<0)b.append(")");
        return b.toString();
    }

    @Override
    Node diff(Variable var){
        return diffVanilla(var);
    }

    Node diffVanilla(Variable var){
        Sum r = new Sum();
        for(int i=0;i<args.size();i++){
            Prod m = new Prod();
            for(int j=0;j<args.size();j++){
                Node f = args.get(j);
                if(j==i)m.mul(f.diff(var));
                else m.mul(f);
            }
            r.add(m);
        }
        return r;
    }

    boolean isZero(){
        for (Node n : args) {
            if (n.isZero()) {
                return true;
            }
        };
        return false;
    }

}