package com.crestasom.zipmaker;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

import net.lingala.zip4j.exception.ZipException;

public class ZipUI extends JFrame implements ActionListener {
	JPanel mainPanel;
	JButton folderCompress;
	JFileChooser fc;
	String rootDir;
	File choosenFolder;

	public static void main(String[] args) {
		new ZipUI();
	}
	public ZipUI() {
		super("Folder Zipper");
		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		folderCompress = new JButton("Compress from a folder");
		folderCompress.addActionListener(this);
		mainPanel.add(folderCompress);
		add(mainPanel);
		pack();
		setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource().equals(folderCompress)) {
			JFileChooser fc = new JFileChooser();
			fc.setCurrentDirectory(new java.io.File(".")); // start at application current directory
			fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int returnVal = fc.showSaveDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				choosenFolder = fc.getSelectedFile();
				rootDir = choosenFolder.getAbsolutePath();
				try {
					ZipMaker.compressWithPassword(rootDir);
				} catch (ZipException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		}
	}

}
