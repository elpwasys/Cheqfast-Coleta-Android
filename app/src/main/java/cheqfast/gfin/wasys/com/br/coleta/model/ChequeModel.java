package cheqfast.gfin.wasys.com.br.coleta.model;

import br.com.wasys.library.model.Model;

/**
 * Created by pascke on 18/07/17.
 */

public class ChequeModel extends Model {

    public String nome;
    public String cpfCnpj;

    public String banco;
    public String conta;
    public String agencia;

    public Double valor;
}
