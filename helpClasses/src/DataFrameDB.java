import java.sql.*;

public class DataFrameDB extends DataFrame {
    private Connection conn = null;
    private Statement stmt = null;
    private ResultSet rs = null;

    public DataFrameDB (String[] colNamesInput, String[] colTypesInput) throws SQLException {
        super(colNamesInput, colTypesInput);
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:testing.db");
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        stmt = conn.createStatement();
        stmt.executeUpdate(createTable());
        stmt.executeUpdate(insertValues());

        rs = stmt.executeQuery("SELECT * FROM dataFrame;");
        System.out.print(rs);
    }

    public void dfToTable() {
        String createQuery = createTable();
        String insertQuery = insertValues();

    }

    public String createTable() {
        StringBuilder createTable = new StringBuilder("CREATE TABLE IF NOT EXISTS dataFrame (");
        for (int i = 0; i < this.getLen(); i ++) {
            createTable.append(colNames[i]).append(" ").append(colTypes[i]).append(" NOT NULL,\n");
        }
        createTable.delete(createTable.lastIndexOf(","), createTable.lastIndexOf("\n")+1);
        return createTable.toString();

    }

    public String insertValues() {
        StringBuilder insertValues = new StringBuilder("INSERT INTO dataFrame VALUES ");
        for (int i = 0; i < this.getHeight(); i ++) {
            StringBuilder row = new StringBuilder("(");
            for (String name: colNames) {
                row.append(this.getCell(name, i).toString()).append(",");
            }
            row.deleteCharAt(row.lastIndexOf(","));
            row.append("),\n");
            insertValues.append(row);
        }
        insertValues.delete(insertValues.lastIndexOf(","), insertValues.lastIndexOf("\n")+1);
        return insertValues.toString();
    }

    public static void main(String[] args) throws SQLException {
        DataFrameDB dfdb = new DataFrameDB(new String[]{"first", "second", "third"}, new String[]{"VARCHAR(64)", "INT", "VARCHAR(64)"});
        dfdb.set("first", 3, new MyString("ThirdRowString"));
    }



}
