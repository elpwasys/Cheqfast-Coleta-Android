package cheqfast.gfin.wasys.com.br.coleta.endpoint;

import cheqfast.gfin.wasys.com.br.coleta.model.CredencialModel;
import cheqfast.gfin.wasys.com.br.coleta.model.DispositivoModel;
import cheqfast.gfin.wasys.com.br.coleta.model.RecoveryModel;
import cheqfast.gfin.wasys.com.br.coleta.model.ResultModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by pascke on 02/08/16.
 */
public interface DispositivoEndpoint {

    @POST("dispositivo/recuperar")
    Call<ResultModel> recuperar(@Body RecoveryModel recoveryModel);

    @POST("dispositivo/autenticar")
    Call<DispositivoModel> autenticar(@Body CredencialModel credencialModel);
}
