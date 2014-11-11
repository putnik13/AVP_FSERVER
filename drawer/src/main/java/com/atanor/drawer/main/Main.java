package com.atanor.drawer.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Action;

import com.atanor.drawer.draw.Canvas;


public class Main {
	
	public static void main(String[] args) {
		Canvas c = new Canvas("NetDrawer");
		c.setSize(800, 600);
		c.setVisible(true);
	}

}
