package cn.linshenkx.halosyncserver.model;

import lombok.Data;

import java.util.List;

@Data
public class PageObject2<T> {
    private int page;
    private int size;
    private int total;
    private List<T> items;
    private boolean first;
    private boolean last;
    private boolean hasNext;
    private boolean hasPrevious;
    private int totalPages;
}
