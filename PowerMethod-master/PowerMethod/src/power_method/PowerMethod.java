/**
 * 
 */
package power_method;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.zip.ZipEntry;

/**
 * reads in an input file and
 * 
 * @author Marshall Skelton
 *
 */
public class PowerMethod {
    static double epsilon = .000001;
    static int ITMAX;

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            System.out.println("File path of input: ");
            Scanner input = new Scanner(System.in);
            Scanner file = new Scanner(new File(input.next()));
            System.out.println("want to set itmax(y/n): ");
            String response = input.next();
            if (response.equals("y")) {
                System.out.println("What value would you like to set it to? ");
                ITMAX = input.nextInt();
            }
            long startTime = System.currentTimeMillis();
            // the size of the matrix
            int squareSize = file.nextInt();
            // the number of nonzero elements
            int nonZero = file.nextInt();
            if (response.equals("n")) {
                ITMAX = nonZero * nonZero;
            }
            // creates an array object that holds placement of the data
            // excluding 0 elements
            List[] array = new List[squareSize + 1];
            // insert the elements in order of row and then columns
            for (int i = nonZero; i > 0; i--) {
                double val = file.nextDouble();
                int column = file.nextInt();
                int row = file.nextInt();
                file.nextLine();
                Sparse obj = new Sparse(column, val);
                if (array[row] == null) {
                    array[row] = new List();
                }
                array[row].add(obj);
            }
            long endTime = System.currentTimeMillis();
            double[] initvect = createInitvect(squareSize + 1);
            rightMultiply(array, initvect);
            System.out.println();
            System.out.println("time it took to read in file (in seconds): " + (endTime - startTime) / 1000);
            file.close();
            input.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static String toString(List[] array) {
        String str = "";
        for (int i = 1; i < array.length; i++) {
            if (array[i] == null) {
                str += "position: " + i + " NULL";
            } else {
                str += "position: " + i + "\n" + array[i].toString();
            }
        }
        return str;
    }

    public static void rightMultiply(List[] list, double[] initVect) {
        long startRight = System.currentTimeMillis();
        int iteration = 1;
        double approximation = 1;
        double[] z = null;
        while (approximation >= epsilon && iteration <= ITMAX) {
            System.out.println("iteration: " + iteration);
            z = new double[list.length - 1];
            double total = 0;
            for (int i = 1; i < list.length; i++) {
                List obj = list[i];
                for (int j = 1; j < obj.length; j++) {
                    int col = obj.getRow()[j].getColumn();
                    z[i - 1] += initVect[col] * obj.getRow()[j].getValue();
                }
                total += z[i - 1];
            }
            z = normalizeZ(z, total);
            approximation = 0;
            for (int i = 1; i < initVect.length; i++) {
                double subtract = initVect[i] - z[i - 1];
                approximation += Math.abs(subtract);
                initVect[i] = z[i-1];
            }
            System.out.println("Approximation: " + approximation);
            iteration++;
        }
        long endRight = System.currentTimeMillis();
        System.out.println();
        System.out.println("time it took for right hand approximations(time in seconds): " + (endRight - startRight) / 1000);
        System.out.println("final eigen vector: " + Arrays.toString(z));
    }
    public static double[] createInitvect(int size) {
        double[] array = new double[size];
        double total = 0;
        for (int i = 1; i < size; i++) {
            if (i % 2 == 0) {
                array[i] = 0;
            } else {
                array[i] = 1;
                total += 1.0;
            }
        }
        return normalizeInit(array, total);
    }

    public static double[] normalizeInit(double[] vector, double total) {
        for (int i = 1; i < vector.length; i++) {
            vector[i] = vector[i] / total;
        }
        return vector;
    }

    public static double[] normalizeZ(double[] vector, double total) {
        for (int i = 0; i < vector.length; i++) {
            vector[i] = vector[i] / total;
        }
        return vector;
    }

}

