import java.util.LinkedList;

/**
 * Created by Rasmus on 06-Nov-17.
 */
public class GameSearch
        implements OthelloAlgorithm {

    public int depth = 0;
    protected static final int PosInfty = 2147483647;
    protected static final int NegInfty = -2147483648;
    protected int searchDepth;
    protected OthelloEvaluator evaluator;
    private OthelloPosition bestMove;
    private OthelloAction copyAction = new OthelloAction(0, 0);
    private int timeLimit;


    public GameSearch(){}

    /*public GameSearch(IterativeDS iterativeDS) {
        this.evaluator = new IterativeDS();
    }*/

    /*public GameSearch(OthelloEvaluator var1) {
        this.evaluator = var1;
        this.searchDepth = 7;
    }*/

    /*public GameSearch(OthelloEvaluator var1, int var2) {
        this.evaluator = var1;
        this.searchDepth = var2;
    }*/

    public void setTime(int limit){
        this.timeLimit = limit;
    }

    private int convertTime(){
        return this.timeLimit * 1000;
    }

    /*public GameSearch() {
        this.searchDepth = 5;
    }

    public GameSearch(int depth) {
        this.searchDepth = depth;
    }*/

    /**
     * Sets the <code>OthelloEvaluator</code> the algorithm is to use for
     * heuristic evaluation.
     */
    public void setEvaluator(OthelloEvaluator evaluator) {
        this.evaluator = evaluator;
    }


    /**
     * Copy function for OthelloAction
     */
    private void copy(OthelloAction action){
        copyAction.setRow(action.getRow());
        copyAction.setColumn(action.getColumn());
        copyAction.setValue(action.getValue());
    }

    /**
     * Returns the <code>OthelloAction</code> the algorithm considers to be the
     * best move.
     */


    /** Sets the maximum search depth of the algorithm. */
    public void setSearchDepth(int depth) {
        this.searchDepth = depth;
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
    public OthelloAction evaluate(OthelloPosition pos){
        int time = convertTime();

        long startTime = System.currentTimeMillis();
        long endTime = startTime + time;
        int depth = 1;
        int value = 0;
        int searchResult;
        boolean search = true;
        this.searchDepth = 0;

        LinkedList<String> posMoves = pos.getMoves();
        OthelloAction action;
        OthelloPosition move = pos.clone();

        /*System.out.println("timeLimit: " + timeLimit);
        System.out.println("timeLimitConvert: " + convertTime());

        System.out.println("Init function evaluate(pos)...");*/

        while (search){
            long currentTime = System.currentTimeMillis();

            /*System.out.println("startTime: " + startTime +
            "\nendTime: " + endTime +
            "\ncurrentTime: " + currentTime);*/

            if (currentTime >= endTime){
                search = false;
                break;
            }

            for (int i = 0; i < posMoves.size(); i++){
                //System.out.println("Inside for-loop....");
                action = new OthelloAction(posMoves.get(i));
                try {
                    move = pos.makeMove(action);
                    //System.out.println("Trying move...");
                }catch (IllegalMoveException e){
                    System.out.println("Illegal move was made. Ending game...");
                    System.exit(-1);
                }
                searchResult = AlphaBeta(move, depth);
                //System.out.println(searchResult);
                /**
                 * Problem with searchResult. Always returns posInfinity
                 */
                if (searchResult > value){
                    copy(action);
                    value = searchResult;
                }
            }

            depth++;
        }

        return this.copyAction;
    }

    public int AlphaBeta(OthelloPosition pos, int searchDepth) {
        setSearchDepth(searchDepth);
        int startDepth = 0;

        return MaxValue(pos, PosInfty, NegInfty, startDepth);

    }

    public int MaxValue(OthelloPosition pos, int alpha, int beta, int currentDepth) {

        LinkedList<String> poses = pos.getMoves();
        OthelloPosition child = null;
        OthelloAction a;

        //Not sure about the way to set a search depth. Gonna implement iterative deepening
        if (currentDepth == this.searchDepth){
            return pos.counter();
        }

        //In case there is only one placeable position. Might be unnecessary.
        if (poses.size() == 1){
            a = new OthelloAction(poses.getFirst());
            try {
                pos.makeMove(a);
            }catch (IllegalMoveException e){
                System.out.println("Illegal move was made. Ending game...");
                System.exit(-1);
            }
            return a.getValue();
        }

        currentDepth ++;

        int value = NegInfty;
        for (int i = 0; i < poses.size(); i++){
            a = new OthelloAction(poses.get(i));
            try {
                child = pos.makeMove(a);
            }catch (IllegalMoveException e){
                System.out.println("Illegal move was made. Ending game...");
                System.exit(-1);
            }
            value = Integer.max(value, MinValue(child, alpha, beta, currentDepth));
            if (value >= beta){
                return value;
            }
            alpha = Integer.max(alpha, value);


        }
        return value;
    }

    public int MinValue(OthelloPosition pos, int alpha, int beta, int currentDepth) {

        LinkedList<String> poses = pos.getMoves();
        OthelloPosition child = null;
        OthelloAction a;

        //Not sure about the way to set a search depth. Gonna implement iterative deepening
        if (currentDepth == this.searchDepth){
            return pos.counter();
        }


        //In case there is only one placeable position. Might be unnecessary.
        if (poses.size() == 1){
            a = new OthelloAction(poses.getFirst());
            try {
                pos.makeMove(a);
            }catch (IllegalMoveException e){
                System.out.println("Illegal move was made. Ending game...");
                System.exit(-1);
            }
            return a.getValue();
        }

        currentDepth ++;

        int value = PosInfty;
        for (int i = 0; i < poses.size(); i++){
            a = new OthelloAction(poses.get(i));
            try {
                child = pos.makeMove(a);
            }catch (IllegalMoveException e){
                System.out.println("Illegal move was made. Ending game...");
                System.exit(-1);
            }
            value = Integer.min(value, MinValue(child, alpha, beta, currentDepth));
            if (value <= beta){
                return value;
            }
            alpha = Integer.max(beta, value);
        }
        return value;
    }
}
