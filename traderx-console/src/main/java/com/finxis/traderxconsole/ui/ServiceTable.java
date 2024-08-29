package com.finxis.traderxconsole.ui;

import com.finxis.traderxconsole.*;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.security.Provider;

public class ServiceTable extends JTable implements MouseListener {

    private final transient TraderXConsoleApplication application;

    public ServiceTable(ServiceTableModel serviceTableModel, TraderXConsoleApplication application) {
        super(serviceTableModel);
        this.application = application;
        addMouseListener(this);
    }

    public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
        Service service = ((ServiceTableModel) dataModel).getService(row);
        DefaultTableCellRenderer r = (DefaultTableCellRenderer) renderer;
        r.setBackground(Color.white);
        r.setForeground(Color.black);
        return super.prepareRenderer(renderer, row, column);

    }

    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() != 2)
            return;
        int row = rowAtPoint(e.getPoint());
        int col = columnAtPoint(e.getPoint());
        if(col == 3) {
            JOptionPane.showMessageDialog(this, "Start", "Alert", JOptionPane.INFORMATION_MESSAGE);
            PowerShellCommand psc = new PowerShellCommand();
            JAppMonitor jAppMonitor = new JAppMonitor();

            ServiceTableModel stm = ((ServiceTableModel) dataModel);

            Service service = ((ServiceTableModel) dataModel).getService(row);
            String serviceName = service.getService();

            try {
                //jAppMonitor.runJavaApp(serviceName);
                psc.runPowerShell(serviceName);
                if (service.getProcessLock(serviceName)){
                    service.setStatus("Running");
                    service.setStart_stop("Stop");
                    stm.updateService(service);
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public void mouseEntered(MouseEvent e) {}

    public void mouseExited(MouseEvent e) {}

    public void mousePressed(MouseEvent e) {}

    public void mouseReleased(MouseEvent e) {}
}
