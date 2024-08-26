package com.aiia.gpt_be.history.vo;

import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Getter
public class PageIndex {

    private final int startPageNumber;
    private final int endPageNumber;

    public PageIndex(Page pages, Pageable pageable, int blockLimit) {
        int currentPageNumber = pageable.getPageNumber();
        int totalPageCount = pages.getTotalPages();

        this.startPageNumber = getStartPageNumber(currentPageNumber, totalPageCount, blockLimit);
        this.endPageNumber = getEndPageNumber(currentPageNumber, totalPageCount, blockLimit);
    }

    private int getStartPageNumber(int currentPageNumber, int totalPageCount, int blockLimit){
        if (doesTotalPageEmpty(totalPageCount) || doesInitialPage(currentPageNumber)) return 0;
        else return currentPageNumber - blockLimit;
    }

    private int getEndPageNumber(int currentPageNumber, int totalPageCount, int blockLimit) {
        if (doesTotalPageEmpty(totalPageCount)) return 0;
        if (doesLastPage(currentPageNumber, totalPageCount)) return totalPageCount - blockLimit;
        else return currentPageNumber + blockLimit;
    }

    private boolean doesTotalPageEmpty(int totalPageCount) {
        return totalPageCount == 0;
    }

    private boolean doesInitialPage(int currentPageNumber) {
        return currentPageNumber == 0;
    }

    private boolean doesLastPage(int currentPageNumber, int totalPageCount) {
        return currentPageNumber == totalPageCount - 1;
    }
}
