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
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.service.ServiceUser;
import com.mycompany.myapp.utils.Sms;

/**
 * Account activation UI
 *
 * @author Shai Almog
 */
public class ActivateForm extends BaseForm {

    public ActivateForm(Resources res) {
        super(new BorderLayout());
        ServiceUser su = new ServiceUser();
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        tb.setUIID("Container");
        getTitleArea().setUIID("Container");
        Form previous = Display.getInstance().getCurrent();
        tb.setBackCommand("", e -> previous.showBack());
        setUIID("Activate");

        add(BorderLayout.NORTH,
                BoxLayout.encloseY(
                        new Label(res.getImage("mails.png"), "LogoLabel"),
                        new Label("Mot de passe oublié", "LogoLabel")
                )
        );
        TextField num = new TextField("", "Saisir votre numéro de téléphone", 8, TextField.NUMERIC);
        num.setSingleLineTextArea(false);

        Button valider = new Button("Valider");
        Button signIn = new Button("Retour");
        signIn.addActionListener(e -> previous.showBack());
        signIn.setUIID("CenterLink");

        Container content = BoxLayout.encloseY(
                new FloatingHint(num),
                createLineSeparator(),
                valider,
                 signIn
        );
        content.setScrollableY(true);
        add(BorderLayout.CENTER, content);
        valider.requestFocus();
        valider.addActionListener((evt) -> {
            if (num.getText().length() != 8) {
                Dialog.show("Numero invalide", "Veuillez rentrer un numero a 8 chiffres", "OK", null);
            } else if (su.getNumId(num.getText()).equals("numfailed")) {
                Dialog.show("Numero inexistant", "Veuillez rentrer un numero qui existe", "OK", null);

            } else {

                if (!su.verifynumOTP(num.getText(), res).equals("failed")) {
                    new ConfirmationOtp(num.getText(), res).show();
                } else {
                    Dialog.show("OK", "Le numero n'existe pas", "OK", null);
                }
            }

//            InfiniteProgress ip = new InfiniteProgress();
//            final Dialog ipDialog = ip.showInfiniteBlocking();
        });
//        TextField code = new TextField("", "Entrer Code", 20, TextField.PASSWORD);
//        code.setSingleLineTextArea(false);
//        
//        Button signUp = new Button("Création compte");
//        Button resend = new Button("Renvoyez ");
//        resend.setUIID("CenterLink");
//        Label alreadHaveAnAccount = new Label("Vous avez déjà un compte ?");
//        Button signIn = new Button("Sign In");
//        signIn.addActionListener(e -> previous.showBack());
//        signIn.setUIID("CenterLink");
//        
//        Container content = BoxLayout.encloseY(
//                new FloatingHint(code),
//                createLineSeparator(),
//                new SpanLabel("Nous avons envoyé un mail contenant votre code d'activation", "CenterLabel"),
//                resend,
//                signUp,
//                FlowLayout.encloseCenter(alreadHaveAnAccount, signIn)
//        );
//        content.setScrollableY(true);
//        add(BorderLayout.SOUTH, content);
//        signUp.requestFocus();
//        signUp.addActionListener(e -> new NewsfeedForm(res).show());
    }

    public void sendMail() {

    }

}
