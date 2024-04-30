import com.reverso.controller.ControllerAccueil;
import com.reverso.log.FormatterLog;
import com.reverso.log.LoggerRE;


import javax.swing.*;
import java.util.logging.FileHandler;
import java.util.logging.Level;



// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    private static FileHandler fh = null;
    public static void main(String[] args){
        try {
            fh = new FileHandler("logReverso", true);

            LoggerRE.LOGGER.setUseParentHandlers(false);
            LoggerRE.LOGGER.addHandler(fh);

            fh.setFormatter(new FormatterLog());
            LoggerRE.LOGGER.log(Level.INFO, "Début du programme.");

            ControllerAccueil.init();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Une erreur est survenue.");
            e.printStackTrace();
            System.exit(1);
        }

        LoggerRE.LOGGER.log(Level.INFO, "Fin du programme. ");
        System.out.println("fin du main");
    }
}
