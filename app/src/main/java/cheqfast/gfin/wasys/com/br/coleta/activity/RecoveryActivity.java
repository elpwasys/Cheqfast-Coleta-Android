package cheqfast.gfin.wasys.com.br.coleta.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.commons.lang3.StringUtils;

import br.com.wasys.library.utils.FieldUtils;
import br.com.wasys.library.utils.ValidatorUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cheqfast.gfin.wasys.com.br.coleta.R;
import cheqfast.gfin.wasys.com.br.coleta.model.ResultModel;
import cheqfast.gfin.wasys.com.br.coleta.service.DispositivoService;
import rx.Observable;
import rx.Subscriber;

public class RecoveryActivity extends CheqFastActivity {

    @BindView(R.id.edit_email) EditText mEmailEditText;
    @BindView(R.id.layout_email) TextInputLayout mEmailTextInputLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recovery);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, RecoveryActivity.class);
        return intent;
    }

    @OnClick(R.id.button_recuperar)
    public void onRecuperarClick() {
        boolean valid = isValid();
        if (valid) {
            startAsyncRecuperar();
        }
    }

    private boolean isValid() {
        boolean valid = true;
        // LOGIN
        String login = FieldUtils.getValue(mEmailEditText);
        if (StringUtils.isBlank(login)) {
            valid = false;
            mEmailTextInputLayout.setError(getString(R.string.msg_required_field, getString(R.string.email)));
        } else if (!ValidatorUtils.isValidEmail(login)) {
            valid = false;
            mEmailTextInputLayout.setError(getString(R.string.msg_invalid_field, getString(R.string.email)));
        } else {
            mEmailTextInputLayout.setErrorEnabled(false);
        }
        return valid;
    }

    private void onAsyncRecuperarCompleted(ResultModel model) {
        if (model.success) {
            Toast.makeText(this, R.string.senha_recuperada_senha, Toast.LENGTH_LONG).show();
        } else {
            String messages = model.getMessages();
            Toast.makeText(this, messages, Toast.LENGTH_LONG).show();
        }
    }

    private void startAsyncRecuperar() {
        String email = FieldUtils.getValue(mEmailEditText);
        showProgress();
        Observable<ResultModel> observable = DispositivoService.Async.recuperar(email);
        prepare(observable).subscribe(new Subscriber<ResultModel>() {
            @Override
            public void onCompleted() {
                hideProgress();
            }
            @Override
            public void onError(Throwable e) {
                hideProgress();
                handle(e);
            }
            @Override
            public void onNext(ResultModel model) {
                onAsyncRecuperarCompleted(model);
            }
        });
    }
}
