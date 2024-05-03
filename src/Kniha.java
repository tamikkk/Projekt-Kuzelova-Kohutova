
public class Kniha {

	private String nazov;
	private String autor;
	private int rokVydania;
	private StavDostupnosti stavDostupnosti;

	public Kniha(String nazov, String autor, int rokVydania) {
		this.nazov = nazov;
		this.autor = autor;
		this.rokVydania = rokVydania;
		stavDostupnosti = stavDostupnosti.NEOZNACENA;

	}

	public String getNazov() {
		return nazov;
	}

	public void setNazov(String nazov) {
		this.nazov = nazov;

	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public int getRokVydania() {
		return rokVydania;
	}

	public void setRokVydania(int rokVydania) {
		this.rokVydania = rokVydania;
	}

	public StavDostupnosti getStavDostupnosti() {
		return stavDostupnosti;
	}

	public void setStavDostupnosti(StavDostupnosti stavDostupnosti) {
		this.stavDostupnosti = stavDostupnosti;
	}

	public enum StavDostupnosti {
		VRATENA, ZAPOZICANA, NEOZNACENA
	}

}
