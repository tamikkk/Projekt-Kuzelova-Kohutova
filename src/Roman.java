
public class Roman extends Kniha {
	private String zaner;

	public Roman(String nazov, String autor, int rokVydania, String zaner) {
		super(nazov, autor, rokVydania);
		this.zaner = zaner;

	}

	public String getZaner() {
		return zaner;
	}

}
