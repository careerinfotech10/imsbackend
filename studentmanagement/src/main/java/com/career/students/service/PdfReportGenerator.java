package com.career.students.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.career.students.entity.Student;
import com.career.students.repo.StudentRepo;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class PdfReportGenerator {

	@Autowired
	private StudentRepo studentRepo;

	public void generateContract() {

		try (OutputStream file = new FileOutputStream(new File("Contacts.pdf"))) {

			// Create a new Document object
			Document document = new Document();

			// You need PdfWriter to generate PDF document
			PdfWriter.getInstance(document, file);

			// Opening document for writing PDF
			document.open();

			// Add Image
			Image image1 = Image.getInstance("header.jpg");
			// Fixed Positioning
			image1.setAbsolutePosition(0f, 700f);
			// Scale to new height and new width of image
			image1.scaleAbsolute(500, 200);
			// Add to document
			document.add(image1);

			// Writing content
			document.add(new Paragraph(" "));
			document.add(new Paragraph(" "));
			document.add(new Paragraph(" "));
			document.add(new Paragraph(" "));
			document.add(new Paragraph(" "));
			document.add(new Paragraph(" "));
			document.add(new Paragraph(" "));
			document.add(new Paragraph(" "));
			document.add(new Paragraph("Payment Agreement Contracts"));
			document.add(new Paragraph(" "));
			document.add(new Paragraph(
					"By this contract, Jayshri Kishorrao Kadu agrees to make payments to Milind Turerao, hereafter known as �Lender,� by the following schedule in exchange for Software Development Training. This payment schedule is enforceable by law, and the methods described below will be use in cases of delinquent payment."));
			document.add(new Paragraph(" "));
			document.add(new Paragraph(
					"By this agreement, it is agreed that a payment of 17,700 will be surrendered to the Lender every month until the total of the payment required, which is of 1,06,200, has been delivered. The payment plan will take the following form:"));
			document.add(new Paragraph(" "));
			document.add(new Paragraph(" "));
			document.add(new Paragraph(
					"This agreement is binding, and failure to meet its terms will allow the Lender to take certain recourse. Insufficient payment and bounced checks will incur a fee of 500."));
			document.add(new Paragraph(" "));
			document.add(new Paragraph(" "));
			List<Student> students = studentRepo.findAllEmailByIsContractSentFalse();
			PdfPTable table = new PdfPTable(4);
			table.addCell("Sr No");
			table.addCell("Amount");
			table.addCell("Cheque Number");
			table.addCell("Holder Name");

			for (int i = 1; i <= 6; i++) {
				String srNo = Integer.toString(i);
				table.addCell(srNo);
				table.addCell(srNo);
				table.addCell(srNo);
				table.addCell(srNo);
			}
//			students.forEach(student -> {
//				table.addCell(student.getEducation());
//				table.addCell(student.getLastName());
//			});
			document.add(table);
			// Add meta data information to PDF file
			document.addCreationDate();
			document.addAuthor("Career Infotech");
			document.addTitle("How to create PDF document in Java");
			document.addCreator("Thanks to iText, writing into PDF is easy");
			document.add(new Paragraph(new Date(new java.util.Date().getTime()).toString()));
			// close the document
			document.close();
			System.out.println("Your PDF File is succesfully created");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
