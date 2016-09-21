package com.example.jayjay.informer;

import java.util.List;
import java.util.Vector;

import com.example.jayjay.informer.RSSItem;

/**
 * Created by JayJay on 9/21/2016.
 */
public class RSSFeed {
    private String _title = null;
    private String _pubdate = null;
    private int _itemcount = 0;
    private List<RSSItem> _itemlist;


    RSSFeed() {
        _itemlist = new Vector(0);
    }

    int addItem(RSSItem item) {
        _itemlist.add(item);
        _itemcount++;
        return _itemcount;
    }

    RSSItem getItem(int location) {
        return _itemlist.get(location);
    }

    List getAllItems() {
        return _itemlist;
    }

    int getItemCount() {
        return _itemcount;
    }

    void setTitle(String title) {
        _title = title;
    }

    void setPubDate(String pubDate) {
        _pubdate = pubDate;
    }

    String getTitle() {
        return _title;
    }

    String getPubDate() {
        return _pubdate;
    }
}
