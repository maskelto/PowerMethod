package power_method;
/**
 * List object created as a re-sizable array to hold the Sparse objects
 * in each given row
 * @author maskelto
 *
 */
public class List {
    public Sparse[] row;
    public int size;
    public int length;

    public List() {
        this.size = 10;
        length = 1;
        row = new Sparse[size];
    }

    public Sparse[] getRow() {
        return row;
    }

    public void setRow(Sparse[] row) {
        this.row = row;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    /**
     * adds in order of column value ignoring
     * 
     * @param column
     */
    public void add(Sparse s) {
        length++;
        ensureCapacity(length);
        Sparse temp = null;
        int i;
        for (i = 1; i < length; i++) {
            if (row[i] == null) {
                row[i] = s;
                return;
            }
            if (s.getColumn() < row[i].getColumn()) {
                temp = row[i];
                row[i] = s;
                break;
            }
        }
        if (temp == null) {
            row[length] = s;
        }
        while (i < length && temp != null) {
            Sparse current = row[i + 1];
            row[i + 1] = temp;
            temp = current;
            i++;
        }
    }

    private void ensureCapacity(int capacity) {
        if (capacity >= size) {
            size *= 2;
            Sparse[] c = new Sparse[size];
            for (int i = 1; i < size; i++) {
                c[i] = row[i];
            }
            row = c;
        }
    }

    @Override
    public String toString() {
        String str = "";
        int i = 1;
        while (i < row.length && row[i] != null) {
            str += row[i].toString() + "\n";
            i++;
        }
        return str;
    }

}

