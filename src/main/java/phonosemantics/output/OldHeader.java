package phonosemantics.output;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import phonosemantics.service.SoundsBank;

import java.util.ArrayList;
import java.util.HashMap;

public class OldHeader {
    static final Logger userLogger = LogManager.getLogger(OldHeader.class);

    private int row;
    private int column;
    private String text;

    // TODO размер хешмапы, а никакая не константа
    private static int VOWEL_HEADER_WIDTH;
    private static int CONS_MANNER_HEADER_WIDTH;

    public static HashMap<Object, OldHeader>  vowSh = new HashMap<>();
    public static HashMap<Object, OldHeader>  consMannerSh = new HashMap<>();
    public static HashMap<Object, OldHeader>  consPlaceSh = new HashMap<>();


    public OldHeader(int row, int column, String text) {
        this.row = row;
        this.column = column;
        this.text = text;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    /**
     * OldHeader that is common for all General excel sheets
     * **/
    /*public static void addCommonHeader(Sheet sheet) {
        // inicialization and style
        for (int i=0; i <= 5; i++ ) {
            sheet.createRow(i);
            for (int j=0; j <= 2; j++) {
                sheet.getRow(i).createCell(j);
                sheet.getRow(i).getCell(j).setCellStyle(OutputFile.getHeaderCellStyle());
            }
        }

        // merging cells
        sheet.addMergedRegion(new CellRangeAddress(0,2,0, 0));
        sheet.addMergedRegion(new CellRangeAddress(0,2,1, 1));
        sheet.addMergedRegion(new CellRangeAddress(0,2,2, 2));
        sheet.addMergedRegion(new CellRangeAddress(3,5,0, 0));
        sheet.addMergedRegion(new CellRangeAddress(3,5,1, 1));

        ArrayList<OldHeader> list = new ArrayList<>();
        list.add(new OldHeader(0,0, "Semantics"));
        list.add(new OldHeader(0, 1, "Wordlist length"));
        list.add(new OldHeader(3, 2, "% of words with ph-type"));
        list.add(new OldHeader(4, 2, "% of ph with ph-type"));
        list.add(new OldHeader(5, 2, "aver ph per word"));

        // вписываем значения хедеров
        for (OldHeader h : list) {
            Cell cell = sheet.getRow(h.row).getCell(h.column);
            cell.setCellValue(h.text);
        }

        // устанавливаем ширину ячеек
        sheet.setColumnWidth(0, 3700);
        sheet.setColumnWidth(1, 3700);
        sheet.setColumnWidth(2, 5800);

    }*/

    /**
     * OldHeader that is common for all Normality excel sheets
     * **/
   /* public static void addNormalityHeader(Sheet sheet, Statistics.KindOfStats kindOfStats) {
        // inicialization and style
        for (int i=0; i <= 3; i++ ) {
            sheet.createRow(i);
            for (int j=0; j <= 2; j++) {
                sheet.getRow(i).createCell(j);
                sheet.getRow(i).getCell(j).setCellStyle(OutputFile.getHeaderCellStyle());
            }
        }

        // merging cells
        sheet.addMergedRegion(new CellRangeAddress(0,2,0, 0));
        sheet.addMergedRegion(new CellRangeAddress(0,2,1, 1));
        sheet.addMergedRegion(new CellRangeAddress(0,2,2, 2));

        ArrayList<OldHeader> list = new ArrayList<>();
        list.add(new OldHeader(0,0, "Semantics"));
        list.add(new OldHeader(0, 1, "Wordlist length"));
        list.add(new OldHeader(0, 2, kindOfStats.toString()));

        // вписываем значения хедеров
        for (OldHeader h : list) {
            Cell cell = sheet.getRow(h.row).getCell(h.column);
            cell.setCellValue(h.text);
        }

        // устанавливаем ширину ячеек
        sheet.setColumnWidth(0, 3700);
        sheet.setColumnWidth(1, 3700);
        sheet.setColumnWidth(2, 8000);
    }*/



    /**
     *  Headers for Vowel excel sheet
     *  **/
    public static void addVowelsHeader(Sheet sheet) {

        vowSh.put(SoundsBank.Height.OPEN, new OldHeader(2, 3, "Open"));
        vowSh.put(SoundsBank.Height.OPEN_MID, new OldHeader(2, 4, "Op-mid"));
        vowSh.put(SoundsBank.Height.MID, new OldHeader(2, 5, "Mid"));
        vowSh.put(SoundsBank.Height.CLOSE_MID, new OldHeader(2, 6, "Cl-mid"));
        vowSh.put(SoundsBank.Height.CLOSE, new OldHeader(2, 7, "Close"));

        vowSh.put(SoundsBank.Backness.FRONT, new OldHeader(2, 8, "Front"));
        vowSh.put(SoundsBank.Backness.CENTRAL, new OldHeader(2, 9, "Cent"));
        vowSh.put(SoundsBank.Backness.BACK, new OldHeader(2, 10, "Back"));

        vowSh.put(SoundsBank.Roundness.ROUNDED, new OldHeader(2,11, "Round"));
        vowSh.put(SoundsBank.Roundness.UNROUNDED, new OldHeader(2,12, "Unround"));

        vowSh.put(SoundsBank.Nasalization.NASAL, new OldHeader(2, 13, "Nasal"));
        vowSh.put(SoundsBank.Nasalization.NON_NASAL, new OldHeader(2, 14, "Non-Nasal"));

        // TODO: избавиться от этого поля
        VOWEL_HEADER_WIDTH = vowSh.size();

        // inicialization and style
        /*for (int i=0; i <= 2; i++ ) {
            sheet.getRow(i);
            for (int j=3; j < 3 + vowSh.size(); j++) {
                sheet.getRow(i).createCell(j);
                sheet.getRow(i).getCell(j).setCellStyle(OutputFile.getHeaderCellStyle());
            }
        }*/

        // merging cells
        int colOpen = vowSh.get(SoundsBank.Height.OPEN).column;
        int colClose = vowSh.get(SoundsBank.Height.CLOSE).column;
        int colFront = vowSh.get(SoundsBank.Backness.FRONT).column;
        int colBack = vowSh.get(SoundsBank.Backness.BACK).column;
        int colRound = vowSh.get(SoundsBank.Roundness.ROUNDED).column;
        int colUnround = vowSh.get(SoundsBank.Roundness.UNROUNDED).column;
        int colNasal = vowSh.get(SoundsBank.Nasalization.NASAL).column;
        int colNonNasal = vowSh.get(SoundsBank.Nasalization.NON_NASAL).column;

        sheet.addMergedRegion(new CellRangeAddress(0,0, colOpen, colNonNasal));
        sheet.addMergedRegion(new CellRangeAddress(1,1,colOpen, colClose));
        sheet.addMergedRegion(new CellRangeAddress(1,1, colFront, colBack));
        sheet.addMergedRegion(new CellRangeAddress(1,1, colRound, colUnround));
        sheet.addMergedRegion(new CellRangeAddress(1,1, colNasal, colNonNasal));

        // вписываем значения хедеров
        sheet.getRow(0).getCell(colOpen).setCellValue("VOWELS");
        sheet.getRow(1).getCell(colOpen).setCellValue("Height");
        sheet.getRow(1).getCell(colFront).setCellValue("Backness");
        sheet.getRow(1).getCell(colRound).setCellValue("Roundness");
        sheet.getRow(1).getCell(colNasal).setCellValue("Nasalization");

        for (OldHeader h : vowSh.values()) {
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


        consMannerSh.put(SoundsBank.MannerApproximate.OBSTRUENT, new OldHeader(2, 3 + shift, "All"));
        consMannerSh.put(SoundsBank.MannerPricise.STOP, new OldHeader(2, 4 + shift, "Stops"));
        consMannerSh.put(SoundsBank.MannerPricise.FRICATIVE, new OldHeader(2, 5 + shift, "Fricat"));
        consMannerSh.put(SoundsBank.MannerPricise.SIBILANT, new OldHeader(2, 6 + shift, "Sibil"));
        consMannerSh.put(SoundsBank.MannerPricise.AFFRICATE, new OldHeader(2, 7 + shift, "Affric"));
        consMannerSh.put(SoundsBank.MannerPricise.SIBILANT_AFFRICATE, new OldHeader(2, 8 + shift, "Sibil-Aff"));

        consMannerSh.put(SoundsBank.MannerApproximate.SONORANT, new OldHeader(2, 9 + shift, "All"));
        consMannerSh.put(SoundsBank.MannerPricise.NASAL, new OldHeader(2, 10 + shift, "Nasal"));
        consMannerSh.put(SoundsBank.MannerPricise.APPROXIMANT, new OldHeader(2, 11 + shift, "Approx"));
        consMannerSh.put(SoundsBank.MannerPricise.TRILL, new OldHeader(2, 12 + shift, "Trill"));
        consMannerSh.put(SoundsBank.MannerPricise.FLAP, new OldHeader(2, 13 + shift, "Flap"));

        consMannerSh.put(SoundsBank.MannerPricise.LATERAL, new OldHeader(2, 14 + shift, "Lateral"));

        consMannerSh.put(SoundsBank.Phonation.VOICED, new OldHeader(2, 15 + shift, "Voiced"));
        consMannerSh.put(SoundsBank.Phonation.DEVOICED, new OldHeader(2, 16 + shift, "DeVoiced"));

        // TODO delete later
        CONS_MANNER_HEADER_WIDTH = consMannerSh.size();

        int startCol = consMannerSh.get(SoundsBank.MannerApproximate.OBSTRUENT).column;
        int finCol = startCol + consMannerSh.size();

        // inicialization and style
        /*for (int i = 0; i <= 2; i++ ) {
            sheet.getRow(i);
            for (int j = startCol; j < finCol; j++) {
                sheet.getRow(i).createCell(j);
                sheet.getRow(i).getCell(j).setCellStyle(OutputFile.getHeaderCellStyle());
            }
        }*/

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

        for (OldHeader h : consMannerSh.values()) {
            Cell cell = sheet.getRow(h.row).getCell(h.column);
            cell.setCellValue(h.text);
        }
    }


    /**
     *  Headers for Place excel sheet
     *  **/
    public static void addPlaceHeader(Sheet sheet) {
        // inicialization and style
        /*for (int i=0; i <= 2; i++ ) {
            sheet.getRow(i);
            for (int j=3; j <= 17; j++) {
                sheet.getRow(i).createCell(j);
                sheet.getRow(i).getCell(j).setCellStyle(OutputFile.getHeaderCellStyle());
            }
        }*/

        // merging cells
        sheet.addMergedRegion(new CellRangeAddress(0,0,3, 17));
        sheet.addMergedRegion(new CellRangeAddress(1,1,3, 5));
        sheet.addMergedRegion(new CellRangeAddress(1,1,6, 10));
        sheet.addMergedRegion(new CellRangeAddress(1,1,11, 14));
        sheet.addMergedRegion(new CellRangeAddress(1,1,15, 17));

        ArrayList<OldHeader> list = new ArrayList<>();
        list.add(new OldHeader(0,3, "PLACE"));
        list.add(new OldHeader(1, 3, "Labial"));
        list.add(new OldHeader(1, 6, "Coronal"));
        list.add(new OldHeader(1, 11, "Dorsal"));
        list.add(new OldHeader(1, 15, "Laryngeal"));
        list.add(new OldHeader(2, 3, "All"));
        list.add(new OldHeader(2, 4, "Bilab"));
        list.add(new OldHeader(2, 5, "Lab-dent"));
        list.add(new OldHeader(2, 6, "All"));
        list.add(new OldHeader(2, 7, "Dent"));
        list.add(new OldHeader(2, 8, "Alveol"));
        list.add(new OldHeader(2, 9, "Postalv"));
        list.add(new OldHeader(2, 10, "Retroflex"));
        list.add(new OldHeader(2, 11, "All"));
        list.add(new OldHeader(2, 12, "Palat"));
        list.add(new OldHeader(2, 13, "Velar"));
        list.add(new OldHeader(2, 14, "Uvular"));
        list.add(new OldHeader(2, 15, "All"));
        list.add(new OldHeader(2, 16, "Epiglot"));
        list.add(new OldHeader(2, 17, "Glottal"));

        // вписываем значения хедеров
        for (OldHeader h : list) {
            Cell cell = sheet.getRow(h.row).getCell(h.column);
            cell.setCellValue(h.text);
        }
    }


    public static int getColumnNum(Object phTypeName) {
        int col = 0;

        if (phTypeName.getClass() == SoundsBank.Height.class ||
                phTypeName.getClass() == SoundsBank.Backness.class) {
            col = vowSh.get(phTypeName).getColumn();
        }
        return col;
    }
}
