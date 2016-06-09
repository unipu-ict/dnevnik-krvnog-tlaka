package projekt.mobilne.unipu.dnevnikkrvnogtlaka;


public class KrvniTlak {

    // region PRIVATNE VARIJABLE
    private String id;
    private String sistolicki;
    private String dijastolicki;
    private String puls;
    private String vrijeme;
    // endregion

    // region KONSTRUKTOR
    KrvniTlak(String id, String sistolicki, String dijastolicki, String puls, String vrijeme) {
        this.id = id;
        this.sistolicki = sistolicki;
        this.dijastolicki = dijastolicki;
        this.puls = puls;
        this.vrijeme = vrijeme;
    }
    // endregion

    // region JAVNE METODE
    public String getDijastolicki() {
        return dijastolicki;
    }

    public String getId() {
        return id;
    }

    public String getPuls() {
        return puls;
    }

    public String getSistolicki() {
        return sistolicki;
    }

    public String getVrijeme() {
        return vrijeme;
    }

    public String getOpis() {

        StringBuilder sb = new StringBuilder();
        sb.append("Vrijeme: ");
        sb.append("\t\t\t" + getVrijeme() + "\nSistolički: ");
        sb.append("\t\t" + getSistolicki() + " mmHg\nDijastolički: ");
        sb.append("\t" + getDijastolicki() + " mmHg\nPuls:");
        sb.append("\t\t\t\t" + getPuls() + "/min\n");

        return sb.toString();
    }
    // endregion
}
