import java.util.Objects;

public class MyDouble extends Value implements Cloneable {
    public double val;

    public final String type = "DOUBLE PRECISION";

    public MyDouble(double valInput) { val = valInput; }

    public MyDouble(String valInput) {
        val = Double.parseDouble(valInput);
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return Double.toString(val);
    }

    @Override
    public Value add(Value other) {
        MyDouble casted = (MyDouble) other;
        val += casted.val;
        return this;
    }

    @Override
    public Value sub(Value other) {
        MyDouble casted = (MyDouble) other;
        val -= casted.val;
        return this;
    }

    @Override
    public Value mul(Value other) {
        MyDouble casted = (MyDouble) other;
        val *= casted.val;
        return this;
    }

    @Override
    public Value div(Value other) {
        MyDouble casted = (MyDouble) other;
        val /= casted.val;
        return this;
    }

    @Override
    public Value pow(Value other) {
        return this;
    }

    @Override
    public boolean eq(Value other) {
        return val == ((MyDouble) other).val;
    }

    @Override
    public boolean lte(Value other) {
        return val <= ((MyDouble) other).val;
    }

    @Override
    public boolean gte(Value other) {
        return val >= ((MyDouble) other).val;
    }

    @Override
    public boolean neq(Value other) {
        return val != ((MyDouble) other).val;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        MyDouble MyDouble = (MyDouble) other;
        return val == MyDouble.val;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public int hashCode() {
        return Objects.hash(val);
    }

    @Override
    public Value create(String s) {
        return new MyDouble(Double.parseDouble(s));
    }
}
