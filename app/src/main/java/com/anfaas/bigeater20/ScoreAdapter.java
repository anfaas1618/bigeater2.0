package com.anfaas.bigeater20;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

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
        TextView rank = myView.findViewById(R.id.rank_id);
        int rank_int= position+1;
        rank.setText(String.format(" %d ", rank_int));
        TextView name=myView.findViewById(R.id.nameTxt);
        TextView score=myView.findViewById(R.id.scoreTxt);
        CircleImageView circleImageView =myView.findViewById(R.id.list_image);
        PlayerScore score1=getItem(position);
        name.setText(score1.getName());
        //Uri imageUri=Uri.parse(score1.getImageurl());
        Picasso.get().load(score1.getImageurl()).into(circleImageView);
        score.setText(String.valueOf(score1.getScore()));
        return  myView;
    }
}
