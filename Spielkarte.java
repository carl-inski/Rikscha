public class Spielkarte {
    private String wert;
    private int zahlenWert;
    private String farbe;
    private String id;
    private boolean offen;
    private boolean gueltig;

    public Spielkarte(String wert, String farbe, int zahlenWert, String id) {
        this.wert = wert;
        this.farbe = farbe;
        this.zahlenWert = zahlenWert;
        this.offen = false;
        this.gueltig = false;
        this.id = id;
    }

    public String getWert() {
        return wert;
    }

    public void setWert(String wert) {
        this.wert = wert;
    }

    public int getZahlenwert() {
        return zahlenWert;
    }

    public void setZahlenwert(int zahlenwert) {
        this.zahlenWert = zahlenwert;
    }

    public String getFarbe() {
        return farbe;
    }

    public void setFarbe(String farbe) {
        this.farbe = farbe;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean getOffen() {
        return offen;
    }

    public void umdrehen() {
        offen = !offen;
    }

    public boolean isGueltig() {
        return gueltig;
    }

    public void setGueltig(boolean gueltig) {
        this.gueltig = gueltig;
    }
}

