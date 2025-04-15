package math;

import java.util.ArrayList;
import java.util.List;

public class Sum extends Node {

    List<Node> args = new ArrayList<>();

    Sum(){}

    Sum(Node n1, Node n2){
        args.add(n1);
        args.add(n2);
    }

    Sum add(Node n){
        if(n.isZero()) return this;
        args.add(n);
        return this;
    }

    Sum add(double c){
        if(c==0)return this;
        args.add(new Constant(c));
        return this;
    }

    Sum add(double c, Node n) {
        Node mul = new Prod(c,n);
        args.add(mul);
        return this;
    }

    @Override
    double evaluate() {
        double result =0;
        for(Node n : args) {
            result += n.evaluate();
        }
//        oblicz sumę wartości zwróconych przez wywołanie evaluate skłądników sumy
        return sign*result;
    }

    int getArgumentsCount(){return args.size();}

    public String toString(){

        StringBuilder b =  new StringBuilder();
        if(sign<0)b.append("-(");

//        checks if there is already something written out - to make sure + is not written out before the first element
        boolean firstElementWritten = false;

        for(Node arg:args){
//            b.append("(");
//            b.append(arg.isZero());
//            b.append(arg.toString());
//            b.append(")");
            if(arg.isZero()) continue;
            if(firstElementWritten) b.append(" + ");
            if(arg.getSign() == -1) b.append("(");
            b.append(arg.toString());
            if(arg.getSign() == -1) b.append(")");
            firstElementWritten = true;
        }

        if(sign<0)b.append(")");
        return b.toString();
    }

    public Node diffVanilla(Variable var){
        Sum r = new Sum();
        for(Node n:args){
            Node diff = n.diff(var);
            if(!diff.isZero()) r.add(diff);
        }
        return r;
    }

    @Override
    Node diff(Variable var) {
        return diffVanilla(var);
    }

    @Override
    boolean isZero(){
        for(Node n:args){
            if(!n.isZero()) return false;
        }
        return true;
    }


}