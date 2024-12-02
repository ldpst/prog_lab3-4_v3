import configreader.ConfigReader;
import story.constants.Book;
import story.constants.Imagination;
import story.constants.Place;
import story.light.types.bulb.Bulb;
import story.light.types.bulb.BulbPosition;
import story.person.shorty.Shorty;
import story.scene.Scene;
import story.play.Play;

import javax.swing.text.Position;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        BufferedReader br = null;
        Scene scene = null;
        try {
            br = new BufferedReader(new FileReader("data/Main.cfg"));
            String line;
            ConfigReader reader = new ConfigReader();
            while ((line = br.readLine()) != null) {
                reader.parse(line);
            }

            List<Shorty> cast = reader.getCast();
            boolean randomEmotions = reader.getRandomEmotions();
            Shorty slacker = reader.getSlacker();
            Place goToThisPlace = reader.getGoToThisPlace();
            Shorty statementAim = reader.getStatementAim();
            Book statementAimsBook = reader.getStatementAimsBook();
            Imagination crazymansIdeology = reader.getCrazymansIdeology();
            Place katalajka = reader.getKatalajka();
            int countOfWindows = reader.getCountOfWindows();
            BulbPosition bulbPosition = reader.getBulbPosition();
            int bulbPower = reader.getBulbPower();
            boolean doorClosed = reader.getDoorClosed();
            int closePower = reader.getClosePower();

            scene = new Scene(cast, randomEmotions, slacker, goToThisPlace, statementAim, statementAimsBook, crazymansIdeology, katalajka,countOfWindows, bulbPosition, bulbPower, doorClosed, closePower);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

        Play play = new Play(scene);
        play.go();
    }
}