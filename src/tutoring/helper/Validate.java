/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutoring.helper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 *
 * @author team Ubuntu
 */
public class Validate
{

    /**
     * Validate timestamp
     *
     * @param t - timestamp to validate
     * @return whether was a valid timestamp
     */
    public static boolean validateTimestamp(String t)
    {
        try
        {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm aa", Locale.ENGLISH);

            String[] split;
            sdf.setLenient(false);
            Date d = sdf.parse(t.trim());

            split = t.trim().split("[/ :]");

            if (split[2].length() != 4)
            {
                throw new Exception();
            }

            int month = Integer.parseInt(split[0]);
            int day = Integer.parseInt(split[1]);
            int year = Integer.parseInt(split[2]);

            int hour = Integer.parseInt(split[3]);
            int min = Integer.parseInt(split[4]);

            String ampm = split[5];

            if (month <= 0 || month > 12)
            {
                throw new Exception();
            }
            if (day < 1 || day > 31)
            {
                throw new Exception();
            }
            if (year < 1000 || year > 9999)
            {
                throw new Exception();
            }
            if (hour < 1 || hour > 12)
            {
                throw new Exception();
            }
            if (min < 0 || min > 60)
            {
                throw new Exception();
            }
            if (!ampm.equalsIgnoreCase("am") && !ampm.equalsIgnoreCase("pm"))
            {
                throw new Exception();
            }
        } catch (Exception e)
        {

            return false;
        }
        return true;
    }
}
