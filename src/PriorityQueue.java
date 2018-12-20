import java.util.ArrayList;
import java.util.List;

public class PriorityQueue<K extends Comparable<K>, V> {
    private static class Element<K extends Comparable<K>, V> implements Comparable<Element<K, V>> {
        K key;
        V value;
        
        Element(K key, V value) {
            this.key = key;
            this.value = value;
        }
        
        @Override
        public int compareTo(Element<K, V> other) {
            return key.compareTo(other.key);
        }
    }
    
    private List<Element<K, V>> heap = new ArrayList<>();
    
    void insert(K key, V value) {
        Element<K, V> element = new Element<>(key, value);
        heap.add(element);
        
        pullUp(heap.size() - 1);
    }
    
    private void swap(int i, int j) {
        var temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }
    
    private void pullUp(int i) {
        int predecessor = (i - 1) / 2;
        if (predecessor >= 0 && heap.get(predecessor).compareTo(heap.get(i)) > 0) {
            swap(predecessor, i);
            pullUp(predecessor);
        }
    }
    
    V getMin() {
        return heap.get(0).value;
    }
    
    V extractMin() {
        var value = heap.get(0).value;
        
        heap.set(0, heap.remove(heap.size() - 1));
        
        pushDown(0);
        
        return value;
    }
    
    private void pushDown(int i) {
        int smallest = i;
        if (2 * i + 1 < heap.size() && heap.get(2 * i + 1).compareTo(heap.get(smallest)) < 0) {
            smallest = 2 * i + 1;
        }
        if (2 * i + 2 < heap.size() && heap.get(2 * i + 2).compareTo(heap.get(smallest)) < 0) {
            smallest = 2 * i + 2;
        }
        swap(i, smallest);
        
        if (i != smallest) {
            pushDown(smallest);
        }
    }
    
    void decreaseKey(int pos, K newKey) {
        var element = heap.get(pos);
        if (element.key.compareTo(newKey) <= 0) {
            throw new IllegalStateException("New key has to be smaller than current key");
        }
        element.key = newKey;
        pullUp(pos);
    }
    
}
