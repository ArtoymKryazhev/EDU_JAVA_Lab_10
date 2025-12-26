package model;

import java.awt.*;

/**
 * Интерфейс для всех элементов диаграммы
 */
public interface DiagramElement {
    /**
     * Рисует элемент на панели
     */
    void draw(Graphics2D g);

    /**
     * Проверяет, находится ли точка (x, y) внутри элемента
     */
    boolean contains(int x, int y);

    /**
     * Перемещает элемент на (dx, dy)
     */
    void move(int dx, int dy);

    /**
     * Возвращает название элемента
     */
    String getName();
}

