import javax.swing.*;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;

import java.awt.*;
import java.awt.event.*;

public class Calculator extends JFrame
{
    private JPanel panel;
    private JButton add;
    private JButton subtract;
    private JButton multiply;
    private JButton divide;
    private JButton clear;
    private JButton equal;
    private JButton numbers[] = new JButton[10]; // Numbers.
    private JButton dot;
    private JButton back;

    private JTextField value = new JTextField();
    private CalculatorAPI api;

    // 1 = add, 2 = subtract, 3 = multiply & 4 = divide.
    private int clicked;
    private boolean numberClicked;
    private boolean dotClicked;
    private String operand1, operand2, result;

    public Calculator()
    {
        // Initialize the button objects.
        panel = new JPanel();
        add = new JButton();
        subtract = new JButton();
        multiply = new JButton();
        divide = new JButton();
        clear = new JButton();
        equal = new JButton();
        dot = new JButton();
        back = new JButton();

        // Initialize the number objects.
        for(int i = 0; i < 10; ++i) { numbers[i] = new JButton(); }

        // Instantiate the CalculatorAPI.
        api = new CalculatorAPI();
    }

    public void clearFields()
    {
        clicked = 0;
        numberClicked = dotClicked = false;
        operand1 = operand2 = result = "0";
        value.setText("0");
    }

    public void run()
    {
        clearFields();
        createLayout();
        addListeners();
        mapInputs();
    }

    public void mapInputs()
    {
        // Clear button.
        String mapKey = "KEY_C";
        InputMap inputMap = clear.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(KeyStroke.getKeyStroke("C"), mapKey);

        clear.getActionMap().put(mapKey, new AbstractAction()
        {
            public void actionPerformed(ActionEvent evt)
            {
               clear.doClick();
            }
        });

        // Add button.
        mapKey = "KEY_A";
        inputMap = add.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(KeyStroke.getKeyStroke("A"), mapKey);

        add.getActionMap().put(mapKey, new AbstractAction()
        {
            public void actionPerformed(ActionEvent evt)
            {
                add.doClick();
            }
        });

        // Subtract button.
        mapKey = "KEY_S";
        inputMap = subtract.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(KeyStroke.getKeyStroke("S"), mapKey);

        subtract.getActionMap().put(mapKey, new AbstractAction()
        {
            public void actionPerformed(ActionEvent evt)
            {
                subtract.doClick();
            }
        });

        // Multiply button.
        mapKey = "KEY_M";
        inputMap = multiply.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(KeyStroke.getKeyStroke("M"), mapKey);

        multiply.getActionMap().put(mapKey, new AbstractAction()
        {
            public void actionPerformed(ActionEvent evt)
            {
                multiply.doClick();
            }
        });

        // Divide button.
        mapKey = "KEY_D";
        inputMap = divide.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(KeyStroke.getKeyStroke("D"), mapKey);
        inputMap.put(KeyStroke.getKeyStroke("SLASH"), mapKey);

        divide.getActionMap().put(mapKey, new AbstractAction()
        {
            public void actionPerformed(ActionEvent evt)
            {
                divide.doClick();
            }
        });

        // Equal button.
        mapKey = "KEY_ENTER";
        inputMap = equal.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(KeyStroke.getKeyStroke("ENTER"), mapKey);

        equal.getActionMap().put(mapKey, new AbstractAction()
        {
            public void actionPerformed(ActionEvent evt)
            {
                equal.doClick();
            }
        });

        // Numbers.
        for(int i = 1; i <= 10; ++i)
        {
            final int j = i;
            mapKey = (i % 10) + "";
            inputMap = numbers[j - 1].getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
            inputMap.put(KeyStroke.getKeyStroke(mapKey), mapKey);

            numbers[j - 1].getActionMap().put(mapKey, new AbstractAction()
            {
                public void actionPerformed(ActionEvent evt)
                {
                    numbers[j - 1].doClick();
                }
            });
        }

        // Dot button.
        mapKey = "KEY_DECIMAL";
        inputMap = dot.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(KeyStroke.getKeyStroke("DECIMAL"), mapKey);

        dot.getActionMap().put(mapKey, new AbstractAction()
        {
            public void actionPerformed(ActionEvent evt)
            {
                dot.doClick();
            }
        });

        // Backspace button.
        mapKey = "KEY_BACK_SPACE";
        inputMap = back.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(KeyStroke.getKeyStroke("BACK_SPACE"), mapKey);

        back.getActionMap().put(mapKey, new AbstractAction()
        {
            public void actionPerformed(ActionEvent evt)
            {
                back.doClick();
            }
        });
    }

    public void createLayout()
    {
        // Get the screen dimensions.
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        int width = ((int) toolkit.getScreenSize().getWidth());
        int height = ((int) toolkit.getScreenSize().getHeight());

        // Set the button dimensions.
        int x = 15, y = 70;
        int w = 45, h = 45;

        // Input value properties.
        Font font = new Font("Arial", Font.PLAIN, 18);
        value.setFont(font);
        value.setBounds(15, 15, 210, 30);
        value.setForeground(Color.WHITE);
        value.setBackground(Color.DARK_GRAY);
        value.setHorizontalAlignment(JTextField.RIGHT);
        value.setVisible(true);
        value.setEditable(false);

        // Numbers.
        int x1 = x, y1 = y;
        for(int i = 1; i <= 10; ++i)
        {
            numbers[i - 1].setBounds(x1, y1, w, h);
            numbers[i - 1].setBackground(Color.GRAY);
            numbers[i - 1].setText((i % 10) + "");
            numbers[i - 1].setVisible(true);

            // Go to next row.
            if(i % 4 == 0) { x1 = x; y1 += h + 10; }
            else { x1 += w + 10; }
        }

        // Add.
        add.setBounds(x1, y1, w, h);
        add.setBackground(Color.GRAY);
        add.setText("+");
        add.setVisible(true);

        // Subtract.
        x1 += w + 10;
        subtract.setBounds(x1, y1, w, h);
        subtract.setBackground(Color.GRAY);
        subtract.setText("-");
        subtract.setVisible(true);

        // Multiply.
        x1 = x;
        y1 += h + 10;
        multiply.setBounds(x1, y1, w, h);
        multiply.setBackground(Color.GRAY);
        multiply.setText("*");
        multiply.setVisible(true);

        // Divide.
        x1 += w + 10;
        divide.setBounds(x1, y1, w, h);
        divide.setBackground(Color.GRAY);
        divide.setText("/");
        divide.setVisible(true);

        // Clear.
        x1 += w + 10;
        clear.setBounds(x1, y1, w, h);
        clear.setBackground(new Color(250, 150, 50));
        clear.setForeground(new Color(255, 255, 255));
        clear.setText("C");
        clear.setVisible(true);

        // Equal.
        x1 += w + 10;
        equal.setBounds(x1, y1, w, h);
        equal.setBackground(Color.GRAY);
        equal.setText("=");
        equal.setVisible(true);

        // Dot.
        x1 = x;
        y1 += h + 10;
        dot.setBounds(x1, y1, w, h);
        dot.setBackground(Color.GRAY);
        dot.setText(".");
        dot.setVisible(true);

        // Backspace.
        x1 += w + 10;
        back.setBounds(x1, y1, 3 * w + 20, h);
        back.setBackground(Color.GRAY);
        back.setText("Backspace");
        back.setVisible(true);

        // JPanel properties.
        panel.setBounds(0, 0, 200, 200);
        panel.setBackground(Color.LIGHT_GRAY);
        panel.setVisible(true);
        panel.setLayout(null);

        // Add the buttons to the panel.
        for(int i = 0; i < 10; ++i) panel.add(numbers[i]);
        panel.add(add);
        panel.add(subtract);
        panel.add(multiply);
        panel.add(divide);
        panel.add(clear);
        panel.add(equal);
        panel.add(value);
        panel.add(dot);
        panel.add(back);

        // JFrame properties.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(width / 3, height / 3); // Start location.
        setSize(245 + 1, 380);
        setResizable(false);
        setTitle("Simple Calculator");
        add(panel);
        setVisible(true);
    }

    public void addListeners()
    {
        // Add document listener for refreshing the value field.
        value.getDocument().addDocumentListener(new DocumentListener()
        {
            @Override
            public void insertUpdate(DocumentEvent e){}
            @Override
            public void removeUpdate(DocumentEvent e){}
            @Override
            public void changedUpdate(DocumentEvent e){}
        });

        // Listener for the add button.
        add.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if(clicked != 1)
                {
                    clicked = 1;
                    operand1 = operand2 = value.getText();
                }
                else { operand2 = value.getText(); }
                numberClicked = dotClicked = false;
            }
        });

        // Listener for the subtract button.
        subtract.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if(clicked != 2)
                {
                    clicked = 2;
                    operand1 = operand2 = value.getText();
                }
                else { operand2 = value.getText(); }
                numberClicked = dotClicked = false;
            }
        });

        // Listener for the multiply button.
        multiply.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if(clicked != 3)
                {
                    clicked = 3;
                    operand1 = operand2 = value.getText();
                }
                else { operand2 = value.getText(); }
                numberClicked = dotClicked = false;
            }
        });

        // Listener for the divide button.
        divide.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if(clicked != 4)
                {
                    clicked = 4;
                    operand1 = operand2 = value.getText();
                }
                else { operand2 = value.getText(); }
                numberClicked = dotClicked = false;
            }
        });

        // Listener for the equal button.
        equal.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                switch(clicked)
                {
                    case 1:
                        if(numberClicked) operand2 = value.getText();
                        result = api.add(operand1, operand2);
                        value.setText(result);
                        break;
                    case 2:
                        if(numberClicked) operand2 = value.getText();
                        result = api.subtract(operand1, operand2);
                        value.setText(result);
                        break;
                    case 3:
                        if(numberClicked) operand2 = value.getText();
                        result = api.multiply(operand1, operand2);
                        value.setText(result);
                        break;
                    case 4:
                        if(numberClicked) operand2 = value.getText();
                        result = api.divide(operand1, operand2);
                        value.setText(result);
                        break;
                    default:
                }

                if(result == "Error") clearFields();

                clicked = 0;
                numberClicked = dotClicked = false;
                operand1 = operand2 = result = "0";
            }
        });

        // Listener for the clear button.
        clear.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                clearFields();
                numberClicked = dotClicked = false;
            }
        });

        // Listeners for the number buttons.
        for(int i = 0; i < 10; ++i)
        {
            final int j = (i + 1) % 10; // ....!?
            numbers[i].addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    if(numberClicked)
                    {
                        String s = value.getText();
                        if(s.length() < api.LIMIT)
                        {
                            String digit = j + "";
                            if(s.charAt(0) != '0' || s.contains(".")) s = s + digit;
                            else s = digit;
                            value.setText(s);
                            dotClicked = false;
                        }
                    }
                    else
                    {
                        String s = value.getText();
                        if(s.length() < api.LIMIT)
                        {
                            if(!dotClicked) value.setText(j + "");
                            else value.setText(s + j + "");
                            numberClicked = true;
                            dotClicked = false;
                        }
                    }
                }
            });
        }

        // Listener for the dot button.
        dot.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String s = value.getText();

                if(s.length() < api.LIMIT && !s.contains("."))
                {
                    s = s + ".";
                    value.setText(s);
                    dotClicked = true;
                    numberClicked = false;
                }
            }
        });

        // Listener for the backspace button.
        back.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                String s = value.getText();

                if(s.length() == 1)
                {
                    value.setText("0");
                    numberClicked = true;
                    dotClicked = false;
                }
                else
                {
                    s = s.substring(0, s.length() - 1);
                    value.setText(s);
                    if(s.charAt(s.length() - 1) == '.') { dotClicked = true; numberClicked = false; }
                    else { numberClicked = true; dotClicked = false; }
                }
            }
        });
    }
}