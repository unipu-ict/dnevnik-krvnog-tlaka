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

        // ukoliko dođe do pogreške
        Toast.makeText(context, "Poruka o grešci", Toast.LENGTH_SHORT).show();

        return true;
    }
}
// pozdrav iz rešetara :D