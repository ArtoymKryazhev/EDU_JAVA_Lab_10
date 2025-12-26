package model;

import java.awt.*;

/**
 * Паттерн Flyweight (Приспособленец)
 * Общие свойства для всех элементов одного типа
 * Все элементы одного типа используют один и тот же объект стиля
 */
public class ElementStyle {
    private Color fillColor;
    private Color strokeColor;
    private int strokeWidth;
    private Font font;

    public ElementStyle(Color fill, Color stroke, int width, Font font) {
        this.fillColor = fill;
        this.strokeColor = stroke;
        this.strokeWidth = width;
        this.font = font;
    }

    // Геттеры
    public Color getFillColor() { return fillColor; }
    public Color getStrokeColor() { return strokeColor; }
    public int getStrokeWidth() { return strokeWidth; }
    public Font getFont() { return font; }
}

