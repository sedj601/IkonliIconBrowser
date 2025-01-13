package org.sedj601.fun;

import javafx.application.HostServices;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DataModel
{
    DataModel(){}

    final private ObservableList<IkonItem> gridViewObservableList = FXCollections.observableArrayList();

    public ObservableList<IkonItem> getGridViewObservableList() {
        return this.gridViewObservableList;
    }

    final private ObservableList<String> comboBoxObservableList = FXCollections.observableArrayList();

    public ObservableList<String> getComboBoxObservableList()
    {
        return this.comboBoxObservableList;
    }

    private HostServices hostServices;

    public void setHostServices(HostServices hostServices)
    {
        this.hostServices = hostServices;
    }

    public HostServices getHostServices()
    {
        return this.hostServices;
    }
}
