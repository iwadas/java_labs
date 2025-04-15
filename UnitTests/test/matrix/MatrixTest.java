package matrix;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MatrixTest {

    static Matrix matrix1;
    static Matrix matrix2;

    @Before
    public void setUp() {
        matrix1 = new Matrix(2,4);
        matrix2 = new Matrix(new double[][] {{1,2,3,4}, {5,6,7}, {8,9}});
    }

//1. Matrix - sprawdza rozmiary macierzy
    @Test
    public void testRowsAndCols() {
        assertEquals(2, matrix1.rows);
        assertEquals(4, matrix1.cols);
        assertEquals(3, matrix2.rows);
        assertEquals(4, matrix2.cols);
    }

//2. Matrix - sprawdza czy są dodawane zera aby macierz była prostokątna.
    @Test
    public void testConstructor(){

        double[][] input = {{1,2,3,4}, {5,6,7}, {8,9}};
        Matrix matrix2 = new Matrix(input);
        double[][] result = matrix2.asArray();

        double[][] expected = {{1,2,3,4}, {5,6,7,0}, {8,9,0,0}};

        for(int i = 0; i < matrix2.rows; i++){
//            sprawdz ilosc kolumn
            assertEquals(expected[i].length, result[i].length);
//            sprawcz czy dodalo zera na koncu
            int diff = matrix2.cols - input[i].length;
            if(diff>0){
                for(int j=0; j<diff; j++){
                    assertEquals(0, result[i][input[i].length + j], 0.001);
                }
            }
        }
    }

//3. asArray
    @Test
    public void checkEachRow(){
        double[][] expected = {{1,2,3,4}, {5,6,7,0}, {8,9,0,0}};
        double[][] result = matrix2.asArray();

        for(int i = 0; i < matrix2.rows; i++){
            assertArrayEquals(expected[i], result[i], 0.001);
        }
    }

//    4. get/set
    @Test
    public void testGet(){
        assertEquals(1, matrix2.get(0, 0), 0.001);
        assertEquals(6, matrix2.get(1, 1), 0.001);
        assertEquals(0, matrix2.get(1, 3), 0.001);
    }
    @Test
    public void testSet(){
        matrix2.set(1, 1, 10);
        assertEquals(10, matrix2.get(1, 1), 0.001);
    }

//5. ToString() - funkcja sprawdza czy zgadza się liczba przecinków w stringu
    @Test
    public void testToString() {
        int expectedCommas = (matrix2.cols - 1)* matrix2.rows;
//        2 initial brackets + 2 for each row
        int expectedBrackets = 2 + 2 * matrix2.rows;
        String result = matrix2.toString();
        int actualCommas = result.length() - result.replace(",", "").length();
        int actualBrackets = result.length() - result.replace("[", "").replace("]", "").length();

        assertEquals(expectedCommas, actualCommas);
        assertEquals(expectedBrackets, actualBrackets);
    }

//6. reshape - sprawdzam czy macierz niespelniajaca wymagan zwraca wyjatek
    @Test
    public void testReshapeException(){
        try{
            matrix2.reshape(5, 5);
            fail("Should throw RuntimeException");
        } catch (RuntimeException e){}
    }

//    7. sub - testuje pewien przypadek
    @Test
    public void testSub(){

        Matrix matrix3 = new Matrix(new double[][] {{1,1,1,1}, {1,1,1,1}, {1,1,1,1}});
        Matrix matrixAfterSub = matrix2.sub(matrix3);

        double[][] result = matrixAfterSub.asArray();
        double[][] expected = {{0,1,2,3}, {4,5,6,-1}, {7,8,-1,-1}};

        for(int i = 0; i < matrix3.rows; i++){
            assertArrayEquals(expected[i], result[i], 0.001);
        }
    }

//    8. mul - testuje pewien przypadek z mnozeniem przez liczbe
    @Test
    public void testMul(){

        matrix2.mul(2);

        double[][] expected = {{2,4,6,8}, {10,12,14,0}, {16,18,0,0}};
        double[][] result = matrix2.asArray();

        for(int i = 0; i < matrix2.rows; i++){
            assertArrayEquals(expected[i], result[i], 0.001);
        }
    }

//    9. add - testuje pewien przypadek z dodaniem drugiej macierzy
    @Test
    public void testAdd(){
        Matrix matrix3 = new Matrix(new double[][] {{1,1,1,1}, {1,1,1,1}, {1,1,1,1}});
        Matrix matrixAfterAdd = matrix2.add(matrix3);

        double[][] result = matrixAfterAdd.asArray();
        double[][] expected = {{2,3,4,5}, {6,7,8,1}, {9,10,1,1}};

        for(int i = 0; i < matrix3.rows; i++){
            assertArrayEquals(expected[i], result[i], 0.001);
        }
    }

//    10. div - testuje pewien przypadek z dzieleniem przez druga macierz
    @Test
    public void testDiv(){

        Matrix matrix3 = new Matrix(new double[][] {{1,2,1,2}, {1,2,1,2}, {2,1,2,2024}});
        Matrix resultMatrix = matrix2.div(matrix3);

        double[][] expected = {{1,1,3,2}, {5,3,7,0}, {4,9,0,0}};
        double[][] result = resultMatrix.asArray();

        for(int i = 0; i < matrix2.rows; i++){
            assertArrayEquals(expected[i], result[i], 0.001);
        }
    }

//    11. dot - testuje pewien pryzpadek mnozenia macierzy (obliczenia wykonane na kartce)
    @Test
    public void testDot(){
        Matrix matrix3 = new Matrix(new double[][] {{1,2,3}, {4,5,6}});
        Matrix matrix4 = new Matrix(new double[][] {{7,8}, {9,10}, {11,12}});
        Matrix resultMatrix = matrix3.dot(matrix4);

        double[][] expected = {{58, 64}, {139, 154}};
        double[][] result = resultMatrix.asArray();

        for(int i = 0; i < matrix3.rows; i++){
            assertArrayEquals(expected[i], result[i], 0.001);
        }
    }

//    11. frobenius - testuje czy
    @Test
    public void testEye(){

        Matrix resultMatrix = Matrix.eye(3);
        double expectedFrobenius = Math.sqrt(3);

        assertEquals(expectedFrobenius, resultMatrix.frobenius(), 0.001);

    }

}