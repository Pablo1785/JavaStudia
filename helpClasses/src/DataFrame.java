import java.util.ArrayList;
import java.util.Arrays;

public class DataFrame {

    private ArrayList<ArrayList<Object>> data = new ArrayList<>();
    private String[] colNames;
    private String[] colTypes;
    private int height = 0;

    public DataFrame (String[] colNamesInput, String[] colTypesInput) {
        this.colNames = colNamesInput;
        this.colTypes = colTypesInput;
        while (data.size() < colNames.length) {
            data.add(new ArrayList<>());
        }
    }

    public String[] getColNames() {
        return this.colNames;
    }

    public ArrayList<Object> getColByIndex(int index) {
        return this.data.get(index);
    }

    public int getLen() {
        /*
         * outputs how many columns there are
         */
        return this.data.size();
    }

    public void set (String colName, int rowNum, Object obj) {
        ArrayList<Object> newCol = this.get(colName);
        int colNum = -1;
        for (int i = 0; i < colNames.length; i++) {
            if (colNames[i].equals(colName)){ colNum = i; }
        }
        while (newCol.size() < rowNum) {
            newCol.add(new Object());
            height ++;
        }
        newCol.add(obj);
        height ++;

        data.set(colNum, newCol);
    }

    public void setRow(int rowNum, Object[] rowData) {

    }

    public ArrayList<Object> get(String colName) {
        for (int i = 0; i < this.colNames.length; i++) {
            if (colNames[i].equals(colName)) {
                return this.data.get(i);
            }
        }
        return null;
    }
    public int getHeight() {
        if (this.data.size() > 0) {
            return this.height;
        } else { return 0; }
    }
    public DataFrame get (String [] cols, boolean copy) {
        if (copy) {
            String[] newColTypes = new String[cols.length];
            for (int i = 0; i < cols.length; i++) {
                for (int j = 0; j < this.colNames.length; j++) {
                    if (cols[i].equals(this.colNames[j])) {
                        newColTypes[i] = this.colTypes[j];
                    }
                }
            }
            return new DataFrame(cols, newColTypes);
        } else { return this; }
    }

    public DataFrame iloc(int i) {
        DataFrame newFrame = new DataFrame(this.colNames, this.colTypes);

        return newFrame;
    }

    public static void main(String[] argv) {
        DataFrame df = new DataFrame(new String[]{"col1", "col2", "col3"}, new String[]{"type1", "type2", "type3"});
        df.set("col2", 13, "word");
        System.out.println(Arrays.toString(df.get("col2").toArray()));
        System.out.print(df.height);
    }
}
