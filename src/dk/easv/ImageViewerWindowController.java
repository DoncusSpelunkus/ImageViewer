package dk.easv;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class ImageViewerWindowController implements Initializable
{
    private final List<Image> images = new ArrayList<>();
    private int currentImageIndex = 0;

    @FXML
    Parent root;

    @FXML
    private ImageView imageView;

    @FXML
    private ChoiceBox<Integer> timeChoiceBox;

    private ObservableList<Integer> observableList;

    public ImageViewerWindowController(){
        List<Integer> anotherList = Arrays.asList(1,2,3,4,5);
        observableList = FXCollections.observableArrayList();
        observableList.setAll(anotherList);
    }

    @FXML
    private void handleBtnLoadAction() throws InterruptedException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select image files");
        fileChooser.getExtensionFilters().add(new ExtensionFilter("Images",
                "*.png", "*.jpg", "*.gif", "*.tif", "*.bmp"));
        List<File> files = fileChooser.showOpenMultipleDialog(new Stage());

        if (!files.isEmpty())
        {
            files.forEach((File f) ->
            {
                images.add(new Image(f.toURI().toString()));

            });
            displayImage();
            runSlideShow();
        }
    }

    @FXML
    private void handleBtnPreviousAction()
    {
        if (!images.isEmpty())
        {
            currentImageIndex =
                    (currentImageIndex - 1 + images.size()) % images.size();
            displayImage();
        }
    }

    @FXML
    private void handleBtnNextAction()
    {
        if (!images.isEmpty())
        {
            currentImageIndex = (currentImageIndex + 1) % images.size();
            displayImage();
        }
    }

    private void displayImage()
    {
        if (!images.isEmpty())
        {
            imageView.setImage(images.get(currentImageIndex));
        }
    }

    private void runSlideShow() throws InterruptedException {
        ExecutorService es = Executors.newCachedThreadPool();
        es.submit(() -> {
            while (true){
            handleBtnNextAction();
            try {
                if(timeChoiceBox.getValue() != null){
                    TimeUnit.SECONDS.sleep(timeChoiceBox.getValue());
                }
                else TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }});
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        timeChoiceBox.setItems(observableList);
    }
}