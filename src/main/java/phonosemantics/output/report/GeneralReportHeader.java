package phonosemantics.output.report;

import lombok.Data;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import phonosemantics.phonetics.phoneme.distinctiveFeatures.MannerPrecise;
import phonosemantics.phonetics.phoneme.distinctiveFeatures.consonants.Lateral;
import phonosemantics.phonetics.phoneme.distinctiveFeatures.vowels.Backness;
import phonosemantics.phonetics.phoneme.distinctiveFeatures.vowels.Height;
import phonosemantics.phonetics.phoneme.distinctiveFeatures.vowels.Roundness;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Data
@Deprecated
/**
 * ДАННЫЙ КЛАСС УСТАРЕЛ, СЛЕДУЕТ ИСПОЛЬЗОВАТЬ OutputFile и OutputFilePage
 */
public class GeneralReportHeader {
    static final Logger userLogger = Logger.getLogger(GeneralReportHeader.class);

    private int row;
    private int column;
    private String text;

    // TODO размер хешмапы, а никакая не константа
    private static int VOWEL_HEADER_WIDTH;
    private static int CONS_MANNER_HEADER_WIDTH;

    //TODO заменить статические использования и удалить
    public static HashMap<Object, GeneralReportHeader> vowSh = new HashMap<>();
    public static HashMap<Object, GeneralReportHeader>  consMannerSh = new HashMap<>();
    public static HashMap<Object, GeneralReportHeader>  consPlaceSh = new HashMap<>();

    public GeneralReportHeader(int row, int column, String text) {
        this.row = row;
        this.column = column;
        this.text = text;
    }

    /**
     *  Headers for Vowel excel sheet
     *  **/
    @Deprecated
    public static void addVowelsHeader(OutputFilePage page) {
        Sheet sheet = page.getSheet();
        Map<Object, GeneralReportHeader> headersMap = page.getHeaders();

        userLogger.info("start adding vowel headers");
        headersMap.put(Height.OPEN, new GeneralReportHeader(2, 3, "Open"));
        headersMap.put(Height.OPEN_MID, new GeneralReportHeader(2, 4, "Op-mid"));
        headersMap.put(Height.MID, new GeneralReportHeader(2, 5, "Mid"));
        headersMap.put(Height.CLOSE_MID, new GeneralReportHeader(2, 6, "Cl-mid"));
        headersMap.put(Height.CLOSE, new GeneralReportHeader(2, 7, "Close"));

        headersMap.put(Backness.FRONT, new GeneralReportHeader(2, 8, "Front"));
        headersMap.put(Backness.CENTRAL, new GeneralReportHeader(2, 9, "Cent"));
        headersMap.put(Backness.BACK, new GeneralReportHeader(2, 10, "Back"));

        headersMap.put(Roundness.ROUNDED, new GeneralReportHeader(2,11, "Round"));
        headersMap.put(Roundness.UNROUNDED, new GeneralReportHeader(2,12, "Unround"));

        //TODO посмотриеть, надо ли развить тему
        //headersMap.put(Nasalization.NASAL, new Header(2, 13, "Nasal"));
        //headersMap.put(Nasalization.NON_NASAL, new Header(2, 14, "Non-Nasal"));

        // TODO: избавиться от этого поля
        VOWEL_HEADER_WIDTH = headersMap.size();

        userLogger.info("inicialization and style vowel headers");
        // inicialization and style
        for (int i=0; i <= 2; i++ ) {
            sheet.getRow(i);
            for (int j=3; j < 3 + headersMap.size(); j++) {
                sheet.getRow(i).createCell(j);
                sheet.getRow(i).getCell(j).setCellStyle(OutputFile.getHeaderCellStyle());
            }
        }

        userLogger.info("merging cells vowel headers");
        // merging cells
        int colOpen = headersMap.get(Height.OPEN).column;
        int colClose = headersMap.get(Height.CLOSE).column;
        int colFront = headersMap.get(Backness.FRONT).column;
        int colBack = headersMap.get(Backness.BACK).column;
        int colRound = headersMap.get(Roundness.ROUNDED).column;
        int colUnround = headersMap.get(Roundness.UNROUNDED).column;
        //TODO
        //int colNasal = headersMap.get(Nasalization.NASAL).column;
        //int colNonNasal = headersMap.get(Nasalization.NON_NASAL).column;

        sheet.addMergedRegion(new CellRangeAddress(0,0, colOpen, colUnround));
        //sheet.addMergedRegion(new CellRangeAddress(0,0, colOpen, colNonNasal));
        sheet.addMergedRegion(new CellRangeAddress(1,1,colOpen, colClose));
        sheet.addMergedRegion(new CellRangeAddress(1,1, colFront, colBack));
        sheet.addMergedRegion(new CellRangeAddress(1,1, colRound, colUnround));
        //sheet.addMergedRegion(new CellRangeAddress(1,1, colNasal, colNonNasal));

        userLogger.info("adding values for vowel headers");
        // вписываем значения хедеров
        sheet.getRow(0).getCell(colOpen).setCellValue("VOWELS");
        sheet.getRow(1).getCell(colOpen).setCellValue("Height");
        sheet.getRow(1).getCell(colFront).setCellValue("Backness");
        sheet.getRow(1).getCell(colRound).setCellValue("Roundness");
        //sheet.getRow(1).getCell(colNasal).setCellValue("Nasalization");

        for (GeneralReportHeader h : headersMap.values()) {
            Cell cell = sheet.getRow(h.row).getCell(h.column);
            cell.setCellValue(h.text);
        }
    }

    /**
     *  Headers for Manner excel sheet
     *  **/
    public static void addMannerHeader(Sheet sheet) {
        addMannerHeader(sheet, false);
    }

    public static void addMannerHeader(Sheet sheet, boolean shift_required) {
        // default shift is 0
        // true flag means that header is for NORMALITY file, otherwise for GENERAL file
        int shift = 0;
        if (shift_required) {
            shift = VOWEL_HEADER_WIDTH;
        }

        //TODO MannerApproximate больше не используется
        //consMannerSh.put(MannerApproximate.OBSTRUENT, new Header(2, 3 + shift, "All"));
        consMannerSh.put(MannerPrecise.PLOSIVE, new GeneralReportHeader(2, 4 + shift, "Stops"));
        consMannerSh.put(MannerPrecise.FRICATIVE, new GeneralReportHeader(2, 5 + shift, "Fricat"));
        //TODO consMannerSh.put(MannerPrecise.SIBILANT, new GeneralReportHeader(2, 6 + shift, "Sibil"));
        consMannerSh.put(MannerPrecise.AFFRICATE, new GeneralReportHeader(2, 7 + shift, "Affric"));
        //TODO consMannerSh.put(MannerPrecise.SIBILANT_AFFRICATE, new GeneralReportHeader(2, 8 + shift, "Sibil-Aff"));

        //TODO MannerApproximate больше не используется
        //consMannerSh.put(SoundsBank.MannerApproximate.SONORANT, new Header(2, 9 + shift, "All"));
        consMannerSh.put(MannerPrecise.NASAL, new GeneralReportHeader(2, 10 + shift, "Nasal"));
        consMannerSh.put(MannerPrecise.APPROXIMANT, new GeneralReportHeader(2, 11 + shift, "Approx"));
        consMannerSh.put(MannerPrecise.TRILL, new GeneralReportHeader(2, 12 + shift, "Trill"));
        consMannerSh.put(MannerPrecise.TAP_FLAP, new GeneralReportHeader(2, 13 + shift, "Flap"));

        consMannerSh.put(Lateral.LATERAL, new GeneralReportHeader(2, 14 + shift, "Lateral"));

        //TODO consMannerSh.put(Phonation.VOICED, new Header(2, 15 + shift, "Voiced"));
        //TODO consMannerSh.put(Phonation.DEVOICED, new Header(2, 16 + shift, "DeVoiced"));

        // TODO delete later
        CONS_MANNER_HEADER_WIDTH = consMannerSh.size();

        int startCol = consMannerSh.get(MannerPrecise.PLOSIVE).column;
        int finCol = startCol + consMannerSh.size();

        // inicialization and style
        for (int i = 0; i <= 2; i++ ) {
            sheet.getRow(i);
            for (int j = startCol; j < finCol; j++) {
                sheet.getRow(i).createCell(j);
                sheet.getRow(i).getCell(j).setCellStyle(OutputFile.getHeaderCellStyle());
            }
        }

        // merging cells
        // TODO всё поправить!!

        userLogger.debug("start col: " + startCol + " , finish col: " + finCol);
        sheet.addMergedRegion(new CellRangeAddress(0,0,startCol, finCol));      //Manner
        sheet.addMergedRegion(new CellRangeAddress(1,1,shift + 3, shift + 8)); //Obstruent
        sheet.addMergedRegion(new CellRangeAddress(1,1,shift + 9, shift + 13)); //Sonorant
        sheet.addMergedRegion(new CellRangeAddress(1,1,shift + 15, shift + 16)); //Phonation

        // вписываем значения хедеров
        sheet.getRow(0).getCell(startCol).setCellValue("MANNER");
        sheet.getRow(1).getCell(startCol).setCellValue("Obstruent");
        sheet.getRow(1).getCell(shift + 9).setCellValue("Sonorant");
        sheet.getRow(1).getCell(shift + 14).setCellValue("Liquid");
        sheet.getRow(1).getCell(shift + 15).setCellValue("Phonation");

        for (GeneralReportHeader h : consMannerSh.values()) {
            Cell cell = sheet.getRow(h.row).getCell(h.column);
            cell.setCellValue(h.text);
        }
    }

    /**
     *  Headers for Place excel sheet
     *  **/
    public static void addPlaceHeader(Sheet sheet) {
        // inicialization and style
        for (int i=0; i <= 2; i++ ) {
            sheet.getRow(i);
            for (int j=3; j <= 17; j++) {
                sheet.getRow(i).createCell(j);
                sheet.getRow(i).getCell(j).setCellStyle(OutputFile.getHeaderCellStyle());
            }
        }

        // merging cells
        sheet.addMergedRegion(new CellRangeAddress(0,0,3, 17));
        sheet.addMergedRegion(new CellRangeAddress(1,1,3, 5));
        sheet.addMergedRegion(new CellRangeAddress(1,1,6, 10));
        sheet.addMergedRegion(new CellRangeAddress(1,1,11, 14));
        sheet.addMergedRegion(new CellRangeAddress(1,1,15, 17));

        ArrayList<GeneralReportHeader> list = new ArrayList<>();
        list.add(new GeneralReportHeader(0,3, "PLACE"));
        list.add(new GeneralReportHeader(1, 3, "Labial"));
        list.add(new GeneralReportHeader(1, 6, "Coronal"));
        list.add(new GeneralReportHeader(1, 11, "Dorsal"));
        list.add(new GeneralReportHeader(1, 15, "Laryngeal"));
        list.add(new GeneralReportHeader(2, 3, "All"));
        list.add(new GeneralReportHeader(2, 4, "Bilab"));
        list.add(new GeneralReportHeader(2, 5, "Lab-dent"));
        list.add(new GeneralReportHeader(2, 6, "All"));
        list.add(new GeneralReportHeader(2, 7, "Dent"));
        list.add(new GeneralReportHeader(2, 8, "Alveol"));
        list.add(new GeneralReportHeader(2, 9, "Postalv"));
        list.add(new GeneralReportHeader(2, 10, "Retroflex"));
        list.add(new GeneralReportHeader(2, 11, "All"));
        list.add(new GeneralReportHeader(2, 12, "Palat"));
        list.add(new GeneralReportHeader(2, 13, "Velar"));
        list.add(new GeneralReportHeader(2, 14, "Uvular"));
        list.add(new GeneralReportHeader(2, 15, "All"));
        list.add(new GeneralReportHeader(2, 16, "Epiglot"));
        list.add(new GeneralReportHeader(2, 17, "Glottal"));

        // вписываем значения хедеров
        for (GeneralReportHeader h : list) {
            Cell cell = sheet.getRow(h.row).getCell(h.column);
            cell.setCellValue(h.text);
        }
    }

    public static int getColumnNum(Object phTypeName) {
        int col = 0;

        if (phTypeName.getClass() == Height.class ||
                phTypeName.getClass() == Backness.class) {
            col = vowSh.get(phTypeName).getColumn();
        }
        return col;
    }
}
