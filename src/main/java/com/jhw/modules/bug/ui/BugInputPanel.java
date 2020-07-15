package com.jhw.modules.bug.ui;

import com.clean.core.app.services.ExceptionHandler;
import com.jhw.modules.bug.core.domain.Bug;
import java.awt.*;
import java.awt.image.BufferedImage;
import com.jhw.modules.bug.core.app.BugHandler;
import com.jhw.swing.material.components.container.layout.VerticalLayoutContainer;
import com.jhw.swing.material.components.textarea._MaterialTextArea;
import com.jhw.swing.material.components.textfield.validated._MaterialTextFieldStringNotEmpty;
import com.jhw.swing.models.input.panels.ModelPanel;

/**
 *
 * @author Jesús Hernández Barrios (jhernandezb96@gmail.com)
 */
public class BugInputPanel extends ModelPanel<Bug> {

    private BufferedImage shot;

    public BugInputPanel() {
        super(null);
        try {
            shot = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
        } catch (Exception e) {
        }
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        textAreaDescripcion = new _MaterialTextArea();
        textFieldBug = new _MaterialTextFieldStringNotEmpty();

        textFieldBug.setHint("Bug encontrado");
        textFieldBug.setLabel("Bug");
        textAreaDescripcion.setTitledBorder("Descripción");
        
        VerticalLayoutContainer.builder vlc = VerticalLayoutContainer.builder();

        vlc.add(textFieldBug);
        vlc.add(textAreaDescripcion);
        super.setComponent(vlc.build());
    }

    // Variables declaration - do not modify
    private _MaterialTextArea textAreaDescripcion;
    private _MaterialTextFieldStringNotEmpty textFieldBug;
    // End of variables declaration                   

    @Override
    public Bug getNewModel() {
        String bug = textFieldBug.getText();
        String desc = textAreaDescripcion.getText();
        return new Bug(bug, desc, shot);
    }

    @Override
    public Bug onDeleteAction() {
        return null;
    }

    @Override
    public Bug onCreateAction() {
        try {
            Bug bug = getNewModel();
            BugHandler.reportBug(bug);
            return bug;
        } catch (Exception e) {
            ExceptionHandler.getExceptionHandlerService().handleException(e);
        }
        return null;
    }

    @Override
    public boolean onCancelAction() {
        return true;
    }

}