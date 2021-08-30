package com.jackzhao.easyrecyclerview.data;

public class SimpleTextData {
    String title;
    String Content;

    public SimpleTextData(String title, String content) {
        this.title = title;
        Content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
