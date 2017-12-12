/**
 * Created by Rasmus on 27-Nov-17.
 */
public class myOthello {
    public myOthello() {
    }

    public static void main(String[] args){

        if (args.length != 2){
            System.out.println("Wrong number of arguments, try again!");
            System.exit(-1);
        }
        if (args[0].length() != 65) {
            System.out.println("Invalid length of board string. Try again.");
            System.exit(-1);
        }


        OthelloPosition pos = new OthelloPosition(args[0]);

        GameSearch game = new GameSearch();
        String time = args[1];
        game.setTime(Integer.valueOf(time));

        OthelloAction eval = game.evaluate(pos);
        eval.print();
    }
}
