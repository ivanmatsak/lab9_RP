package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;


public class Controller {
    @FXML
    RadioButton radioSin;
    @FXML
    RadioButton radioCos;
    @FXML
    RadioButton radioE;
    @FXML
    Button cleanButton;
    @FXML
    Label labelXk;
    @FXML
    Label labelH;
    @FXML
    Label labelXn;
    @FXML
    Button button;
    @FXML
    ScrollPane area;
    @FXML
    TextField fieldXn;
    @FXML
    TextField fieldXk;
    @FXML
    TextField fieldH;
    @FXML
    TextField fieldM;
    @FXML
    ToggleGroup funcGroup;
    @FXML
    Slider redSlider;
    @FXML
    Slider greenSlider;
    @FXML
    Slider blueSlider;
    @FXML
    Label redLabel;
    @FXML
    Label greenLabel;
    @FXML
    Label blueLabel;

    public double getFactorial(double num) {
        int result = 1;
        for (int i = 1; i <= num; i++) {
            result *= i;
        }
        return result;
    }

    public StringBuilder calculate(Operationable operation,double Xn,double Xk, double h, int m) {
        StringBuilder str=new StringBuilder(" Результаты:\n");
        int counter=0;
        for (double i=Xn;i<=Xk;i+=h){
            str.append(" x= "+String.format("%.5g%n", i)+" y= "+String.format("%.5g%n",operation.function(i,m))+"\n");
        }
        return str;
    }
    static Color color;

    public static Color getColor(){
        return color;
    }
    @FXML
    void initialize() {

        Label buffLabel = new Label();
        //redValueLabel.textProperty().bind(redSlider.valueProperty().asString("%.0f"));
       // blueValueLabel.textProperty().bind(blueSlider.valueProperty().asString("%.0f"));
        //greenValueLabel.textProperty().bind(greenSlider.valueProperty().asString("%.0f"));

        button.setOnAction(ActionEvent -> {

            double Xn = Double.parseDouble(fieldXn.getText());
            double Xk = Double.parseDouble(fieldXk.getText());
            double H = Double.parseDouble(fieldH.getText());
            int M = Integer.parseInt(fieldM.getText());

            RadioButton selection = (RadioButton) funcGroup.getSelectedToggle();
            String str=selection.getText();
            Operationable operation;
            switch (str){
                case "cos(x)":
                    operation=(x,accuracy)->{
                        double sum=0;
                        for(int i=0;i<=accuracy;i++){
                            sum+=Math.pow(-1, i) * Math.pow(x, 2 * i) / getFactorial(2 * i);
                        }
                        return sum;
                    };
                    break;
                case "e^x":
                    operation=(x,accuracy)->{
                        double sum=0;
                        for(int i=0;i<=accuracy;i++){
                            sum+=Math.pow(x, i)/ getFactorial(i);
                        }
                        return sum;
                    };
                    break;
                case "sin(x)":
                    operation=(x,accuracy)->{
                        double sum=0;
                        for(int i=0;i<=accuracy;i++){
                            sum+=Math.pow(-1, i) * Math.pow(x, (2*i)+1) / getFactorial((2 * i)+1);
                        }
                        return sum;
                    };
                    break;
                default:
                    operation=(X, accuracy) ->-1;
                    break;
            }
            StringBuilder result = calculate(operation,Xn, Xk, H, M);
            buffLabel.setText(result.toString());
            area.setContent(buffLabel);
        });

        cleanButton.setOnAction(ActionEvent -> {
            area.setContent(new Label());
        });

        redSlider.valueProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable, //
                                Number oldValue, Number newValue) {

                redLabel.setTextFill(Color.rgb((int)redSlider.getValue(), 0, 0));
                redLabel.setText("Red: "+(int)redSlider.getValue());
                button.setTextFill(Color.rgb((int)redSlider.getValue(), (int)greenSlider.getValue(), (int)blueSlider.getValue()));
            }
        });
        greenSlider.valueProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable, //
                                Number oldValue, Number newValue) {

                greenLabel.setTextFill(Color.rgb(0, (int)greenSlider.getValue(), 0));
                greenLabel.setText("Green: "+(int)greenSlider.getValue());
                button.setTextFill(Color.rgb((int)redSlider.getValue(), (int)greenSlider.getValue(), (int)blueSlider.getValue()));
            }
        });
        blueSlider.valueProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable, //
                                Number oldValue, Number newValue) {

                blueLabel.setTextFill(Color.rgb( 0, 0,(int)blueSlider.getValue()));
                blueLabel.setText("Blue: "+(int)blueSlider.getValue());
                button.setTextFill(Color.rgb((int)redSlider.getValue(), (int)greenSlider.getValue(), (int)blueSlider.getValue()));
            }
        });
    }
}

interface Operationable {
    double function(double X, int accuracy);
}