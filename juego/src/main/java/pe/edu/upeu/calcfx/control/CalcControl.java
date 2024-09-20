package pe.edu.upeu.calcfx.control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.springframework.stereotype.Component;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pe.edu.upeu.calcfx.modelo.Match;

@Component
public class CalcControl {

    @FXML
    private Button btn00, btn01, btn02, btn10, btn11, btn12, btn20, btn21, btn22;
    @FXML
    private Button btnIniciar, btnAnular;
    @FXML
    private TextField player1, player2;
    @FXML
    private Label lblTurno, lblMarcador;
    @FXML
    private TableView<Match> tableView;
    @FXML
    private TableColumn<Match, String> Nombrepartido;
    @FXML
    private TableColumn<Match, String> Nombrejugador1;
    @FXML
    private TableColumn<Match, String> Nombrejugador2;
    @FXML
    private TableColumn<Match, String> Ganador;
    @FXML
    private TableColumn<Match, String> Puntuacion;
    @FXML
    private TableColumn<Match, String> Estado;

    private Button[][] buttons;
    private ObservableList<Match> matches;
    private boolean turnox = true; // Variable para saber quién debe jugar
    private String jugador1Name;
    private String jugador2Name;
    private int scoreX = 0;
    private int scoreO = 0;

    @FXML
    public void initialize() {
        buttons = new Button[][]{
                {btn00, btn01, btn02},
                {btn10, btn11, btn12},
                {btn20, btn21, btn22}
        };

        // Inicializa la lista observable y la TableView
        matches = FXCollections.observableArrayList();
        tableView.setItems(matches);

        // Inicializa las columnas de la tabla
        Nombrepartido.setCellValueFactory(cellData -> cellData.getValue().partidoProperty());
        Nombrejugador1.setCellValueFactory(cellData -> cellData.getValue().jugador1Property());
        Nombrejugador2.setCellValueFactory(cellData -> cellData.getValue().jugador2Property());
        Ganador.setCellValueFactory(cellData -> cellData.getValue().ganadorProperty());
        Puntuacion.setCellValueFactory(cellData -> cellData.getValue().puntuacionProperty());
        Estado.setCellValueFactory(cellData -> cellData.getValue().estadoProperty());

        // Desactiva el tablero al inicio
        activaDesactiva(true);

        // Inicializa el marcador y el turno
        lblMarcador.setText("X: 0 - O: 0");
        lblTurno.setText("Turno: X");

        // Configura los botones
        btnIniciar.setDisable(false);
        btnAnular.setDisable(true);
    }

    @FXML
    public void accionButton(ActionEvent event) {
        Button button = (Button) event.getSource();
        if (button.getText().isEmpty()) {
            button.setText(turnox ? "X" : "O");
            comprobarGanador();
            turnox = !turnox;
            lblTurno.setText("Turno: " + (turnox ? "X" : "O"));
        }
    }

    private void marcarGanador() {
        String ganador = turnox ? player1.getText() : player2.getText();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Ganador");
        alert.setHeaderText("El ganador es: " + ganador);
        alert.showAndWait();

        // Actualiza el marcador
        if (turnox) {
            scoreX++;
        } else {
            scoreO++;
        }


        System.out.println("Score X: " + scoreX);
        System.out.println("Score O: " + scoreO);

        // Actualiza la etiqueta del marcador
        lblMarcador.setText("X: " + scoreX + " - O: " + scoreO);

        // Debugging - Verify score in the table
        System.out.println("Puntuacion para agregar: " + scoreX + " - " + scoreO);

        // Guarda el resultado en la lista y la tabla
        saveMatchResult(ganador, scoreX + " - " + scoreO, "Ganado");

        // Desactiva los botones y activa el botón Iniciar
        anular();
        btnIniciar.setDisable(false);
        btnAnular.setDisable(true);
    }

    private void comprobarGanador() {
        // Comprobar filas
        for (int i = 0; i < 3; i++) {
            if (buttons[i][0].getText().equals(buttons[i][1].getText()) &&
                    buttons[i][1].getText().equals(buttons[i][2].getText()) &&
                    !buttons[i][0].getText().isEmpty()) {
                marcarGanador();
                return;
            }
        }

        // Comprobar columnas
        for (int j = 0; j < 3; j++) {
            if (buttons[0][j].getText().equals(buttons[1][j].getText()) &&
                    buttons[1][j].getText().equals(buttons[2][j].getText()) &&
                    !buttons[0][j].getText().isEmpty()) {
                marcarGanador();
                return;
            }
        }

        // Comprobar diagonales
        if (buttons[0][0].getText().equals(buttons[1][1].getText()) &&
                buttons[1][1].getText().equals(buttons[2][2].getText()) &&
                !buttons[0][0].getText().isEmpty()) {
            marcarGanador();
            return;
        }

        if (buttons[0][2].getText().equals(buttons[1][1].getText()) &&
                buttons[1][1].getText().equals(buttons[2][0].getText()) &&
                !buttons[0][2].getText().isEmpty()) {
            marcarGanador();
            return;
        }
    }

    @FXML
    public void iniciar() {
        jugador1Name = player1.getText();
        jugador2Name = player2.getText();

        if (jugador1Name.isEmpty() || jugador2Name.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Ambos jugadores deben tener un nombre.");
            alert.showAndWait();
            return;
        }

        // funcion iniciar y anular
        activaDesactiva(false);
        btnIniciar.setDisable(true);
        btnAnular.setDisable(false);
        lblTurno.setText("Turno: X"); // Restablece el turno al inicio
    }

    @FXML
    public void anular() {
        // desactiva el juego
        activaDesactiva(true);
        for (Button[] row : buttons) {
            for (Button button : row) {
                button.setText("");
                button.setStyle(""); // Limpia el estilo de fondo
            }
        }
        turnox = true;
        lblTurno.setText("Turno: X");
    }

    private void saveMatchResult(String ganador, String puntuacion, String estado) {
        Match match = new Match(jugador1Name, jugador2Name, ganador, puntuacion);
        match.setEstado(estado);

        // Añade el resultado a la lista observable
        matches.add(match);
    }

    private void activaDesactiva(boolean indi) {
        for (Button[] row : buttons) {
            for (Button button : row) {
                button.setDisable(indi);
            }
        }
    }
}
