/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose ToolKit | Templates
 * and open the template in the editor.
 */
package Course.Overflow.Global.Customize;

import Course.Overflow.Global.ToolKit;
import com.qoppa.pdf.PDFException;
import com.qoppa.pdfViewerFX.PDFViewer;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javax.imageio.ImageIO;

/**
 *
 * @author Md Mehedi Hasan
 */
public class PDF {

      File file;
      PDFViewer viewer;

      public PDF(File pdfFile) {
            file = pdfFile;
            viewer = new PDFViewer();
      }

      public void openPDFfile(File file) throws Exception {
            ToolKit.openPDF(file);
      }

      public Image getThumbnail() throws PDFException {
            viewer.loadPDF(file.getAbsolutePath());
            SnapshotParameters param = new SnapshotParameters();
            param.setDepthBuffer(true);
            WritableImage snapshot = viewer.snapshot(new SnapshotParameters(), new WritableImage(200, 200));
            BufferedImage tempImg = SwingFXUtils.fromFXImage(snapshot, null);
            BufferedImage img = null;

            byte[] imageInByte;
            try {
                  ByteArrayOutputStream baos = new ByteArrayOutputStream();
                  ImageIO.write(tempImg, "png", baos);
                  baos.flush();
                  imageInByte = baos.toByteArray();
                  baos.close();
                  InputStream in = new ByteArrayInputStream(imageInByte);
                  img = ImageIO.read(in);
            } catch (IOException e) {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
            }
            Image image = SwingFXUtils.toFXImage(img, null );
            return image;
//            return new Image(GLOBAL.PICTURE_LOCATION + "/plus_48px.png");
      }
}
