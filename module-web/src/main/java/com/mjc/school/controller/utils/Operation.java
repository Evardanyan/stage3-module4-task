package com.mjc.school.controller.utils;

public enum Operation {
    GET_ALL_NEWS(1, "Get all news."),
    GET_NEWS_BY_ID(2, "Get news by id."),
    CREATE_NEWS(3, "Create news."),
    UPDATE_NEWS(4, "Update news."),
    REMOVE_NEWS_BY_ID(5, "Remove news by id."),
    GET_ALL_AUTHOR(6, "Get all authors."),
    GET_AUTHOR_BY_ID(7, "Get authors by id."),
    CREATE_AUTHOR(8, "Create authors."),
    UPDATE_AUTHOR(9, "Update authors."),
    REMOVE_AUTHOR_BY_ID(10, "Remove authors by id."),


    GET_ALL_TAG(11, "Get all tags."),
    GET_TAG_BY_ID(12, "Get tags by id."),
    CREATE_TAG(13, "Create tags."),
    UPDATE_TAG(14, "Update tags."),
    REMOVE_TAG_BY_ID(15, "Remove tags by id."),

    GET_TAG_BY_NEWS_ID(16, "Get tags by news id."),
    GET_AUTHOR_BY_NEWS_ID(17, "Get author by news id."),

    GET_NEWS_BY_PARAMS(18, "Get News by tag names, tag ids, author name, title, content (all params are optional and can be used in conjunction)."),

    EXIT(0, "Exit.");

    public final Integer operationNum;
    public final String ops;


    private Operation(Integer operationNum, String ops) {
        this.operationNum = operationNum;
        this.ops = ops;
    }

    public Integer getOperationNum() {
        return operationNum;
    }

    public String getOps() {
        return ops;
    }

    public String getOperationWithNumber() {
        return this.operationNum + " - " + this.ops;
    }
}
