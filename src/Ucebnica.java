
public class Ucebnica extends Kniha {

	private int rocnik;

	public Ucebnica(String nazov, String autor, int rokVydania, int rocnik) {
		super(nazov, autor, rokVydania);
		this.rocnik = rocnik;
	}

	public int getRocnik() {
		return rocnik;
	}

}
