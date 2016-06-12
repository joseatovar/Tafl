package jose.tovar.utils;

/**
 * Created by Jose on 26/12/2015.
 */
public class Position
{
    private int row;
    private int column;

    public int getColumn()
    {
        return column;
    }

    public int getRow()
    {
        return row;
    }

    public Position(int row, int column)
    {
        this.row = row;
        this.column = column;
    }

    public void setColumn(int column)
    {
        this.column = column;
    }

    public void setRow(int row)
    {
        this.row = row;
    }

    public boolean isCorrectPosition(int boardSize)
    {
        return row >= 0 && row < boardSize
                && column >= 0 && column < boardSize;

    }
}
