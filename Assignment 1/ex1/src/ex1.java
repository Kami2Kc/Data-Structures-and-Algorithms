public class ex1<T> implements Deque<T>
{
    private T[] dequeue;

    private int leftPos;
    private int rightPos;

    ex1()
    {
        dequeue = (T[]) new Object[20];
        leftPos = 0;
        rightPos = 0;
    }

    public boolean isEmpty()
    {
        return leftPos == rightPos;
    }

    public void addLeft(T x)
    {
        if (leftPos == 0)
        {
            for(int i = rightPos; i >= leftPos; i--)
            {
                dequeue[i+1] = dequeue[i];
            }
            rightPos++;
        }
        else
            {
                leftPos--;
            }

        dequeue[leftPos] = x;

        if (rightPos + 1 == dequeue.length)
        {
            expand();
        }
    }

    public void addRight(T x)
    {
        if (leftPos != 0)
        {
            for (int i = leftPos; i <= rightPos; i++)
            {
                dequeue[i - 1] = dequeue[i];
            }
            leftPos--;
        }
        dequeue[rightPos] = x;
        rightPos++;

        if(rightPos + 1 == dequeue.length)
        {
            expand();
        }
    }

    public T left() throws DequeueException
    {
        if (leftPos != rightPos)
        {
            return dequeue[leftPos];
        }
        else
            {
                throw new DequeueException("left");
            }
    }

    public T right() throws DequeueException
    {
        if (leftPos != rightPos)
        {
            return dequeue[rightPos];
        }
        else
        {
            throw new DequeueException("right");
        }
    }

    public void removeLeft() throws DequeueException
    {
        if (leftPos != rightPos)
        {
            leftPos++;
        }
        else
            {
                throw new DequeueException("removeLeft");
            }
    }

    public void removeRight() throws DequeueException
    {
        if (leftPos != rightPos)
        {
            rightPos--;
        }
        else
        {
            throw new DequeueException("removeRight");
        }
    }

    public String toString()
    {
        if (isEmpty())
        {
            return "<>";
        }
        else
            {
                StringBuilder sb = new StringBuilder();

                sb.append('<');

                int position = leftPos;

                while (position != rightPos)
                {
                    sb.append(dequeue[position]);
                    sb.append(',');
                    position ++;
                }

                sb.append('>');
                return sb.toString();
            }
    }

    public void expand()
    {
        T[] newDequeue = (T[]) new Object[dequeue.length * 2];
        int i;
        for(i = leftPos; i <= dequeue.length; i ++)
        {
            newDequeue[i] = dequeue[i];
        }
        dequeue = newDequeue;
        leftPos = 0;
        rightPos = i-1;
    }
}