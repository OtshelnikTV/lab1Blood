package com.example.lab4.view;

import com.example.lab4.AnimalsApplication;
import com.example.lab4.Baza.Lions;
import com.example.lab4.Baza.Wolves;
import com.example.lab4.Collections.Collections;
import com.example.lab4.core.Client;
import com.example.lab4.core.ClientCallback;
import com.example.lab4.core.Habitat;
import com.example.lab4.core.Updater;
import com.example.lab4.core.ai.LionsAI;
import com.example.lab4.core.ai.WolvesAI;
import com.example.lab4.Baza.Animals;
import com.example.lab4.model.IAnimalsPresent;
import com.example.lab4.model.AnimalsPresent;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;


import java.io.*;
import java.util.*;

public class Controller implements IController,Command, ClientCallback {

    public static final int FPS = 140;
    private final IAnimalsPresent presenter = new AnimalsPresent(this);
    private Timer timer;
    private final Stage stage;

    @FXML
    private Pane pane;
    @FXML
    private Button startButton;
    @FXML
    private Button stopButton;
    @FXML
    private CheckBox showInfoCheckBox;
    @FXML
    private RadioButton showTimeRadio;
    @FXML
    private RadioButton hideTimeRadio;
    @FXML
    private TextField LionsIntervalTextField;
    @FXML
    private TextField WolvesIntervalTextField;
    @FXML
    private TextField WolvesLifeTimeTextField;
    @FXML
    private Button consoleButton;
    @FXML
    private MenuItem loadObjectsMenuItem;
    @FXML
    private MenuItem saveObjectsMenuItem;
    @FXML
    private TextField LionsLifeTimeTextField;
    @FXML
    private Slider LionsProbabilitySlider;
    @FXML
    private Slider WolvesProbabilitySlider;
    @FXML
    private Button currentObjectsButton;
    @FXML
    private MenuItem startMenuItem;
    @FXML
    private MenuItem stopMenuItem;
    @FXML
    private CheckBox LionsAICheckBox;
    @FXML
    private CheckBox WolvesAICheckBox;
    @FXML
    private CheckBox WolvesSpawnCheckBox;
    @FXML
    private ChoiceBox<Integer> wolvesPriorityChoiceBox;
    @FXML
    private ComboBox<Integer> LionsPriorityComboBox;
    private WolvesAI wolvesAI;
    private LionsAI lionsAI;
    @FXML
    private ListView clientsListView;
    @FXML
    Button BtnGetClientAnimals;


    private Console consoleController = new Console(this);
    private final ToggleGroup radioGroup = new ToggleGroup();
    private final Text simulationTimeText = new Text();
    private final ImageView backgroundImage = new ImageView(System.getProperty("user.dir") + "\\src\\main\\resources\\assets\\Show.png");
    private final ObservableList<Integer> priorities = FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    public Controller(Stage stage){this.stage=stage;}
    private boolean isLoaded = false;
    public void changeWolvesPriority(int priority) {
        wolvesAI.setAIPriority(priority);
    }

    public void changeLionsPriority(int priority) {
        lionsAI.setAIPriority(priority);
    }
    private final Client client = new Client(this);
    @FXML
    private void initialize() {
        System.out.println("Загрузка конфига...");
        client.start();
        loadProperties();
        setupViews();
        setListeners();
    }

    @Override
    public void setClients(String[] clientsNames) {
        Platform.runLater(() -> {
            clientsListView.setItems(FXCollections.observableArrayList(clientsNames));

            clientsListView.setOnMouseClicked(e -> {
                String name = (String) clientsListView.getSelectionModel().getSelectedItem();
                System.out.println("Отправлено к : " + name);
                client.sendObjects(name,
                        LionsIntervalTextField.getText(),
                        WolvesIntervalTextField.getText(),
                        LionsProbabilitySlider.getValue(),
                        WolvesProbabilitySlider.getValue(),
                        WolvesLifeTimeTextField.getText(),
                        LionsLifeTimeTextField.getText(),
                        Collections.getInstance().getArray());
            });

        });
    }
    @Override
    public void setParametersFromClient(String lionsLifeTime, String lionsInterval, double lionsProbability, String wolvesLifeTime, String wolvesInterval, double wolvesProbability) {
        Platform.runLater(() -> {
            LionsIntervalTextField.setText(lionsInterval);
            WolvesIntervalTextField.setText(wolvesInterval);
            LionsProbabilitySlider.setValue(lionsProbability);
            WolvesProbabilitySlider.setValue(wolvesProbability);
            LionsLifeTimeTextField.setText(lionsLifeTime);
            WolvesLifeTimeTextField.setText(wolvesLifeTime);
        });
    }

    @Override
    public void showFetchedMessage(String message) {
        consoleController.addMessage(message);
    }
    public  Pane getMainPane() {
        return pane;
    }
    private void setupViews() {
        simulationTimeText.setFont(Font.font("comic sans ms", 20));
        simulationTimeText.setFill(Color.RED);
        simulationTimeText.setX(50);
        simulationTimeText.setY(50);

        stopButton.setDisable(true);

        showTimeRadio.setToggleGroup(radioGroup);
        hideTimeRadio.setToggleGroup(radioGroup);
        showTimeRadio.fire();

        LionsAICheckBox.setDisable(true);
        WolvesAICheckBox.setDisable(true);
        WolvesSpawnCheckBox.setDisable(true);

        saveObjectsMenuItem.setDisable(true);

        wolvesPriorityChoiceBox.setItems(priorities);
        LionsPriorityComboBox.setItems(priorities);
    }


    private void setListeners() {
        startButton.setOnMouseClicked(mouseEvent -> startSimulation());

        stopButton.setOnMouseClicked(mouseEvent -> stopSimulation());

        radioGroup.selectedToggleProperty().addListener((changed, oldValue, newValue) -> {
            RadioButton selectedRadio = (RadioButton) newValue;
            simulationTimeText.setVisible(selectedRadio.getId().equals("showTimeRadio"));
        });

        startMenuItem.setOnAction(actionEvent -> startSimulation());

        stopMenuItem.setOnAction(actionEvent -> stopSimulation());
        consoleButton.setOnAction(actionEvent -> openConsole());
        loadObjectsMenuItem.setOnAction(actionEvent -> loadObjects());

        saveObjectsMenuItem.setOnAction(actionEvent -> saveObjects());

        currentObjectsButton.setOnAction(actionEvent -> showCurrentObjects());


        LionsIntervalTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!presenter.checkInput(newValue)) {
                LionsIntervalTextField.setText(oldValue);
            }
        });

        WolvesIntervalTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!presenter.checkInput(newValue)) {
                WolvesIntervalTextField.setText(oldValue);
            }
        });

        WolvesLifeTimeTextField.textProperty().addListener(((observableValue, oldValue, newValue) -> {
            if (!presenter.checkInput(newValue)) {
                WolvesLifeTimeTextField.setText(oldValue);
            }
        }));

        LionsLifeTimeTextField.textProperty().addListener(((observableValue, oldValue, newValue) -> {
            if (!presenter.checkInput(newValue)) {
                LionsLifeTimeTextField.setText(oldValue);
            }
        }));

        WolvesAICheckBox.selectedProperty().addListener((observableValue, oldValue, newValue) -> {
            presenter.setWolvesAIEnable(newValue);
        });
        WolvesSpawnCheckBox.selectedProperty().addListener((observableValue, oldValue, newValue) -> {
            if(WolvesSpawnCheckBox.isSelected()){
                presenter.setParams(
                        LionsIntervalTextField.getText(),
                        WolvesIntervalTextField.getText(),
                        LionsProbabilitySlider.getValue(),
                        WolvesProbabilitySlider.getValue(),
                        WolvesLifeTimeTextField.getText(),
                        LionsLifeTimeTextField.getText());
            }
            else{

                presenter.setParams(
                        LionsIntervalTextField.getText(),
                        WolvesIntervalTextField.getText(),
                        LionsProbabilitySlider.getValue(),
                        0,
                        WolvesLifeTimeTextField.getText(),
                        LionsLifeTimeTextField.getText());
            }
        });

       LionsAICheckBox.selectedProperty().addListener((observableValue, oldValue, newValue) -> {
            presenter.setLionsAIEnable(newValue);
        });
        wolvesPriorityChoiceBox.setOnAction(e -> {
            int selectedPriority = wolvesPriorityChoiceBox.getSelectionModel().getSelectedItem();
            System.out.println(selectedPriority);
            presenter.setWolvesAIPriority(selectedPriority);
        });

        LionsPriorityComboBox.setOnAction(e -> {
            int selectedPriority = LionsPriorityComboBox.getSelectionModel().getSelectedItem();
            System.out.println(selectedPriority);
            presenter.setLionsAIPriority(selectedPriority);
        });
    }

    @FXML
    private void onKeyPressed(KeyEvent event) {
        switch (event.getCode()) {
            case B -> startSimulation();
            case E -> stopSimulation();
            case T -> {
                if (simulationTimeText.isVisible()) {
                    hideTimeRadio.fire();
                } else {
                    showTimeRadio.fire();
                }
            }
        }
    }
    public void setWolvesSpawnAvailable(Boolean isEnable){
        if (isEnable) {
            presenter.setParams(
                    LionsIntervalTextField.getText(),
                    WolvesIntervalTextField.getText(),
                    LionsProbabilitySlider.getValue(),
                    0,
                    WolvesLifeTimeTextField.getText(),
                    LionsLifeTimeTextField.getText());
        } else {
            if (!isEnable ) {
                presenter.setParams(
                        LionsIntervalTextField.getText(),
                        WolvesIntervalTextField.getText(),
                        LionsProbabilitySlider.getValue(),
                        WolvesProbabilitySlider.getValue(),
                        WolvesLifeTimeTextField.getText(),
                        LionsLifeTimeTextField.getText());
            }
        }
    }
    private void openConsole() {
        try {
            FXMLLoader loader = new FXMLLoader(AnimalsApplication.class.getResource("console.fxml"));
            loader.setController(consoleController);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage dialog = new Stage();
            dialog.setScene(scene);
            dialog.initModality(Modality.NONE);
            dialog.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private ArrayList<Animals> animalList;
    @Override
    public void Command(String command) {

        //обрабатываем команды
        if (command.contains("wolves_off")) {
            try {
                    consoleController.addMessage("Wolves birth off \n");
                   WolvesSpawnCheckBox.setSelected(false);
                presenter.setParams(
                        LionsIntervalTextField.getText(),
                        WolvesIntervalTextField.getText(),
                        LionsProbabilitySlider.getValue(),
                       0,
                        WolvesLifeTimeTextField.getText(),
                        LionsLifeTimeTextField.getText());
            } catch (NumberFormatException e) {
                consoleController.addMessage("Exception - wrong number\n");
            }
        }
        else if (command.contains("wolves_on")) {
            try {
                WolvesSpawnCheckBox.setSelected(true);
                consoleController.addMessage("Wolves birth on \n");
                //presenter.decreaseAlbinoRabbits(percent);
                presenter.setParams(
                        LionsIntervalTextField.getText(),
                        WolvesIntervalTextField.getText(),
                        LionsProbabilitySlider.getValue(),
                        LionsProbabilitySlider.getValue(),
                        WolvesLifeTimeTextField.getText(),
                        LionsLifeTimeTextField.getText());
            } catch (NumberFormatException e) {
                consoleController.addMessage("Exception - wrong number\n");
            }
        } else if (command.contains("message")) {
            String message = command.replace("message", "").trim();
            String name = client.getClientName();
            client.sendMessage(name, message, animalList);
        }
        else {
            consoleController.addMessage("Unknown command: " + command + "\n");
        }
    }
    public void loadProperties() {

        try {
            Properties simulationProps = new Properties();
            simulationProps.load(new FileInputStream(System.getProperty("user.dir") + "\\src\\config.properties"));
            if (simulationProps.isEmpty()) return;

            String lionsInterval = simulationProps.getProperty("lionsInterval");
            String wolvesInterval = simulationProps.getProperty("wolvesInterval");
            String lionsProbability = simulationProps.getProperty("lionsProbability");
            String wolvesProbability = simulationProps.getProperty("wolvesProbability");
            String lionsLifeTime = simulationProps.getProperty("lionsLifeTime");
            String wolvesLifeTime = simulationProps.getProperty("wolvesLifeTime");

            LionsIntervalTextField.setText(lionsInterval);
            WolvesIntervalTextField.setText(wolvesInterval);
            LionsProbabilitySlider.setValue(Double.parseDouble(lionsProbability));
            WolvesProbabilitySlider.setValue(Double.parseDouble(wolvesProbability));
            LionsLifeTimeTextField.setText(lionsLifeTime);
            WolvesLifeTimeTextField.setText(wolvesLifeTime);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveProperties() {
        try {
            Properties simulationProps = new Properties();
            simulationProps.load(new FileInputStream(System.getProperty("user.dir") + "\\src\\config.properties"));
            simulationProps.setProperty("lionsInterval", LionsIntervalTextField.getText());
            simulationProps.setProperty("wolvesInterval", WolvesIntervalTextField.getText());
            simulationProps.setProperty("lionsProbability", String.valueOf(LionsProbabilitySlider.getValue()));
            simulationProps.setProperty("wolvesProbability", String.valueOf(WolvesProbabilitySlider.getValue()));
            simulationProps.setProperty("lionsLifeTime", String.valueOf(LionsLifeTimeTextField.getText()));
            simulationProps.setProperty("wolvesLifeTime", String.valueOf(WolvesLifeTimeTextField.getText()));

            OutputStream out = new FileOutputStream(System.getProperty("user.dir") + "\\src\\config.properties");
            simulationProps.store(out, "Simulation properties");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void showCurrentObjects() {
        if (!canStopTimer()) return;
        stopTimer();
        presenter.stopSimulation();
        presenter.showCurrentObjects();
    }
    private Controller controller;
    private Habitat habitat;
    public Habitat getHabitatModel() {
        return habitat;
    }
    private void startSimulation() {

        //controller.getMainPane().getChildren().add(backgroundImage);
        if (!canStartTimer()) return;
        if (!isLoaded) {
            presenter.resetAll();
        }
        isLoaded = false;
        //presenter.resetAll();
        presenter.setParams(
                LionsIntervalTextField.getText(),
                WolvesIntervalTextField.getText(),
                LionsProbabilitySlider.getValue(),
                WolvesProbabilitySlider.getValue(),
                WolvesLifeTimeTextField.getText(),
                LionsLifeTimeTextField.getText());

        startButton.setDisable(true);
        stopButton.setDisable(false);

        LionsAICheckBox.setDisable(false);
        WolvesAICheckBox.setDisable(false);
        WolvesSpawnCheckBox.setDisable(false);

        LionsAICheckBox.setSelected(true);
        WolvesAICheckBox.setSelected(true);
        WolvesSpawnCheckBox.setSelected(true);
        saveObjectsMenuItem.setDisable(false);
        presenter.startAllAI();
        startTimer();
    }

    private void stopSimulation() {
        if (!canStopTimer()) return;
        stopTimer();

        presenter.showStatsDialog();
    }

    private void onStopSimulation() {
        startButton.setDisable(false);
        stopButton.setDisable(true);
        showTimeRadio.fire();

        LionsAICheckBox.setDisable(true);
        WolvesAICheckBox.setDisable(true);
        WolvesSpawnCheckBox.setDisable(true);

        saveObjectsMenuItem.setDisable(true);
        presenter.stopAllAI();
        presenter.resetAll();
    }

    @Override
    public void updateAnimals(ArrayList<Animals> animals) {
        pane.getChildren().clear();
        for (Animals r : animals) {
            Image image = r.getImage().getImage();
            ImageView imageView = new ImageView(image);
            //ImageView image = new ImageView(r.getImage());
            imageView.setX(r.getX());
            imageView.setY(r.getY());
            imageView.setFitWidth(r.getSize());
            imageView.setFitHeight(r.getSize());
            pane.getChildren().add(imageView);
        }
    }

    @Override
    public void updateStats(int simulationTime) {
        simulationTimeText.setText("Time: " + simulationTime);
        pane.getChildren().add(simulationTimeText);
    }

    @Override
    public void showStatsDialog(int simulationTime, int WolvesCount, int LionsCount) {
        if (showInfoCheckBox.isSelected()) {
            presenter.stopSimulation();
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Animals!");
            dialog.setHeaderText("Statistics");
            dialog.setContentText("Time: " + simulationTime + "\n" +
                    "Lions count: " + LionsCount + "\n" +
                    "Wolves count: " + WolvesCount);

            ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(okButton, ButtonType.CANCEL);

            Optional<ButtonType> result = dialog.showAndWait();

            if (result.isPresent() && result.get() == okButton) {
                System.out.println("OK (ENDING)");
                onStopSimulation();
            } else {
                presenter.startSimulation();
                continueTimer();
            }
        } else {
            onStopSimulation();
        }
    }

    @Override
    public void showErrorDialog(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Animals!");
        alert.setHeaderText("Warning!");
        alert.setContentText(errorMessage);
        alert.showAndWait();
    }

    @Override
    public void showCurrentObjectsDialog(ArrayList<Animals> animals) {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Animals!");
        dialog.setHeaderText("Current Objects");
        ListView<Animals> listView = new ListView<>(FXCollections.observableArrayList(animals));
        dialog.getDialogPane().setContent(listView);

        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButton);

        dialog.showAndWait();

        presenter.startSimulation();
        continueTimer();
    }

    @Override
    public void update(double elapsed, double frameTime) {
        presenter.update(elapsed);
    }

    @Override
    public void setLionsTextField(String s) {
        LionsIntervalTextField.setText(s);
    }

    @Override
    public void setWolvesTextField(String s) {
        WolvesIntervalTextField.setText(s);
    }

    @Override
    public void setWolvesLifeTimeTextField(String s) {
        WolvesLifeTimeTextField.setText(s);
    }

    @Override
    public void setLionsLifeTimeTextField(String s) {
        LionsLifeTimeTextField.setText(s);
    }

    private void startTimer() {
        if (timer != null) return;
        timer = new Timer();
        timer.schedule(new Updater(this, 0), 0, 1000/FPS);
    }

    private void continueTimer() {
        if (timer != null) return;
        timer = new Timer();
        timer.schedule(new Updater(this, presenter.getSimulationTime()), 0, 1000/FPS);
    }


    private void stopTimer() {
        if (timer == null) return;
        timer.cancel();

        timer = null;
    }

    private boolean canStartTimer() {
        return timer == null;
    }

    private boolean canStopTimer() {
        return timer != null;
    }
    private void loadObjects() {
        presenter.stopAllAI();
        stopTimer();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Files", "*.*"));
        File file = fileChooser.showOpenDialog(stage);
        ArrayList<Animals> loadedAnimals = new ArrayList<>();
        if (file != null) {
            try {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
                Animals readAnimals;
                while ((readAnimals = (Animals) ois.readObject()) != null) {
                    loadedAnimals.add(readAnimals);
                }
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("EOF");
            }
            onStopSimulation();
            presenter.setAnimals(loadedAnimals);
            update(0, 0);
        }
        System.out.println(loadedAnimals);

    }


    private void saveObjects() {
        stopTimer();
        presenter.stopAllAI();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Files", "*.*"));
        File file = fileChooser.showOpenDialog(stage);
        ArrayList<Animals> animals = presenter.getAnimals();
        if (file != null) {
            try {
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
                for (Animals animal : animals) {
                    oos.writeObject(animal);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        continueTimer();
        presenter.continueSimulation(WolvesAICheckBox.isSelected(), LionsAICheckBox.isSelected());
    }
    @FXML
    ListView listViewOfAllConnectedClients;
    @FXML
    ComboBox ComboBoxOfAllConnectedClients;
    public void updateConnectionsViews(ArrayList<Integer> ids, int myId) {
        listViewOfAllConnectedClients.getItems().clear();
        ComboBoxOfAllConnectedClients.getItems().clear();
        for (int id : ids) {
            if (id != myId) {
                listViewOfAllConnectedClients.getItems().add("client number " + id);
                ComboBoxOfAllConnectedClients.getItems().add(id + "");
            }

        }

    }
    @FXML
    public void BtnExchangeAnimalsClicked() {
        String string = (String) ComboBoxOfAllConnectedClients.getValue();
        if (string != null) {
            try {
                int exchangeWith = Integer.parseInt(string);
                //client.exchangeAnimals(exchangeWith);
            }
            catch (NumberFormatException e)
            {
                e.printStackTrace();
            }
        }

    }
}