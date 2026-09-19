package com.key_stone.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.key_stone.Entity.PartUsage;
import com.key_stone.Entity.WorkOrder;
import com.key_stone.Repository.PartUsageRepository;
import com.key_stone.Repository.WorkOrderRepository;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

@Service
public class InvoicePdfService {

	@Autowired
	private WorkOrderRepository workOrderRepository;
	
	@Autowired
	private PartUsageRepository partUsageRepository;
	
	public ByteArrayInputStream generateInvoicePdf(Long workOrderId) {
		WorkOrder wo = workOrderRepository.findById(workOrderId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Work Order not found"));
		
		List<PartUsage> partsUsed = partUsageRepository.findByWorkOrderId(workOrderId);
				
		Document document = new Document();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		try {
			PdfWriter.getInstance(document, out);
			document.open();
			
			// title
            Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20);
            Paragraph title = new Paragraph("KEYSTONE INVOICE", fontTitle);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph(" "));

            // work order details
            document.add(new Paragraph("Work Order Code: " + wo.getCode()));
            document.add(new Paragraph("Title: " + wo.getTitle()));
            document.add(new Paragraph("Status: " + wo.getStatus()));
            document.add(new Paragraph("--------------------------------------------------------------------------------"));

            // parts breakdown table
            PdfPTable table = new PdfPTable(3);
            table.setWidthPercentage(100);
            table.addCell("Part Name");
            table.addCell("Quantity");
            table.addCell("Total Price");

            double grandTotal = 0.0;
            for (PartUsage usage : partsUsed) {
                table.addCell(usage.getPart().getName());
                table.addCell(String.valueOf(usage.getQuantityUsed()));
                table.addCell("Rs. " + usage.getTotalCost());
                grandTotal += usage.getTotalCost();
            }
			
            document.add(table);
            document.add(new Paragraph(" "));
            
            // grand total
            Font fontBold = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
            document.add(new Paragraph("Grand total cost: Rs. " + grandTotal, fontBold));
            
            document.close();
            
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error generating PDF Invoice");
		}
		
		return new ByteArrayInputStream(out.toByteArray());
	}
}
