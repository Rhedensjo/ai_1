import java.util.LinkedList;

/**
 * Created by Rasmus on 27-Nov-17.
 */
public class evaluator implements OthelloEvaluator {
    private OthelloAction copyAction = new OthelloAction(0, 0);
    private int timeLimit;
    protected int searchDepth;


    public evaluator(){
    }

    /**
     * Returns the <code>OthelloAction</code> the algorithm considers to be the
     * best move.
     */

    /*
    Got inspiration from nealyoung:s implementation of an iterative deepening search
    https://github.com/nealyoung/CS171/blob/master/AI.java
    Implements an IterativeDeepening search
     */
    public int evaluate(OthelloPosition pos){
        int white = 0;
        int black = 0;

        OthelloPosition board = pos.clone();
        if (board.playerToMove){
            white = board.counter();
        } else{
            black = board.counter();
        }
        board.switchPlay();
        if (board.playerToMove){
            white = board.counter();
        } else{
            black = board.counter();
        }

        if (white > black){
            return 1;
        }else if(white < black){
            return -1;
        }else{
            return 0;
        }
    }
}
