package projekt.mobilne.unipu.dnevnikkrvnogtlaka;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class LstFragment extends ListFragment {

    // region PRIVATNE VARIJABLE
    private DbHelper myDb;
    private ArrayList<KrvniTlak> arrayList = new ArrayList<>();
    private ArrayList<String> arrayListTv = new ArrayList<>();
    private int id = 0;
    private int pulse = 0;
    // endregion

    // region JAVNE METODE
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_layout, container, false);

        // Dohvaćam kontekst i sve podatke iz SQLite baze
        myDb = new DbHelper(getContext());
        myDb.getAllData();

        Cursor cur = myDb.getAllData();
        int brojac = 0;

        while (cur.moveToNext()) {
            brojac++;

            // Instanciranje novog primjerka klase KrvniTlak prema zapisima iz baze
            KrvniTlak kt = new KrvniTlak(
                    cur.getString(0),
                    cur.getString(1),
                    cur.getString(2),
                    cur.getString(3),
                    cur.getString(4)
            );
            arrayList.add(kt);

            arrayListTv.add("Tlak br. " + brojac + " i indeks: " + cur.getString(0));
        }
        cur.close();

        // Stvaram adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.fragment_row_layout, R.id.txtitem, arrayListTv);

        // Vežem adapter na ListFragment
        setListAdapter(adapter);
        setRetainInstance(true);

        return rootView;
    }

    // Handler za item click
    public void onListItemClick(ListView l, View view, final int position, long id) {

        // Programsko kreiranje AlertDialoga za prikaz informacija o napravljenim unosima
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        // Postavljam naslov dijaloškog okvira
        alertDialogBuilder.setTitle("Krvni tlak");
        // Postavljam glavni tekst dijaloškog okvira i dvije tipke "Natrag" i "Obriši"
        alertDialogBuilder
                .setMessage(arrayList.get(position).getOpis())
                .setCancelable(true)
                .setPositiveButton("Obriši", new DialogInterface.OnClickListener() {
                    @Override
                    // onClick metoda za brisanje odabranog krvnog tlaka
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Brisanje odabranog unosa sa ListFragmenta prema dvije varijable
                        myDb.obrisiUnos(
                                Integer.parseInt(arrayList.get(position).getId()),
                                Integer.parseInt(arrayList.get(position).getPuls())
                        );
                        // Nakon brisanja završi i premjesti me na glavnu aktivnost
                        getActivity().finish();
                    }
                })
                // onClick metoda za povratak na ListFragment sa svim unosima
                .setNegativeButton("Natrag", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Izlaz iz dijaloškog okvira
                        dialogInterface.cancel();
                    }
                });

        // Kreiranje AlertDialoga i njegov prikaz
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    // endregion
}
