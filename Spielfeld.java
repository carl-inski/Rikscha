import java.util.Random;

public class Spielfeld {
    public Spielkarte[] kartenFelder;
    public Spielkarte[] nachziehStapel;

    public Spielfeld() {
        kartenFelder = new Spielkarte[16];
        nachziehStapel = new Spielkarte[16];
        stapel s = new stapel();
        s.shuffle();
        Spielkarte[] karten = s.getKarten();

        for (int i = 0; i < 16; i++) {
            kartenFelder[i] = karten[i];
        }
        kartenFelder[0].umdrehen();
        kartenFelder[4].umdrehen();
        kartenFelder[8].umdrehen();
        kartenFelder[12].umdrehen();

        for (int i=16; i<32; i++) {
            nachziehStapel[i-16] = karten[i];
        }

    }

    public void setKarte(int pos, Spielkarte karte) {
        kartenFelder[pos] = karte;
    }

    public Spielkarte getKarte(int index) {
        return kartenFelder[index];
    }

    public void printSpielfeld() {
        System.out.println("");
        int[][] viereck = {
            { 1,  2,  3,  4,  5 },
            { 16, 0,  0,  0,  6 },
            { 15, 0,  0,  0,  7 },
            { 14, 0,  0,  0,  8 },
            { 13, 12, 11, 10, 9 }
        };
    
        for (int i = 0; i < viereck.length; i++) {
            for (int j = 0; j < viereck[i].length; j++) {
                int kartenIndex = viereck[i][j] - 1;
    
                if (kartenIndex >= 0 && kartenIndex < kartenFelder.length) {
                    Spielkarte karte = kartenFelder[kartenIndex];
    
                    if (karte.getOffen()) {
                        System.out.print(karte.getId() + "\t");
                    } else {
                        System.out.print("?\t");
                    }
                } else {
                    System.out.print("\t");
                }
            }
    
             // Ausgabe der Zahlen 1-16 daneben
            if (i == 0) {
                System.out.print("   0    1    2    3    4");
            } else if (i == 1) {
                System.out.print("  15                   5");
            } else if (i == 2) {
                System.out.print("  14                   6");
            } else if (i == 3) {
                System.out.print("  13                   7");
            } else if (i == 4) {
                System.out.print("  12    11   10   9    8");
            }
    
            System.out.println(); // Leerzeile einfügen
    
            // Leerzeile zwischen den Zeilen einfügen
            if (i < viereck.length - 1) {
                System.out.println();
            }
        }
    }
    
    
    
    public boolean vergleichen(String guess, int karte) {
        Spielkarte aktuelleKarte = kartenFelder[karte];
        Spielkarte vorherigeKarte = null;
        Spielkarte naechsteKarte = null;

        if (karte == 0) {
            vorherigeKarte = kartenFelder[15];
        }
        else {
            vorherigeKarte = kartenFelder[karte - 1];
        }
        if (karte == kartenFelder.length - 1) {
            naechsteKarte = kartenFelder[0];
        }
        else {
            naechsteKarte = kartenFelder[karte + 1];
        }

        if (vorherigeKarte.getOffen() && !naechsteKarte.getOffen()) {
            // NUR Vorherige Karte ist umgedreht
            return vergleicheMitOffenerKarte(aktuelleKarte, vorherigeKarte, guess);
        } else if (naechsteKarte.getOffen() && !vorherigeKarte.getOffen()) {
            // NUR Nächste Karte ist umgedreht
            return vergleicheMitOffenerKarte(aktuelleKarte, naechsteKarte, guess);
        } else {
            // Beide anliegenden Karten sind aufgedeckt
            return vergleicheMitUmgedrehtenKarten(aktuelleKarte, vorherigeKarte, naechsteKarte, guess);
        }
    }

    private boolean vergleicheMitOffenerKarte(Spielkarte aktuelleKarte, Spielkarte offeneKarte, String guess) {
        int aktuellerWert = aktuelleKarte.getZahlenwert();
        int offenerWert = offeneKarte.getZahlenwert();

        if (guess.equals("Höher") || guess.equals("höher") || guess.equals("1")) {
            return aktuellerWert > offenerWert;
        } else if (guess.equals("Niedriger") || guess.equals("niedriger") || guess.equals("2")) {
            return aktuellerWert < offenerWert;
        } else {
            return false;
        }
    }

    private boolean vergleicheMitUmgedrehtenKarten(Spielkarte aktuelleKarte, Spielkarte vorherigeKarte, Spielkarte naechsteKarte, String guess) {
        if (vorherigeKarte == null || naechsteKarte == null) {
            return false;
        }

        int aktuellerWert = aktuelleKarte.getZahlenwert();
        int vorherigerWert = vorherigeKarte.getZahlenwert();
        int naechsterWert = naechsteKarte.getZahlenwert();

        if (guess.equals("Innerhalb") || guess.equals("innerhalb") || guess.equals("Inerhalb") || guess.equals("inerhalb") || guess.equals("1")) {
            return aktuellerWert > vorherigerWert && aktuellerWert < naechsterWert;
        } else if (guess.equals("Außerhalb") || guess.equals("außerhalb") || guess.equals("Ausserhalb") || guess.equals("ausserhalb") || guess.equals("2")) {
            return aktuellerWert < vorherigerWert || aktuellerWert > naechsterWert;
        } else {
            return false;
        }
    }

    public boolean alleKartenUmgdreht() {
        for (Spielkarte karte : kartenFelder) {
            if (!karte.getOffen()) {
                return false;
            }
        }
        return true;
    }

    public boolean istGueltig() {
        // Alle Karten initial auf ungültig setzen
        for (Spielkarte karte : kartenFelder) {
            karte.setGueltig(false);
        }
    
        int letztePosition = kartenFelder.length - 1;
    
        // Überprüfung der ersten Karte
        if (!kartenFelder[0].getOffen() && (kartenFelder[1].getOffen() || kartenFelder[letztePosition].getOffen())) {
            kartenFelder[0].setGueltig(true);
        }
    
        // Überprüfung der letzten Karte
        if (!kartenFelder[letztePosition].getOffen() && (kartenFelder[0].getOffen() || kartenFelder[letztePosition - 1].getOffen())) {
            kartenFelder[letztePosition].setGueltig(true);
        }
    
        // Überprüfung der restlichen Karten
        for (int i = 1; i < letztePosition; i++) {
            if (!kartenFelder[i].getOffen() && (kartenFelder[i - 1].getOffen() || kartenFelder[i + 1].getOffen())) {
                kartenFelder[i].setGueltig(true);
            }
        }
        
        // Überprüfung, ob mindestens eine Karte gültig ist
        for (Spielkarte karte : kartenFelder) {
            if (karte.isGueltig()) {
                //System.out.println(karte.getId()+ " :"+karte.isGueltig());
                return true;
            }
        }

    
        return false;
    }

public int anliegendeZaehlen(int position) {
    // Überprüfen, ob die übergebene Position gültig ist
    if (position < 0 || position >= kartenFelder.length) {
        System.out.println("Ungültige Position");
        return 0;
    }

    int count = 0;  // Zähler für aufgedeckte Karten

    // Nach vorne zählen
    int i = position;
    if (!kartenFelder[i].getOffen()) {
        kartenFelder[i].umdrehen();
    }

    while (kartenFelder[i].getOffen()) {
        count++;
        if (i == 15){
            i = 0;
        }
        else {
            i++;  // Übergang zur nächsten Karte, kreisförmig
        }
        if (i == position) break;  // Abbrechen, wenn wir wieder zur Ausgangsposition kommen
    }

    if (i == 0){
        i = 15;
    }
    else {
        i = position - 1;
    }

    while (kartenFelder[i].getOffen()) {
        count++;
        if (i == 0){
            i = 15;
        }
        else {
            i--;  // Übergang zur nächsten Karte, kreisförmig
        }
        if (i == position - 1) break;  // Abbrechen, wenn wir wieder zur Ausgangsposition kommen
    }

    return count;
}



public void anliegendeAustauschen(int position) {

    Spielkarte[] tempStapel = new Spielkarte[16];

    // Nach vorne zählen
    int i = position;

    if (!kartenFelder[i].getOffen()) {
        kartenFelder[i].umdrehen();
    }

    for (int j = 0; j < nachziehStapel.length; j++) {
        if (nachziehStapel[j].getOffen()) {
            nachziehStapel[j].umdrehen();
        }
    }

    while (kartenFelder[i].getOffen()) {
        if (i != 0 && i != 4 && i != 8 && i != 12) {
            tempStapel[i] = kartenFelder[i];
            kartenFelder[i] = nachziehStapel[i];
            nachziehStapel[i] = tempStapel[i];
        }

        if (i == 15){
            i = 0;
        }
        else {
            i++; 
        }
        if (i == position) break;  // Abbrechen, wenn wir wieder zur Ausgangsposition kommen
    }

    if (i == 0){
        i = 15;
    }
    else {
        i = position - 1;
    }

    while (kartenFelder[i].getOffen()) {
        
        if (i != 0 && i != 4 && i != 8 && i != 12) {
            tempStapel[i] = kartenFelder[i];
            kartenFelder[i] = nachziehStapel[i];
            nachziehStapel[i] = tempStapel[i];
        }

        if (i == 0){
            i = 15;
        }
        else {
            i--;  // Übergang zur nächsten Karte, kreisförmig
        }
        if (i == position - 1) break;  // Abbrechen, wenn wir wieder zur Ausgangsposition kommen
    }

    //Nachziehstapel durchmischen

    Random r = new Random();
    for (int k = nachziehStapel.length - 1; k > 0; k--) {
        int j = r.nextInt(k + 1);
        Spielkarte temp = nachziehStapel[k];
        nachziehStapel[k] = nachziehStapel[j];
        nachziehStapel[j] = temp;
    }

}



    public void setKartenFelder(Spielkarte[] kartenFelder) {
        this.kartenFelder = kartenFelder;
    }

    public Spielkarte[] getKartenFelder() {
        return kartenFelder;
    }
    
  

    public static void main(String[] args) {
        Spielfeld spielfeld = new Spielfeld();
        spielfeld.printSpielfeld();
    
    int kartenPosition = 5; // Beispielposition

    int anzahlAufgedeckterKarten = spielfeld.anliegendeZaehlen(kartenPosition);
    System.out.println("Anzahl der aufgedeckten Karten: " + anzahlAufgedeckterKarten);
    }

}
