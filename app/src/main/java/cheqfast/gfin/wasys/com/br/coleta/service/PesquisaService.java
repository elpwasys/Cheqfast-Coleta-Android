package cheqfast.gfin.wasys.com.br.coleta.service;

import br.com.wasys.library.service.Service;
import cheqfast.gfin.wasys.com.br.coleta.endpoint.Endpoint;
import cheqfast.gfin.wasys.com.br.coleta.endpoint.PesquisaEndpoint;
import cheqfast.gfin.wasys.com.br.coleta.model.PesquisaModel;
import cheqfast.gfin.wasys.com.br.coleta.paging.ProcessoPagingModel;
import retrofit2.Call;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by pascke on 03/09/16.
 */
public class PesquisaService extends Service {

    public static ProcessoPagingModel filtrar(PesquisaModel pesquisaModel) throws Throwable {
        PesquisaEndpoint endpoint = Endpoint.create(PesquisaEndpoint.class);
        Call<ProcessoPagingModel> call = endpoint.filtrar(pesquisaModel);
        ProcessoPagingModel pagingModel = Endpoint.execute(call);
        return pagingModel;
    }

    public static class Async {

        public static Observable<ProcessoPagingModel> filtrar(final PesquisaModel pesquisaModel) {
            return Observable.create(new Observable.OnSubscribe<ProcessoPagingModel>() {
                @Override
                public void call(Subscriber<? super ProcessoPagingModel> subscriber) {
                    try {
                        ProcessoPagingModel pagingModel = PesquisaService.filtrar(pesquisaModel);
                        subscriber.onNext(pagingModel);
                        subscriber.onCompleted();
                    } catch (Throwable e) {
                        subscriber.onError(e);
                    }
                }
            });
        }
    }
}