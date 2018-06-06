package com.elektrimasinad.aho.server;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

public class RaportServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = -4210862892257557487L;

	protected void doGet( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException
    {
        String fileName = req.getParameter( "fileInfo1" );

        /*int BUFFER = 1024 * 100;
        resp.setContentType( "application/octet-stream" );
        resp.setHeader( "Content-Disposition:", "attachment;filename=" + "\"" + fileName + "\"" );
        ServletOutputStream outputStream = resp.getOutputStream();
        resp.setContentLength( Long.valueOf( getRaport().length() ).intValue() );
        resp.setBufferSize( BUFFER );
        //Your IO code goes here to create a file and set to outputStream//
        */
        //File docxFile = new File("RaportTemplate.docx");
        //File file = new File(docxFile.toString());
        
      //Blank Document
        XWPFDocument document = null;
		try {
			document = new XWPFDocument(OPCPackage.open("res/RaportTemplate.docx"));
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (document == null) {
			document = new XWPFDocument();
		}
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setText("At tutorialspoint.com, we strive hard to " +
           "provide quality tutorials for self-learning " +
           "purpose in the domains of Academics, Information " +
           "Technology, Management and Computer Programming Languages.");
  		
        //Write the Document in file system
        //FileOutputStream out = new FileOutputStream( new File("createdocument.docx"));
        //document.write(out);
        //out.close();
        //System.out.println("createdocument.docx written successully");
     
        //long length = file.length();
        //FileInputStream fis = new FileInputStream(file);
        resp.addHeader("Content-Disposition","attachment; filename=\"Raport.docx\"");        
        resp.setContentType("application/force-download");
     
        //if (length > 0 && length <= Integer.MAX_VALUE) {
        //	resp.setContentLength((int)length);
        //}
     
        resp.setBufferSize(32768);
        ServletOutputStream out = resp.getOutputStream();
        
     
        /*int bufSize = resp.getBufferSize();
        byte[] buffer = new byte[bufSize];
        BufferedInputStream bis = new BufferedInputStream(fis,bufSize);
     
        int bytes;
        while ((bytes = bis.read(buffer, 0, bufSize)) >= 0)
            out.write(buffer, 0, bytes);
        bis.close();      
        fis.close();*/
        document.write(out);
        out.flush();
        out.close();

    }
}