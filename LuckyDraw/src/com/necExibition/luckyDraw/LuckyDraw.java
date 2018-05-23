package com.necExibition.luckyDraw;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.*;

/**
 * This application can be use to perform luck draw
 * 
 * @author crestaSom
 *
 */
public class LuckyDraw extends Component implements ActionListener, ItemListener {
	private static final long serialVersionUID = 1L;
	private JFrame mainFrame;
	JMenu menu;
	JMenuBar bar;
	JMenuItem chooseFolder;
	int flag = 0, timerFlag = 0;
	int width;
	File fname;
	String rootDir = System.getProperty("user.dir") + "/datalist";
	Scanner sc;
	private JComboBox<String> selectCategory;
	private JLabel headerLabel, label;
	private final String pre = "<html><font color='", post = "</font></html>";
	private String displayText;
	private String[] fileList = new String[20];
	private String[] nameList = new String[20];

	private int nameListLength, fileListLength;

	private JButton start, stop;
	private java.util.Timer timer;
	GridBagConstraints gbcx;

	public LuckyDraw() {

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
		System.out.println(fileListLength);
		selectCategory.removeAllItems();
		for (int m = 0; m < fileListLength; m++) {
			selectCategory.addItem(fileList[m]);
		}
	}

	private void prepareGUI() {
		gbcx = new GridBagConstraints();
		mainFrame = new JFrame("NEC Project Exibition 2016");
		mainFrame.setSize(900, 900);
		mainFrame.setLayout(new GridBagLayout());

		headerLabel = new JLabel(pre + "blue'>Lucky Draw" + post, JLabel.CENTER);
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
		JPanel panel = new JPanel();
		label = new JLabel(displayText, SwingConstants.CENTER);
		label.setVerticalAlignment(SwingConstants.TOP);
		label.setFont(new Font(label.getFont().getName(), label.getFont().getStyle(), 100));
		label.setForeground(Color.WHITE);
		panel.setBackground(Color.BLACK);
		label.setHorizontalTextPosition(JLabel.CENTER);
		panel.add(label);
		gbcx.gridx = 0;
		gbcx.gridy = 2;
		gbcx.gridwidth = 2;
		mainFrame.add(panel, gbcx);

		mainFrame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == start) {

			timer = new Timer();
			TimerTask task = new TimerTask() {
				@Override
				public void run() {
					Random rand = new Random();
					int randomNum = rand.nextInt(((nameListLength - 1) - 0) + 1) + 0;
					displayText = pre + "white'>" + nameList[randomNum] + post;

					label.setText(nameList[randomNum]);
				}
			};

			timer.schedule(task, java.util.Calendar.getInstance().getTime(), 150);
			timerFlag = 1;
		}

		if (e.getSource() == stop) {
			if (timerFlag == 1) {
				timer.cancel();// cancel the tasks scheduled
				timerFlag = 0;
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

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		if (selectCategory.getSelectedItem() != null) {
			String item = (String) selectCategory.getSelectedItem();
			try {
				if (!item.equals(null)) {
					fname = new File(rootDir + "/" + item + ".csv");

					int i = 0;
					sc = new Scanner(fname);
					while (sc.hasNextLine()) {
						System.out.println(i);
						nameList[i++] = sc.nextLine();
					}
					nameListLength = i;
					System.out.println(nameListLength);
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