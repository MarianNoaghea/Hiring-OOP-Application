package Interfete;
import Company.*;
import User.User;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

public class ManagerPage extends JLabel implements ActionListener {

    Company company;
    JList<CheckboxRequest> list;

    public ManagerPage(Company company) {

        this.company = company;

        JFrame frame = new JFrame();
        CheckboxRequest[] checkboxRequests = new CheckboxRequest[company.manager.getRequests().size()];
        JScrollPane checkBoxList;
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        //crearea listei de checkboxRequesturi
        int i = 0;
        for(Request request : company.manager.getRequests()) {
            checkboxRequests[i] = new CheckboxRequest(request);

            i++;
        }

        list = new JList<>(checkboxRequests);

        list.setCellRenderer(new CheckboxListRenderer());
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //actiune pentru selectia checkboxurilor
        list.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                JList<CheckboxRequest> list =
                        (JList<CheckboxRequest>) event.getSource();

                int index = list.locationToIndex(event.getPoint());

                CheckboxRequest item = (CheckboxRequest) list.getModel()
                        .getElementAt(index);

                item.isSelected = !(item.isSelected);

                list.repaint(list.getCellBounds(index, index));

            }
        });

        JPanel table = new JPanel(new GridLayout(1,3));

        JButton jButton1 = new JButton("AcceptRequest");
        JButton jButton2 = new JButton("DeleteRequest");
        JTextArea number = new JTextArea("0");

        jButton1.addActionListener(this);
        jButton2.addActionListener(this);

        JPanel avaibleNumber = new JPanel();
        avaibleNumber.add(number);

        table.add(jButton1);
        table.add(jButton2);

        table.add(jButton1);
        table.add(jButton2);
        table.add(avaibleNumber);

        JSplitPane splitPane = new JSplitPane((JSplitPane.HORIZONTAL_SPLIT));


        checkBoxList = new JScrollPane(list);
        JScrollPane tableOfContents = new JScrollPane(table);


        splitPane.setLeftComponent(checkBoxList);
        splitPane.setRightComponent(tableOfContents);

        Dimension minimumSize = new Dimension(200, 100);
        checkBoxList.setMinimumSize(minimumSize);
        tableOfContents.setMinimumSize(minimumSize);
        splitPane.setDividerLocation(1200);
        splitPane.setPreferredSize(new Dimension(2100, 600));

        frame.getContentPane().add(splitPane);
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // actiuni pentru butoane
        ListModel curentModel = list.getModel();
        DefaultListModel newModel = new DefaultListModel();
        if(e.getActionCommand().equals("DeleteRequest")) {
            for(int i = 0; i < company.manager.getRequests().size(); i++) {
                if(!((CheckboxRequest) list.getModel().getElementAt(i)).isSelected) {
                    newModel.addElement(((CheckboxRequest) list.getModel().getElementAt(i)));
                }
                else {
                    company.manager.deleteRequest(i);
                }

            }
            list.setModel(newModel);
        }

        if(e.getActionCommand().equals("AcceptRequest")) {
            for(int i = 0; i < company.manager.getRequests().size(); i++) {
                if(!((CheckboxRequest) list.getModel().getElementAt(i)).isSelected)
                    newModel.addElement(((CheckboxRequest) list.getModel().getElementAt(i)));
                else {
                    Request requestAcceptat = company.manager.getRequest(i);
                    company.manager.deleteRequest(i);
                    Employee newEmployee = ((User)requestAcceptat.getValue1()).convert();
                }

            }
            list.setModel(newModel);
        }
    }
    // clasa creata de mine pentru obtinerea obiectelor de tipul : (checkbox):(information)
    class CheckboxRequest {

        Request request;
        public JTextArea jTextArea = new JTextArea();
        boolean isSelected = false;

        public CheckboxRequest(Request request) {
            this.request = request;
            jTextArea.setText("User: " + ((User)request.getValue1()).getName() + " (" + ((User)request.getValue1()).cod
                    + ") " +  "a facut o cerere pentru " + ((Job)request.getKey()).numeJob + "\n. Cerere procesata de " +
                    ((Recruiter)request.getValue2()).getName() + " (" + ((Recruiter)request.getValue2()).cod + ") ." +
                    "In urma evaluarii el are scor: " + ((Double)request.getScore()));
        }

        //pentru afisarea interfetei grafice
        @Override
        public String toString() {
            return jTextArea.getText();
        }
    }

    //clasa recomandata de pe net
    class CheckboxListRenderer extends JCheckBox implements
            ListCellRenderer<CheckboxRequest> {

        @Override
        public Component getListCellRendererComponent(
                JList<? extends CheckboxRequest> list, CheckboxRequest value,
                int index, boolean isSelected, boolean cellHasFocus) {
            setEnabled(list.isEnabled());
            setSelected(value.isSelected);
            setFont(list.getFont());
            setBackground(list.getBackground());
            setForeground(list.getForeground());
            setText(value.toString());
            return this;
        }
    }
}