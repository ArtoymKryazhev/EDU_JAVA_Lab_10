package model;

import java.awt.*;

/**
 * Элемент диаграммы: связь между двумя классами (линия)
 */
public class Association implements DiagramElement {
    private UMLClass fromClass;
    private UMLClass toClass;
    private String label;

    // Flyweight: все объекты Association используют один ElementStyle
    private static ElementStyle style;

    static {
        // Инициализируем единый стиль для всех связей один раз
        style = new ElementStyle(
                Color.BLACK,                // Для линий цвет заполнения не используется
                Color.BLACK,                // Черная линия
                1,                          // Тонкая линия 1 пиксель
                new Font("Arial", Font.PLAIN, 10)
        );
    }

    public Association(UMLClass from, UMLClass to, String label) {
        this.fromClass = from;
        this.toClass = to;
        this.label = label;
    }

    @Override
    public void draw(Graphics2D g) {
        // Берем свойства из Flyweight-стиля
        g.setColor(style.getStrokeColor());
        g.setStroke(new BasicStroke(style.getStrokeWidth()));

        // Рисуем линию от центра первого класса к центру второго
        int x1 = fromClass.getX() + fromClass.getWidth() / 2;
        int y1 = fromClass.getY() + fromClass.getHeight() / 2;

        int x2 = toClass.getX() + toClass.getWidth() / 2;
        int y2 = toClass.getY() + toClass.getHeight() / 2;

        g.drawLine(x1, y1, x2, y2);

        // Рисуем стрелку на конце линии
        drawArrow(g, x1, y1, x2, y2);

        // Рисуем текст связи (если есть)
        if (!label.isEmpty()) {
            g.setFont(style.getFont());
            g.setColor(Color.BLACK);
            int midX = (x1 + x2) / 2;
            int midY = (y1 + y2) / 2;
            g.drawString(label, midX + 5, midY - 5);
        }
    }

    /**
     * Вспомогательный метод: рисует стрелку в конце линии
     */
    private void drawArrow(Graphics2D g, int x1, int y1, int x2, int y2) {
        double angle = Math.atan2(y2 - y1, x2 - x1);
        int arrowSize = 10;

        // Конец стрелки
        int endX = x2 - (int)(arrowSize * Math.cos(angle));
        int endY = y2 - (int)(arrowSize * Math.sin(angle));

        // Левая точка стрелки
        int leftX = endX - (int)(arrowSize * Math.cos(angle + Math.PI / 6));
        int leftY = endY - (int)(arrowSize * Math.sin(angle + Math.PI / 6));

        // Правая точка стрелки
        int rightX = endX - (int)(arrowSize * Math.cos(angle - Math.PI / 6));
        int rightY = endY - (int)(arrowSize * Math.sin(angle - Math.PI / 6));

        // Рисуем треугольник стрелки
        int[] xPoints = {x2, leftX, rightX};
        int[] yPoints = {y2, leftY, rightY};
        g.fillPolygon(xPoints, yPoints, 3);
    }

    @Override
    public boolean contains(int x, int y) {
        // Упрощенная проверка: находится ли точка близко к линии
        int x1 = fromClass.getX() + fromClass.getWidth() / 2;
        int y1 = fromClass.getY() + fromClass.getHeight() / 2;
        int x2 = toClass.getX() + toClass.getWidth() / 2;
        int y2 = toClass.getY() + toClass.getHeight() / 2;

        // Расстояние от точки до линии (приблизительно)
        double distance = Math.abs((y2 - y1) * x - (x2 - x1) * y + x2 * y1 - y2 * x1) /
                Math.sqrt(Math.pow(y2 - y1, 2) + Math.pow(x2 - x1, 2));

        return distance < 5 && x > Math.min(x1, x2) - 10 && x < Math.max(x1, x2) + 10 &&
                y > Math.min(y1, y2) - 10 && y < Math.max(y1, y2) + 10;
    }

    @Override
    public void move(int dx, int dy) {
        // Связь не перемещается сама, она следует за классами
    }

    @Override
    public String getName() {
        return label;
    }

    public UMLClass getFromClass() { return fromClass; }
    public UMLClass getToClass() { return toClass; }
}

