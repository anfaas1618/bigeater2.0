package com.anfaas.bigeater20;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ScoreAdapter extends ArrayAdapter<PlayerScore> {
    Activity context;
    List <PlayerScore> score;

    public ScoreAdapter(@NonNull Activity context, List score) {
        super(context, R.layout.scorelistview,score);
        this.context = context;
        this.score = score;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View myView=inflater.inflate(R.layout.scorelistview,null,true);
        TextView name=myView.findViewById(R.id.nameTxt);
        TextView score=myView.findViewById(R.id.scoreTxt);
        PlayerScore score1=getItem(position);
        name.setText(score1.getName());
        score.setText(String.valueOf(score1.getScore()));
        return  myView;
    }
}
