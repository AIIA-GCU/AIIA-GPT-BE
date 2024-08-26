package com.aiia.gpt_be.history.vo;

import com.aiia.gpt_be.history.dto.HistoryMetaInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PageIndexTest {

    @DisplayName("현재 페이지 번호에 따라 페이지 범위를 구할 수 있다.")
    @CsvSource(value = {
            // totalPageCount = 15 / 5 = 3 -> 3
                        "0, 15, 0, 1",
                        "1, 15, 0, 2",
                        "2, 15, 1, 2",

            // totalPageCount = 16 / 5 = 3 ... 1 -> 4 (나머지 있으면 몫 + 1)
                        "0, 16, 0, 1",
                        "1, 16, 0, 2",
                        "2, 16, 1, 3",
                        "3, 16, 2, 3",

            // totalPageCount = 20 / 5 = 4 -> 4
                        "0, 20, 0, 1",
                        "1, 20, 0, 2",
                        "2, 20, 1, 3",
                        "3, 20, 2, 3",

            // totalPageCount = 21 / 5 = 4...1 -> 5 (나머지 있으면 몫 + 1)
                        "0, 21, 0, 1",
                        "1, 21, 0, 2",
                        "2, 21, 1, 3",
                        "3, 21, 2, 4",
                        "4, 21, 3, 4"
    })
    @ParameterizedTest(name = "currentPageNumber = {0}, totalPageCount = {1}, startPageNumber = {2}, endPageNumber = {3}")
    void pageIndex(int currentPageNumber, int totalPageCount, int startPageNumber, int endPageNumber) {

        // given
        int pageSize = 5;
        int blockLimit = 1;

        Pageable pageable = PageRequest.of(currentPageNumber, pageSize);

        Page<HistoryMetaInfo> pages = new PageImpl<>(List.of(HistoryMetaInfo.builder().build()),
                pageable, totalPageCount);

        // when
        PageIndex pageIndex = new PageIndex(pages, pageable, blockLimit);

        // then
        assertThat(pageIndex.getStartPageNumber()).isEqualTo(startPageNumber);
        assertThat(pageIndex.getEndPageNumber()).isEqualTo(endPageNumber);
    }

    @DisplayName("페이징한 내용이 아무것도 없을 수 있다.")
    @Test
    void pageIndex_AllPagesAreEmpty(){
        // given
        int pageSize = 5;
        int blockLimit = 1;

        int currentPageNumber = 0;
        int totalPageCount = 0;

        Pageable pageable = PageRequest.of(currentPageNumber, pageSize);

        Page<HistoryMetaInfo> pages = new PageImpl<>(List.of(HistoryMetaInfo.builder().build()),
                pageable, totalPageCount);

        // when
        PageIndex pageIndex = new PageIndex(pages, pageable, blockLimit);

        // then
        assertThat(pageIndex.getStartPageNumber()).isZero();
        assertThat(pageIndex.getEndPageNumber()).isZero();
    }
}