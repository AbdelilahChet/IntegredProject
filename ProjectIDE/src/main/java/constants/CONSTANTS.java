package constants;

import bean.Project;

import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class CONSTANTS{
    private static final Logger LOGGER = Logger.getLogger(Project.class.getName());
    private static CONSTANTS ourInstance = new CONSTANTS();
    private String absolutePath = "";
    // le temps qu'un fichier peut etre lock
    private String loggerSeparator = "###############################################################\n";
    private long timeLocket = 500;

    private CONSTANTS() {
    }

    public static CONSTANTS getInstance() {
        return ourInstance;
    }

    /**
     * will create a new directory in the absolute path
     *
     * @param project the name of the project
     * @return chemin du projet
     */
    public String createProjectOnDisk(String project) {
        LOGGER.setLevel(Level.ALL);
        LOGGER.info("Attempting to create a folder for the project " + project + "...");
        LOGGER.info("Folder name will be: " + getAbsolutePath() + project);
        File dir = new File(getAbsolutePath() + project);
        if (!dir.canWrite())
            LOGGER.warning("No rights to write on this directory! ");
        if (dir.mkdirs())
            LOGGER.info("New folder successfully created: " + getAbsolutePath() + project);
        else
            LOGGER.severe("New folder failed to be created: " + getAbsolutePath() + project);
        return getAbsolutePath() + project;
    }

    /**
     * will create a file on the disk at the given path
     *
     * @param file the name of the file you want to create, it will be the concatenation
     *             of the absolute path, the project name, the file path, and the file name
     * @return chemin du fichier
     * @throws IOException
     */
    public String createFileOnDisk(String file) throws IOException {
        String completePath = getAbsolutePath() + file;
        LOGGER.setLevel(Level.ALL);
        LOGGER.info("Attempting to create a file " + completePath);
        File f = new File(completePath);
        if (f.getParentFile().mkdirs()) {
            if (f.createNewFile())
                LOGGER.info("file created");
            else
                LOGGER.warning("failed to create file");
        }
        return file;
    }

    /** Génère un dossier à l'edroit donnée
     * @param path, nom du dossier
     * @return chemin du dossier
     */
    public String createFolderOnDisk(String path){
        LOGGER.setLevel(Level.ALL);
        LOGGER.info("Attempting to create a folder " + path + "...");
        LOGGER.info("Folder name will be: " + getAbsolutePath() + path);
        File dir = new File(getAbsolutePath() + path);
        if (dir.mkdirs())
            LOGGER.info("New folder successfully created: " + getAbsolutePath() + path);
        else
            LOGGER.severe("New folder failed to be created: " + getAbsolutePath() + path);
        return getAbsolutePath() + path;
    }

    /** Récupère tous les dossiers au chemin indiqué
     * @param path, nom du dossier
     * @return liste de dossier
     */
    public List<File> findFolderOnDisk(String path){
        LOGGER.setLevel(Level.ALL);
        LOGGER.info("Attempting to find a folder " + path + "...");
        LOGGER.info("Folder name will be: " + getAbsolutePath() + path);
        File dir = new File(getAbsolutePath() + path);
        List<File> dirList = new ArrayList<File>();
        File[] dirTab = dir.listFiles();
        for(File folder : dirTab){
            if(folder.isDirectory()) {
                dirList.add(folder);
            }
        }
        if (dir.mkdirs())
            LOGGER.info("New folder successfully created: " + getAbsolutePath() + path);
        else
            LOGGER.severe("New folder failed to be created: " + getAbsolutePath() + path);

        return dirList;
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
                    new FileOutputStream(getAbsolutePath() + fileName), "utf-8"));
            writer.write(content);
            LOGGER.info("file " + fileName + " edited.");
        } catch (IOException ex) {
            LOGGER.warning("unable to write on file");
        } finally {
            try {
                writer.close();
            } catch (Exception ex) {
                LOGGER.warning(ex.toString());
            }
        }

    }

    /**
     * will delete a given file from the disk
     *
     * @param filename the filename
     */
    public void deleteFileOrDirectoryOndisk(String filename) {
        try {
            File file = new File(getAbsolutePath() + filename);
            if (file.delete()) {
                LOGGER.info(loggerSeparator + filename + " deleted.");
            } else {
                LOGGER.warning(loggerSeparator+ "Delete operation is failed.");
            }
        } catch (Exception e) {
            LOGGER.warning(loggerSeparator + e.toString());
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
            LOGGER.info(loggerSeparator + "Content successfully sent!");
            return str;
        } catch (FileNotFoundException ex1) {
            LOGGER.warning(loggerSeparator + "File not found");
            LOGGER.warning(ex1.toString());
        } catch (IOException e) {
            LOGGER.warning(loggerSeparator + "IOD Exception");
            LOGGER.warning(e.toString());
        }
        return null;
    }


    /**
     *  va creer un zip au nom du project + .zip
     * @param projectName le nom du project
     * @return le nom de l'archive
     */
    public String zipIt(String projectName){
        LOGGER.info(loggerSeparator + "Attempting to build an archive for " + projectName);
        try {
            File dir = new File(getAbsolutePath());
            if (!dir.isDirectory()) {
                dir.mkdirs();
            }
            ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(new File(dir, projectName + ".zip")));
            zos.close();

            LOGGER.info(loggerSeparator + "Archive successfully created at name " + getAbsolutePath() + projectName + ".zip");
            return projectName + ".zip";
        }catch (FileNotFoundException e1){
            LOGGER.warning(loggerSeparator + e1.toString());
        }
        catch (IOException e2){
            LOGGER.warning(loggerSeparator + e2.toString());
        }
        return "";
    }

    /**
     * permet d'importer un project
     * source https://www.mkyong.com/java/how-to-decompress-files-from-a-zip-file/
     * @param zipFile file to unzip
     */
    public void unZipIt(String zipFile){
        LOGGER.info(loggerSeparator + "Attempting to unzip the file: " + zipFile + "...");
        byte[] buffer = new byte[1024];
        try{
            //create output directory is not exists
            File folder = new File(getAbsolutePath());
            if(!folder.exists()){
                LOGGER.info(loggerSeparator + "creating the appropriate folder...");
                folder.mkdir();
            }
            ZipInputStream zis =
                    new ZipInputStream(new FileInputStream(zipFile));
            //get the zipped file list entry
            ZipEntry ze = zis.getNextEntry();
            while(ze!=null){
                String fileName = ze.getName();
                File newFile = new File(fileName);
                //create all non exists folders
                //else you will hit FileNotFoundException for compressed folder
                new File(newFile.getParent()).mkdirs();
                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fos.close();
                ze = zis.getNextEntry();
            }
            zis.closeEntry();
            zis.close();
            LOGGER.info(loggerSeparator + "file unzipped");
        }catch(IOException ex){
            LOGGER.warning(loggerSeparator + ex.toString());
        }
    }

    public long getTimeLocket() {
        return timeLocket;
    }

    public void setTimeLocket(long timeLocket) {
        this.timeLocket = timeLocket;
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }
}
