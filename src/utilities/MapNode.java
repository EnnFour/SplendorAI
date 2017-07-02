package utilities;

public class MapNode<K, V> {

	private K key;
	private V value;
	
	public MapNode(K key, V value) {
		this.key = key;
		this.value = value;
	}
	
	public K getKey() {
		return key;
	}
	
	public V getValue() {
		return value;
	}
	
}
