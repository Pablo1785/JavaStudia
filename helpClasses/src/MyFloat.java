import java.util.Objects;

public class MyFloat extends Value implements Cloneable {
    public float val;

    public final String type = "float";

    public MyFloat(float valInput) { val = valInput; }

    public MyFloat(String valInput) {
        val = Float.parseFloat(valInput);
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return Float.toString(val);
    }

    @Override
    public Value add(Value other) {
        MyFloat casted = (MyFloat) other;
        val += casted.val;
        return this;
    }

    @Override
    public Value sub(Value other) {
        MyFloat casted = (MyFloat) other;
        val -= casted.val;
        return this;
    }

    @Override
    public Value mul(Value other) {
        MyFloat casted = (MyFloat) other;
        val *= casted.val;
        return this;
    }

    @Override
    public Value div(Value other) {
        MyFloat casted = (MyFloat) other;
        val /= casted.val;
        return this;
    }

    @Override
    public Value pow(Value other) {
        return this;
    }

    @Override
    public boolean eq(Value other) {
        return val == ((MyFloat) other).val;
    }

    @Override
    public boolean lte(Value other) {
        return val <= ((MyFloat) other).val;
    }

    @Override
    public boolean gte(Value other) {
        return val >= ((MyFloat) other).val;
    }

    @Override
    public boolean neq(Value other) {
        return val != ((MyFloat) other).val;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        MyFloat myFloat = (MyFloat) other;
        return val == myFloat.val;
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
        return new MyFloat(Float.parseFloat(s));
    }
}
