package cheqfast.gfin.wasys.com.br.coleta.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;

import java.util.Arrays;
import java.util.List;

import br.com.wasys.library.utils.TypeUtils;
import br.com.wasys.library.widget.AppSpinner;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cheqfast.gfin.wasys.com.br.coleta.R;
import cheqfast.gfin.wasys.com.br.coleta.model.FiltroModel;
import cheqfast.gfin.wasys.com.br.coleta.model.ProcessoModel;
import cheqfast.gfin.wasys.com.br.coleta.widget.AppDateEditText;
import cheqfast.gfin.wasys.com.br.coleta.widget.AppNumberEditText;

/**
 * Created by pascke on 01/07/17.
 */

public class FiltroDialog extends AppCompatDialogFragment {

    @BindView(R.id.edit_numero)
    AppNumberEditText mNumeroEditText;
    @BindView(R.id.edit_inicio)
    AppDateEditText mInicioDateEditText;
    @BindView(R.id.edit_termino) AppDateEditText mTerminoDateEditText;
    @BindView(R.id.spinner_status)
    AppSpinner mStatusSpinner;
    @BindView(R.id.spinner_coleta)
    AppSpinner mColetaSpinner;
    @BindView(R.id.layout_status) TextInputLayout mStatusLayout;

    private FiltroModel mFiltro;
    private FiltroDialogListener mListener;
    private int mStatusVisibility = View.VISIBLE;

    public static FiltroDialog newInstance(FiltroModel filtro) {
        FiltroDialog dialog = new FiltroDialog();
        dialog.mFiltro = filtro;
        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        FragmentActivity activity = getActivity();
        LayoutInflater inflater = LayoutInflater.from(activity);
        View view = inflater.inflate(R.layout.dialog_filtro, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setView(view);
        ButterKnife.bind(this, view);
        List<ProcessoModel.Status> processoStatusList = Arrays.asList(ProcessoModel.Status.values());
        mStatusSpinner.setOptions(processoStatusList);
        List<ProcessoModel.Coleta> processoColetaList = Arrays.asList(ProcessoModel.Coleta.values());
        mColetaSpinner.setOptions(processoColetaList);
        if (mFiltro != null) {
            mNumeroEditText.setValue(mFiltro.numero);
            mInicioDateEditText.setValue(mFiltro.dataInicio);
            mTerminoDateEditText.setValue(mFiltro.dataTermino);
            if (mFiltro.status != null) {
                mStatusSpinner.setValue(mFiltro.status.name());
            }
            if (mFiltro.coleta != null) {
                mColetaSpinner.setValue(mFiltro.coleta.name());
            }
        }
        mStatusLayout.setVisibility(mStatusVisibility);
        return builder.create();
    }

    @OnClick(R.id.button_limpar)
    public void onLimparClick() {
        mFiltro = new FiltroModel();
        if (mListener != null) {
            mListener.onFiltrar(mFiltro);
        }
        dismiss();
    }

    @OnClick(R.id.button_filtrar)
    public void onFiltrarClick() {
        if (mFiltro == null) {
            mFiltro = new FiltroModel();
        }
        mFiltro.numero = mNumeroEditText.getValue();
        mFiltro.dataInicio = mInicioDateEditText.getValue();
        mFiltro.dataTermino = mTerminoDateEditText.getValue();
        mFiltro.status = TypeUtils.parse(ProcessoModel.Status.class, mStatusSpinner.getValue());
        mFiltro.coleta = TypeUtils.parse(ProcessoModel.Coleta.class, mColetaSpinner.getValue());
        if (mListener != null) {
            mListener.onFiltrar(mFiltro);
        }
        dismiss();
    }

    public void setStatusVisibility(int visibility) {
        mStatusVisibility = visibility;
    }

    public void setListener(FiltroDialogListener listener) {
        mListener = listener;
    }

    public static interface FiltroDialogListener {
        void onFiltrar(FiltroModel filtroModel);
    }
}
