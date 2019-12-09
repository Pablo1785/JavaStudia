import org.jetbrains.annotations.Contract;

public abstract class Value {
    public abstract String getType();

    public abstract String toString();

    public abstract Value add(Value other);

    public abstract Value sub(Value other);

    public abstract Value mul(Value other);

    public abstract Value div(Value other);

    public abstract Value pow(Value other);

    public abstract boolean eq(Value other);

    public abstract boolean lte(Value other);

    public abstract boolean gte(Value other);

    public abstract boolean neq(Value other);

    @Contract(value = "null -> false", pure = true)
    public abstract boolean equals(Object other);

    public abstract int hashCode();

    public abstract Value create(String s);
}
