package org.sedj601.fun;

import javafx.application.HostServices;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.IkonProvider;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;
import java.util.*;

public class SplashScreenViewController
{
    @FXML
    Label lblLoadingIconPacks;
    @FXML
    FontIcon fiIcon;

    List<IkonItem> ikonItemList = new ArrayList<>();
    List<String> providerList = new ArrayList<>();
    HostServices hostServices;
    DataModel dataModel = new DataModel();

    Task<Void> loadDataTask =   new Task<Void>()
                                {
                                    @Override
                                    protected Void call()
                                    {
                                        Set<String> providerSet = new TreeSet<>();
                                        ServiceLoader<IkonProvider> providers = ServiceLoader.load(IkonProvider.class);
                                        for(IkonProvider provider : providers)
                                        {
                                            Object[] enumConstants = provider.getIkon().getEnumConstants();
                                            for (Object enumConstant : enumConstants) {
                                                Ikon tempIkon = (Ikon) enumConstant;
                                                IkonItem ikonItem = new IkonItem(tempIkon.getCode(), tempIkon.getDescription(), tempIkon.toString(), provider.getIkon().getSimpleName());
                                                ikonItemList.add(ikonItem);

                                                providerSet.add(provider.getIkon().getSimpleName());
                                                updateMessage(tempIkon.getDescription());
                                            }
                                        }

                                        providerList.add("All");
                                        providerList.addAll(providerSet);
                                        return null;
                                    }
                                };

    public void initHostServices(HostServices hostServices)
    {
        if (this.hostServices != null) {
            throw new IllegalStateException("HostServices can only be initialized once");
        }
        this.hostServices = hostServices ;


        loadDataTask.setOnSucceeded(onSucceeded ->
        {
            //load primary scene!
            try
            {
                dataModel.setHostServices(this.hostServices);
                dataModel.getGridViewObservableList().addAll(ikonItemList);
                dataModel.getComboBoxObservableList().addAll(providerList);
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("primary.fxml"));
                Parent root = fxmlLoader.load();
                PrimaryController primaryController = fxmlLoader.getController();
                primaryController.initDataModel(dataModel);

                Scene scene = new Scene(root);
                Stage stage = (Stage)lblLoadingIconPacks.getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            }
            catch (IOException e)
            {
                System.out.println(e.toString());
            }
        });
        loadDataTask.messageProperty().addListener((observableValue, oldString, newString) ->
        {
            fiIcon.setIconLiteral(newString);
        });
        Thread thread = new Thread(loadDataTask);
        thread.setDaemon(true);
        thread.start();
    }
}
