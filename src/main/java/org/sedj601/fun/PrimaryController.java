package org.sedj601.fun;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.controlsfx.control.GridCell;
import org.controlsfx.control.GridView;
import org.kordamp.ikonli.javafx.FontIcon;

public class PrimaryController
{
    @FXML
    GridView<IkonItem> gvMain;
    @FXML
    ComboBox<String> cbMain;
    @FXML
    TextField tfSearch;

    @FXML
    protected void initialize()
    {

    }

    @FXML
    private void handleMenuItemCloseOnAction(ActionEvent actionEvent)
    {
        Platform.exit();
    }

    @FXML
    private void handleMenuItemAboutOnAction(ActionEvent actionEvent)
    {
        System.out.println("Here!");
        createCustomAboutDialog();
    }



    FilteredList<IkonItem> gridViewFilteredList;
    DataModel dataModel;


    public void configureTextFieldSearch()
    {
        tfSearch.textProperty().addListener((obs, oldValue, newValue) -> {
            System.out.println("new value: " + newValue);
            gridViewFilteredList.setPredicate(p -> {
                if(cbMain.getValue().equals("All"))
                {
                    String stringIndex = Integer.toString(p.getIndex());
                    return p.getName().toLowerCase().contains(newValue.toLowerCase().trim()) || p.getEnumName().toLowerCase().contains(newValue.toLowerCase().trim()) || stringIndex.contains(newValue.trim());
                }
                else
                {
                    String stringIndex = Integer.toString(p.getIndex());
                    return p.getProvider().equals(cbMain.getValue()) && (p.getName().toLowerCase().contains(newValue.toLowerCase().trim()) || p.getEnumName().toLowerCase().contains(newValue.toLowerCase().trim()) || stringIndex.contains(newValue.trim()));
                }
            });
        });
    }



    public void loadComboBox()
    {
        cbMain.setItems(dataModel.getComboBoxObservableList());
        cbMain.setValue("All");

        cbMain.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) ->
        {
            if(newSelection != null)
            {
                tfSearch.setText("");
                if (newSelection.equals("All"))
                {
                    gridViewFilteredList.setPredicate(p -> true);
                }
                else
                {
                    gridViewFilteredList.setPredicate(p -> p.getProvider().equals(cbMain.getValue()));
                }
            }
        });
    }

    public void loadGridView()
    {
        gridViewFilteredList = new  FilteredList<>(dataModel.getGridViewObservableList(), p -> true);
        gvMain.setItems(gridViewFilteredList);
        gvMain.setHorizontalCellSpacing(2);
        gvMain.setVerticalCellSpacing(2);
        gvMain.setCellWidth(175);
        gvMain.setCellHeight(225);
        gvMain.setCellFactory(gridView -> new GridCell<>() {
            final CellViewController cellViewController = new CellViewController();
            final Clipboard cbEnum = Clipboard.getSystemClipboard();
            final ClipboardContent cbContentEnum = new ClipboardContent();
            final Clipboard cbId = Clipboard.getSystemClipboard();
            final ClipboardContent cbContentId = new ClipboardContent();
            final Clipboard cbName = Clipboard.getSystemClipboard();
            final ClipboardContent cbContentName = new ClipboardContent();

            @Override
            public void updateItem(IkonItem item, boolean empty) {
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    cellViewController.setId(item.getIndex());
                    cellViewController.setLblIconName(item.getName());
                    cellViewController.setLblEnumName(item.getEnumName());
                    cellViewController.setFiIcon(item.getName());
                    cellViewController.setProvider(item.getProvider());
                    cellViewController.setFiEnumNameCopyOnMouseClicked(new EventHandler<MouseEvent>()
                    {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            FontIcon tempFontIcon = (FontIcon) mouseEvent.getSource();
                            cbContentEnum.putString(item.getEnumName());
                            cbEnum.setContent(cbContentEnum);
                            tempFontIcon.setIconColor(Color.GREEN);
                            PauseTransition pause = new PauseTransition(Duration.seconds(1));
                            pause.setOnFinished(event -> tempFontIcon.setIconColor(Color.BLACK));
                            pause.play();
                        }
                    });
                    cellViewController.setFiIconNameCopyOnMouseClicked(new EventHandler<MouseEvent>()
                    {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            FontIcon tempFontIcon = (FontIcon) mouseEvent.getSource();
                            cbContentName.putString(item.getName());
                            cbName.setContent(cbContentName);
                            tempFontIcon.setIconColor(Color.GREEN);
                            PauseTransition pause = new PauseTransition(Duration.seconds(1));
                            pause.setOnFinished(event -> tempFontIcon.setIconColor(Color.BLACK));
                            pause.play();
                        }
                    });
                    cellViewController.setFiIconIdCopyOnMouseClicked(new EventHandler<MouseEvent>()
                    {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            FontIcon tempFontIcon = (FontIcon) mouseEvent.getSource();
                            cbContentId.putString(Integer.toString(item.getIndex()));
                            cbId.setContent(cbContentId);
                            tempFontIcon.setIconColor(Color.GREEN);
                            PauseTransition pause = new PauseTransition(Duration.seconds(1));
                            pause.setOnFinished(event -> tempFontIcon.setIconColor(Color.BLACK));
                            pause.play();
                        }
                    });

                    setGraphic(cellViewController);
                }
            }
        });
    }

    private void createCustomAboutDialog()
    {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("About Dialog");

        FontIcon fiAbout = new FontIcon("mdi2i-information-outline");
        fiAbout.setIconSize(50);
        dialog.setGraphic(fiAbout);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CLOSE);

        GridPane grid = getGridPane();

        dialog.getDialogPane().setContent(grid);
        dialog.showAndWait();
    }

    private GridPane getGridPane()
    {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        FontIcon fiGitHub1 = new FontIcon("codicon-github");
        Hyperlink hlGitHub = new Hyperlink("Project");
        hlGitHub.setGraphic(fiGitHub1);
        hlGitHub.setOnAction(actionEvent -> dataModel.getHostServices().showDocument("https://github.com/sedj601/IkonliIconBrowser"));

        FontIcon fiGitHub2 = new FontIcon("codicon-github");
        Hyperlink hlMe = new Hyperlink("SedJ601");
        hlMe.setGraphic(fiGitHub2);
        hlMe.setOnAction(actionEvent -> dataModel.getHostServices().showDocument("https://github.com/sedj601"));

        grid.add(hlGitHub, 0, 0);
        grid.add(hlMe, 0, 1);

        return grid;
    }

    public void initDataModel(DataModel dataModel)
    {
        if (this.dataModel != null) {
            throw new IllegalStateException("Model can only be initialized once");
        }
        this.dataModel = dataModel ;

        loadGridView();
        loadComboBox();
        configureTextFieldSearch();
    }
}
