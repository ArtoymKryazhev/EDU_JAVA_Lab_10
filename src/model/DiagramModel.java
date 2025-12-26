package model;

import java.util.*;

/**
 * Модель диаграммы: хранит все элементы (классы и связи)
 */
public class DiagramModel {
    private List<UMLClass> classes;      // Все классы в диаграмме
    private List<Association> associations; // Все связи в диаграмме

    public DiagramModel() {
        this.classes = new ArrayList<>();
        this.associations = new ArrayList<>();
    }

    // ===== ОПЕРАЦИИ С КЛАССАМИ =====
    public void addClass(UMLClass umlClass) {
        classes.add(umlClass);
    }

    public void removeClass(UMLClass umlClass) {
        classes.remove(umlClass);
        // Удаляем все связи этого класса
        associations.removeIf(a -> a.getFromClass() == umlClass || a.getToClass() == umlClass);
    }

    public List<UMLClass> getClasses() {
        return new ArrayList<>(classes);
    }

    // ===== ОПЕРАЦИИ СО СВЯЗЯМИ =====
    public void addAssociation(Association association) {
        associations.add(association);
    }

    public void removeAssociation(Association association) {
        associations.remove(association);
    }

    public List<Association> getAssociations() {
        return new ArrayList<>(associations);
    }

    // ===== ПОИСК ЭЛЕМЕНТОВ =====
    public DiagramElement findElement(int x, int y) {
        // Ищем среди связей (они должны быть сверху)
        for (Association a : associations) {
            if (a.contains(x, y)) return a;
        }
        // Ищем среди классов
        for (UMLClass c : classes) {
            if (c.contains(x, y)) return c;
        }
        return null;
    }

    public void clear() {
        classes.clear();
        associations.clear();
    }
}

