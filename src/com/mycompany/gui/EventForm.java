/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.FloatingHint;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Container;
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
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entity.Evenement;
import com.mycompany.myapp.service.ServiceEvents;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

/**
 * GUI builder created Form
 *
 * @author Ben Gouta Monam
 */
public class EventForm extends BaseForm {
    
    private String filename = "";
    private String filepath = "";

    public EventForm(String json,Resources res) {
        super(new BorderLayout());
        Toolbar tb = new Toolbar(true);

       setToolbar(tb);
        tb.setUIID("Container");
       // getTitleArea().setUIID("Container");
        Form previous = Display.getInstance().getCurrent();
        tb.setBackCommand("", e -> previous.showBack());
        //setUIID("Evenement");
        ServiceEvents su =new ServiceEvents();
        TextField titre = new TextField("", "Titre", 20, TextField.ANY);
        TextField nbplace=new TextField("","Nombre de place", 20, TextField.ANY);
        TextField prix=new TextField("","Prix unitaire", 20, TextField.NUMERIC);
        TextField Description = new TextField("", "Description", 20, TextField.ANY);
        Vector<String> vectorRoles;
        vectorRoles = new Vector();
        vectorRoles.add("01");
        vectorRoles.add("02");
        ComboBox<String> roles = new ComboBox<>(vectorRoles);
        titre.setSingleLineTextArea(false);
        Description.setSingleLineTextArea(false);
        Picker datedebut=new Picker();
        Picker datefin=new Picker();

        Button browse = new Button("Parcourir");
        Button  save = new Button("Enregistrer");
        Button annuler = new Button("Annuler");
        annuler.addActionListener(e -> previous.showBack());
        
      
       
        Dimension d = new Dimension(512, 512);
        titre.setUIID("color: #FFFFFF");
        Description.setUIID("color: #FFFFFF");
         datedebut.setUIID("color: #FFFFFF");
         datefin.setUIID("color: #FFFFFF");
         nbplace.setUIID("color: #FFFFFF");
         prix.setUIID("color: #FFFFFF");
         Label ivv = new Label(res.getImage("image.png"), "Pdp");
        Container buttoncontent =BoxLayout.encloseXCenter(save,annuler);
        Container content = BoxLayout.encloseY(
                new Label("Création d'évenement", "LogoLabel"),
                new FloatingHint(titre),
                new FloatingHint(Description),
                new Label("Date debut"),
                datedebut,
                new Label("Date fin"),
                datefin,
                roles,
                new Label("Nombre de place"),
                nbplace,
                new Label("Prix unitaire"),
                prix,
                browse,
                ivv,
                buttoncontent
               
               
                
        );
        content.setScrollableY(true);
        add(BorderLayout.CENTER, content);
       
         save.requestFocus();

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
                        

                    } catch (IOException ex) {

                    }
                }
            };
            Display.getInstance().openGallery(callback, Display.GALLERY_IMAGE);
        });
         save.addActionListener((evt) -> {
            String titrein=titre.getText();
            String descin=Description.getText();
            Date datedbin=(Date) datedebut.getValue();
            Date datefnin=(Date) datefin.getValue();
            String salle=roles.getSelectedItem();
            String datedbafterparsin=new SimpleDateFormat("MM/dd/yyyy").format(datedbin);
            String datefnafterparsin=new SimpleDateFormat("MM/dd/yyyy").format(datefnin);
            String nombreplace=nbplace.getText();
            String price=prix.getText();
             Evenement evenement=new Evenement();
             evenement.setTitre(titrein);
             evenement.setDescription(descin);
             evenement.setDate_debut(datedbafterparsin);
             evenement.setDate_Fin(datefnafterparsin);
             evenement.setLocall(salle);
             evenement.setNombre_place(Integer.parseInt(nombreplace));
             evenement.setPrix(Integer.parseInt(price));
             evenement.setImage(filename);
             ServiceEvents.getInstance().addEvent(evenement);
            
            try {
                ServiceEvents.getInstance().sendphp(filepath);
            } catch (IOException ex) {
                System.out.println("lllllllllllllll");
                      
            }
             
            
            
             
            
            
         });
    }
//
//
//    public EventForm() {
//        this(com.codename1.ui.util.Resources.getGlobalResources());
//        
//
//    }
//
//    public EventForm(com.codename1.ui.util.Resources resourceObjectInstance) {
//        initGuiBuilderComponents(resourceObjectInstance);
//        Toolbar tb =new Toolbar();
//        
//
//        //sallecontainer.addAll(sallelabel,listsalle);
//        
//        titrecontainer.addAll(titrelabel, titre);
//        descriptioncontainer.addAll(descriptionlabel, description);
//        
//        picker.setStrings(listsalle);
//        sallecontainer.addAll(sallelabel, picker);
//        datedebut .setFormatter(new com.codename1.l10n.SimpleDateFormat("dd-mm-yyyy"));
//        datedebutcontainer.addAll(datedebutlabel,datedebut);
//        datefincontainer.addAll(datefinlabel,datefin);
//        buttonscontainer.addAll(savebtn,cancelbtn);
//        imagebtn.addActionListener((evt) -> {
//            
//            String path = Capture.capturePhoto(Display.getInstance().getDisplayWidth(), -1);
//            if (path != null) {
//                try {
//                    Image img = Image.createImage(path);
//                    imagevent.setWidth(800);
//                    imagevent.setHeight(800);
//                    imagevent.setImage(img);
//                    Date date = Calendar.getInstance().getTime();
//        
//        
//        
//        System.out.println(picker.getValue());
//                    
//                } catch (IOException ex) {
//                    ex.printStackTrace();
//                }
//            }
//        
//        });
//        
//        imagecontainer.addAll(imagebtn,imagevent);
//        Container vcontainer = BoxLayout.encloseY(titrecontainer, descriptioncontainer, sallecontainer,datedebutcontainer,datefincontainer,imagecontainer,buttonscontainer);
//       
//
//        addAll(vcontainer);
//        cancelbtn.addActionListener((evt) -> {
//            titre.setText("");
//        });
//        savebtn.addActionListener((evt) -> {
//            System.out.println(titre.getText());
//            System.out.println(description.getText());
//            
//            System.out.println(picker.getValue());
//            System.out.println(datedebut.getValue());
//            System.out.println(datefin.getValue());
//            
//            
//            
//        });
//
//    }
//
//    Container titrecontainer = new Container(new BoxLayout(TOP));
//    Label titrelabel = new Label("titre");
//    TextField titre = new TextField("", "Titre", 10, TextField.ANY);
//    ////////////////
//    Container descriptioncontainer = new Container(new BoxLayout(TOP));
//    Label descriptionlabel = new Label("Description");
//    TextField description = new TextField("", "Description", 10, TextField.ANY);
//    ////////////////
//    Container sallecontainer = new Container(new BoxLayout(CENTER));
//    Label sallelabel = new Label("Salle");
//       
//    Picker picker=new Picker();
//    
//
//    ///////////////
//    Container datedebutcontainer = new Container(new BoxLayout(TOP));
//    Label datedebutlabel = new Label("Date debut");
//    Picker datedebut = new Picker();
//    ///////////////
//    Container datefincontainer = new Container(new BoxLayout(TOP));
//    Label datefinlabel = new Label("Date fin");
//    String[] listsalle={"01","03"};
//    
//    Picker datefin = new Picker();
//    ///////////////////////////
//        Container imagecontainer = new Container(new BoxLayout(CENTER));
//        Button imagebtn=new Button("Selectionne une photo");
//        ImageViewer imagevent=new ImageViewer();
//        
//
//    ////////////
//    Container buttonscontainer = new Container(new BoxLayout(TOP));
//    Button savebtn = new Button("Enregistrer");
//    Button cancelbtn = new Button("Annuler");
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-- DON'T EDIT BELOW THIS LINE!!!


// <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initGuiBuilderComponents(com.codename1.ui.util.Resources resourceObjectInstance) {
        setLayout(new com.codename1.ui.layouts.LayeredLayout());
        setInlineStylesTheme(resourceObjectInstance);
                setInlineStylesTheme(resourceObjectInstance);
        setTitle("EventForm");
        setName("EventForm");
    }// </editor-fold>

//-- DON'T EDIT ABOVE THIS LINE!!!
//
//    private void add(String CENTER, Container content) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
}
