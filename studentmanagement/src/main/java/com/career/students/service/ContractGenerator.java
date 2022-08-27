package com.career.students.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.career.students.entity.Student;
import com.career.students.repo.StudentRepo;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class ContractGenerator {

	List<String> successRecipent = new ArrayList<String>();
	List<String> failedRecipent = new ArrayList<String>();

	@Autowired
	private StudentRepo studentRepo;

	public Map<String, List<String>> prepareContract() {
		Map<String, List<String>> mapOfStudents = new HashMap<String, List<String>>();
		List<Student> foundStudents = studentRepo.findAllEmailByformalContractGeneratedFalse();
		boolean isContractSent = true;
		for (Student student : foundStudents) {
			boolean generateContract = generateContract("contracts\\", student);
			if (generateContract) {
				successRecipent.add(student.getFirstName() + student.getLastName());
				student.setFormalContractGenerated(isContractSent);
				studentRepo.saveAndFlush(student);
			} else {
				failedRecipent.add(student.getFirstName() + student.getLastName());
			}
			mapOfStudents.put("success", successRecipent);
			mapOfStudents.put("failed", failedRecipent);

		}
		return mapOfStudents;

	}

	public boolean generateContract(String folderLocations, Student student) {
		boolean isGenerated = false;

		try {

			String strDate = "Date";
			String fileName = student.getFirstName() + student.getLastName() + ".pdf";
			String fullName = student.getFirstName() + " " + student.getLastName();
			String owner = " Milind Turerao";
			Double percent = 0.20;
			Long basePackage = student.getBasePackage();
			Double baseFees = (student.getBasePackage() * percent);
			Double gst = 0.18;
			int months = 6;
			Double taxAmount = baseFees * gst;
			String totalAmount = " " + Math.ceil(baseFees + taxAmount);
			String monthlyEmi = " " + Math.ceil((baseFees + taxAmount) / months);

			String bounceCharges = "600";

			try (OutputStream file = new FileOutputStream(new File(folderLocations + fileName))) {
				Document document = new Document();
				PdfWriter.getInstance(document, file);
				document.open();
				Image image1 = Image.getInstance("header.jpg");
				image1.setAbsolutePosition(0f, 700f);
				image1.scaleAbsolute(500, 200);
				document.add(image1);
				document.add(new Paragraph(" "));
				document.add(new Paragraph(" "));
				document.add(new Paragraph(" "));
				document.add(new Paragraph(" "));
				document.add(new Paragraph(" "));
				document.add(new Paragraph(" "));
				document.add(new Paragraph(" "));
				document.add(new Paragraph(" "));

				Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);

				Paragraph incomeSharingAgreement = new Paragraph("Income Sharing Agreement", boldFont);
				incomeSharingAgreement.setAlignment(Element.ALIGN_CENTER);

				document.add(incomeSharingAgreement);
				document.add(new Paragraph(" "));

				document.add(new Paragraph("By this contract, " + fullName + " agrees to make payments to " + owner
						+ ", here after known as “Lender,” by the following schedule in exchange for Software Development Training. This payment schedule is enforceable by law, and the methods described below will be use in cases of delinquent payment."));
				document.add(new Paragraph(" "));

				document.add(new Paragraph("By this agreement, it is agreed that a payment of rupees " + monthlyEmi
						+ " as of base package of " + basePackage
						+ " will be surrendered to the Lender every month until the total of the payment required, which is of "
						+ totalAmount + " has been delivered. The payment plan will take the following form:"));
				document.add(new Paragraph(" "));
				PdfPTable table = new PdfPTable(4);
				table.addCell("Sr No");
				table.addCell("Amount");
				table.addCell("Cheque Number");
				table.addCell("Holder Name");

				for (int i = 1; i <= 6; i++) {
					String srNo = Integer.toString(i);
					table.addCell(srNo);
					table.addCell("Rs. " + monthlyEmi + " /-");
					table.addCell("");
					table.addCell("");
				}

				document.add(table);
				document.add(new Paragraph(" "));

				document.add(new Paragraph(
						"provided cheques  we will deposit only when student start getting salary from IT job. "));
				document.add(new Paragraph(" "));

				document.add(new Paragraph(
						"This agreement is binding, and failure to meet its terms will allow the Lender to take certain recourse. Insufficient payment and bounced checks will incur a fee of "
								+ bounceCharges + " rupees."));
				document.add(new Paragraph(" "));
				document.add(new Paragraph(
						"By signing this agreement, all parties agree to the terms as described above. Alterations to this agreement can only be made by both parties and must be placed in writing. Both parties will receive a printed copy of this agreement, and will be responsible for upholding its terms."));

				document.add(new Paragraph(" "));
				document.add(new Paragraph(" "));
				document.add(new Paragraph(" "));

				document.add(new Paragraph(
						"--------------------------------------------------------------                 --------------------------------------------- "));
				document.add(new Paragraph("              " + fullName
						+ "                                                                       " + strDate
						+ "            "));
				document.addCreationDate();
				document.addAuthor("Career Infotech");
				document.addTitle(student.getFirstName() + " " + student.getLastName());
				document.addCreator("Career Infotech");

//			document.add(new Paragraph(new Date(new java.util.Date().getTime()).toString()));
				// close the document
				document.close();
				System.out.println("Your PDF File is succesfully created");

			} catch (Exception e) {

				e.printStackTrace();
			}
			isGenerated = true;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return isGenerated;
	}

}
