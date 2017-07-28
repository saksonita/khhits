package khhits.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;




/**
 * Created by HP1 on 5/3/2017.
 */
@Entity
public class Page {
    private int id;
    private String pageId;
    private String pageTitle;

    public Page() {
    }

    public Page( String pageId, String pageTitle ) {
        this.pageId = pageId;
        this.pageTitle = pageTitle;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

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
