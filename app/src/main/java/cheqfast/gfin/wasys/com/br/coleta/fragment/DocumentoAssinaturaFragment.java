package cheqfast.gfin.wasys.com.br.coleta.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.viewpagerindicator.CirclePageIndicator;

import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

import br.com.wasys.library.utils.AndroidUtils;
import br.com.wasys.library.utils.FragmentUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import cheqfast.gfin.wasys.com.br.coleta.R;
import cheqfast.gfin.wasys.com.br.coleta.adapter.ImagePageAdapter;
import cheqfast.gfin.wasys.com.br.coleta.model.DocumentoModel;
import cheqfast.gfin.wasys.com.br.coleta.model.ImagemModel;
import cheqfast.gfin.wasys.com.br.coleta.service.DocumentoService;
import rx.Observable;
import rx.Subscriber;

/**
 * A simple {@link Fragment} subclass.
 */
public class DocumentoAssinaturaFragment extends CheqFastFragment {

    @BindView(R.id.pager) ViewPager mPager;
    @BindView(R.id.indicator) CirclePageIndicator mIndicator;

    private ImagePageAdapter mAdapter;

    private Long mId;
    private DocumentoModel.Nome mNome;
    private DocumentoModel mDocumento;
    private DocumentoAssinaturaListener mListener;

    private static final String KEY_ID = DocumentoAssinaturaFragment.class.getSimpleName() + ".mId";
    private static final String KEY_NOME = DocumentoAssinaturaFragment.class.getSimpleName() + ".mNome";

    public static DocumentoAssinaturaFragment newInstance(Long id, DocumentoModel.Nome nome) {
        DocumentoAssinaturaFragment fragment = new DocumentoAssinaturaFragment();
        if (id != null && nome != null) {
            Bundle bundle = new Bundle();
            bundle.putLong(KEY_ID, id);
            bundle.putSerializable(KEY_NOME, nome);
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
            if (bundle.containsKey(KEY_NOME)) {
                mNome = (DocumentoModel.Nome) bundle.getSerializable(KEY_NOME);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_documento_assinatura, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        prepare();
        buscar();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_documento_assinatura, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.action_confirmar:
                confirmar();
                return true;
        }
        return false;
    }

    private void prepare() {
        setTitle(R.string.documento);
        if (mNome != null) {
            setTitle(mNome.stringRes);
        }
        Context context = getContext();
        mPager.setPageMargin(AndroidUtils.toPixels(context, 16f));
    }

    private void confirmar() {
        if (mListener != null) {
            mListener.onConfirmarClick(mNome);
        }
        FragmentUtils.popBackStackImmediate(getActivity(), getBackStackName());
    }

    private void populate() {
        if (mDocumento != null) {
            List<ImagemModel> imagens = mDocumento.imagens;
            if (CollectionUtils.isNotEmpty(imagens)) {
                mAdapter = new ImagePageAdapter(getFragmentManager(), imagens);
                mPager.setAdapter(mAdapter);
                mIndicator.setViewPager(mPager);
            }
        }
    }

    private void buscar() {
        if (mId != null && mNome != null) {
            startAsyncObter(mId, mNome);
        }
    }

    /**
     *
     * COMPLETED METHODS ASYNCHRONOUS HANDLERS
     *
     */
    private void onAsyncObterCompleted(DocumentoModel documentoModel) {
        mDocumento = documentoModel;
        populate();
    }

    /**
     *
     * ASYNCHRONOUS METHODS
     *
     */
    private void startAsyncObter(Long id, DocumentoModel.Nome nome) {
        showProgress();
        Observable<DocumentoModel> observable = DocumentoService.Async.obter(id, nome);
        prepare(observable).subscribe(new Subscriber<DocumentoModel>() {
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
            public void onNext(DocumentoModel documentoModel) {
                hideProgress();
                onAsyncObterCompleted(documentoModel);
            }
        });
    }

    public void setListener(DocumentoAssinaturaListener listener) {
        mListener = listener;
    }

    public static interface DocumentoAssinaturaListener {
        void onConfirmarClick(DocumentoModel.Nome nome);
    }
}
