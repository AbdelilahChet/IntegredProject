package constants;
import java.util.logging.Logger;

public class Regex {

    private static Logger LOGGER = Logger.getLogger(Regex.class.getName());
    /**
     * try to match enzo.the@gmail.com
     * but not @@tata
     * @param mail the candidate mail
     * @return true if match the regex else false
     */
    public boolean addresMailMatch(String mail){
        boolean result = mail.matches("\\b[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}\\b");
        if(!result)
            LOGGER.warning("the address mail: " + mail + " doesn't match the regex \\b[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}\\b");
        return result;
    }

}
