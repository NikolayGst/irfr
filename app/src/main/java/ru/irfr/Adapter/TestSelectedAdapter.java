package ru.irfr.Adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ru.irfr.Interface.OnTabSelectListener;
import ru.irfr.Model.Test.TestStatus;
import ru.irfr.R;

public class TestSelectedAdapter extends RecyclerView.Adapter<TestSelectedAdapter.ViewHolder> {

    private Context mContext;
    private List<TestStatus> mTestList;
    private LayoutInflater mLayoutInflater;
    private int selectedPos = 0;
    private OnTabSelectListener mOnTabSelectListener;

    public TestSelectedAdapter(Context context) {
        mContext = context;
    }

    public void setTestStatusList(List<TestStatus> testList) {
        mTestList = testList;
    }

    public void setOnTabSelectListener(OnTabSelectListener onTabSelectListener) {
        mOnTabSelectListener = onTabSelectListener;
    }

    public void updateIndicator(int position, int selectAnswer, int correctAnswer){
        TestStatus test = mTestList.get(position);
        if (selectAnswer == correctAnswer) test.setTrueAnswer(true);
        test.setSelectedAnswer(selectAnswer);
        test.setSelectableAnswer(1);
        mTestList.set(position, test);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtIndicator;
        ImageView imgIndicator;

        public ViewHolder(View itemView) {
            super(itemView);
            txtIndicator = (TextView) itemView.findViewById(R.id.txtIndicator);
            imgIndicator = (ImageView) itemView.findViewById(R.id.imgIndicator);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    notifyItem(getLayoutPosition());
                    if (mOnTabSelectListener != null) mOnTabSelectListener
                            .onTabSelected(getLayoutPosition());
                }
            });
        }
    }

    public void notifyItem(int pos) {
        notifyItemChanged(selectedPos);
        selectedPos = pos;
        notifyItemChanged(selectedPos);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mLayoutInflater = LayoutInflater.from(mContext);
        View view = mLayoutInflater.inflate(R.layout.item_indicator_test, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TestStatus test = mTestList.get(position);
        holder.txtIndicator.setText(Integer.toString(position + 1));
        setStatus(holder, test);

        if (selectedPos == position) {
            holder.txtIndicator.setBackgroundResource(R.drawable.circle_blue);
            holder.txtIndicator.setTextColor(ContextCompat.getColor(mContext, R.color.white));
        } else {
            holder.txtIndicator.setBackgroundColor(ContextCompat.getColor(mContext, android.R.color.transparent));
            holder.txtIndicator.setTextColor(ContextCompat.getColor(mContext, R.color.blue));
        }
    }

    private void setStatus(ViewHolder viewHolder, TestStatus test) {
        if (test.isTrueAnswer()) {
            viewHolder.imgIndicator.setImageResource(R.drawable.dot_green);
        } else if (test.isSelectableAnswer() != 1) {
            viewHolder.imgIndicator.setImageResource(R.drawable.dot_gray);
        } else {
            viewHolder.imgIndicator.setImageResource(R.drawable.dot_red);
        }
    }
    @Override
    public int getItemCount() {
        if (mTestList != null) return mTestList.size();
        else return 0;
    }

    public int getSelectedPos() {
        return selectedPos;
    }
}
