/**
 * Created by Rasmus on 27-Nov-17.
 */
public class myOthello {
    public myOthello() {
    }

    public static void main(String [] args){
        String var1;
        if(args.length > 0) {
            var1 = args[0];
        } else {
            var1 = "WEEEEEEEEEEEEEEEEEEEEEEEEEEEOXEEEEEEXOEEEEEEEEEEEEEEEEEEEEEEEEEEE";
        }

        OthelloPosition pos = new OthelloPosition(var1);

        GameSearch game = new GameSearch();
        String time = args[3];
        game.setTime(Integer.valueOf(time));

        OthelloAction eval = game.evaluate(pos);
        eval.print();
    }
}
