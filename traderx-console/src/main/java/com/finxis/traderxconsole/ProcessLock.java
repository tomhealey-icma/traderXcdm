package com.finxis.traderxconsole;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class ProcessLock {

    public void writeProcessIdToFile(String wd, String service, String processId) throws IOException {


        String userDirectory = Paths.get("")
                .toAbsolutePath()
                .toString();
        File udir = new File(userDirectory);
        File pudir = udir.getParentFile();
        File processFile = new File(wd  + "servicelock" + ".txt");
        java.io.FileWriter fr = new java.io.FileWriter(processFile);

        try {
            boolean result = processFile.exists();
            if (result) {
                fr.write(processId);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //close resources
            try {
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println("writing processFile:" + processId);

    }

    public boolean getProcessLockStatus(String wd, String service) throws IOException {


        String userDirectory = Paths.get("")
                .toAbsolutePath()
                .toString();
        File udir = new File(userDirectory);
        File pudir = udir.getParentFile();
        File processFile = new File(wd + "servicelock" + ".txt");
        java.io.FileWriter fr = new java.io.FileWriter(processFile);

        try {
            boolean result = processFile.exists();
            if (result) {
                return true;
            } else {
                    return false;}

        } finally {
            //close resources
            try {
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
