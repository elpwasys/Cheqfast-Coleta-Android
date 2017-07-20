package cheqfast.gfin.wasys.com.br.coleta.fragment;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

import br.com.wasys.library.utils.FieldUtils;
import br.com.wasys.library.utils.FragmentUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cheqfast.gfin.wasys.com.br.coleta.R;
import cheqfast.gfin.wasys.com.br.coleta.dataset.DataSet;
import cheqfast.gfin.wasys.com.br.coleta.model.CampoGrupoModel;
import cheqfast.gfin.wasys.com.br.coleta.model.ChequeModel;
import cheqfast.gfin.wasys.com.br.coleta.model.DocumentoModel;
import cheqfast.gfin.wasys.com.br.coleta.model.ProcessoModel;
import cheqfast.gfin.wasys.com.br.coleta.model.ProcessoRegraModel;
import cheqfast.gfin.wasys.com.br.coleta.service.ProcessoService;
import cheqfast.gfin.wasys.com.br.coleta.widget.AppGroupReadonlyInputLayout;
import rx.Observable;
import rx.Subscriber;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProcessoDetalheFragment extends CheqFastFragment implements View.OnClickListener, DocumentoAssinaturaFragment.DocumentoAssinaturaListener {

    @BindView(R.id.view_root) View mViewRoot;
    @BindView(R.id.text_id) TextView mIdTextView;
    @BindView(R.id.text_taxa) TextView mTaxaTextView;
    @BindView(R.id.text_data) TextView mDataTextView;
    @BindView(R.id.text_status) TextView mStatusTextView;
    @BindView(R.id.text_coleta) TextView mColetaTextView;
    @BindView(R.id.image_status) ImageView mStatusImageView;
    @BindView(R.id.layout_fields) LinearLayout mLayoutFields;
    @BindView(R.id.layout_documentos) LinearLayout mLayoutDocumentos;
    @BindView(R.id.text_valor_liberado) TextView mValorLiberadoTextView;

    @BindView(R.id.fab_menu) FloatingActionMenu mFloatingActionMenu;
    @BindView(R.id.fab_assinar_aditivo) FloatingActionButton mAssinarAditivoFloatingActionButton;
    @BindView(R.id.fab_registrar_coleta) FloatingActionButton mRegistrarColetaFloatingActionButton;
    @BindView(R.id.fab_assinar_promissoria) FloatingActionButton mAssinarPromissoriaFloatingActionButton;

    private Long mId;
    private MenuItem mMapaItem;
    private ProcessoModel mProcesso;
    private ProcessoRegraModel mRegra;

    private static final String KEY_ID = ProcessoPesquisaFragment.class.getSimpleName() + ".mId";
    private static final String KEY_ADITIVO_OK = ProcessoPesquisaFragment.class.getSimpleName() + ".mAditivoOk";
    private static final String KEY_PROMISSOIA_OK = ProcessoPesquisaFragment.class.getSimpleName() + ".mPromissoriaOk";

    public static ProcessoDetalheFragment newInstance(Long id) {
        ProcessoDetalheFragment fragment = new ProcessoDetalheFragment();
        if (id != null) {
            Bundle bundle = new Bundle();
            bundle.putLong(KEY_ID, id);
            fragment.setArguments(bundle);
        }
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            if (bundle.containsKey(KEY_ID)) {
                mId = bundle.getLong(KEY_ID);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_processo_detalhe, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        prepare();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mId != null) {
            startAsyncEditar(mId);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_processo_detalhe, menu);
        mMapaItem = menu.findItem(R.id.action_place);
        mMapaItem.setVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.action_place:
                openMaps();
                return true;
        }
        return false;
    }

    @Override
    public void onClick(View view) {
        Object object = view.getTag();
        if (object instanceof DocumentoModel) {
            DocumentoModel documento = (DocumentoModel) object;
            DocumentoDetalheFragment fragment = DocumentoDetalheFragment.newInstance(documento.id);
            FragmentUtils.replace(getActivity(), R.id.content_main, fragment, fragment.getBackStackName());
        }
    }

    @Override
    public void onConfirmarClick(DocumentoModel.Nome nome) {
        String key = null;
        if (DocumentoModel.Nome.ADITIVO.equals(nome)) {
            key = KEY_ADITIVO_OK;
        } else if (DocumentoModel.Nome.PROMISSORIA.equals(nome)) {
            key = KEY_PROMISSOIA_OK;
        }
        if (StringUtils.isNotEmpty(key)) {
            Bundle bundle = getArguments();
            if (bundle == null) {
                bundle = new Bundle();
                setArguments(bundle);
            }
            bundle.putBoolean(key, true);
            setRegistrarVisibility();
        }
    }

    @OnClick(R.id.fab_assinar_aditivo)
    public void onAssinarAditivoClick() {
        mFloatingActionMenu.close(true);
        DocumentoAssinaturaFragment fragment = DocumentoAssinaturaFragment.newInstance(mId, DocumentoModel.Nome.ADITIVO);
        fragment.setListener(this);
        FragmentUtils.replace(getActivity(), R.id.content_main, fragment, fragment.getBackStackName());
    }

    @OnClick(R.id.fab_assinar_promissoria)
    public void onAssinarPromissoriaClick() {
        mFloatingActionMenu.close(true);
        DocumentoAssinaturaFragment fragment = DocumentoAssinaturaFragment.newInstance(mId, DocumentoModel.Nome.PROMISSORIA);
        fragment.setListener(this);
        FragmentUtils.replace(getActivity(), R.id.content_main, fragment, fragment.getBackStackName());
    }

    @OnClick(R.id.fab_registrar_coleta)
    public void onRegistrarColetaClick() {
        mFloatingActionMenu.close(true);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.coleta)
                .setMessage(R.string.msg_registrar_coleta)
                .setPositiveButton(R.string.sim, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        registrar();
                    }
                })
                .setNegativeButton(R.string.nao, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void openMaps() {
        String completo = getEnderecoCompleto();
        if (StringUtils.isNotBlank(completo)) {
            String endereco = String.format("geo:0,0?q=%1$s", Uri.encode(completo));
            Uri ur = Uri.parse(endereco);
            Intent intent = new Intent(Intent.ACTION_VIEW, ur);
            intent.setPackage("com.google.android.apps.maps");
            if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                startActivity(intent);
            }
        }
    }

    private String getEnderecoCompleto() {
        if (mProcesso != null && mProcesso.endereco != null) {
            String completo = mProcesso.endereco.getCompleto();
            if (StringUtils.isNotBlank(completo)) {
                return completo;
            }
        }
        return null;
    }

    private void registrar() {
        if (mId != null) {
            startAsyncRegistrarColeta(mId);
        }
    }

    private void prepare() {
        setTitle(R.string.detalhe);
        setRootViewVisibility(View.GONE);
        mFloatingActionMenu.setClosedOnTouchOutside(true);
    }

    private void setRootViewVisibility(int visibility) {
        mViewRoot.setVisibility(visibility);
    }

    private void populate() {

        mLayoutFields.removeAllViews();
        mLayoutDocumentos.removeAllViews();

        mFloatingActionMenu.setVisibility(View.GONE);
        mRegistrarColetaFloatingActionButton.setEnabled(false);
        mAssinarAditivoFloatingActionButton.setEnabled(false);
        mAssinarPromissoriaFloatingActionButton.setEnabled(false);

        if (mProcesso != null) {

            Context context = getContext();

            mStatusImageView.setImageResource(mProcesso.status.drawableRes);

            FieldUtils.setText(mIdTextView, mProcesso.id);
            FieldUtils.setText(mStatusTextView, getString(mProcesso.status.stringRes));
            FieldUtils.setText(mDataTextView, mProcesso.dataCriacao, "dd/MM/yyyy HH:mm");

            if (mProcesso.coleta != null) {
                FieldUtils.setText(mColetaTextView, getString(mProcesso.coleta.stringRes));
            }

            FieldUtils.setText(mTaxaTextView, mProcesso.taxa);
            FieldUtils.setText(mValorLiberadoTextView, mProcesso.valorLiberado);

            ArrayList<CampoGrupoModel> gruposCampos = mProcesso.gruposCampos;
            if (CollectionUtils.isNotEmpty(gruposCampos)) {
                for (CampoGrupoModel grupo : gruposCampos) {
                    AppGroupReadonlyInputLayout campoGrupoLayout = new AppGroupReadonlyInputLayout(context);
                    campoGrupoLayout.setOrientation(LinearLayout.VERTICAL);
                    campoGrupoLayout.setGrupo(grupo);
                    mLayoutFields.addView(campoGrupoLayout);
                }
            }

            ArrayList<DocumentoModel> documentos = mProcesso.documentos;
            if (CollectionUtils.isNotEmpty(documentos)) {
                LayoutInflater inflater = LayoutInflater.from(context);
                for (DocumentoModel documento : documentos) {
                    View view = inflater.inflate(R.layout.list_item_documento, null);
                    DocumentoModel.Status status = documento.status;
                    ImageView statusImageView = ButterKnife.findById(view, R.id.image_status);
                    TextView dataTextView = ButterKnife.findById(view, R.id.text_data);
                    TextView nomeTextView = ButterKnife.findById(view, R.id.text_nome);
                    TextView statusTextView = ButterKnife.findById(view, R.id.text_status);
                    TextView documentoTextView = ButterKnife.findById(view, R.id.text_documento);
                    statusImageView.setImageResource(status.drawableRes);
                    FieldUtils.setText(dataTextView, documento.dataDigitalizacao);
                    FieldUtils.setText(nomeTextView, documento.nome);
                    FieldUtils.setText(statusTextView, getString(status.stringRes));
                    ChequeModel cheque = documento.cheque;
                    if (cheque != null) {
                        FieldUtils.setText(documentoTextView, cheque.cpfCnpj);
                    }
                    view.setTag(documento);
                    view.setOnClickListener(this);
                    mLayoutDocumentos.addView(view);
                }
            }

            if (mRegra.podeRegistrarColeta) {
                mFloatingActionMenu.setVisibility(View.VISIBLE);
                mAssinarAditivoFloatingActionButton.setEnabled(true);
                mAssinarPromissoriaFloatingActionButton.setEnabled(true);
                setRegistrarVisibility();
            }

            String endereco = getEnderecoCompleto();
            if (StringUtils.isNotBlank(endereco)) {
                mMapaItem.setVisible(true);
            }

            setRootViewVisibility(View.VISIBLE);
        }
    }

    private void setRegistrarVisibility() {
        mRegistrarColetaFloatingActionButton.setEnabled(false);
        if (mRegra != null && mRegra.podeRegistrarColeta) {
            Bundle bundle = getArguments();
            boolean isAditivoOk = bundle.getBoolean(KEY_ADITIVO_OK, false);
            boolean isPromissoriaOk = bundle.getBoolean(KEY_PROMISSOIA_OK, false);
            if (isAditivoOk && isPromissoriaOk) {
                mRegistrarColetaFloatingActionButton.setEnabled(true);
            }
        }
    }

    /**
     *
     * COMPLETED METHODS ASYNCHRONOUS HANDLERS
     *
     */
    private void onAsyncEditarCompleted(DataSet<ProcessoModel, ProcessoRegraModel> dataSet) {

        mRegra = dataSet.meta;
        mProcesso = dataSet.data;

        populate();
    }

    /**
     *
     * ASYNCHRONOUS METHODS
     *
     */
    private void startAsyncEditar(Long id) {
        showProgress();
        setRootViewVisibility(View.GONE);
        Observable<DataSet<ProcessoModel, ProcessoRegraModel>> observable = ProcessoService.Async.editar(id);
        prepare(observable).subscribe(new Subscriber<DataSet<ProcessoModel, ProcessoRegraModel>>() {
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
            public void onNext(DataSet<ProcessoModel, ProcessoRegraModel> dataSet) {
                hideProgress();
                onAsyncEditarCompleted(dataSet);
            }
        });
    }

    private void startAsyncRegistrarColeta(Long id) {
        showProgress();
        setRootViewVisibility(View.GONE);
        Observable<DataSet<ProcessoModel, ProcessoRegraModel>> observable = ProcessoService.Async.registrarColeta(id);
        prepare(observable).subscribe(new Subscriber<DataSet<ProcessoModel, ProcessoRegraModel>>() {
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
            public void onNext(DataSet<ProcessoModel, ProcessoRegraModel> dataSet) {
                hideProgress();
                onAsyncEditarCompleted(dataSet);
            }
        });
    }
}
