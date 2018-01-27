package constants;

import bean.Project;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CONSTANTS {
    private final static Logger LOGGER = Logger.getLogger(Project.class.getName());
    private static CONSTANTS ourInstance = new CONSTANTS();
    private String absolutePath = "/home/abdel/work/depot/";

    private CONSTANTS() {
    }

    public static CONSTANTS getInstance() {
        return ourInstance;
    }

    /**
     * will create a new directory in the absolute path
     *
     * @param project the name of the project
     */
    public void createProjectOnDisk(String project) {
        LOGGER.setLevel(Level.ALL);
        LOGGER.info("Attempting to create a folder for the project " + project + "...");
        LOGGER.info("Folder name will be: " + absolutePath + project);
        File dir = new File(absolutePath + project);
        if (!dir.canWrite())
            LOGGER.warning("No rights to write on this directory! ");
        if (dir.mkdirs())
            LOGGER.info("New folder successfully created: " + absolutePath + project);
        else
            LOGGER.severe("New folder failed to be created: " + absolutePath + project);
    }

    /**
     * will create a file on the disk at the given path
     *
     * @param file the name of the file you want to create, it will be the concatenation
     *             of the absolute path, the project name, the file path, and the file name
     * @throws IOException
     */
    public void createFileOnDisk(String file) throws IOException {
        file = absolutePath + file;
        LOGGER.setLevel(Level.ALL);
        LOGGER.info("Attempting to create a file " + file);
        File f = new File(file);
        if (f.getParentFile().mkdirs()) {
            if (f.createNewFile())
                LOGGER.info("file created");
            else
                LOGGER.warning("failed to create file");
        }
    }

    /**
     * will write on a given file
     *
     * @param content  the content of what you want to write
     * @param fileName the path to the file since the project name + the file name
     */
    public void writeOnFileOnDisk(String content, String fileName) {
        Writer writer = null;

        try {
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(absolutePath + fileName), "utf-8"));
            writer.write(content);
            LOGGER.info("file " + fileName + " edited.");
        } catch (IOException ex) {
            LOGGER.warning("unable to write on file");
        } finally {
            try {
                writer.close();
            } catch (Exception ex) {/*ignore*/}
        }

    }

    /**
     * will delete a given file from the disk
     *
     * @param filename the filename
     */
    public void deleteFileOrDirectoryOndisk(String filename) {
        try {
            File file = new File(absolutePath + filename);
            if (file.delete()) {
                LOGGER.info(filename + " deleted.");
            } else {
                LOGGER.warning("Delete operation is failed.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * will get the content of a given file
     *
     * @param filename his name
     * @return return his content
     */
    public String getContentFile(String filename) {
        try {
            FileReader reader = new FileReader(filename);
            String str = reader.toString();
            reader.close();
            return str;
        } catch (FileNotFoundException ex1) {
            ex1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
