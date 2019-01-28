import com.google.gson.JsonObject;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

class Transaction {

    public  String fromId;
    public  String toId;
    public  String actionId;
    public  String status;
    public  Float amount;
    public  String time;

    public Transaction(JsonObject o){
        status = o.get("status").getAsString();
        String id = o.get("id").getAsString();
        fromId = o.get("from_user_id").getAsString();
        toId = o.get("to_user_id").getAsString();
        String hash = o.get("transaction_hash").getAsString();
        actionId = o.get("action_id").getAsString();
        Float fee = o.get("transaction_fee").getAsFloat();
        Float commission = o.get("commission_amount").getAsFloat();
        amount = o.get("amount").getAsFloat();
        Float airDropAmount = o.get("airdropped_amount").getAsFloat();
        long temp = o.get("timestamp").getAsLong();
        Date date = new Timestamp(temp);
        SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        isoFormat.setTimeZone(TimeZone.getDefault());
        time = String.valueOf(isoFormat.format(date));
    }
}
