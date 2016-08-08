package jose.tovar.utils;

import java.util.ArrayList;
import java.util.List;

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

    public Position positionUp()
    {
        return new Position(row, column - 1);
    }

    public Position positionDown()
    {
        return new Position(row, column + 1);
    }

    public Position positionRight()
    {
        return new Position(row + 1, column);
    }

    public Position positionLeft()
    {
        return new Position(row - 1, column);
    }

    public List<Position> getPositionAround()
    {
        List<Position> positionList = new ArrayList<Position>();
        positionList.add(positionUp());
        positionList.add(positionDown());
        positionList.add(positionLeft());
        positionList.add(positionRight());
        return positionList;
    }

    @Override
    public boolean equals(Object obj)
    {
        if(obj instanceof Position)
        {
            Position position = (Position) obj;
            return row == position.row && column == position.column;
        }
        else
        {
            return false;
        }
    }

}
