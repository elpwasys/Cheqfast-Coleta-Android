package cheqfast.gfin.wasys.com.br.coleta.service;

import br.com.wasys.library.service.Service;
import cheqfast.gfin.wasys.com.br.coleta.endpoint.DispositivoEndpoint;
import cheqfast.gfin.wasys.com.br.coleta.endpoint.Endpoint;
import cheqfast.gfin.wasys.com.br.coleta.model.CredencialModel;
import cheqfast.gfin.wasys.com.br.coleta.model.DispositivoModel;
import cheqfast.gfin.wasys.com.br.coleta.model.RecoveryModel;
import cheqfast.gfin.wasys.com.br.coleta.model.ResultModel;
import retrofit2.Call;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by pascke on 03/09/16.
 */
public class DispositivoService extends Service {

    public static ResultModel recuperar(String senha) throws Throwable {
        DispositivoEndpoint endpoint = Endpoint.create(DispositivoEndpoint.class);
        Call<ResultModel> call = endpoint.recuperar(new RecoveryModel(senha));
        ResultModel model = Endpoint.execute(call);
        return model;
    }

    public static DispositivoModel autenticar(String login, String senha) throws Throwable {
        DispositivoEndpoint endpoint = Endpoint.create(DispositivoEndpoint.class);
        Call<DispositivoModel> call = endpoint.autenticar(new CredencialModel(login, senha));
        DispositivoModel model = Endpoint.execute(call);
        return model;
    }

    public static class Async {

        public static Observable<ResultModel> recuperar(final String email) {
            return Observable.create(new Observable.OnSubscribe<ResultModel>() {
                @Override
                public void call(Subscriber<? super ResultModel> subscriber) {
                    try {
                        ResultModel model = DispositivoService.recuperar(email);
                        subscriber.onNext(model);
                        subscriber.onCompleted();
                    } catch (Throwable e) {
                        subscriber.onError(e);
                    }
                }
            });
        }

        public static Observable<DispositivoModel> autenticar(final String login, final String senha) {
            return Observable.create(new Observable.OnSubscribe<DispositivoModel>() {
                @Override
                public void call(Subscriber<? super DispositivoModel> subscriber) {
                    try {
                        DispositivoModel userModel = DispositivoService.autenticar(login, senha);
                        subscriber.onNext(userModel);
                        subscriber.onCompleted();
                    } catch (Throwable e) {
                        subscriber.onError(e);
                    }
                }
            });
        }
    }
}