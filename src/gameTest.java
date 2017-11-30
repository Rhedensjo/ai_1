/**
 * Created by Rasmus on 30-Nov-17.
 */

import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.*;

public class gameTest {

    OthelloPosition pos;
    GameSearch game;
    int time;
    OthelloAction action;

    @Before
    public void initObj(){
        pos = new OthelloPosition();
        pos.initialize();
        game = new GameSearch();
        time = 5;
    }

    @Test
    public void firstMoveTest(){
        game.setTime(time);
        action = game.evaluate(pos);
        action.print();
    }


    /*OthelloPosition pos = new OthelloPosition(var1);

    GameSearch game = new GameSearch();
    String time = args[1];
        game.setTime(Integer.valueOf(time));

    OthelloAction eval = game.evaluate(pos);
        eval.print();*/

}
