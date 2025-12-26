package model;

import java.awt.*;

/**
 * Элемент диаграммы: класс (прямоугольник с именем)
 */
public class UMLClass implements DiagramElement {
    private int x, y;
    private int width, height;
    private String name;

    // Flyweight: все объекты UMLClass используют один ElementStyle
    private static ElementStyle style;

    static {
        // Инициализируем единый стиль для всех классов один раз
        style = new ElementStyle(
                new Color(200, 220, 255),  // Голубой фон
                Color.BLACK,                // Черная граница
                2,                          // Толщина линии 2 пикса
                new Font("Arial", Font.BOLD, 12)
        );
    }

    public UMLClass(int x, int y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
        this.width = 120;
        this.height = 60;
    }

    @Override
    public void draw(Graphics2D g) {
        // Устанавливаем свойства из Flyweight-стиля
        g.setColor(style.getFillColor());
        g.fillRect(x, y, width, height);

        g.setColor(style.getStrokeColor());
        g.setStroke(new BasicStroke(style.getStrokeWidth()));
        g.drawRect(x, y, width, height);

        // Рисуем текст (имя класса)
        g.setFont(style.getFont());
        g.setColor(Color.BLACK);
        FontMetrics fm = g.getFontMetrics();
        int textX = x + (width - fm.stringWidth(name)) / 2;
        int textY = y + ((height - fm.getHeight()) / 2) + fm.getAscent();
        g.drawString(name, textX, textY);
    }

    @Override
    public boolean contains(int px, int py) {
        return px >= x && px <= x + width && py >= y && py <= y + height;
    }

    @Override
    public void move(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }

    @Override
    public String getName() {
        return name;
    }

    // Геттеры координат для связей
    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }

    public void setName(String name) {
        this.name = name;
    }
}

