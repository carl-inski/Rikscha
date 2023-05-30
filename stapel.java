import java.util.Random;

public class stapel {
    private Spielkarte[] karten;

    public stapel() {
        // Erstellt ein Kartendeck mit allen Karten eines Bayerischen Blatts, jedem Wert
        // wird ein "zahlenWert" zugeordnet, zum einfacheren Vergleichen der Karten.
        karten = new Spielkarte[32];

        String[] farben = { "Eichel", "Gras", "Herz", "Schelln" };
        String[] werte = { "7", "8", "9", "10", "Unter", "Ober", "König", "Ass" };

        int zahlenWert = 0;
        int index = 0;
        for (String wert : werte) {
            for (String farbe : farben) {
                String kuerzel;
                if (wert.equals("Unter")) {
                    kuerzel = "U_" + farbe.charAt(0);
                } else if (wert.equals("Ober")) {
                    kuerzel = "O_" + farbe.charAt(0);
                } else if (wert.equals("König")) {
                    kuerzel = "K_" + farbe.charAt(0);
                } else if (wert.equals("Ass")) {
                    kuerzel = "A_" + farbe.charAt(0);
                } else {
                    kuerzel = wert + "_" + farbe.charAt(0);
                }
                karten[index] = new Spielkarte(farbe, wert, zahlenWert, kuerzel);
                index++;
            }
            zahlenWert++;
        }
    }

    // gibt das Kartendeck auf der Konsole aus
    public void printStapel() {
        for (int i = 0; i < karten.length; i++) {
            Spielkarte karte = karten[i];
            System.out.println(karte.getFarbe() + " " + karte.getWert() + " (ID: " + karte.getId() + ")");
        }
    }

    public Spielkarte[] getKarten() {
        return karten;
    }

    // Funktion mischt die Karten
    public void shuffle() {
        Random r = new Random();
        for (int i = karten.length - 1; i > 0; i--) {
            int j = r.nextInt(i + 1);
            Spielkarte temp = karten[i];
            karten[i] = karten[j];
            karten[j] = temp;
        }
    }

public static void main(String[] args){
stapel s = new stapel();
s.shuffle();
s.printStapel();

}
}
