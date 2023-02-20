package com.whradam.sigmamall.entity.dto.request;

/**
 * 用于分页排序请求
 */

public class PaginationRequest {
    //用于按时间查询功能
    //和前端约定使用时间戳:55211021或字符串日期2000/02/02
    private String startTime;
    private String endTime;
    //用于分页查询
    private int currentPage = 1; //当前查询页
    private int pageSize = 3; //每页记录数
    //用于组合条件查询
    private String sort; //组合排序规则用; e.g. time,account
    private String sortType ="ASC"; //排序方式

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }
}
