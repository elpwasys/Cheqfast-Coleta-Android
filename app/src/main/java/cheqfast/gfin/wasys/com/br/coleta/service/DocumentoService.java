package cheqfast.gfin.wasys.com.br.coleta.service;

import br.com.wasys.library.service.Service;
import cheqfast.gfin.wasys.com.br.coleta.endpoint.DocumentoEndpoint;
import cheqfast.gfin.wasys.com.br.coleta.endpoint.Endpoint;
import cheqfast.gfin.wasys.com.br.coleta.model.DocumentoModel;
import retrofit2.Call;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by pascke on 03/09/16.
 */
public class DocumentoService extends Service {

    public static DocumentoModel obter(Long id) throws Throwable {
        DocumentoEndpoint endpoint = Endpoint.create(DocumentoEndpoint.class);
        Call<DocumentoModel> call = endpoint.obter(id);
        DocumentoModel model = Endpoint.execute(call);
        return model;
    }

    public static class Async {

        public static Observable<DocumentoModel> obter(final Long id) {
            return Observable.create(new Observable.OnSubscribe<DocumentoModel>() {
                @Override
                public void call(Subscriber<? super DocumentoModel> subscriber) {
                    try {
                        DocumentoModel model = DocumentoService.obter(id);
                        subscriber.onNext(model);
                        subscriber.onCompleted();
                    } catch (Throwable e) {
                        subscriber.onError(e);
                    }
                }
            });
        }
    }
}