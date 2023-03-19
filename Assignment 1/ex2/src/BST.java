public class BST
{
    private BTNode<Integer> root;

    public BST()
    {
        root = null;
    }

    public boolean find(Integer i)
    {
        BTNode<Integer> n = root;
        boolean found = false;

        while (n!=null && !found)
        {
            int comp = i.compareTo(n.data);
            if (comp==0)
                found = true;
            else if (comp<0)
                n = n.left;
            else
                n = n.right;
        }

        return found;
    }

    public boolean insert(Integer i)
    {
        BTNode<Integer> parent = root, child = root;
        boolean goneLeft = false;

        while (child!=null && i.compareTo(child.data)!=0)
        {
            parent = child;
            if (i.compareTo(child.data)<0)
            {
                child = child.left;
                goneLeft = true;
            }
            else
            {
                child = child.right;
                goneLeft = false;
            }
        }

        if (child!=null)
        {
            return false;  // number already present
        }

        else
        {
            BTNode<Integer> leaf = new BTNode<Integer>(i);
            if (parent==null) // tree was empty
                root = leaf;
            else if (goneLeft)
                parent.left = leaf;
            else
                parent.right = leaf;
            return true;
        }
    }

    public int nonleaves()
    {
        BTNode<Integer> parent = root;

        if(parent == null)
        {
            return 0;
        }
        else
        {
            return checkNonLeaves(parent);
        }
    }

    public int depth()
    {
        BTNode<Integer> node = root;

        if (node == null)
        {
            return 0;
        }
        else
            {
                return getDepth(node);
            }
    }

    public int range(int min, int max)
    {
        BTNode<Integer> node = root;

        if(min > max)
        {
            throw new IllegalArgumentException("Minimum value is larger than maximum");
        }

        if (node == null)
        {
            return 0;
        }
        else
            {
                return checkInRange(node, min, max);
            }
    }

    public int checkNonLeaves(BTNode node)
    {
        if (node == null || (node.left == null && node.right == null))
        {
            return 0;
        }
        else
            {
                return checkNonLeaves (node.left) + checkNonLeaves(node.right) + 1;
            }
    }

    public int getDepth(BTNode node)
    {
        if (node == null)
        {
            return 0;
        }
        else
            {
                int leftDepth = getDepth(node.left);
                int rightDepth = getDepth(node.right);

                if (leftDepth > rightDepth)
                {
                    return (leftDepth + 1);
                }
                else
                    {
                        return (rightDepth + 1);
                    }
            }
    }

    public int checkInRange(BTNode<Integer> node, int min, int max)
    {
        if (node == null)
        {
            return 0;
        }
        else
            {
                if (node.data >= min && node.data <= max)
                {
                    return checkInRange(node.left, min, max) + checkInRange(node.right, min, max) + 1;
                }

                else if(node.data < min)
                {
                    return checkInRange(node.right, min, max);
                }

                else
                {
                    return checkInRange(node.left, min, max);
                }
            }
    }
}

class BTNode<T>
{
    T data;
    BTNode<T> left, right;

    BTNode(T o)
    {
        data = o; left = right = null;
    }
}