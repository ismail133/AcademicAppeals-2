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
import io.academicappeals.a1413249.academicappeals.activities.TimelineActivity;
import io.academicappeals.a1413249.academicappeals.models.Department;
import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;
import io.realm.RealmResults;

public class DepartmentAdapter extends RealmRecyclerViewAdapter<Department,
        DepartmentAdapter.MyViewHolder> {

    public DepartmentAdapter(RealmResults<Department> data) {
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
        Department data = getData().get(position);
        holder.data = data;
        holder.departmentName.setText(data.getDepartmentName());
        holder.departmentId = data.getDepartmentId();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView departmentName;
        int departmentId = 0;

        public Department data;

        MyViewHolder(View view) {
            super(view);
            cardView = itemView.findViewById(R.id.card_view);
            departmentName = view.findViewById(R.id.name);

            cardView.setOnClickListener((v) -> {
                Intent intent = new Intent(v.getContext(), TimelineActivity.class);
                intent.putExtra("departmentIdExtra", departmentId);
                intent.putExtra("departmentNameExtra", departmentName.getText().toString());
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                v.getContext().startActivity(intent);
            });
        }

    }

    @Nullable
    @Override
    public OrderedRealmCollection<Department> getData() {
        return super.getData();
    }

}
