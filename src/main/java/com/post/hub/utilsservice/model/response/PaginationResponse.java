package com.post.hub.utilsservice.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaginationResponse<T> implements Serializable {
    private List<T> content;
    private Pagination pagination;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Pagination implements Serializable {
        private long total;
        private int limit;
        private int page;
        private int pages;
    }
}
