package cheqfast.gfin.wasys.com.br.coleta.model;

import java.util.List;

import br.com.wasys.library.model.Model;

/**
 * Created by pascke on 24/06/17.
 */

public class CampoGrupoModel extends Model {

    public String nome;
    public Integer ordem;

    public List<CampoModel> campos;
}