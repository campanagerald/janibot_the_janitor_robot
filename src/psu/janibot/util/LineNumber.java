package psu.janibot.util;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;

//http://javaknowledge.info/jtextpane-line-number/
public class LineNumber extends JList {

    private JList list;
    private boolean started;
    private JTextPane textPane;
    private Element root;
    private Font font;

    private LineNumber() {
    }

    public LineNumber(JTextPane textPane) {
        super();
        list = new LineNumber();
        this.textPane = textPane;
        root = textPane.getDocument().getDefaultRootElement();
        setModel(new TextComponentListModel());
        setCellRenderer(new RowHeaderRenderer());
        setSizes(textPane.getFont());
        setBackground(new Color(247, 247, 247));
    }

    class TextComponentListModel extends AbstractListModel implements DocumentListener, CaretListener {

        private int currentLines;

        public TextComponentListModel() {
            textPane.getDocument().addDocumentListener(this);
            textPane.addCaretListener(this);
        }

        @Override
        public int getSize() {
            return root.getElementCount();
        }

        @Override
        public Object getElementAt(int index) {
            int lines = root.getElementCount();
            if (index < lines) {
                return "" + (index + 1);
            } else {
                return "";
            }
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            font = textPane.getFont();
            setSizes(font);
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            int lines = root.getElementCount();
            if (lines >= currentLines) {
                fireIntervalAdded(this, currentLines, lines);
                currentLines = lines;
            }
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            int lines = root.getElementCount();
            if (lines <= currentLines) {
                fireIntervalRemoved(this, currentLines, lines);
                currentLines = lines;
            }
        }

        @Override
        public void caretUpdate(CaretEvent e) {
            int offset = textPane.getCaretPosition();
            int line = root.getElementIndex(offset);
            list.setSelectedIndex(line);
        }
    } // end TextComponentListModel

    class RowHeaderRenderer extends JLabel implements ListCellRenderer {

        RowHeaderRenderer() {
            setOpaque(true);
            setHorizontalAlignment(RIGHT);
        }

        // Returns the JLabel after setting the text of the cell
        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

            setFont(new Font("Consolas", Font.PLAIN, 13));
            setText((value == null) ? "" : value.toString());
            int offset = textPane.getCaretPosition();
            int line = root.getElementIndex(offset);
            setBackground(new Color(247, 247, 247));
            setForeground(new Color(176, 170, 165));

            if(index == line){
                setBackground(new Color(156, 215, 247));
                setForeground(Color.YELLOW);
            }

            return this;
        }
    }

    public final void setSizes(Font font) {
        FontMetrics fm = getFontMetrics(font);
        setFixedCellHeight(fm.getHeight());
        setFixedCellWidth(fm.stringWidth("00000"));
    }
}