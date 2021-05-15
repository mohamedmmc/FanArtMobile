/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.FloatingHint;
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
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.service.ServiceUser;
import com.mycompany.myapp.utils.Sms;

/**
 *
 * @author splin
 */
public class ConfirmationOtp extends BaseForm {
     public ConfirmationOtp(String num, Resources res) {
        super(new BorderLayout());
        Toolbar tb = new Toolbar(true);
        ServiceUser su = new ServiceUser();
        Sms sms = new Sms();
                sms.otpsend(num);
        setToolbar(tb);
        tb.setUIID("Container");
        getTitleArea().setUIID("Container");
        Form previous = Display.getInstance().getCurrent();
        tb.setBackCommand("", e -> previous.showBack());
        setUIID("SignIn");
        add(BorderLayout.NORTH, 
                BoxLayout.encloseY(
                        new Label(res.getImage("otp.png"), "LogoLabel"),
                        new Label("Confirmation code", "LogoLabel")
                )
        );
        TextField otp = new TextField("","Tapper le code à 6 chiffres",6,TextField.NUMERIC);
        otp.setSingleLineTextArea(false);
        
        Button valider = new Button ("Valider");
        Button signIn = new Button("Retour");
        signIn.addActionListener(e-> previous.showBack());
        signIn.setUIID("CenterLink");
        
        Container content = BoxLayout.encloseY(
                new FloatingHint(otp),
                createLineSeparator(),
                valider
                ,signIn
        );
        content.setScrollableY(true);
        add(BorderLayout.CENTER,content);
        valider.requestFocus();
        valider.addActionListener((evt) -> {
             
            if (sms.otpverify(num, otp.getText().toString())) {
                new ResetPass(num, res).show();
            }
            else{
                Dialog.show("Code incorrect", "Veuillez rentrer a nouveau votre code !", "OK", null);
            }
        });
}
}
