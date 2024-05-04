import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

import java.io.IOException;
import java.util.InputMismatchException;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import java.util.Scanner;

public class DatabazaKnih {

	private static List<Kniha> zoznamKnih = new ArrayList<>();

	public static int pouzeCelaCisla(Scanner sc) {
		int cislo = 0;
		try {
			cislo = sc.nextInt();
		} catch (Exception e) {
			System.out.println("Nastala výnimka typu " + e.toString());
			System.out.println("Zadajte prosím celé číslo ");
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
			System.out.println("Vyberte požadovanú činnosť:");
			System.out.println("1 .. pridanie novej knihy");
			System.out.println("2 .. úprava knihy");
			System.out.println("3 .. zmazanie knihy");
			System.out.println("4 .. označenie knihy ako zapozičaná/vrátená");
			System.out.println("5 .. výpis kníh");
			System.out.println("6 .. vyhľadanie knihy");
			System.out.println("7 .. výpis kníh daného autora");
			System.out.println("8 .. výpis románov daného žánru");
			System.out.println("9 .. výpis podľa typu (učebnica/román)");
			System.out.println("10 .. uloženie do súboru");
			System.out.println("11 .. načítanie zo súboru");
			System.out.println("12 .. ukončenie aplikácie");
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
		System.out.println("Vyberte typ knihy (1 - román, 2 - učebnica): ");
		int typKnihy = 0;
		while (true) {
			try {
				typKnihy = sc.nextInt();
				if (typKnihy == 1 || typKnihy == 2) {
					break;
				} else {
					System.out.println("Možete vybrať 1 pre román alebo 2 pre učebnicu. Zadajte znova.");
				}
			} catch (InputMismatchException e) {
				System.out.println("Neplatný vstup. Zadajte číslo (1 pre román, 2 pre učebnicu).");
				sc.nextLine();
			}

		}
		sc.nextLine();

		System.out.println("Zadajte názov knihy: ");
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
				System.out.println("Nezadali ste platný rok vydania. Zadajte platný rok.");
				sc.nextLine();
			}

		}

		Kniha novaKniha = null;
		String zaner = "";
		int rocnik = 0;

		if (typKnihy == 1) {
			System.out.println("Zadajte žáner románu: ");
			sc.nextLine();
			zaner = sc.nextLine();
			novaKniha = new Roman(nazov, autor, rokVydania, zaner);
		}

		else if (typKnihy == 2) {
			System.out.println("Zadajte ročník, pre ktorý je učebnica určená: ");
			int rocnik1;
			while (true) {
				try {
					rocnik1 = sc.nextInt();
					break;
				} catch (InputMismatchException e) {
					System.out.println("Neplatný vstup. Zadajte ročník znova.");
					sc.nextLine();
				}
			}
			novaKniha = new Ucebnica(nazov, autor, rokVydania, rocnik1);
		} else {
			System.out.println("Neplatný výber typu knihy");
			return;
		}
		zoznamKnih.add(novaKniha);

		System.out.println("Nová kniha bola úspešne pridaná:");
		System.out.println("Názov: " + novaKniha.getNazov());
		System.out.print("Autor: " + autor);
		System.out.println("\nRok vydania: " + novaKniha.getRokVydania());

		if (novaKniha instanceof Ucebnica) {
			System.out.println("Ročnk, pre ktorý je určená: " + ((Ucebnica) novaKniha).getRocnik());
		} else if (novaKniha instanceof Roman) {
			System.out.println("Žáner knihy: " + ((Roman) novaKniha).getZaner());
		}

		FunkcieDatabazy.ulozKnihu(nazov, rokVydania, autor, zaner, rocnik, "");

	}

	public static void dostupnost(Scanner sc) {
		System.out.println("Zadajte názov knihy, ktorú chcete vrátiť alebo zapožičať:  ");
		String nazov = sc.next();

		System.out.println("Chcete knihu vrátiť alebo zapožičať? (vracanie - 1, zapožičanie - 2): ");
		int volba;
		while (true) {
			try {
				volba = sc.nextInt();
				if (volba == 1 || volba == 2) {
					break;
				} else {
					System.out
							.println("Môžete zadať 1 pre vrátenie knihy alebo 2 pre zapožičanie knihy. Zadajte znova:");
				}
			} catch (InputMismatchException e) {
				System.out.println("Neplatný vstup. Zadajte číslo (1 pre vracanie, 2 pre zapožičanie):");
				sc.nextLine();
			}
		}

		boolean knihaNajdena = false;
		for (Kniha kniha : zoznamKnih) {

			if (kniha.getNazov().equalsIgnoreCase(nazov)) {
				knihaNajdena = true;

				if (volba == 1) {
					kniha.setStavDostupnosti(Kniha.StavDostupnosti.VRATENA);
					System.out.println("Kniha '" + nazov + "' bola označená ako vrátená.");

				} else if (volba == 2) {
					kniha.setStavDostupnosti(Kniha.StavDostupnosti.ZAPOZICANA);
					System.out.println("Kniha '" + nazov + "' bola označená ako zapožicaná.");

				} else {
					System.out.println("Neplatná voľba.");
					return;
				}
				break;
			}
		}

		if (!knihaNajdena) {
			System.out.println("Kniha '" + nazov + "' nebola nájdená.");
		}
	}

	public static void upravaKnihy(Scanner sc) {
		System.out.println("Zadajte názov knihy, ktorú chcete upraviť: ");
		String nazovUprava = sc.next();

		boolean knihaNajdena = false;

		for (Kniha kniha : zoznamKnih) {
			if (kniha.getNazov().equalsIgnoreCase(nazovUprava)) {
				knihaNajdena = true;

				System.out.println("Vyberte, čo chcete upraviť:");
				System.out.println("1 - Autor");
				System.out.println("2 - Rok vydania");
				System.out.println("3 - Stav dostupnosti");

				int volba = pouzeCelaCisla(sc);
				switch (volba) {
				case 1:
					System.out.println("Zadajte nového autora: ");
					sc.nextLine();
					String autor = sc.nextLine();
					kniha.setAutor(autor);
					System.out.println("Autor knihy '" + nazovUprava + "' bol aktualizovaný.");
					break;
				case 2:
					System.out.println("Zadajte nový rok vydania: ");
					int rokVydania = pouzeCelaCisla(sc);
					kniha.setRokVydania(rokVydania);
					System.out.println("Rok vydania knihy '" + nazovUprava + "' bol aktualizovaný.");
					break;
				case 3:
					System.out.println("Zadajte nový stav dostupnosti (VRATENA/ZAPOZICANA): ");
					String stav = sc.next().toUpperCase();
					Kniha.StavDostupnosti novyStav;
					if (stav.equals("VRATENA")) {
						novyStav = Kniha.StavDostupnosti.VRATENA;
					} else if (stav.equals("ZAPOZICANA")) {
						novyStav = Kniha.StavDostupnosti.ZAPOZICANA;
					} else {
						System.out.println("Nepľatná voľba stavu dostupnosti.");
						return;
					}
					kniha.setStavDostupnosti(novyStav);
					System.out.println("Stav dostupnosti knihy '" + nazovUprava + "' bol aktualizovaný.");
					break;
				default:
					System.out.println("Neplatná voľba.");
					return;
				}
				break;
			}
		}

		if (!knihaNajdena) {
			System.out.println("Kniha '" + nazovUprava + "' nebola nájdená.");
		}

	}

	public static void mazanieKnihy(Scanner sc) {
		System.out.println("Zadajte názov knihy, ktorú chcete zmazať: ");
		String knihaNazov = sc.next();

		boolean knihaNajdena = false;

		for (int i = 0; i < zoznamKnih.size(); i++) {
			Kniha kniha = zoznamKnih.get(i);
			if (kniha.getNazov().equalsIgnoreCase(knihaNazov)) {
				zoznamKnih.remove(i);
				knihaNajdena = true;
				System.out.println("Kniha '" + knihaNazov + "' bola zmazaná.");
				break;
			}
		}

		if (!knihaNajdena) {
			System.out.println("Kniha '" + knihaNazov + "'nebola nájdená.");
		}
	}

	public static void vypisKnih() {
		if (zoznamKnih.isEmpty()) {
			System.out.println("Neexistujú žiadne knihy v databáze.");
			return;
		}

		Collections.sort(zoznamKnih, Comparator.comparing(Kniha::getNazov));

		System.out.println("Zoznam kníh podľa abecedy: ");
		for (Kniha kniha : zoznamKnih) {
			System.out.println("Názov: " + kniha.getNazov());
			System.out.print("Autor: " + kniha.getAutor());
			System.out.println("\nRok vydania: " + kniha.getRokVydania());
			System.out.println("Stav dostupnosti: " + kniha.getStavDostupnosti());

			if (kniha instanceof Roman) {
				System.out.println("Žáner: " + ((Roman) kniha).getZaner());
			} else if (kniha instanceof Ucebnica) {
				System.out.println("Ročník: " + ((Ucebnica) kniha).getRocnik());
			}

			System.out.println();
		}

	}

	public static void vyhladanieKnihy(Scanner sc) {
		System.out.println("Zadajte názov knihy, ktorú chcete vyhľadať: ");
		String hladanyNazov = sc.next();

		boolean knihaNajdena = false;

		for (Kniha kniha : zoznamKnih) {
			if (kniha.getNazov().equalsIgnoreCase(hladanyNazov)) {
				knihaNajdena = true;

				System.out.println("Názov: " + kniha.getNazov());
				System.out.print("Autor: " + kniha.getAutor());
				System.out.println("\nRok vydania: " + kniha.getRokVydania());
				System.out.println("Stav dostupnosti: " + kniha.getStavDostupnosti());

				if (kniha instanceof Roman) {
					System.out.println("Žáner: " + ((Roman) kniha).getZaner());
				} else if (kniha instanceof Ucebnica) {
					System.out.println("Ročník: " + ((Ucebnica) kniha).getRocnik());
				}

				System.out.println();
				break;
			}
		}

		if (!knihaNajdena) {
			System.out.println("Kniha s názvom '" + hladanyNazov + "' nebola nájdená.");
		}

	}

	public static void vypisKnihPodlaAutora(Scanner sc) {
		System.out.println("Zadajte meno autora, ktorého knihy chcete vypísať: ");
		String hladanyAutor = sc.next();

		ArrayList<Kniha> knihyAutora = new ArrayList<>();

		for (Kniha kniha : zoznamKnih) {

			if (kniha.getAutor().equalsIgnoreCase(hladanyAutor)) {
				knihyAutora.add(kniha);
				break;
			}
		}

		if (knihyAutora.isEmpty()) {
			System.out.println("Kniha od autora '" + hladanyAutor + "' nebola nájdená.");
			return;
		}
		Collections.sort(knihyAutora, Comparator.comparingInt(Kniha::getRokVydania));

		System.out.println("Zoznam kníh autora '" + hladanyAutor + "' v chronologickej postupnosti:");

		for (Kniha kniha : knihyAutora) {
			System.out.println("Názov: " + kniha.getNazov());
			System.out.print("Autor: " + kniha.getAutor());
			System.out.println("\nRok vydania: " + kniha.getRokVydania());
			System.out.println("Stav dostupnosti: " + kniha.getStavDostupnosti());

			if (kniha instanceof Roman) {
				System.out.println("Žáner: " + ((Roman) kniha).getZaner());
			} else if (kniha instanceof Ucebnica) {
				System.out.println("Ročník: " + ((Ucebnica) kniha).getRocnik());
			}

			System.out.println();
		}

	}

	public static void vypisKnihPodlaZanru(Scanner sc) {
		System.out.println("Zadajte žáner kníh, ktoré chcete vypísať: ");
		String hladanyZanr = sc.next();

		ArrayList<Kniha> knihyPodleZanru = new ArrayList<>();

		for (Kniha kniha : zoznamKnih) {
			if (kniha instanceof Roman) {
				Roman roman = (Roman) kniha;
				if (roman.getZaner().equalsIgnoreCase(hladanyZanr)) {
					knihyPodleZanru.add(kniha);
				}
			}
		}

		if (knihyPodleZanru.isEmpty()) {
			System.out.println("Žiadne knihy v žánri '" + hladanyZanr + "' neboli nájdené.");
			return;
		}
		System.out.println("Zoznam kníh v žánri '" + hladanyZanr + "':");
		for (Kniha kniha : knihyPodleZanru) {
			System.out.println("Názov: " + kniha.getNazov());
			System.out.print("Autor: " + kniha.getAutor());
			System.out.println("\nRok vydania: " + kniha.getRokVydania());
			System.out.println("Stav dostupnosti: " + kniha.getStavDostupnosti());
			System.out.println("Žáner: " + ((Roman) kniha).getZaner());

			System.out.println();
		}
	}

	public static void vypisZapozicanychKnih() {
		boolean existujeZapozicanaKniha = false;

		System.out.println("Výpis zapožičaných kníh:");
		for (Kniha kniha : zoznamKnih) {
			if (kniha.getStavDostupnosti() == Kniha.StavDostupnosti.ZAPOZICANA) {
				existujeZapozicanaKniha = true;
				System.out.println("Názov: " + kniha.getNazov());
				System.out.print("Typ knihy: ");

				if (kniha instanceof Roman) {
					System.out.println("Román");
				} else if (kniha instanceof Ucebnica) {
					System.out.println("Učebnica");
				}
				System.out.println();
			}
		}

		if (!existujeZapozicanaKniha) {
			System.out.println("Žiadne knihy nie sú zapožičané.");
		}
	}

	public static void ulozenieKnihyDoSuboru(Scanner sc) {
		System.out.println("Zadajte názov knihy, ktorú chcete uložiť do súboru: ");
		String hladanyNazov = sc.next();

		boolean knihaNajdena = false;

		for (Kniha kniha : zoznamKnih) {
			if (kniha.getNazov().equalsIgnoreCase(hladanyNazov)) {
				knihaNajdena = true;
				String souborNazev = "informace_o_knihe" + kniha.getNazov() + ".txt";

				try (PrintWriter writer = new PrintWriter(new FileWriter(souborNazev))) {
					writer.println("Názov: " + kniha.getNazov());
					writer.print("Autor: " + kniha.getAutor());
					writer.println("\nRok vydania: " + kniha.getRokVydania());
					writer.println("Stav dostupnosti: " + kniha.getStavDostupnosti());

					if (kniha instanceof Roman) {
						writer.println("Žáner: " + ((Roman) kniha).getZaner());
					} else if (kniha instanceof Ucebnica) {
						writer.println("Ročník: " + ((Ucebnica) kniha).getRocnik());
					}
					System.out.println("Informácie o knihe '" + kniha.getNazov() + "' boli uložené do súboru '"
							+ souborNazev + "'.");

				} catch (IOException e) {
					System.out.println("Chyba pri zápise do súboru: " + e.getMessage());
				}
				break;
			}
		}

		if (!knihaNajdena) {
			System.out.println("Kniha s názvom '" + hladanyNazov + "' nebola nájdená..");
		}
	}

	public static void nacitanieKnihyZoSuboru(Scanner sc) {
		System.out.println("Zadajte názov súboru obsahujúci informácie o knihe: ");
		String suborNazov = sc.next();

		try (BufferedReader reader = new BufferedReader(new FileReader(suborNazov))) {
			String line;
			line = reader.readLine();
			while (line != null) {
				System.out.println(line);
			}
		} catch (IOException e) {
			System.out.println("Chyba pri načítavaní zo súboru: " + e.getMessage());
		}

	}

}
