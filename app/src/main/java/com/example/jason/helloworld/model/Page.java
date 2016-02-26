package com.example.jason.helloworld.model;

public class Page {
    private int number=0;
    private int size=10;
    private int numberOfElements;
    private int totalPages;
    private int totalElements;
    private boolean last;
    private boolean first;

    public Page(int number, int size, int numberOfElements, int totalPages, int totalElements, boolean last, boolean first) {
        this.number = number;
        this.size = size;
        this.numberOfElements = numberOfElements;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.last = last;
        this.first = first;
    }

    public Page() {
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }
}
