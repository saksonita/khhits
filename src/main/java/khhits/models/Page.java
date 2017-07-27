package khhits.models;

import jdk.nashorn.internal.ir.annotations.Immutable;

/**
 * Created by HP1 on 5/3/2017.
 */

public class Page {
    private int id;
    private String pageId;
    private String pageTitle;

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public String getPageId() {
        return pageId;
    }

    public void setPageId( String pageId ) {
        this.pageId = pageId;
    }

    public String getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle( String pageTitle ) {
        this.pageTitle = pageTitle;
    }
}
