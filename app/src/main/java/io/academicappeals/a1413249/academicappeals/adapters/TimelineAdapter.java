package io.academicappeals.a1413249.academicappeals.adapters;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.academicappeals.a1413249.academicappeals.R;
import io.academicappeals.a1413249.academicappeals.activities.EventActivity;
import io.academicappeals.a1413249.academicappeals.models.Timeline;
import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;
import io.realm.RealmResults;

public class TimelineAdapter extends RealmRecyclerViewAdapter<Timeline,
        TimelineAdapter.MyViewHolder> {

    public TimelineAdapter(RealmResults<Timeline> data) {
        super(data, true, true);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.base_row,
                        parent,
                        false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Timeline data = getData().get(position);
        holder.data = data;
        holder.timelineName.setText(data.getTimelineName());
        holder.timelineId = data.getTimelineId();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView timelineName;
        int timelineId;

        public Timeline data;

        MyViewHolder(View view) {
            super(view);
            cardView = itemView.findViewById(R.id.card_view);
            timelineName = view.findViewById(R.id.name);

            cardView.setOnClickListener((v) -> {
                Intent intent = new Intent(v.getContext(), EventActivity.class);
                intent.putExtra("timelineIdExtra", timelineId);
                intent.putExtra("timelineNameExtra", timelineName.getText().toString());
//                intent.setFlags(Intent.);
                v.getContext().startActivity(intent);
            });
        }

    }

    @Nullable
    @Override
    public OrderedRealmCollection<Timeline> getData() {
        return super.getData();
    }

}
