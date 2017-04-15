package ru.irfr.Adapter;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import ru.irfr.Interface.OnClickAdapterListener;
import ru.irfr.Model.DB.Question;
import ru.irfr.Model.DB.Result;
import ru.irfr.R;

public class TestErrorAdapter extends RecyclerView.Adapter<TestErrorAdapter.ViewHolder> {
    private final Context context;
    private RealmResults<Result> items;
    private OnClickAdapterListener<Result> mOnClickAdapterListener;
    private LayoutInflater mLayoutInflater;
    private Realm mRealm;

    public TestErrorAdapter(Context context) {
        this.context = context;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mRealm = Realm.getDefaultInstance();
    }

    public void setItems(RealmResults<Result> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public void setOnClickAdapterListener(OnClickAdapterListener<Result> onClickAdapterListener) {
        mOnClickAdapterListener = onClickAdapterListener;
    }

    @Override
    public TestErrorAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_result_test, parent, false);
        return new TestErrorAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final TestErrorAdapter.ViewHolder holder, final int position) {
        final Result item = items.get(position);
        holder.txtNameExam.setText(item.getTitle());
        holder.txtPoint.setText("Количество баллов: " + item.getPoint());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy 'в' HH:mm");
        holder.txtDate.setText("Дата: " + dateFormat.format(item.getDate()));
        holder.txtMode.setText("Режим: Экзамен");
        String result;
        if (calcPoint(item.getQuestions()) >= 80) result = "Сдал";
        else result = "Не сдал";
        holder.txtResult.setText("Результат: " + result);
        holder.rltError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickAdapterListener != null) mOnClickAdapterListener
                        .onClick(items.get(holder.getAdapterPosition()));
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeDWarningDialog(item.getId(), position);
            }
        });
    }

    private void makeDWarningDialog(final int id, final int pos) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = mLayoutInflater.inflate(R.layout.dialog_delete, null);
        Button yes = (Button) view.findViewById(R.id.yesButton);
        Button no = (Button) view.findViewById(R.id.noButton);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               mRealm.beginTransaction();
                Result result = items.where().equalTo("id", id).findFirst();
                result.deleteFromRealm();
                notifyItemRemoved(pos);
                dialog.dismiss();
                mRealm.commitTransaction();
            }
        });
        dialog.show();
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private int calcPoint(RealmList<Question> questions) {
        int point = 0;
        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);
            if (q.getCorrectnum() == q.getTestSelectedAnswer() && q.isSelectableAnswer() == 1) {
                point += questions.get(i).getWeight();
            }
        }
        return point;
    }

    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        }
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNameExam;
        TextView txtPoint;
        TextView txtDate;
        TextView txtMode;
        RelativeLayout rltError;
        TextView txtResult;
        ImageView delete;

        public ViewHolder(View itemView) {
            super(itemView);
            txtNameExam = (TextView) itemView.findViewById(R.id.txtNameExam);
            txtPoint = (TextView) itemView.findViewById(R.id.txtPoint);
            txtDate = (TextView) itemView.findViewById(R.id.txtDate);
            txtMode = (TextView) itemView.findViewById(R.id.txtMode);
            rltError = (RelativeLayout) itemView.findViewById(R.id.rltError);
            txtResult = (TextView) itemView.findViewById(R.id.txtResult);
            delete = (ImageView) itemView.findViewById(R.id.delete);
        }
    }
}