package view;


import model.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * Панель для рисования диаграммы
 */
public class DiagramPanel extends JPanel {
    private DiagramModel model;
    private DiagramElement selectedElement;

    public DiagramPanel(DiagramModel model) {
        this.model = model;
        this.selectedElement = null;
        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        // Рисуем все связи
        for (Association a : model.getAssociations()) {
            a.draw(g2d);
        }

        // Рисуем все классы
        for (UMLClass c : model.getClasses()) {
            c.draw(g2d);
        }

        // Рисуем выделение выбранного элемента
        if (selectedElement instanceof UMLClass) {
            UMLClass c = (UMLClass) selectedElement;
            g2d.setColor(Color.RED);
            g2d.setStroke(new BasicStroke(3));
            g2d.drawRect(c.getX() - 2, c.getY() - 2, c.getWidth() + 4, c.getHeight() + 4);
        }
    }

    public void setSelectedElement(DiagramElement element) {
        this.selectedElement = element;
        repaint();
    }

    public DiagramElement getSelectedElement() {
        return selectedElement;
    }
}

