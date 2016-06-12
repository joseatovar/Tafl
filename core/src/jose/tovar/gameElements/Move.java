package jose.tovar.gameElements;

import jose.tovar.utils.Position;

/**
 * Created by Jose on 26/12/2015.
 */
public class Move
{
    private Warrior warrior;
    private Position initialPosition;
    private Position finalPosition;

    public Move()
    {
        clearMove();
    }

    public Move(Warrior warrior, Position initialPosition)
    {
        this.warrior = warrior;
        this.initialPosition = initialPosition;
    }

    public Move(Position init, Position end)
    {
        initialPosition = init;
        finalPosition = end;
    }

    public Position getInitialPosition()
    {
        return initialPosition;
    }

    public Warrior getWarrior()
    {
        return warrior;
    }

    public void setInitialPosition(Position position)
    {
        this.initialPosition = position;
    }

    public void setWarrior(Warrior warrior)
    {
        this.warrior = warrior;
    }

    public Position getFinalPosition()
    {
        return finalPosition;
    }

    public void setFinalPosition(Position finalPosition)
    {
        this.finalPosition = finalPosition;
    }

    public boolean isCompleteMove()
    {
        return initialPosition!=null && finalPosition!=null;
    }

    public boolean isEmptyMove()
    {
        return initialPosition==null && finalPosition==null;
    }

    public boolean isNotCompleteMove()
    {
        return initialPosition!=null && finalPosition==null;
    }

    public boolean isVerticalMove()
    {
        return initialPosition.getColumn() == finalPosition.getColumn();
    }

    public boolean isHorizontalMove()
    {
        return initialPosition.getRow() == finalPosition.getRow();
    }

    public boolean isTowerMove()
    {
        return isHorizontalMove() || isVerticalMove();
    }

    public  void clearMove()
    {
        initialPosition = null;
        finalPosition = null;
    }

    public boolean isBoardPosition(int boardSize)
    {
        return initialPosition.isCorrectPosition(boardSize) && finalPosition.isCorrectPosition(boardSize);
    }

    public boolean areDifferentPositions()
    {
        return initialPosition.getRow() != finalPosition.getRow()
                || initialPosition.getColumn() != finalPosition.getColumn();
    }
}
