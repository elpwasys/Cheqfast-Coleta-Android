package cheqfast.gfin.wasys.com.br.coleta.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import cheqfast.gfin.wasys.com.br.coleta.BuildConfig;
import cheqfast.gfin.wasys.com.br.coleta.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TermoFragment extends CheqFastFragment {

    @BindView(R.id.webview) WebView webView;

    public static TermoFragment newInstance() {
        TermoFragment fragment = new TermoFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_termo, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        prepare();
    }

    private void prepare() {
        setTitle(R.string.termo_uso);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                hideProgress();
            }
        });
        showProgress();
        webView.loadUrl(BuildConfig.TERMO_URL);
    }
}