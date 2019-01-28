import javafx.scene.control.TableView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseEvent;

import java.util.concurrent.atomic.AtomicLong;

public class helper {

    public static void copy(MouseEvent event){
        String e = event.toString();
        int start = e.indexOf("text") + 6;
        int end =e.indexOf("\"",start);
        String text = e.substring(start,end);
        ClipboardContent content = new ClipboardContent();
        content.putString(text);
        Clipboard.getSystemClipboard().setContent(content);
    }

    public static void customResize(TableView<?> view) {
        AtomicLong width = new AtomicLong();
        view.getColumns().forEach(col -> width.addAndGet((long) col.getWidth()));
        double tableWidth = view.getWidth();

        if (tableWidth > width.get()) {
            view.getColumns().forEach(col ->
                    col.setPrefWidth(col.getWidth()+((tableWidth-width.get())/view.getColumns().size())));
        }
    }

}
