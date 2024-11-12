package ru.job4j.collection.map;

import java.util.*;

public class NonCollisionMap<K, V> implements SimpleMap<K, V> {

    private static final float LOAD_FACTOR = 0.75f;

    private int capacity = 8;
    private int count = 0;
    private int modCount = 0;
    private MapEntry<K, V>[] table = new MapEntry[capacity];

    @Override
    public boolean put(K key, V value) {
        if (count == (capacity * LOAD_FACTOR)) {
            expand();
        }
        boolean result = false;
        int index = indexFor(Objects.hashCode(key));
        if (table[index] == null) {
            table[index] = new MapEntry<>(key, value);
            count++;
            modCount++;
            result = true;
        }
        return result;
    }

    @Override
    public V get(K key) {
        V result = null;
        int index = indexFor(Objects.hashCode(key));
        if (table[index] != null) {
            if (compareKey(key, table[index])) {
                result = table[index].value;
            }
        }
        return result;
    }

    @Override
    public boolean remove(K key) {
        boolean result = false;
        int index = indexFor(Objects.hashCode(key));
        if (table[index] != null) {
            if (compareKey(key, table[index])) {
                table[index] = null;
                count--;
                modCount++;
                result = true;
            }
        }
        return result;
    }

    @Override
    public Iterator<K> iterator() {
        return new Iterator<K>() {
            int pointer = 0;
            int expectedModCount = modCount;

            @Override
            public boolean hasNext() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                while (pointer < table.length && table[pointer] == null) {
                    pointer++;
                }
                return pointer < table.length;
            }

            @Override
            public K next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return table[pointer++].key;
            }
        };
    }

    private int hash(int hashCode) {
        return hashCode ^ (hashCode >>> 16);
    }

    private int indexFor(int hash) {
        return hash & (capacity - 1);
    }

    private void expand() {
        int newCapacity = capacity << 1;
        MapEntry<K, V>[] newTable = new MapEntry[newCapacity];
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null) {
                int newIndex = hash(Objects.hashCode(table[i].key)) & (newCapacity - 1);
                newTable[newIndex] = table[i];
            }
        }
        table = newTable;
        capacity = newCapacity;
    }

    private boolean compareKey(K key, MapEntry<K, V> element) {
        return key == null && (element.key == null)
                || Objects.hashCode(key) == Objects.hashCode(element.key)
                && Objects.equals(key, element.key);
    }

    private static class MapEntry<K, V> {
        K key;
        V value;

        public MapEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}
