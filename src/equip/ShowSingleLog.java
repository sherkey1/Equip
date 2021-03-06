/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package equip;

import com.mysql.jdbc.ResultSet;
import java.io.File;
import javax.swing.RowSorter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.sql.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

/**
 *
 * @author 3
 */
public class ShowSingleLog extends javax.swing.JFrame implements TableModelListener{
    String sql;
    ResultSet rs;
    ResultSetMetaData rsm;
    DefaultTableModel model;
    String[] columnNames={"序列号","设备编号","维修时间","维修人员","联系方式","维修日志"};
    Object[][] data =null;
    String id;
    

    /**
     * Creates new form ShowSingleLog
     */
    public ShowSingleLog(String id) {
        this.id=id;
        sql="select * from maintain where product='"+id+"';";
        initComponents();
        showData();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        dataTable = new javax.swing.JTable();
        B_export = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jScrollPane1.setViewportView(dataTable);

        B_export.setText("导出");
        B_export.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B_exportActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 494, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(175, 175, 175)
                .addComponent(B_export)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(B_export)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void B_exportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B_exportActionPerformed
       deleteFile();
       exportToExcel();
    }//GEN-LAST:event_B_exportActionPerformed

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /*
//         * Set the Nimbus look and feel
//         */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /*
//         * If Nimbus (introduced in Java SE 6) is not available, stay with the
//         * default look and feel. For details see
//         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(ShowSingleLog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(ShowSingleLog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(ShowSingleLog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(ShowSingleLog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /*
//         * Create and display the form
//         */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//
//            public void run() {
//                new ShowSingleLog().setVisible(true);
//            }
//        });
//    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton B_export;
    private javax.swing.JTable dataTable;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

    private void showData() {
        try {
            Connector c = new Connector();
            rs = (ResultSet) c.sm_updatable.executeQuery(sql);
            rsm = rs.getMetaData();

            int row = 0;
            int column = 0;
            int columnCount = rsm.getColumnCount();
            rs.last();
            int rooCount = rs.getRow();
            rs.beforeFirst();
            data = new Object[rooCount][columnCount];
            while (rs.next()) {
                for (column = 0; column < columnCount; column++) {
                    data[row][column] = rs.getObject(column + 1);
                }
                row++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        model = new DefaultTableModel(data, columnNames) {

            public Class getColumnClass(int c) {
                if (dataVector.isEmpty() == false && getValueAt(0, c) != null) {
                    return getValueAt(0, c).getClass();
                } else {
                    return Object.class;
                }
            }
        };
        model.addTableModelListener(this);
        dataTable.setModel(model);
        //实现表格的排序
        RowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(model);
        dataTable.setRowSorter(sorter);
    }

    @Override
    public void tableChanged(TableModelEvent e) {
         //-1表示空行
        if (e.getColumn() != -1) {
            Object o = null;
            o = dataTable.getValueAt(e.getFirstRow(), 0);
            if (o != null) {
                //转换到模型行
                int select = e.getFirstRow();
                if (select == -1) {
                    return;
                }
                select = dataTable.getRowSorter().convertRowIndexToModel(select);

                try {
                    rs.absolute(select + 1);
                    //更新数据集中某个位置的内容
                    rs.updateObject(e.getColumn() + 1, dataTable.getValueAt(e.getFirstRow(), e.getColumn()));
                    //更新数据库
                    rs.updateRow();

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private void deleteFile() {
          String path="单项维修日志.xls";
        File file=new File(path);
        if(file.isFile()&&file.exists())
        {
            file.delete();
        }
    }

    private void exportToExcel() {
          try
        {
//            ExcelExporter exp=new ExcelExporter();
//            exp.exportTable(dataTable, new File("results.xls"));
            String heading="型号"+id+":"+"维修日志";
            String inscribe=null;
              JTableToExcel je=new JTableToExcel();
              je.export(new File("单项维修日志.xls"), heading, inscribe, dataTable);
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }
    }

