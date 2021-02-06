package first_task.entities;

public class Boundary {
    private final Double value;
    private final Sign sign;

    public Boundary(Double value, Sign sign) {
        this.value = value;
        this.sign = sign;
    }

    public Double getValue() {
        return value;
    }

    public Sign getSign() {
        return sign;
    }

    public boolean checkNumber(Double number) {
        int resultOfComparison = this.value.compareTo(number);
        switch (sign) {
            case EQUAL:
                return resultOfComparison == 0;
            case LOWER:
                return resultOfComparison > 0;
            case LOWER_AND_EQUAL:
                return resultOfComparison > 0 || resultOfComparison == 0;
            case GREATER:
                return resultOfComparison < 0;
            case GREATER_AND_EQUAL:
                return resultOfComparison < 0 || resultOfComparison == 0;
            default:
                throw new RuntimeException("The wrong sign!");
        }
    }
}
