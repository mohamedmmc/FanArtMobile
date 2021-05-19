/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.service;

import com.codename1.capture.Capture;
import com.codename1.components.InfiniteProgress;
import com.codename1.components.ToastBar;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.MultipartRequest;
import com.codename1.io.NetworkManager;
import com.codename1.ui.CheckBox;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.util.Resources;
import com.codename1.util.Callback;
import com.mycompany.gui.ConfirmationOtp;
import com.mycompany.gui.NewsfeedForm;
import com.mycompany.gui.ProfileForm;
import com.mycompany.gui.SignInForm;
import com.mycompany.myapp.entity.User;
import com.mycompany.myapp.utils.MailSender;
import com.mycompany.myapp.utils.Sms;
import com.mycompany.myapp.utils.Statics;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Vector;
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
import org.json.JSONObject;

/**
 *
 * @author bhk
 */
public class ServiceUser extends Form {

    public String json;
    public String numteljson;
    public static ServiceUser instance = null;
    public static boolean resultOK = true;
    private ConnectionRequest req;

    public static ServiceUser getInstance() {
        if (instance == null) {
            instance = new ServiceUser();
        }
        return instance;
    }

    public ServiceUser() {
        req = new ConnectionRequest();
    }

    public Integer existance(String mail, String num) {
        return 0;
    }

    public void SignUp(TextField nom, TextField prenom, TextField email, TextField mdp, TextField rmdp, String photo, TextField numtel, ComboBox roles, Resources res) {

        String url = Statics.BASE_URL + "/signup?nom=" + nom.getText().toString() + "&prenom=" + prenom.getText().toString() + "&email=" + email.getText().toString() + "&mdp=" + mdp.getText().toString() + "&photo=" + photo + "&numtel=" + numtel.getText().toString()+"&type=client";
        req.setUrl(url);

        req.addResponseListener((evt) -> {

            try {
                MailSender sm = new MailSender();
                sm.send(email.getText(), prenom.getText());
                //sendphp(photo);
            } catch (Exception ex) {
                ex.printStackTrace();

            }

        });
        NetworkManager.getInstance().addToQueueAndWait(req);
    }

    public String modifierProfil(String id, String nom, String prenom, String email, String numtel, String photo, ComboBox type, Resources res) {
        String url = Statics.BASE_URL + "/user/updateuserjson?id=" + id + "&nom=" + nom + "&prenom=" + prenom + "&email=" + email + "&photo=" + photo + "&numtel=" + numtel;
        req.setUrl(url);
        req.addResponseListener((evt) -> {
            json = new String(req.getResponseData()) + "";
            JSONParser j = new JSONParser();

        });
        NetworkManager.getInstance().addToQueueAndWait(req);

        return json;
    }

    public String verifyEMAIL(String numtel, Resources res) {
        String url = Statics.BASE_URL + "/user/getemail?email=" + numtel;
        req.setUrl(url);

        req.addResponseListener((evt) -> {
            json = new String(req.getResponseData()) + "";
            JSONParser j = new JSONParser();

        });

        NetworkManager.getInstance().addToQueueAndWait(req);

        return json;
    }

    public void delUser(String id, Resources res) {
        String url = Statics.BASE_URL + "/user/deluserjson?id=" + id;
        req.setUrl(url);
        req.addResponseListener((evt) -> {
            json = new String(req.getResponseData()) + "";
            System.out.println(json);

        });

        NetworkManager.getInstance().addToQueueAndWait(req);

    }

    public String getNumId(String id) {
        String url = Statics.BASE_URL + "/user/getnumberid?id=" + id;
        req.setUrl(url);
        req.addResponseListener((evt) -> {

            JSONParser j = new JSONParser();
            json = new String(req.getResponseData()) + "";

        });

        NetworkManager.getInstance().addToQueueAndWait(req);

        return (json);
    }

    public String verifynum(String numtel, Resources res) {
        String url = Statics.BASE_URL + "/user/getnumber?numtel=" + numtel;
        req.setUrl(url);
        req.addResponseListener((evt) -> {

            JSONParser j = new JSONParser();
            json = new String(req.getResponseData()) + "";

        });

        NetworkManager.getInstance().addToQueueAndWait(req);

        return json;
    }

    public String verifynumOTP(String numtel, Resources res) {
        String url = Statics.BASE_URL + "/user/getnumber?numtel=" + numtel;
        req.setUrl(url);
        req.addResponseListener((evt) -> {

            JSONParser j = new JSONParser();
            json = new String(req.getResponseData()) + "";

        });

        NetworkManager.getInstance().addToQueueAndWait(req);
        return json;
    }

    public User Profil(String id) {
        System.out.println("I'm here");
        User u = new User();
        if (!Statics.session.equals("")) {
            String url = Statics.BASE_URL + "/user/getuserjson?id=" + Statics.session;
            req.setUrl(url);

//            req.addResponseListener((evt) -> {
//                String str = new String(req.getResponseData());
//                
//                JSONObject objj = new JSONObject(str);
//                u.setId(objj.getInt("id"));
//                u.setNum(objj.getInt("numtel"));
//                u.setNom(objj.getString("nom"));
//                u.setPrenom(objj.getString("prenom"));
//                u.setEmail(objj.getString("email"));
//                u.setType(objj.getString("type"));
//                u.setPhoto(objj.getString("photo"));
//
//            });
            req.addResponseListener((evt) -> {
                String str = new String(req.getResponseData());
                JSONParser jsonp = new JSONParser();
                System.out.println(str);
                
                try {
                    Map<String, Object> obj = jsonp.parseJSON(new CharArrayReader(str.toCharArray()));
                    System.out.println(obj.get("photo").toString());
                    u.setNom(obj.get("nom").toString());
                    u.setPrenom(obj.get("prenom").toString());
                    u.setEmail(obj.get("email").toString());

                    u.setType(obj.get("type").toString());
                    u.setPhoto(obj.get("photo").toString());
                    u.setMdp(obj.get("mdp").toString());
                    u.setNum(Integer.parseInt(getNumId(id)));

                } catch (Exception e) {
                    
                }

            });

            NetworkManager.getInstance().addToQueueAndWait(req);
        }
        return u;
    }

    public void ResetPass(TextField mdp, String num, Resources res) {
        String url = Statics.BASE_URL + "/user/resetPassword?num=" + num + "&mdp=" + mdp.getText().toString();
        req.setUrl(url);
        req.addResponseListener((evt) -> {

            JSONParser j = new JSONParser();
            json = new String(req.getResponseData()) + "";

            if (json.equals("failed")) {
                Dialog.show("Echec de la modification", "Erreur", "OK", null);
            } else {
                Dialog.show("Succes", "Modification avec success !", "OK", null);
                if (Statics.session.equals("")) {
                    new SignInForm(res).show();
                } else {
                    new ProfileForm(Statics.session, res).show();
                }

            }

        });

        NetworkManager.getInstance().addToQueueAndWait(req);
    }

    public String test(String id, Resources res) {
        String url = Statics.BASE_URL + "/user/getphoto?id=" + id;
        req = new ConnectionRequest(url, false);
        req.setUrl(url);
        req.addResponseListener((evt) -> {
            JSONParser j = new JSONParser();
            json = new String(req.getResponseData()) + "";
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return json.substring(1, json.length() - 1);
    }

    public String getiduser(String email, Resources res) {
        String url = Statics.BASE_URL + "/user/getiduser?email=" + email;
        req = new ConnectionRequest(url, false);
        req.setUrl(url);
        req.addResponseListener((evt) -> {
            JSONParser j = new JSONParser();
            json = new String(req.getResponseData()) + "";
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        Statics.session = json;
        return (json);
    }

    public void SignIn(TextField email, TextField mdp, Resources res) {
        String url = Statics.BASE_URL + "/user/signin?email=" + email.getText().toString() + "&mdp=" + mdp.getText().toString();
        req = new ConnectionRequest(url, false);
        req.setUrl(url);

        req.addResponseListener((evt) -> {

            JSONParser j = new JSONParser();
            json = new String(req.getResponseData());
            try {
                if (json.equals("failed")) {
                    Dialog.show("Echec d'autentification", "Email ou mot de passe incorrect", "OK", null);
                } else {
                    Statics.session = json;
                    new NewsfeedForm(json, res).show();

                }
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
