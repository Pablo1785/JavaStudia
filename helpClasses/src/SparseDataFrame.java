import java.util.ArrayList;

public class SparseDataFrame extends DataFrame {
    private Object hide;
    private ArrayList<ArrayList<Object>> sparseFrame;

    public SparseDataFrame (String[] colNamesInput, String[] colTypesInput, Object hideInput) {
        super(colNamesInput, colTypesInput);
        hide = hideInput;
        while (sparseFrame.size() < this.getLen()) {
            sparseFrame.add(new ArrayList<Object>());
        }
    }

    private void sparseCol (int index) {
        ArrayList<Object> col = this.getColByIndex(index);
        ArrayList<Object> cooCol = new ArrayList<>();
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

    public void setHide (Object newHide) {
        hide = newHide;
    }

    public void set (String colName, int rowNum, Object obj) {
        super.set(colName, rowNum, obj);
        this.sparseCol(getColNames().indexOf(obj));
    }



}
