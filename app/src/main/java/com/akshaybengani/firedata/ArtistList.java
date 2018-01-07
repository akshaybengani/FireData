package com.akshaybengani.firedata;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by root on 07/01/18.
 */


public class ArtistList extends ArrayAdapter<Artist>{ //Array Adapter is for Artist

    private Activity context;
    private List<Artist> artistList; //This will store all the artist

    public ArtistList(Activity context,List<Artist> artistList)
    {
        super(context,R.layout.list_layout,artistList);
        this.context=context;
        this.artistList = artistList;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater=context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_layout,null,true);
        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textviewName);
        TextView textViewGenre = (TextView) listViewItem.findViewById(R.id.textviewGenre);

        Artist artist = artistList.get(position);
        textViewName.setText(artist.getArtistName());
        textViewGenre.setText(artist.getArtistGenre());
        return listViewItem;
    }

}
