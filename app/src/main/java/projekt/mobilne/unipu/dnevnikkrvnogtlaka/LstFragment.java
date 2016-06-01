package projekt.mobilne.unipu.dnevnikkrvnogtlaka;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class LstFragment extends ListFragment {

    private DbHelper myDb;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {

        // Inflate the fragment layout file
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_layout, container, false);

        ArrayList<String> arrayList = new ArrayList<>();

        myDb = new DbHelper(getContext());
        myDb.getAllData();

        Cursor cur = myDb.getAllData();
        StringBuffer buffer = new StringBuffer();

        while (cur.moveToNext()) {
            buffer.append(cur.getString(4) + " | ");
            buffer.append(cur.getString(1) + " | ");
            buffer.append(cur.getString(2) + " | ");
            buffer.append(cur.getString(3) + "\n");
            arrayList.add(buffer.toString());
            buffer.delete(0, buffer.length());
        }
        cur.close();

        // Create adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.fragment_row_layout, R.id.txtitem, arrayList);

        // Bind adapter to listfragment
        setListAdapter(adapter);

        // Retain listfragment instance across configuration changes
        setRetainInstance(true);

        return rootView;
    }

    // Handler za item click
    public void onListItemClick(ListView l, View view, int position, long id) {
        // ViewGroup viewGroup = (ViewGroup) view;
        // Primjer dinamičnog dohvata texta labele
        // TextView txt = (TextView) viewGroup.findViewById(R.id.txtitem);
        StringBuilder poruka = new StringBuilder("Odabrali ste unos br. ");
        // TODO: 29.05. P-3
        // Toast.makeText(getActivity(), poruka.append(position+1), Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }
}
