package wordanalyzer;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

// Word analyzer control
public class Analyzer extends JFrame implements ActionListener {

    // Stores words and frequency in decending order
    private ArrayList<Pair> sortedWords;
    // Screen width
    private static final int WIDTH=500;
    // Screen height
    private static final int HEIGHT=700;
    // File open(choose) button
    private JButton openFileBtn;
    // File chooser
    private JFileChooser chooser;
    // Table to display result
    private JTable table;
    
    // Constructor for initialization
    public Analyzer() {
        super("Word Analyzer");
        // Sets screen size
        setSize(WIDTH,HEIGHT);
        // Sets screen location
        setLocation(200,0);
        // Sets screen layout as null
        setLayout(null);
        // Sets screen resizalble false
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Initializes
        init();
        //Sets screen visible true
        setVisible(true);
    }

    // Initializes
    private void init()
    {
        // initializes arraylist
        sortedWords=new ArrayList<>();
        // Initializes button
        openFileBtn =new JButton();
        // sets text for button
        openFileBtn.setText("choose file");
        // sets position and size
        openFileBtn.setBounds(50,50,100,20);
        // sets event listener
        openFileBtn.addActionListener(this);
        // adds button to screen
        add(openFileBtn);
        // Creates table model
        DefaultTableModel tableModel=new DefaultTableModel();
        // Adds table header column
        tableModel.addColumn("No");
        tableModel.addColumn("Word");
        tableModel.addColumn("Frequency");
        // Initialize table
        table=new JTable(tableModel);
        // Sets table position and size
        table.setBounds(20,100,200,400);
        // Creates scrollpane for table
        JScrollPane sp=new JScrollPane(table);
        // Sets scroll pane position and size
        sp.setBounds(0,100,WIDTH,HEIGHT-150);
        // Adds Scroll Pane to screen
        add(sp);
        chooser=new JFileChooser();
        // Filter for file chooser
        FileNameExtensionFilter filter = new FileNameExtensionFilter("*.txt", "txt");
        // sets file filter
        chooser.setFileFilter(filter);

    }


    // Opens file
    private void openFile() {
        // Shows file choose dialog
        int val = chooser.showOpenDialog(this);
       
        if (val == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            // Reads words from choosen file
            readWord(file);
            // Sets up the table
            setUpTable();
        }
    }


    // Sets up table for final result view
    private void setUpTable()
    {
        // Gets table model from table
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        // Clears table
        model.setNumRows(0);
        // Displays result to table
        for (int i = 0; i < sortedWords.size(); i++) {
            Pair sortedWord = sortedWords.get(i);
            model.addRow(new Object[]{(i+1),sortedWord.getWord(),sortedWord.getCount()});
        }
    }
    
    // Sorts list in decending order uses bubble sort
    private void sort(ArrayList<Pair> list)
    {
        boolean needSwap=true;
        // loops until fully sorted
        while (needSwap)
        {
            needSwap=false;
           
            for (int i = 1; i < list.size(); i++) {
                // if previous one is less than current one then swaps it
                if(list.get(i-1).getCount()<list.get(i).getCount())
                {
                    Pair t=list.get(i-1);
                    list.set(i-1,list.get(i));
                    list.set(i,t);
                    needSwap=true;
                }
            }
        }
    }

    // Reads word from file
    private void readWord(File file)
    {
        try {
            // Stores word and counts into hashmap
            Map<String,Integer> wordVsCount=new HashMap<>();
            // Clears the arraylist
            sortedWords.clear();
            Scanner reader = new Scanner(file);
            // loops until fully read line
            while (reader.hasNextLine()) {
                // reads line by line
                String line = reader.nextLine();
                // splits line by space
                String[] datas = line.split(" ");
                
                for (String w : datas) {
                    // ignores all special character and space
                    String word=w.replaceAll("[^a-zA-Z0-9]", "").trim();
                    // if word is empty then continues
                    if(word.isEmpty())
                        continue;
                    // if words already in hashmap then increases frequency by one
                    if(wordVsCount.containsKey(word))
                    {
                        int count=wordVsCount.get(word);
                        count++;
                        wordVsCount.replace(word,count);
                    }
                    // if word is not in hashmap then adds into hashmap
                    else
                    {
                        wordVsCount.put(word,1);
                    }
                }
            }
            // closes the scanner
            reader.close();
            // gets keys from hashmap
            Set<String> keys = wordVsCount.keySet();
            for (String key : keys) {
                // creates pair
                Pair pair=new Pair(key,wordVsCount.get(key));
                // adds pair into list
                sortedWords.add(pair);
            }
            // sorts array list
            sort(sortedWords);
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        }
    }

    // button listener
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == openFileBtn) {
            // opens file
            openFile();
        }
    }

    public static void main(String[] args) {
        Analyzer analyzer=new Analyzer();
    }

}
