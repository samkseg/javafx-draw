package se.iths.laboration3;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import se.iths.laboration3.model.Model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class SVG {
    static FileChooser fileChooser = new FileChooser();
    public static void save(Model model) {
        prepareFileSave();

        List<String> svgStringList = new ArrayList<>();
        Path path = Path.of(fileChooser.showSaveDialog(new Stage()).getPath());
        svgStringList.add(beginSVG());
        model.getShapes().forEach(shape -> svgStringList.add(shape.toStringSVG()));
        svgStringList.add("</svg>");
        try {
            Files.write(path, svgStringList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static String beginSVG() {
        return "<svg xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\" width=\"3840\" height=\"1080\">";
    }
    private static void prepareFileSave() {
        fileChooser.setTitle("Save as .svg");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("SVG file",".svg"));
    }
}
