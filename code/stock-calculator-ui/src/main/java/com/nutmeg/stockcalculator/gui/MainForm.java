package com.nutmeg.stockcalculator.gui;

import com.nutmeg.stockcalculator.HoldingCalculator;
import com.nutmeg.stockcalculator.NumtegHoldingCalculator;
import com.nutmeg.stockcalculator.gui.component.DateLabelFormatter;
import com.nutmeg.stockcalculator.gui.component.StatementFormatter;
import com.nutmeg.stockcalculator.infrastructure.parser.CsvFileParser;
import lombok.val;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.GregorianCalendar;
import java.util.Properties;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import static javax.swing.filechooser.FileSystemView.getFileSystemView;

public class MainForm {

    private JPanel placeholder;
    private JButton calculateButton;
    private JTextArea textArea;
    private JButton openButton;
    private JFileChooser fileChooser;
    private JDatePickerImpl datePicker;

    private File selectedFile;
    private LocalDate selectedDate;
    private final HoldingCalculator calculator;

    public MainForm() {
        calculator = new NumtegHoldingCalculator(new CsvFileParser());

        openButton.addActionListener(e -> {
            int dialogResult = fileChooser.showOpenDialog(placeholder);
            if (dialogResult == JFileChooser.APPROVE_OPTION) {
                selectedFile = fileChooser.getSelectedFile();
                textArea.append(String.format("File %s selected...\n", selectedFile.getName()));
                onInputChange();
            }
        });

        datePicker.addActionListener(e -> {
            GregorianCalendar calendar = (GregorianCalendar) datePicker.getJFormattedTextField().getValue();
            selectedDate = LocalDateTime.ofInstant(calendar.getTime().toInstant(), calendar.getTimeZone().toZoneId()).toLocalDate();
            textArea.append(String.format("Date %s selected...\n", selectedDate));
            onInputChange();
        });

        calculateButton.addActionListener(e -> {
            val statement = calculator.calculateHoldings(selectedFile, selectedDate);
            val output = StatementFormatter.format(statement, selectedDate);
            textArea.append(String.format("\n%s\n", output));
        });
    }

    private void onInputChange() {
        calculateButton.setEnabled(selectedFile != null && selectedDate != null);
    }

    private void createUIComponents() {
        UtilDateModel model = new UtilDateModel();
        Properties properties = new Properties();
        properties.put("text.today", "Today");
        properties.put("text.month", "Month");
        properties.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, properties);
        datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());

        fileChooser = new JFileChooser(getFileSystemView().getHomeDirectory());
        FileNameExtensionFilter csvFilesFilter = new FileNameExtensionFilter("CSV FILES", "csv", "csv");
        fileChooser.setFileFilter(csvFilesFilter);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Stock Calculator");
        frame.setContentPane(new MainForm().placeholder);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(550, 500));
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }
}
