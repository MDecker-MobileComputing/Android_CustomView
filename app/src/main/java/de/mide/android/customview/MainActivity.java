package de.mide.android.customview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.Arrays;


/**
 * Haupt-Activity der App, verwendet Custom View für Darstellung eines Balkendiagramms.
 */
public class MainActivity extends AppCompatActivity {

    public static final String TAG4LOGGING = "Balkendiagramm";

    /** Key, um Array mit Prozentwerten in <i>Saved Instance State</i> zu sichern. */
    private static final String PROZENTWERTE_GESICHERT = "prozentwerte-gesichert";

    /** Anzahl der im Diagramm darzustellenden Balken. */
    private static final int ANZAHL_BALKEN = 4;

    /** Darzustellende Prozentwerte (Höhe der Balken). */
    private float[] _prozentwerteArray = null;

    /** Instanz des Custom View, zeigt ein Balkendiagramm an. */
    private BalkenDiagrammView _balkenDiagramm = null;


    /**
     * Lifecycle-Methode
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _balkenDiagramm = (BalkenDiagrammView) findViewById(R.id.balkendiagramm);

        _prozentwerteArray = null;
        if (savedInstanceState == null ||
            savedInstanceState.containsKey(PROZENTWERTE_GESICHERT) == false ) {

            _prozentwerteArray = balkenwerteErzeugen();

        } else {

            _prozentwerteArray = savedInstanceState.getFloatArray(PROZENTWERTE_GESICHERT);
            final String arrayString = Arrays.toString(_prozentwerteArray);
            Log.i(TAG4LOGGING, "Prozentwerte wiederhergestellt: " + arrayString);
        }

        _balkenDiagramm.setBalkenwerte(_prozentwerteArray);
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

        final float[] prozentwerteArray = new float[ ANZAHL_BALKEN ];
        for (int i = 0; i < prozentwerteArray.length; i++) {

            prozentwerteArray[i] = (float)(Math.random() * 95 + 5);
        }

        final String arrayString = Arrays.toString(prozentwerteArray);
        Log.i(TAG4LOGGING, "Neue Zufallswerte für Balkenhöhe erzeugt: " + arrayString);

        return prozentwerteArray;
    }


    /**
     * Array mit Prozentwerten  vor möglicher Zerstörung der Activity-Instanz
     * speichern.
     *
     * @param savedInstanceState Bundle in which to place your saved state.
     */
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putFloatArray(PROZENTWERTE_GESICHERT, _prozentwerteArray);

        Log.i(TAG4LOGGING, "Prozentwerte gesichert.");
    }


    /**
     * Event-Handler-Methode für Klick auf Button "Neue Daten laden".
     *
     * @param view UI-Element, welches das Event ausgelöst hat
     */
    public void onNeueDatenButton(View view) {

        _prozentwerteArray = balkenwerteErzeugen();
        _balkenDiagramm.setBalkenwerte(_prozentwerteArray);
    }

}