import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.ost.OSTSDK;
import com.ost.services.OSTAPIService;
import com.ost.services.v1_1.Manifest;
import javafx.scene.control.Alert;
import javafx.stage.Window;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class UserInteract{

    private static com.ost.services.v1_1.Users userService;
    private static com.ost.services.v1_1.Balances balanceService;
    private static com.ost.services.v1_1.Ledger ledgerService;
    private static com.ost.services.v1_1.Transactions transactionService;
    private static com.ost.services.v1_1.Actions actionService;
    private static com.ost.services.v1_1.Transfers transferService;
    private static final String company_id = "47e62424-abd6-4c0e-a46a-8e431ec088eb";
    private static String userId = null;
    public static String userName = null;
    public static String userAddress = null;
    public static Float userBalance = null;
    public static ArrayList<transactionFx> table = null;
    public static ArrayList<transactionFx> projectTable = null;
    public static Map<String, String> addressToId;

    public static void init(){
        HashMap <String,Object> sdkConfig = new HashMap<>();
        table = new ArrayList<>();
        projectTable = new ArrayList<>();
        sdkConfig.put("apiEndpoint", "https://sandboxapi.ost.com/v1.1");
        sdkConfig.put("apiKey","8761f5fefb74a3a941f5");
        sdkConfig.put("apiSecret","958ccbe22e81bf2e8c0ff08aee8776990c48cce3f75ba54ed81857547da5264b");
        OSTSDK ostObj = new OSTSDK(sdkConfig);
        com.ost.services.v1_1.Manifest services = (com.ost.services.v1_1.Manifest) ostObj.services;
        balanceService = services.balances;
        com.ost.services.v1_1.AirDrops airdropService = services.airdrops;
        userService = services.users;
        ledgerService = services.ledger;
        transactionService = services.transactions;
        actionService = services.actions;
        transferService = services.transfers;
        addressToId = new HashMap<>();
    }

     private static JsonObject createUser(String _username){
        // returns object containing Alice's ID, among other information, which you will need later
        HashMap<String,Object> params = new HashMap<>();
        params.put("name", _username);
         JsonObject response = null;
         try {
             response = userService.create(params);
         } catch (IOException e) {
             e.printStackTrace();
         }
         System.out.println("response: " + Objects.requireNonNull(response).toString());
         return response;
    }

    private static JsonObject userCheck(String id){
        HashMap <String,Object> params = new HashMap<>();
        params.put("id", id);
        JsonObject response = null;
        try {
            response = userService.get(params);
        } catch (OSTAPIService.MissingParameter | IOException missingParameter) {
            missingParameter.printStackTrace();
        }
        System.out.println("response: " + Objects.requireNonNull(response).toString() );
        if(response.get("success").getAsBoolean())
            return response;
        else
            return null;
    }

//    public static void getIdLsit(){
//        HashMap <String,Object> params = new HashMap<>();
//        JsonObject response;
//        int pageNo = 1;
//        try {
//            for(int i = 1; i <= pageNo; ++i){
//                params.put("page_no", pageNo);
//                response = userService.list(params);
//                System.out.println("response: " + response.toString());
//                if(response.get("success").getAsBoolean()){
//                    JsonArray array = response.get("data").getAsJsonObject().getAsJsonArray("users");
//                    for(JsonElement o : array){
//                        String address = o.getAsJsonObject().getAsJsonArray("addresses").get(0).getAsJsonArray().get(1).getAsString();
//                        String id = o.getAsJsonObject().get("id").getAsString();
//                        addressToId.put(address, id);
//                    }
//                    try{
//                        pageNo = response.get("data").getAsJsonObject().get("meta").getAsJsonObject()
//                                .get("next_page_payload").getAsJsonObject().get("page_no").getAsInt();
//                    }
//                    catch (Exception e){}
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static boolean test(){
//        HashMap <String,Object> params = new HashMap<String,Object>();
////        params.put("to_address", "0x2930755577edA50986A0f8c021b4A085bd7A5F42");
////        params.put("amount", "1000000000000000000");
//        JsonObject response = null;
//        try {
//            response = transferService.list( params );
////            response = transferService.execute( params );
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        System.out.println("response: " + response.toString() );
//        return true;
//    }

    public static boolean donate(String id, Float amount){
        HashMap <String,Object> params = new HashMap<>();
        params.put("from_user_id", userId);
        params.put("to_user_id", id);
        params.put("action_id", "41501");
        params.put("amount", amount);
        JsonObject response = null;
        try {
            response = transactionService.execute( params );
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("response: " + Objects.requireNonNull(response).toString());
        return response.get("success").getAsBoolean();
    }

    public static void readTransaction(String id){
        ArrayList<transactionFx> t;
        if(id.equals("")){
            id = userId;
            t = table;
        }
        else t = projectTable;
        HashMap <String,Object> params = new HashMap<>();
        params.put("id", id);
        JsonObject response;
        int pageNo = 1;
        t.clear();
        try {
            for(int i = 1; i <= pageNo; ++i) {
                params.put("page_no", pageNo);
                response = ledgerService.get(params);
                System.out.println(response);
                JsonArray transaction = Objects.requireNonNull(response).get("data").getAsJsonObject().get("transactions").getAsJsonArray();
                for(JsonElement o :  transaction){
                    if(o.getAsJsonObject().get("status").getAsString().equals("complete")){
                        Transaction temp = new Transaction(o.getAsJsonObject());
                        t.add(new transactionFx(temp));
                    }
                }
                try{
                    pageNo = response.get("data").getAsJsonObject().get("meta").getAsJsonObject()
                            .get("next_page_payload").getAsJsonObject().get("page_no").getAsInt();
                }
                catch (Exception e){}
            }
        } catch (OSTAPIService.MissingParameter | IOException missingParameter) {
            missingParameter.printStackTrace();
        }
    }

    public static boolean login(String pass, Window owner){
        String decryptId;
        try {
            FileInputStream in = new FileInputStream("data");
            ObjectInputStream input = new ObjectInputStream(in);
            byte[] encode = (byte[]) input.readObject();
            Encryption.setSalt((byte[]) input.readObject());
            decryptId = new String(Objects.requireNonNull(Encryption.crypto(Cipher.DECRYPT_MODE, pass, encode)), StandardCharsets.UTF_8);
            System.out.println(decryptId);
            input.close();
            in.close();
            JsonObject info = userCheck(decryptId);
            if(info == null)
                AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Login Error",
                        "User not find");
            else {
                userId = decryptId;
                info = info.getAsJsonObject("data").getAsJsonObject("user");
                userName = info.get("name").getAsString();
                userAddress = info.get("addresses").getAsJsonArray().get(0).getAsJsonArray().get(1).getAsString();
                UserInteract.getBalance();
                return true;
            }
        } catch (FileNotFoundException e) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Login Error",
                    "No login data find");
        }catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Login Error",
                    "The password is wrong");
        }
        return false;
    }

    public static boolean reg(String username, String pass, String email, boolean charity){
        JsonObject res = createUser(username);
        if(!res.get("success").getAsBoolean()) {
            return false;
        }
        JsonObject userDetail = res.getAsJsonObject("data").getAsJsonObject("user");
        String address = userDetail.get("addresses").getAsJsonArray().get(0).getAsJsonArray().get(1).getAsString();
        String id = userDetail.get("id").getAsString();
        if(charity)
            Server.regProj(id, address);
        else
            Server.addUser(id, address, username, email);
        try {
            FileOutputStream o = new FileOutputStream("data");
            ObjectOutputStream out = new ObjectOutputStream(o);
            out.writeObject(Encryption.crypto(Cipher.ENCRYPT_MODE,pass,id.getBytes(StandardCharsets.UTF_8)));
            out.writeObject(Encryption.getSalt());
            out.close(); o.close();
        } catch (BadPaddingException | IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static void getBalance(){
       userBalance =  _getBalance(userId);
    }

    public static Float _getBalance(String id){
        HashMap <String,Object> params = new HashMap<>();
        params.put("id", id);
        JsonObject response = null;
        try {
            response = balanceService.get(params);
        } catch (OSTAPIService.MissingParameter | IOException missingParameter) {
            missingParameter.printStackTrace();
        }
        System.out.println("response: " + Objects.requireNonNull(response).toString() );
        if(response.get("success").getAsBoolean())
            return response.get("data").getAsJsonObject().get("balance")
                    .getAsJsonObject().get("available_balance").getAsFloat();
        else
            return 0.0f;
    }

    //airdrop
//    public static boolean buy(Integer amount){
//        HashMap <String,Object> params = new HashMap<>();
//        params.put("amount", amount);
//        params.put("user_ids", userId);
//        JsonObject response = null;
//        try {
//            response = airdropService.execute(params);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        System.out.println("response: " + response.toString());
//        return response.get("success").getAsBoolean();
//    }
    public static boolean buy(Float amount){
        HashMap <String,Object> params = new HashMap<>();
        params.put("from_user_id", company_id);
        params.put("to_user_id", userId);
        params.put("action_id", "41500  ");
        params.put("amount", amount);
        JsonObject response = null;
        try {
            response = transactionService.execute( params );
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("response: " + Objects.requireNonNull(response).toString());
        return response.get("success").getAsBoolean();
    }

    public static String[] getAction(Integer id){
        HashMap <String,Object> params = new HashMap<>();
        params.put("id", id);
        JsonObject response = null;
        try {
            response = actionService.get( params );
        } catch (OSTAPIService.MissingParameter | IOException missingParameter) {
            missingParameter.printStackTrace();
        }
        if(Objects.requireNonNull(response).get("success").getAsBoolean()){
            JsonObject action = response.get("data").getAsJsonObject().get("action").getAsJsonObject();
            String name = action.get("name").getAsString();
            String type = action.get("kind").getAsString();
            return new String[]{name, type};
        }
        System.out.println("response: " + response.toString() );
        return null;
    }



}
