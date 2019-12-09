import org.jetbrains.annotations.Contract;

public class CooValue extends Value implements Cloneable {
    private final Value value;

    private final int index;

    private String type;

    public CooValue (int indexInput, Value valInput) {
        value = valInput;
        index = indexInput;
        try {
            type = value.getType();
        }
        catch (NullPointerException e) {
            type = "CooValue";
        }
    }

    public final Value getVal() {
        return value;
    }

    public final int getIndex() { return index; }

    @Override
    public String getType() { return type; }

    @Override
    public String toString() {
        return index + ", " + value.toString();
    }

    @Override
    public Value add(Value other) {
        return null;
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
