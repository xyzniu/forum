package xyz.xyzniu.forum.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Pagination {
    
    private Integer currentPage;
    private Integer pageCount;
    private Integer size;
    private Integer offset;
    private List<Integer> pages;
    
    public void init(int currentPage, int size, int questionCount) {
        pageCount = questionCount % size == 0 ? questionCount / size : questionCount / size + 1;
        this.size = size;
        
        if (currentPage <= 0) {
            currentPage = 1;
        } else if (currentPage > pageCount) {
            currentPage = pageCount;
        }
        this.currentPage = currentPage;
        
        offset = (currentPage - 1) * size;
        
        pages = new ArrayList<>();
        
        for (int i = -3; i < 4 && currentPage + i <= pageCount; i++) {
            if (currentPage + i > 0) {
                pages.add(currentPage + i);
            }
        }
    }
}
