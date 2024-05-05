package Component;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.Transferable;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SquaredInputField extends JPanel {
    // int gap = 20;

    Runnable onLeftInLeftestField;
    Runnable onRightInRightestField;
    Runnable onEnter;
    Runnable onUp;
    Runnable onDown;


    public void setText(String text) {
        // remove all input fields
        for (InputField inputField : inputFieldList) {
            remove(inputField);
        }
        inputFieldList.clear();

        // add new input fields
        for (int i = 0; i < text.length(); i++) {
            InputField newInputField = getInputField();
            newInputField.setText(text.charAt(i)+"");
            add(newInputField);
            inputFieldList.add(newInputField);
        }
        
        InputField newInputField = getInputField();
        newInputField.setText("");
        add(newInputField);
        inputFieldList.add(newInputField);

        repaint();
        revalidate();
    }
    public String getText() {
        StringBuilder sb = new StringBuilder();
        for (InputField inputField : inputFieldList) {
            String text = inputField.getText();
            if(text.length() > 0)
                sb.append(text);
        }
        return sb.toString();
    }

    void removeAllEmptyFields() {
        int i = 0;
        while(i < inputFieldList.size() - 1) {
            String text = inputFieldList.get(i).getText();
            if(text.equals("") || text.equals(" ")) {
                remove(i); 
                inputFieldList.remove(i);
            } else {
                i++;
            }
        }
        for(InputField input : inputFieldList) {
            input.setCaretPosition(0);
            if(input.getText().length() < 1) continue;
            input.setText(input.getText().charAt(0)+"");
        }

        repaint(); revalidate();
    }

    // each will only contain 1 letter, default is 5
    ArrayList<InputField> inputFieldList = new ArrayList<InputField>();
    public SquaredInputField() {
        setBackground(Colors.slate950);
        
        InputField newInputField = getInputField();
        add(newInputField); repaint(); revalidate(); inputFieldList.add(newInputField);
        
        StartBlinkingAnimation();

    }

    public InputField getInputField() {
        InputField inputField = new InputField(1);
        inputField.setFont(Fonts.OutfitBold.deriveFont(24f));
        inputField.setHorizontalAlignment(JTextField.CENTER);
        inputField.setCaretColor(Colors.slate800);


        inputField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {
                removeAllEmptyFields();
            }

            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                // Set all input field before the last one to be unblink
                for (int i = 0; i < inputFieldList.size() - 1; i++) {
                    setBlink(inputFieldList.get(i), true);
                }

                @SuppressWarnings("deprecation")
                int modifiers = e.getModifiers();
                @SuppressWarnings("deprecation")
                int CTRL_MASK = KeyEvent.CTRL_MASK;

                if ((e.getKeyCode() == KeyEvent.VK_V) && ((modifiers & CTRL_MASK) != 0)) {
                    try{
                        String text = getPasteFromClipboard();
                        System.out.println(text);
                        setText(text);
                    } catch (Exception ex) {
                        // ex.printStackTrace();
                    }
                    return;
                }

                int keyCode = e.getExtendedKeyCode();
                int index = inputFieldList.indexOf(inputField);
                switch (keyCode) {
                    case KeyEvent.VK_BACK_SPACE:
                        if(index == 0) return;
                        InputField prevInputField = inputFieldList.get(index - 1);
                        focusMoveLeft(prevInputField);

                        inputFieldList.remove(index-1);
                        remove(index-1); repaint(); revalidate();
                        e.consume();
                        return;
                    case KeyEvent.VK_DELETE:
                        if(index >= inputFieldList.size() - 2){
                            e.consume();
                            return;
                        }
                        inputFieldList.remove(index + 1);
                        remove(index + 1); repaint(); revalidate(); 
                        e.consume();
                        return;
                    case KeyEvent.VK_LEFT:
                        if (index > 0) {
                            focusMoveLeft(inputFieldList.get(index - 1));
                        }
                        if (inputFieldList.get(0).hasFocus()) {
                            if(onLeftInLeftestField != null) onLeftInLeftestField.run();
                        }
                        return;
                    case KeyEvent.VK_RIGHT:
                        if (index < inputFieldList.size() - 1) {
                            focusMoveLeft(inputFieldList.get(index + 1));
                        }
                        if (inputFieldList.get(inputFieldList.size() - 1).hasFocus()) {
                            if(onRightInRightestField != null) onRightInRightestField.run();
                        }
                        return;
                    case KeyEvent.VK_SPACE:
                        if(inputField.getText().length() > 0) {
                            inputField.setText("");
                        }
                        e.consume();
                        return;
                    case KeyEvent.VK_ENTER:
                        if(onEnter != null) onEnter.run();
                        return;
                    case KeyEvent.VK_DOWN:
                        if(onDown != null) onDown.run();
                        return;
                    case KeyEvent.VK_UP:
                        if(onUp != null) onUp.run();
                        return;
                }
                

                // if the input is not a letter, ignore
                if (!Character.isLetter(e.getKeyChar())) {
                    removeAllEmptyFields();
                    e.consume();
                    return;
                }

                if(inputField.getText().length() > 0) {
                    inputField.setText("");
                }
                
                // Add to next
                if(index == inputFieldList.size() - 1) {
                    InputField newInputField = getInputField();
                    add(newInputField); repaint(); revalidate(); inputFieldList.add(newInputField);
                    focusMoveLeft(newInputField);
                } else {
                    focusMoveLeft(inputFieldList.get(index + 1));
                }

                e.consume();
            }

        });

        return inputField;
    }

    // Blinking animation for the last input field
    Thread blinkingThread;
    public void StartBlinkingAnimation() {
        if(blinkingThread != null) {
            blinkingThread.interrupt();
        }
        blinkingThread = new Thread(new Runnable() {
            @Override
            public void run() {
                boolean on = true;
                while (true) {
                    try {
                        Thread.sleep(500);
                        InputField lastInputField = inputFieldList.get(inputFieldList.size() - 1);
                        // Set all input field before the last one to be visible
                        for (int i = 0; i < inputFieldList.size() - 1; i++) {
                            setBlink(inputFieldList.get(i), true);
                        }

                        on = !on;
                        setBlink(lastInputField, on);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        blinkingThread.start();
    }
    public void StopBlinkingAnimation() {
        if(blinkingThread != null) {
            blinkingThread.interrupt();
        }
    }

    void setBlink(InputField inputField, boolean on){
        if (on) {
            inputField.setBackground(Colors.slate800); 
            inputField.setCaretColor(Colors.slate800);
            inputField.repaint();
        } else {
            inputField.setBackground(Colors.slate900); 
            inputField.setCaretColor(Colors.slate900);
            inputField.repaint();
        }
    }

    public void setFocusToLeftestField() {
        focusMoveLeft(inputFieldList.get(0));
    }

    void focusMoveLeft(InputField inputField) {
        inputField.setCaretPosition(0);
        inputField.requestFocus();
    }

    public static String getPasteFromClipboard() {
        Clipboard clipboard = java.awt.Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable clipData = clipboard.getContents(clipboard);
        if (clipData != null) {
            if (clipData.isDataFlavorSupported(java.awt.datatransfer.DataFlavor.stringFlavor)) {
                try {
                    String s = (String) (clipData.getTransferData(java.awt.datatransfer.DataFlavor.stringFlavor));
                    return s;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }
}
