import java.util.Objects;

public class MyInteger extends Value implements Cloneable {
    public int val;

    private final String type = "int";

    public MyInteger(int valInput) {
        val = valInput;
    }

    public MyInteger(String valInput) {
        val = Integer.parseInt(valInput);
    }

    public String getType() { return this.type; }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return Integer.toString(val);
    }

    @Override
    public Value add(Value other) {
        MyInteger casted = (MyInteger) other;
        this.val += casted.val;
        return this;
    }

    @Override
    public Value sub(Value other) {
        MyInteger casted = (MyInteger) other;
        this.val -= casted.val;
        return this;
    }

    @Override
    public Value mul(Value other){
        MyInteger casted = (MyInteger) other;
        this.val *= casted.val;
        return this;
    }

    @Override
    public Value div(Value other) {
        MyInteger casted = (MyInteger) other;
        this.val /= casted.val;
        return this;
    }

    @Override
    public Value pow(Value other) {
        MyInteger casted = (MyInteger) other;
        this.val ^= casted.val;
        return this;
    }

    @Override
    public boolean eq(Value other) {
        return this.val == ((MyInteger) other).val;
    }

    @Override
    public boolean lte(Value other) {
        return this.val <= ((MyInteger) other).val;
    }

    @Override
    public boolean gte(Value other) {
        return this.val >= ((MyInteger) other).val;
    }

    @Override
    public boolean neq(Value other) {
        return this.val != ((MyInteger) other).val;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyInteger myInteger = (MyInteger) o;
        return val == myInteger.val;
    }

    @Override
    public int hashCode() {
        return Objects.hash(val);
    }

    @Override
    public MyInteger create(String s) {
        return new MyInteger(Integer.parseInt(s));
    }

    public static void main(String[] args) {
        MyInteger mint = new MyInteger(5);
        MyInteger mint2 = new MyInteger(7);
        System.out.println(mint.toString() + " - it just works! - " + mint2.toString());
    }
}
