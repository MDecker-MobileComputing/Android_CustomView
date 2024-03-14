package de.mide.android.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;


/**
 * Von View abgeleitete Klasse die ein Balkendiagramm darstellt.
 */
public class MeinDiagrammView extends View {

    private int _anzahlBalken = 4;

    /** Abstand zwischen den Balken in Pixeln */
    private int _abstandZwischenBalken = 10;

    /** Abstand der Unterseite der Balken um unteren Rand (in Pixel), und min Abstand vom oberen Rand. */
    private int _abstandObenUnten = 5;

    /** Paint-Objekt enthält Infos für Stil/Farbe für zu zeichnende Objekte. */
    private Paint _paint;

    /**
     * Array mit darzustellenden Balkenwerten in Prozent; wird in Member-Variable gespeichert,
     * damit beim Drehen des Displays die selben Werte dargestellt werden.
     */
    private float[] _balkenwerteProzent = null;


    /**
     * Konstruktor; erzeugt Paint-Objekt mit Stil für Rechtecke.
     */
    public MeinDiagrammView(Context context, AttributeSet attrs) {

        super(context, attrs);

        _paint = new Paint();
        _paint.setStyle(Paint.Style.FILL);

        balkenwerteErzeugen();
    }


    /**
     * Zufällige Balkenwerte (Höhe in Prozent) erzeugen.
     * <br><br>
     *
     * Für jeden Balken wird ein zufälliger Prozentwert zwischen 5% und 100%
     * erzeugt (mindestens 5%, damit Balken auch sichtbar ist).
     */
    private void balkenwerteErzeugen() {

        _balkenwerteProzent = new float[ _anzahlBalken ];
        for (int i = 0; i < _anzahlBalken; i++) {

            _balkenwerteProzent[i] = (float)(Math.random() * 95 + 5);
        }
    }


    /**
     * Balkendiagramm auf Zeichenfläche einzeichnen.
     *
     * @param canvas Zeichenfläche
     */
    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);

        int balkenbreite  = getBalkenbreite();
        int balkenOffsetX = _abstandZwischenBalken + balkenbreite;

        int maxHoeheBalken = getHeight() - 2* _abstandObenUnten;

        int unten = getHeight() - _abstandObenUnten;

        for (int i = 0; i < _anzahlBalken; i++) {

            int farbe = getBalkenFarbe(i);
            _paint.setColor( farbe );

            int balkenHoehe = (int)( maxHoeheBalken * _balkenwerteProzent[i] / 100.0 );

            int links  = _abstandZwischenBalken + i * balkenOffsetX;
            int rechts = links + balkenbreite;
            int oben   = maxHoeheBalken - balkenHoehe;


            canvas.drawRect(links, oben, rechts, unten, _paint);
        }
    }


    /**
     * Breite für einen Balken anhand aktuellen Fensterbreite, Anzahl der Balken
     * und dem Abstand zwischen den Balken berechnen.
     *
     * @return Breite für einen Balken in Pixel
     */
    private int getBalkenbreite() {

        int nettoBreiteCanvas = getWidth() - (_anzahlBalken + 1)*_abstandZwischenBalken;
        return nettoBreiteCanvas / _anzahlBalken;
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

        int indexFarbe = balkenIndex % FARBEN.length;

        return FARBEN[ indexFarbe ];
    }

}
