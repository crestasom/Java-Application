package com.crestasom.zipmaker;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import net.lingala.zip4j.exception.ZipException;

public class ZipUI extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	JPanel mainPanel, sourcePanel, destPannel, actionPanel;
	JButton srcFolderBtn, destFolderBtn, compressBtn, resetBtn;
	JLabel srcFolderLbl, destFolderLbl;
	JFileChooser fc;
	String rootDir, srcDir, destDir;
	File srcFolder, destFolder;

	public static void main(String[] args) {
		new ZipUI();
	}

	public ZipUI() {
		super("Folder Zipper");
		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

		sourcePanel = new JPanel();
		sourcePanel.setLayout(new FlowLayout(FlowLayout.LEFT));

		srcFolderBtn = new JButton("Choose Source Folder");
		srcFolderBtn.addActionListener(this);
		sourcePanel.add(srcFolderBtn);
		srcFolderLbl = new JLabel("\tNo Folder Selected");
		sourcePanel.add(srcFolderLbl);

		destPannel = new JPanel();
		destPannel.setLayout(new FlowLayout(FlowLayout.LEFT));

		destFolderBtn = new JButton("Choose Destination Folder");
		destFolderBtn.addActionListener(this);
		destPannel.add(destFolderBtn);
		destFolderLbl = new JLabel("No Folder Selected");
		destPannel.add(destFolderLbl);

		actionPanel = new JPanel();
		actionPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

		compressBtn = new JButton("Compress");
		compressBtn.addActionListener(this);
		actionPanel.add(compressBtn);
		resetBtn = new JButton("Reset");
		resetBtn.addActionListener(this);
		actionPanel.add(resetBtn);

		mainPanel.add(sourcePanel);
		mainPanel.add(Box.createVerticalGlue());
		mainPanel.add(destPannel);
		mainPanel.add(Box.createVerticalGlue());
		mainPanel.add(actionPanel);
		add(mainPanel);
		setResizable(false);
		setSize(500, 250);
		setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(srcFolderBtn)) {
			JFileChooser fc = new JFileChooser();
			fc.setCurrentDirectory(new java.io.File(".")); // start at application current directory
			fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int returnVal = fc.showSaveDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				srcFolder = fc.getSelectedFile();
				srcDir = srcFolder.getAbsolutePath();
				srcFolderLbl.setText(srcDir);
			}
		} else if (e.getSource().equals(destFolderBtn)) {
			JFileChooser fc = new JFileChooser();
			fc.setCurrentDirectory(new java.io.File(".")); // start at application current directory
			fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int returnVal = fc.showSaveDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				destFolder = fc.getSelectedFile();
				destDir = destFolder.getAbsolutePath();
				destFolderLbl.setText(destDir);
			}
		} else if (e.getSource().equals(resetBtn)) {
			srcDir = null;
			destDir = null;
			srcFolderLbl.setText("No Folder Selected");
			destFolderLbl.setText("No Folder Selected");
		} else if (e.getSource().equals(compressBtn)) {
			if (srcDir != null && destDir != null) {
				try {
					ZipMaker.compressWithPassword(srcDir, destDir);
					JOptionPane.showMessageDialog(this,
							"All folders inside '" + srcDir + "' were successfully zipped with password");
				} catch (ZipException | IOException e1) {
					e1.printStackTrace();
				}
			} else {
				JOptionPane.showMessageDialog(this, "Source or destination directory not selected", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

}
