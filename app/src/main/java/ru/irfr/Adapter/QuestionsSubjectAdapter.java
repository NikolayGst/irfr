package ru.irfr.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import ru.irfr.Interface.OnClickAdapterListener;
import ru.irfr.Model.Test.QuestionsSubject;
import ru.irfr.R;

public class QuestionsSubjectAdapter extends RecyclerView.Adapter<QuestionsSubjectAdapter.ViewHolder> {

    private final Context context;
    private List<QuestionsSubject> items;
    private OnClickAdapterListener<QuestionsSubject> mOnClickAdapterListener;

    public QuestionsSubjectAdapter(List<QuestionsSubject> items, Context context) {
        this.items = items;
        this.context = context;
    }

    public void setOnClickAdapterListener(OnClickAdapterListener<QuestionsSubject> onClickAdapterListener) {
        mOnClickAdapterListener = onClickAdapterListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context)
                .inflate(R.layout.item_questions_subject, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final QuestionsSubject item = items.get(position);
        holder.txtNameSubject.setText(item.getName());
        holder.rltSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnClickAdapterListener != null) mOnClickAdapterListener
                        .onClick(items.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        }
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtNameSubject;
        RelativeLayout rltSubject;
        public ViewHolder(View itemView) {
            super(itemView);
            txtNameSubject = (TextView) itemView.findViewById(R.id.txtNameSubject);
            rltSubject = (RelativeLayout) itemView.findViewById(R.id.rltSubject);
        }
    }
}