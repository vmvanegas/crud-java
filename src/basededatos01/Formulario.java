package basededatos01;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Cursor;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import java.awt.ComponentOrientation;
import java.awt.Component;
import java.awt.Dimension;

public class Formulario extends JFrame {

	private JPanel contentPane;
	private JTextField tfnombre;
	private JTextField tfapellido;
	private JLabel labelResultado;
	private JButton btnConsultaPorCdigo;
	private JLabel lblIngreseCdigoDe;

	private JTextField tf3;
	private JTextField tfCorreo;
	private JTextField tfContrasea;
	private JTextField tfNombreTienda;
	private JTextField tfDireccion;
	private JTextField tfTelefono;
	private JButton btnModificar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Formulario frame = new Formulario();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	* Create the frame.
	*/
	public Formulario() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1084, 778);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		contentPane.setLayout(null);
		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setForeground(Color.DARK_GRAY);
		lblNombre.setFont(new Font("Lato", Font.PLAIN, 16));
		lblNombre.setBounds(622, 25, 193, 14);
		contentPane.add(lblNombre);
		
		tfnombre = new JTextField();
		tfnombre.setFont(new Font("Lato", Font.PLAIN, 16));
		tfnombre.setBounds(622, 50, 367, 39);
		contentPane.add(tfnombre);
		tfnombre.setColumns(10);
		
		JLabel lblPrecio = new JLabel("Apellido:");
		lblPrecio.setForeground(Color.DARK_GRAY);
		lblPrecio.setFont(new Font("Lato", Font.PLAIN, 16));
		lblPrecio.setBounds(622, 100, 95, 21);
		contentPane.add(lblPrecio);
		
		tfapellido = new JTextField();
		tfapellido.setFont(new Font("Lato", Font.PLAIN, 16));
		tfapellido.setBounds(622, 132, 367, 39);
		contentPane.add(tfapellido);
		tfapellido.setColumns(10);
		
		JButton btnAlta = new JButton("Ingresar");
		btnAlta.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAlta.setFont(new Font("Lato", Font.PLAIN, 16));
		btnAlta.setForeground(Color.WHITE);
		btnAlta.setBorder(null);
		btnAlta.setBackground(new Color(255, 69, 0));
		btnAlta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				labelResultado.setText("");
				try {
					Connection
					conexion=DriverManager.getConnection("jdbc:mysql://localhost/mispollos","root","");
					Statement comando=conexion.createStatement();
					
					UUID uuid = UUID.randomUUID();
					System.out.println(uuid);
					
					comando.executeUpdate("insert into tienda(id_tienda, nombre,telefono, direccion) values ('"+uuid+"','"+tfNombreTienda.getText()+"','"+tfTelefono.getText()+"','"+tfDireccion.getText()+"')");
					comando.executeUpdate("insert into usuario(nombre,apellido, correo, clave, id_rol, id_tienda) values ('"+tfnombre.getText()+"','"+tfapellido.getText()+"','"+tfCorreo.getText()+"','"+tfContrasea.getText()+"',"+ 1 +",'"+ uuid +"')");
					conexion.close();
					labelResultado.setText("se registraron los datos");
					tfnombre.setText("");
					tfapellido.setText("");
					tfCorreo.setText("");
					tfContrasea.setText("");
					tfNombreTienda.setText("");
					tfTelefono.setText("");
					tfDireccion.setText("");
				} catch(SQLException ex){
					setTitle(ex.toString());
					System.out.println(ex);
				}
			}
		});
					
		btnAlta.setBounds(900, 578, 89, 39);
		contentPane.add(btnAlta);
		
		labelResultado = new JLabel("resultado");
		labelResultado.setForeground(Color.DARK_GRAY);
		labelResultado.setFont(new Font("Lato", Font.PLAIN, 16));
		labelResultado.setBounds(622, 590, 229, 14);
		contentPane.add(labelResultado);
		
		btnConsultaPorCdigo = new JButton("Consulta por código");
		btnConsultaPorCdigo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnConsultaPorCdigo.setForeground(Color.WHITE);
		btnConsultaPorCdigo.setBorder(null);
		btnConsultaPorCdigo.setBackground(new Color(255, 69, 0));
		btnConsultaPorCdigo.setFont(new Font("Lato", Font.PLAIN, 16));
		btnConsultaPorCdigo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				
							
				labelResultado.setText("");
				tfnombre.setText("");
				tfapellido.setText("");
				tfCorreo.setText("");
				tfContrasea.setText("");
				tfNombreTienda.setText("");
				tfTelefono.setText("");
				tfDireccion.setText("");
				try {
					Connection
					conexion=DriverManager.getConnection("jdbc:mysql://localhost/mispollos","root" ,"");
					Statement comando=conexion.createStatement();
					
					ResultSet registro = comando.executeQuery("select u.nombre,u.apellido, u.correo, u.clave,t.nombre,t.telefono, t.direccion from usuario u join tienda t on u.id_tienda = t.id_tienda where id_usuario="+tf3.getText());
					if (registro.next()==true) {
						tfnombre.setText(registro.getString("u.nombre"));
						tfapellido.setText(registro.getString("u.apellido"));
						tfCorreo.setText(registro.getString("u.correo"));
						tfContrasea.setText(registro.getString("u.clave"));
						tfNombreTienda.setText(registro.getString("t.nombre"));
						tfTelefono.setText(registro.getString("t.telefono"));
						tfDireccion.setText(registro.getString("t.direccion"));
					} else {
						labelResultado.setText("No existe un artículo con dicho código");
					}
					conexion.close();
				} catch(SQLException ex){
					setTitle(ex.toString());
				}
			}
		});
		btnConsultaPorCdigo.setBounds(157, 116, 258, 39);
		contentPane.add(btnConsultaPorCdigo);
		
		lblIngreseCdigoDe = new JLabel("Ingrese código de articulo a consultar:");
		lblIngreseCdigoDe.setForeground(Color.DARK_GRAY);
		lblIngreseCdigoDe.setFont(new Font("Lato", Font.PLAIN, 16));
		lblIngreseCdigoDe.setBounds(157, 25, 270, 20);
		contentPane.add(lblIngreseCdigoDe);
		
		tf3 = new JTextField();
		tf3.setBounds(157, 56, 258, 39);
		contentPane.add(tf3);
		tf3.setColumns(10);
		
		tfCorreo = new JTextField();
		tfCorreo.setFont(new Font("Lato", Font.PLAIN, 16));
		tfCorreo.setColumns(10);
		tfCorreo.setBounds(622, 207, 367, 39);
		contentPane.add(tfCorreo);
		
		JLabel lblCorreo = new JLabel("Correo: ");
		lblCorreo.setForeground(Color.DARK_GRAY);
		lblCorreo.setFont(new Font("Lato", Font.PLAIN, 16));
		lblCorreo.setBounds(622, 182, 95, 14);
		contentPane.add(lblCorreo);
		
		tfContrasea = new JTextField();
		tfContrasea.setFont(new Font("Lato", Font.PLAIN, 16));
		tfContrasea.setColumns(10);
		tfContrasea.setBounds(622, 285, 367, 39);
		contentPane.add(tfContrasea);
		
		JLabel lblContrasea = new JLabel("Contrase\u00F1a");
		lblContrasea.setForeground(Color.DARK_GRAY);
		lblContrasea.setFont(new Font("Lato", Font.PLAIN, 16));
		lblContrasea.setBounds(622, 260, 95, 14);
		contentPane.add(lblContrasea);
		
		tfNombreTienda = new JTextField();
		tfNombreTienda.setFont(new Font("Lato", Font.PLAIN, 16));
		tfNombreTienda.setColumns(10);
		tfNombreTienda.setBounds(622, 360, 367, 39);
		contentPane.add(tfNombreTienda);
		
		JLabel lblNombreTienda = new JLabel("Nombre de la tienda");
		lblNombreTienda.setForeground(Color.DARK_GRAY);
		lblNombreTienda.setFont(new Font("Lato", Font.PLAIN, 16));
		lblNombreTienda.setBounds(622, 335, 177, 14);
		contentPane.add(lblNombreTienda);
		
		tfDireccion = new JTextField();
		tfDireccion.setFont(new Font("Lato", Font.PLAIN, 16));
		tfDireccion.setColumns(10);
		tfDireccion.setBounds(622, 436, 367, 39);
		contentPane.add(tfDireccion);
		
		JLabel lblDireccion = new JLabel("Direccion:");
		lblDireccion.setForeground(Color.DARK_GRAY);
		lblDireccion.setFont(new Font("Lato", Font.PLAIN, 16));
		lblDireccion.setBounds(622, 410, 95, 14);
		contentPane.add(lblDireccion);
		
		tfTelefono = new JTextField();
		tfTelefono.setFont(new Font("Lato", Font.PLAIN, 16));
		tfTelefono.setColumns(10);
		tfTelefono.setBounds(622, 509, 367, 39);
		contentPane.add(tfTelefono);
		
		JLabel lblTelefono = new JLabel("Telefono:");
		lblTelefono.setForeground(Color.DARK_GRAY);
		lblTelefono.setFont(new Font("Lato", Font.PLAIN, 16));
		lblTelefono.setBounds(622, 484, 95, 14);
		contentPane.add(lblTelefono);
		
		JButton btnEliminar = new JButton("Eliminar por c\u00F3digo");
		btnEliminar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				labelResultado.setText("");

				try {

					Connection conexion=DriverManager.getConnection("jdbc:mysql://localhost/mispollos","root" ,"");
	
					Statement comando=conexion.createStatement();
	
					int cantidad = comando.executeUpdate("delete from usuario where id_usuario="+tf3.getText());

				if (cantidad==1) {
					tf3.setText("");
					labelResultado.setText("");
					tfnombre.setText("");
					tfapellido.setText("");
					tfCorreo.setText("");
					tfContrasea.setText("");
					tfNombreTienda.setText("");
					tfTelefono.setText("");
					tfDireccion.setText("");
	
					labelResultado.setText("Se borro el artículo con dicho código");

				} else {

					labelResultado.setText("No existe un artículo con dicho código");

				}
				conexion.close();
				} catch(SQLException ex){
					setTitle(ex.toString());
				}
				
			}
		});
		btnEliminar.setForeground(Color.WHITE);
		btnEliminar.setFont(new Font("Lato", Font.PLAIN, 16));
		btnEliminar.setBorder(null);
		btnEliminar.setBackground(new Color(255, 69, 0));
		btnEliminar.setBounds(157, 214, 258, 39);
		contentPane.add(btnEliminar);
		
		btnModificar = new JButton("Modificar");
		btnModificar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				labelResultado.setText("");

				try {

				Connection conexion=DriverManager.getConnection("jdbc:mysql://localhost/mispollos","root" ,"");

				Statement comando=conexion.createStatement();

				int cantidad = comando.executeUpdate("update usuario set nombre='" + tfnombre.getText() + "'," + "apellido='" + tfapellido.getText() + "'," + "correo='" + tfCorreo.getText() + "'," + "clave='" + tfContrasea.getText() + "' where id_usuario="+tf3.getText());

				if (cantidad==1) {
					
					tf3.setText("");
					labelResultado.setText("");
					tfnombre.setText("");
					tfapellido.setText("");
					tfCorreo.setText("");
					tfContrasea.setText("");
					tfNombreTienda.setText("");
					tfTelefono.setText("");
					tfDireccion.setText("");

					labelResultado.setText("Se modifico la descripcion y el precio del artículo con dicho código");

				} else {

					labelResultado.setText("No existe un artículo con dicho código");

				}
				conexion.close();
				} catch(SQLException ex){
					setTitle(ex.toString());
				}
			}
		});
		btnModificar.setForeground(Color.WHITE);
		btnModificar.setFont(new Font("Lato", Font.PLAIN, 16));
		btnModificar.setBorder(null);
		btnModificar.setBackground(new Color(255, 69, 0));
		btnModificar.setBounds(157, 166, 258, 39);
		contentPane.add(btnModificar);
		cargarDriver();
	}

	private void cargarDriver() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception ex) {
			setTitle(ex.toString());
		}
	}
}