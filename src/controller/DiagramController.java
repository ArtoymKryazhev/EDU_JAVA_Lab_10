package controller;

import model.*;
import view.*;
import java.awt.event.*;
import java.awt.event.KeyEvent;

/**
 * Контроллер: обрабатывает события мыши и клавиатуры
 */
public class DiagramController extends MouseAdapter implements KeyListener {
    private DiagramModel model;
    private DiagramPanel panel;

    private int lastMouseX, lastMouseY;
    private DiagramElement draggingElement;

    public DiagramController(DiagramModel model, DiagramPanel panel) {
        this.model = model;
        this.panel = panel;
        this.draggingElement = null;

        // Добавляем слушатель клавиатуры
        panel.addKeyListener(this);
        panel.setFocusable(true);
    }

    // ===== ОБРАБОТЧИКИ МЫШИ =====

    @Override
    public void mousePressed(MouseEvent e) {
        lastMouseX = e.getX();
        lastMouseY = e.getY();

        // Ищем элемент под мышью
        DiagramElement element = model.findElement(e.getX(), e.getY());
        panel.setSelectedElement(element);

        if (element instanceof UMLClass) {
            draggingElement = element;
        }

        panel.requestFocus();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (draggingElement instanceof UMLClass) {
            int dx = e.getX() - lastMouseX;
            int dy = e.getY() - lastMouseY;

            draggingElement.move(dx, dy);

            lastMouseX = e.getX();
            lastMouseY = e.getY();

            panel.repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        draggingElement = null;
    }

    // ===== ОБРАБОТЧИКИ КЛАВИАТУРЫ =====

    @Override
    public void keyPressed(KeyEvent e) {
        DiagramElement selected = panel.getSelectedElement();
        if (!(selected instanceof UMLClass)) return;

        UMLClass umlClass = (UMLClass) selected;
        int step = 5; // Шаг перемещения

        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                umlClass.move(0, -step);
                panel.repaint();
                break;
            case KeyEvent.VK_DOWN:
                umlClass.move(0, step);
                panel.repaint();
                break;
            case KeyEvent.VK_LEFT:
                umlClass.move(-step, 0);
                panel.repaint();
                break;
            case KeyEvent.VK_RIGHT:
                umlClass.move(step, 0);
                panel.repaint();
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}
}
