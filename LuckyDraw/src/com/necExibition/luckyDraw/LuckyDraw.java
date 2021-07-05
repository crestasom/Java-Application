package com.necExibition.luckyDraw;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class LuckyDraw extends Component implements ActionListener, ItemListener {
	private static final long serialVersionUID = 1L;
	private JFrame mainFrame;
	JMenu menu;
	JMenuBar bar;
	JMenuItem chooseFolder;
	int flag = 0, timerFlag = 0;
	boolean firstTime = true;
	// File plagFile;
	String fileName, visitedFileName, resultFileName;
	int width;
	int randomNum;
	String rootDir = System.getProperty("user.dir") + "/datalist";
	Scanner sc;
	private JComboBox<String> selectCategory;
	private JLabel headerLabel, label;
	private final String pre = "<html><font color='", post = "</font></html>";
	private String displayText;
	private String[] fileList = new String[20];
	String selectedName;
	private List<String> nameList;
	private List<String> visitedNameList;
	private int nameListLength, fileListLength;

	private JButton start, stop;
	private Timer timer;
	GridBagConstraints gbcx;
	StringBuffer sb = new StringBuffer();

	public LuckyDraw() {
		nameList = new ArrayList<>();

		readFileList();

		prepareGUI();

		prepareMenu();

	}

	public void prepareMenu() {
		bar = new JMenuBar();
		menu = new JMenu("File");
		bar.add(menu);
		chooseFolder = new JMenuItem("Choose Directory");
		menu.add(chooseFolder);
		chooseFolder.addActionListener(this);
		chooseFolder.setActionCommand("choose");
		mainFrame.setJMenuBar(bar);
	}

	private void readFileList() {
		File file = new File(rootDir);
		int x = 0;
		fileList = new String[200];
		// //System.out.println(rootDir);
		for (String s : file.list()) {
			if (s.endsWith("csv")) {
				fileList[x++] = s.substring(0, s.lastIndexOf('.'));
			}
		}
		fileListLength = x;

	}

	public static void main(String[] args) {
		new LuckyDraw();
	}

	public void appendItemsToComboBox() {
		// //System.out.println(fileListLength);
		selectCategory.removeAllItems();
		for (int m = 0; m < fileListLength; m++) {
			selectCategory.addItem(fileList[m]);
		}
	}

	private void prepareGUI() {
		gbcx = new GridBagConstraints();
		mainFrame = new JFrame("OOP 1st Assignment Viva");
		mainFrame.setSize(900, 900);
		mainFrame.setLayout(new GridBagLayout());

		headerLabel = new JLabel(pre + "blue'>Next Question goes to" + post, JLabel.CENTER);
		headerLabel.setFont(new Font(headerLabel.getFont().getName(), headerLabel.getFont().getStyle(), 100));
		displayText = pre + "blue'>Lucky Draw" + post;
		gbcx.fill = GridBagConstraints.HORIZONTAL;
		gbcx.weightx = 1.0;
		gbcx.gridx = 0;
		gbcx.gridy = 0;
		gbcx.anchor = GridBagConstraints.CENTER;
		mainFrame.add(headerLabel, gbcx);

		selectCategory = new JComboBox<>();
		selectCategory.setFont(new Font("TimesRoman", 0, 25));
		selectCategory.addItemListener(this);
		appendItemsToComboBox();
		gbcx.gridx = 1;
		gbcx.fill = GridBagConstraints.NONE;
		gbcx.anchor = GridBagConstraints.LINE_END;
		gbcx.weightx = 0.1;
		mainFrame.add(selectCategory, gbcx);

		start = new JButton("Start");
		start.addActionListener(this);
		start.setMnemonic(KeyEvent.VK_1);
		start.setActionCommand("start");
		start.setFont(new Font("", 0, 50));
		gbcx.gridy = 1;
		gbcx.gridx = 0;
		gbcx.fill = GridBagConstraints.HORIZONTAL;
		gbcx.weightx = 1.0;
		mainFrame.add(start, gbcx);

		stop = new JButton("Stop");
		stop.addActionListener(this);
		stop.setMnemonic(KeyEvent.VK_2);
		stop.setActionCommand("start");
		stop.setFont(new Font("", 0, 50));
		gbcx.gridx = 1;
		gbcx.weightx = 2.5;
		mainFrame.add(stop, gbcx);

		displayText = pre + "white'>Good Luck!!" + post;
		displayText = pre + "Narendra Lama Tamang" + post;
		JPanel panel = new JPanel();
		label = new JLabel(displayText, SwingConstants.CENTER);
		label.setVerticalAlignment(SwingConstants.TOP);
		label.setFont(new Font(label.getFont().getName(), label.getFont().getStyle(), 100));
		label.setForeground(Color.WHITE);
		panel.setBackground(Color.BLACK);
		label.setText("Good Luck!!");
		label.setHorizontalTextPosition(JLabel.CENTER);
		panel.add(label);
		gbcx.gridx = 0;
		gbcx.gridy = 2;
		gbcx.gridwidth = 2;
		mainFrame.add(panel, gbcx);
		mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		mainFrame.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowClosing(WindowEvent e) {
				//System.out.println("Closing");
				writeToFile();
				System.exit(0);
			}

			@Override
			public void windowClosed(WindowEvent e) {

			}

			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub

			}
		});
		mainFrame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == start && timerFlag == 0) {
			timerFlag = 1;
			timer = new Timer();
			TimerTask task = new TimerTask() {
				@Override
				public void run() {
					Random rand = new Random();
					randomNum = rand.nextInt(((nameListLength - 1) - 0) + 1) + 0;
					selectedName = nameList.get(randomNum);
					displayText = pre + "white'>" + selectedName + post;
					label.setText(displayText);
				}
			};

			timer.schedule(task, java.util.Calendar.getInstance().getTime(), 100);
			timerFlag = 1;
		}

		if (e.getSource() == stop) {
			if (timerFlag == 1) {
				timer.cancel();// cancel the tasks scheduled
				// add name into visited list
				visitedNameList.add(nameList.get(randomNum));
				// remove the name from current name list
				nameList.remove(randomNum);
				nameListLength--;
				timerFlag = 0;
				String[] reviews = { "Excellent", "Good", "Average", "Poor", "Absent", "Cancel" };
				int confimNum = JOptionPane.showOptionDialog(null, "Review of student's viva?", "Viva Review",
						JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, reviews, reviews[0]);
				//System.out.println(reviews[confimNum]);
				if (confimNum != 5) {
					//System.out.println("appending");
					appendToBuffer(selectedName, reviews[confimNum]);
				}
				// //System.out.println(selectedName + " Added to plagrism list");

			}
		}

		if (e.getActionCommand().equals("choose")) {
			JFileChooser fc = new JFileChooser();
			fc.setCurrentDirectory(new java.io.File(".")); // start at application current directory
			fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int returnVal = fc.showSaveDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File yourFolder = fc.getSelectedFile();
				rootDir = yourFolder.getAbsolutePath();
				readFileList();
				appendItemsToComboBox();
			}
		}
	}

	private void writeToFile() {
		if (sb != null && sb.length() > 0) {
			File resultFile = new File(resultFileName);
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
			LocalDateTime now = LocalDateTime.now();
			try (FileWriter resultWriter = new FileWriter(resultFile, true);
					FileWriter resultWriter1 = new FileWriter(
							"archive/" + resultFileName.substring(0, resultFileName.indexOf(".csv")) + "_"
									+ dtf.format(now) + ".csv",
							true);) {
				resultWriter.append(sb);
				resultWriter1.append(sb);
			} catch (IOException ex) {
				ex.printStackTrace();
			} finally {
				sb = new StringBuffer();
			}
		}
		// write visited namelist
		if (visitedNameList != null && visitedNameList.size() > 0) {
			StringBuffer sb1 = new StringBuffer();
			for (String s1 : visitedNameList) {
				sb1.append(s1 + "\n");
			}

			if (sb1 != null && sb1.length() > 0) {
				try (FileWriter visitedNameListWriter = new FileWriter(visitedFileName);) {
					visitedNameListWriter.write(sb1.toString());
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	private void appendToBuffer(String selectedName, String review) {
//		if (firstTime) {
//			try {
//				plagWriter = new FileWriter(plagFile);
//				plagWriter.append(selectedName2);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			firstTime = !firstTime;
//		} else {
//			try {
//				plagWriter.append("\n" + selectedName2+","+review);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
		sb.append(selectedName + "," + review + "\n");

	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (selectCategory.getSelectedItem() != null) {
			writeToFile();
			String itemSel = selectCategory.getSelectedItem().toString();
			mainFrame.setTitle("Viva of " + itemSel + " Semester");
			resultFileName = itemSel + "_Result.csv";
			String item = (String) selectCategory.getSelectedItem();

			fileName = rootDir + "/" + item + ".csv";
			visitedFileName = item + "_visited.csv";
			try {
				if (!item.equals(null)) {

					int i = 0;
					// populate previously selected names
					File fname = new File(visitedFileName);
					visitedNameList = new ArrayList<String>();
					if (fname.exists()) {

						i = 0;
						sc = new Scanner(fname);
						while (sc.hasNextLine()) {
							visitedNameList.add(sc.nextLine());
							i++;
						}
					}

					// populate names
					fname = new File(fileName);
					nameList = new ArrayList<String>();
					i = 0;
					sc = new Scanner(fname);
					while (sc.hasNextLine()) {
						String cName = sc.nextLine();
						if (!visitedNameList.contains(cName)) {
							nameList.add(cName);
							i++;
						}
					}
					nameListLength = i;

					if (flag == 1) {
						label.setText("Good Luck!!");
						label.setForeground(Color.WHITE);
					}
					flag = 1;

				}

			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}

		}
	}
}
