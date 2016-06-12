package jose.tovar.utils;

/**
 * Created by Jose on 26/12/2015.
 */
public enum Color
{
    WHITE, BLACK;

    private static Color[] vals = values();

    public Color nextColor()
    {
        return vals[(this.ordinal()+1) % vals.length];
    }
}
