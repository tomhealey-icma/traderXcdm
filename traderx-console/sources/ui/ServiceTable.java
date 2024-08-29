package com.finxis.traderxconsole.ui;

import com.finxis.traderxconsole.ServiceTableModel;
import com.finxis.traderxconsole.TraderXConsoleApplication;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ServiceTable extends JTable implements MouseListener {

    private final transient TraderXConsoleApplication application;

    public ServiceTable(ServiceTableModel serviceTableModel, TraderXConsoleApplication application) {
        super(serviceTableModel);
        this.application = application;
    }


    public void mouseClicked(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {}

    public void mouseExited(MouseEvent e) {}

    public void mousePressed(MouseEvent e) {}

    public void mouseReleased(MouseEvent e) {}
}
