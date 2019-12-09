import java.util.LinkedList;

public interface Groupby {
    DataFrame max();

    DataFrame min();

    DataFrame mean();

    DataFrame std();

    DataFrame sum();

    DataFrame var();

    DataFrame apply();
}
