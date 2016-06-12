package jose.tovar.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jose on 28/12/2015.
 */
public class ListConverter
{
    public static List<Integer> toList(int[] array)
    {
        List<Integer> result = new ArrayList<Integer>();
        for(int number : array)
        {
            result.add(number);
        }
        return result;
    }
}
