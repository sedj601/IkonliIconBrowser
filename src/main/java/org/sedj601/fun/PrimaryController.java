package org.sedj601.fun;

import java.util.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.controlsfx.control.GridCell;
import org.controlsfx.control.GridView;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.IkonProvider;

public class PrimaryController
{
    List<IkonItem> ikonItemList = new ArrayList<>();
    Set<String> providerList = new HashSet<>();
    ObservableList<IkonItem> gridViewObservableList = FXCollections.observableArrayList();
    FilteredList<IkonItem> gridViewFilteredList;
    ObservableList<String> comboBoxObservableList = FXCollections.observableArrayList();
    @FXML
    GridView<IkonItem> gvMain;
    @FXML
    ComboBox<String> cbMain;
    @FXML
    TextField tfSearch;

    @FXML
    protected void initialize()
    {
        loadAppData();
        loadGridView();
        loadComboBox();
        configureTextFieldSearch();
    }

    public void configureTextFieldSearch()
    {
        tfSearch.textProperty().addListener((obs, oldValue, newValue) -> {
            System.out.println("new value: " + newValue);
            gridViewFilteredList.setPredicate(p -> {
                if(cbMain.getValue().equals("All"))
                {
                    return p.getName().toLowerCase().contains(newValue.toLowerCase().trim()) || p.getEnumName().toLowerCase().contains(newValue.toLowerCase().trim());
                }
                else
                {
                    return p.getProvider().equals(cbMain.getValue()) && (p.getName().toLowerCase().contains(newValue.toLowerCase().trim()) || p.getEnumName().toLowerCase().contains(newValue.toLowerCase().trim()));
                }
            });
        });
    }
    
    public void loadAppData()
    {
        providerList.add("All");

        ServiceLoader<IkonProvider> providers = ServiceLoader.load(IkonProvider.class);
        for(IkonProvider provider : providers)
        {
            for(Object object : provider.getIkon().getEnumConstants())
            {
                Ikon tempIkon = (Ikon)object;
                IkonItem ikonItem = new IkonItem(tempIkon.getCode(), tempIkon.getDescription(), tempIkon.toString(), provider.getIkon().getSimpleName());
                ikonItemList.add(ikonItem);

                providerList.add(provider.getIkon().getSimpleName());
            }
        }
    }

    public void loadComboBox()
    {
        comboBoxObservableList.addAll(providerList);
        cbMain.setItems(comboBoxObservableList);
        cbMain.setValue("All");//todo -> Maybe make this be the last selection when the app ended.

        cbMain.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) ->
        {
            if(newSelection != null)
            {
                tfSearch.setText("");
                switch (newSelection)//Switch on choiceBox value
                {
                    case "All":
                        gridViewFilteredList.setPredicate(p -> true);
                        break;
                    default:
                        gridViewFilteredList.setPredicate(p -> p.getProvider().equals(cbMain.getValue()));
                        break;

                }
            }
        });
    }

    public void loadGridView()
    {
        gridViewObservableList.addAll(ikonItemList);
        gridViewFilteredList = new  FilteredList(gridViewObservableList, p -> true);
        gvMain.setItems(gridViewFilteredList);
        gvMain.setHorizontalCellSpacing(2);
        gvMain.setVerticalCellSpacing(2);
        gvMain.setCellWidth(175);
        gvMain.setCellHeight(225);
        gvMain.setCellFactory(gridView -> {
            return new GridCell<IkonItem>()
            {
                final CellViewController cellViewController = new CellViewController();

                @Override
                public void updateItem(IkonItem item, boolean empty)
                {
                    if (empty || item == null)


                    {
                        setText(null);
                        setGraphic(null);
                    }
                    else
                    {
                        cellViewController.setId(item.getIndex());
                        cellViewController.setLblIconName(item.getName());
                        cellViewController.setLblEnumName(item.getEnumName());
                        cellViewController.setFiIcon(item.getName());
                        cellViewController.setProvider(item.getProvider());
                        setGraphic(cellViewController);
                    }

                }
            };
        });
    }
}
