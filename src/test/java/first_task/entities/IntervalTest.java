package first_task.entities;

import org.junit.Test;

import static org.junit.Assert.*;

public class IntervalTest {
    @Test
    public void checkNumber() {
        Interval firstInterval = new Interval(
                new Boundary(1500d,Sign.GREATER_AND_EQUAL),
                new Boundary(20000d, Sign.LOWER)
        );

        assertTrue(firstInterval.checkNumber(5000d));
        assertTrue(firstInterval.checkNumber(1500d));
        assertFalse(firstInterval.checkNumber(20000d));
        assertFalse(firstInterval.checkNumber(0d));
        assertFalse(firstInterval.checkNumber(Double.MAX_VALUE));

        Interval secondInterval = new Interval(
                new Boundary(20d,Sign.GREATER),
                new Boundary(30d, Sign.LOWER_AND_EQUAL)
        );

        assertTrue(secondInterval.checkNumber(27d));
        assertTrue(secondInterval.checkNumber(30d));
        assertFalse(secondInterval.checkNumber(20d));
        assertFalse(secondInterval.checkNumber(Double.MIN_VALUE));
        assertFalse(secondInterval.checkNumber(Double.MAX_VALUE));

    }
}