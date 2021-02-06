package first_task.entities;

public class Interval {
    private Boundary low;
    private Boundary upper;

    public Interval(Boundary low, Boundary upper) {
        this.low = low;
        this.upper = upper;
    }

    public boolean checkNumber(Double number) {
        return low.checkNumber(number) && upper.checkNumber(number);
    }
}
