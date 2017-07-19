package cheqfast.gfin.wasys.com.br.coleta;

import com.facebook.stetho.Stetho;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import io.realm.Realm;

/**
 * Created by pascke on 03/08/16.
 */
public class Application extends br.com.wasys.library.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        /*RealmConfiguration configuration = new RealmConfiguration.Builder()
                .name("data.realm")
                .build();
        Realm.setDefaultConfiguration(configuration);*/
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                        .build()
        );
    }
}