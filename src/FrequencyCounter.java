import org.w3c.dom.ls.LSOutput;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FrequencyCounter {
    public static void main(String[] args){
        int minlen = Integer.parseInt(args[0]);
        //SequentialSearchST<String, Integer> st = new SequentialSearchST<String,Integer>();
        BinarySearchST<String, Integer> st = new BinarySearchST<String,Integer>();

        File file;
        final JFileChooser fc = new JFileChooser();
        if(fc.showOpenDialog(null)==JFileChooser.APPROVE_OPTION) {
            file = fc.getSelectedFile();
        }
        else{
            JOptionPane.showMessageDialog(null,"파일을 선택하세요","오류",JOptionPane.ERROR_MESSAGE);
            return;
        }
        Scanner sc = null;
        try{
            sc = new Scanner(file);
            long start = System.currentTimeMillis();
            while(sc.hasNext()){
                String word = sc.next();
                if(word.length() < minlen)  continue;
                if(!st.contains(word)) st.put(word,1);
                else    st.put(word,st.get(word) + 1);
            }
            String maxKey = ""; int maxValue = 0;
            for(String word : st.keys()){
                if(st.get(word) > maxValue){
                    maxValue = st.get(word);
                    maxKey = word;
                }
            }
            long end = System.currentTimeMillis();
            System.out.println(maxKey + " " + maxValue);
            System.out.println("소요시간 = " + (end - start) + "ms");
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        if(sc!=null) sc.close();
    }
}
