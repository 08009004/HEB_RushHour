package g42116.rushhour.view;

import g42116.rushhour.jsonIO.Language;
import g42116.rushhour.model.Direction;
import static g42116.rushhour.model.Direction.*;
import g42116.rushhour.model.RushHourException;
import g42116.rushhour.model.RushHourGame;
import static g42116.rushhour.view.Colour.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This static class interfaces the game for player keyboard entries.
 * 
 * @author g42116
 */
public class UserInput {
    
    private Language lang;

    /**
     * Class constructor.
     * 
     * @param   lang    the current game language.
     */
    public UserInput(Language lang) {
        this.lang = lang;
    }

    /**
     * Language attribute setter.
     * 
     * @return the language attribute
     */
    public Language getLang() {
        return lang;
    }

    /**
     * Language attribute setter.
     * 
     * @param   lang    the new language
     */
    public void setLang(Language lang) {
        this.lang = lang;
    }

    /**
     * Asks user to key in a character, until only a single character is keyed 
     * in (blank characters are ignored).
     * 
     * @param   query   message printed to screen to prompt the user to key in a 
     *                  character
     * @param   error   message printed to screen to prompt for a new input if 
     *                  user didn't key in a single non-blank character 
     *                  (coloured in red)
     * @return          user's entry (upper case)
     */
    private char askChar(String query, String error) {
        Scanner keyboard = new Scanner(System.in);
        String str1;
        System.out.println(query);
/*      API: "A Scanner breaks its input into tokens using a delimiter pattern, 
        which by default matches whitespace."
        nextLine() "advances this scanner past the current line and returns the 
        input that was skipped."
*/
        do {
            str1 = keyboard.nextLine();
            str1 = str1.replace(" ", "").replace("\t", "");  // \t == tabulation
            str1 = str1.toUpperCase();

            if (str1.equals("")) {
                System.out.println(
                        ColourString.to(
                                lang.getErrCharsOnlyBlank() + error, null, RED));
            }

            if (str1.length() > 1) {
                System.out.println(
                        ColourString.to(
                                lang.getErrCharsSeveral() + error, null, RED));
            }

        } while (str1.length() > 1 || str1.equals(""));

        return str1.charAt(0);
    }

    /**
     * Scans and displays a folder content (list of files), and asks player to 
     * chose a file amongst those.
     * 
     * @param   fldrPath    the path of the folder
     * @param   msg1        the message displayed to query user to select one of
     *                      the files
     * @param msg2          the message displayed to query for a new entry if
     *                      previous one was not valid
     * @return 
     */
    private String askFile(String fldrPath, String msg1, String msg2) {
        List<String> folderContent = null;
        try {
            folderContent = linesOf(createTempFile(fldrPath));
        } catch (IOException | RushHourException ex) {
            System.out.println(" ex = " +  ex);
        }
        
        String printListItem;
        int index;
        int selected;

        do {
            index = 1;

            for (String fileName : folderContent) {
                printListItem = index + " - " + fileName;
                printListItem = printListItem.replace(".json", "");
                System.out.println(printListItem);
                index++;
            }

            selected = Character.getNumericValue(askChar(msg1, msg2));
            selected--;
     
        } while (selected < 0 || selected > folderContent.size() - 1);

        return folderContent.get(selected);
    }

    /**
     * Creates a .txt copy at project root of any given file. Also works with 
     * files nested inside of a jar archive, hence  allowing access without 
     * needing to extract it.
     * 
     * @param   filePath    the file path of the file to copy.
     * @return              the copied file
     * @throws IOException 
     */
    public File createTempFile(String filePath) throws IOException {
        File f=new File("temp.txt");
        InputStream input = this.getClass().getResourceAsStream(filePath);
        OutputStream output=new FileOutputStream(f);
        byte buf[]=new byte[1024];
        int len;

        /* Comme discuté au cours, la ligne suivante ne marche pas quand on
         * exécute le programme depuis le jar -> getResourceAsStream (ligne 147)
         * renvoie :
         * - java.io.FilterInputStream.read() si le programme est exécuté sous
         *   Netbeans. -> OK
         * - null si le programme est exécuté avec 'java -jar' -> crash
         *
         * Une solution qui fonctionerait pour le jar mais plus sous NetBeans
         * serait d'utiliser ZipInputStream et 
         * ZipEntry.zgetNextEntry().getName() comme expliqué ici :
         * http://stackoverflow.com/questions/1429172/how-do-i-list-the-files-inside-a-jar-file
         *
         * Nous nous sommes mis d'accord que le projet vous convenait en l'état.
         */
        while((len=input.read(buf))>0) output.write(buf,0,len);

        output.close();
        input.close();
        return f;
    }

    /**
     * 
     * @param file
     * @return
     * @throws RushHourException 
     */
    private List<String> linesOf(File file) throws RushHourException {
        int i = 0;
        File temp = new File("temp.txt");
        Scanner inputFile;

        try {
            inputFile = new Scanner(temp);
        } catch (FileNotFoundException ex) {
            throw new RushHourException(
                           "UserInput.linesOf(): Problem accessing temp file.");
        }

        while(inputFile.hasNextLine()) {
            i++;
            inputFile.nextLine();
        }
        inputFile.close();

        String[] fileLines = new String[i];

        int j = 0;
        try {
            inputFile = new Scanner(temp);
        } catch (FileNotFoundException ex) {
            throw new RushHourException(
                             "UserInput.linesOf(): Problem reading temp file.");
        }

        while(inputFile.hasNext()) {
            fileLines[j] = inputFile.nextLine();
            System.out.println(fileLines[j]);
            j++;
        }
        inputFile.close();

        return Arrays.asList(fileLines);
    }

    /**
     * Asks user to select the game language amongst the language init files,
     * from a folder content list.
     * 
     * @return              the selected language configuration relative file 
     *                      path (from project root package, included)
     */
    public String askLang() {
        // Path to the folder where the language files are stored:
        String folder = "/g42116/rushhour/jsonIO/resources/languages";

        System.out.println(lang.getListLangFiles());
        String query = lang.getQueryLang();
        String reQuery = lang.getReQueryLang();    // Upon incorrect user entry.

        String filePath = folder + "/" + askFile(folder, query, reQuery);
        return filePath;
        
    }

    /**
     * Asks user to select the game he wants to play amongst the initial board
     * files (a list of JSon objects which is the content of a folder).
     * 
     * @return              the selected initial board configuration relative  
     *                      file path (from project root package, included)
     */             
    public String askBoard() {
        // Path to the folder where the game files are stored:
        String folder = "/g42116/rushhour/jsonIO/resources/games";

        System.out.println(lang.getListGameInitFiles());
        String query = lang.getQueryGameInit();
        String reQuery = lang.getReQueryGameInit();// Upon incorrect user entry.

        String filePath = folder + "/" + askFile(folder, query, reQuery);
        System.out.println("IN ASK_BOARD: filePath = " +  filePath);
        return filePath;
    }

    /**
     * Asks player to key in the ID of the car that he wants to move.
     * 
     * @param   game    the current game
     * @return          player's entry (in upper case)
     */
    public char askId(RushHourGame game) {
        char carID;

        do {
            carID = askChar(lang.getQueryCarId(), lang.getErrNotAnId());
            if (!game.isValidId(carID)) {
                    System.out.println(
                            ColourString.to(lang.getErrNoSuchCar(), null, RED));
                    carID = askChar(lang.getQueryCarId(), lang.getErrNotAnId());
            }
        } while (!game.isValidId(carID));

        return carID;
    }

    /**
     * Asks user to key in a direction, until a valid key is pressed: U for Up, 
     * D for Down, L for Left or R for Right. This method is not case sensitive.
     *             
     * @return  character representative of the user's choice (upper case)
     */
    public Direction askDir() {

        char keyedIn;
        String query = lang.getQueryDir().concat("\n" + lang.getListDirChoices());

        do {
            keyedIn = askChar(query, lang.getErrInvalidDir());
            switch (keyedIn) {
                case 'U': return UP;
                case 'D': return DOWN;
                case 'L': return LEFT;
                case 'R': return RIGHT;
                default:
                    System.out.print(keyedIn + 
                             ColourString.to(lang.getErrIsNotDir(), null, RED));
                    break;
            }
        } while (true);
    }
}
