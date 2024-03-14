package de.mide.android.customview;

import static de.mide.android.customview.MainActivity.TAG4LOGGING;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;


/**
 * Von View abgeleitete Klasse die ein Balkendiagramm darstellt.
 */
public class BalkenDiagrammView extends View {

    /** Abstand zwischen den Balken in Pixeln */
    private int _abstandZwischenBalken = 10;

    /** Abstand der Unterseite der Balken um unteren Rand (in Pixel), und min Abstand vom oberen Rand. */
    private int _abstandObenUnten = 5;

    /** Paint-Objekt enthält Infos für Stil/Farbe für zu zeichnende Objekte. */
    private Paint _paint;

    /** Array mit darzustellenden Werten in Prozent der max möglichen Balkenhöhe. */
    private float[] _balkenwerteProzent = null;


    /**
     * Konstruktor; erzeugt Paint-Objekt mit Stil für Rechtecke.
     */
    public BalkenDiagrammView(Context context, AttributeSet attrs) {

        super(context, attrs);

        _paint = new Paint();
        _paint.setStyle(Paint.Style.FILL);
    }


    /**
     * Darzustellende Balkenwerte übergeben
     *
     * @param prozentwerteArray Array mit darzustellenden Balkenwerten in Prozent
     */
    public void setBalkenwerte(float[] prozentwerteArray) {

        _balkenwerteProzent = prozentwerteArray;

        // damit bei Klick auf Button "Neue Daten laden" die neuen Daten gleich dargestellt werden
        invalidate();
    }


    /**
     * Balkendiagramm auf Zeichenfläche einzeichnen.
     *
     * @param canvas Zeichenfläche
     */
    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);

        if (_balkenwerteProzent == null) {

            Log.i(TAG4LOGGING, "Noch keine Werte für Diagramm da, kann nichts zeichnen.");
            return;
        }

        final int balkenbreite  = getBalkenbreite();
        final int balkenOffsetX = _abstandZwischenBalken + balkenbreite;

        final int maxHoeheBalken = getHeight() - 2* _abstandObenUnten;

        final int unten = getHeight() - _abstandObenUnten;

        for (int i = 0; i < _balkenwerteProzent.length; i++) {

            int farbe = getBalkenFarbe(i);
            _paint.setColor( farbe );

            int balkenHoehe = (int)( maxHoeheBalken * _balkenwerteProzent[i] / 100.0 );

            int links  = _abstandZwischenBalken + i * balkenOffsetX;
            int rechts = links + balkenbreite;
            int oben   = maxHoeheBalken - balkenHoehe;

            canvas.drawRect(links, oben, rechts, unten, _paint); // Rechteck zeichnen
        }
    }


    /**
     * Breite für einen Balken anhand aktuellen Fensterbreite, Anzahl der Balken
     * und dem Abstand zwischen den Balken berechnen.
     *
     * @return Breite für einen Balken in Pixel
     */
    private int getBalkenbreite() {

        final int anzahlBalken = _balkenwerteProzent.length;

        final int nettoBreiteCanvas = getWidth() - (anzahlBalken + 1)*_abstandZwischenBalken;

        return nettoBreiteCanvas / anzahlBalken;
    }


    /**
     * Farbe für Balken nach "Round Robin"-Verfahren bestimmen.
     *
     * @param balkenIndex 0-basierter Index des Balken
     *
     * @return Farbe für Balkenfläche
     */
    private int getBalkenFarbe(int balkenIndex) {

        final int[] FARBEN = { Color.RED, Color.GREEN, Color.BLUE, Color.GRAY, Color.CYAN };

        final int indexFarbe = balkenIndex % FARBEN.length;

        return FARBEN[ indexFarbe ];
    }

}
