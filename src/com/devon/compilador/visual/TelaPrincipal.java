package com.devon.compilador.visual;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

public class TelaPrincipal {

	private JFrame frame;
	private JTextPane txtEditor = new JTextPane();
	private JTextPane txtMensagens = new JTextPane();
	private JLabel lblStatus = new JLabel("lblStatus");
	private JFileChooser fileChooser = new JFileChooser();

	private File arquivo = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
					TelaPrincipal window = new TelaPrincipal();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TelaPrincipal() {
		initialize();

		resetar();
	}

	private void resetar() {
		txtEditor.setText("");
		txtMensagens.setText("");
		lblStatus.setText("");

		arquivo = null;
	}

	public void escolherArquivo() {
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Arquivos Texto", "txt");
		fileChooser.setFileFilter(filter);
		int returnVal = fileChooser.showOpenDialog(this.frame);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			arquivo = fileChooser.getSelectedFile();
			try {
				FileInputStream fi = new FileInputStream(arquivo);
				InputStreamReader isr = new InputStreamReader(fi, Charset.forName("UTF-8"));
				BufferedReader bf = new BufferedReader(isr);

				String texto = "";
				StringBuilder sb = new StringBuilder();
				while ((texto = bf.readLine()) != null) {
					sb.append(texto + "\n");
				}

				txtEditor.setText(sb.toString());
				lblStatus.setText(arquivo.getAbsolutePath());
			} catch (IOException ex) {
				System.out.println("Problemas ao acessar o arquivo" + arquivo.getAbsolutePath());
			}

		} else {
			System.out.println("Escolha de arquivo cancelada pelo usuário.");
		}

	}

	private void salvar() {
		JFileChooser fcSalvar = new JFileChooser();
		int userSelection = 99;
		if(arquivo == null) {
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Arquivos Texto", "txt");
			fcSalvar.setFileFilter(filter);
			fcSalvar.setDialogTitle("Salvar");
			userSelection = fcSalvar.showSaveDialog(this.frame);
		}

		if (userSelection == JFileChooser.APPROVE_OPTION && arquivo == null) {
			File fileToSave = fcSalvar.getSelectedFile();
			System.out.println(fileToSave.getName());
			System.out.println("Salvo: " + fileToSave.getAbsolutePath());

			arquivo = new File(fileToSave.getAbsolutePath() + ".txt");
		} 
		
		FileWriter fr = null;
		try {
			fr = new FileWriter(arquivo);
			fr.write(txtEditor.getText());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void copiar() {
		txtEditor.copy();
	}

	private void colar() {
		txtEditor.paste();
	}

	private void recortar() {
		txtEditor.cut();
	}

	private void compilar() {
		txtMensagens.setText(txtMensagens.getText() + "Compilação de programas ainda não foi implementada \n");
	}

	private void equipe() {
		txtMensagens.setText(txtMensagens.getText() + "Devon Barth Schvabe \n");
	}

	private ImageIcon getIcone(String imagem) {
		String caminho = "";
		try {
			caminho = new File(imagem).getCanonicalPath();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		ImageIcon im = new ImageIcon(caminho);

		Image img = im.getImage();
		Image newimg = img.getScaledInstance(24, 24, java.awt.Image.SCALE_SMOOTH);
		im = new ImageIcon(newimg);
		return im;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setMinimumSize(new Dimension(900, 600));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 0));

		JPanel pnlFerramentas = new JPanel();
		panel.add(pnlFerramentas, BorderLayout.CENTER);
		pnlFerramentas.setSize(900, 100);
		GridBagLayout gbl_pnlFerramentas = new GridBagLayout();
		gbl_pnlFerramentas.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_pnlFerramentas.rowHeights = new int[] { 0, 0 };
		gbl_pnlFerramentas.columnWeights = new double[] { 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE };
		gbl_pnlFerramentas.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		pnlFerramentas.setLayout(gbl_pnlFerramentas);

		JButton btnNovo = new JButton("Novo [ctrl+n]");
		btnNovo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetar();
			}
		});

		GridBagConstraints gbc_btnNovo = new GridBagConstraints();
		gbc_btnNovo.fill = GridBagConstraints.BOTH;
		gbc_btnNovo.insets = new Insets(0, 0, 0, 5);
		gbc_btnNovo.gridx = 0;
		gbc_btnNovo.gridy = 0;
		btnNovo.setVerticalTextPosition(AbstractButton.BOTTOM);
		btnNovo.setHorizontalTextPosition(AbstractButton.CENTER);
		btnNovo.setPreferredSize(new Dimension(100, 70));
		pnlFerramentas.add(btnNovo, gbc_btnNovo);

		Action novoAction = new AbstractAction("Novo [ctrl+n]") {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("novo");
				resetar();
			}
		};
		novoAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));
		novoAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);
		btnNovo.getActionMap().put("acaoNovo", novoAction);
		btnNovo.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
				.put((KeyStroke) novoAction.getValue(Action.ACCELERATOR_KEY), "acaoNovo");

		ImageIcon imgNovo = getIcone("assets/novo.png");
		btnNovo.setIcon(imgNovo);

		JButton btnAbrir = new JButton("Abrir [ctrl+o]");
		btnAbrir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				escolherArquivo();
			}
		});
		GridBagConstraints gbc_btnAbrir = new GridBagConstraints();
		gbc_btnAbrir.fill = GridBagConstraints.BOTH;
		gbc_btnAbrir.insets = new Insets(0, 0, 0, 5);
		gbc_btnAbrir.gridx = 1;
		gbc_btnAbrir.gridy = 0;
		btnAbrir.setVerticalTextPosition(AbstractButton.BOTTOM);
		btnAbrir.setHorizontalTextPosition(AbstractButton.CENTER);
		btnAbrir.setPreferredSize(new Dimension(100, 70));
		pnlFerramentas.add(btnAbrir, gbc_btnAbrir);

		Action abrirAction = new AbstractAction("acaoAbrir") {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("abrir");
				escolherArquivo();
			}
		};
		abrirAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		abrirAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);
		btnNovo.getActionMap().put("acaoAbrir", abrirAction);
		btnNovo.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
				.put((KeyStroke) abrirAction.getValue(Action.ACCELERATOR_KEY), "acaoAbrir");

		ImageIcon imgAbrir = getIcone("assets/abrir.png");
		btnAbrir.setIcon(imgAbrir);

		JButton btnSalvar = new JButton("Salvar [ctrl+s]");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				salvar();
			}
		});
		GridBagConstraints gbc_btnSalvar = new GridBagConstraints();
		gbc_btnSalvar.fill = GridBagConstraints.BOTH;
		gbc_btnSalvar.insets = new Insets(0, 0, 0, 5);
		gbc_btnSalvar.gridx = 2;
		gbc_btnSalvar.gridy = 0;
		btnSalvar.setVerticalTextPosition(AbstractButton.BOTTOM);
		btnSalvar.setHorizontalTextPosition(AbstractButton.CENTER);
		btnSalvar.setPreferredSize(new Dimension(100, 70));
		pnlFerramentas.add(btnSalvar, gbc_btnSalvar);

		Action salvarAction = new AbstractAction("salvarAction") {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("salvar");
				salvar();
			}
		};
		salvarAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		salvarAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);
		btnSalvar.getActionMap().put("salvarAction", salvarAction);
		btnSalvar.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
				.put((KeyStroke) salvarAction.getValue(Action.ACCELERATOR_KEY), "salvarAction");

		ImageIcon imgSalvar = getIcone("assets/salvar.png");
		btnSalvar.setIcon(imgSalvar);

		JButton btnCopiar = new JButton("Copiar [ctrl+c]");
		btnCopiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				copiar();
			}
		});
		GridBagConstraints gbc_btnCopiar = new GridBagConstraints();
		gbc_btnCopiar.fill = GridBagConstraints.BOTH;
		gbc_btnCopiar.insets = new Insets(0, 0, 0, 5);
		gbc_btnCopiar.gridx = 3;
		gbc_btnCopiar.gridy = 0;
		btnCopiar.setVerticalTextPosition(AbstractButton.BOTTOM);
		btnCopiar.setHorizontalTextPosition(AbstractButton.CENTER);
		btnCopiar.setPreferredSize(new Dimension(100, 70));
		pnlFerramentas.add(btnCopiar, gbc_btnCopiar);

		Action copiarAction = new AbstractAction("copiarAction") {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("copiar");
				copiar();
			}
		};
		copiarAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
		copiarAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);
		btnCopiar.getActionMap().put("copiarAction", copiarAction);
		btnCopiar.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
				.put((KeyStroke) copiarAction.getValue(Action.ACCELERATOR_KEY), "copiarAction");

		ImageIcon imgCopiar = getIcone("assets/copiar.png");
		btnCopiar.setIcon(imgCopiar);

		JButton btnColar = new JButton("Colar [ctrl+v]");
		btnColar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				colar();
			}
		});
		GridBagConstraints gbc_btnColar = new GridBagConstraints();
		gbc_btnColar.fill = GridBagConstraints.BOTH;
		gbc_btnColar.insets = new Insets(0, 0, 0, 5);
		gbc_btnColar.gridx = 4;
		gbc_btnColar.gridy = 0;
		btnColar.setVerticalTextPosition(AbstractButton.BOTTOM);
		btnColar.setHorizontalTextPosition(AbstractButton.CENTER);
		btnColar.setPreferredSize(new Dimension(100, 70));
		pnlFerramentas.add(btnColar, gbc_btnColar);

		Action colarAction = new AbstractAction("colarAction") {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("colar");
				colar();
			}
		};
		colarAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control V"));
		colarAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);
		btnColar.getActionMap().put("colarAction", colarAction);
		btnColar.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
				.put((KeyStroke) colarAction.getValue(Action.ACCELERATOR_KEY), "colarAction");

		ImageIcon imgColar = getIcone("assets/colar.png");
		btnColar.setIcon(imgColar);

		JButton btnRecortar = new JButton("Recortar [ctrl+x]");
		btnRecortar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				recortar();
			}
		});
		GridBagConstraints gbc_btnRecortar = new GridBagConstraints();
		gbc_btnRecortar.fill = GridBagConstraints.BOTH;
		gbc_btnRecortar.insets = new Insets(0, 0, 0, 5);
		gbc_btnRecortar.gridx = 5;
		gbc_btnRecortar.gridy = 0;
		btnRecortar.setVerticalTextPosition(AbstractButton.BOTTOM);
		btnRecortar.setHorizontalTextPosition(AbstractButton.CENTER);
		btnRecortar.setPreferredSize(new Dimension(100, 70));
		pnlFerramentas.add(btnRecortar, gbc_btnRecortar);

		Action recortarAction = new AbstractAction("recortarAction") {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("recortar");
				recortar();
			}
		};
		recortarAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
		recortarAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);
		btnRecortar.getActionMap().put("recortarAction", recortarAction);
		btnRecortar.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
				.put((KeyStroke) recortarAction.getValue(Action.ACCELERATOR_KEY), "recortarAction");

		ImageIcon imgRecortar = getIcone("assets/recortar.png");
		btnRecortar.setIcon(imgRecortar);

		JButton btnCompilar = new JButton("Compilar [F9]");
		btnCompilar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				compilar();
			}
		});
		GridBagConstraints gbc_btnCompilar = new GridBagConstraints();
		gbc_btnCompilar.fill = GridBagConstraints.BOTH;
		gbc_btnCompilar.insets = new Insets(0, 0, 0, 5);
		gbc_btnCompilar.gridx = 6;
		gbc_btnCompilar.gridy = 0;
		btnCompilar.setVerticalTextPosition(AbstractButton.BOTTOM);
		btnCompilar.setHorizontalTextPosition(AbstractButton.CENTER);
		btnCompilar.setPreferredSize(new Dimension(100, 70));
		pnlFerramentas.add(btnCompilar, gbc_btnCompilar);

		Action compilarAction = new AbstractAction("compilarAction") {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("compilar");
				compilar();
			}
		};
		compilarAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("F9"));
		compilarAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);
		btnCompilar.getActionMap().put("compilarAction", compilarAction);
		btnCompilar.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
				.put((KeyStroke) compilarAction.getValue(Action.ACCELERATOR_KEY), "compilarAction");

		ImageIcon imgCompilar = getIcone("assets/compilar.png");
		btnCompilar.setIcon(imgCompilar);

		JButton btnEquipe = new JButton("Equipe [F1]");
		btnEquipe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				equipe();
			}
		});
		GridBagConstraints gbc_btnEquipe = new GridBagConstraints();
		gbc_btnEquipe.fill = GridBagConstraints.BOTH;
		gbc_btnEquipe.gridx = 7;
		gbc_btnEquipe.gridy = 0;
		btnEquipe.setVerticalTextPosition(AbstractButton.BOTTOM);
		btnEquipe.setHorizontalTextPosition(AbstractButton.CENTER);
		btnEquipe.setPreferredSize(new Dimension(100, 70));
		pnlFerramentas.add(btnEquipe, gbc_btnEquipe);

		Action equipeAction = new AbstractAction("equipeAction") {

			@Override
			public void actionPerformed(ActionEvent e) {
				equipe();
			}
		};
		equipeAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("F1"));
		equipeAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);
		btnEquipe.getActionMap().put("equipeAction", equipeAction);
		btnEquipe.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
				.put((KeyStroke) equipeAction.getValue(Action.ACCELERATOR_KEY), "equipeAction");

		ImageIcon imgTime = getIcone("assets/time.png");
		btnEquipe.setIcon(imgTime);

		JPanel pnlEditor = new JPanel();
		frame.getContentPane().add(pnlEditor, BorderLayout.CENTER);
		GridBagLayout gbl_pnlEditor = new GridBagLayout();
		gbl_pnlEditor.columnWidths = new int[] { 420, 0 };
		gbl_pnlEditor.rowHeights = new int[] { 20, 0 };
		gbl_pnlEditor.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_pnlEditor.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		pnlEditor.setLayout(gbl_pnlEditor);

		JScrollPane jsp = new JScrollPane(txtEditor);
		jsp.setPreferredSize(new Dimension(900, 400));
		jsp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		jsp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

		txtEditor.setText("");
		GridBagConstraints gbc_txtEditor = new GridBagConstraints();
		gbc_txtEditor.fill = GridBagConstraints.BOTH;
		gbc_txtEditor.gridx = 0;
		gbc_txtEditor.gridy = 0;
		pnlEditor.add(jsp, gbc_txtEditor);

		JPanel pnlRodape = new JPanel();
		frame.getContentPane().add(pnlRodape, BorderLayout.SOUTH);
		GridBagLayout gbl_pnlRodape = new GridBagLayout();
		gbl_pnlRodape.columnWidths = new int[] { 0, 0 };
		gbl_pnlRodape.rowHeights = new int[] { 0, 0, 0 };
		gbl_pnlRodape.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_pnlRodape.rowWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		pnlRodape.setLayout(gbl_pnlRodape);

		JPanel pnlMensagens = new JPanel();
		GridBagConstraints gbc_pnlMensagens = new GridBagConstraints();
		pnlMensagens.setMinimumSize(new Dimension(900, 100));
		gbc_pnlMensagens.insets = new Insets(0, 0, 5, 0);
		gbc_pnlMensagens.fill = GridBagConstraints.BOTH;
		gbc_pnlMensagens.gridx = 0;
		gbc_pnlMensagens.gridy = 0;
		pnlRodape.add(pnlMensagens, gbc_pnlMensagens);

		JPanel pnlStatus = new JPanel();
		pnlStatus.setPreferredSize(new Dimension(900, 30));
		GridBagConstraints gbc_pnlStatus = new GridBagConstraints();
		gbc_pnlStatus.anchor = GridBagConstraints.WEST;
		pnlMensagens.setMinimumSize(new Dimension(900, 30));
		GridBagLayout gbl_pnlMensagens = new GridBagLayout();
		gbl_pnlMensagens.columnWidths = new int[] { 63, 0 };
		gbl_pnlMensagens.rowHeights = new int[] { 39, 0 };
		gbl_pnlMensagens.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_pnlMensagens.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		pnlMensagens.setLayout(gbl_pnlMensagens);

		JScrollPane jpsMensagens = new JScrollPane(txtMensagens);

		jpsMensagens.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		jpsMensagens.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		jpsMensagens.setPreferredSize(new Dimension(900, 100));
		GridBagConstraints gbc_jpsMensagens = new GridBagConstraints();
		gbc_jpsMensagens.fill = GridBagConstraints.BOTH;
		gbc_jpsMensagens.gridx = 0;
		gbc_jpsMensagens.gridy = 0;
		pnlMensagens.add(jpsMensagens, gbc_jpsMensagens);

		txtMensagens.setText("tpEditor");
		gbc_pnlStatus.fill = GridBagConstraints.VERTICAL;
		gbc_pnlStatus.gridx = 0;
		gbc_pnlStatus.gridy = 1;
		pnlRodape.add(pnlStatus, gbc_pnlStatus);
		GridBagLayout gbl_pnlStatus = new GridBagLayout();
		gbl_pnlStatus.columnWidths = new int[] { 41, 0 };
		gbl_pnlStatus.rowHeights = new int[] { 14, 0 };
		gbl_pnlStatus.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gbl_pnlStatus.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		pnlStatus.setLayout(gbl_pnlStatus);

		GridBagConstraints gbc_lblLblstatus = new GridBagConstraints();
		gbc_lblLblstatus.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblLblstatus.anchor = GridBagConstraints.NORTH;
		gbc_lblLblstatus.gridx = 0;
		gbc_lblLblstatus.gridy = 0;
		pnlStatus.add(lblStatus, gbc_lblLblstatus);

		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

	}

}
