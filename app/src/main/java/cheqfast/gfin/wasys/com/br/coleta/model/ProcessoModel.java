package cheqfast.gfin.wasys.com.br.coleta.model;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

import java.util.ArrayList;
import java.util.Date;

import br.com.wasys.library.model.Model;
import br.com.wasys.library.widget.AppSpinner;
import cheqfast.gfin.wasys.com.br.coleta.Application;
import cheqfast.gfin.wasys.com.br.coleta.R;

/**
 * Created by pascke on 24/06/17.
 */

public class ProcessoModel extends Model {

    public Status status;
    public Coleta coleta;

    public Date dataCriacao;

    public Double taxa;
    public Double valorLiberado;

    public EnderecoModel endereco;
    public TipoProcessoModel tipoProcesso;

    public ArrayList<UploadModel> uploads;
    public ArrayList<DocumentoModel> documentos;
    public ArrayList<CampoGrupoModel> gruposCampos;

    public enum Coleta implements AppSpinner.Option {
        AGUARDANDO_COLETA (R.string.coleta_status_aguardando_coleta, R.drawable.ic_bike),
        COLETADO (R.string.coleta_status_coletado, R.drawable.ic_folder),
        ARMAZENADO (R.string.coleta_status_armazenado, R.drawable.ic_database);
        public int stringRes;
        public int drawableRes;
        Coleta(@StringRes int stringRes, @DrawableRes int drawableRes) {
            this.stringRes = stringRes;
            this.drawableRes = drawableRes;
        }
        @Override
        public String getValue() {
            return name();
        }
        @Override
        public String getLabel() {
            Context context = Application.getContext();
            return context.getString(stringRes);
        }
    }

    public enum Status implements AppSpinner.Option {
        RASCUNHO (R.string.processo_status_rascunho, R.drawable.processo_status_rascunho),
        EM_ANALISE (R.string.processo_status_em_analise, R.drawable.processo_status_em_analise),
        PENDENTE (R.string.processo_status_pendente, R.drawable.processo_status_pendente),
        CONCLUIDO (R.string.processo_status_concluido, R.drawable.processo_status_concluido),
        CANCELADO (R.string.processo_status_cancelado, R.drawable.processo_status_cancelado),
        AGUARDANDO_APROVACAO (R.string.processo_status_aguardando_aprovacao, R.drawable.processo_status_aguardando_aprovacao),
        AGUARDANDO_DOCUMENTOS (R.string.processo_status_aguardando_documentos, R.drawable.processo_status_aguardando_documentos),
        EM_LIBERACAO (R.string.processo_status_em_liberacao, R.drawable.processo_status_em_liberacao);
        public int stringRes;
        public int drawableRes;
        Status(@StringRes int stringRes, @DrawableRes int drawableRes) {
            this.stringRes = stringRes;
            this.drawableRes = drawableRes;
        }
        @Override
        public String getValue() {
            return name();
        }
        @Override
        public String getLabel() {
            Context context = Application.getContext();
            return context.getString(stringRes);
        }
    }
}
