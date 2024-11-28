package configreader;

import configreader.exception.*;
import story.constants.Book;
import story.constants.Imagination;
import story.constants.Place;
import story.light.types.bulb.BulbPosition;
import story.person.shorty.Shorty;

import java.util.ArrayList;
import java.util.List;

public class ConfigReader {
    List<Shorty> cast = new ArrayList<>();
    boolean randomEmotions;
    Shorty slacker = null;
    Place goToThisPlace = null;
    Shorty statementAim = null;
    Book statementAimsBook = null;
    Imagination crazymansIdeology = null;
    Place katalajka = null;
    int countOfWindows;
    BulbPosition bulbPosition = null;
    int bulbPower;
    boolean doorClosed;
    int closePower;


    public ConfigReader() {
    }

    public void parse(String line) throws SyntaxException {
        List<String> split = List.of(line.split(" "));
        if (split.getFirst().equals("Cast")) {
            checkIsStructureEmpty(split, "Cast");
            parseCast(split.subList(1, split.size()));
        } else if (split.getFirst().equals("RandomEmotions")) {
            checkIsStructureEmpty(split, "RandomEmotions");
            parseRandomEmotions(split.subList(1, split.size()));
        } else if (split.getFirst().equals("Slacker")) {
            checkIsStructureEmpty(split, "Slacker");
            parseSlacker(split.subList(1, split.size()));
        } else if (split.getFirst().equals("NewPlace")) {
            checkIsStructureEmpty(split, "NewPlace");
            parseNewPlace(split.subList(1, split.size()));
        } else if (split.getFirst().equals("Aim")) {
            checkIsStructureEmpty(split, "Aim");
            parseAim(split.subList(1, split.size()));
        } else if (split.getFirst().equals("Book")) {
            checkIsStructureEmpty(split, "Book");
            parseBook(split.subList(1, split.size()));
        } else if (split.getFirst().equals("Crazyman")) {
            checkIsStructureEmpty(split, "Crazyman");
            parseCrazyman(split.subList(1, split.size()));
        } else if (split.getFirst().equals("PlaceKatalajka")) {
            checkIsStructureEmpty(split, "PlaceKatalajka");
            parsePlaceKatalajka(split.subList(1, split.size()));
        } else if (split.getFirst().equals("CountOfWindows")) {
            checkIsStructureEmpty(split, "CountOfWindows");
            parseCountOfWindows(split.subList(1, split.size()));
        } else if (split.getFirst().equals("BulbPosition")) {
            checkIsStructureEmpty(split, "BulbPosition");
            parseBulbPosition(split.subList(1, split.size()));
        } else if (split.getFirst().equals("BulbPower")) {
            checkIsStructureEmpty(split, "BulbPower");
            parseBulbPower(split.subList(1, split.size()));
        } else if (split.getFirst().equals("DoorClosed")) {
            checkIsStructureEmpty(split, "DoorClosed");
            parseDoorClosed(split.subList(1, split.size()));
        } else if (split.getFirst().equals("ClosePower")) {
            checkIsStructureEmpty(split, "ClosePower");
            parseClosePower(split.subList(1, split.size()));
        }
        else {
            if (!(split.getFirst().isEmpty())) {
                throw new SyntaxException(line);
            }
        }
    }

    private void parseCast(List<String> line) {
        cast.clear();
        List<String> names = parseNames(line, "Cast");
        checkDublicates(names, "Cast");
        for (var i : names) {
            this.cast.add(new Shorty(i));
        }
    }

    private List<String> parseNames(List<String> line, String structure) throws WrongFormatException {
        List<String> names = new ArrayList<>();
        names.add(line.getFirst());
        for (int i = 1; i < line.size(); i++) {
            if (!(line.get(i).startsWith("\""))) {
                names.set(names.size() - 1, names.get(names.size() - 1) + " " + line.get(i));
            } else {
                names.add(line.get(i));
            }
        }
        for (int i = 0; i < names.size(); i++) {
            if (!(names.get(i).startsWith("\"") && names.get(i).endsWith("\""))) {
                throw new WrongFormatException(structure, names.get(i));
            }
            names.set(i, names.get(i).substring(1, names.get(i).length() - 1));
        }
        return names;
    }

    private void parseRandomEmotions(List<String> line) {
        checkIsSingleValue(line, "RandomEmotions");
        if (line.getFirst().equals("true")) {
            this.randomEmotions = true;
        } else if (line.getFirst().equals("false")) {
            this.randomEmotions = false;
        } else {
            throw new WrongFormatException("RandomEmotions", line.getFirst());
        }
    }

    private void parseSlacker(List<String> line) {
        List<String> names = parseNames(line, "Slacker");
        checkIsSingleValue(names, "Slacker");
        String name = names.getFirst();
        checkIsShortyInCast(name, "Slacker");
        this.slacker = new Shorty(name);
    }

    private void parseNewPlace(List<String> line) throws WrongFormatException {
        checkIsSingleValue(line, "NewPlace");
        String place = line.getFirst();
        Place found = null;
        for (var p : Place.values()) {
            if (p.name().equals(place)) {
                found = p;
            }
        }
        if (found == null) {
            throw new WrongFormatException("NewPlace", place);
        } else {
            this.goToThisPlace = found;
        }
    }

    private void parseAim(List<String> line) {
        List<String> names = parseNames(line, "Aim");
        checkIsSingleValue(names, "Aim");
        String name = names.getFirst();
        checkIsShortyInCast(name, "Aim");
        this.statementAim = new Shorty(name);
    }

    private void parseBook(List<String> line) {
        checkIsSingleValue(line, "Book");
        String book = line.getFirst();
        Book found = null;
        for (var b : Book.values()) {
            if (b.name().equals(book)) {
                found = b;
            }
        }
        if (found == null) {
            throw new WrongFormatException("Book", book);
        } else {
            this.statementAimsBook = found;
        }
    }

    private void parseCrazyman(List<String> line) {
        checkIsSingleValue(line, "Crazyman");
        String imagination = line.getFirst();
        Imagination found = null;
        for (var i : Imagination.values()) {
            if (i.name().equals(imagination)) {
                found = i;
            }
        }
        if (found == null) {
            throw new WrongFormatException("Crazyman", imagination);
        } else {
            this.crazymansIdeology = found;
        }
    }

    private void parsePlaceKatalajka(List<String> line) throws WrongFormatException {
        checkIsSingleValue(line, "PlaceKatalajka");
        String place = line.getFirst();
        Place found = null;
        for (var p : Place.values()) {
            if (p.name().equals(place)) {
                found = p;
            }
        }
        if (found == null) {
            throw new WrongFormatException("PlaceKatalajka", place);
        } else {
            this.katalajka = found;
        }
    }

    private void parseCountOfWindows(List<String> line) throws WrongFormatException {
        checkIsSingleValue(line, "CountOfWindows");
        countOfWindows = parseInt(line, "CountOfWindows");
    }

    private int parseInt(List<String> line, String structure) {
        int integer;
        try {
            integer = Integer.parseInt(line.getFirst());
        }
        catch (NumberFormatException e) {
            throw new WrongFormatException(structure, line.getFirst());
        }
        return integer;
    }

    private void parseBulbPosition(List<String> line) throws WrongFormatException {
        checkIsSingleValue(line, "BulbPosition");
        String place = line.getFirst();
        BulbPosition found = null;
        for (var p : BulbPosition.values()) {
            if (p.name().equals(place)) {
                found = p;
            }
        }
        if (found == null) {
            throw new WrongFormatException("BulbPosition", place);
        } else {
            this.bulbPosition = found;
        }
    }

    private void parseBulbPower(List<String> line) throws WrongFormatException {
        checkIsSingleValue(line, "BulbPower");
        bulbPower = parseInt(line, "BulbPower");
    }

    private void parseDoorClosed(List<String> line) {
        checkIsSingleValue(line, "DoorClosed");
        if (line.getFirst().equals("true")) {
            this.doorClosed = true;
        } else if (line.getFirst().equals("false")) {
            this.doorClosed = false;
        } else {
            throw new WrongFormatException("DoorClosed", line.getFirst());
        }
    }

    private void parseClosePower(List<String> line) throws WrongFormatException {
        checkIsSingleValue(line, "ClosePower");
        closePower = parseInt(line, "ClosePower");
    }

    private void checkIsShortyInCast(String name, String structure) throws ShortyNotInCastException {
        boolean found = false;
        for (Shorty s : cast) {
            if (s.getName().equals(name)) {
                found = true;
            }
        }
        if (!found) {
            throw new ShortyNotInCastException(structure);
        }
    }

    private void checkDublicates(List<String> line, String structure) throws DublicatedNamesException {
        List<String> copy = new ArrayList<String>(line);
        copy.sort(new ListOfStringsComparator());
        for (int i = 1; i < copy.size(); i++) {
            if (copy.get(i).equals(copy.get(i - 1))) {
                throw new DublicatedNamesException(structure);
            }
        }
    }

    private void checkIsSingleValue(List<String> line, String structure) throws WrongFormatException {
        if (line.size() != 1) {
            throw new WrongFormatException(structure, OpenListOfStrings.open(line));
        }
    }

    private void checkIsStructureEmpty(List<String> line, String structure) throws StructureIsEmptyException {
        if (line.size() == 1) {
            throw new StructureIsEmptyException(structure);
        }
    }

    public Place getKatalajka() {
        return katalajka;
    }

    public int getCountOfWindows() {
        return countOfWindows;
    }

    public BulbPosition getBulbPosition() {
        return bulbPosition;
    }

    public int getBulbPower() {
        return bulbPower;
    }

    public boolean getDoorClosed() {
        return doorClosed;
    }

    public int getClosePower() {
        return closePower;
    }

    public List<Shorty> getCast() {
        return cast;
    }

    public boolean getRandomEmotions() {
        return randomEmotions;
    }

    public Shorty getSlacker() {
        return slacker;
    }

    public Place getGoToThisPlace() {
        return goToThisPlace;
    }

    public Shorty getStatementAim() {
        return statementAim;
    }

    public Book getStatementAimsBook() {
        return statementAimsBook;
    }

    public Imagination getCrazymansIdeology() {
        return crazymansIdeology;
    }
}
