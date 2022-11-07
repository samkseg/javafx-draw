package se.iths.laboration3;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import se.iths.laboration3.model.Model;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class saveImage {
    static FileChooser fileChooser = new FileChooser();
    public static void saveSVG(Model model) {
        prepareSVGFileSave();

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
    public static void savePNG(Canvas canvas) {
        try {
            Image snapShot = canvas.snapshot(null,null);
            ImageIO.write(SwingFXUtils.fromFXImage(snapShot, null), "png", new File("image.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static String beginSVG() {
        return "<svg xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\" width=\"3840\" height=\"1080\">";
    }
    private static void prepareSVGFileSave() {
        fileChooser.setTitle("Save as .svg");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("SVG file",".svg"));
    }


}
