package org.sedj601.fun;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;

public class CellViewController extends VBox
{
    public CellViewController()
    {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("cell_view.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected Label lblIconName, lblEnumName, lblId, lblProvider;
    @FXML
    protected FontIcon fiIconNameCopy, fiEnumNameCopy, fiIcon, fiIdCopy;

    public void setLblIconName(String iconName)
    {
        lblIconName.setText(iconName);
    }

    public void setLblEnumName(String enumName)
    {
        lblEnumName.setText(enumName);
    }

    public void setFiIconNameCopyOnMouseClicked(EventHandler<MouseEvent> onMouseClicked)
    {
        fiIconNameCopy.setOnMouseClicked(onMouseClicked);
    }

    public void setFiEnumNameCopyOnMouseClicked(EventHandler<MouseEvent> onMouseClicked)
    {
        fiEnumNameCopy.setOnMouseClicked(onMouseClicked);
    }

    public void setFiIconIdCopyOnMouseClicked(EventHandler<MouseEvent> onMouseClicked)
    {
        fiIdCopy.setOnMouseClicked(onMouseClicked);
    }

    public void setFiIcon(String iconCode)
    {
        fiIcon.setIconLiteral(iconCode);
    }

    public void setId(int id)
    {
        lblId.setText(Integer.toString(id));
    }

    public void setProvider(String provider)
    {
        lblProvider.setText(provider);
    }
}
