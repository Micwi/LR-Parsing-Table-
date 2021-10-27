import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

//@author Micwi
public class parsingTableClass {
	public static JTable table;
	public static JFrame frame;
	
	/**
	 * @wbp.parser.entryPoint
	 */
	public void start() {
		createMainFrame();
		createTable();
	}
	public static void createMainFrame() {
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 489); //dimensions of table
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setBackground(new Color(0, 0, 0, 0));
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);

	}
	public void createTable() {
		try {
			table = new JTable() {
				private static final long serialVersionUID = 1L;

				public boolean isCellEditable(int rowIndex, int colIndex) {
					return false;
				}
			};

			Object[] column_headers = { "State", "id", "+", "*", "(", ")", "$", "E", "T", "F"}; // Column names
			DefaultTableModel tableModel = new DefaultTableModel();
			tableModel.setColumnIdentifiers(column_headers);
			table.setModel(tableModel);
			table.doLayout();
			
			table.setGridColor(Color.black);
			table.setFont(new Font("Tahoma", Font.PLAIN, 17));
			table.setRowHeight(30);
			table.setAutoCreateRowSorter(true);
			table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
			//sets the number of rows for Parsing table
			tableModel.setRowCount(12);
			//numbers the "state" column from 0-11 just like picture
			int count = 0;
			for(int i = 0; i < table.getRowCount(); i++) {
				table.setValueAt(count, i, 0);
				count++;
			}

			JScrollPane gPane = new JScrollPane(table);
			gPane.setBounds(25, 32, 745, 387);
			
			frame.getContentPane().add(gPane);
			
			settingTableValues(); //calling method below
			
			
			
		} catch (Exception e) {
			System.out.println(e);
		}

	}
	//setting the values of the table just like picture 
	public void settingTableValues() {
						//value | row | column
		table.setValueAt("S5", 0, 1);
		table.setValueAt("S4", 0, 4);
		table.setValueAt("1", 0, 7);
		table.setValueAt("2", 0, 8);
		table.setValueAt("3", 0, 9);
		table.setValueAt("S6", 1, 2);
		table.setValueAt("accept", 1, 6);
		table.setValueAt("R2", 2, 2);
		table.setValueAt("S7", 2, 3);
		table.setValueAt("R2", 2, 5);
		table.setValueAt("R2", 2, 6);
		table.setValueAt("R4", 3, 2);
		table.setValueAt("R4", 3, 3);
		table.setValueAt("R4", 3, 5);
		table.setValueAt("R4", 3, 6);
		table.setValueAt("S5", 4, 1);
		table.setValueAt("S4", 4, 4);
		table.setValueAt("8", 4, 7);
		table.setValueAt("2", 4, 8);
		table.setValueAt("3", 4, 9);
		table.setValueAt("R6", 5, 2);
		table.setValueAt("R6", 5, 3);
		table.setValueAt("R6", 5, 5);
		table.setValueAt("R6", 5, 6);
		table.setValueAt("S5", 6, 1);
		table.setValueAt("S4", 6, 4);
		table.setValueAt("9", 6, 8);
		table.setValueAt("3", 6, 9);
		table.setValueAt("S5", 7, 1);
		table.setValueAt("S4", 7, 4);
		table.setValueAt("10", 7, 9);
		table.setValueAt("S6", 8, 2);
		table.setValueAt("S11", 8, 5);
		table.setValueAt("R1", 9, 2);
		table.setValueAt("S7", 9, 3);
		table.setValueAt("R1", 9, 5);
		table.setValueAt("R1", 9, 6);
		table.setValueAt("R3", 10, 2);
		table.setValueAt("R3", 10, 3);
		table.setValueAt("R3", 10, 5);
		table.setValueAt("R3", 10, 6);
		table.setValueAt("R5", 11, 2);
		table.setValueAt("R5", 11, 3);
		table.setValueAt("R5", 11, 5);
		table.setValueAt("R5", 11, 6);
		
		
	}
	
}
