public class CooValue {
    private final Object[] value;
    public CooValue (int index, Object obj) {
        value = new Object[]{index, obj};
    }

    public final Object[] getVal() {
        return value;
    }
}
