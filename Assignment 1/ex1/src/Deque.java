public interface Deque<T>
{
    public boolean isEmpty();
    public void addLeft(T x);
    public void addRight(T x);
    public void removeLeft() throws DequeueException;
    public void removeRight() throws DequeueException;
    public T left() throws DequeueException;
    public T right() throws DequeueException;
}

class DequeueException extends RuntimeException
{
    public DequeueException(String msg)
    {
        super("Tried to apply '" + msg + "' to empty dequeue.");
    }
}