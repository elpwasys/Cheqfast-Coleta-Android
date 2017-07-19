package cheqfast.gfin.wasys.com.br.coleta.model;

import br.com.wasys.library.model.Model;

public class EnderecoModel extends Model {

	public Estado uf;
	public String cep;
	public String numero;
	public String bairro;
	public String cidade;
	public String logradouro;
	public String complemento;

	private String completo;

	public enum Estado {
		AC,
		AL,
		AM,
		AP,
		BA,
		CE,
		DF,
		ES,
		GO,
		MA,
		MG,
		MS,
		MT,
		PA,
		PB,
		PE,
		PI,
		PR,
		RJ,
		RN,
		RO,
		RR,
		RS,
		SC,
		SE,
		SP,
		TO
	}

	public String getCompleto() {
		if (this.completo == null) {
			StringBuilder builder = new StringBuilder();
			if (logradouro != null) {
				builder.append(logradouro);
			}
			if (numero != null) {
				if (builder.length() > 0) {
					builder.append(", ");
				}
				builder.append(numero);
			}
			if (bairro != null) {
				if (builder.length() > 0) {
					builder.append(", ");
				}
				builder.append(bairro);
			}
			if (cidade != null) {
				if (builder.length() > 0) {
					builder.append(", ");
				}
				builder.append(cidade);
			}
			if (uf != null) {
				if (cidade != null) {
					builder.append(" - ");
				}
				else if (builder.length() > 0) {
					builder.append(", ");
				}
				builder.append(uf);
			}
			if (cep != null) {
				if (builder.length() > 0) {
					builder.append(" ");
				}
				builder.append(cep);
			}
			this.completo = String.valueOf(builder);
		}
		return this.completo;
	}
}