package Model;

public class carta {
	private String naipe;
	private String numero;

	public carta(String np, String num) {
		naipe = np.toUpperCase();
	    numero = num;
	}

	public String getnaipe() {
	    return naipe;
	}

	public String getNumero() {
	    return numero;
	}

	protected void setnaipe(String np) {
		naipe = np.toUpperCase();
	}

	protected void setNumero(String num) {
	    numero = num;
	}
}