
import javax.print.*;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.PrinterName;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class ZPLPrinter {

    public static void main(String[] args) {
        //String zplCommand = "^XA^RS8^RFW,H,1,6^FD" + "666070709123456789123456" + "^FS^XZ"; //teste rfid
        String zplCommand = "^XA^FO100,200^A0N,30,20^FDTESTE DE TXT^FS^XZ"; //teste txt
        //String printerName = "ZDesigner ZQ521 (ZPL)"; //imp rfid sem vergonha
        String printerName = "ZDesigner ZT411R-203dpi ZPL"; //
        InputStream zplInputStream = new ByteArrayInputStream(zplCommand.getBytes(StandardCharsets.UTF_8));
        PrintService printService = getPrintService(printerName); //impressora manual selecionada
        //PrintService printService = PrintServiceLookup.lookupDefaultPrintService(); //impressora padrao
        if (printService != null) {
            DocPrintJob printJob = printService.createPrintJob();
            Doc doc = new SimpleDoc(zplInputStream, DocFlavor.INPUT_STREAM.AUTOSENSE, null);
            PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
            try {
                printJob.print(doc, printRequestAttributeSet);
            } catch (PrintException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Printer not found.");
        }
    }

    private static PrintService getPrintService(String printerName) {
        PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
        for (PrintService printService : printServices) {
            if (printService.getName().equals(printerName)) {
                return printService;
            }
        }
        return null;
    }
}
