package ru.irfr.Adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.List;
import ru.irfr.Interface.OnTabSelectListener;
import ru.irfr.R;

public class ExamSelectedAdapter extends RecyclerView.Adapter<ExamSelectedAdapter.ViewHolder> {

    private List<String> mStringList;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private OnTabSelectListener mOnTabSelectListener;
    private int selectedPos = 0;

    public ExamSelectedAdapter(Context context) {
        mContext = context;
    }

    public void setStatusList(List<String> stringList) {
        mStringList = stringList;
        notifyDataSetChanged();
    }

    public void setOnTabSelectListener(OnTabSelectListener onTabSelectListener) {
        mOnTabSelectListener = onTabSelectListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mLayoutInflater = LayoutInflater.from(mContext);
        View view = mLayoutInflater.inflate(R.layout.item_indecator_exam, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.txtIndicator.setText(mStringList.get(position));
        holder.txtIndicator.setTextColor(ContextCompat.getColor(mContext, R.color.blue));
        if (selectedPos == position) {
            holder.lrIndicator.setBackgroundResource(R.drawable.circle_blue);
            holder.txtIndicator.setTextColor(ContextCompat.getColor(mContext, R.color.white));
        } else {
            holder.lrIndicator.setBackgroundColor(ContextCompat.getColor(mContext, android.R.color.transparent));
            holder.txtIndicator.setTextColor(ContextCompat.getColor(mContext, R.color.blue));
        }
    }

    @Override
    public int getItemCount() {
        return mStringList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtIndicator;
        private LinearLayout lrIndicator;

        public ViewHolder(View itemView) {
            super(itemView);
            txtIndicator = (TextView) itemView.findViewById(R.id.txtIndicator);
            lrIndicator = (LinearLayout) itemView.findViewById(R.id.lrIndicator);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnTabSelectListener != null) mOnTabSelectListener
                            .onTabSelected(getLayoutPosition());
                    notifyItem(getLayoutPosition());
                }
            });
        }
    }

    public void notifyItem(int pos) {
        notifyItemChanged(selectedPos);
        selectedPos = pos;
        notifyItemChanged(selectedPos);
    }
}
