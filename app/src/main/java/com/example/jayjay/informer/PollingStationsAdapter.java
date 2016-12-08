package com.example.jayjay.informer;

/**
 * Created by JayJay on 12/6/2016.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by tmend on 12/3/2016.
 */

public class PollingStationsAdapter extends RecyclerView.Adapter<PollingStationsAdapter.PollingStationsHolder> {

    ArrayList<Stations> filteredPollingStations;
    Context mContext;

    public PollingStationsAdapter(Context context, ArrayList<Stations> pollingStations) {
        this.filteredPollingStations = pollingStations;
        this.mContext = context;
    }


    @Override
    public PollingStationsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.polling_station_card, parent, false);

        PollingStationsHolder pollingHolder = new PollingStationsHolder(view);
        return pollingHolder;
    }

    @Override
    public void onBindViewHolder(PollingStationsAdapter.PollingStationsHolder holder, int position) {
        Stations indexedStations = filteredPollingStations.get(position);

        holder.pollingStationNames.setText(" " + indexedStations.getPolling_station_name());

    }

    @Override
    public int getItemCount() {

        return filteredPollingStations.size();
    }

    public class PollingStationsHolder extends RecyclerView.ViewHolder {
        public TextView pollingStationNames;
        public TextView psTitle;

        public PollingStationsHolder(View itemView) {
            super(itemView);
            psTitle = (TextView) itemView.findViewById(R.id.ps_title);
            pollingStationNames = (TextView) itemView.findViewById(R.id.pollingstation_name);
        }
    }
}
