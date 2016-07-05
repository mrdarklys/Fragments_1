package com.palamarchuk.vlad.fragmenttask;

import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by User on 25.06.2016.
 */
public class TeamsFragment extends ListFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyListAdapter adapter = new MyListAdapter(getActivity(),R.layout.list_frag_item,Teams.mTeamNameArray);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        DetailsFragment viewer = (DetailsFragment)getFragmentManager().findFragmentById(R.id.viewers);
        if(viewer==null||!viewer.isInLayout()){
            Intent launchIntent = new Intent(getActivity(),DetailsActivity.class);
            launchIntent.putExtra("teamPos",position);
            startActivity(launchIntent);
        }else {
            viewer.updateTeamDescr(position);
        }
    }

    public class MyListAdapter extends ArrayAdapter<String> {

        private Context mContext;

        public MyListAdapter(Context context, int textViewResourceId,
                             String[] objects) {
            super(context, textViewResourceId, objects);
            mContext = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.list_frag_item, parent,
                    false);
            TextView teamNameTextView = (TextView) row.findViewById(R.id.frag_textView);

            ImageView iconImageView = (ImageView) row.findViewById(R.id.frag_imageView);
            teamNameTextView.setText(Teams.mTeamNameArray[position]);
            iconImageView.setImageResource(Teams.mTeamLogoArray[position]);
            return row;
        }
    }
}
