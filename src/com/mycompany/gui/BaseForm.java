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

import com.codename1.components.ScaleImageLabel;
import com.codename1.ui.Component;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.layouts.Layout;
import com.codename1.ui.plaf.Style;
import com.mycompany.myapp.service.ServiceUser;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entity.User;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.Random;
import jdk.internal.dynalink.beans.StaticClass;

/**
 * Base class for the forms with common functionality
 *
 * @author Shai Almog
 */
public class BaseForm extends Form {

    public BaseForm() {
    }

    public BaseForm(Layout contentPaneLayout) {
        super(contentPaneLayout);
    }

    public BaseForm(String title, Layout contentPaneLayout) {
        super(title, contentPaneLayout);
    }

    public Component createLineSeparator() {
        Label separator = new Label("", "WhiteSeparator");
        separator.setShowEvenIfBlank(true);
        return separator;
    }

    public Component createLineSeparator(int color) {
        Label separator = new Label("", "WhiteSeparator");
        separator.getUnselectedStyle().setBgColor(color);
        separator.getUnselectedStyle().setBgTransparency(255);
        separator.setShowEvenIfBlank(true);
        return separator;
    }

    public void addSideMenu(String id, Resources res) {
        Toolbar tb = getToolbar();
        ServiceUser su = new ServiceUser();
        User u = new User();
        System.out.println(Statics.session);
        if (!Statics.session.equals("")){
            u=su.Profil(id);
        }
            
        Image img = res.getImage("profile-background.jpg");
        Random a = new Random();
        int mm = Display.getInstance().convertToPixels(3); 
        EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(mm * 7, mm * 7, 0), true);
        Image icon1 = URLImage.createToStorage(placeholder, a.toString(), "http://localhost:80/img/"+u.getPhoto());
        
        
        if (img.getHeight() > Display.getInstance().getDisplayHeight() / 3) {
            img = img.scaledHeight(Display.getInstance().getDisplayHeight() / 3);
        }
        ScaleImageLabel sl = new ScaleImageLabel(img);
        sl.setUIID("BottomPad");
        sl.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        
       
        tb.addComponentToSideMenu(LayeredLayout.encloseIn(
                sl,
                FlowLayout.encloseCenterBottom(
                        new Label(icon1))
        ));

        tb.addMaterialCommandToSideMenu("Accueil", FontImage.MATERIAL_UPDATE, e -> new NewsfeedForm(id, res).show());
        tb.addMaterialCommandToSideMenu("Profil", FontImage.MATERIAL_SETTINGS, e -> {
            new ProfileForm(id, res).show();
        });
        tb.addMaterialCommandToSideMenu("Evenement", FontImage.MATERIAL_UPDATE, e -> new EventForm(id, res).show());

        tb.addMaterialCommandToSideMenu("Déconnexion", FontImage.MATERIAL_EXIT_TO_APP, e -> new WalkthruForm(res).show());
    }
}
