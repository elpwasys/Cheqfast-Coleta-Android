package cheqfast.gfin.wasys.com.br.coleta.endpoint;

import cheqfast.gfin.wasys.com.br.coleta.model.PesquisaModel;
import cheqfast.gfin.wasys.com.br.coleta.paging.ProcessoPagingModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by pascke on 02/08/16.
 */
public interface PesquisaEndpoint {

    @POST("pesquisa/filtrar")
    Call<ProcessoPagingModel> filtrar(@Body PesquisaModel pesquisaModel);
}
