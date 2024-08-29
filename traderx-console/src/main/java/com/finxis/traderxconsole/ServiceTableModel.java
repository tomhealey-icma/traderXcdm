package com.finxis.traderxconsole;


import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.security.Provider;
import java.util.HashMap;

public class ServiceTableModel extends AbstractTableModel {

    private final static int ID=0;
    private final static int SERVICE=1;
    private final static int STATUS=2;
    private final static int START_STOP=3;
    private final static int LOG=4;

    private final HashMap<Integer, Service> rowToService;
    private final HashMap<String, Integer> idToRow;
    private final HashMap<String, Service> idToService;
    private final String[] headers;


    public ServiceTableModel() {
        rowToService = new HashMap<>();
        idToRow = new HashMap<>();
        idToService = new HashMap<>();

        headers = new String[] { "ID", "Service", "Status", "Start/Stop", "LOG" };

        }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public void addService(Service service) {
        int row = rowToService.size();

        rowToService.put(row, service);
        idToRow.put(service.getID(), row);
        idToService.put(service.getID(), service);

        fireTableRowsInserted(row, row);
    }

    public void updateService(Service service) {


        replaceService(service);
        Integer row = idToRow.get(service.getID());
        if (row == null)
            return;
        fireTableRowsUpdated(row, row);
    }

    public void replaceService(Service service) {

        Integer row = idToRow.get(service.getID());
        if (row == null)
            return;

        rowToService.put(row, service);
        idToRow.put(service.getID(), row);
        idToService.put(service.getID(), service);

        fireTableRowsUpdated(row, row);
    }

    public void setValueAt(Object value, int rowIndex, int columnIndex) { }

    public Class<String> getColumnClass(int columnIndex) {
        return String.class;
    }

    public int getRowCount() {
        return rowToService.size();
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        Service service = rowToService.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return service.getID();
            case 1:
                return service.getService();
            case 2:
                return service.getStatus();
            case 3:
                return service.getStart_stop();
            case 4:
                return service.getLog();
        }
        return "";
    }

    public Service getService(String id) {
        return idToService.get(id);
    }

    public Service getService(int row) {
        return rowToService.get(row);
    }


    public int getColumnCount() {
        return headers.length;
    }

    public String getColumnName(int columnIndex) {
        return headers[columnIndex];
    }
}
