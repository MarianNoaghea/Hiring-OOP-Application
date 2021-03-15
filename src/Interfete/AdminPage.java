package Interfete;

import Aplication.Aplication;
import Company.*;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import java.awt.*;


public class AdminPage extends JPanel implements TreeSelectionListener {

    private JTree tree;

    JTextArea textArea1 = new JTextArea();
    JTextArea textArea2 = new JTextArea();

    public AdminPage() {

        super(new GridLayout(1,0));


        Aplication aplication = Aplication.getInstance();

        Object obj = null;
        //creez nodurile si frunzele
        DefaultMutableTreeNode top =
                new DefaultMutableTreeNode("Companies");

        for(Company company : aplication.companies) {
            DefaultMutableTreeNode companyNode = new DefaultMutableTreeNode(company);
            top.add(companyNode);

            for(Departament departament : company.departaments) {
                DefaultMutableTreeNode departamentNode = new DefaultMutableTreeNode(departament);
                companyNode.add(departamentNode);

                for(Employee employee : departament.getEmployees()) {
                    DefaultMutableTreeNode employeeNode = new DefaultMutableTreeNode(employee);
                    departamentNode.add(employeeNode);
                }
            }
        }

        tree = new JTree(top);
        tree.getSelectionModel().setSelectionMode
                (TreeSelectionModel.SINGLE_TREE_SELECTION);

        tree.addTreeSelectionListener(this);


        JScrollPane treeView = new JScrollPane(tree);

        textArea1.setText("");
        textArea2.setText("");


        //splituiesc labelurile
        JPanel informations = new JPanel(new GridLayout(2,1));
        JLabel label1 = new JLabel("Taxes Applied");
        JLabel label2 = new JLabel("Total Salary");

        JPanel textPanel1 = new JPanel(new GridLayout(2, 1));
        JPanel textPanel2 = new JPanel(new GridLayout(2, 1));
        textPanel1.add(label1);
        textPanel1.add(textArea1);
        textPanel2.add(label2);
        textPanel2.add(textArea2);
        informations.add(textPanel1);
        informations.add(textPanel2);


        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setLeftComponent(treeView);
        splitPane.setRightComponent(informations);

        Dimension minimumSize = new Dimension(200, 100);
        informations.setMinimumSize(minimumSize);
        treeView.setMinimumSize(minimumSize);
        splitPane.setDividerLocation(600);
        splitPane.setPreferredSize(new Dimension(1500, 600));

        add(splitPane);
    }

    //action listener pentru selectarea nodurilor din ierarhie
    @Override
    public void valueChanged(TreeSelectionEvent e) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                tree.getLastSelectedPathComponent();

        if (node == null) return;

        Object nodeInfo = node.getUserObject();

        if(nodeInfo instanceof Departament) {
            this.textArea2.setText("" + ((Departament) nodeInfo).getTotalSalaryBudget());
            this.textArea1.setText("" + ((Departament)nodeInfo).getTaxRule());
        }
    }
}

