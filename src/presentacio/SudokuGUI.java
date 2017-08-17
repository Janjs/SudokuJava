package presentacio;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import aplicacio.ControlBBDD;
import aplicacio.ControlJoc;
import domini.Casella;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;
import java.awt.Panel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class SudokuGUI extends JFrame {
	private ControlJoc sudoku;
	private JTextField[][] caselles = new JTextField[9][9];
	private JButton btnConfirmarDadesInicials;
	private int[][] customCaselles = new int[9][9];
	private int[][] valors;
	private boolean inputError=false;
	private boolean custom=false;
	
	private Panel sudokuContainer;
	private JPanel menu;
	private JButton btnGenerarNouTaulell;
	private JButton btnGuardarPartida;
	
	int customInputs=0;
	private ControlBBDD controlBD;

	/**
	 * Create the frame.
	 */
	public SudokuGUI(String titol,ControlBBDD controlBD){
		super(titol);
		this.controlBD = controlBD; 
		this.sudoku = new ControlJoc(controlBD);
		
		if(controlBD.isNew())this.sudoku.generarTaulell(false);
		else this.sudoku.carregarTaulell();
		
		initComponents();
		if(!controlBD.isOnline()) btnGuardarPartida.setEnabled(false);
		if(!controlBD.isNew()){
			sudokuContainer.setVisible(true);
			btnGenerarNouTaulell.setVisible(true);
			btnGuardarPartida.setVisible(true);
			menu.setVisible(false);
			actualitzarModificables();
		}
		
		this.setSize(new Dimension(700,700));
		this.setVisible(true);	
	}
	public void initComponents() {
		
		java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
	    setBounds((screenSize.width-700)/2, (screenSize.height-700)/2, 700, 700);
	    
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				customCaselles[i][j]=Casella.getPosBuida();
			}
		}
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		valors = sudoku.getValorsGraella();
		getContentPane().setLayout(null);
		getContentPane().setLayout(new BorderLayout());
		
		menu = new JPanel();
		menu.setSize(434, 100);
		getContentPane().add(menu, BorderLayout.CENTER);
		menu.setBounds(0, 0, 680, 631);
		menu.setLayout(null);
		
		JLabel lblSudoku = new JLabel("Sudoku");
		lblSudoku.setHorizontalAlignment(SwingConstants.CENTER);
		lblSudoku.setFont(new Font("Sitka Small", Font.PLAIN, 64));
		lblSudoku.setBounds(98, 96, 479, 84);
		menu.add(lblSudoku);
		
		Panel buttonContainer = new Panel();
		buttonContainer.setLocation(0, 222);
		getContentPane().add(buttonContainer,BorderLayout.PAGE_END);
		buttonContainer.setLayout(new BoxLayout(buttonContainer, BoxLayout.X_AXIS));

		
		btnGenerarNouTaulell = new JButton("Generar Nou Taulell");
		buttonContainer.add(btnGenerarNouTaulell);
		btnGenerarNouTaulell.setPreferredSize(new Dimension(540,30));
		btnGenerarNouTaulell.setVisible(false);
		
		btnGuardarPartida = new JButton("Guardar Partida");
		btnGuardarPartida.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					controlBD.guardarSudoku();
					JOptionPane.showMessageDialog(new JFrame(),
						    "La partida s'ha guardat satisfactoriament.",
						    null,
						    JOptionPane.INFORMATION_MESSAGE);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(new JFrame(),
						    "No s'ha pogut guardar la partida. Comprova que la conexió a Internet funcioni correctament.",
						    null,
						    JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		btnGuardarPartida.setPreferredSize(new Dimension(540,30));
		buttonContainer.add(btnGuardarPartida);
		btnGuardarPartida.setVisible(false);
		
				btnConfirmarDadesInicials = new JButton("Confirmar Dades Inicials");
				btnConfirmarDadesInicials.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if (inputError)
							JOptionPane.showMessageDialog(new JFrame(),
								    "Hi ha un error pendent de corregir. S'ha de solucionar abans de poder continuar.",
								    "Dades Incorrectes",
								    JOptionPane.WARNING_MESSAGE);
						else if(customInputs<17){
							JOptionPane.showMessageDialog(new JFrame(),
								    "S'han d'entrar com a mínim 17 valors abans de continuar.",
								    "Dades Incorrectes",
								    JOptionPane.WARNING_MESSAGE);
						}
						else{
							custom = false;
							btnConfirmarDadesInicials.setVisible(false);
							btnGenerarNouTaulell.setVisible(true);
							btnGuardarPartida.setVisible(true);
							sudoku.confirmarCaselles();
							actualitzar();
						}
					}
				});
				buttonContainer.add(btnConfirmarDadesInicials);
				btnConfirmarDadesInicials.setPreferredSize(new Dimension(1080,30));
				btnConfirmarDadesInicials.setVisible(false);
		
		sudokuContainer = new Panel();
		getContentPane().add(sudokuContainer,BorderLayout.CENTER);
		sudokuContainer.setLayout(new GridLayout(9, 9));
		sudokuContainer.setVisible(false);
		
		
		// Butó Iniciar
		
		JButton btnIniciar = new JButton("Iniciar");
		btnIniciar.setFont(new Font("Sitka Small", Font.PLAIN, 32));
		btnIniciar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sudokuContainer.setVisible(true);
				btnGenerarNouTaulell.setVisible(true);
				btnGuardarPartida.setVisible(true);
				menu.setVisible(false);
			}
		});
		btnIniciar.setBounds(61, 246, 570, 84);
		menu.add(btnIniciar);
		
		
		// Butó Crear Personalitzat
		
		JButton btnCrearPersonalitzat = new JButton("Crear Personalitzat");
		btnCrearPersonalitzat.setFont(new Font("Sitka Small", Font.PLAIN, 32));
		btnCrearPersonalitzat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				custom=true;
				sudoku.generarTaulell(true);
				actualitzar();
				sudokuContainer.setVisible(true);
				btnConfirmarDadesInicials.setVisible(true);
				menu.setVisible(false);
			}
		});
		btnCrearPersonalitzat.setBounds(61, 356, 570, 92);
		menu.add(btnCrearPersonalitzat);
		
		btnGenerarNouTaulell.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Object[] options = { "Si", "No" };
				int n = JOptionPane.showOptionDialog(new JFrame(),
						"Generar una nova graella borrarà l'actual. Continuar?", "", JOptionPane.YES_NO_OPTION,
						JOptionPane.WARNING_MESSAGE, null, options, options[1]);
				if (n == JOptionPane.OK_OPTION) { // Afirmative
					System.out.println(sudoku.getToStringTaulell());
					inputError=false;
					sudoku.generarEquivalent();
					actualitzar();
					System.out.println(sudoku.getToStringTaulell());
				}
			}
  	
		});

		for(int i=0; i<9;i++){
			for(int j=0; j<9;j++){
				initCaselles(i,j,sudokuContainer);
			}
		}
		
		SudokuGUI frame = this;
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				if (!menu.isVisible() && controlBD.isOnline()) {
					if (JOptionPane.showConfirmDialog(frame, "Vols guardar la partida abans de sortir?", null,
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
						try {
							controlBD.guardarSudoku();
							JOptionPane.showMessageDialog(new JFrame(), "La partida s'ha guardat satisfactoriament.",
									null, JOptionPane.INFORMATION_MESSAGE);
						} catch (Exception e) {
							JOptionPane.showMessageDialog(new JFrame(),
									"No s'ha pogut guardar la partida. Comprova que la conexió a Internet funcioni correctament.",
									null, JOptionPane.WARNING_MESSAGE);
						}
					}

					try {
						controlBD.finalitzar();
					} catch (Exception e) {
						JOptionPane.showMessageDialog(new JFrame(),
								"Hi ha hagut un problema per finalitzar la sesió a la Base de Dades. Si la connexió a internet es correcte, contacta amb l'administrador.",
								null, JOptionPane.WARNING_MESSAGE);
					}
				}
				System.exit(0);
			}
		});
		
		
		// END initComponents
	}

	private void actualitzar() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				int valor = sudoku.getValor(i, j); // public int getValor(int x,int y)
				if (valor == Casella.getPosBuida()) {
					caselles[i][j].setBackground(Color.WHITE);
					caselles[i][j].setEnabled(true);
					caselles[i][j].setText("");
				} else {
					caselles[i][j].setEnabled(false);
					caselles[i][j].setBackground(Color.DARK_GRAY);
					caselles[i][j].setDisabledTextColor(Color.yellow);
					caselles[i][j].setText(Integer.toString(valor));
				}
			}
		}
	}
	
	private void actualitzarModificables(){
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (sudoku.getModificable(i, j)) {
					caselles[i][j].setBackground(Color.WHITE);
					caselles[i][j].setEnabled(true);
				} else {
					caselles[i][j].setEnabled(false);
					caselles[i][j].setBackground(Color.DARK_GRAY);
					caselles[i][j].setDisabledTextColor(Color.yellow);
				}
			}
		}
	}
	
	private void initCaselles(int i, int j,Panel SudokuContainer) {
		int x=i;
		int y=j;
		
		caselles[i][j]=new JTextField();
		caselles[i][j].setHorizontalAlignment(JTextField.CENTER);
		caselles[i][j].setFont(new Font("Arial", Font.PLAIN, 20));	
		crearborders(i, j);
		if(valors[i][j]!=0){
			caselles[i][j].setText(Integer.toString(valors[i][j]));
			caselles[i][j].setBackground(Color.DARK_GRAY);
			caselles[i][j].setDisabledTextColor(Color.yellow);
			caselles[i][j].setEnabled(false);

		}
		caselles[i][j].addKeyListener(new KeyAdapter() {
			
			int [] casellaError;
			JTextField aux = new JTextField();
			
			// Event handler: keyPressed
			public void keyPressed(KeyEvent arg0) {
				char input = arg0.getKeyChar();
				int num = 0;
				if (inputError && aux != caselles[x][y]){
					JOptionPane.showMessageDialog(null,
							"S'ha introduït un valor incorrecte prèviament. Cal corregir-lo abans de continuar", "",
							JOptionPane.ERROR_MESSAGE);
					caselles[x][y].setText("");
				}
				else {
					try {			
						if (caselles[x][y].equals(aux)) {
							sudoku.afegirNouValor(x + 1, y + 1);
							caselles[casellaError[0]][casellaError[1]].setBackground(Color.DARK_GRAY);
							if (caselles[casellaError[0]][casellaError[1]].isEnabled()) {
								caselles[casellaError[0]][casellaError[1]].setBackground(Color.white);
							}
						}
						if (arg0.getKeyChar() == KeyEvent.VK_BACK_SPACE) {
							sudoku.afegirNouValor(x + 1, y + 1);
							if (casellaError != null) {
								caselles[casellaError[0]][casellaError[1]].setBackground(Color.DARK_GRAY);
								if (caselles[casellaError[0]][casellaError[1]].isEnabled()) {
									caselles[casellaError[0]][casellaError[1]].setBackground(Color.white);
								}
							}
							if (custom && customCaselles[x][y] != Casella.getPosBuida()) {
									customInputs--;
									customCaselles[x][y]=Casella.getPosBuida();
									System.out.println("Nº d'entrades personalitzades: " + customInputs);	
							}
						} else if (!(Character.isDigit(input))) {
							caselles[x][y].setBackground(Color.red);
							JOptionPane.showMessageDialog(null, "Ha de ser un número", "ERROR: xifra incorrecte",
									JOptionPane.ERROR_MESSAGE);
							caselles[x][y].setText("");
							caselles[x][y].setBackground(Color.white);
						} else if (input == Character.forDigit(Casella.getPosBuida(), 10)) {
							caselles[x][y].setBackground(Color.red);
							JOptionPane.showMessageDialog(null,
									"El número " + input + " no és correcte, ha de ser 1--9", "ERROR: xifra incorrecte",
									JOptionPane.ERROR_MESSAGE);
							caselles[x][y].setText("");
							caselles[x][y].setBackground(Color.white);
						} else {
							num = (int) Character.getNumericValue(input);
							sudoku.afegirNouValor(x + 1, y + 1, num);
							if(custom && customCaselles[x][y] == Casella.getPosBuida()){
								customInputs++;
								customCaselles[x][y]=num;
								System.out.println("Nº d'entrades personalitzades: "+customInputs);
							}
						}
						inputError = false;
					}
					catch (Exception e) {
						try{casellaError = sudoku.getErrorTaulell();
						caselles[casellaError[0]][casellaError[1]].setBackground(Color.red);
						caselles[x][y].setText("");
						aux = caselles[x][y];
						inputError = true;
						}
						catch(NullPointerException e2){}
					}

					if (sudoku.jocCompletat()) {
						for (int i = 0; i < 9; i++) {
							for (int j = 0; j < 9; j++) {
								caselles[i][j].setEnabled(false);
							}
						}
						JOptionPane.showMessageDialog(null, "Felicitats! Has emplenat totes les cel·les.", "FI JOC.",
								JOptionPane.INFORMATION_MESSAGE);
						try {
							if (controlBD.isOnline()) {
								controlBD.deleteSudoku();
								controlBD.finalitzar();
							}
							System.exit(0);
							
						} catch (Exception e) {
							JOptionPane.showMessageDialog(null, "S'ha produït un error al actualizar la partida. Comprova que la conexió a internet funcioni correctament.", null,
									JOptionPane.INFORMATION_MESSAGE);
							e.printStackTrace();
						}
						System.exit(1);
					}
					caselles[i][j].setText("");
				}
			}
		});
		SudokuContainer.add(caselles[i][j]);

	}

	private void crearborders(int i, int j) {
		int x = i;
		int y = j;
		if((x==0&&y==0)||(x==3&&y==0)||(x==6&&y==0)||(x==0&&y==3)||(x==3&&y==3)
				||(x==6&&y==3)||(x==0&&y==6)||(x==3&&y==6)||(x==6&&y==6))
			caselles[i][j].setBorder(new MatteBorder(4, 4, 1, 1, (Color) new Color(0, 0, 0)));
		else if((x==8&&y==0)||(x==8&&y==3)||(x==8&&y==6))caselles[i][j].setBorder(new MatteBorder(1, 4, 4, 1, (Color) new Color(0, 0, 0)));
		else if((x==0&&y==8)||(x==3&&y==8)||(x==6&&y==8))caselles[i][j].setBorder(new MatteBorder(4, 1, 1, 4, (Color) new Color(0, 0, 0)));
		else if(x==8&&y==8)caselles[i][j].setBorder(new MatteBorder(1, 1, 4, 4, (Color) new Color(0, 0, 0)));
		else if(x==0||x==3||x==6)caselles[i][j].setBorder(new MatteBorder(4, 1, 1, 1, (Color) new Color(0, 0, 0)));
		else if(y==0||y==3||y==6)caselles[i][j].setBorder(new MatteBorder(1, 4, 1, 1, (Color) new Color(0, 0, 0)));
		else if(x==8)caselles[i][j].setBorder(new MatteBorder(1, 1, 4, 1, (Color) new Color(0, 0, 0)));
		else if(y==8)caselles[i][j].setBorder(new MatteBorder(1, 1, 1, 4, (Color) new Color(0, 0, 0)));
		else caselles[i][j].setBorder(new LineBorder(new Color(0, 0, 0)));
	}
}