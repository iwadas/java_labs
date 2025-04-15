package matrix;

import java.util.Random;

public class Matrix {

    double[] data;
    int rows;
    int cols;

    Matrix(int rows, int cols){
        this.rows = rows;
        this.cols = cols;
        data = new double[rows * cols];
    }

    // 2.1
    Matrix(double[][] data){
        int maxColumns = 0;
        this.rows = data.length;

        for(int i = 0; i<this.rows; i++){
            if(data[i].length > maxColumns){
                maxColumns = data[i].length;
            }
        }

        this.cols = maxColumns;
        this.data = new double[data.length * maxColumns];

        for(int i=0; i<data.length; i++){
            for(int j=0; j<data[i].length; j++){
                this.data[i*maxColumns + j] = data[i][j];
            }
        }
    }

    // 2.2
    double[][] asArray(){
        double[][] result = new double[this.rows][this.cols];
        for(int i = 0; i<this.rows; i++){
            for(int j = 0; j<this.cols; j++){
                result[i][j] = this.data[i*this.cols + j];
            }
        }
        return result;
    }

    // 2.3
    double get(int r, int c){
        return this.data[r*this.cols + c];
    }
    void set(int r, int c, double value){
        data[this.cols * r + c ] = value;
    }

    // 2.4
    public String toString(){
        StringBuilder buf = new StringBuilder();
        buf.append("[\n");
        for(int i = 0; i<this.rows; i++){
            buf.append("\t[");
            for(int j=0; j<this.cols; j++){
                buf.append(data[this.cols * i + j]);
                if(j<this.cols-1){
                    buf.append(", ");
                }
            }
            buf.append("]\n");
        }
        buf.append("]");
        return buf.toString();
    }

    // 2.5
    void reshape(int newRows, int newCols){
        if(newRows * newCols != this.rows * this.cols){
            throw new RuntimeException(String.format("%d x %d matrix can't be reshaped to %d x %d", this.rows, this.cols, newRows, newCols));
        } else {
            this.rows = newRows;
            this.cols = newCols;
        }
    }

    // 2.6
    int[] shape(){
        return new int[]{this.rows, this.cols};
    }

    // 2.7
    Matrix add(Matrix m){
        return this.mathAction(this, m, "add");
    }

    // 2.8
    Matrix sub(Matrix m){
        return this.mathAction(this, m, "sub");
    }
    Matrix mul(Matrix m){
        return this.mathAction(this, m, "mul");
    }
    Matrix div(Matrix m){
        return this.mathAction(this, m, "div");
    }
    Matrix mathAction(Matrix m1, Matrix m2, String operationType){
        if(m1.rows != m2.rows || m1.cols != m2.cols){
            throw new RuntimeException("Matrix dimensions must agree.");
        }
        Matrix result = new Matrix(m1.rows, m1.cols);
        for(int i = 0; i<m1.rows; i++){
            for(int j = 0; j<m2.cols; j++){
                switch(operationType){
                    case "add":
                        result.set(i, j, m1.get(i,j) + m2.get(i, j));
                        break;
                    case "sub":
                        result.set(i, j, m1.get(i,j) - m2.get(i, j));
                        break;
                    case "mul":
                        result.set(i, j, m1.get(i,j) * m2.get(i, j));
                        break;
                    case "div":
                        result.set(i, j, m1.get(i,j) / m2.get(i, j));
                        break;
                }
            }
        }
        return result;
    }

    void vectorAction(double w, String operationType){
        for(int i = 0; i<this.rows; i++){
            for(int j = 0; j<this.cols; j++){
                switch(operationType){
                    case "add":
                        this.set(i, j, this.get(i, j) + w);
                        break;
                    case "sub":
                        this.set(i, j, this.get(i, j) - w);
                        break;
                    case "mul":
                        this.set(i, j, this.get(i,j) * w);
                        break;
                    case "div":
                        this.set(i, j, this.get(i,j) / w);
                        break;

                }
            }
        }
    }
    void add(double w){
        this.vectorAction(w, "add");
    }
    void sub(double w){
        this.vectorAction(w, "sub");
    }
    void mul(double w){
        this.vectorAction(w, "mul");
    }
    void div(double w){
        this.vectorAction(w, "div");
    }

    // 2.9
    Matrix dot(Matrix m){
        if(this.cols != m.rows){
            throw new RuntimeException("Matrix dimensions must agree.");
        }

        Matrix result = new Matrix(this.rows, m.cols);
        for(int i = 0; i<m.cols; i++){
            for(int j = 0; j<this.rows; j++){
                double sum = 0;
                for(int k = 0; k<this.cols; k++){
                    sum += this.get(j, k) * m.get(k, i);
                }
                result.set(j, i, sum);
            }
        }

        return result;
    }

    // 2.10
    double frobenius(){
        double sum = 0;
        for(int i = 0; i<this.rows; i++){
            for(int j = 0; j<this.cols; j++){
                sum += Math.pow(this.get(i, j), 2);
            }
        }
        return Math.sqrt(sum);
    }

    // 2.11
    public static Matrix random(int rows, int cols){
        Matrix m = new Matrix(rows,cols);
        Random r = new Random();
        for(int i = 0; i<rows; i++){
            for(int j = 0; j<cols; j++){
                m.set(i,j, r.nextDouble());
            }
        }
        return m;
    }

    public static Matrix eye(int n){
        Matrix m = new Matrix(n,n);
        for(int i = 0; i<n; i++){
            m.set(i, i, 1);
        }
        return m;
    }
}
