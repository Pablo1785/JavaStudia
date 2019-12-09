import java.util.ArrayList;
import java.util.Arrays;

public class SparseDataFrame extends DataFrame {
    private Value hide;
    private ArrayList<ArrayList<CooValue>> sparseFrame = new ArrayList<>();

    public SparseDataFrame (String[] colNamesInput, String[] colTypesInput, Value hideInput) {
        super(colNamesInput, colTypesInput);
        hide = hideInput;
        while (sparseFrame.size() < this.getLen()) {
            sparseFrame.add(new ArrayList<>());
        }
    }

    private void sparseCol (int index) {
        ArrayList<Value> col = this.getColByIndex(index);
        ArrayList<CooValue> cooCol = new ArrayList<>();
        for (int j = 0; j < col.size(); j ++) {
            if (col.get(j) != hide) {
                cooCol.add(new CooValue(j, col.get(j)));
            }
        }
        sparseFrame.set(index, cooCol);
    }

    private void sparseSelf () {
        for (int i = 0; i < this.getLen(); i ++) {
            this.sparseCol(i);
        }
    }

    public DataFrame toDense() {
        return this.get(this.getColNames(), true);
    }

    /*
     * ATTRIBUTE GETTERS AND SETTERS
     */

    public int getLen() { return super.getLen(); }

    public String[] getColNames() { return super.getColNames(); }

    public ArrayList<Value> getColByIndex (int index) { return super.getColByIndex(index); }

    public ArrayList<Value> getColByName (String colName) { return super.getColByName(colName); }

    public int getColIndex (String colName) { return super.getColIndex(colName); }

    public ArrayList<?> getSparseFrame () {
        return this.sparseFrame;
    }

    public CooValue getCooValue(int index, String colName) { return sparseFrame.get(getColIndex(colName)).get(index); }

    public void setCol (String colName, ArrayList<Value> colData) {
        super.setCol(colName, colData);
        this.sparseCol(getColIndex(colName));
    }

    public void set (String colName, int rowNum, Value obj) {
        super.set(colName, rowNum, obj);
        this.sparseCol(getColIndex(colName));
    }

    public void setHide (Value newHide) {
        hide = newHide;
    }

    public static void main(String[] args) {
        SparseDataFrame sf = new SparseDataFrame(new String[]{"col1", "col2"}, new String[]{"int", "int"}, new MyInteger(0));
        sf.set("col1", 5, new MyInteger(1));
        System.out.print(sf.getCooValue(5, "col1").toString());
    }
}
