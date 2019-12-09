import java.time.LocalDate;
import java.util.Objects;

public class MyDateTime extends Value implements Cloneable {
    public LocalDate val;

    public final String type = "DATETIME";

    public MyDateTime(LocalDate valInput) { val = valInput; }

    public MyDateTime(String valInput) {
        val = LocalDate.parse(valInput);
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return val.toString();
    }

    @Override
    public Value add(Value other) {
        MyDateTime casted = (MyDateTime) other;
        val = val.plusYears(casted.val.getYear());
        val = val.plusMonths(casted.val.getYear());
        val = val.plusDays(casted.val.getDayOfMonth());
        return this;
    }

    @Override
    public Value sub(Value other) {
        MyDateTime casted = (MyDateTime) other;
        val = val.minusYears(casted.val.getYear());
        val = val.minusMonths(casted.val.getYear());
        val = val.minusDays(casted.val.getDayOfMonth());
        return this;
    }

    @Override
    public Value mul(Value other) {
        return this;
    }

    @Override
    public Value div(Value other) {
        return this;
    }

    @Override
    public Value pow(Value other) {
        return this;
    }

    @Override
    public boolean eq(Value other) {
        return val.compareTo(((MyDateTime) other).val) == 0;
    }

    @Override
    public boolean lte(Value other) {
        return val.compareTo(((MyDateTime) other).val) <= 0;
    }

    @Override
    public boolean gte(Value other) {
        return val.compareTo(((MyDateTime) other).val) >= 0;
    }

    @Override
    public boolean neq(Value other) {
        return val.compareTo(((MyDateTime) other).val) != 0;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        MyDateTime MyDateTime = (MyDateTime) other;
        return val == MyDateTime.val;
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
        return new MyDateTime(LocalDate.parse(s));
    }
}
