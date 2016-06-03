package projekt.mobilne.unipu.dnevnikkrvnogtlaka;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        // Pokretanje aplikacije i progress bar-a
        new StartApplication().execute(Short.parseShort("10"));
    }

    private class StartApplication extends
            AsyncTask<Short, Integer, Long> {

        @Override
        protected void onPreExecute() {
            //reset and show progress bar
            ProgressBar pbar = (ProgressBar) StartActivity.this
                    .findViewById(R.id.progressBar1);
            pbar.setProgress(0);
            pbar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Long doInBackground(Short... params) {
            short n = params[0];
            long result = 1L;

            for (short i = 1; i <= n; i++) {
                if (Long.MAX_VALUE / i < result) {
                    //skip if overflow
                    result = 0L;
                    break;
                }
                result = result * i;
                //slow down the thread
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                //publish progress update
                publishProgress((int) Math.round((i * 100) / n));
            }
            return result;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            //update progress bar
            ProgressBar pbar = (ProgressBar) StartActivity.this
                    .findViewById(R.id.progressBar1);
            pbar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Long result) {
            //hide progress bar
            ProgressBar pbar = (ProgressBar) StartActivity.this
                    .findViewById(R.id.progressBar1);
            pbar.setVisibility(View.INVISIBLE);

            Intent intent = new Intent(StartActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }
}
