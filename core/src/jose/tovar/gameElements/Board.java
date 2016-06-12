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

    public Warrior[][] getSquares()
    {
        return squares;
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
            Position initPosition = move.getInitialPosition();
            squares[move.getFinalPosition().getRow()][move.getFinalPosition().getColumn()] = squares[initPosition.getRow()][initPosition.getColumn()];
            squares[initPosition.getRow()][initPosition.getColumn()] = null;
        }
    }
}
