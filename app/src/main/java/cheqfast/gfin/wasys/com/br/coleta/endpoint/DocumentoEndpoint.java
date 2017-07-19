package cheqfast.gfin.wasys.com.br.coleta.endpoint;

import cheqfast.gfin.wasys.com.br.coleta.model.DocumentoModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by pascke on 02/08/16.
 */
public interface DocumentoEndpoint {

    @GET("documento/obter/{id}")
    Call<DocumentoModel> obter(@Path("id") Long id);
}