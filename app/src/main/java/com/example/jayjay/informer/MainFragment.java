package com.example.jayjay.informer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.*;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.util.Log;

import java.util.ArrayList;
import java.net.URL;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;

import org.xml.sax.XMLReader;

import android.content.Intent;

import com.example.jayjay.informer.ShowDescription;

/**
 * Created by JayJay on 20/09/2016.
 */
//public class MainFragment extends Fragment implements OnItemClickListener{
//
//    public final String RSSFEEDOFCHOICE = "http://www.standardmedia.co.ke/rss/politics.php";
//
//    public final String tag = "RSSReader";
//    private RSSFeed feed = null;
//
//    @Nullable
//    @Override
//
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//
//        View rootView = inflater.inflate(R.layout.fragment_main,container,false);
//
//        // go get our feed!
//        feed = getFeed(RSSFEEDOFCHOICE);
//
//        // display UI
//        UpdateDisplay();
//
//        return rootView;
//    }
//
//    private RSSFeed getFeed(String urlToRssFeed)
//    {
//        try
//        {
//            // setup the url
//            URL url = new URL(urlToRssFeed);
//
//            // create the factory
//            SAXParserFactory factory = SAXParserFactory.newInstance();
//            // create a parser
//            SAXParser parser = factory.newSAXParser();
//
//            // create the reader (scanner)
//            XMLReader xmlreader = parser.getXMLReader();
//            // instantiate our handler
//            RSSHandler theRssHandler = new RSSHandler();
//            // assign our handler
//            xmlreader.setContentHandler(theRssHandler);
//            // get our data via the url class
//            InputSource is = new InputSource(url.openStream());
//            // perform the synchronous parse
//            xmlreader.parse(is);
//            // get the results - should be a fully populated RSSFeed instance, or null on error
//            return theRssHandler.getFeed();
//        }
//        catch (Exception ee)
//        {
//            // if we have a problem, simply return null
//            return null;
//        }
//    }
////    public boolean onCreateOptionsMenu(Menu menu)
////    {
////        super.onCreateOptionsMenu(menu);
////
//////        menu.add(0,0,"Choose RSS Feed");
//////        menu.add(0,1,"Refresh");
////        Log.i(tag,"onCreateOptionsMenu");
////        return true;
////    }
//
////    public boolean onOptionsItemSelected(MenuItem item){
//////        switch (item.getId()) {
//////            case 0:
//////
//////                Log.i(tag,"Set RSS Feed");
//////                return true;
//////            case 1:
//////                Log.i(tag,"Refreshing RSS Feed");
//////                return true;
//////        }
//////        return false;
////    }
//
//
//    private void UpdateDisplay()
//    {
//        TextView feedtitle = (TextView)getActivity().findViewById(R.id.feedtitle);
//        TextView feedpubdate = (TextView)getActivity().findViewById(R.id.feedpubdate);
//        ListView itemlist = (ListView)getActivity().findViewById(R.id.itemlist);
//
//
//        if (feed == null)
//        {
//            feedtitle.setText("No RSS Feed Available");
//            return;
//        }
//
//        feedtitle.setText(feed.getTitle());
//        feedpubdate.setText(feed.getPubDate());
//
//        ArrayAdapter<RSSItem> adapter = new ArrayAdapter<RSSItem>(getActivity(),android.R.layout.simple_list_item_1,feed.getAllItems());
//
//        itemlist.setAdapter(adapter);
//
//        itemlist.setOnItemClickListener(this);
//
//        itemlist.setSelection(0);
//
//    }
//
//
//    public void onItemClick(AdapterView parent, View v, int position, long id)
//    {
//        Log.i(tag,"item clicked! [" + feed.getItem(position).getTitle() + "]");
//
//        Intent itemintent = new Intent(getActivity(),ShowDescription.class);
//
//        Bundle b = new Bundle();
//        b.putString("title", feed.getItem(position).getTitle());
//        b.putString("description", feed.getItem(position).getDescription());
//        b.putString("link", feed.getItem(position).getLink());
//        b.putString("pubDate", feed.getItem(position).getPubDate());
//
//        itemintent.putExtra("android.intent.extra.INTENT", b);
//
//        startActivityForResult(itemintent,0);
//    }
public class MainFragment extends Fragment {
    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.voter_education_main, container, false);

        return rootView;
    }
}
