package projekt.mobilne.unipu.dnevnikkrvnogtlaka;

import android.content.Context;


public class Validacija {

    // region PRIVATNE VARIJABLE
    private Context context;
    private final int SISTOLICKI_MAX = 200;
    private final int DIJASTOLICKI_MAX = 200;
    private final int PULS_MAX = 200;
    private final int SISTOLICKI_MIN = 1;
    private final int DIJASTOLICKI_MIN = 1;
    private final int PULS_MIN = 1;
    // endregion

    // region JAVNE METODE
    public Validacija(Context context) {
        this.context = context;
    }

    public boolean validiraj(int sistolicki, int diastolicki, int puls) {

        if(sistolicki > SISTOLICKI_MAX || diastolicki > DIJASTOLICKI_MAX || puls > PULS_MAX || sistolicki < SISTOLICKI_MIN || diastolicki < DIJASTOLICKI_MIN || puls < PULS_MIN) {
            // Evidentiranje pogreške prilikom unosa
            return false;
        }
        // Unos je ispravan
        return true;
    }
    // endregion
}