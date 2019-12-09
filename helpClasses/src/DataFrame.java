import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.*;

public class DataFrame {

    private ArrayList<ArrayList<Value>> data = new ArrayList<>();
    public String[] colNames;
    public String[] colTypes;
    private int height = 0;

    public DataFrame() {
        data = new ArrayList<>();
        height = 0;
    }

    public DataFrame (String[] colNamesInput, String[] colTypesInput) {
        this.colNames = colNamesInput;
        this.colTypes = colTypesInput;
        while (data.size() < colNames.length) {
            data.add(new ArrayList<>());
        }
    }

    /*
     * if header (colNames) is null, read colNames from first line of file
     */
    public DataFrame (String filename, String[] colTypes, boolean header) throws IOException {
        this.colTypes = colTypes;
        while (data.size() < colTypes.length) {
            data.add(new ArrayList<>());
        }

        FileInputStream fstream = new FileInputStream(filename);
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

        String strLine;
        String[] splitLine;
        if ((strLine = br.readLine()) != null) {
            if (header) {
                splitLine = strLine.trim().split(",");
                this.colNames = splitLine;
            } else {
                this.colNames = new String[colTypes.length];
                Arrays.fill(this.colNames, "default");
            }
        }
        while((strLine = br.readLine()) != null) {
            DataFrame row = new DataFrame(this.colNames, this.colTypes);
            splitLine = strLine.trim().split(",");
            for (int col = 0; col < splitLine.length; col ++) {
                assert this.colNames != null;
                switch (this.colTypes[col]) {
                    case "DOUBLE PRECISION":
                        row.set(this.colNames[col], 0, new MyDouble(splitLine[col]));
                        break;
                    case "float":
                        row.set(this.colNames[col], 0, new MyFloat(splitLine[col]));
                        break;
                    case "DATETIME":
                        row.set(this.colNames[col], 0, new MyDateTime(splitLine[col]));
                        break;
                    case "int":
                        row.set(this.colNames[col], 0, new MyInteger(splitLine[col]));
                        break;
                    default:
                        row.set(this.colNames[col], 0, new MyString(splitLine[col]));
                }
            }
            this.appendFrame(row);
        }
        fstream.close();
    }

    private void adjustHeights () {
        for (ArrayList<Value> column: this.getData()) {
            while (column.size() < this.height) {
                column.add(null);
            }
        }
    }

    public String[] getColNames() {
        return this.colNames;
    }

    public String[] getColTypes() { return this.colTypes; }

    public ArrayList<ArrayList<Value>> getData() { return this.data; }

    /*
     * REQUEST FIX: faulty, needs revision
     */
    public int getHeight() {
        if (this.data.size() > 0) {
            return this.height;
        } else { return 0; }
    }

    /*
     * outputs how many columns there are
     */
    public int getLen() {
        return this.colNames.length;
    }

    public ArrayList<Value> getColByIndex(int index) {
        return this.data.get(index);
    }

    public ArrayList<Value> getColByName(String colName) {
        int i = this.getColIndex(colName);
        return this.data.get(i);
    }

    /*
     * returns -1 if no matches
     */
    public int getColIndex (String colName) {
        for (int i = 0; i < this.getLen(); i ++) {
            if (this.colNames[i].equals(colName)) {
                return i;
            }
        }
        return -1;
    }

    public Value getCell (String colName, int rowNum) {
        return this.getColByName(colName).get(rowNum);
    }

    /*
     * make new DataFrame consisting of columns given in String [] cols
     */
    public DataFrame get (@NotNull String [] cols, boolean copy) {
        String[] newColTypes = new String[cols.length];
        int colIndex;
        for (int i = 0; i < cols.length; i++) {
            /*
             * for each of given column names get its index in OG DataFrame
             * and set its type to its OG type (new strings are always "deep copies" cause they're immutable)
             */
            colIndex = this.getColIndex(cols[i]);
            newColTypes[i] = this.colTypes[colIndex];
        }

        DataFrame copied = new DataFrame(cols, newColTypes);

        if (copy) {
            String colName;
            ArrayList<Value> newCol = new ArrayList<>();
            for (int i = 0; i < cols.length; i ++) {
                colName = copied.getColNames()[i];
                newCol.addAll(this.getColByName(colName));
                copied.setCol(colName, newCol);
            }
            return copied;
        } else {
            String colName;
            ArrayList<Value> newCol = new ArrayList<>();
            for (int i = 0; i < cols.length; i ++) {
                colName = copied.getColNames()[i];
                newCol.addAll(this.getColByName(colName));
                copied.setCol(colName, newCol);
            }
            return copied;
        }
    }

    public void set (String colName, int rowNum, Value obj) {
        /*
         * finds col by name
         * extends the column if rowNum is bigger than current col height
         * sets cell value
         * replaces old column with new one
         */
        ArrayList<Value> newCol = this.getColByName(colName);
        int colNum = this.getColIndex(colName);
        try {
            while (newCol.size() < rowNum + 1) {
                newCol.add(null);
            }
            newCol.set(rowNum, obj);
            if(newCol.size() > this.height) {
                this.height = newCol.size();
            }
        }
        catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        data.set(colNum, newCol);
    }

    public void setCol (String colName, ArrayList<Value> colData) {
        int colIndex = this.getColIndex(colName);
        if(colData.size() > this.height) {
            this.height = colData.size();
        }
        this.data.set(colIndex, colData);
    }

    /*
     * adjust second column len to match the first, e.x. firstCol.len == 4, secCol.len == 6, if types 1-4 are matching
     *      then only append cols 1-4 from secCol to firstCol
     *      e.x.2. firstCol.len == 6 secCol.len == 3, if types 1-3 match, append cols 1-3, append null to cols 4-6
     */
    public void appendFrame (DataFrame other) {
        int checkLen = Math.min(this.getLen(), other.getLen());
        if (this.matchesColTypes(other)) {
            this.height += other.getHeight();
            for (int i = 0; i < checkLen; i ++) {
                this.appendToCol(this.getColNames()[i], other.getColByIndex(i));
            }
        }
    }

    public void appendToCol (String colName, ArrayList<Value> otherCol) {
        ArrayList<Value> newCol = this.getColByName(colName);
        newCol.addAll(otherCol);
        if (newCol.size() > this.height) {
            this.height = newCol.size();
        }
        this.setCol(colName, newCol);
    }

    /*
     * adjust second column len to match the first, e.x. firstCol.len == 4, secCol.len == 6, if types 1-4 are matching
     *      then only append cols 1-4 from secCol to firstCol
     *      e.x.2. firstCol.len == 6 secCol.len == 3, if types 1-3 match, append cols 1-3, append null to cols 4-6
     */
    public boolean matchesColTypes (@NotNull DataFrame other) {
        boolean matching = true;
        int checkLen = Math.min(this.getLen(), other.getLen());
        for (int i = 0; i < checkLen; i ++) {
            if (!this.getColTypes()[i].equals(other.getColTypes()[i])) {
                matching = false;
                break;
            }
        }
        return matching;
    }

    public DataFrame getRow (int i) {
        DataFrame rowFrame = new DataFrame(this.colNames, this.colTypes);
        for (String name: this.colNames) {
            rowFrame.set(name, 0, this.getCell(name, i));
        }
        return rowFrame;
    }

    public DataFrame getRowsBetween (int from, int to) {
        DataFrame cutRows = new DataFrame(this.colNames, this.colTypes);
        ArrayList<Value> cutCol;
        for (String name: this.colNames) {
            cutCol = new ArrayList<>(this.getColByName(name).subList(from, to));
            cutRows.setCol(name, cutCol);
        }
        return cutRows;
    }

    public String[][] dfToString(DataFrame df) {
        // only returns the actual data, not colNames nor colTypes
        String[][] stringDataFrame = new String[df.getHeight()][df.getLen()];
        for (int i = 0; i < df.getHeight(); i ++) {
            String[] stringRow = new String[df.getLen()];
            for (int j = 0; j < df.getLen(); j ++) {
                if (df.getCell(df.getColNames()[j], i) != null) {
                    stringRow[j] = df.getCell(df.getColNames()[j], i).toString();
                } else {
                    stringRow[j] = "";
                }
            }
            stringDataFrame[i] = stringRow;
        }
        return stringDataFrame;
    }

    public void print() {
        String[][] sdf = dfToString(this);
        for (String name: colNames) {
            System.out.print(name + " ");
        }
        System.out.print("\n");
        for (String[] row: sdf) {
            for (String elem: row) {
                System.out.print(elem + " ");
            }
            System.out.print("\n");
        }
    }

    /*
     * returns linked list of specified columns
     */
    public LinkedList<DataFrame> groupBy(String[] keys) {
        LinkedList<DataFrame> groupedList = new LinkedList<>();
        LinkedList<DataFrame> groupedListCopy;
        groupedList.add(this);
        for (String key: keys) {
            groupedListCopy = (LinkedList<DataFrame>) groupedList.clone();
            groupedList.clear();
            for(DataFrame df: groupedListCopy) {
                Map<String, DataFrame> groupMap = new TreeMap<>();
                for (int i = 0; i < df.height; i ++) {
                    DataFrame row = df.getRow(i);
                    Value rowKeyValue = row.getCell(key, 0);
                    String rowKey = rowKeyValue.toString();

                    // check if key is already in the map
                    //      if it is, append row to key's DataFrame
                    // if key has no value in current row, append row to NoKey group
                    if (groupMap.containsKey(rowKey)) {
                        groupMap.get(rowKey).appendFrame(row);
                    } else if (rowKeyValue == null) {
                        if (groupMap.get("NoKey") == null) {
                            groupMap.put("NoKey", row);
                        } else {
                            groupMap.get("NoKey").appendFrame(row);
                        }
                    } else if (!groupMap.containsKey(rowKey)) {
                        groupMap.put(rowKey, row);
                    }
                }
                // after regrouping DataFrame into Map values, move them to a LinkedList
                for (Map.Entry<String, DataFrame> dfFromMap: groupMap.entrySet()) {
                    groupedList.add(dfFromMap.getValue());
                }
            }
        }
        return groupedList;
    }



    public static void main(String[] argv) throws SQLException {
        try {
            DataFrame df = new DataFrame("groupby.csv", new String[]{"VARCHAR", "DATETIME", "float", "float"}, true);
            LinkedList<DataFrame> groups = df.groupBy(new String[]{"id", "date"});
            GroupedDataFrame gdf = new GroupedDataFrame(groups);
            DataFrame maxDf = gdf.threadedMax();
            maxDf.print();
            System.out.println("Success!");
            maxDf = gdf.max();
            maxDf.print();
        } catch (InterruptedException ie) {
            System.out.println("Threading failed! " + ie.getMessage());
        } catch (IOException ioe) {
            System.out.println("Error encountered when reading file" + ioe.getMessage());
            System.exit(-1);
        }
    }
}
