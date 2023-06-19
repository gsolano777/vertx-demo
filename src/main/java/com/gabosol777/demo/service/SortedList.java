package com.gabosol777.demo.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class SortedList<T extends Comparable<T>> {

    private List<T> sortedList = new ArrayList<>();

    public void add(T t) {
        this.sortedList.add(t);
        Collections.sort(sortedList);
    }
    public int findIndexOf(T t) {
        return Collections.binarySearch(sortedList, t);
    }

    public int size() {
        return sortedList.size();
    }

    public T get(int index) {
        return sortedList.get(index);
    }

    public Stream<T> stream() {
        return sortedList.stream();
    }

}
