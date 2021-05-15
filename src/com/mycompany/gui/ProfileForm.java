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

import com.codename1.components.InteractionDialog;
import com.codename1.components.ScaleImageLabel;
import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Component;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entity.User;
import com.mycompany.myapp.service.ServiceUser;
import com.mycompany.myapp.utils.Statics;
import java.util.Random;

/**
 * The user profile form
 *
 * @author Shai Almog
 */
public class ProfileForm extends BaseForm {

    public User u = new User();

    public ProfileForm(String id, Resources res) {
        super("Newsfeed", BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Profil");
        getContentPane().setScrollVisible(false);

        super.addSideMenu(id, res);

        tb.addSearchCommand(e -> {
        });

        Image img = res.getImage("profile-background.jpg");
        if (img.getHeight() > Display.getInstance().getDisplayHeight() / 3) {
            img = img.scaledHeight(Display.getInstance().getDisplayHeight() / 3);
        }
        ScaleImageLabel sl = new ScaleImageLabel(img);
        sl.setUIID("BottomPad");
        sl.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);

        int mm = Display.getInstance().convertToPixels(3);
        ServiceUser su = new ServiceUser();

        u = su.Profil(id);
        Random a = new Random();
        Button modifier = new Button("Modifier");
        Button modifiermdp = new Button("Modifier mot de passe");
        Button supprimer = new Button("Supprimer le profil");
        EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(mm * 7, mm * 7, 0), true);
        Image icon1 = URLImage.createToStorage(placeholder, a.toString(), "http://localhost:8080/img/" + u.getPhoto());
        add(LayeredLayout.encloseIn(
                sl,
                FlowLayout.encloseCenterBottom(
                        new Label(icon1))
        )
        );

        Label username = new Label(u.getNom());
        username.setUIID("TextFieldBlack");
        addStringValue("Nom", username);

        Label prenom = new Label(u.getPrenom());
        prenom.setUIID("TextFieldBlack");
        addStringValue("Prenom", prenom);

        Label email = new Label(u.getEmail());
        email.setUIID("TextFieldBlack");
        addStringValue("Email", email);

        Label numtel = new Label(String.valueOf(u.getNum()));
        numtel.setUIID("TextFieldBlack");
        addStringValue("Numero de téléphone", numtel);

        Label type = new Label(u.getType(), u.getType());
        type.setUIID("TextFieldBlack");
        addStringValue("Type", type);
        add(BoxLayout.encloseY(
                modifier, modifiermdp, supprimer));
        modifier.addActionListener((evt) -> {
            new ModifierProfil(u, res).show();

        });

        modifiermdp.addActionListener((evt) -> {
            new ConfirmationOtp(String.valueOf(u.getNum()), res).show();
        });
        supprimer.addActionListener((evt) -> {

            if (Dialog.show("Supression", "Voulez-vous vraiment supprimer " + u.getNom()+" ?", "OK", "Annuler")) {
               Statics.session = "";
                new SignInForm(res).show();
                
                su.delUser(id, res);
            }

        });
    }

    private void addStringValue(String s, Component v) {
        add(BorderLayout.west(new Label(s, "PaddedLabel")).
                add(BorderLayout.CENTER, v));
        add(createLineSeparator(0xeeeeee));
    }
}
