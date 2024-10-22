import java.util.Scanner;
import java.util.Random;

public class TicTacToe {

    private final Scanner input;

    public TicTacToe(){
        System.out.println("Welcome to tic tac toe, you go first, indicate your move with two numbers, first row then column");
        input = new Scanner(System.in);
        do {
            Square winner = move(new Board());
            if (winner == Square.X) {
                System.out.println("You Win!");
            } else if (winner == Square.O) {
                System.out.println("You Lose!");
            } else {
                System.out.println("Tie!");
            }
            System.out.println("Please type exit to quit or enter to play again");
        } while (!input.nextLine().equals("exit"));
    }


    public Square move(Board board){
        if(board.getChildren().isEmpty()||board.winner()!=Square.EMPTY){
            return board.winner();
        }
        System.out.println(board);
        Board b = board.move(input.nextInt(),input.nextInt());
        while(b==null){
            System.out.println("Impossible Move, try again");
            b = board.move(input.nextInt(),input.nextInt());
        }
        System.out.println(b);
        //System.out.println(b.winner());
        Random r = new Random();
        if(b.getChildren().isEmpty()||b.winner()!=Square.EMPTY){
            return b.winner();
        }
        return move(b.getChildren().get(r.nextInt(b.getChildren().size())));
    }

    public static void main(String[] args){
        new TicTacToe();

    }


}
