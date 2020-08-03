package com.anfaas.bigeater20;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ScoreAdapter extends ArrayAdapter<PlayerScore> {
    private static final String TAG = "scoreadapter";
    Activity context;
    List <PlayerScore> score;
    String current_player_name;

    public ScoreAdapter(@NonNull Activity context, List score,String current_player_name) {
        super(context, R.layout.scorelistview,score);
        this.context = context;
        this.score = score;
        this.current_player_name =current_player_name;
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
        Picasso.get().load(score1.getImageurl()).placeholder(R.drawable.ic_baseline_perm_contact_calendar_24).into(circleImageView);
        score.setText(String.valueOf(score1.getScore()));
        if (current_player_name.equals(score1.getName())){

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ColorStateList csl = new ColorStateList(new int[][]{{}}, new int[]{Color.GREEN});
                myView.findViewById(R.id.colorcard).setBackgroundTintList(csl);
            }

        }
        return  myView;
    }
}
