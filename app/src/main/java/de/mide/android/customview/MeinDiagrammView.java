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

    private int _abstandZwischenBalken = 10;

    private int _abstandObenUnten = 5;

    /** Paint-Objekt enthält Infos für Stil/Farbe für zu zeichnende Objekte. */
    private Paint _paint;


    /**
     * Konstruktor.
     */
    public MeinDiagrammView(Context context, AttributeSet attrs) {

        super(context, attrs);

        _paint = new Paint();
        _paint.setStyle(Paint.Style.FILL);
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

            double prozentwert = getZufallsProzentWert();
            int balkenHoehe = (int)(maxHoeheBalken*prozentwert/100.0);

            int links = _abstandZwischenBalken + i * balkenOffsetX;
            int rechts = links + balkenbreite;

            int oben  = maxHoeheBalken - balkenHoehe;


            canvas.drawRect(links, oben, rechts, unten, _paint);
        }

    }

    private int getBalkenbreite() {

        int nettoBreiteCanvas = getWidth() - (_anzahlBalken + 1)*_abstandZwischenBalken;
        return nettoBreiteCanvas / _anzahlBalken;
    }

    private final int[] FARBEN = { Color.RED, Color.GREEN, Color.BLUE, Color.GRAY, Color.CYAN };

    /**
     * Farbe für Balken nach "Round Robin"-Verfahren bestimmen.
     *
     * @param balkenIndex 0-basierter Index des Balken
     *
     * @return Farbe für Balkenfläche
     */
    private int getBalkenFarbe(int balkenIndex) {

        int indexFarbe = balkenIndex % FARBEN.length;

        return FARBEN[ indexFarbe ];
    }


    /**
     * Zufällige Höhe von Balken bestimmen.
     *
     * @return Zufälliger Prozentwert zwischen 5% und 100%
     *                    (mindest. 5%, damit der Balken auch sichtbar ist).
     */
    private float getZufallsProzentWert() {

        return (float)(Math.random() * 95 + 5);
    }

}
