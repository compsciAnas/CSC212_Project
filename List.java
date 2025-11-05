package projectFiles;

public interface List<T> {
    boolean empty();
    boolean last();
    void findFirst();
    void findNext();
    T retrieve();
    void update(T e);
    void insert(T e);
    void remove();
}