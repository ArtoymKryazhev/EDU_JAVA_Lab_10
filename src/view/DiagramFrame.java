package view;

import model.*;
import controller.*;
import javax.swing.*;
import java.awt.*;

/**
 * Главное окно приложения
 */
public class DiagramFrame extends JFrame {
    private DiagramModel model;
    private DiagramPanel diagramPanel;
    private DiagramController controller;

    // UI компоненты
    private JTextField classNameField;
    private JComboBox<String> fromClassCombo;
    private JComboBox<String> toClassCombo;
    private JTextField associationLabelField;

    public DiagramFrame() {
        setTitle("Редактор UML Диаграмм");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Создаем модель
        model = new DiagramModel();

        // Создаем панель рисования
        diagramPanel = new DiagramPanel(model);

        // Создаем контроллер
        controller = new DiagramController(model, diagramPanel);

        // Добавляем слушатели мыши к панели
        diagramPanel.addMouseListener(controller);
        diagramPanel.addMouseMotionListener(controller);

        // Главная панель с BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(diagramPanel, BorderLayout.CENTER);

        // Панель управления (сверху)
        JPanel controlPanel = createControlPanel();
        mainPanel.add(controlPanel, BorderLayout.NORTH);

        // Панель информации (снизу)
        JPanel infoPanel = createInfoPanel();
        mainPanel.add(infoPanel, BorderLayout.SOUTH);

        getContentPane().add(mainPanel);
        setVisible(true);
    }

    /**
     * Создает панель управления (добавление классов и связей)
     */
    private JPanel createControlPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Управление диаграммой"));

        // ===== ДОБАВЛЕНИЕ КЛАССА =====
        panel.add(new JLabel("Имя класса:"));
        classNameField = new JTextField(12);
        panel.add(classNameField);

        JButton addClassButton = new JButton("Добавить класс");
        addClassButton.addActionListener(e -> addClassAction());
        panel.add(addClassButton);

        panel.add(new JSeparator(SwingConstants.VERTICAL));

        // ===== ДОБАВЛЕНИЕ СВЯЗИ =====
        panel.add(new JLabel("Из класса:"));
        fromClassCombo = new JComboBox<>();
        panel.add(fromClassCombo);

        panel.add(new JLabel("В класс:"));
        toClassCombo = new JComboBox<>();
        panel.add(toClassCombo);

        panel.add(new JLabel("Связь:"));
        associationLabelField = new JTextField(10);
        panel.add(associationLabelField);

        JButton addAssociationButton = new JButton("Добавить связь");
        addAssociationButton.addActionListener(e -> addAssociationAction());
        panel.add(addAssociationButton);

        panel.add(new JSeparator(SwingConstants.VERTICAL));

        // ===== УДАЛЕНИЕ И ОЧИСТКА =====
        JButton deleteButton = new JButton("Удалить выбранное");
        deleteButton.addActionListener(e -> deleteSelectedAction());
        panel.add(deleteButton);

        JButton clearButton = new JButton("Очистить диаграмму");
        clearButton.addActionListener(e -> clearAction());
        panel.add(clearButton);

        return panel;
    }

    /**
     * Создает панель информации (снизу)
     */
    private JPanel createInfoPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Информация"));
        panel.add(new JLabel("Совет: кликните на класс для выделения, потом используйте клавиши стрелок для перемещения"));
        return panel;
    }

    // ===== ОБРАБОТЧИКИ ДЕЙСТВИЙ =====

    private void addClassAction() {
        String name = classNameField.getText().trim();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Введите имя класса!", "Ошибка",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Берем случайную позицию для нового класса
        int x = (int) (Math.random() * 600);
        int y = (int) (Math.random() * 400);

        UMLClass umlClass = new UMLClass(x, y, name);
        model.addClass(umlClass);

        updateClassCombo();
        classNameField.setText("");
        diagramPanel.repaint();
    }

    private void addAssociationAction() {
        if (fromClassCombo.getSelectedIndex() < 0 || toClassCombo.getSelectedIndex() < 0) {
            JOptionPane.showMessageDialog(this, "Выберите оба класса!", "Ошибка",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        UMLClass from = model.getClasses().get(fromClassCombo.getSelectedIndex());
        UMLClass to = model.getClasses().get(toClassCombo.getSelectedIndex());
        String label = associationLabelField.getText().trim();

        Association assoc = new Association(from, to, label);
        model.addAssociation(assoc);

        associationLabelField.setText("");
        diagramPanel.repaint();
    }

    private void deleteSelectedAction() {
        DiagramElement selected = diagramPanel.getSelectedElement();
        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Выберите элемент для удаления!", "Ошибка",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (selected instanceof UMLClass) {
            model.removeClass((UMLClass) selected);
        } else if (selected instanceof Association) {
            model.removeAssociation((Association) selected);
        }

        updateClassCombo();
        diagramPanel.setSelectedElement(null);
        diagramPanel.repaint();
    }

    private void clearAction() {
        int result = JOptionPane.showConfirmDialog(this,
                "Вы уверены? Это удалит всю диаграмму.", "Подтверждение",
                JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_OPTION) {
            model.clear();
            updateClassCombo();
            diagramPanel.setSelectedElement(null);
            diagramPanel.repaint();
        }
    }

    /**
     * Обновляет ComboBox с именами всех классов
     */
    private void updateClassCombo() {
        fromClassCombo.removeAllItems();
        toClassCombo.removeAllItems();

        for (UMLClass c : model.getClasses()) {
            fromClassCombo.addItem(c.getName());
            toClassCombo.addItem(c.getName());
        }
    }
}

