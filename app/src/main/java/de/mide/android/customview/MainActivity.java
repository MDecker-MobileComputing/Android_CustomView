package de.mide.android.customview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    /** Instanz des Custom View */
    private MeinDiagrammView _balkenDiagramm = null;


    /**
     * Lifecycle-Methode
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _balkenDiagramm = (MeinDiagrammView) findViewById(R.id.balkendiagramm);

        float[] prozentwerteArray = balkenwerteErzeugen();
        _balkenDiagramm.setBalkenwerte(prozentwerteArray);
    }


    /**
     * Zufällige Balkenwerte (Höhe in Prozent) erzeugen.
     * <br><br>
     *
     * Für jeden Balken wird ein zufälliger Prozentwert zwischen 5% und 100%
     * erzeugt (mindestens 5%, damit Balken auch sichtbar ist).
     *
     * @return Array mit den Balkenwerten (Balkenhöhe) in Prozent (von max. Höher)
     */
    private static float[] balkenwerteErzeugen() {

        float[] prozentwerteArray = new float[ 4 ];
        for (int i = 0; i < prozentwerteArray.length; i++) {

            prozentwerteArray[i] = (float)(Math.random() * 95 + 5);
        }

        return prozentwerteArray;
    }

}