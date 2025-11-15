package iterators;

import java.util.List;
import java.util.Iterator;

public class ExtendedIteratorImpl<T> implements ExtendedIterator<T> {
    private List<T> list;
    private int position = 0;
    public ExtendedIteratorImpl(List<T> list) {
        this.list = list;
    }
    @Override
    public boolean hasNext() {
        return position < list.size();
    }
    @Override
    public T next() {
        return list.get(position++);
    }
    @Override
    public boolean hasPrevious() {
        return position > 0;
    }
    @Override
    public T previous() {
        return list.get(--position);
    }
    @Override
    public void goFirst() {
        position = 0;
    }
    @Override
    public void goLast() {
        position = list.size();
    }
}
