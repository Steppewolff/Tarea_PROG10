/**
 *
 * @author Fernando Gómez Romano
 */

import javax.swing.*;
import java.awt.event.*;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;
import java.awt.Font;
import javax.swing.Timer;

public class NewContainer extends JFrame implements ActionListener {
    
    private JButton generaCarton;
    private JPanel cartonBingo;
    private JButton startNumero;
    private JLabel panelNumeros;
    private int bingoMatriz[][];
    private JButton bingoCasillas[][];
    private int min = 1;
    private int max = 90;    
    
    public NewContainer(){

        //
        setLayout(null);

        //
        setBounds(450,0,1000,450);

        //
        setTitle("TarjetaBingo");

        //Redimensionable
        setResizable(true);

        //
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //
        generaCarton = new JButton("Nuevo cartón");
        generaCarton.setBounds(570,30,150,40);
        add(generaCarton);
        generaCarton.addActionListener(this);

        //
        startNumero = new JButton("Comenzar Bingo");
        startNumero.setBounds(280,30,150,40);
        add(startNumero);
        startNumero.addActionListener(this);

        //
        cartonBingo = new JPanel();
        cartonBingo.setBounds(50, 100, 900, 300);
        add(cartonBingo);
        cartonBingo.setBorder(BorderFactory.createLineBorder(Color.black));
        cartonBingo.setLayout(new GridLayout(3, 9));

        //
        panelNumeros = new JLabel("0",JLabel.CENTER);
        panelNumeros.setBounds(460, 10, 80, 80);
        panelNumeros.setFont(new Font("Verdana", Font.PLAIN, 24));
        add(panelNumeros);
        panelNumeros.setBorder(BorderFactory.createLineBorder(Color.black));
        
        bingoCasillas = new JButton[3][9];
        
        generaCarton();

        //Muestro JFrame (lo último para que lo pinte todo correctamanete)
        setVisible(true);
    }

    public void generaCarton(){
        bingoMatriz = new int[3][9];
        ArrayList<Integer>numerosValidos = new ArrayList<>();
        int num;
        
        for (int c = 0; c < 9; c++) {
            ArrayList<Integer>columna = new ArrayList<>();
            for (int f = 0; f < 3; f++) {
                do {
                    if (c == 0){
                        num = random (0, 9);
                    }else if (c == 8){
                        num = random (81, 90);
                    }else{
                        num = random ((c*10)+1, (c+1)*10);
                    }
                } while(numerosValidos.contains(num));
                numerosValidos.add(num);
                columna.add(num);
            }
            Collections.sort(columna);
            for (int i = 0; i < 3; i++){
                bingoMatriz[i][c] = columna.get(i);
            }
        }
        
        for (int f = 0; f < 3; f++) {
            int vacios = 0;
            do {
               int col = random (0, 8);
               if (bingoMatriz[f][col] != 0){
                   bingoMatriz[f][col] = 0;
                   vacios++;
               } 
            }while (vacios < 4);

            for (int c = 0; c < 9; c++) {
                if (bingoMatriz[f][c]==0){
                    bingoCasillas[f][c] = new JButton("X");
                    bingoCasillas[f][c].setBackground(Color.BLACK);
                    bingoCasillas[f][c].setForeground(Color.RED);
                    bingoCasillas[f][c].setFont(new Font("Verdana", Font.PLAIN, 20));                    
                }else{
                    bingoCasillas[f][c] = new JButton(""+bingoMatriz[f][c]);//String numCadena =  Integer.toString(bingoMatriz[f][c]);
                    bingoCasillas[f][c].addActionListener(this);
                    bingoCasillas[f][c].setFont(new Font("Verdana", Font.PLAIN, 20));                    
                }
                bingoCasillas[f][c].setBounds(0, 0, 100, 100);

                cartonBingo.add(bingoCasillas[f][c]);
                cartonBingo.setVisible(true);
            }
        }
    }
    
    public void renovarNumerosCarton(){
        for (int f = 0; f < 3; f++) {
            for (int c = 0; c < 9; c++) {
//                bingoCasillas[f][c] = new JButton(String str = Integer.toString(bingoMatriz[f][c]));
                bingoCasillas[f][c] = new JButton(""+bingoMatriz[f][c]);//String numCadena =  Integer.toString(numEntero);
                bingoCasillas[f][c].setBounds(0, 0, 100, 100);

                cartonBingo.add(bingoCasillas[f][c]);
                cartonBingo.setVisible(true);
                
                bingoCasillas[f][c].setBounds(0, 0, 100, 100);

                cartonBingo.add(bingoCasillas[f][c]);
                cartonBingo.setVisible(true);
            }
        }
    }
    
    //
    public static int random (int min, int max){
        return new Random().nextInt(max - min + 1) + min;
    }
    
    public void showNumbers(){
           
        int delay = 2000;
        ArrayList<Integer> numeros = new ArrayList<>();
        Timer timer = new Timer( delay, new ActionListener() {
          private int counter = 0;
          @Override
          public void actionPerformed( ActionEvent e ) {
            int aux = random(1, 90);
            if (!numeros.contains(aux)){
                panelNumeros.setText( aux + "" );
                counter++;//keep track of the number of times the Timer executed
                numeros.add(aux);
            }
            //El timer se detiene cuando llega a 90
            if ( counter == 90){
              ( ( Timer ) e.getSource() ).stop();
            }
          }
        });
        timer.setInitialDelay( delay );
        timer.start();
}
    
    //Evento disparado por botón generaCarton
    public void actionPerformed(ActionEvent evt) {
      //  
      if (evt.getSource()==generaCarton) {
        //  
        setTitle("Nuevo cartón");
        cartonBingo.removeAll();
        generaCarton();
        //Muestro JFrame (lo último para que lo pinte todo correctamanete)
        setVisible(true);
      }else if (evt.getSource()==startNumero){
        setTitle("Start numbers");
        showNumbers();
      }
      
      for (int f = 0; f < 3; f++) {
          for (int c = 0; c < 9; c++) {
              if (evt.getSource()==bingoCasillas[f][c]){
                  bingoCasillas[f][c].setBackground(Color.GREEN);
                  bingoCasillas[f][c].setForeground(Color.WHITE);
              }
          }
      }
    }
}