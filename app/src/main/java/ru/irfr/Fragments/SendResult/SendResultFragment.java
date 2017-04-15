package ru.irfr.Fragments.SendResult;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import io.realm.Realm;
import java.util.List;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import ru.irfr.Base.BaseFragment;
import ru.irfr.Model.DB.Result;
import ru.irfr.Model.DB.Share;
import ru.irfr.R;

@EFragment(R.layout.fragment_send_result)
public class SendResultFragment extends BaseFragment {

    @ViewById
    TextView txtCountPoint;

    @ViewById
    TextView txtYourDiscount;

    @ViewById
    EditText txtFio;

    @ViewById
    EditText txtTel;

    @ViewById
    EditText txtEmail;

    @ViewById
    TextView txtPrice;

    private static final String TAG = "Result";
    private int point;
    private int price;
    private long imei;
    private Realm mRealm;
    private TelephonyManager mTelephonyManager;
    boolean isPermissonOk;
    private Share mShare;
    private int socPoint;

    private void getImei() {
        Dexter.checkPermission(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse response) {
                mTelephonyManager = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
                imei = Long.parseLong(mTelephonyManager.getDeviceId());
                isPermissonOk = true;
                sendData();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse response) {
                isPermissonOk = false;
                Toast.makeText(getContext(), "Access denied", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                token.continuePermissionRequest();
            }
        }, Manifest.permission.READ_PHONE_STATE);

    }


    @AfterViews
    void init() {

        initRealm();

        //     point = getPoint() + socPoint;

        point = socPoint;

        txtCountPoint.setVisibility(View.GONE);


        price = point + getPriceFromExamResult();
        //price = point * 10;

        if (price >= 100) price = 100;


        txtYourDiscount.setText(String.format(getString(R.string.your_discount), price));
        txtPrice.setText(String.format(getString(R.string.rub), price));
    }

    private void initRealm() {
        mRealm = Realm.getDefaultInstance();
        mShare = mRealm.where(Share.class).findFirst();
        socPoint = mShare == null ? 0 : mShare.getSocialPoint();
    }

    /*private void getResult() {
        List<Result> resultList = mRealm.where(Result.class).findAll();
        Log.d(TAG, "*//***************************** getResult: *****************************//*");
        for (Result result :
                resultList) {
            Log.d(TAG, "id: " + result.getId());
            Log.d(TAG, "title: " + result.getTitle());
            Log.d(TAG, "point: " + result.getPoint());
            Log.d(TAG, "type: " + result.getType());
            Log.d(TAG, "duration: " + result.getDuration());
            Log.d(TAG, "date: " + result.getDate());
        }
        Log.d(TAG, "*//***************************** getResult: *****************************//*");

    }*/

    private int getPriceFromExamResult() {
        int price = 0;
        List<Result> resultList = mRealm.where(Result.class).findAll();
        for (Result result :
                resultList) {
            if (result.getPoint() >= 80) price += 10;
        }
        return price;
    }

    @Click(R.id.getDiscount)
    void getDiscount() {
        if (!isPermissonOk) {
            getImei();
        } else {
            sendData();
            // Log.d(TAG, "imei: " + mTelephonyManager.getDeviceId());
            //  Toast.makeText(getContext(), "imei " + mTelephonyManager.getDeviceId(), Toast.LENGTH_LONG).show();
        }
    }

    private void sendData() {

        String fio = txtFio.getText().toString().trim();
        String tel = txtTel.getText().toString().trim();
        String email = txtEmail.getText().toString().trim();

        if (!fio.isEmpty() && !tel.isEmpty() && !email.isEmpty()) {

            String body = "Фамилия: " + fio + "\nТелефон: " + tel + "\nEmail: " + email +
                    "\nКоличество баллов: " + point + "\nCумма скидки: " + price + " руб." +
                    "\nИдентификатор: " + imei;

            try {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("message/rfc822");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"mail@i.ua"});
                intent.setData(Uri.parse("mail@i.ua"));
                intent.putExtra(Intent.EXTRA_SUBJECT, "Получение скидки");
                intent.setType("plain/text");
                intent.putExtra(Intent.EXTRA_TEXT, body);
                startActivity(intent);

                //удаление данных о прохождении тестов
                //    clearData();
                clearPoint();

            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(getContext(), "Отсутствует почтовый клиент.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getContext(), "Пожалуйста, заполните все поля!", Toast.LENGTH_LONG).show();
        }
    }

    @Click(R.id.txtRulesGetDicount)
    void rules() {

    }

    /*private void makeWarningSendDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_send_email, null);
        Button yes = (Button) view.findViewById(R.id.yesButton);
        Button no = (Button) view.findViewById(R.id.noButton);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData();
                dialog.dismiss();
            }
        });
        dialog.show();
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }*/

    private void clearPoint() {
        mRealm.beginTransaction();
        List<Result> resultList = mRealm.where(Result.class).findAll();
        for (Result result :
                resultList) {
            result.setPoint(0);
        }
        mRealm.commitTransaction();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mRealm != null) mRealm.close();
    }


}
