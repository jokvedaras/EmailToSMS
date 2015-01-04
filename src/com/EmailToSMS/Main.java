package com.EmailToSMS;

public class Main {

	public static void main(String args[]) {
		
		 /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Gui().setVisible(true);
            }
        });
	}
}
