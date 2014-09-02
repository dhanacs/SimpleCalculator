// Contains the methods for some basic calculator operations.

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculatorAPI
{
    private static String error = "Error";
    public static int LIMIT = 15;

    public String add(String operand1, String operand2)
    {
        BigDecimal a = new BigDecimal(operand1);
        BigDecimal b = new BigDecimal(operand2);
        BigDecimal c = a.add(b).setScale(2, BigDecimal.ROUND_UP);

        if(c.toString().length() > LIMIT) return error;
        else return c.toString();
    }

    public String subtract(String operand1, String operand2)
    {
        BigDecimal a = new BigDecimal(operand1);
        BigDecimal b = new BigDecimal(operand2);
        BigDecimal c = a.subtract(b).setScale(2, BigDecimal.ROUND_UP);

        if(c.toString().length() > LIMIT) return error;
        else return c.toString();
    }

    public String multiply(String operand1, String operand2)
    {
        BigDecimal a = new BigDecimal(operand1);
        BigDecimal b = new BigDecimal(operand2);
        BigDecimal c = a.multiply(b).setScale(2, BigDecimal.ROUND_UP);

        if(c.toString().length() > LIMIT) return error;
        else return c.toString();
    }

    public String divide(String operand1, String operand2)
    {
        try
        {
            BigDecimal a = new BigDecimal(operand1);
            BigDecimal b = new BigDecimal(operand2);
            BigDecimal c = a.divide(b, 2, BigDecimal.ROUND_UP);

            if(c.toString().length() > LIMIT) return error;
            else return c.toString();
        }
        catch(Exception e)
        {
            return error;
        }
    }
}
