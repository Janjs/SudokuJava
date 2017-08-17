package presentacio;

import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import aplicacio.ControlBBDD;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Map;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JList;

@SuppressWarnings("serial")
public class PreGameGUI extends JFrame {

	private ControlBBDD controlBBDD = new ControlBBDD();
	private JPanel contentPane;
	private JLabel lblSenseConnexi;
	private JTextField textField;
	private JList<String> list;
	DefaultListModel<String> model;
	private int idsPartides[] = new int[100];

	String nomJugador = "Anonim";
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new PreGameGUI();
				} catch (Exception e) {
					System.out.println("Tot funciona perfectament");
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * Create the frame.
	 */
	public PreGameGUI() {
		
		this.setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblSenseConnexi = new JLabel("Sense Connexi\u00F3");
		lblSenseConnexi.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblSenseConnexi.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSenseConnexi.setBounds(10, 10, 414, 14);
		contentPane.add(lblSenseConnexi);
		
		getContentPane().setLayout(null);
			
		JLabel lblNomJugador = new JLabel("Nom Jugador: ");
		lblNomJugador.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNomJugador.setBounds(25, 36, 92, 14);
		getContentPane().add(lblNomJugador);
		
		textField = new JTextField();
		textField.setHorizontalAlignment(SwingConstants.RIGHT);
		textField.setBounds(127, 33, 258, 20);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnIniciarPartidaNova = new JButton("Iniciar Partida Nova");
		btnIniciarPartidaNova.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(controlBBDD.gameCount()>=100){
					JOptionPane.showMessageDialog(new JFrame(),
						 "S'ha arribat al nombre màxim de partides.",
						    null,
						    JOptionPane.WARNING_MESSAGE);
				}
				else iniciarSudoku(0);
			}
		});
		btnIniciarPartidaNova.setBounds(35, 222, 167, 23);
		contentPane.add(btnIniciarPartidaNova);
		btnIniciarPartidaNova.setEnabled(false);
		
		JButton btnCarregarPartida = new JButton("Carregar Partida");
		btnCarregarPartida.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				iniciarSudoku(idsPartides[list.getSelectedIndex()]);
			}
		});
		
		JButton btnIdentificarse = new JButton("Identificar-se");
		btnIdentificarse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// Load / Create Player
				
				try {
					controlBBDD.initPlayer(textField.getText());	
					btnIdentificarse.setEnabled(false);
					textField.setEnabled(false);
					list.setEnabled(true);
					btnIniciarPartidaNova.setEnabled(true);
					btnCarregarPartida.setEnabled(true);
					checkGameCount();
				} 
				catch (IllegalStateException e0){
					JOptionPane.showMessageDialog(new JFrame(),
							 "L'usuari que ha entrat ja es troba en partida. \nSi el problema persisteix, contacta amb l'administrador de la Base de Dades.",
							    null,
							    JOptionPane.WARNING_MESSAGE);	
				}
				catch (Exception e1) {
					System.out.println("Tot funciona amb normalitat Â¯|_(ãƒ„)_|Â¯");
					e1.printStackTrace();
				}	
			}
		});
		btnIdentificarse.setBounds(260, 74, 125, 23);
		getContentPane().add(btnIdentificarse);
		
		this.model = new DefaultListModel<String>();
		
		list = new JList<String>(model);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setEnabled(false);
		list.setBounds(25, 124, 376, 87);
	
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(list);
		scrollPane.setBounds(25, 124, 376, 87);
		getContentPane().add(scrollPane);
		
		btnCarregarPartida.setEnabled(false);
		btnCarregarPartida.setBounds(212, 222, 173, 23);
		getContentPane().add(btnCarregarPartida);
		
		int result = 1;
		do {
			try {
				result = 1;
				controlBBDD.login();
				controlBBDD.setOnline(true);
				lblSenseConnexi.setText("Conectat com: G12GEILAB1");
			} catch (Exception e) {
				String theMessage = "No s'ha pogut conectar amb la Base de Dades. Vol continuar sense conexiÃ³? (No es guardarÃ¡n les partides jugades)";
				result = JOptionPane.showOptionDialog((Component) null, theMessage, "Error de conexiÃ³:",
						JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null,
						new String[] { "Reintentar", "Continuar sense conexiÃ³" }, "default");

			}
		} while (result == 0);

		if (!controlBBDD.isOnline()){
			iniciarSudoku(0);
			endPreGameScreen();
		}
	    
		java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
	    setBounds((screenSize.width-450)/2, (screenSize.height-300)/2, 450, 300);

		this.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				if (controlBBDD.playerInitialized()) {
					try {
						controlBBDD.finalitzar();
					} catch (Exception e) {
						JOptionPane.showMessageDialog(new JFrame(),
								"Hi ha hagut un problema per finalitzar la sesió a la Base de Dades. Si la connexió a internet es correcte, contacta amb l'administrador.",
								null, JOptionPane.WARNING_MESSAGE);
					}
					System.exit(0);
				}
			}
		});
		
		// END initComponents
	    
	}
	
	private void iniciarSudoku(int id){
		try {
			if(controlBBDD.isOnline()) controlBBDD.initPartida(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		new SudokuGUI("Sudoku",this.controlBBDD);
		endPreGameScreen();
	}

	
	public void checkGameCount(){
		try {
			switch (controlBBDD.gameCount()) {
			case 0:
				iniciarSudoku(0);
				break;
			case 1: {
				String theMessage = "Hi ha una partida anterior iniciada. Vol recuperar-la o iniciar una nova?";
				int result = JOptionPane.showOptionDialog((Component) null, theMessage,
						"Hola " + textField.getText() + ": ", JOptionPane.YES_NO_OPTION,
						JOptionPane.INFORMATION_MESSAGE, null,
						new String[] { "Recuperar partida anterior", "Iniciar nova partida" }, "default");
				if (result == 0) {
					iniciarSudoku(controlBBDD.idPartidaAnterior());
				} else {
					iniciarSudoku(0);
				}
			}
			default: {
				Map<Integer, Date> llistat = controlBBDD.partides();
				String[] items = new String[100];
				int i = 0;
				for (Integer id : llistat.keySet()) {
					items[i] = "Partida nº" + id + " (" + llistat.get(id) + ")";
					this.idsPartides[i] = id;
					i++;
				}
				for (int j = 0; j < i; j++) {
					model.add(j, items[j]);
				}
			}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	private void endPreGameScreen(){
		this.setVisible(false);
	}
}
