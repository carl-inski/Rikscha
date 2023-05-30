import java.util.Scanner;
import java.util.Random;

public class Spiel {
    private BinaryTree<Spieler> spielerBaum;
    private Spieler aktuellerSpieler;
    private Spielfeld Feld;
    private Scanner scanner; // Create a single instance of Scanner

    public Spiel() {
        scanner = new Scanner(System.in); // Initialize the scanner instance
        int anzahlSpieler = abfrageAnzahlSpieler();
        spielerBaum = erstelleSpielerBaum(anzahlSpieler);
        Feld = new Spielfeld();
    }

    private int abfrageAnzahlSpieler() {
        System.out.println("Wie viele Spieler nehmen am Spiel teil?");
        return scanner.nextInt();

    }

    private BinaryTree<Spieler> erstelleSpielerBaum(int anzahl) {
        BinaryTree<Spieler> spielerBaum = null;

        for (int i = 1; i <= anzahl; i++) {
            System.out.println("Wie heißt der " + i + ". Spieler?");
            String name = scanner.next();
            Spieler spieler = new Spieler(name);
            if (spielerBaum == null) {
                spielerBaum = new BinaryTree<Spieler>(spieler);
            } else {
                spielerBaum.insert(spieler);
            }
        }

        return spielerBaum;
    }

    private Spieler waehleZufaelligenSpieler(BinaryTree<Spieler> baum) {
        Random random = new Random();
        BinaryTree<Spieler> currentNode = baum;
        
        while (currentNode.getLeftChild() != null || currentNode.getRightChild() != null) {
            // Wenn der aktuelle Knoten einen linken und rechten Kindknoten hat, wähle zufällig einen aus
            if (currentNode.getLeftChild() != null && currentNode.getRightChild() != null) {
                int randomNum = random.nextInt(2);
                if (randomNum == 0) {
                    currentNode = currentNode.getLeftChild();
                } else {
                    currentNode = currentNode.getRightChild();
                }
            }
            // Wenn der aktuelle Knoten nur einen linken Kindknoten hat, gehe zum linken Kindknoten
            else if (currentNode.getLeftChild() != null) {
                currentNode = currentNode.getLeftChild();
            }
            // Wenn der aktuelle Knoten nur einen rechten Kindknoten hat, gehe zum rechten Kindknoten
            else if (currentNode.getRightChild() != null) {
                currentNode = currentNode.getRightChild();
            }
        }
        
        return currentNode.getData();


    }

    private void printKnotenDaten(BinaryTree<Spieler> Knoten, Spieler aktuellerSpieler) {
        if (Knoten.getData() != aktuellerSpieler) {
            System.out.println(Knoten.getData().getName());
        }
        if (Knoten.getLeftChild() != null) {
            printKnotenDaten(Knoten.getLeftChild(), aktuellerSpieler);
        }
        if (Knoten.getRightChild() != null) {
            printKnotenDaten(Knoten.getRightChild(), aktuellerSpieler);
        }
    }

    private void spielzug(Spieler aktSpieler, int counter) {
        if (Feld.alleKartenUmgdreht() == false) {
            Feld.printSpielfeld();
            
            // Alle gültigen Optionen ausgebend
            System.out.println("Gültige Optionen:");
                Feld.istGueltig();

                StringBuilder optionenZeile = new StringBuilder();
                boolean ersteOption = true;
                for (int i = 0; i < Feld.kartenFelder.length; i++) {
                    if (Feld.getKarte(i).isGueltig()) {
                        if (!ersteOption) {
                            optionenZeile.append(", ");
                            }   
                        optionenZeile.append(i+1);
                        ersteOption = false;
                    }
                }

            System.out.println(optionenZeile);
            
            // Spieler wählt eine Option aus
            System.out.println("Bitte wähle eine Option:");
            int ausgewaehlteOption = scanner.nextInt();
            
            // Spieler wird gefragt, ob die Karte höher oder niedriger ist
            System.out.println("Ist die Karte höher(1)/niedriger(2) bzw. innerhalb(1)/außerhalb(2) als die anliegende?");
            String guess = scanner.next();
            System.out.println(guess);
            
            // Überprüfung, ob die Auswahl richtig ist
            boolean istRichtig = Feld.vergleichen(guess, ausgewaehlteOption-1);

            if (istRichtig){
                System.out.println("Das war richtig!");
            }
            else{
                System.out.println("Das war leider falsch!");
            }

            System.out.println("Drücke Enter, um fortzufahren.");
            String weiter = scanner.next();

            
            if (istRichtig) {
                Feld.kartenFelder[ausgewaehlteOption-1].umdrehen();
                counter++;
                // Counter überprüfen
                if (counter >= 2) {
                    // Spieler wird gefragt, ob er das Spiel abgeben möchte
                    System.out.println("Dein Counter ist " + counter + ". Möchtest du das Spiel abgeben? (Ja/Nein)");
                    String antwort = scanner.next();
                    
                    if (antwort.equalsIgnoreCase("Ja")) {
                        // Spiel abgeben, alle Spieler außer dem aktuellen Spieler ausgeben
                        System.out.println("");
                        System.out.println("Verfügbare Spieler: ");

                        //ruft rekursive methode auf, die die Spielernamen ausgibt, außer den Aktuellen
                        BinaryTree<Spieler> currentNode = spielerBaum;
                        printKnotenDaten(spielerBaum, aktSpieler);
                        
                        // Spieler wählt einen der anderen Spieler aus
                        System.out.println("Wähle einen Spieler, der das Spiel fortsetzt:");
                        String ausgewaehlterSpieler = scanner.next();
                        
                        // Neustart der Methode spielzug mit dem ausgewählten Spieler und Counter = 0
                        BinaryTree<Spieler> neuerSpieler = spielerBaum.findNode(ausgewaehlterSpieler);
                        if (neuerSpieler != null) {
                            spielzug(neuerSpieler.getData(), 0);
                        } else {
                            System.out.println("Ungültiger Spielername. Das Spiel endet hier.");
                        }
                        
                        return;
                    }

                    else{
                        System.out.println("Dein Counter ist "+counter+". Du hast dich entschieden, weiterzuspielen!");


                        System.out.println("Drücke Enter, um fortzufahren.");
                        String weiter2 = scanner.next();

                        // Spiel geht weiter, Methode spielzug neu starten mit aktuellem Spieler und Counter erhöht um 1
                        spielzug(aktSpieler, counter);

                    }
                }
                
                System.out.println("Dein Counter ist "+counter+". Du musst noch weiterspielen!");


                System.out.println("Drücke Enter, um fortzufahren.");
                String weiter3 = scanner.next();

                // Spiel geht weiter, Methode spielzug neu starten mit aktuellem Spieler und Counter erhöht um 1
                spielzug(aktSpieler, counter);
            } else {
                // Spieler liegt falsch, Schlücke trinken
                int schluecke = Feld.anliegendeZaehlen(ausgewaehlteOption-1);
                System.out.println("Du musst " + schluecke + " Schlücke trinken.");

                System.out.println("Drücke Enter, wenn du die erforderte Anzahl an Schlücken getrunken hast.");
                String weiter4 = scanner.next();

                Feld.anliegendeAustauschen(ausgewaehlteOption-1);

                spielzug(aktSpieler, 0);

            }
        }
        else {
            System.out.println("Das Spiel ist vorbei. Alle Karten sind aufgedeckt.");
            System.out.println(aktSpieler.getName()+" darf 16 Schlücke verteilen. Danke fürs Spielen!");

        }
    }

    public void altStart(BinaryTree<Spieler> baum) {
        Spieler zufaelligerSpieler = waehleZufaelligenSpieler(baum);
        System.out.println("Der zufällig ausgewählte Spieler ist: " + zufaelligerSpieler.getName());

        System.out.println("Drücke Enter, um fortzufahren.");
        String weiter5 = scanner.nextLine();

        spielzug(zufaelligerSpieler, 0);
    }

    public static void main(String[] args) {
        Spiel spiel = new Spiel();
        spiel.altStart(spiel.spielerBaum);
    }
}









