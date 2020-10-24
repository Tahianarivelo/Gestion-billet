/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf;

import java.io.FileOutputStream;
import java.util.Date;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import javax.servlet.http.HttpServletResponse;

/**
 *  Exportation local
 *  Exportation par navigateur
 * @author tahiana
 */
public class ExportPDF {
    Document document;
    
    //Config init
    String title = "Export PDF";
    String subject = "Exportation";
    String author = "Dev";
    String creator = "Dev";
    
    public ExportPDF(){
        this.document = new Document();
    }
    
    public ExportPDF(String title,String subject,String author,String creator){
        this.title = title;
        this.subject = subject;
        this.author = author;
        this.creator = creator;
        this.document = new Document();
    }
    
     private void addMetaData() {
        this.document.addTitle(this.title);
        this.document.addSubject(this.subject);
        this.document.addKeywords("Java, PDF, iText");
        this.document.addAuthor(this.author);
        this.document.addCreator(this.creator);
    }
     
     /*
       *ajouter un titre( a utiliser au premier line)
     */
     public void addTitle(String title,Font font) throws DocumentException{
         Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 22,Font.BOLDITALIC);
       titleFont.setColor(81,83,206);
         Paragraph titre = new Paragraph(title,titleFont);
         if(font != null){
             titre = new Paragraph(title,font);
         }
         titre.setAlignment(Element.ALIGN_RIGHT);
         this.document.add(titre);
     }
     
     public void addTitle2(String title,Font font) throws DocumentException{
         Paragraph titre = new Paragraph(title,new Font(Font.FontFamily.TIMES_ROMAN,18,Font.BOLD));
         if(font != null){
             titre = new Paragraph(title,font);
         }
         titre.setAlignment(Element.ALIGN_LEFT);
         this.document.add(titre);
     }
     /*
        *ajouter un sous titre (a utiliser apres le titre)
     */
     public void addSubtitle(String[] text,Font font) throws DocumentException{
         Paragraph global = new Paragraph();
         if(font != null){
             for(int i=0;i<text.length;i++){
                 Paragraph p = new Paragraph(text[i],font);
                 p.setAlignment(Element.ALIGN_CENTER);
                 global.add(p);
            }
         }else{
             for(int i=0;i<text.length;i++){
                 Paragraph p = new Paragraph(text[i],new Font(Font.FontFamily.TIMES_ROMAN,14,Font.NORMAL));
                  p.setAlignment(Element.ALIGN_CENTER);
                 global.add(p);
            }
         }
         this.document.add(global);
     }
     
      private String firstCap(String str) 
    {
        String result = "";
        result = str.substring(0,1).toUpperCase() + str.substring(1);
        return result;
    }
     
     public void addTable(String[] th,String[] affiche_th,String[] verif_number,Object[] data) throws Exception{
         PdfPTable table = new PdfPTable(th.length);
        table.setSpacingBefore(10f); //Space before table
        table.setSpacingAfter(10f); //Space after table
        table.setWidthPercentage(100);
         for(int i=0;i<th.length;i++){
             PdfPCell cell = new PdfPCell(new Paragraph(affiche_th[i]));
             cell.setHorizontalAlignment(Element.ALIGN_CENTER);
             cell.setVerticalAlignment(Element.ALIGN_MIDDLE);              
              cell.setBorderColor(BaseColor.WHITE);
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
             table.addCell(cell);
         }
         if(verif_number == null){
             verif_number = new String[0];
         }
         if(data.length != 0){
             Field[] at = data[0].getClass().getDeclaredFields();
             for(int i=0;i<th.length;i++){
                 boolean test = false;
                 for(int j=0;j<at.length;j++){
                     if(at[j].getName().toLowerCase().equals(th[i].toLowerCase())){
                         test = true;
                         break;
                     }
                 }
                 if(test == false){
                     throw new Exception("Attribut "+th[i]+" n'est dans "+data[0].getClass().getTypeName());
                 }
             }
             for(int i=0;i<data.length;i++){
                 for(int j=0;j<th.length;j++){
                    Object o = data[i].getClass().getMethod("get"+firstCap(th[j].toLowerCase()),null).invoke(data[i],null);
                    PdfPCell cell = new PdfPCell(new Paragraph(o.toString()));
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    for(int x=0;x < verif_number.length;x++){
                        if(th[j].equals(verif_number[x])){
                            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            
                            break;
                        }
                    }
                    cell.setBorder(Rectangle.NO_BORDER);
                    table.addCell(cell);
                 }
             }
         }
         table.setHorizontalAlignment(Element.ALIGN_LEFT);
         this.document.add(table);
     }
     
     /*
        *ajouter un paragraph
     */
     public void addParagraph(String text,String alignement) throws DocumentException{
         Paragraph temp = new Paragraph(text);
         if(alignement.equals("center")){
             temp.setAlignment(Element.ALIGN_CENTER);
         }else if(alignement.equals("right")){
             temp.setAlignment(Element.ALIGN_RIGHT);
         }else{
             temp.setAlignment(Element.ALIGN_LEFT);
         }
        this.document.add(temp);
     }
     
     /*
     *ajouter des paragraph
     *number: nombre de line sauter par paragraph
     */
     public void addParagraphs(Paragraph[] paragraphs,String alignement,int number){
         Paragraph global = new Paragraph();
        if(alignement.equals("center")){
            for(int i=0;i<paragraphs.length;i++){
                if(i != 0){
                    this.addEmptyLine(global,number);
                }
                paragraphs[i].setAlignment(Element.ALIGN_CENTER);
                global.add(paragraphs[i]);
            }
         }else if(alignement.equals("right")){
             for(int i=0;i<paragraphs.length;i++){
                if(i != 0){
                    this.addEmptyLine(global,number);
                }
                paragraphs[i].setAlignment(Element.ALIGN_RIGHT);
                global.add(paragraphs[i]);
            }
         }else{
             for(int i=0;i<paragraphs.length;i++){
                if(i != 0){
                    this.addEmptyLine(global,number);
                }
                paragraphs[i].setAlignment(Element.ALIGN_LEFT);
                global.add(paragraphs[i]);
            }
         }
         
     }
     
     /*
        *sauter de line
     */
     public void addEmptyLine(int number) throws DocumentException {
         Paragraph temp = new Paragraph();
        for (int i = 0; i < number; i++) {
            temp.add(new Paragraph(" "));
        }
        this.document.add(temp);
    }
     
     /*
        *sauter de line dans un paragraph
     */
     public void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
     
    /*
     *export PDF dans un repertoire donnee
     */
    public void exportLocal(String path,String name_pdf) throws Exception {
        PdfWriter.getInstance(this.document,new FileOutputStream(path+name_pdf));
        this.document.open();
    }
    
    /*
     *export PDF par le navigateur
     *NB: utiliser  target="_blank" pour ouvrir un nouvelle onglet
     */
    public void exportHTTP(HttpServletResponse res) throws Exception {
            res.setContentType("application/pdf");
            PdfWriter.getInstance(document,res.getOutputStream());
            document.open();
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    
    
}
