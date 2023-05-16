package cop2805;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import javax.swing.*;
import java.io.IOException;
import java.net.*;
import java.io.*;

public class Client {

	private JFrame frame;
	private JTextField field;
	private JButton button;
	private JLabel label;
	private JList<Integer> list;
	private DefaultListModel<Integer> model;

	public static void main(String[] args) {
		new Client();
	}
	public Client() {
		create();
		label("Word to search for:", 5, 15, 300, 50, 30);
		label("Responses:", 5, 400, 300, 50, 30);
		setField();
		setList();
		setButton();
	}

	private void create() {
		frame = new JFrame();
		frame.getContentPane().setLayout(null);
		frame.setTitle("Word Search");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 620);
		frame.setVisible(true);
	}

	private JLabel label(String text, int x, int y, int width, int height, int size) {
		JLabel label = new JLabel(text);
		label.setBounds(x, y, width, height);
		label.setFont(new Font(Font.SERIF, Font.PLAIN, size));
		frame.add(label);
		return label;
	}

	private void setField() {
		field = new JTextField();
		field.setBounds(300, 15, 200, 50);
		field.setBackground(Color.WHITE);
		field.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
		frame.add(field);
	}

	private void setList() {
		model = new DefaultListModel<>();
		list = new JList<>(model);
		list.setBackground(Color.WHITE);
		list.setBounds(300, 80, 200, 400);
		list.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
		frame.add(list);
	}

	private void setButton() {
		button = new JButton("Transmit");
		button.setBounds(200, 510, 150, 50);
		button.setFont(new Font(Font.SERIF, Font.PLAIN, 30));
		frame.add(button);
		button.addActionListener(a -> new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.clear();
				String word = field.getText();
				Socket socket;
				try {
					socket = new Socket("localhost", 1236);
					DataOutputStream stream = new DataOutputStream(socket.getOutputStream());
					DataInputStream inputStream = new DataInputStream(socket.getInputStream());
					BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					stream.writeUTF(word);
					String str = "";
					while (str != null) {
						str = br.readLine();
						if (str != null)
							model.addElement(Integer.parseInt(str));
					}
					socket.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}

		});
	}
}