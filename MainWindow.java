import javax.swing.*;
import java.awt.event.*;
import java.awt.Color;
import java.awt.Insets;
import java.awt.event.*;
import java.io.File;
import java.math.BigInteger;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Font;

public class MainWindow extends JDialog
{
	/** This is the main file for creating your application window.
	 *  TODO: It should create the window described in the GUI and add ActionListeners to all the GUI elements that
	 *  need user interactivity.
	 *  Complete code is provided for the “Generating Prime Numbers” dialog box.
	 *  You may optimize this code or use it as-it.
	 *  It is intended as a starting point example of writing a GUI, and is intentionally uncommented to force
	 *  the student to research and understand the code, rather than just using cookie-cutter copy-and-paste.
	 */
	private static final long serialVersionUID = -3880026026104218593L;
	private Primes m_Primes;
	//private JTextField tfPrimeFileName = new JTextField(Config.Prime_file_name, 80);
	//private JTextField tfCrossFileName = new JTextField(Config.Cross_file_name, 80);
	private JLabel lblPrimeCount;
	private JLabel lblCrossCount;
	private JLabel lblLargestPrime;
	private JLabel lblStatus ;

	private JPanel contentPane;
	private JButton saveButtonPrimes;
	private JButton loadButtonPrimes;
	private JTextField tfPrimeFileName;
	private JButton saveButtonCrosses;
	private JButton loadButtonCrosses;
	private JTextField tfCrossFileName;
	private JButton generateCrossesButton;
	private JButton generatePrimesButton;
	private JLabel primesFileLabel;
	private JButton buttonOK;
	private JButton buttonCancel;
	
	MainWindow(String name, Primes p)
	{
		setContentPane(contentPane);
		setModal(true);
		getRootPane().setDefaultButton(buttonOK);

		saveButtonPrimes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FileAccess.savePrimes(m_Primes, tfPrimeFileName.getAccessibleContext().getAccessibleText().getSelectedText());
			}
		});

		buttonCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onCancel();
			}
		});

		// call onCancel() when cross is clicked
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				onCancel();
			}
		});

		// call onCancel() on ESCAPE
		contentPane.registerKeyboardAction(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onCancel();
			}
		}, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
	}

	protected void popupGeneratePrimes()
	{
		// Create a new window called dPrimes
		JDialog dPrimes = new JDialog(this, "Prime Number Generation");
		GridBagLayout gridLayout = new GridBagLayout();
		dPrimes.getContentPane().setBackground(new Color(80, 0, 0)); // #500000 is A&M Maroon
		dPrimes.getContentPane().setLayout(gridLayout);
		
		GridBagConstraints gbcDialog = new GridBagConstraints();
		gbcDialog.fill = GridBagConstraints.HORIZONTAL;
		gbcDialog.anchor = GridBagConstraints.WEST;
		gbcDialog.ipady = 10;
		gbcDialog.weightx = .5;
		gbcDialog.insets = new Insets(1,2,0,0);
		gbcDialog.gridx = 0;
		gbcDialog.gridy = 0;
		
		GridBagConstraints gbcPanel = new GridBagConstraints();
		gbcPanel.anchor = GridBagConstraints.WEST;
		gbcPanel.weightx = .5;
		gbcPanel.insets = new Insets(1,2,0,0);
		gbcPanel.gridx = 0;
		gbcPanel.gridy = 0;

		// -------------------------------------------------------------------------------------------------------------
		JPanel pnlGenerate = new JPanel();
		pnlGenerate.setLayout(new GridBagLayout());
		
		JLabel lblCount = new JLabel("Number of Primes to Generate");
		lblCount.setFont(new Font("Tahoma", Font.PLAIN, 12));
		pnlGenerate.add(lblCount, gbcPanel);
		
		JTextField tfCount = new JTextField();
		lblCount.setLabelFor(tfCount);
		tfCount.setColumns(30);
		gbcPanel.gridx = 1;
		pnlGenerate.add(tfCount, gbcPanel);
		
		JLabel lblStart = new JLabel("Starting Number (does not have to be prime)");
		lblStart.setFont(new Font("Tahoma", Font.PLAIN, 12));
		gbcPanel.gridx = 0;
		gbcPanel.gridy = 1;
		pnlGenerate.add(lblStart, gbcPanel);
		
		JTextField tfStart = new JTextField();
		lblStart.setLabelFor(tfStart);
		tfStart.setColumns(30);
		gbcPanel.gridx = 1;
		pnlGenerate.add(tfStart, gbcPanel);

		// -------------------------------------------------------------------------------------------------------------

		dPrimes.add(pnlGenerate, gbcDialog);
		
		JPanel pnlButtons = new JPanel();
		pnlButtons.setLayout(new GridBagLayout());
		
		JButton btnGeneratePrimes = new JButton("Generate Primes");
		btnGeneratePrimes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
				{
					BigInteger start = new BigInteger(tfStart.getText());
					int count = Integer.parseInt(tfCount.getText());
					m_Primes.generatePrimes(start, count);
					lblStatus.setText("Status: Excited. Primes have been generated.");
					updateStats();
					dPrimes.dispose();
				}
				catch(NumberFormatException ex)
				{
					lblStatus.setText("You failed to type in an integer successfully. You are terrible at math. Shame.");
					dPrimes.dispose();
				}
			}
		}
    	);
		gbcPanel.gridx = 0;
		gbcPanel.gridy = 0;
		pnlButtons.add(btnGeneratePrimes, gbcPanel);
		
		JButton btnCancel = new JButton("Cancel Generation");
		btnCancel.addActionListener(
			new ActionListener() { public void actionPerformed(ActionEvent e) {
      	dPrimes.dispose();
      } }
		);
		gbcPanel.anchor = GridBagConstraints.EAST;
		gbcPanel.gridx = 1;
		pnlButtons.add(btnCancel, gbcPanel);		
		
		gbcDialog.gridy = 1;
		dPrimes.add(pnlButtons, gbcDialog);
		
		JPanel pnlStatus = new JPanel();
		pnlStatus.setLayout(new GridBagLayout());

		gbcPanel.anchor = GridBagConstraints.SOUTHWEST;
		gbcPanel.weightx = .5;
		gbcPanel.insets = new Insets(1,2,0,0);
		gbcPanel.gridx = 0;
		gbcPanel.gridy = 1;

		JLabel lblNotice = new JLabel("Warning: This application is single threaded, and will freeze while generating primes.");
		lblNotice.setFont(new Font("Tahoma", Font.PLAIN, 12));
		pnlStatus.add(lblNotice, gbcPanel);
		
		gbcDialog.gridy = 2;
		dPrimes.add(pnlStatus, gbcDialog);
		
		dPrimes.setSize(200,200);
		dPrimes.pack(); // Knowing what this is and why it is needed is important. You should read the documentation on this function!
		dPrimes.setVisible(true);		
	}

	// This function updates all the GUI statistics. (# of primes, # of crosses, etc)
	private void updateStats()
	{
		/*
		TODO: Implement this function
		 */
 	}

}
