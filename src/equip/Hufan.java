/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package equip;
import javax.swing.*;  
import java.awt.event.*;  

/**
 *
 * @author 3
 */
public class Hufan extends JFrame implements ActionListener{  
    JButton jb;  
    JPanel jp;  
    public static void main(String[] args) {  
        new Hufan();  
    }  
  
    public Hufan() {  
        jb = new JButton("窗口抖动!");  
        jp=new JPanel();  
        this.add(jp);  
        jp.add(jb);  
        jb.addActionListener(this);  
        this.setSize(300, 200);  
        this.setLocation(500, 400);  
        this.setVisible(true);  
        this.setResizable(false);  
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
    }  
    public void actionPerformed(ActionEvent e) {  
        int x = this.getX();  
        int y = this.getY();  
        for (int i = 0; i < 20; i++) {  
            if ((i & 1) == 0) {  
                x += 3;  
                y += 3;  
            } else {  
                x -= 3;  
                y -= 3;  
            }  
            this.setLocation(x, y);  
            try {  
                Thread.sleep(50);  
            } catch (Exception e1) {  
                e1.printStackTrace();  
            }  
        }  
    }  
}  