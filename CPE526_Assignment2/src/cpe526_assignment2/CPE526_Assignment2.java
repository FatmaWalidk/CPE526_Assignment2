package cpe526_assignment2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 *
 * @author Fatma
 */
public class CPE526_Assignment2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String path = System.getProperty("user.dir")+"/input.txt";
        CaesarCipher test = new CaesarCipher(new File(path));
    }
    
}
class CaesarCipher {
    private final char[] English = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
    private final char[] French =  {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
    private final char[] Spanish = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','ñ','o','p','q','r','s','t','u','v','w','x','y','z'};
    private final char[] Turkish = {'a','b','c','ç','d','e','f','g','ğ','h','ı','i','j','k','l','m','n','o','ö','p','r','s','ş','t','u','ü','v','y','z'};
    private int shiftCount, method, language;
    private String text;
    private File inputFile; 
	
    public CaesarCipher(File inputFile) {
        this.inputFile = inputFile;
        this.ReadFile(); 
    }
 
    public void ReadFile()  {	
        FileReader fr = null;
        BufferedReader readText = null;
        String line = null;
        try {
            fr = new FileReader(this.inputFile);
            readText = new BufferedReader (fr);
            if((line = readText.readLine()) != null) {	
                this.shiftCount = Integer.parseInt(line.split(":") [0]);
                this.method = Integer.parseInt(line.split(":") [1]);
                this.language = Integer.parseInt(line.split(":") [2]);
                this.text = line.split(":") [3];
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                readText.close();
            }catch(Exception e) {
                e.printStackTrace();
            }
        }

        if (method == 0) {
            this.text = text.toLowerCase();
            this.Encrypt();
        } else if(method == 1) {
            this.text = text.toUpperCase();
            this.De_crypt();
        }
    }

    public char[] GetDictionary(int count, char [] alphabet){	
        char[] dictionary = new char[alphabet.length];
        for(int i= 0; i<dictionary.length;i++) {
            dictionary[i]= alphabet[(i+count) % alphabet.length];
        }
        return dictionary;
    }

    public void Encrypt() {
        String cipherText = "";
        char[] dictionary = null ;
        char [] alphabet = null;
        switch(this.language){
            case 0:dictionary = this.GetDictionary(this.shiftCount, English); alphabet = English; break;
            case 1:dictionary = this.GetDictionary(this.shiftCount, French); alphabet = French; break;
            case 2:dictionary = this.GetDictionary(this.shiftCount, Spanish);alphabet = Spanish; break;
            case 3:dictionary = this.GetDictionary(this.shiftCount, Turkish); alphabet = Turkish;break;	
        }
        for (char c : this.text.toCharArray()) {
            if( Character.isWhitespace(c)) {
                cipherText +=c;
            } else {
                for(int i = 0; i < alphabet.length; i++) {
                    if( Character.toLowerCase(c) == alphabet[i]) {
                        cipherText += dictionary[i];
                    }
                }
            }
        }
        System.out.println(cipherText.toUpperCase());
    }

    public void De_crypt() {
        String plainText = "";
        char[] dictionary = null;
        char [] alphabet = null;
        switch(this.language){
            case 0:dictionary = this.GetDictionary(this.shiftCount, English); alphabet = English; break;
            case 1:dictionary = this.GetDictionary(this.shiftCount, French); alphabet = French; break;
            case 2:dictionary = this.GetDictionary(this.shiftCount, Spanish);alphabet = Spanish; break;
            case 3:dictionary = this.GetDictionary(this.shiftCount, Turkish); alphabet = Turkish;break;				
        }
        for (char c : this.text.toCharArray()) {
            if( Character.isWhitespace(c)) {
                plainText +=c;
            } else {
                for(int i = 0; i < alphabet.length; i++) {
                    if(Character.toLowerCase(c) == dictionary[i]) {
                        plainText +=  alphabet[i];
                    }
                }
            }
        }
        System.out.println(plainText.toLowerCase());
    }

}