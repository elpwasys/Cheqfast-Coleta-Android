package cheqfast.gfin.wasys.com.br.coleta.service;

import br.com.wasys.library.service.Service;
import cheqfast.gfin.wasys.com.br.coleta.dataset.DataSet;
import cheqfast.gfin.wasys.com.br.coleta.endpoint.Endpoint;
import cheqfast.gfin.wasys.com.br.coleta.endpoint.ProcessoEndpoint;
import cheqfast.gfin.wasys.com.br.coleta.model.ProcessoModel;
import cheqfast.gfin.wasys.com.br.coleta.model.ProcessoRegraModel;
import retrofit2.Call;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by pascke on 03/09/16.
 */
public class ProcessoService extends Service {

    public static DataSet<ProcessoModel, ProcessoRegraModel> editar(Long id) throws Throwable {
        ProcessoEndpoint endpoint = Endpoint.create(ProcessoEndpoint.class);
        Call<DataSet<ProcessoModel, ProcessoRegraModel>> call = endpoint.editar(id);
        DataSet<ProcessoModel, ProcessoRegraModel> dataSet = Endpoint.execute(call);
        return dataSet;
    }

    public static DataSet<ProcessoModel, ProcessoRegraModel> registrarColeta(Long id) throws Throwable {
        ProcessoEndpoint endpoint = Endpoint.create(ProcessoEndpoint.class);
        Call<DataSet<ProcessoModel, ProcessoRegraModel>> call = endpoint.registrarColeta(id);
        DataSet<ProcessoModel, ProcessoRegraModel> dataSet = Endpoint.execute(call);
        return dataSet;
    }

    public static class Async {

        public static Observable<DataSet<ProcessoModel, ProcessoRegraModel>> editar(final Long id) {
            return Observable.create(new Observable.OnSubscribe<DataSet<ProcessoModel, ProcessoRegraModel>>() {
                @Override
                public void call(Subscriber<? super DataSet<ProcessoModel, ProcessoRegraModel>> subscriber) {
                    try {
                        DataSet<ProcessoModel, ProcessoRegraModel> dataSet = ProcessoService.editar(id);
                        subscriber.onNext(dataSet);
                        subscriber.onCompleted();
                    } catch (Throwable e) {
                        subscriber.onError(e);
                    }
                }
            });
        }

        public static Observable<DataSet<ProcessoModel, ProcessoRegraModel>> registrarColeta(final Long id) {
            return Observable.create(new Observable.OnSubscribe<DataSet<ProcessoModel, ProcessoRegraModel>>() {
                @Override
                public void call(Subscriber<? super DataSet<ProcessoModel, ProcessoRegraModel>> subscriber) {
                    try {
                        DataSet<ProcessoModel, ProcessoRegraModel> dataSet = ProcessoService.registrarColeta(id);
                        subscriber.onNext(dataSet);
                        subscriber.onCompleted();
                    } catch (Throwable e) {
                        subscriber.onError(e);
                    }
                }
            });
        }
    }
}