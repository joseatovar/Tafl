package jose.tovar.gameElements;

import jose.tovar.utils.Color;
import jose.tovar.utils.ListConverter;
import jose.tovar.utils.Position;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Jose on 26/12/2015.
 */
public class Board
{
    private int size;
    private Warrior[][] squares;

    public Board(int size,int[] whiteWarriors, int[] blackWarriors)
    {
        this.size = size;
        squares = new Warrior[size][size];
        createLegions(whiteWarriors,blackWarriors);
    }

    public int getSize()
    {
        return size;
    }

    public Warrior getPosition(int x, int y)
    {
        Warrior warrior = null;
        if(x>=0 && x<size && y>=0 && y<size)
        {
            warrior = squares[x][y];
        }
        return warrior;
    }

    public Position getKingPosition()
    {
        for(int i=0; i<size; i++)
        {
            for(int j=0; j<size; j++)
            {
                Warrior warrior = getPosition(i,j);
                if(warrior instanceof King)
                {
                    return new Position(i,j);
                }
            }
        }
        return null;
    }

    public boolean isCorner(Position position)
    {
        int row = position.getRow();
        int column = position.getColumn();
        return (row==0 || row == size-1)  && (column==0 || column == size-1);
    }

    public boolean isCenter(Position position)
    {
        int center = size / 2;
        return position.getRow()==center && position.getColumn()==center;
    }

    public boolean isKingMove(Move move)
    {
        return getPosition(move.getInitialPosition()) instanceof King &&
                (isCorner(move.getFinalPosition()) ||
                isCenter(move.getFinalPosition()));
    }

    public boolean isWarriorMove(Move move)
    {
        return !isCorner(move.getFinalPosition()) &&
                !isCenter(move.getFinalPosition());
    }

    public boolean whiteWarriorWinGame()
    {
        return isCorner(getKingPosition());
    }

    public boolean blackWarriorWinGame()
    {
        Position kingPosition = getKingPosition();
        List<Position> positionList = kingPosition.getPositionAround();
        return iskingSurrounded(positionList);
    }

    public Warrior getPosition(Position position)
    {
        return getPosition(position.getRow(),position.getColumn());
    }

    private void createLegions(int[] white, int[] black)
    {
        /**
         * put king in the board
         */
        int kingPosition = size / 2;
        squares[kingPosition][kingPosition] = new King();
        /**
         * put legions in the board
         */
        List<Integer> whiteList = ListConverter.toList(white);
        List<Integer> blackList = ListConverter.toList(black);
        int position = 1;
        for(int row=0; row<size; row++)
        {
            for(int column=0; column<size; column++)
            {
                if(whiteList.contains(position))
                {
                    squares[row][column] = new Warrior(Color.WHITE);
                }
                else if (blackList.contains(position))
                {
                    squares[row][column] = new Warrior(Color.BLACK);
                }
                position++;
            }
        }
    }

    public boolean isEmptyPath(Move move)
    {
        boolean isEmpty = true;
        Warrior[] path = getPath(move);
        for(Warrior tile : path)
        {
            if(tile != null)
            {
                isEmpty = false;
            }
        }
        return isEmpty;
    }

    private Warrior[] getPath(Move move)
    {
        int x = -1;
        int y = -1;
        int init,end;
        Warrior[] path;
        Warrior[] result;
        if(move.isHorizontalMove())
        {
            init = move.getInitialPosition().getColumn();
            end = move.getFinalPosition().getColumn();
            x = move.getInitialPosition().getRow();
        }
        else
        {
            init = move.getInitialPosition().getRow();
            end = move.getFinalPosition().getRow();
            y = move.getInitialPosition().getColumn();
        }

        path = getRowOrColumn(x,y);
        return getPathFromPoints(path,init,end);
    }

    private Warrior[] getPathFromPoints(Warrior[] path, int init, int end)
    {
        int first, last;
        if (init >= end)
        {
            first = end;
            last = init;
        }
        else
        {
            first = init + 1;
            last = end + 1;
        }
        return Arrays.copyOfRange(path,first,last);
    }

    private Warrior[] getRowOrColumn(int x, int y)
    {
        Warrior[] result = new Warrior[size];
        if(x>=0 && x<size)
        {
            result = getColumn(x);
        }
        if(y>=0 && y<size)
        {
            result = getRow(y);
        }
        return result;
    }

    private Warrior[] getRow(int column)
    {
        Warrior[] result = new Warrior[size];
        for(int row=0; row<size; row++)
        {
            result[row] = squares[row][column];
        }
        return result;
    }

    private Warrior[] getColumn(int row)
    {
        Warrior[] result = new Warrior[size];
        for(int i=0; i<size; i++)
        {
            result[i] = squares[row][i];
        }
        return result;
    }

    public void updateBoard(Move move)
    {
        if(move.isBoardPosition(size))
        {
            moveWarrior(move);
            captureWarrior(move);
        }
    }

    private void moveWarrior(Move move)
    {
        Position initPosition = move.getInitialPosition();
        squares[move.getFinalPosition().getRow()][move.getFinalPosition().getColumn()] = squares[initPosition.getRow()][initPosition.getColumn()];
        squares[initPosition.getRow()][initPosition.getColumn()] = null;
    }

    private void captureWarrior(Move move)
    {
        Position warriorPosition = move.getFinalPosition();
        captureUp(warriorPosition);
        captureDown(warriorPosition);
        captureRight(warriorPosition);
        captureLeft(warriorPosition);
    }


    private void captureUp(Position warriorPosition)
    {
        Warrior warrior = getPosition(warriorPosition);
        Position enemyPosition = warriorPosition.positionUp();
        Warrior warriorUp = getPosition(warriorPosition.positionUp().positionUp());
        if(enemyIsCaptured(warrior,getPosition(enemyPosition),warriorUp))
        {
            deleteWarrior(enemyPosition);
        }
    }

    private void captureDown(Position warriorPosition)
    {
        Warrior warrior = getPosition(warriorPosition);
        Position enemyPosition = warriorPosition.positionDown();
        Warrior warriorDown = getPosition(warriorPosition.positionDown().positionDown());
        if(enemyIsCaptured(warrior,getPosition(enemyPosition),warriorDown))
        {
            deleteWarrior(enemyPosition);
        }
    }

    private void captureRight(Position warriorPosition)
    {
        Warrior warrior = getPosition(warriorPosition);
        Position enemyPosition = warriorPosition.positionRight();
        Warrior warriorRight = getPosition(warriorPosition.positionRight().positionRight());
        if(enemyIsCaptured(warrior,getPosition(enemyPosition),warriorRight))
        {
            deleteWarrior(enemyPosition);
        }
    }

    private void captureLeft(Position warriorPosition)
    {
        Warrior warrior = getPosition(warriorPosition);
        Position enemyPosition = warriorPosition.positionLeft();
        Warrior warriorLeft = getPosition(warriorPosition.positionLeft().positionLeft());
        if(enemyIsCaptured(warrior,getPosition(enemyPosition),warriorLeft))
        {
            deleteWarrior(enemyPosition);
        }
    }

    private boolean enemyIsCaptured(Warrior warrior1, Warrior enemy, Warrior warrior2)
    {
        if(enemy != null && warrior2 != null )
        {
            return warrior1.getColor()==warrior2.getColor() && enemy.getColor()!=warrior1.getColor();
        }
        else
        {
            return false;
        }
    }

    private void deleteWarrior(Position position)
    {
        if(!position.equals(getKingPosition()))
        {
            squares[position.getRow()][position.getColumn()] = null;
        }
    }

    private boolean iskingSurrounded(List<Position> listPosition)
    {
        int numberBlackWarriors = 0;
        boolean thereIsWallOrCenter = false;
        for(Position position : listPosition)
        {
            Warrior warrior = getPosition(position);
            if(warrior!=null)
            {
                if(warrior.getColor() == Color.BLACK)
                {
                    numberBlackWarriors++;
                }
            }
            else
            {
                if(!position.isCorrectPosition(size) || isCenter(position))
                {
                    thereIsWallOrCenter = true;
                }
            }
        }
        return numberBlackWarriors == 4 ||
                (numberBlackWarriors == 3 && thereIsWallOrCenter);
    }
}
