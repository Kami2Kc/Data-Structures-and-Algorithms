import java.util.ArrayList;

public class ExpTree
{
    private int type;
    private Object value;
    private ExpTree leftChild;
    private ExpTree rightChild;

    private ArrayList<String> idName = new ArrayList<>();
    private ArrayList<Integer> idValue = new ArrayList<>();

    public ExpTree(int type, Object value, ExpTree left, ExpTree right)
    {
        this.type = type;
        this.value = value;
        this.leftChild = left;
        this.rightChild = right;
    }

    public static final int numNode = 0, idNode = 1, opNode = 2, letNode = 3, andNode = 4, equalsNode = 5;

    // The toPostOrder method creates a string builder during each recursion and add to it.
    // Each time a recursion happens and is returned it is then appended and returned which
    // as a result builds up the string and keeps adding to it until it is complete
    // If the root is a let node then it will ignore the left child and go straight for the right child and treat it as
    // if there was no let node and the right child was the root

    public String toPostOrder(ExpTree tree)
    {
        StringBuilder output = new StringBuilder();

        if (tree != null)
        {
            if (tree.type == letNode)
            {
                return toPostOrder(tree.rightChild);
            }
            else
                {
                    output.append(toPostOrder(tree.leftChild));
                    output.append(toPostOrder(tree.rightChild));

                    output.append(tree.value).append(" ");
                }
        }

        return output.toString();
    }

    // The to string method works in a similar way to the toPostOrder method but it will check the values of
    // the root as well as the left and right child to then determine if they will need brackets.
    // The 2 arguments for this method are tree and a boolean brackets. this boolean tells this method if it has to add brackets to its output
    // while at the same time it will use if statements to determine if its children need them as well.
    // If the tree input through the argument is a number or an identifier then it will just return it without any brackets
    // The only time this method checks if brackets are necessary is when its children are of type opNode
    // This is also a recursive method that build up the output as it goes up the tree just like the toPostOrder method

    public String toString(ExpTree tree, boolean brackets)
    {
        if (tree != null)
        {
            StringBuilder inOrderOutput = new StringBuilder();

            boolean leftBrackets = true;
            boolean rightBrackets = true;

            if (tree.type == letNode)
            {
                return inOrderOutput.append(tree.toString(tree.leftChild, false)).append(" in ").append(tree.toString(tree.rightChild, false)).toString();
            }

            else if (tree.type == andNode)
            {
                return inOrderOutput.append(tree.toString(tree.leftChild, false)).append(" and ").append(tree.toString(tree.rightChild, false)).toString();
            }

            else if (tree.type == equalsNode)
            {
                return inOrderOutput.append(tree.value.toString()).append(" = ").append(tree.toString(tree.leftChild, false)).toString();
            }

            else if (tree.type == numNode || tree.type == idNode)
            {
                return inOrderOutput.append(tree.value).toString();
            }

            else
            {
                if ((tree.leftChild.value.toString().equals("+") || tree.leftChild.value.toString().equals("-")) && (tree.value.toString().equals("+") || tree.value.toString().equals("-")))
                {
                    leftBrackets = false;
                }

                if (tree.rightChild.value.toString().equals("+") && (tree.value.toString().equals("+") || tree.value.toString().equals("-")))
                {
                    rightBrackets = false;
                }

                if (brackets)
                {
                    inOrderOutput.append('(');
                }

                inOrderOutput.append(tree.toString(tree.leftChild, leftBrackets));
                inOrderOutput.append(tree.value);
                inOrderOutput.append(tree.toString(tree.rightChild, rightBrackets));

                if (brackets)
                {
                    inOrderOutput.append(')');
                }
            }

            return inOrderOutput.toString();
        }
        else
            {
                return " ";
            }
    }

    // For the evaluate method I first made it check if the provided tree is a let node which will then result in calling
    // evaluate on the leftChild first. This will then create and fill up 2 ArrayLists where 1 stores the string name of
    // the identifier and the other stores the value for that identifier with both of their indexes being the same
    // Because of a problem I ran into while trying to append to the 2 array lists at the top of this class and storing them
    // i had to instead create 2 arguments that allowed me to pass them to the method directly instead, which solved my issue
    // and i have not encountered any issues so far with this method so i decided to stick with it.(knowing my luck it will probably break while being marked)
    // If the tree provided is a and node then it will evaluate its children
    // If the tree is equals node then it will only evaluate its left child as that is what is returned in the parser class
    // The values that this method returns depends on the supplied node, if the node eis an int or a identifier then it will
    // return the int or the value associated with that identifier otherwise it will check if the node it a operator and then calculate the result
    // using the 2 children

    // During the methods calculation process it is first supplied with 2 empty array lists and if the first node is a let node
    // it will create the tree and then fill in these tables. If the expression does not use any user submitted identifiers if will skip
    // the checking process of the 2 array lists. If the expressions contains any user submitted identifiers then it will first check if the supplied node is
    // one of those submitted by the user and if not then it returns the default value.

    // If an empty tree is supplied it will return 0

    public int evaluate(ExpTree tree, ArrayList<String> identifiers, ArrayList<Integer> values)
    {
        if (tree != null)
        {
            if (tree.type == letNode)
            {
                tree.evaluate(tree.leftChild, identifiers, values);
                return tree.evaluate(tree.rightChild, tree.leftChild.idName, tree.leftChild.idValue);
            }

            else if (tree.type == andNode)
            {
                tree.evaluate(tree.leftChild, identifiers, values);
                return tree.evaluate(tree.rightChild, identifiers, values);
            }

            else if (tree.type == equalsNode)
            {
                boolean overwrite = false;

                for (String s : idName)
                {
                    if (tree.value.toString().equals(s))
                    {
                        idValue.add(idName.indexOf(s), tree.evaluate(tree.leftChild, identifiers, values));
                        overwrite = true;
                    }
                }

                if (!overwrite)
                {
                    idName.add(tree.value.toString());
                    idValue.add(tree.evaluate(tree.leftChild, identifiers, values));
                }

                return tree.evaluate(tree.leftChild, identifiers, values);
            }

            else if (tree.type == numNode)
            {
                return (int) tree.value;
            }

            else if (tree.type == idNode)
            {
                if (!identifiers.isEmpty() && !values.isEmpty())
                {
                    for (String s : identifiers)
                    {
                        if (tree.value.toString().equals(s))
                        {
                            return values.get(identifiers.indexOf(s));
                        }
                    }
                }

                char[] idNodeFirstChar = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',};

                for(int i = 0; i <= 26; i++)
                {
                    if (idNodeFirstChar[i] == tree.value.toString().charAt(0))
                    {
                        return 25 - i;
                    }
                }
            }

            else if (tree.type == opNode)
            {
                if (tree.value.toString().equals("+"))
                {
                    return tree.evaluate(tree.leftChild, identifiers, values) + tree.evaluate(tree.rightChild, identifiers, values);
                }

                else if (tree.value.toString().equals("-"))
                {
                    return tree.evaluate(tree.leftChild, identifiers, values) - tree.evaluate(tree.rightChild, identifiers, values);
                }

                else if (tree.value.toString().equals("*"))
                {
                    return tree.evaluate(tree.leftChild, identifiers, values) * tree.evaluate(tree.rightChild, identifiers, values);
                }

                else if (tree.value.toString().equals("/"))
                {
                    return tree.evaluate(tree.leftChild, identifiers, values) / tree.evaluate(tree.rightChild, identifiers, values);
                }

                else if (tree.value.toString().equals("%"))
                {
                    return tree.evaluate(tree.leftChild, identifiers, values) % tree.evaluate(tree.rightChild, identifiers, values);
                }

                else if (tree.value.toString().equals("^"))
                {
                    if (tree.evaluate(tree.rightChild, identifiers, values) < 0)
                    {
                        throw new IllegalArgumentException("Cannot have negative powers!");
                    }

                    return (int) Math.pow(tree.evaluate(tree.leftChild, identifiers, values), tree.evaluate(tree.rightChild, identifiers, values));
                }
            }
        }
        else
            {
                System.out.println("No value has been assigned for identifier '" + "' ,using default value '0'");
                return 0;
            }
        return 0;
    }
}