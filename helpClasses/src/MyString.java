public class MyString extends Value implements Cloneable {
    public String val;

    private final String type = "String";

    public String getValue() {
        return val;
    }

    public void setValue(String value) {
        this.val = value;
    }

    @Override
    public String getType() {
        return null;
    }

    public MyString(String[] arrInput) {
        for (String val : arrInput) {

        }
    }

    public MyString(String valInput) { val = valInput; }

    @Override
    public String toString() {
        return val;
    }

    @Override
    public Value add(Value other) {
        return new MyString(this.val + other.toString());
    }

    @Override
    public Value sub(Value other) {
        return null;
    }

    @Override
    public Value mul(Value other) {
        return null;
    }

    @Override
    public Value div(Value other) {
        return null;
    }

    @Override
    public Value pow(Value other) {
        return null;
    }

    @Override
    public boolean eq(Value other) {
        return false;
    }

    @Override
    public boolean lte(Value other) {
        return false;
    }

    @Override
    public boolean gte(Value other) {
        return false;
    }

    @Override
    public boolean neq(Value other) {
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return false;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public Value create(String s) {
        return null;
    }
}
