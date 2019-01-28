import javafx.beans.property.SimpleStringProperty;

import java.util.Objects;

public class transactionFx {

    public SimpleStringProperty Time = new SimpleStringProperty("");
    public SimpleStringProperty Action = new SimpleStringProperty("");
    public SimpleStringProperty From = new SimpleStringProperty("");
    public SimpleStringProperty To = new SimpleStringProperty("");
    public  SimpleStringProperty Amount = new SimpleStringProperty("");

    public  SimpleStringProperty Status = new SimpleStringProperty("");

    public transactionFx(Transaction t){
        String[] info = UserInteract.getAction(Integer.valueOf(t.actionId));
        String fromID = ""; String toID = "";
        //"user_to_user", "company_to_user", or "user_to_company"
        if(Objects.requireNonNull(info)[1].equals("user_to_user")){
            fromID = Server.getUserName(t.fromId);
            toID = Server.getUserName(t.toId);
        }else if(info[1].equals("company_to_user")){
            fromID = " - ";
            toID = Server.getUserName(t.toId);
        }else if(info[1].equals("user_to_company")){
            fromID = Server.getUserName(t.fromId);
            toID = " - ";
        }
        setTime(t.time);
        setAction(info[0]);
        setFrom(fromID);
        setTo(toID);
        setAmount(t.amount.toString());
        setStatus(t.status);
    }

    public String getStatus() {
        return Status.get();
    }

    private void setStatus(String status) {
        this.Status.set(status);
    }

    public String getTime() {
        return Time.get();
    }

    private void setTime(String time) {
        this.Time.set(time);
    }

    public String getAction() {
        return Action.get();
    }

    private void setAction(String action) {
        this.Action.set(action);
    }

    public String getFrom() {
        return From.get();
    }

    private void setFrom(String from) {
        this.From.set(from);
    }

    public String getTo() {
        return To.get();
    }

    private void setTo(String to) {
        this.To.set(to);
    }

    public String getAmount() {
        return Amount.get();
    }

    private void setAmount(String amount) {
        this.Amount.set(amount);
    }


}
