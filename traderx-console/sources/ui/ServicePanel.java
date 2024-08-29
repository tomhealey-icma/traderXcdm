package com.finxis.traderxconsole.ui;


import com.finxis.traderxconsole.ServiceTableModel;
import com.finxis.traderxconsole.TraderXConsoleApplication;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.*;

public class ServicePanel extends JPanel{

    private JTable serviceTable = null;

    public ServicePanel(ServiceTableModel serviceTableModel, TraderXConsoleApplication application) {
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1;
        constraints.weighty = 1;

        serviceTable = new ServiceTable(serviceTableModel, application);
        JButton service1 = new JButton();
        service1.setName("Run Service");
        serviceTable.add(service1);
        serviceTable.setVisible(true);
        add(new JScrollPane(serviceTable), constraints);
        setVisible(true);
    }

    public JTable serviceTable() {

        return serviceTable;
    }
}
