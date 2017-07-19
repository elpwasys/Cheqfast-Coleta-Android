package cheqfast.gfin.wasys.com.br.coleta.endpoint;

import cheqfast.gfin.wasys.com.br.coleta.dataset.DataSet;
import cheqfast.gfin.wasys.com.br.coleta.model.ProcessoModel;
import cheqfast.gfin.wasys.com.br.coleta.model.ProcessoRegraModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by pascke on 02/08/16.
 */
public interface ProcessoEndpoint {

    @GET("processo/editar/{id}")
    Call<DataSet<ProcessoModel, ProcessoRegraModel>> editar(@Path("id") Long id);

    @GET("processo/registrar/coleta/{id}")
    Call<DataSet<ProcessoModel, ProcessoRegraModel>> registrarColeta(@Path("id") Long id);
}