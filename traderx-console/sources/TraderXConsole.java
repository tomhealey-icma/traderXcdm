package com.finxis.traderxconsole;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.concurrent.CountDownLatch;

import javax.swing.JFrame;
import javax.swing.UIManager;

import com.finxis.traderxconsole.ui.ServiceTable;
import com.finxis.traderxconsole.ui.TraderXConsoleFrame;
import org.slf4j.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TraderXConsole {

    private static final Logger log = LoggerFactory.getLogger(TraderXConsole.class);
    private static TraderXConsole traderXConsole;

    public TraderXConsole(String[] args) throws Exception {


        boolean logHeartbeats = Boolean.valueOf(System.getProperty("logHeartbeats", "true"));

        ServiceTableModel serviceTableModel = new ServiceTableModel();
        TraderXConsoleApplication application = application(serviceTableModel);
        
        frame = new TraderXConsoleFrame(serviceTableModel, application);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    protected TraderXConsoleApplication application(ServiceTableModel serviceTableModel) {
        return new TraderXConsoleApplication(serviceTableModel);
    }


    private JFrame frame = null;
    public JFrame getFrame() {
        return frame;
    }

    public static TraderXConsole get() {
        return traderXConsole;
    }
    public static void main(String[] args) throws Exception {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            log.info(e.getMessage(), e);
        }
        traderXConsole = new TraderXConsole(args);


    }
}