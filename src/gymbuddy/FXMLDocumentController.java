package gymbuddy;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXScrollPane;
import com.jfoenix.controls.JFXTextField;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class FXMLDocumentController {

    Calculator calculator = new Calculator();
    // {45,35,25,15,10,5,2.5}
    int[] poundPlatesToLoad = calculator.getPoundPlatesToLoad();
    // {25,20,15,10,5,2.5,1.25}
    int[] kilogramPlatesToLoad = calculator.getKilogramPlatesToLoad();
    // stores textfields in a collection
    ObservableList<JFXTextField> poundTextFieldList = FXCollections.observableArrayList();
    ObservableList<JFXTextField> kilogramTextFieldList = FXCollections.observableArrayList();

    // stores increment buttons
    private final ArrayList<JFXButton> poundIncrementButtons = new ArrayList();
    private final ArrayList<JFXButton> poundDecrementButtons = new ArrayList();
    private final ArrayList<JFXButton> kilogramIncrementButtons = new ArrayList();
    private final ArrayList<JFXButton> kilogramDecrementButtons = new ArrayList();

    // stores menu buttons
    private final ArrayList<JFXButton> menuButtons = new ArrayList();

    // used to prevent an infinite loop when listemers update fields
    boolean poundsTriggered = false;
    boolean kilogramsTriggered = false;
    boolean poundPlateTriggered = false;
    boolean kilogramPlateTriggered = false;

    @FXML
    private JFXTextField poundsTextField;
    @FXML
    private JFXTextField kilogramsTextField;

    // text fields for plates in pounds
    @FXML
    private JFXTextField poundPlate45TextField, poundPlate35TextField, poundPlate25TextField,
            poundPlate15TextField, poundPlate10TextField, poundPlate5TextField, poundPlate2point5TextField;

    // text fields for plates in kilograms
    @FXML
    private JFXTextField kilogramPlate25TextField, kilogramPlate20TextField, kilogramPlate15TextField,
            kilogramPlate10TextField, kilogramPlate5TextField, kilogramPlate2point5TextField, kilogramPlate1point25TextField;

    // increment and decrement buttons for plates in pounds
    @FXML
    private JFXButton poundIncrement45Button, poundIncrement35Button, poundIncrement25Button,
            poundIncrement15Button, poundIncrement10Button, poundIncrement5Button, poundIncrement2point5Button;
    @FXML
    private JFXButton poundDecrement45Button, poundDecrement35Button, poundDecrement25Button,
            poundDecrement15Button, poundDecrement10Button, poundDecrement5Button, poundDecrement2point5Button;

    // increment and decrement buttons for plates in kilograms
    @FXML
    private JFXButton kilogramIncrement25Button, kilogramIncrement20Button, kilogramIncrement15Button,
            kilogramIncrement10Button, kilogramIncrement5Button, kilogramIncrement2point5Button, kilogramIncrement1point25Button;
    @FXML
    private JFXButton kilogramDecrement25Button, kilogramDecrement20Button, kilogramDecrement15Button,
            kilogramDecrement10Button, kilogramDecrement5Button, kilogramDecrement2point5Button, kilogramDecrement1point25Button;

    // menu buttons
    @FXML
    private JFXButton profileMenuButton, barbellMenuButton, recordsMenuButton, optionsMenuButton;

    @FXML
    private GridPane barbellGridPane;

    // menu button selected indicators
    @FXML
    private Pane profileIndicator, barbellIndicator, recordsIndicator;

    // profile editor view
    @FXML
    private HBox profileHBox;

    @FXML
    private JFXScrollPane profileScrollPane;

    // profile summary view
    @FXML
    private VBox profileSummaryVBox;
    @FXML
    private TextFlow profileSummaryTextFlow;

    @FXML
    private JFXTextField usernameJFXTextField, ageJFXTextField, heightCMJFXTextField, weightKGJFXTextField,
            competitionSquatJFXTextField, competitionBenchPressJFXTextField, competitionDeadliftJFXTextField,
            outOfCompetitionSquatJFXTextField, outOfCompetitionBenchPressJFXTextField, outOfCompetitionDeadliftJFXTextField;

    @FXML
    private JFXComboBox<String> genderJFXComboBox;  // populated inside initializer

    @FXML
    private JFXButton profileSubmitJFXButton, profileEditJFXButton;

    // IPF Records section
    
    @FXML
    private WebView ipfRecordsWebView;
    
    private WebEngine ipfRecordsWebEngine = new WebEngine();

    /**
     *
     * action event methods for increment buttons
     *
     */
    public void poundIncrementButtonPressed(ActionEvent event) {
        System.out.println("pound increment button pressed");
        JFXButton button = (JFXButton) event.getSource();
        int plate = poundIncrementButtons.indexOf(button);
        incrementDecrementPlate(0, 0, plate);
    }

    public void poundDecrementButtonPressed(ActionEvent event) {
        System.out.println("pound decrement button pressed");
        JFXButton button = (JFXButton) event.getSource();
        int plate = poundDecrementButtons.indexOf(button);
        incrementDecrementPlate(0, 1, plate);
    }

    public void kilogramIncrementButtonPressed(ActionEvent event) {
        System.out.println("kilogram increment button pressed");
        JFXButton button = (JFXButton) event.getSource();
        int plate = kilogramIncrementButtons.indexOf(button);
        System.out.println("current plates: " + kilogramTextFieldList.get(plate).getText());
        incrementDecrementPlate(1, 0, plate);
    }

    public void kilogramDecrementButtonPressed(ActionEvent event) {
        System.out.println("kilogram decrement button pressed");
        JFXButton button = (JFXButton) event.getSource();
        int plate = kilogramDecrementButtons.indexOf(button);
        incrementDecrementPlate(1, 1, plate);
    }

    /**
     *
     * action event method for menu buttons
     *
     * @param event
     */
    private void menuButtonPressed(ActionEvent event) {
        JFXButton menuButton = (JFXButton) event.getSource();
        clearMenuIndicators();

        if (menuButton.equals(profileMenuButton)) {
            profileIndicator.getStyleClass().add("menu__indicator-blue");
            profileHBox.toFront();
        } else if (menuButton.equals(barbellMenuButton)) {
            barbellIndicator.getStyleClass().add("menu__indicator-red");
            barbellGridPane.toFront();
        } else if (menuButton.equals(recordsMenuButton)) {
            recordsIndicator.getStyleClass().add("menu__indicator-yellow");
            ipfRecordsWebView.toFront();
            System.out.println("records");
        }
    }

    @FXML
    private Canvas barbellCanvas;

    double canvasWidth;
    double canvasHeight;
    GraphicsContext barbellGraphics;

    @FXML
    private void initialize() {

        poundTextFieldList.add(poundPlate45TextField);
        poundTextFieldList.add(poundPlate35TextField);
        poundTextFieldList.add(poundPlate25TextField);
        poundTextFieldList.add(poundPlate15TextField);
        poundTextFieldList.add(poundPlate10TextField);
        poundTextFieldList.add(poundPlate5TextField);
        poundTextFieldList.add(poundPlate2point5TextField);

        kilogramTextFieldList.add(kilogramPlate25TextField);
        kilogramTextFieldList.add(kilogramPlate20TextField);
        kilogramTextFieldList.add(kilogramPlate15TextField);
        kilogramTextFieldList.add(kilogramPlate10TextField);
        kilogramTextFieldList.add(kilogramPlate5TextField);
        kilogramTextFieldList.add(kilogramPlate2point5TextField);
        kilogramTextFieldList.add(kilogramPlate1point25TextField);

        clearAll();
        // pounds input change listener. Limits input and avoids empty text fields.
        poundsTextField.textProperty().addListener(
                (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    if (!newValue.matches("\\d*")) {
                        poundsTextField.setText(newValue.replaceAll("[^\\d+\\.?(\\d+]", ""));
                    } else if (!poundsTriggered) {
                        if (newValue.isEmpty()) { // adjusts for blank text fields
                            newValue = "0.00";
                            poundsTextField.setText(newValue);
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    poundsTextField.selectAll();
                                }
                            });
                        }
                        textFieldListenerManager(1);
                    }
                });

        // pounds input focus listener. Selects all when focused.
        poundsTextField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {

            System.out.println("pound focus triggered");
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    if (poundsTextField.isFocused() && !poundsTextField.getText().isEmpty()) {
                        poundsTextField.selectAll();
                    }
                }
            });

        });

        // kilograms input change listener. Limits input and avoids empty text fields.
        kilogramsTextField.textProperty().addListener(
                (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {

                    if (!newValue.matches("\\d*")) {
                        kilogramsTextField.setText(newValue.replaceAll("[^\\d+\\.?(\\d+]", ""));
                    } else if (!kilogramsTriggered) {
                        if (newValue.isEmpty()) { // adjusts for blank text fields
                            newValue = "0.00";
                            kilogramsTextField.setText(newValue);
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    kilogramsTextField.selectAll();
                                }
                            });
                        }
                        textFieldListenerManager(2);
                    }
                });

        // kilograms input focus listener. Selects all when focused.
        kilogramsTextField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {

            System.out.println("kilogram focus triggered");
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    if (kilogramsTextField.isFocused() && !kilogramsTextField.getText().isEmpty()) {
                        kilogramsTextField.selectAll();
                    }
                }
            });

        });

        // pound plates listeners
        for (TextField poundPlate : poundTextFieldList) {

            // pound plate input focus listener. Selects all when focused.
            poundPlate.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {

                System.out.println("pound plate focus triggered");
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        if (poundPlate.isFocused() && !poundPlate.getText().isEmpty()) {
                            poundPlate.selectAll();
                        }
                    }
                });

            });

            // pound plate input change listener. Limits input and avoids empty text fields.
            poundPlate.textProperty().addListener(
                    (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                        if (!newValue.matches("\\d*")) {
                            poundPlate.setText(newValue.replaceAll("[^\\d]", ""));
                        } else if (!poundPlateTriggered) {
                            if (newValue.isEmpty()) { // adjusts for blank text fields
                                newValue = "0";
                                poundPlate.setText(newValue);
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        poundPlate.selectAll();
                                    }
                                });
                            }
                            int numberOfPlates = Integer.parseInt(newValue);
                            if (numberOfPlates % 2 == 0) {
                                poundPlate.getStyleClass().removeAll("textfield-error");
                                poundPlatesToLoad[poundTextFieldList.indexOf(poundPlate)] = Integer.parseInt(newValue);
                                System.out.println("even pound plate added: " + Arrays.toString(poundPlatesToLoad));
                                calculator.setPoundPlatesToLoad(poundPlatesToLoad);
                                textFieldListenerManager(3);
                            } else {
                                poundPlate.getStyleClass().add("textfield-error");
                                poundPlatesToLoad[poundTextFieldList.indexOf(poundPlate)] = Integer.parseInt(newValue);
                                System.out.println("odd pound plate added: " + Arrays.toString(poundPlatesToLoad));
                                poundPlateTriggered = false;
                            }
                        }
                    });
        }

        // kilogram plates listeners
        for (TextField kilogramPlate : kilogramTextFieldList) {

            // kilogram plate input focus listener. Selects all when focused.
            kilogramPlate.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {

                System.out.println("kilogram plate focus triggered");
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        if (kilogramPlate.isFocused() && !kilogramPlate.getText().isEmpty()) {
                            kilogramPlate.selectAll();
                        }
                    }
                });

            });

            // kilogram plate input change listener. Limits input and avoids empty text fields.
            kilogramPlate.textProperty().addListener(
                    (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                        if (!newValue.matches("\\d*")) {
                            kilogramPlate.setText(newValue.replaceAll("[^\\d]", ""));
                        } else if (!kilogramPlateTriggered) {
                            if (newValue.isEmpty()) { // adjusts for blank textfields 
                                newValue = "0";
                                kilogramPlate.setText(newValue);
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        kilogramPlate.selectAll();
                                    }
                                });
                            }
                            double numberOfPlates = Double.parseDouble(newValue);
                            if (numberOfPlates % 2 == 0) {
                                kilogramPlate.getStyleClass().removeAll("textfield-error");
                                kilogramPlatesToLoad[kilogramTextFieldList.indexOf(kilogramPlate)] = Integer.parseInt(newValue);
                                System.out.println("even kilogram plate added: " + Arrays.toString(kilogramPlatesToLoad));
                                calculator.setKilogramPlatesToLoad(kilogramPlatesToLoad);
                                textFieldListenerManager(4);
                            } else {
                                kilogramPlate.getStyleClass().add("textfield-error");
                                kilogramPlatesToLoad[kilogramTextFieldList.indexOf(kilogramPlate)] = Integer.parseInt(newValue);
                                System.out.println("odd kilogram plate added: " + Arrays.toString(kilogramPlatesToLoad));
                                kilogramPlateTriggered = false;
                            }
                        }
                    });

            //fills arrays with increment and decrement buttons
            poundIncrementButtons.add(poundIncrement45Button);
            poundIncrementButtons.add(poundIncrement35Button);
            poundIncrementButtons.add(poundIncrement25Button);
            poundIncrementButtons.add(poundIncrement15Button);
            poundIncrementButtons.add(poundIncrement10Button);
            poundIncrementButtons.add(poundIncrement5Button);
            poundIncrementButtons.add(poundIncrement2point5Button);

            poundDecrementButtons.add(poundDecrement45Button);
            poundDecrementButtons.add(poundDecrement35Button);
            poundDecrementButtons.add(poundDecrement25Button);
            poundDecrementButtons.add(poundDecrement15Button);
            poundDecrementButtons.add(poundDecrement10Button);
            poundDecrementButtons.add(poundDecrement5Button);
            poundDecrementButtons.add(poundDecrement2point5Button);

            kilogramIncrementButtons.add(kilogramIncrement25Button);
            kilogramIncrementButtons.add(kilogramIncrement20Button);
            kilogramIncrementButtons.add(kilogramIncrement15Button);
            kilogramIncrementButtons.add(kilogramIncrement10Button);
            kilogramIncrementButtons.add(kilogramIncrement5Button);
            kilogramIncrementButtons.add(kilogramIncrement2point5Button);
            kilogramIncrementButtons.add(kilogramIncrement1point25Button);

            kilogramDecrementButtons.add(kilogramDecrement25Button);
            kilogramDecrementButtons.add(kilogramDecrement20Button);
            kilogramDecrementButtons.add(kilogramDecrement15Button);
            kilogramDecrementButtons.add(kilogramDecrement10Button);
            kilogramDecrementButtons.add(kilogramDecrement5Button);
            kilogramDecrementButtons.add(kilogramDecrement2point5Button);
            kilogramDecrementButtons.add(kilogramDecrement1point25Button);

            // assigns action listeners to increment and decrement buttons
            for (JFXButton button : poundIncrementButtons) {
                button.setOnAction(this::poundIncrementButtonPressed);
            }
            for (JFXButton button : poundDecrementButtons) {
                button.setOnAction(this::poundDecrementButtonPressed);
            }
            for (JFXButton button : kilogramIncrementButtons) {
                button.setOnAction(this::kilogramIncrementButtonPressed);
            }
            for (JFXButton button : kilogramDecrementButtons) {
                button.setOnAction(this::kilogramDecrementButtonPressed);
            }

            // assigns actions listeners to menu buttons
            menuButtons.add(profileMenuButton);
            barbellIndicator.getStyleClass().add("menu__indicator-red");
            menuButtons.add(barbellMenuButton);
            menuButtons.add(recordsMenuButton);

            for (JFXButton button : menuButtons) {
                button.setOnAction(this::menuButtonPressed);
            }

        }

        // initializes barbell graphics properties
        canvasWidth = barbellCanvas.getWidth();
        canvasHeight = barbellCanvas.getHeight();
        barbellGraphics = barbellCanvas.getGraphicsContext2D();
        drawBarbell(kilogramPlatesToLoad);

        // populates gender comboBox
        genderJFXComboBox.getItems().add("Male");
        genderJFXComboBox.getItems().add("Female");

        profileSubmitJFXButton.setOnAction(this::submitProfileButtonPressed);
        profileEditJFXButton.setOnAction(this::editProfileButtonPressed);

        profileSummaryTextFlow.setLineSpacing(5);

        ipfRecordsWebEngine = ipfRecordsWebView.getEngine();
        ipfRecordsWebEngine.setUserStyleSheetLocation(getClass().getResource("webview.css").toString());
        ipfRecordsWebEngine.load("http://goodlift.info/records_last_s.php");

    }

    private void textFieldListenerManager(int tf) {
        switch (tf) {

            // pounds text field
            case 1:
                poundsTriggered = true;
                double poundsInput = Double.parseDouble(poundsTextField.getText());
                if (poundsInput >= 45) {
                    poundPlateTriggered = true;
                    calculator.setWeightInPounds(poundsInput);
                    updatePoundPlates();
                    if (!kilogramsTriggered) {
                        kilogramsTriggered = true;
                        convertToKilograms();
                        if (!kilogramPlateTriggered) {
                            kilogramPlateTriggered = true;
                            updateKilogramPlates();
                            drawBarbell(kilogramPlatesToLoad);
                        }
                    }
                }
                poundsTriggered = poundPlateTriggered = kilogramsTriggered = kilogramPlateTriggered = false;
                break;

            // kilograms text field
            case 2:
                kilogramsTriggered = true;
                double kilogramsInput = Double.parseDouble(kilogramsTextField.getText());
                if (kilogramsInput >= 20) {
                    kilogramPlateTriggered = true;
                    calculator.setWeightInKilograms(kilogramsInput);
                    updateKilogramPlates();
                    drawBarbell(kilogramPlatesToLoad);
                    if (!poundsTriggered) {
                        poundsTriggered = true;
                        convertToPounds();
                        if (!poundPlateTriggered) {
                            poundPlateTriggered = true;
                            updatePoundPlates();
                        }
                    }
                }
                poundsTriggered = poundPlateTriggered = kilogramsTriggered = kilogramPlateTriggered = false;
                break;

            // triggered if specific pound plate input is even
            case 3:
                poundPlateTriggered = true;
                if (checkPlates(poundPlatesToLoad)) {
                    poundsTriggered = true;
                    updatePoundsTextFieldFromPlates();
                    kilogramsTriggered = true;
                    convertToKilograms();
                    kilogramPlateTriggered = true;
                    updateKilogramPlates();
                    drawBarbell(kilogramPlatesToLoad);
                }
                poundsTriggered = poundPlateTriggered = kilogramsTriggered = kilogramPlateTriggered = false;
                break;

            // triggered if specific kilogram plate input is even
            case 4:
                kilogramPlateTriggered = true;
                if (checkPlates(kilogramPlatesToLoad)) {
                    drawBarbell(kilogramPlatesToLoad);
                    kilogramsTriggered = true;
                    updateKilogramsTextFieldFromPlates();
                    poundsTriggered = true;
                    convertToPounds();
                    poundPlateTriggered = true;
                    updatePoundPlates();
                }
                poundsTriggered = poundPlateTriggered = kilogramsTriggered = kilogramPlateTriggered = false;
                break;
        }
    }

    // Updates total weight in pounds if plates are even. Otherwise do nothing.
    private void convertToPounds() {
        double kilogramsInput = Double.parseDouble(kilogramsTextField.getText());
        double convertedValue = calculator.kilogramsToPounds(kilogramsInput);
        poundsTextField.setText(Double.toString(convertedValue));
        System.out.println("converted pounds: " + convertedValue + "lb");
    }

    private void updatePoundsTextFieldFromPlates() {
        poundsTextField.setText(Double.toString(calculator.getWeightInPounds()));
    }

    // Updates total weight in kilograms if plates are even. Otherwise do nothing.
    private void convertToKilograms() {
        double poundsInput = Double.parseDouble(poundsTextField.getText());
        double convertedValue = calculator.poundsToKilograms(poundsInput);
        kilogramsTextField.setText(Double.toString(convertedValue));
        System.out.println("converted kilograms: " + convertedValue + "kg");
    }

    private void updateKilogramsTextFieldFromPlates() {
        kilogramsTextField.setText(Double.toString(calculator.getWeightInKilograms()));
    }

    // updates plates from pounds
    private void updatePoundPlates() {
        poundPlatesToLoad = calculator.getPoundPlatesToLoad();
        int index = 0;
        for (TextField poundPlates : poundTextFieldList) {
            poundPlates.setText(Integer.toString((int) poundPlatesToLoad[(index++)]));
            poundPlates.getStyleClass().removeAll("textfield-error");
        }
    }

    // updates plates from kilograms
    private void updateKilogramPlates() {
        kilogramPlatesToLoad = calculator.getKilogramPlatesToLoad();
        int index = 0;
        for (TextField kilogramPlates : kilogramTextFieldList) {
            kilogramPlates.setText(Integer.toString((int) kilogramPlatesToLoad[(index++)]));
            kilogramPlates.getStyleClass().removeAll("textfield-error");
        }
    }

    // clears all fields
    private void clearAll() {
        poundsTextField.setText("45.00");
        for (TextField poundPlates : poundTextFieldList) {
            poundPlates.setText("0");
        }

        kilogramsTextField.setText("20.41");
        for (TextField kilogramPlates : kilogramTextFieldList) {
            kilogramPlates.setText("0");
        }

        updatePoundPlates();
        updateKilogramPlates();
    }

    // clears coloured menu indicators
    private void clearMenuIndicators() {
        profileIndicator.getStyleClass().clear();
        barbellIndicator.getStyleClass().clear();
        recordsIndicator.getStyleClass().clear();
    }

    // returns true if all plates in array are even
    private boolean checkPlates(int[] plates) {
        boolean platesEven = true;
        for (double plate : plates) {
            if (plate % 2 != 0) {
                platesEven = false;
                break;
            }
        }
        return platesEven;
    }

    /**
     *
     * increments or decrements plates
     *
     * @param unit
     * @param incrementDecrement
     * @param textField
     */
    private void incrementDecrementPlate(int unit, int incrementDecrement, int textField) {

        int oldValue;
        if (unit == 0) {
            oldValue = (int) poundPlatesToLoad[textField];
        } else {
            oldValue = (int) kilogramPlatesToLoad[textField];
        }

        String incremented;
        String decremented;

        if (oldValue % 2 != 0) {
            incremented = Integer.toString(oldValue + 1);
        } else {
            incremented = Integer.toString(oldValue + 2);
        }

        if (oldValue % 2 != 0) {
            decremented = Integer.toString(oldValue - 1);
        } else if (oldValue != 0) {
            decremented = Integer.toString(oldValue - 2);
        } else {
            decremented = Integer.toString(oldValue);
        }

        switch (unit) {
            case 0:
                if (incrementDecrement == 0) {
                    poundTextFieldList.get(textField).setText(incremented);
                } else {
                    poundTextFieldList.get(textField).setText(decremented);
                }
                break;
            case 1:
                if (incrementDecrement == 0) {
                    kilogramTextFieldList.get(textField).setText(incremented);
                } else {
                    kilogramTextFieldList.get(textField).setText(decremented);
                }
                break;
        }
    }

    /**
     * Relevant IPF powerlifting bar information from Eleiko schematics
     *
     * Bar Diameter: 29mm, Sleeve Diameter: 52mm, Sleeve Length: 433mm Flange
     * diameter: , Flange Width: 12mm
     *
     * Plate Colours: 25kg - red, 20kg - blue, 15kg - yellow, 10kg and under -
     * anything
     *
     */
    private double platePositionX; // tracks position of last plate on bar
    private double platePositionY;

    private void drawBarbell(int[] kilogramPlatesToLoad) {
        // clears canvas to prepare for updates
        barbellGraphics.clearRect(0, 0, canvasWidth, canvasHeight);
        int[] kilogramPlatesToDraw = kilogramPlatesToLoad;

        double barDiameter = 22;
        Dimensions barbell = new Dimensions(barDiameter);

        double barLength = (canvasWidth - barbell.getSleevePlusFlange()) / 2;
        double sleeveDiameter = barbell.getSleeveDiameter();
        double sleeveLength = barbell.getSleeveLength();
        double flangeDiameter = barbell.getFlangeDiameter();
        double flangeWidth = barbell.getFlangeWidth();
        double[] plateDiameter = barbell.getPlateDiameter();
        double[] plateWidth = barbell.getPlateWidth();

        double collarBigLength = barbell.getCollarBigLength();
        double collarSmallLength = barbell.getCollarSmallLength();
        double collarBigDiameter = barbell.getCollarBigDiameter();
        double collarSmallDiameter = barbell.getCollarSmallDiameter();
        double collarTotalLength = collarBigLength + collarSmallLength;

        double collarKnobLength = barbell.getCollarKnobLength();
        double collarKnobHeight = barbell.getCollarKnobHeight();
        double collarPinLength = barbell.getCollarPinLength();
        double collarPinHeight = barbell.getCollarPinHeight();

        /**
         *
         * Each color is eye dropped from Eleiko IPF calibrated kg plates
         *
         */
        Color barMetal = Color.rgb(134, 134, 134);
        Color kg25Red = Color.rgb(194, 81, 63);
        Color kg20Blue = Color.rgb(54, 88, 149);
        Color kg15Yellow = Color.rgb(228, 201, 0);
        Color kg10Green = Color.rgb(28, 112, 88);
        Color kg5White = Color.rgb(235, 235, 235);
        Color kg2point5Gray = Color.rgb(75, 75, 75);
        Color kg1point25Silver = Color.rgb(192, 192, 192);
        Color collarMetal = Color.rgb(100, 100, 100);

        barbellGraphics.setFill(barMetal);
        barbellGraphics.setStroke(barMetal.darker());

        // draws bar
        barbellGraphics.fillRect(canvasWidth - barLength, canvasHeight / 2 - barDiameter / 2, barLength, barDiameter);
        barbellGraphics.strokeRect(canvasWidth - barLength, canvasHeight / 2 - barDiameter / 2, barLength, barDiameter);
        // draws sleeve
        barbellGraphics.fillRoundRect(canvasWidth - barLength - flangeWidth - sleeveLength,
                canvasHeight / 2 - sleeveDiameter / 2, sleeveLength, sleeveDiameter, 5, 5);
        barbellGraphics.strokeRoundRect(canvasWidth - barLength - flangeWidth - sleeveLength,
                canvasHeight / 2 - sleeveDiameter / 2, sleeveLength, sleeveDiameter, 5, 5);
        // draws flange
        barbellGraphics.fillRoundRect(canvasWidth - barLength - flangeWidth,
                canvasHeight / 2 - flangeDiameter / 2, flangeWidth, flangeDiameter, 3, 3);
        barbellGraphics.strokeRoundRect(canvasWidth - barLength - flangeWidth,
                canvasHeight / 2 - flangeDiameter / 2, flangeWidth, flangeDiameter, 3, 3);

        /**
         *
         * This section draws the plates passed from the
         * kilogramPlatesToLoadArray
         *
         */
        // tracks position of last plate drawn
        platePositionX = canvasWidth - (barLength + flangeWidth);
        platePositionY = canvasHeight / 2;

        Color[] kilogramPlateColor = {kg25Red, kg20Blue, kg15Yellow,
            kg10Green, kg5White, kg2point5Gray, kg1point25Silver};
        Color[] kilogramPlateBorderColor = {kg25Red.darker(), kg20Blue.darker(), kg15Yellow.darker(),
            kg10Green.darker(), kg5White.darker(), kg2point5Gray.darker(), kg1point25Silver.darker()};

        // Draws each plate from array
        int platesToDraw = 0;
        boolean drawCollar = false;
        double roomLeftOnSleeve = sleeveLength - collarTotalLength;
        for (int i = 0; i < kilogramPlatesToDraw.length; i++) {
            if (kilogramPlatesToDraw[i] != 0) {
                // checks for room on the sleeve. *Will need to update once collars are added to the program.*
                platesToDraw = (int) kilogramPlatesToDraw[i] / 2;
                while (platesToDraw != 0) {
                    drawCollar = true;
                    if (roomLeftOnSleeve > plateWidth[i]) {
                        barbellGraphics.setFill(kilogramPlateColor[i]);
                        barbellGraphics.fillRoundRect(platePositionX - plateWidth[i],
                                platePositionY - plateDiameter[i] / 2,
                                plateWidth[i], plateDiameter[i], 3, 3);
                        barbellGraphics.setStroke(kilogramPlateBorderColor[i]);
                        barbellGraphics.strokeRoundRect(platePositionX - plateWidth[i],
                                platePositionY - plateDiameter[i] / 2,
                                plateWidth[i], plateDiameter[i], 3, 3);
                        platePositionX -= plateWidth[i];
                        roomLeftOnSleeve -= plateWidth[i];
                        platesToDraw--;
                    } else {
                        break;
                    }
                }
            }
        }
        if (drawCollar) {
            // Draws main part of collar
            barbellGraphics.setFill(collarMetal);
            barbellGraphics.fillRoundRect(platePositionX - collarBigLength,
                    platePositionY - collarBigDiameter / 2, collarBigLength,
                    collarBigDiameter, 6, 6);
            barbellGraphics.setStroke(collarMetal.darker());
            barbellGraphics.strokeRoundRect(platePositionX - collarBigLength,
                    platePositionY - collarBigDiameter / 2, collarBigLength,
                    collarBigDiameter, 6, 6);
            barbellGraphics.fillRoundRect(platePositionX - collarTotalLength,
                    platePositionY - collarSmallDiameter / 2, collarSmallLength,
                    collarSmallDiameter, 6, 6);
            barbellGraphics.strokeRoundRect(platePositionX - collarTotalLength,
                    platePositionY - collarSmallDiameter / 2, collarSmallLength,
                    collarSmallDiameter, 6, 6);
            // pin
            barbellGraphics.fillRoundRect(platePositionX - collarBigLength / 2 - collarKnobLength / 2 - collarPinLength * 0.7,
                    platePositionY - collarBigDiameter / 2 - collarKnobHeight / 2 - collarPinHeight / 2, collarPinLength,
                    collarPinHeight, 4, 4);
            barbellGraphics.strokeRoundRect(platePositionX - collarBigLength / 2 - collarKnobLength / 2 - collarPinLength * 0.7,
                    platePositionY - collarBigDiameter / 2 - collarKnobHeight / 2 - collarPinHeight / 2, collarPinLength,
                    collarPinHeight, 4, 4);
            // knob
            barbellGraphics.fillRoundRect(platePositionX - collarBigLength / 2 - collarKnobLength / 2,
                    platePositionY - collarBigDiameter / 2 - collarKnobHeight, collarKnobLength,
                    collarKnobHeight, 3, 3);
            barbellGraphics.strokeRoundRect(platePositionX - collarBigLength / 2 - collarKnobLength / 2,
                    platePositionY - collarBigDiameter / 2 - collarKnobHeight, collarKnobLength,
                    collarKnobHeight, 3, 3);
            drawCollar = false;
            System.out.println("room left on Sleeve: " + roomLeftOnSleeve);
        }

    }

    /**
     *
     * This section manages the user profile. It will eventually allow for the
     * management of multiple user profiles.
     *
     */
    Profile profile = new Profile();

    private void submitProfileButtonPressed(ActionEvent event) {
        profile.setUsername(usernameJFXTextField.getText());
        profile.setHeight(Double.parseDouble(heightCMJFXTextField.getText()));
        profile.setAge(Integer.parseInt(ageJFXTextField.getText()));
        profile.setWeight(Double.parseDouble(weightKGJFXTextField.getText()));

        if ((genderJFXComboBox.getValue().equals("Male"))) {
            profile.setGender(0);
        } else {
            profile.setGender(1);
        }

        profile.setCompSquat(Double.parseDouble(competitionSquatJFXTextField.getText()));
        profile.setCompBench(Double.parseDouble(competitionBenchPressJFXTextField.getText()));
        profile.setCompDeadlift(Double.parseDouble(competitionDeadliftJFXTextField.getText()));
        profile.setGymSquat(Double.parseDouble(outOfCompetitionSquatJFXTextField.getText()));
        profile.setGymBench(Double.parseDouble(outOfCompetitionBenchPressJFXTextField.getText()));
        profile.setGymDeadlift(Double.parseDouble(outOfCompetitionDeadliftJFXTextField.getText()));

        updateProfileSummary();
        profileSummaryVBox.toFront();
    }

    private void editProfileButtonPressed(ActionEvent event) {
        profileHBox.toFront();
    }

    Text username = new Text();
    Text statsTitle = new Text();
    Text stats = new Text();
    Text compLiftsTitle = new Text();
    Text compLifts = new Text();
    Text gymLiftsTitle = new Text();
    Text gymLifts = new Text();

    private void updateProfileSummary() {

        username.setFont(new Font(25));
        username.setUnderline(true);
        username.setText(profile.getUsername() + "\n");
        stats.setFont(new Font(15));
        stats.setText("Age: " + Integer.toString(profile.getAge()) + "\n"
                + "Age Category: " + profile.getAgeCategory() + "\n"
                + "Gender: " + profile.getGender() + "\n"
                + "Height: " + profile.getHeight() + "cm\n"
                + "Weight: " + profile.getWeight() + "kg\n"
                + "Weight Category: " + profile.getWeightCategory() + "\n");
        compLiftsTitle.setFont(new Font(25));
        compLiftsTitle.setText("Competition Lifts\n");
        compLifts.setFont(new Font(15));
        compLifts.setText("Squat: " + profile.getCompSquat() + "kg    "
                + "Bench Press: " + profile.getCompBench() + "kg    "
                + "Deadlift: " + profile.getCompDeadlift() + "kg\n"
                + "Total: " + (profile.getCompSquat() + profile.getCompBench() + profile.getCompDeadlift()) + "kg\n"
                + "Wilks: " + profile.getWilksTotal() + "\n"
                + "Strength Level: " + profile.getStrength() + "\n");
        gymLiftsTitle.setFont(new Font(25));
        gymLiftsTitle.setText("Gym Lifts\n");
        gymLifts.setFont(new Font(15));
        gymLifts.setText("Squat: " + profile.getGymSquat() + "kg    "
                + "Bench Press: " + profile.getGymBench() + "kg    "
                + "Deadlift: " + profile.getGymDeadlift() + "kg\n"
                + "Total: " + (profile.getGymSquat() + profile.getGymBench() + profile.getGymDeadlift()) + "kg\n");

        profileSummaryTextFlow.getChildren().addAll(username, statsTitle, stats, compLiftsTitle, compLifts, gymLiftsTitle, gymLifts);
        
    }

}
