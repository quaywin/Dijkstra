
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class GraphFrame extends JFrame {

	public static final int DEFAULT_WIDTH = 1000;
	public static final int DEFAULT_HEIGHT = 600;
	public static final int FONTSIZE = 25;
	public final JTextArea area2, areashortest;
	private JFileChooser fc;
	private JScrollPane scr1, scr2, scr3;
	private File fileInput;
	private static final long serialVersionUID = 1L;
	private ImageIcon loading;
	JPanel contentPaneGIF;
	private BufferedReader reader;

	public GraphFrame() {
		this.setResizable(false);
		Font font = new Font("Courier", Font.PLAIN, 13);
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setTitle("Dijkstra Algorithms");
		fc = new JFileChooser();

		final JTextArea area1 = new JTextArea(2, 61);
		area1.setFont(font);
		scr1 = new JScrollPane(area1, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		add(scr1, BorderLayout.WEST);

		area2 = new JTextArea(4, 50);
		area2.setFont(font);
		scr2 = new JScrollPane(area2, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		add(scr2, BorderLayout.EAST);

		JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JButton bt1 = new JButton("Run");
		panel1.add(bt1);
		bt1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (fileInput != null) {
					try {
						File file = new File(fileInput.getAbsolutePath());
						if (!file.exists()) {
							file.createNewFile();
						}
						PrintWriter out = new PrintWriter(file);
						out.println(area1.getText());
						out.close();
					} catch (IOException e) {
						System.out.println(e.getMessage());
					}

					final String absolutePath = fileInput.getAbsolutePath();
					final Implement implement = new Implement();
					area2.setText("");
					areashortest.setText("");
					Thread t = new Thread() {
						public void run() {
							contentPaneGIF.setVisible(true);
							implement.ReadFile(fileInput.getPath());
							implement.WriteFile(absolutePath.substring(0,
									absolutePath.lastIndexOf(File.separator)));
							contentPaneGIF.setVisible(false);
							area2.setText(implement.getStrDiagram());
							area2.setCaretPosition(0);
							areashortest.setText(implement
									.getStringShortestPath());
							areashortest.setCaretPosition(0);
						}
					};
					t.start();

				}
			}
		});
		JButton bt2 = new JButton("Input File");
		panel1.add(bt2);
		bt2.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					int returnVal = fc.showOpenDialog(null);
					if (returnVal == 0) {
						fileInput = fc.getSelectedFile();
						area1.setText(readMatrix(fileInput.getPath()));
						area1.setCaretPosition(0);
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		JButton bt4 = new JButton("Exit");
		panel1.add(bt4);
		bt4.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				int result = JOptionPane.showConfirmDialog(null,
						"Do you want to exit ?", "Exit Windows",
						JOptionPane.YES_NO_OPTION);
				if (result == 0) {
					System.exit(0);
				}
			}
		});
		add(panel1, BorderLayout.SOUTH);
		JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel lbshortest = new JLabel("Shortest Path is : ");
		panel2.add(lbshortest);
		areashortest = new JTextArea(4, 50);
		scr3 = new JScrollPane(areashortest,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		panel2.add(scr3);
		add(panel2, BorderLayout.NORTH);

		contentPaneGIF = new JPanel();
		contentPaneGIF.setLayout(new BorderLayout());
		JLabel label = new JLabel();
		loading = new ImageIcon("src/loading.gif");
		label.setIcon(loading);
		contentPaneGIF.add(label);
		contentPaneGIF.setVisible(false);
		add(contentPaneGIF, BorderLayout.CENTER);
	}

	public String readMatrix(String file) throws IOException {
		reader = new BufferedReader(new FileReader(file));
		String line = null;
		StringBuilder stringBuilder = new StringBuilder();
		String ls = System.getProperty("line.separator");

		while ((line = reader.readLine()) != null) {
			stringBuilder.append(line);
			stringBuilder.append(ls);
		}

		return stringBuilder.toString();
	}

}