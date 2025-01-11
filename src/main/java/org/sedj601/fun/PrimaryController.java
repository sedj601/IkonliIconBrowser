package org.sedj601.fun;

import java.util.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import org.controlsfx.control.GridCell;
import org.controlsfx.control.GridView;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.IkonProvider;

import static java.util.Arrays.asList;
import static java.util.EnumSet.allOf;

public class PrimaryController
{
    @FXML
    GridView<IkonItem> gvMain;

    @FXML
    protected void initialize()
    {
        loadGridView();
    }

    public void loadGridView()
    {
        List<IkonItem> ikonItemList = new ArrayList<>();
        List<String> providerList = new ArrayList<>();

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

        ObservableList<IkonItem> observableList = FXCollections.observableArrayList(ikonItemList);
        gvMain.setItems(observableList);
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
                    if (empty || item == null) {
                        setText(null);
                        setGraphic(null);
                    }
                    else {
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
