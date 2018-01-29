package bean;

/**
 * Created by Abdelilah Chet on 24/01/18.
 */
import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.CreateBranchCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Repository;

import constants.CONSTANTS;


public class Jgit {

    private String localPath;
    private String remotePath;
    private Repository localRepo;
    private Git git;

    public void init(String user, String projectName) throws IOException {
        localPath = (CONSTANTS.getInstance().getAbsolutePath() + user + "/" + projectName);
        remotePath = "git@github.com:anthony/testicule.git";
        localRepo = new FileRepository(getLocalPath() + "/.git");
        git = new Git(localRepo);
    }

    public String create() throws IOException {
        Repository newRepo = new FileRepository(localPath + "/.git");
        try {
            newRepo.create();
            return "Success";
        } catch (Exception e){
            return "Error";
        }
    }


    public void testClone() throws IOException, GitAPIException {
        Git.cloneRepository().setURI(remotePath)
                .setDirectory(new File(getLocalPath())).call();
    }

    public void testAdd() throws IOException, GitAPIException {
        File myfile = new File(getLocalPath() + "/myfile");
        myfile.createNewFile();
        git.add().addFilepattern("myfile").call();
    }

    public void testCommit() throws IOException, GitAPIException,
            JGitInternalException {
        git.commit().setMessage("Added myfile").call();
    }

    public void testPush() throws IOException, JGitInternalException,
            GitAPIException {
        git.push().call();
    }

    public void testTrackMaster() throws IOException, JGitInternalException,
            GitAPIException {
        git.branchCreate().setName("master")
                .setUpstreamMode(CreateBranchCommand.SetupUpstreamMode.SET_UPSTREAM)
                .setStartPoint("origin/master").setForce(true).call();
    }

    public void testPull() throws IOException, GitAPIException {
        git.pull().call();
    }

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }
}