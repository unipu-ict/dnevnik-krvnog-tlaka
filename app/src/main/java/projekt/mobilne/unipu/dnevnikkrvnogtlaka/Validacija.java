package projekt.mobilne.unipu.dnevnikkrvnogtlaka;

import android.content.Context;
import android.widget.Toast;


public class Validacija {

    private Context context;
    private final int SISTOLICKI_MAX = 155;

    public Validacija(Context context) {
        this.context = context;
    }

    public boolean validiraj(int sistolicki, int diastolicki, int puls) {

        if(sistolicki > 200 || diastolicki > 200 || puls > 200 || sistolicki < 1 || diastolicki < 1 || puls < 1) {
            Toast.makeText(context, "Greška prilikom unosa!", Toast.LENGTH_SHORT).show();

            return false;
        }
        return true;
    }
}