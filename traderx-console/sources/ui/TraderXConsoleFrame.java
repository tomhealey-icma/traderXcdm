package com.finxis.traderxconsole.ui;

import com.finxis.traderxconsole.ServiceTableModel;
import com.finxis.traderxconsole.TraderXConsoleApplication;

import java.awt.BorderLayout;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;


public class TraderXConsoleFrame extends JFrame {

    public TraderXConsoleFrame(ServiceTableModel serviceTableModel, final TraderXConsoleApplication application) {
        super();
        setTitle("TraderXConsole");
        setSize(600, 400);

        if (System.getProperties().containsKey("openfix")) {
            //createMenuBar(application);
        }
        getContentPane().add(new TraderXConsolePanel(serviceTableModel, application),
                BorderLayout.CENTER);
        setVisible(true);
    }
}
