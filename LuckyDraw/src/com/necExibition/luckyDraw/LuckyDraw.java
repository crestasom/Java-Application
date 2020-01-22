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
	File plagFile;
	int width;
	File fname;
	int randomNum;
	FileWriter plagWriter;
	String rootDir = System.getProperty("user.dir") + "";
	Scanner sc;
	private JComboBox<String> selectCategory;
	private JLabel headerLabel, label;
	private final String pre = "<html><font color='", post = "</font></html>";
	private String displayText;
	private String[] fileList = new String[20];
	private String[] nameList = new String[100];
	String selectedName;
	private List<String> visitedNames;
	private int nameListLength, fileListLength;

	private JButton start, stop;
	private java.util.Timer timer;
	GridBagConstraints gbcx;

	public LuckyDraw() {
		visitedNames = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			nameList[i] = i + "";
		}
		readFileList();

		prepareGUI();

		prepareMenu();

	}

	public void prepareMenu() {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub

		File file = new File(rootDir);
		int x = 0;
		fileList = new String[200];
		System.out.println(rootDir);
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
		// System.out.println(fileListLength);
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
		start.setMnemonic(KeyEvent.VK_S);
		start.setActionCommand("start");
		start.setFont(new Font("", 0, 50));
		gbcx.gridy = 1;
		gbcx.gridx = 0;
		gbcx.fill = GridBagConstraints.HORIZONTAL;
		gbcx.weightx = 1.0;
		mainFrame.add(start, gbcx);

		stop = new JButton("Stop");
		stop.addActionListener(this);
		stop.setMnemonic(KeyEvent.VK_S);
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
				try {
					plagWriter.close();
					System.exit(0);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}

			@Override
			public void windowClosed(WindowEvent e) {
				try {
					plagWriter.close();
					System.exit(0);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

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

					// do{

					randomNum = rand.nextInt(((nameListLength - 1) - 0) + 1) + 0;
					// System.out.println(randomNum);
					selectedName = visitedNames.get(randomNum);
					displayText = pre + "white'>" + selectedName + post;
					label.setText(displayText);

					// }while(visitedNames.contains(nameList[randomNum]));

					// visitedNames.add(nameList[randomNum]);
				}
			};

			timer.schedule(task, java.util.Calendar.getInstance().getTime(), 100);
			timerFlag = 1;
		}

		if (e.getSource() == stop) {
			if (timerFlag == 1) {
				timer.cancel();// cancel the tasks scheduled
				visitedNames.remove(randomNum);
				nameListLength--;
				timerFlag = 0;
				int confimNum = JOptionPane.showConfirmDialog(LuckyDraw.this,
						"Add " + selectedName + " to Plagarism list?");
				if (confimNum == JOptionPane.OK_OPTION) {
					writeToFile(selectedName);
					System.out.println(selectedName + " Added to plagrism list");
				}
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

	private void writeToFile(String selectedName2) {
		if (firstTime) {
			try {
				plagWriter = new FileWriter(plagFile);
				plagWriter.append(selectedName2);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			firstTime = !firstTime;
		} else {
			try {
				plagWriter.append(" , " + selectedName2);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		if (selectCategory.getSelectedItem() != null) {
			plagFile = new File(selectCategory.getSelectedItem().toString() + " Plaglist.csv");
			System.out.println("test" + plagFile.getName());
			String item = (String) selectCategory.getSelectedItem();
			try {
				if (!item.equals(null)) {
					fname = new File(rootDir + "/" + item + ".csv");
					visitedNames = new ArrayList<String>();
					int i = 0;
					sc = new Scanner(fname);
					while (sc.hasNextLine()) {

						visitedNames.add(sc.nextLine());
						i++;
					}
					nameListLength = i;

					if (flag == 1) {
						label.setText("Good Luck!!");
						label.setForeground(Color.WHITE);
					}
					flag = 1;
				}

			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
	}
}
