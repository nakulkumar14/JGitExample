package com.company;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.revwalk.RevCommit;

import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println("jgit example");
        Git git = null;
        try {
            git = Git.init().setDirectory(new File("/home/nakulkumar/Workspace/JGitExample/")).call();
            System.out.println(git.getRepository());

            // Returns true if repo is just created and no untracked files.
            System.out.println("Status : " + git.status().call().isClean());
            // Get all branches.
            System.out.println("Branches : " + git.branchList().setListMode(ListBranchCommand.ListMode.ALL).call());

            // Initially head pointing to nothing. Now pointing to last commit.
            //System.out.println(git.commit().setMessage("Initial commit").call());

            // Create a new branch
            //System.out.println(git.branchCreate().setName("testBranch").call());
            // Checkout the new branch
            System.out.println("Checkout branch : " + git.checkout().setName("testBranch").call().getName());

            // Add all files.
            System.out.println("Adding all untrakced files...");
            System.out.println(git.add().addFilepattern(".").call());

            // Add remote.
            System.out.println("Adding remote...");
            StoredConfig config = git.getRepository().getConfig();
            config.setString("remote", "origin", "url", "https://github.com/nakulkumar14/JGitExample");
            config.save();

            RevCommit commit = git.commit().setMessage("Push example files").call();
            System.out.println("Commit : " + commit.getName() + ", commit id  : " + commit.getId());
            System.out.println("Status : " + git.status().call().isClean());

//            File folder = new File("/home/nakulkumar/Workspace/JGitExample/");
//            File[] listOfFiles = folder.listFiles();
//
//            for (int i = 0; i < listOfFiles.length; i++) {
//                if (listOfFiles[i].isFile()) {
//                    System.out.println("File " + listOfFiles[i].getName());
//                } else if (listOfFiles[i].isDirectory()) {
//                    System.out.println("Directory " + listOfFiles[i].getName());
//                }
//            }
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
    }
}
