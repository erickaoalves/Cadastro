package farmacia;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class Cadastro extends JFrame {

	private JTextField campoNome;
	private JTextField campoUsuario; // JTextField é campo de texto;
	private JPasswordField campoSenha;
	private JPasswordField campoConfirmaSenha;
	private JButton botaoCadastrar;

	// Conexão com o banco de dados

	private final String DB_URL = "jdbc:mysql://localhost:3306/farmacia";
	private final String DB_USER = "root";
	private final String DB_PASSWORD = "erick";

	// Criar método de construção

	public Cadastro() {

		setTitle("Cadastro de usuário");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(400, 250);

		JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10)); // cria uma painel Java
		campoNome = new JTextField(); // objeto javaTField do campo nome
		campoUsuario = new JTextField();
		campoSenha = new JPasswordField();
		campoConfirmaSenha = new JPasswordField();
		botaoCadastrar = new JButton("Cadastrar");

		panel.add(new JLabel("Nome:")); // adiciona os objetos criados anteriormente dentro do painel Java
		panel.add(campoNome);
		panel.add(new JLabel("Usuário:"));
		panel.add(campoUsuario);
		panel.add(new JLabel("Senha:"));
		panel.add(campoSenha);
		panel.add(new JLabel("Confirme sua senha:"));
		panel.add(campoConfirmaSenha);
		panel.add(botaoCadastrar);

		add(panel);
		botaoCadastrar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String nome = campoNome.getText();
				String email = campoUsuario.getText();
				String senha = new String(campoSenha.getPassword());
				String confirmarSenha = new String(campoConfirmaSenha.getPassword());

				if (!senha.equals(confirmarSenha)) {
					JOptionPane.showMessageDialog(null, "Favor verificar se as senhas coincidem.", "Erro",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				try {
					Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
					String query = "INSERT INTO usuario(nome, email, senha) VALUES(?,?,?)";
					PreparedStatement preparedStatement = connection.prepareStatement(query);
					preparedStatement.setString(1, nome);
					preparedStatement.setString(2, email);
					preparedStatement.setString(3, senha);
					preparedStatement.executeUpdate();

					JOptionPane.showMessageDialog(null, "Cadastro realizado com sucesso!");
					preparedStatement.close();

				} catch (Exception e2) {

				}
			}
		});
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			Cadastro cadastro = new Cadastro();
			cadastro.setVisible(true);
		});
	}
}