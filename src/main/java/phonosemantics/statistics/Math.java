package phonosemantics.statistics;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Math {

    public static double round(double value, int scale) {
        if (scale <= 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(scale, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}
