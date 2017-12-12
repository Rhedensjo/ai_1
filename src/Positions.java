/**
 * Created by Rasmus on 08-Nov-17.
 */
public class Positions {
    private final int x;
    private final int y;

    public Positions(int X, int Y){
        this.x = X;
        this.y = Y;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public void print(){
        System.out.println("X: " + this.x + "\tY: " + this.y);
    }

    public String asString(){
        String str = "row: " + this.x + "\tcol: " + this.y;
        return str;
    }
}
