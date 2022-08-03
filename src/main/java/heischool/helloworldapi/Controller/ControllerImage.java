package heischool.helloworldapi.Controller;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.postgresql.shaded.com.ongres.scram.common.bouncycastle.base64.Base64;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;

@RestController
public class ControllerImage {
    @GetMapping("/")
    public String home() {
        return "Hello";
    }

    @PostMapping("/")
    public void convertToBlackAndWhite(@RequestParam("image") MultipartFile multipartFile) throws IOException {
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        BufferedImage img = null;
        try {
            //Read in new image file
            img = ImageIO.read(new File(fileName));
        }
        catch (IOException e){
            System.out.println("Error: "+e);
        }
        int h=img.getHeight();
        int w=img.getWidth();

        BufferedImage bufferedImage = new BufferedImage(w,h, BufferedImage.TYPE_INT_RGB);
        if (img == null) {
            System.out.println("No image loaded");
        }
        else {
            for(int i=0;i<w;i++)
            {
                for(int j=0;j<h;j++)
                {
                    //Get RGB Value
                    int val = img.getRGB(i, j);
                    //Convert to three separate channels
                    int r = (0x00ff0000 & val) >> 16;
                    int g = (0x0000ff00 & val) >> 8;
                    int b = (0x000000ff & val);
                    int m=(r+g+b);
                    //(255+255+255)/2 =283 middle of dark and light
                    if(m>=383)
                    {
                        // for light color it set white
                        bufferedImage.setRGB(i, j, Color.WHITE.getRGB());
                    }
                    else{
                        // for dark color it will set black
                        bufferedImage.setRGB(i, j, 0);
                    }
                }
            }
        }


        File file = new File("D:/info/HEI/sary info utile/MyImage.jpg");
        ImageIO.write(bufferedImage, "jpg", file);

    }
}