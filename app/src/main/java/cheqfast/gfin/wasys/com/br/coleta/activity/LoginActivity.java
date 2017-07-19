package cheqfast.gfin.wasys.com.br.coleta.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.widget.EditText;

import org.apache.commons.lang3.StringUtils;

import br.com.wasys.library.utils.FieldUtils;
import br.com.wasys.library.utils.ValidatorUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cheqfast.gfin.wasys.com.br.coleta.Dispositivo;
import cheqfast.gfin.wasys.com.br.coleta.R;
import cheqfast.gfin.wasys.com.br.coleta.model.DispositivoModel;
import cheqfast.gfin.wasys.com.br.coleta.service.DispositivoService;
import rx.Observable;
import rx.Subscriber;

public class LoginActivity extends CheqFastActivity {

    @BindView(R.id.edit_email) EditText mEmailEditText;
    @BindView(R.id.edit_senha) EditText mSenhaEditText;

    @BindView(R.id.layout_email) TextInputLayout mEmailTextInputLayout;
    @BindView(R.id.layout_senha) TextInputLayout mSenhaTextInputLayout;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button_recuperar)
    public void onRecoveryClick() {
        Intent intent = RecoveryActivity.newIntent(this);
        startActivity(intent);
    }

    @OnClick(R.id.button_entrar)
    public void onEntrarClick() {
        boolean valid = isValid();
        if (valid) {
            startAsyncAutenticar();
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
        // SENHA
        String senha = FieldUtils.getValue(mSenhaEditText);
        if (StringUtils.isBlank(senha)) {
            valid = false;
            mSenhaTextInputLayout.setError(getString(R.string.msg_required_field, getString(R.string.senha)));
        } else {
            mSenhaTextInputLayout.setErrorEnabled(false);
        }
        return valid;
    }

    private void onAsyncAutenticarCompleted(DispositivoModel dispositivoModel) {
        Dispositivo.from(dispositivoModel);
        Intent intent = MainActivity.newIntent(this);
        startActivity(intent);
        finish();
    }

    private void startAsyncAutenticar() {
        String login = FieldUtils.getValue(mEmailEditText);
        String senha = FieldUtils.getValue(mSenhaEditText);
        showProgress();
        Observable<DispositivoModel> observable = DispositivoService.Async.autenticar(login, senha);
        prepare(observable).subscribe(new Subscriber<DispositivoModel>() {
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
            public void onNext(DispositivoModel dispositivoModel) {
                onAsyncAutenticarCompleted(dispositivoModel);
            }
        });
    }
}
