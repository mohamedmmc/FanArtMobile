/*
 * Copyright (c) 2016, Codename One
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, 
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions 
 * of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A 
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT 
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE 
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 */
package com.mycompany.gui;

import com.codename1.components.FloatingHint;
import com.codename1.components.InfiniteProgress;
import com.codename1.components.ToastBar;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import com.codename1.ui.validation.RegexConstraint;
import com.codename1.ui.validation.Validator;
import com.mycompany.myapp.service.ServiceUser;
import java.io.IOException;
import java.util.Vector;

/**
 * Signup UI
 *
 * @author Shai Almog
 */
public class SignUpForm extends BaseForm {

    private String filename = "";
    private String filepath = "";

    public SignUpForm(Resources res) {
        super(new BorderLayout());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        tb.setUIID("Container");
        getTitleArea().setUIID("Container");
        Form previous = Display.getInstance().getCurrent();
        tb.setBackCommand("", e -> previous.showBack());
        setUIID("SignIn");
        ServiceUser su = new ServiceUser();
        TextField nom = new TextField("", "Nom", 20, TextField.ANY);
        TextField prenom = new TextField("", "Prenom", 20, TextField.ANY);
        TextField email = new TextField("", "E-Mail", 20, TextField.EMAILADDR);
        TextField password = new TextField("", "Mot de passe", 20, TextField.PASSWORD);
        TextField confirmPassword = new TextField("", "Repeter mot de passe", 20, TextField.PASSWORD);
        TextField numtel = new TextField("", "Numero de téléphone", 20, TextField.PHONENUMBER);
        Vector<String> vectorRoles;
        vectorRoles = new Vector();
        vectorRoles.add("Client");
        vectorRoles.add("Artiste");
        ComboBox<String> roles = new ComboBox<>(vectorRoles);
        nom.setSingleLineTextArea(false);
        prenom.setSingleLineTextArea(false);
        email.setSingleLineTextArea(false);
        password.setSingleLineTextArea(false);
        confirmPassword.setSingleLineTextArea(false);
        Button browse = new Button("Parcourir");
        Button next = new Button("Créer compte");
        Button signIn = new Button("Se connecter");
        signIn.addActionListener(e -> previous.showBack());
        signIn.setUIID("Link");
        Label alreadHaveAnAccount = new Label("Vous avez déjà un compte ?");
        Label ivv = new Label(res.getImage("pdp.png"), "Pdp");
        Dimension d = new Dimension(512, 512);
        ivv.setMaxAutoSize(BOTTOM);
        Container content = BoxLayout.encloseY(
                new Label("Création de compte", "LogoLabel"),
                new FloatingHint(nom),
                createLineSeparator(),
                new FloatingHint(prenom),
                createLineSeparator(),
                new FloatingHint(email),
                createLineSeparator(),
                new FloatingHint(password),
                createLineSeparator(),
                new FloatingHint(confirmPassword),
                createLineSeparator(),
                new FloatingHint(numtel),
                createLineSeparator(),
                browse, ivv,
                createLineSeparator(),
                roles
        );
        content.setScrollableY(true);
        add(BorderLayout.CENTER, content);
        add(BorderLayout.SOUTH, BoxLayout.encloseY(
                next,
                FlowLayout.encloseCenter(alreadHaveAnAccount, signIn)
        ));
        next.requestFocus();

        browse.addActionListener((evt) -> {
            ActionListener callback = e -> {
                if (e != null && e.getSource() != null) {
                    filepath = (String) e.getSource();
                    try {
                        Image img = Image.createImage(filepath).scaledWidth(Math.round(Display.getInstance().getDisplayWidth() / 2));
//                        ImageViewer iv = new ImageViewer(img);
//                        add(BorderLayout.CENTER_BEHAVIOR_SCALE,iv);
                        ivv.setIcon(img);
                        int fileNameIndex = filepath.lastIndexOf("/") + 1;
                        filename = filepath.substring(fileNameIndex);
                        revalidate();

                    } catch (IOException ex) {

                    }
                }
            };
            Display.getInstance().openGallery(callback, Display.GALLERY_IMAGE);
        });
        next.addActionListener((evt) -> {
            Validator validator = new Validator();
            boolean dialog = false;
            RegexConstraint emailConstraint = new RegexConstraint("^[(a-zA-Z-0-9-\\_\\+\\.)]+@[(a-z-A-z)]+\\.[(a-zA-z)]{2,3}$", "Invalid Email Address");
            validator.addConstraint(email, RegexConstraint.validEmail());

            if (!validator.isValid()) {
                dialog = true;
                ToastBar.showErrorMessage("Mail incorrect");
            } else if (!su.verifyEMAIL(email.getText(), res).equals("mailfailed")) {
                ToastBar.showErrorMessage("Mail déjà existant");
                dialog = true;
            } else if (!su.verifynum(numtel.getText(), res).equals("numfailed")) {
                dialog = true;
                ToastBar.showErrorMessage("Numéro déjà existant");
            } else if (nom.getText().equals("") || prenom.getText().equals("") || email.getText().equals("") || password.getText().equals("") || filename.equals("")){
                dialog = true;
                ToastBar.showErrorMessage("Veuillez remplir tout les champs");
            } else if (!password.getText().equals(confirmPassword.getText())) {
                dialog = true;
                ToastBar.showErrorMessage("Les mots de passe ne sont pas identiques");
            } else if (numtel.getText().length() != 8) {
                dialog = true;
                ToastBar.showErrorMessage("Numéro de téléphone invalide");
            } else {
                Dialog ip = new InfiniteProgress().showInifiniteBlocking();
                ServiceUser.getInstance().SignUp(nom, prenom, email, password, confirmPassword, filename, numtel, roles, res);
                try {
                    su.sendphp(filepath);
                    ip.dispose();
                    Dialog.show("Bienvenue", "Création du profil avec succes", "ok", null);
                    new SignInForm(res).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            if (dialog) {
                Dialog.show("Erreur", "Echec de la création du compte", "ok", null);
            }
        });
    }

}
