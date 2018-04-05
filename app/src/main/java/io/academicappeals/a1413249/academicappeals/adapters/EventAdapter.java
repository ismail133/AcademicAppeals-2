package io.academicappeals.a1413249.academicappeals.adapters;

import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.vipulasri.timelineview.TimelineView;

import java.text.SimpleDateFormat;

import io.academicappeals.a1413249.academicappeals.R;
import io.academicappeals.a1413249.academicappeals.models.Event;
import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;
import io.realm.RealmResults;

public class EventAdapter extends RealmRecyclerViewAdapter<Event,
        EventAdapter.MyViewHolder> {

    public EventAdapter(RealmResults<Event> data) {
        super(data, true, true);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.event_row,
                        parent,
                        false), viewType);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Event data = getData().get(position);
        holder.data = data;
        holder.timelineName.setText(data.getEventName());
        holder.timelineDesc.setText(data.getEventDesc());
        holder.timelineDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(data.getEventDate()));
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView timelineName;
        TextView timelineDesc;
        TextView timelineDate;

        public Event data;
        public TimelineView timelineView;

        MyViewHolder(View view, int viewType) {
            super(view);
            timelineView = itemView.findViewById(R.id.time_marker);
            timelineView.initLine(viewType);

            cardView = itemView.findViewById(R.id.card_view_event);
            timelineName = view.findViewById(R.id.event_name);
            timelineDesc = view.findViewById(R.id.event_desc);
            timelineDate = view.findViewById(R.id.event_date);
        }

    }

    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position, getItemCount());
    }

    @Nullable
    @Override
    public OrderedRealmCollection<Event> getData() {
        return super.getData();
    }

}
