import java.util.ArrayList;

public class Ass2
{
    Parser p = new Parser();
    ExpTree myTree;

    public static void main(String[] args)
    {
        System.out.println("=========================================");
        System.out.println("Registration number : 1805841");
        System.out.println("Name : Kamil Cwigun\nWelcome to Kam's expression evaluation program.");
        System.out.println("=========================================\n");

        Ass2 runDoLoop = new Ass2();
        runDoLoop.doLoop();
    }

    public void doLoop()
    {
        ArrayList<String> identifiers = new ArrayList<>();
        ArrayList<Integer> values = new ArrayList<>();

        String input = "input";

        do
        {
            try
            {
                System.out.println("Please enter your expression");
                myTree =  p.parseLine();
            }

            catch (ParseException e)
            {
                System.out.println("\nInvalid Expression!\nPlease make sure you have a semicolon at the end.");
                continue;
            }

            System.out.println("\nPost-order output : " + myTree.toPostOrder(myTree));

            System.out.println("In-Order output : " + myTree.toString(myTree, false));

            try
            {
                System.out.println("Result of expression : " + myTree.evaluate(myTree, identifiers, values));
            }

            catch (Exception e)
            {
                System.out.println("Error!\nException : " + e + "\n");
            }

            System.out.println("\nPlease enter 'Y' to input another expression or 'N' to quit");

            input = p.getLine();

            while(!input.equals("y") && !input.equals("Y") && !input.equals("n") && !input.equals("N"))
            {
                System.out.println("\nInvalid input!");
                System.out.println("Please enter 'Y' to input another expression or 'N' to quit");

                input = p.getLine();
            }

        }while(!input.equals("n") && !input.equals("N"));

        System.out.println("\nGoodbye...");
        System.exit(0);
    }
}