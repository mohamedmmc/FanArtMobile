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
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.mycompany.myapp.entity.Evenement;
import com.mycompany.myapp.service.ServiceEvents;
import com.mycompany.myapp.utils.GenerateQRCode;
import static com.mycompany.myapp.utils.GenerateQRCode.generateQRcode;
import com.mycompany.myapp.utils.MailSender;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.stripe.model.Token;
import java.io.IOException;

/**
 * GUI builder created Form
 *
 * @author Ben Gouta Monam
 */
public class ParticiperForm extends com.codename1.ui.Form {

    public ParticiperForm(String json, Resources res) {
        super(new BorderLayout());
        Toolbar tb = new Toolbar(true);

        setToolbar(tb);
        tb.setUIID("Container");
        // getTitleArea().setUIID("Container");
        Form previous = Display.getInstance().getCurrent();
        tb.setBackCommand("", e -> previous.showBack());
        //setUIID("Evenement");
        ServiceEvents su = new ServiceEvents();
        TextField num_compte_id = new TextField("", "Numero carte", 20, TextField.ANY);
        TextField date_month = new TextField("", "le mois d'expiration", 20, TextField.ANY);
        TextField date_year = new TextField("", "l'anné d'expiration", 20, TextField.ANY);
        TextField cvc = new TextField("", "c vs", 20, TextField.ANY);
        TextField Nbillet = new TextField("", "Nombre de billet", 20, TextField.ANY);
        num_compte_id.setUIID("color: #FFFFFF");
        num_compte_id.getAllStyles().setAlignment(RIGHT);
        date_month.setUIID("color: #FFFFFF");
        date_year.setUIID("color: #FFFFFF");
        cvc.setUIID("color: #FFFFFF");
        Nbillet.setUIID("color: #FFFFFF");
        Nbillet.setText("1");

        Button save = new Button("Enregistrer");
        Button annuler = new Button("Annuler");
        annuler.addActionListener(e -> previous.showBack());

        Dimension d = new Dimension(512, 512);

        Label ivv = new Label(res.getImage("image.png"), "Pdp");
        Container buttoncontent = BoxLayout.encloseXCenter(save, annuler);
        Container content = BoxLayout.encloseY(
                new Label("Création d'évenement", "LogoLabel"),
                new FloatingHint(num_compte_id),
                new FloatingHint(date_month),
                new FloatingHint(date_year),
                new FloatingHint(cvc),
                new FloatingHint(Nbillet),
                ivv,
                buttoncontent
        );
        content.setScrollableY(true);
        add(BorderLayout.CENTER, content);

        save.requestFocus();
        save.addActionListener((evt) -> {
            try {
                Stripe.apiKey = "sk_test_51IYHfGBQ0LLhBexiKiPzJjHM7f7z3koVIrDiiEr4hfUu35iV558XKAIZIiY3Xbm9tkF6zCn0fEjTXRpt4aIYmpww00p9s6z11h";
                Customer a = Customer.retrieve("cus_JAdxOt3MYaPCuU");
                /*CustomerListParams params =CustomerListParams.builder().build();
            CustomerCollection customers =
            Customer.list(params);
            System.out.println(customers);*/
                Map<String, Object> cardParam = new HashMap<String, Object>();
                if (!(num_compte_id.getText().equals("") || date_month.getText().equals("") || date_year.getText().equals("") || cvc.getText().equals(""))) {
                    cardParam.put("number", num_compte_id.getText());
                    cardParam.put("exp_month", Integer.parseInt(date_month.getText()));
                    cardParam.put("exp_year", Integer.parseInt(date_year.getText()));
                    cardParam.put("cvc", cvc.getText());
                } else {
                    Dialog.show("Bienvenue", "SVP verifiez vous cordonné", "ok", null);

                }
                Map<String, Object> tokenParam = new HashMap<String, Object>();
                tokenParam.put("card", cardParam);

                Token token = Token.create(tokenParam);

                Map<String, Object> source = new HashMap<String, Object>();
                source.put("source", token.getId());

                Gson gson = new GsonBuilder().setPrettyPrinting().create();

                Map<String, Object> chargeParam = new HashMap<String, Object>();
                Evenement evenement = new Evenement();
                ArrayList<Evenement> event = ServiceEvents.getInstance().getAllTasks();
                for (Evenement it : event) {
                    if (it.getId_evenement() == com.mycompany.myapp.utils.Statics.idevenement) {
                        evenement = it;
                    }
                }

                int prixtotal = Integer.parseInt(Nbillet.getText()) * evenement.getPrix();
                chargeParam.put("amount", prixtotal * 100);
                chargeParam.put("currency", "usd");
                chargeParam.put("source", token.getId());

                Charge.create(chargeParam);
                /*
          generer code qr qui contient un id crypt& pour une billet
                 */
                //////////////////////////////

//data that we want to store in the QR code  
                String str = com.mycompany.myapp.utils.Statics.session + com.mycompany.myapp.utils.Statics.idevenement;
//path where we want to get QR Code  
                String path = "C:\\xampp\\htdocs\\img\\qrcode.png";
//Encoding charset to be used  
                String charset = "UTF-8";
                Map<EncodeHintType, ErrorCorrectionLevel> hashMap = new HashMap<EncodeHintType, ErrorCorrectionLevel>();
//generates QR code with Low level(L) error correction capability  
                hashMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
//invoking the user-defined method that creates the QR code  
                GenerateQRCode gr = new GenerateQRCode();
                generateQRcode(str, path, charset, hashMap, 200, 200);//increase or decrease height and width accodingly   

                //generateQRcode(str, path, charset, hashMap, 200, 200);//increase or decrease height and width accodingly   
//prints if the QR code is generated   
       

                /////////////////////////////////////
                
                try {
                    
                    MailSender s = new MailSender();
                   s.sendQRcode(com.mycompany.myapp.utils.Statics.mailuser);
                                   previous.showBack();

                } catch (Exception e) {
                    e.printStackTrace();
                }


            } catch (StripeException e) {
                System.out.println(e.getMessage());
            } catch (WriterException ex) {
            } catch (IOException ex) {
            }
        });

    }

//////////////////////////////////////////////////////////////////-- DON'T EDIT BELOW THIS LINE!!!


// <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initGuiBuilderComponents(com.codename1.ui.util.Resources resourceObjectInstance) {
        setLayout(new com.codename1.ui.layouts.FlowLayout());
        setInlineStylesTheme(resourceObjectInstance);
                setInlineStylesTheme(resourceObjectInstance);
        setTitle("ParticiperForm");
        setName("ParticiperForm");
    }// </editor-fold>

//-- DON'T EDIT ABOVE THIS LINE!!!
}
