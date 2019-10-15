package com.tristan.store;

import javax.swing.*;

public class StoreManager {
    public static final String DBMS_SQ_LITE = "SQLite";
    public static final String DATABASE_ADDRESS = "jdbc:sqlite:D:/school/classes/fall 2019/Software Modeling/sqlite/store.db";

    IDataAdapter adapter = null;
    private static StoreManager instance = null;

    public static StoreManager getInstance() {
        if (instance == null) {

            String dbfile = DATABASE_ADDRESS;
            if (dbfile.length() == 0) {
                JFileChooser fc = new JFileChooser();
                if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
                    dbfile = fc.getSelectedFile().getAbsolutePath();
            }
            instance = new StoreManager(DBMS_SQ_LITE, dbfile);
        }
        return instance;
    }

    private StoreManager(String dbms, String dbfile) {
        if (dbms.equals("Oracle"))
            adapter = new OracleDataAdapter();
        else
        if (dbms.equals("SQLite"))
            adapter = new SQLiteDataAdapter();

        adapter.connect(dbfile);
    }

    public IDataAdapter getDataAdapter() {
        return adapter;
    }

    public void setDataAdapter(IDataAdapter a) {
        adapter = a;
    }


    public void run() {
        MainUI ui = new MainUI();
        ui.view.setVisible(true);
    }

    public static void main(String[] args) {
        StoreManager.getInstance().run();
    }
}
