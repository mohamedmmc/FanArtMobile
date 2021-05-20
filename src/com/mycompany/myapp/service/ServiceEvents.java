/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.service;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entity.Evenement;
import com.mycompany.myapp.utils.MailSender;
import com.mycompany.myapp.utils.Statics;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author Ben Gouta Monam
 */
public class ServiceEvents extends Form {
     public ArrayList<Evenement> events;
    
    public static ServiceEvents instance=null;
    public boolean resultOK;
    private ConnectionRequest req;

    public ServiceEvents() {
         req = new ConnectionRequest();
    }

    public static ServiceEvents getInstance() {
        if (instance == null) {
            instance = new ServiceEvents();
        }
        return instance;
    }
    public ArrayList<Evenement> getAllTasks(){
        String url = Statics.BASE_URL+"/geteventsmobile/";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                events = parseTasks(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return events;
    }
    public ArrayList<Evenement> parseTasks(String jsonText){
        try {
            events=new ArrayList<>();
            JSONParser j = new JSONParser();// Instanciation d'un objet JSONParser permettant le parsing du résultat json

            /*
                On doit convertir notre réponse texte en CharArray à fin de
            permettre au JSONParser de la lire et la manipuler d'ou vient 
            l'utilité de new CharArrayReader(json.toCharArray())
            
            La méthode parse json retourne une MAP<String,Object> ou String est 
            la clé principale de notre résultat.
            Dans notre cas la clé principale n'est pas définie cela ne veux pas
            dire qu'elle est manquante mais plutôt gardée à la valeur par defaut
            qui est root.
            En fait c'est la clé de l'objet qui englobe la totalité des objets 
                    c'est la clé définissant le tableau de tâches.
            */
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
              /* Ici on récupère l'objet contenant notre liste dans une liste 
            d'objets json List<MAP<String,Object>> ou chaque Map est une tâche.               
            
            Le format Json impose que l'objet soit définit sous forme
            de clé valeur avec la valeur elle même peut être un objet Json.
            Pour cela on utilise la structure Map comme elle est la structure la
            plus adéquate en Java pour stocker des couples Key/Value.
            
            Pour le cas d'un tableau (Json Array) contenant plusieurs objets
            sa valeur est une liste d'objets Json, donc une liste de Map
            */
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            
            //Parcourir la liste des tâches Json
            for(Map<String,Object> obj : list){
                //Création des tâches et récupération de leurs données
                Evenement t = new Evenement();
               
                t.setId_evenement((int)Float.parseFloat(obj.get("idEvenement").toString()));
                t.setTitre(obj.get("titre").toString());
                t.setDescription(obj.get("description").toString());
                t.setDate_debut(obj.get("dateDebut").toString());
                t.setDate_Fin(obj.get("dateFin").toString());
                t.setLocall(obj.get("locall").toString());
                t.setNombre_place((int)Float.parseFloat(obj.get("nombrePlace").toString()));
                t.setPrix((int)Float.parseFloat(obj.get("prix").toString()));
                t.setImage(obj.get("image").toString());
               
                //Ajouter la tâche extraite de la réponse Json à la liste
                events.add(t);
            }
            
            
        } catch (IOException ex) {
            
        }
         /*
            A ce niveau on a pu récupérer une liste des tâches à partir
        de la base de données à travers un service web
        
        */
        return events;
    }
    public void addEvent(Evenement evenement) {

        String url = Statics.BASE_URL + "/addeventmobile?titre=" + evenement.getTitre()
                + "&description=" + evenement.getDescription() + "&salle=" +evenement.getLocall()
                + "&datedebut=" + evenement.getDate_debut() + "&datefin=" + evenement.getDate_Fin() + "&nbplace=" +
                evenement.getNombre_place()+"&prix="+evenement.getPrix()+"&image="+evenement.getImage()+"&iduser"+Statics.session;
        req.setUrl(url);

        req.addResponseListener((evt) -> {

            try {
               
                System.out.println("add validated !");
            } catch (Exception ex) {
                
                ex.printStackTrace();

            }

        });
        NetworkManager.getInstance().addToQueueAndWait(req);
    }
    
    public void sendphp(String che) throws IOException {
        HttpClient httpclient = new DefaultHttpClient();
        httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);

        HttpPost httppost = new HttpPost("http://localhost:80/img/upload2.php");

        File file = new File(che.substring(6));

        MultipartEntity mpEntity = new MultipartEntity();
        ContentBody cbFile = new FileBody(file, "image/jpeg");
        //System.out.println(cbFile.getFilename());
        mpEntity.addPart("userfile", cbFile);

        httppost.setEntity(mpEntity);
        //System.out.println("executing request " + httppost.getRequestLine());
        HttpResponse response = httpclient.execute(httppost);
        HttpEntity resEntity = response.getEntity();

        //System.out.println(response.getStatusLine());
        if (resEntity != null) {
            System.out.println(EntityUtils.toString(resEntity));
        }
        if (resEntity != null) {
            resEntity.consumeContent();
        }

        httpclient.getConnectionManager().shutdown();
        revalidate();
    }
}
