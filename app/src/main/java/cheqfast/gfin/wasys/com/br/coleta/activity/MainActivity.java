package cheqfast.gfin.wasys.com.br.coleta.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import br.com.wasys.library.utils.FieldUtils;
import br.com.wasys.library.utils.FragmentUtils;
import butterknife.ButterKnife;
import cheqfast.gfin.wasys.com.br.coleta.Dispositivo;
import cheqfast.gfin.wasys.com.br.coleta.R;
import cheqfast.gfin.wasys.com.br.coleta.Usuario;
import cheqfast.gfin.wasys.com.br.coleta.fragment.CheqFastFragment;
import cheqfast.gfin.wasys.com.br.coleta.fragment.ProcessoPesquisaFragment;
import cheqfast.gfin.wasys.com.br.coleta.fragment.TermoFragment;
import cheqfast.gfin.wasys.com.br.coleta.model.ProcessoModel;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mToggle;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(
                this, mDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.setDrawerListener(mToggle);
        mToggle.syncState();

        /*mToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });*/

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Usuario usuario = Usuario.current();
        if (usuario != null) {
            View headerView = navigationView.getHeaderView(0);
            // USUARIO NOME
            TextView nomeTextView = ButterKnife.findById(headerView, R.id.usuario_nome);
            String nome = usuario.getNome();
            FieldUtils.setText(nomeTextView, nome);
            // USUARIO EMAIL
            String email = usuario.getEmail();
            TextView emailTextView = ButterKnife.findById(headerView, R.id.usuario_email);
            FieldUtils.setText(emailTextView, email);
        }

        mDrawer.openDrawer(GravityCompat.START);
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
       /* } else if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
            mDrawer.openDrawer(GravityCompat.START);*/
        } else {
            FragmentManager manager = getSupportFragmentManager();
            if (manager.getBackStackEntryCount() < 2) {
                mDrawer.openDrawer(GravityCompat.START);
            }
            else {
                super.onBackPressed();
            }
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        CheqFastFragment fragment = null;

        switch (id) {
            case R.id.nav_search:
                fragment = ProcessoPesquisaFragment.newInstance();
                break;
            case R.id.nav_info: {

                break;
            }
            case R.id.nav_termo: {
                fragment = TermoFragment.newInstance();
                break;
            }
            case R.id.nav_power: {
                sair();
                break;
            }
        }

        if (fragment != null) {
            FragmentUtils.popAllBackStackImmediate(this);
            FragmentUtils.replace(this, R.id.content_main, fragment, fragment.getBackStackName());
            /*getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_main, fragment)
                    .addToBackStack(fragment.getBackStackName())
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();*/
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    public void setNavigationDrawerState(boolean isEnable) {
        if (isEnable) {
            mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            mToggle.setDrawerIndicatorEnabled(true);
            mToggle.syncState();
            getSupportActionBar().setTitle(R.string.app_name);
        } else {
            mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            mToggle.setDrawerIndicatorEnabled(false);
            mToggle.setHomeAsUpIndicator(R.drawable.ic_bar_arrow_back);
            mToggle.syncState();
        }
    }

    private void sair() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.sair);
        builder.setMessage(R.string.msg_sair_conta);
        builder.setNegativeButton(R.string.nao, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setPositiveButton(R.string.sim, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Dispositivo.clear();
                Intent intent = SplashActivity.newIntent(MainActivity.this);
                startActivity(intent);
                finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
