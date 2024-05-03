import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class DatabazaKnih {

	private static List<Kniha> zoznamKnih = new ArrayList<>();

	public static int pouzeCelaCisla(Scanner sc) {
		int cislo = 0;
		try {
			cislo = sc.nextInt();
		} catch (Exception e) {
			System.out.println("Nastala vyjimka typu " + e.toString());
			System.out.println("zadejte prosim cele cislo ");
			sc.nextLine();
			cislo = pouzeCelaCisla(sc);
		}
		return cislo;
	}

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		int volba;
		boolean run = true;
		while (run) {
			FunkcieDatabazy.nacitatKnihy();
			System.out.println("Vyberte pozadovanou cinnost:");
			System.out.println("1 .. pridanie novej knihy");
			System.out.println("2 .. uprava knihy");
			System.out.println("3 .. zmazanie knihy");
			System.out.println("4 .. oznacenie knihy ako zapozicane/vratene");
			System.out.println("5 .. vypis knih");
			System.out.println("6 .. vyhladanie knihy");
			System.out.println("7 .. vypis knih daneho autora");
			System.out.println("8 .. vypis romanov daneho zanru");
			System.out.println("9 .. vypis podla typu (ucebnica/roman)");
			System.out.println("10 .. ulozenie do suboru");
			System.out.println("11 .. nacitanie zo suboru");
			System.out.println("12 .. ukonceni aplikace");
			volba = pouzeCelaCisla(sc);
			switch (volba) {

			case 1:
				pridanieKnihy(sc);
				break;

			case 2:
				upravaKnihy(sc);
				break;

			case 3:
				mazanieKnihy(sc);
				break;

			case 4:
				dostupnost(sc);
				break;

			case 5:
				vypisKnih();
				break;

			case 6:
				vyhladanieKnihy(sc);
				break;

			case 7:
				vypisKnihPodlaAutora(sc);
				break;

			case 8:
				vypisKnihPodlaZanru(sc);
				break;

			case 9:
				vypisZapozicanychKnih();
				break;

			case 10:
				ulozenieKnihyDoSuboru(sc);
				break;

			case 11:
				nacitanieKnihyZoSuboru(sc);
				break;

			case 12:
				run = false;
				break;

			}

		}

	}

	public static void pridanieKnihy(Scanner sc) {
		System.out.println("Vyberte typ knihy (1 - román, 2 - učebnice): ");
		int typKnihy = 0;
		while (true) {
			try {
				typKnihy = sc.nextInt();
				if (typKnihy == 1 || typKnihy == 2) {
					break;
				} else {
					System.out.println("Můžete vybrat pouze 1 pro román nebo 2 pro učebnici. Zadejte znovu.");
				}
			} catch (InputMismatchException e) {
				System.out.println("Neplatný vstup. Zadejte číslo (1 pro román, 2 pro učebnici).");
				sc.nextLine();
			}

		}
		sc.nextLine();

		System.out.println("Zadajte název knihy: ");
		String nazov = sc.nextLine();

		System.out.println("Zadajte autora knihy: ");
		String autor = sc.nextLine();

		int rokVydania = 0;
		while (true) {
			System.out.println("Zadajte rok vydania knihy: ");
			try {
				rokVydania = sc.nextInt();
				break;
			} catch (InputMismatchException e) {
				System.out.println("Nezadali jste platný rok vydání. Zadejte prosím platný rok.");
				sc.nextLine();
			}

		}

		Kniha novaKniha = null;
		String zaner = "";
		int rocnik = 0;

		if (typKnihy == 1) {
			System.out.println("Zadajte zaner romanu: ");
			sc.nextLine();
			zaner = sc.nextLine();
			novaKniha = new Roman(nazov, autor, rokVydania, zaner);
		}

		else if (typKnihy == 2) {
			System.out.println("Zadajte rocnik, pre ktory je ucebnica urcena: ");
			int rocnik1;
			while (true) {
				try {
					rocnik1 = sc.nextInt();
					break;
				} catch (InputMismatchException e) {
					System.out.println("Neplatný vstup. Zadejte prosím rocnik znovu.");
					sc.nextLine();
				}
			}
			novaKniha = new Ucebnica(nazov, autor, rokVydania, rocnik1);
		} else {
			System.out.println("Neplatny vyber typu knihy");
			return;
		}
		zoznamKnih.add(novaKniha);

		System.out.println("Nová kniha bola uspesne pridana:");
		System.out.println("Názov: " + novaKniha.getNazov());
		System.out.print("Autor: " + autor);

		System.out.println("\nRok vydania: " + novaKniha.getRokVydania());

		if (novaKniha instanceof Ucebnica) {
			System.out.println("Rocnik, pre ktory je urcena: " + ((Ucebnica) novaKniha).getRocnik());
		} else if (novaKniha instanceof Roman) {
			System.out.println("Zaner knihy: " + ((Roman) novaKniha).getZaner());
		}

		FunkcieDatabazy.ulozKnihu(nazov, rokVydania, autor, zaner, rocnik, "");

	}

	public static void dostupnost(Scanner sc) {
		System.out.println("Zadajte nazov knihy, ktoru chcete vratit alebo zapoziciat:  ");
		String nazov = sc.next();

		System.out.println("Chcete knihu vratit alebo zapoziciat? (vracanie - 1, zapozicanie - 2): ");
		int volba;
		while (true) {
			try {
				volba = sc.nextInt();
				if (volba == 1 || volba == 2) {
					break;
				} else {
					System.out.println(
							"Můžete zadat pouze 1 pro vrácení knihy nebo 2 pro zapůjčení knihy. Zadejte znovu:");
				}
			} catch (InputMismatchException e) {
				System.out.println("Neplatný vstup. Zadejte číslo (1 pro vrácení, 2 pro zapůjčení):");
				sc.nextLine();
			}
		}

		boolean knihaNajdena = false;
		for (Kniha kniha : zoznamKnih) {

			if (kniha.getNazov().equalsIgnoreCase(nazov)) {
				knihaNajdena = true;

				if (volba == 1) {
					kniha.setStavDostupnosti(Kniha.StavDostupnosti.VRATENA);
					System.out.println("Kniha '" + nazov + "' bola označena ako vrátená.");

				} else if (volba == 2) {
					kniha.setStavDostupnosti(Kniha.StavDostupnosti.ZAPOZICANA);
					System.out.println("Kniha '" + nazov + "' bola označena ako zapozicana.");

				} else {
					System.out.println("Neplatná volba.");
					return;
				}
				break;
			}
		}

		if (!knihaNajdena) {
			System.out.println("Kniha '" + nazov + "' nebyla nalezena.");
		}
	}

	public static void upravaKnihy(Scanner sc) {
		System.out.println("Zadejte název knihy, kterou chcete upravit: ");
		String nazovUprava = sc.next();

		boolean knihaNajdena = false;

		for (Kniha kniha : zoznamKnih) {
			if (kniha.getNazov().equalsIgnoreCase(nazovUprava)) {
				knihaNajdena = true;

				System.out.println("Vyberte, co chcete upravit:");
				System.out.println("1 - Autor");
				System.out.println("2 - Rok vydání");
				System.out.println("3 - Stav dostupnosti");

				int volba = pouzeCelaCisla(sc);
				switch (volba) {
				case 1:
					System.out.println("Zadejte nového autora: ");
					sc.nextLine();
					String autor = sc.nextLine();
					kniha.setAutor(autor);
					System.out.println("Autor knihy '" + nazovUprava + "' byli aktualizováni.");
					break;
				case 2:
					System.out.println("Zadejte nový rok vydání: ");
					int rokVydania = pouzeCelaCisla(sc);
					kniha.setRokVydania(rokVydania);
					System.out.println("Rok vydání knihy '" + nazovUprava + "' byl aktualizován.");
					break;
				case 3:
					System.out.println("Zadejte nový stav dostupnosti (VRATENA/ZAPOZICANA): ");
					String stav = sc.next().toUpperCase();
					Kniha.StavDostupnosti novyStav;
					if (stav.equals("VRATENA")) {
						novyStav = Kniha.StavDostupnosti.VRATENA;
					} else if (stav.equals("ZAPOZICANA")) {
						novyStav = Kniha.StavDostupnosti.ZAPOZICANA;
					} else {
						System.out.println("Neplatná volba stavu dostupnosti.");
						return;
					}
					kniha.setStavDostupnosti(novyStav);
					System.out.println("Stav dostupnosti knihy '" + nazovUprava + "' byl aktualizován.");
					break;
				default:
					System.out.println("Neplatná volba.");
					return;
				}
				break;
			}
		}

		if (!knihaNajdena) {
			System.out.println("Kniha '" + nazovUprava + "' nebyla nalezena.");
		}

	}

	public static void mazanieKnihy(Scanner sc) {
		System.out.println("Zadejte nazov knihy, ktoru chcete zmazat: ");
		String knihaNazov = sc.next();

		boolean knihaNajdena = false;

		for (int i = 0; i < zoznamKnih.size(); i++) {
			Kniha kniha = zoznamKnih.get(i);
			if (kniha.getNazov().equalsIgnoreCase(knihaNazov)) {
				zoznamKnih.remove(i);
				knihaNajdena = true;
				System.out.println("Kniha '" + knihaNazov + "' byla smazána.");
				break;
			}
		}

		if (!knihaNajdena) {
			System.out.println("Kniha '" + knihaNazov + "' nebyla nalezena.");
		}
	}

	public static void vypisKnih() {
		if (zoznamKnih.isEmpty()) {
			System.out.println("Neexistujú žiadne knihy v databáze.");
			return;
		}

		Collections.sort(zoznamKnih, Comparator.comparing(Kniha::getNazov));

		System.out.println("Seznam knih v abecedním pořadí:");
		for (Kniha kniha : zoznamKnih) {
			System.out.println("Název: " + kniha.getNazov());
			System.out.print("Autor: " + kniha.getAutor());
			System.out.println("\nRok vydání: " + kniha.getRokVydania());
			System.out.println("Stav dostupnosti: " + kniha.getStavDostupnosti());

			if (kniha instanceof Roman) {
				System.out.println("Žánr: " + ((Roman) kniha).getZaner());
			} else if (kniha instanceof Ucebnica) {
				System.out.println("Ročník: " + ((Ucebnica) kniha).getRocnik());
			}

			System.out.println();
		}

	}

	public static void vyhladanieKnihy(Scanner sc) {
		System.out.println("Zadejte název knihy, kterou chcete vyhledat: ");
		String hladanyNazov = sc.next();

		boolean knihaNajdena = false;

		for (Kniha kniha : zoznamKnih) {
			if (kniha.getNazov().equalsIgnoreCase(hladanyNazov)) {
				knihaNajdena = true;

				System.out.println("Název: " + kniha.getNazov());
				System.out.print("Autor: " + kniha.getAutor());
				System.out.println("\nRok vydání: " + kniha.getRokVydania());
				System.out.println("Stav dostupnosti: " + kniha.getStavDostupnosti());

				if (kniha instanceof Roman) {
					System.out.println("Žánr: " + ((Roman) kniha).getZaner());
				} else if (kniha instanceof Ucebnica) {
					System.out.println("Ročník: " + ((Ucebnica) kniha).getRocnik());
				}

				System.out.println();
				break;
			}
		}

		if (!knihaNajdena) {
			System.out.println("Kniha s názvem '" + hladanyNazov + "' nebyla nalezena.");
		}

	}

	public static void vypisKnihPodlaAutora(Scanner sc) {
		System.out.println("Zadejte jméno autora, jehož knihy chcete vypsat: ");
		String hledanyAutor = sc.next();

		ArrayList<Kniha> knihyAutora = new ArrayList<>();

		for (Kniha kniha : zoznamKnih) {

			if (kniha.getAutor().equalsIgnoreCase(hledanyAutor)) {
				knihyAutora.add(kniha);
				break;
			}
		}

		if (knihyAutora.isEmpty()) {
			System.out.println("Kniha od autora '" + hledanyAutor + "' nebyla nalezena.");
			return;
		}
		Collections.sort(knihyAutora, Comparator.comparingInt(Kniha::getRokVydania));
		// Collections.sort(zoznamKnih, Comparator.comparing(Kniha::getNazov));

		System.out.println("Seznam knih autora '" + hledanyAutor + "' v chronologickém pořadí:");
		for (Kniha kniha : knihyAutora) {
			System.out.println("Název: " + kniha.getNazov());
			System.out.print("Autor: " + kniha.getAutor());
			System.out.println("\nRok vydání: " + kniha.getRokVydania());
			System.out.println("Stav dostupnosti: " + kniha.getStavDostupnosti());

			if (kniha instanceof Roman) {
				System.out.println("Žánr: " + ((Roman) kniha).getZaner());
			} else if (kniha instanceof Ucebnica) {
				System.out.println("Ročník: " + ((Ucebnica) kniha).getRocnik());
			}

			System.out.println();
		}

	}

	public static void vypisKnihPodlaZanru(Scanner sc) {
		System.out.println("Zadejte žánr knih, které chcete vypsat: ");
		String hledanyZanr = sc.next();

		ArrayList<Kniha> knihyPodleZanru = new ArrayList<>();

		for (Kniha kniha : zoznamKnih) {
			if (kniha instanceof Roman) {
				Roman roman = (Roman) kniha;
				if (roman.getZaner().equalsIgnoreCase(hledanyZanr)) {
					knihyPodleZanru.add(kniha);
				}
			}
		}

		if (knihyPodleZanru.isEmpty()) {
			System.out.println("Žádné knihy v žánru '" + hledanyZanr + "' nebyly nalezeny.");
			return;
		}
		System.out.println("Seznam knih v žánru '" + hledanyZanr + "':");
		for (Kniha kniha : knihyPodleZanru) {
			System.out.println("Název: " + kniha.getNazov());
			System.out.print("Autor: " + kniha.getAutor());
			System.out.println("\nRok vydání: " + kniha.getRokVydania());
			System.out.println("Stav dostupnosti: " + kniha.getStavDostupnosti());
			System.out.println("Žánr: " + ((Roman) kniha).getZaner());

			System.out.println();
		}
	}

	public static void vypisZapozicanychKnih() {
		boolean existujeVypujcenaKnih = false;

		System.out.println("Výpis vypůjčených knih:");
		for (Kniha kniha : zoznamKnih) {
			if (kniha.getStavDostupnosti() == Kniha.StavDostupnosti.ZAPOZICANA) {
				existujeVypujcenaKnih = true;
				System.out.println("Název: " + kniha.getNazov());
				System.out.print("Typ knihy: ");

				if (kniha instanceof Roman) {
					System.out.println("Román");
				} else if (kniha instanceof Ucebnica) {
					System.out.println("Učebnice");
				}
				System.out.println();
			}
		}

		if (!existujeVypujcenaKnih) {
			System.out.println("Žádné knihy nejsou vypůjčené.");
		}
	}

	public static void ulozenieKnihyDoSuboru(Scanner sc) {
		System.out.println("Zadejte název knihy, kterou chcete uložit do souboru: ");
		String hledanyNazov = sc.next();

		boolean knihaNajdena = false;

		for (Kniha kniha : zoznamKnih) {
			if (kniha.getNazov().equalsIgnoreCase(hledanyNazov)) {
				knihaNajdena = true;
				String souborNazev = "informace_o_knize" + kniha.getNazov() + ".txt";

				try (PrintWriter writer = new PrintWriter(new FileWriter(souborNazev))) {
					writer.println("Název: " + kniha.getNazov());
					writer.print("Autor: " + kniha.getAutor());
					writer.println("\nRok vydání: " + kniha.getRokVydania());
					writer.println("Stav dostupnosti: " + kniha.getStavDostupnosti());

					if (kniha instanceof Roman) {
						writer.println("Žánr: " + ((Roman) kniha).getZaner());
					} else if (kniha instanceof Ucebnica) {
						writer.println("Ročník: " + ((Ucebnica) kniha).getRocnik());
					}
					System.out.println("Informace o knize '" + kniha.getNazov() + "' byly uloženy do souboru '"
							+ souborNazev + "'.");

				} catch (IOException e) {
					System.out.println("Chyba při zápisu do souboru: " + e.getMessage());
				}
				break;
			}
		}

		if (!knihaNajdena) {
			System.out.println("Kniha s názvem '" + hledanyNazov + "' nebyla nalezena.");
		}
	}

	public static void nacitanieKnihyZoSuboru(Scanner sc) {
		System.out.println("Zadejte název souboru obsahující informace o knize: ");
		String souborNazev = sc.next();

		try (BufferedReader reader = new BufferedReader(new FileReader(souborNazev))) {
			String line;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}
		} catch (IOException e) {
			System.out.println("Chyba při čtení ze souboru: " + e.getMessage());
		}

	}

}
