/**
 * 
 */
package power_method;

/**
 * The Sparse objects hold the column place and the value of the double stored there
 * @author maskelto
 *
 */
public class Sparse {
    private int column;
    private double value;

    public Sparse(int column, double value) {
        this.column = column;
        this.value = value;
    }

    public int getColumn() {
        return column;
    }

    public double getValue() {
        return value;
    }

    public String toString() {
        return "Sparse [column=" + column + ", value=" + value + "]\n";
    }

}
