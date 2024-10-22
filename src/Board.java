import java.util.ArrayList;
import java.util.Arrays;

public class Board {

    private static final int SIZE = 3;
    private static Square[][] EMPTY_BOARD(){
        Square[][] b = new Square[SIZE][SIZE];
        for(int i=0;i<SIZE;i++){
            Arrays.fill(b[i],Square.EMPTY);
        }
        return b;
    }

    private final Square[][] board;
    private final ArrayList<Board> children;

    private final Square turn;


    public Board(){
        this(Square.X,EMPTY_BOARD());
    }

    public Board(Square turn, Square[][] newBoard){
        this.turn = turn;
        board = newBoard;
        children = new ArrayList<>();
        if(winner()!=Square.EMPTY)return;
        for(int row = 0; row<SIZE;row++){
            for(int col = 0; col<SIZE;col++){
                if(board[col][row]==Square.EMPTY){
                    Square[][] tempBoard = new Square[SIZE][SIZE];
                    for(int i = 0; i<SIZE; i++){
                        tempBoard[i] = Arrays.copyOf(board[i],SIZE);
                    }
                    tempBoard[col][row]=turn;
                    Board child = new Board(otherTurn(),tempBoard);
                    children.add(child);
                }
            }
        }
    }

    private Square otherTurn(){

        if(turn==Square.X){
            return Square.O;
        }
        else if(turn==Square.O){
            return Square.X;
        }
        return null;
    }

    public Square winner(){
        //Check rows and columns by iteration
        for(int row = 0; row<SIZE;row++){
            Square start = board[row][0];
            if(start==Square.EMPTY)break;
            int col = 1;
            while(col<SIZE && board[col][row]==start)col++;
            if(col==SIZE)return start;
        }
        for(int col = 0; col<SIZE;col++){
            Square start = board[col][0];
            if(start==Square.EMPTY)break;
            int row = 1;
            while(row<SIZE && board[col][row]==start)row++;
            if(row==SIZE){
                return start;
            }
        }

        //Check diagonals
        Square startLeft = board[0][0];
        boolean leftDiag = startLeft != Square.EMPTY;
        Square startRight = board[SIZE-1][0];
        boolean rightDiag = startRight != Square.EMPTY;
        for(int i = 1;i<SIZE;i++){
            if(leftDiag && board[i][i]!=startLeft){
                leftDiag=false;
            }
            if(rightDiag&&board[SIZE-i-1][i]!=startRight){
                rightDiag=false;
            }
        }
        if(leftDiag)return startLeft;
        if(rightDiag){
            return startRight;
        }

        return Square.EMPTY;
    }

    public ArrayList<Board> getChildren(){
        return children;
    }

    public String toString(){
        String output = "";
        for(int row = 0;row<SIZE;row++){
            char[] current = new char[SIZE+1];
            current[SIZE]='\n';
            for(int col = 0;col<SIZE;col++){
                current[col]=board[col][row] == Square.EMPTY ? '*' : (board[col][row]==Square.X ? 'X' : 'O');
            }
            output = output.concat(new String(current));
        }
        return output;
    }

    public Square getSquare(int row, int col){
        return board[col][row];
    }

    public Board move(int row, int col){
        if(board[col][row]!=Square.EMPTY)return null;
        for(Board i : children){
            if(i.getSquare(row,col)==turn){
                return i;
            }
        }
        return this;
    }

    public static void main(String[] args){
        new Board();
    }

}
