package com.finxis.traderxconsole.ui;

import com.finxis.traderxconsole.ServiceTableModel;
import com.finxis.traderxconsole.TraderXConsoleApplication;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class TraderXConsolePanel extends JPanel implements ActionListener {

    private final ServicePanel servicePanel;
    private final ServiceTableModel serviceTableModel;


    public TraderXConsolePanel(ServiceTableModel serviceTableModel,
                                    TraderXConsoleApplication application) {
        setName("Console Panel");
        this.serviceTableModel = serviceTableModel;

        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1;


        constraints.gridx++;
        constraints.weighty = 10;

        JTabbedPane tabbedPane = new JTabbedPane();
        servicePanel = new ServicePanel(serviceTableModel, application);

        tabbedPane.add("Services", servicePanel);
        add(tabbedPane, constraints);
    }

    public TraderXConsolePanel(ServicePanel servicePanel, ServiceTableModel serviceTableModel) {
        this.servicePanel = servicePanel;
        this.serviceTableModel = serviceTableModel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
