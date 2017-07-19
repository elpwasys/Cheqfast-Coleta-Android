package cheqfast.gfin.wasys.com.br.coleta.model;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

import java.util.Date;
import java.util.List;

import br.com.wasys.library.model.Model;
import cheqfast.gfin.wasys.com.br.coleta.R;

/**
 * Created by pascke on 02/07/17.
 */

public class DocumentoModel extends Model {

    public String nome;
    public Status status;

    public Integer versaoAtual;
    public Integer qtdeImagens;
    public Date dataDigitalizacao;

    public Boolean obrigatorio;
    public Boolean podeExcluir;
    public Boolean justificavel;
    public Boolean digitalizavel;

    public String irregularidadeNome;
    public String pendenciaObservacao;
    public String pendenciaJustificativa;

    public ChequeModel cheque;
    public List<ImagemModel> imagens;

    public enum Status {
        INCLUIDO (R.string.documento_status_incluido, R.drawable.documento_status_incluido),
        PENDENTE (R.string.documento_status_pendente, R.drawable.documento_status_pendente),
        APROVADO (R.string.documento_status_aprovado, R.drawable.documento_status_aprovado),
        PROCESSANDO (R.string.documento_status_processando, R.drawable.documento_status_processando),
        REJEITADO (R.string.documento_status_rejeitado, R.drawable.documento_status_rejeitado),
        DIGITALIZADO (R.string.documento_status_digitalizado, R.drawable.documento_status_digitalizado);
        public int stringRes;
        public int drawableRes;
        Status(@StringRes int stringRes, @DrawableRes int drawableRes) {
            this.stringRes = stringRes;
            this.drawableRes = drawableRes;
        }
    }
}
