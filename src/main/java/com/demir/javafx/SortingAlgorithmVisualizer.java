package com.demir.javafx;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Objects;
import java.util.Random;

public class SortingAlgorithmVisualizer extends Application {
    private static final int ARRAY_SIZE=30;
    private static final int MAXIMUM_VALUE=200;
    private static final int ANIMATION_SPEED=400; //ms

    private static int[] array;
    private XYChart.Series<String, Number> numberSeries;
    private Timeline timeline;
    private int currentStep=0;
    private boolean isSorting=false;

    @Override
    public void start(Stage stage) {
        array=generateRandomArray(ARRAY_SIZE,MAXIMUM_VALUE);

        //Create axis
        CategoryAxis xAxis=new CategoryAxis();
        NumberAxis yAxis=new NumberAxis();
        xAxis.setLabel("Index");
        yAxis.setLabel("Value");

        //BarChart
        BarChart<String,Number> barChart=new BarChart<>(xAxis,yAxis);
        barChart.setTitle("Algorithm Visualizer");
        barChart.setAnimated(false);

        //Data Set
        numberSeries=new XYChart.Series<>();
        numberSeries.setName("Array Elements");
        updateChart();
        barChart.getData().add(numberSeries);

       //Buttons
        Button randomButton=new Button("Create Random Array");
        randomButton.setOnAction(e-> {
                if(!isSorting){
                    array=generateRandomArray(ARRAY_SIZE,MAXIMUM_VALUE);
                    updateChart();
                    currentStep=0;
                }
        });

        Button bubbleSortArray=new Button("Bubble Sort");
        bubbleSortArray.setOnAction(e->{
            if(!isSorting){
                bubbleSort();
            }

        });

        Button selectionSort=new Button("Selection Sort");
        selectionSort.setOnAction(e->{
            if(!isSorting){
                selectionSort();
            }
        });
        Button insertionSort=new Button("Insertion Sort");
        insertionSort.setOnAction(e->{
            if(!isSorting){
                insertionSort();
            }
        });

        //Set to buttons
        HBox controls=new HBox(10,randomButton,bubbleSortArray,selectionSort,insertionSort);
        controls.setStyle("-fx-padding: 10px;"+
                "-fx-color: #ffefef;"+
                "-fx-border-radius: 10px");

        //Main Order
        BorderPane borderPane=new BorderPane();
        borderPane.setCenter(barChart);
        borderPane.setBottom(controls);

        barChart.setStyle("-fx-bar-fill: #8cf3b2;");

        //Create the scene and show
        Scene scene=new Scene(borderPane,800,600);
        stage.setTitle("Algorithm Visualization");
        barChart.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());
        stage.setScene(scene);
        stage.show();

    }

    private void insertionSort() {
        isSorting=true;
        currentStep=0;
        timeline=new Timeline(new KeyFrame(Duration.millis(ANIMATION_SPEED),actionEvent -> {
            if(currentStep<array.length-1){
                int key = array[currentStep+1]; //begin with second element of array
                int j = currentStep;

                while (j >= 0 && array[j] > key) {
                    array[j + 1] = array[j]; //Shift the larger element to the right
                    j = j - 1; //check previous element
                }
                array[j + 1] = key; // Add key to the empty space

                updateChart();
                currentStep++;

            } else{
                timeline.stop();
                isSorting = false;
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void selectionSort() {
        isSorting=true;
        currentStep=0;
        timeline=new Timeline(new KeyFrame(Duration.millis(ANIMATION_SPEED),actionEvent -> {
            if(currentStep<array.length-1){
                int minIndex=currentStep;
                for(int j=currentStep+1;j<array.length;j++){
                    if(array[j]<array[minIndex]){
                        minIndex=j;
                    }
                }
                //Change minimum element to current step
                int temp=array[currentStep];
                array[currentStep]=array[minIndex];
                array[minIndex]=temp;

                updateChart();
                currentStep++;
            } else {
                timeline.stop();
                isSorting = false;
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void bubbleSort() {
        isSorting=true;
        currentStep=0;
        timeline=new Timeline(new KeyFrame(Duration.millis(ANIMATION_SPEED),actionEvent -> {
            if(currentStep<array.length-1){
                boolean swap=false;
                for(int i=0;i<array.length-currentStep-1;i++){
                    if(array[i]>array[i+1]){
                        //swap elements
                        int temp=array[i];
                        array[i]=array[i+1];
                        array[i+1]=temp;
                        swap=true;

                    }
                }
                updateChart();
                currentStep++;

                if (!swap) {
                    timeline.stop();
                    isSorting = false;
                }
            } else {
                timeline.stop();
                isSorting = false;
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void updateChart() {
        numberSeries.getData().clear();
        for(int i=0;i<array.length;i++){
            numberSeries.getData().add(new XYChart.Data<>(String.valueOf(i+1),array[i]));

        }
    }

    public int[] generateRandomArray(int size,int max){
        int[] randomArray=new int[size];
        Random random=new Random();
        for(int i=0;i<size;i++){
            randomArray[i]=random.nextInt(max)+1; //1 to max
        }

        return randomArray;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
