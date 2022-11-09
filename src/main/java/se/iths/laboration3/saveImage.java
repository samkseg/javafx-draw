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

        try {
            prepareFileSave("Save as .svg", "SVG file",".svg");
            List<String> svgStringList = new ArrayList<>();
            Path path = Path.of(fileChooser.showSaveDialog(new Stage()).getPath());
            svgStringList.add(beginSVG());
            model.getShapes().forEach(shape -> svgStringList.add(shape.toStringSVG()));
            svgStringList.add("</svg>");
            Files.write(path, svgStringList);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException ignored) {}
    }
    public static void savePNG(Canvas canvas) {
        try {
            prepareFileSave("Save as .png","PNG file",".png");
            Path path = Path.of(fileChooser.showSaveDialog(new Stage()).getPath());
            Image snapShot = canvas.snapshot(null,null);
            ImageIO.write(SwingFXUtils.fromFXImage(snapShot, null), "png", new File(path + ""));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NullPointerException ignored) {}
    }
    private static String beginSVG() {
        return "<svg xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\" width=\"3840\" height=\"2160\">";
    }

    private static void prepareFileSave(String title, String description, String extension) {
        fileChooser.setTitle(title);
        fileChooser.getExtensionFilters().clear();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter(description,extension));
    }


}
