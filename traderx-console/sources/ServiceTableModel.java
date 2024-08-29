package com.finxis.traderxconsole;


import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.security.Provider;

public class ServiceTableModel extends AbstractTableModel {

    public ServiceTableModel() {
        String[] columnNames = { "Service", "Status", "Start/Stop" };

        JButton service1 = new JButton();



        }


    @Override
    public int getRowCount() {
        return 0;
    }

    @Override
    public int getColumnCount() {
        return 0;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return null;
    }
}
