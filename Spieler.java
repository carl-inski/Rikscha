public class Spieler implements Comparable<Spieler> {
    private String name; // private Instanzvariable, die den Namen des Spielers speichert

    public Spieler(String name) {
        this.name = name; // Konstruktor, der den Namen des Spielers setzt
    }
    
    public void setName(String name){
        this.name = name; // Setter-Methode, um den Namen des Spielers zu ändern
    }
    
    public String getName(){
        return name; // Getter-Methode, um den Namen des Spielers abzurufen
    }

    public int compareTo(Spieler other) {
        // Vergleicht zwei Spielerobjekte anhand ihrer Namen
        // Rückgabewert ist ein negativer Integer, wenn der aktuelle Spielername alphabetisch vor dem anderen liegt
        // Rückgabewert ist 0, wenn die Namen gleich sind
        // Rückgabewert ist ein positiver Integer, wenn der aktuelle Spielername alphabetisch nach dem anderen liegt
        return this.name.compareTo(other.name);
    }
}



