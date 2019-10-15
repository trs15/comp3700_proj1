package com.tristan.store;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class AddCustomerUI {
    public JFrame frame;
    
    public JButton btnAdd = new JButton("Add");
    public JButton btnCancel = new JButton("Cancel");

    public JTextField txtCustomerID = new JTextField(20);
    public JTextField txtName = new JTextField(20);
    public JTextField txtAddress = new JTextField(20);
    public JTextField txtPhone = new JTextField(20);
    public JTextField txtPaymentInfo = new JTextField(20);


    public AddCustomerUI() {
        frame = new JFrame();
        
        frame.setTitle("Add Customer");
        frame.setSize(600, 400);
        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));

        String[] labels = {"CustomerID ", "Name ", "Address ", "Phone ", "Payment Info"};

        JPanel line1 = new JPanel(new FlowLayout());
        line1.add(new JLabel("CustomerID "));
        line1.add(txtCustomerID);
        frame.getContentPane().add(line1);

        JPanel line2 = new JPanel(new FlowLayout());
        line2.add(new JLabel("Name "));
        line2.add(txtName);
        frame.getContentPane().add(line2);

        JPanel line3 = new JPanel(new FlowLayout());
        line3.add(new JLabel("Address "));
        line3.add(txtAddress);
        frame.getContentPane().add(line3);

        JPanel line4 = new JPanel(new FlowLayout());
        line4.add(new JLabel("Phone "));
        line4.add(txtPhone);
        frame.getContentPane().add(line4);

        JPanel line5 = new JPanel(new FlowLayout());
        line5.add(new JLabel("Payment Info "));
        line5.add(txtPaymentInfo);
        frame.getContentPane().add(line5);

        JPanel panelButtons = new JPanel(new FlowLayout());
        panelButtons.add(btnAdd);
        panelButtons.add(btnCancel);
        frame.getContentPane().add(panelButtons);

        btnAdd.addActionListener(new AddButtonListener());
        btnCancel.addActionListener(new CancelButtonListener());
    }

    public void run() {frame.setVisible(true);}

    class AddButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            CustomerModel customer = new CustomerModel();

            String id = txtCustomerID.getText();

            if (id.length() == 0) {
                JOptionPane.showMessageDialog(null, "CustomerID cannot be null!");
                return;
            }

            try {
                customer.mCustomerID = Integer.parseInt(id);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "CustomerID is invalid!");
                return;
            }

            String name = txtName.getText();
            if (name.length() == 0) {
                JOptionPane.showMessageDialog(null, "Customer name cannot be empty!");
                return;
            }

            customer.mName = name;

            String address = txtAddress.getText();
            if (address.length() == 0){
                JOptionPane.showMessageDialog(null, "Customer address cannot be empty!");
                return;
            }

            customer.mAddress = address;

            String phone = txtPhone.getText();
            if (phone.length() == 0){
                JOptionPane.showMessageDialog(null, "Phone number cannot be empty!");
                return;
            }

            customer.mPhone = phone;

            String payment = txtPaymentInfo.getText();
            if (payment.length() == 0){
                JOptionPane.showMessageDialog(null, "Payment info cannot be empty!");
                return;
            }

            customer.mPaymentInfo = payment;



            switch (StoreManager.getInstance().getDataAdapter().saveCustomer(customer)) {
                case SQLiteDataAdapter.CUSTOMER_DUPLICATE_ERROR:
                    JOptionPane.showMessageDialog(null, "Customer NOT added successfully! Duplicate customer ID!");
                default:
                    JOptionPane.showMessageDialog(null, "Customer added successfully!" + customer);
            }
        }
    }

    class CancelButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        }
    }
}
