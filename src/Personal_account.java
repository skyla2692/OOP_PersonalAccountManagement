import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Personal_account{
    static String filepath = "/Users/skylalee/Desktop/LABS/2019LABS/OOP/Team_Project/Personal_account.xlsx";
    static String OG_total_amount;

    public static int get_total_amount(String total_amount, String amount, int a){
        int new_amount = 0;
        int t_amount = Integer.parseInt(total_amount);
        int c_amount = Integer.parseInt(amount);
        if(a == 0){
            new_amount = t_amount + c_amount;
        }
        else if(a == 1){
            new_amount = t_amount - c_amount;
        }
        return new_amount;
    }

    public static ArrayList get_excel_data() throws IOException {
        ArrayList data_list = new ArrayList();
        FileInputStream fis = new FileInputStream(filepath);
        XSSFWorkbook workbook = new XSSFWorkbook(fis);

        int rowindex = 0;
        int columnindex = 0;

        XSSFSheet sheet = workbook.getSheetAt(0);
        int rows = sheet.getPhysicalNumberOfRows();
        for (rowindex = 1; rowindex < rows; rowindex++){
            XSSFRow row = sheet.getRow(rowindex);

            if (row != null){
                int cells = row.getLastCellNum();

                for (columnindex = 0; columnindex <= cells; columnindex++){
                    XSSFCell cell = sheet.getRow(rowindex).getCell((short) columnindex);
                    String value;

                    if (cell == null){
                        continue;
                    }
                    else{
                        if (cell.getCellType() == CellType.FORMULA){
                            value = cell.getCellFormula();
                        }
                        else if (cell.getCellType() == CellType.NUMERIC){
                            value = (int)cell.getNumericCellValue() + "";
                        }
                        else if (cell.getCellType() == CellType.STRING){
                            value = cell.getStringCellValue() + "";
                        }
                        else if (cell.getCellType() == CellType.BOOLEAN){
                            value = cell.getBooleanCellValue() + "";
                        }
                        else if (cell.getCellType() == CellType.BLANK){
                            value = "";
                        }
                        else if (cell.getCellType() == CellType.ERROR){
                            value = cell.getErrorCellValue() + "";
                        }
                        else{
                            value = cell.getStringCellValue();
                        }
                        data_list.add(value);
                    }
                }
            }
        }
        return data_list;
    }

    public static void main(String[] args) throws IOException{
        ArrayList data_list = Personal_account.get_excel_data();
        OG_total_amount = (String) data_list.get((data_list.size()) - 1);
        JLabel title_label, method_label, amount_label, total_label, caution_label;
        JTextField method, amount;
        JPanel title_pane, method_pane, amount_pane, check_pane, caution_pane, null_pane1;
        JButton in_button, out_button, check_button;
        JFrame frame = new JFrame("Check your account");

        frame.setLayout(new GridLayout(7, 1));
        null_pane1 = new JPanel();

        title_pane = new JPanel();
        title_label = new JLabel("<< ACCOUNT BOOK >>");
        title_pane.add(title_label);

        method_pane = new JPanel();
        method = new JTextField(26);
        method_label = new JLabel("Method");
        method_pane.add(method_label);
        method_pane.add(method);

        amount_pane = new JPanel();
        amount = new JTextField(13);
        amount_label = new JLabel("Amount");
        in_button = new JButton("IN");
        in_button.setBackground(Color.RED);
        out_button = new JButton("OUT");
        out_button.setBackground(Color.BLUE);
        amount_pane.add(amount_label);
        amount_pane.add(amount);
        amount_pane.add(in_button);
        amount_pane.add(out_button);

        caution_pane = new JPanel();
        caution_label = new JLabel("PLEASE PUT NUMBERS ONLY in AMOUNT AREA");
        caution_pane.add(caution_label);

        check_pane = new JPanel();
        total_label = new JLabel(OG_total_amount + " WON");
        check_button = new JButton("Check Log");
        check_button.setBackground(Color.GRAY);
        check_pane.add(total_label);
        check_pane.add(check_button);

        frame.add(title_pane);
        frame.add(null_pane1);
        frame.add(method_pane);
        frame.add(amount_pane);
        frame.add(caution_pane);
        frame.add(check_pane);

        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String total_amount = OG_total_amount;
                JButton pressed_button = (JButton) e.getSource();

                if (pressed_button == in_button) {
                    int reply = JOptionPane.showConfirmDialog(null, "Amount will be recorded as 'Income'", "Are you sure?", JOptionPane.YES_NO_OPTION);

                    if (reply == JOptionPane.YES_OPTION) {
                        try {
                            FileInputStream fis = new FileInputStream(filepath);
                            XSSFWorkbook workbook = new XSSFWorkbook(fis);
                            XSSFSheet sheet = workbook.getSheetAt(0);

                            SimpleDateFormat dateformat = new SimpleDateFormat("yy/MM/dd");
                            String date = dateformat.format(new Date());

                            int rowCount = sheet.getLastRowNum() + 1;

                            String n_amount = amount.getText();

                            int new_total_amount = Personal_account.get_total_amount(total_amount, n_amount, 0);
                            total_amount = String.valueOf(new_total_amount);
                            total_label.setText(total_amount + " WON");

                            String new_amount = amount.getText();
                            String new_method = method.getText();

                            Row row = sheet.createRow(rowCount++);

                            int columnCount = 0;

                            Cell cell = null;
                            cell = row.createCell(columnCount++);
                            cell.setCellValue(date);

                            cell = row.createCell(columnCount++);
                            cell.setCellValue(new_amount);

                            cell = row.createCell(columnCount++);
                            cell.setCellValue("0");

                            cell = row.createCell(columnCount++);
                            cell.setCellValue(new_method);

                            cell = row.createCell(columnCount++);
                            cell.setCellValue(Integer.toString(new_total_amount));

                            fis.close();

                            FileOutputStream outputStream = new FileOutputStream(filepath);
                            workbook.write(outputStream);
                            workbook.close();
                            outputStream.close();
                            OG_total_amount = total_amount;

                        }
                        catch (IOException | EncryptedDocumentException ex) {
                            ex.printStackTrace();
                        }
                    }
                }

                else if (pressed_button == out_button) {
                    int reply = JOptionPane.showConfirmDialog(null, "Amount will be recorded as 'Expense'", "Are you sure?", JOptionPane.YES_NO_OPTION);

                    if (reply == JOptionPane.YES_OPTION) {
                        try {
                            FileInputStream fis = new FileInputStream(filepath);
                            XSSFWorkbook workbook = new XSSFWorkbook(fis);
                            XSSFSheet sheet = workbook.getSheetAt(0);

                            SimpleDateFormat dateformat = new SimpleDateFormat("yy/MM/dd");
                            String date = dateformat.format(new Date());

                            int rowCount = sheet.getLastRowNum() + 1;

                            String n_amount = amount.getText();

                            int new_total_amount = Personal_account.get_total_amount(total_amount, n_amount, 1);
                            total_amount = String.valueOf(new_total_amount);
                            total_label.setText(total_amount + " WON");

                            String new_amount = amount.getText();
                            String new_method = method.getText();

                            Row row = sheet.createRow(rowCount++);

                            int columnCount = 0;

                            Cell cell = null;
                            cell = row.createCell(columnCount++);
                            cell.setCellValue(date);

                            cell = row.createCell(columnCount++);
                            cell.setCellValue("0");

                            cell = row.createCell(columnCount++);
                            cell.setCellValue(new_amount);

                            cell = row.createCell(columnCount++);
                            cell.setCellValue(new_method);

                            cell = row.createCell(columnCount++);
                            cell.setCellValue(Integer.toString(new_total_amount));

                            fis.close();

                            FileOutputStream outputStream = new FileOutputStream(filepath);
                            workbook.write(outputStream);
                            workbook.close();
                            outputStream.close();
                            OG_total_amount = total_amount;
                        }
                        catch (IOException | EncryptedDocumentException ex) {
                            ex.printStackTrace();
                        }
                    }
                }

                else if (pressed_button == check_button) {
                    FileInputStream fis = null;
                    ArrayList data_list = null;

                    try {
                        fis = new FileInputStream(filepath);
                        data_list = Personal_account.get_excel_data();
                        XSSFWorkbook workbook = new XSSFWorkbook(fis);
                        XSSFSheet sheet = workbook.getSheetAt(0);

                        int row_num = sheet.getPhysicalNumberOfRows() - 1;
                        int column = 5;

                        Vector header = new Vector<>();
                        header.add("Date");
                        header.add("Income");
                        header.add("Expense");
                        header.add("Method");
                        header.add("Total");

                        Vector content = new Vector<>();
                        for (int i = 1; i <= row_num; i++){
                            Vector cont = new Vector<>();
                            for (int j = 0; j < column; j++){
                                int num = ((i - 1) * 5) + j;
                                String temp = String.valueOf(data_list.get(num));
                                cont.add(temp);
                            }
                            content.add(cont);
                        }

                        DefaultTableModel dtm = new DefaultTableModel(content, header);
                        JTable ex_table = new JTable(dtm);
                        JScrollPane scrollPane = new JScrollPane(ex_table);
                        JFrame ex_frame = new JFrame("Check your log");
                        ex_frame.setSize(600, 400);
                        ex_frame.add(scrollPane);
                        ex_frame.setVisible(true);
                    }
                    catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        };

        in_button.addActionListener(al);
        out_button.addActionListener(al);
        check_button.addActionListener(al);

        frame.setSize(405, 250);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}