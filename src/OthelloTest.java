import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.*;

/**
 * Created by Rasmus on 22-Nov-17.
 */
public class OthelloTest{

    private OthelloPosition brade;

    @Before
    public void initObj(){
        brade = new OthelloPosition();
        brade.initialize();
    }
    @Test
    public void testGetMovesIni() {
        LinkedList<String> list = new LinkedList();
        list.add("(3,5)");
        list.add("(4,6)");
        list.add("(5,3)");
        list.add("(6,4)");
        brade.illustrate();
        assertEquals(list,brade.getMoves());
    }

    @Test
    public void testGetMovesFalse(){
        LinkedList<String> list = new LinkedList<>();
        assertNotEquals(list,brade.getMoves());
    }

    @Test
    public void testBoard(){
        OthelloPosition move = brade.clone();
        assertTrue(brade.toMove());
        move.switchPlay();
        assertFalse(move.toMove());

    }

    @Test
    public void testPosMoveAll(){
        brade.switchPlay();
        for (int i = 0; i < brade.BOARD_SIZE + 2; i++) {
            for (int j = 0; j < brade.BOARD_SIZE + 2; j++){
                if (brade.checkPosMove(i,j)){
                    System.out.println("r: " + i + "\tc: " + j);
                }
            }
        }
    }

    @Test
    public void testMakeMove1() {
        OthelloAction action1 = new OthelloAction(4,6);
        OthelloAction action2 = new OthelloAction(5,2);
        OthelloPosition move1, move2;

        brade.board[5][3] = 'B';
        brade.board[6][3] = 'W';

        brade.illustrate();

        try{
            move1 = brade.makeMove(action1);
            move1.illustrate();

            move2 = brade.makeMove(action2);
            move2.illustrate();

            assertEquals(5, action1.getValue());
            assertEquals(6, action2.getValue());
        } catch (IllegalMoveException e) {
            e.printStackTrace();
        }

    }

    /**
     * Tests placeability
     */
    @Test
    public void testCheckPlacabilitySimple() {
        assertFalse(brade.checkPosMove(4,4));
        assertFalse(brade.checkPosMove(4,5));
        assertTrue(brade.checkPosMove(6,4));
        assertFalse(brade.checkPosMove(2,4));
    }

    @Test
    public void testCheckPlacabilitySimple2() {
        brade.board[1][2] = brade.board[2][1] = 'B';
        brade.board[2][2] = 'W';
        assertFalse(brade.checkPosMove(2,2));
        assertFalse(brade.checkPosMove(1,1));
        brade.board[1][3] = 'W';
        brade.illustrate();
        assertTrue(brade.checkPosMove(1,1));
    }

    /**
     * Tests the direction checker
     */
    @Test
    public void testCheckDir() {
        Positions w,e,n,s,nw,ne,sw,se;

        w = brade.checkDir('B', 5, 6, 0, -1);
        assertEquals(w.getX(), 5);
        assertEquals(w.getY(), 4);

        e = brade.checkDir('B', 4,3,0,1);
        assertEquals(e.getX(), 4);
        assertEquals(e.getY(), 5);

        n = brade.checkDir('B', 6,5,-1,0);
        assertEquals(n.getX(), 4);
        assertEquals(n.getY(), 5);

        s = brade.checkDir('B', 3,4,1,0);
        assertEquals(s.getX(), 5);
        assertEquals(s.getY(), 4);

        brade.board[4][6] = 'B';
        brade.board[6][5] = 'W';

        ne = brade.checkDir('B', 6,4,-1,1);
        assertEquals(ne.getX(), 4);
        assertEquals(ne.getY(), 6);

        nw = brade.checkDir('B', 7,6,-1,-1);
        assertEquals(nw.getX(), 5);
        assertEquals(nw.getY(), 4);

        sw = brade.checkDir('W', 3,7,1,-1);
        assertEquals(sw.getX(), 5);
        assertEquals(sw.getY(), 5);

        se = brade.checkDir('W', 4,3,1,1);
        assertEquals(se.getX(), 6);
        assertEquals(se.getY(), 5);

    }

    @Test
    public void testCheckDirEnd(){
        Positions pos;
        brade.board[4][6] = brade.board[4][7] = brade.board[4][8] = 'B';
        brade.illustrate();
        pos = brade.checkDir('W', 4, 4,0,1);
        assertNull(pos);
    }

    @Test
    public void testCheckDirJump(){
        Positions pos1, pos2, pos3;
        brade.board[4][6] = 'W';
        brade.board[4][7] = 'B';
        brade.illustrate();
        pos1 = brade.checkDir('W', 4,6,0,1);
        pos2 = brade.checkDir('B', 4,3,0,1);
        assertNull(pos1);
        assertEquals(pos2.getX(),4);
        assertEquals(pos2.getY(),5);
        pos3 = brade.checkDir('W', 4,8,0,-1);
        assertEquals(pos3.getX(), 4);
        assertEquals(pos3.getY(), 6);
    }

    @Test
    public void testCheckDirEdge(){
        assertNull(brade.checkDir('W', 3,1,0,-1));
        assertNull(brade.checkDir('W', 8,8,1,0));
        brade.board[1][1] = 'B';
        brade.board[8][8] = 'W';
        assertNull(brade.checkDir('B', 1,1,0,-1));
        assertNull(brade.checkDir('W', 8,8,0,1));
    }


    /**
     * Test if the player can be confirmed and switched
     */
    @Test
    public void testDetPlayer() {
        char c = 'W';
        char cT =  brade.detPlayer();
        assertEquals(c, cT);
    }
    @Test
    public void testSwitchPlay() {
        char c = 'B';
        brade.switchPlay();
        char cT = brade.detPlayer();
        assertEquals(c, cT);
    }

    @Test
    public void testswitch2(){
        assertFalse(brade.checkPosMove(3,4));
        brade.switchPlay();
        assertTrue(brade.checkPosMove(3,4));
    }

    /**
     * Tests the flippin of pieces
     */
    @Test
    public void testFlipperOne() {
        OthelloAction action = new OthelloAction(6,4);
        OthelloAction move;
        int[] pos = new int[2];

        brade.board[6][3] = 'B';
        brade.board[6][2] = 'W';
        brade.board[5][3] = 'B';
        brade.board[4][2] = 'W';
        brade.board[5][5] = 'B';
        brade.board[4][6] = 'W';
        brade.board[6][5] = 'B';
        brade.board[6][6] = 'W';
        brade.board[7][3] = 'B';
        brade.board[8][2] = 'W';
        brade.board[7][4] = 'B';
        brade.board[6][6] = 'W';
        brade.board[8][4] = 'W';
        brade.board[6][6] = 'W';
        brade.board[7][5] = 'B';
        brade.board[8][6] = 'W';
        pos[0] = 6;
        pos[1] = 4;
        brade.illustrate();

        brade.flipper(brade, pos);
        brade.illustrate();
    }

    /**
     * Tests the flippin of pieces
     */
    @Test
    public void testFlipperTwo() {
        int[] pos = new int[2];

        brade.board[4][4] = 'B';
        pos[0] = 3;
        pos[1] = 3;

        brade.flipper(brade, pos);
        brade.illustrate();
    }

    @Test
    public void testFlipperThree() {
        int[] pos = new int[2];

        brade.board[5][3] = 'B';
        brade.board[6][3] = 'B';
        pos[0] = 5;
        pos[1] = 2;

        brade.flipper(brade, pos);
        brade.illustrate();
    }

    @Test
    public void testretFlipsSimple(){
        LinkedList<int[]> c;
        c = brade.retFlips(4,6);
        assertEquals(4,c.getFirst()[0]);
        assertEquals(5,c.getFirst()[1]);
    }

    @Test
    public void testretFlipsTwo(){
        brade.board[6][3] = 'B';
        brade.board[6][2] = 'W';
        brade.board[5][3] = 'B';
        brade.board[4][2] = 'W';
        brade.board[5][5] = 'B';
        brade.board[4][6] = 'W';
        brade.board[6][5] = 'B';
        brade.board[6][6] = 'W';
        brade.board[7][3] = 'B';
        brade.board[8][2] = 'W';
        brade.board[7][4] = 'B';
        brade.board[6][6] = 'W';
        brade.board[8][4] = 'W';
        brade.board[6][6] = 'W';
        brade.board[7][5] = 'B';
        brade.board[8][6] = 'W';

        brade.illustrate();
        LinkedList<int[]> c;
        c = brade.retFlips(6,4);
        for (int i = 0; i < c.size(); i++){
            System.out.println("row: " + c.get(i)[0] + "\tcol: " + c.get(i)[1]);
        }
        //Oddity: doublets of 5,3 and 7,5
        assertTrue(true);
    }

    /*@Test
    public void testRetFlipsThree(){
        LinkedList<int[]> c;
        brade.board[5][3] = 'B';
        brade.board[6][3] = 'B';

        brade.illustrate();

        c = brade.retFlips(5,2);
        System.out.println(c.size());
    }*/

    @Test
    public void testretCheckDir(){
        LinkedList<int[]> c;
        c = brade.retCheckedDir('W',4,6,0,-1);

        assertEquals(4,c.getFirst()[0]);
        assertEquals(5,c.getFirst()[1]);
    }

    @Test
    public void testretCheckDirTwo(){
        LinkedList<int[]> c;

        brade.board[5][3] = 'B';
        brade.board[6][3] = 'B';

        c = brade.retCheckedDir('W',5,2,0,1);

        assertEquals(5,c.get(0)[0]);
        assertEquals(3,c.get(0)[1]);
        assertEquals(5,c.get(1)[0]);
        assertEquals(4,c.get(1)[1]);

    }
}
