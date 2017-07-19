package cheqfast.gfin.wasys.com.br.coleta.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import br.com.wasys.library.fragment.AppFragment;

/**
 * Created by pascke on 24/06/17.
 */

public abstract class CheqFastFragment extends AppFragment {

    private Snackbar mSnackbar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onPause() {
        hideSnackbar();
        super.onPause();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /*FragmentActivity activity = getActivity();
        if (activity instanceof MainActivity) {
            ((MainActivity) activity).setNavigationDrawerState(false);
        }*/
    }

    @Override
    public void onDestroyView() {
        /*FragmentActivity activity = getActivity();
        if (activity instanceof MainActivity) {
            ((MainActivity) activity).setNavigationDrawerState(true);
        }*/
        super.onDestroyView();
    }

    public String getBackStackName() {
        return getClass().getSimpleName();
    }

    protected void setTitle(int id) {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setTitle(id);
    }

    protected void setTitle(String title) {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setTitle(title);
    }

    protected void hideSnackbar() {
        if (mSnackbar != null && mSnackbar.isShown()) {
            mSnackbar.dismiss();
        }
    }

    protected Snackbar makeSnackbar(String text, @Snackbar.Duration int duration) {
        hideSnackbar();
        if (mSnackbar != null) {
            mSnackbar.setText(text);
            mSnackbar.setDuration(duration);
        } else {
            View view = getView();
            mSnackbar = Snackbar.make(view, text, duration);
        }
        return mSnackbar;
    }
}