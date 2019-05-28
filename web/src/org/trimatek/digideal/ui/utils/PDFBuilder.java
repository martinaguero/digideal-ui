package org.trimatek.digideal.ui.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.pdfbox.multipdf.Overlay;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.trimatek.digideal.ui.Config;
import org.trimatek.digideal.ui.Context;

public class PDFBuilder {

	private static final PDFont FONT = PDType1Font.COURIER;
	private static final float FONT_SIZE = 12;
	private static final float LEADING = -1.9f * FONT_SIZE;

	public static byte[] getPdf(String draft, String contractId, List<String> signatures) {

		byte[] file = null;
		try {
			PDDocument document = new PDDocument();
			PDPage page = new PDPage();
			document.addPage(page);

			PDPageContentStream contentStream = new PDPageContentStream(document, page);

			PDRectangle mediaBox = page.getMediaBox();
			float marginY = 85;
			float marginX = 85;
			float width = mediaBox.getWidth() - 2 * marginX;
			float startX = mediaBox.getLowerLeftX() + marginX;
			float startY = mediaBox.getUpperRightY() - marginY;
			contentStream.beginText();
			addParagraph(contentStream, width, startX, startY, draft, true, signatures);
			contentStream.endText();
			contentStream.close();

			HashMap<Integer, String> overlayGuide = new HashMap<Integer, String>();
			for (int i = 0; i < document.getNumberOfPages(); i++) {
				overlayGuide.put(i + 1, Config.getWatermarkPath());
			}
			Overlay overlay = new Overlay();
			overlay.setInputPDF(document);
			overlay.setOverlayPosition(Overlay.Position.BACKGROUND);
			overlay.overlay(overlayGuide);

			ByteArrayOutputStream out = new ByteArrayOutputStream();
			document.save(out);
			file = out.toByteArray();
			document.close();
		} catch (IOException e) {

		}

		return file;
	}

	private static void addParagraph(PDPageContentStream contentStream, float width, float sx, float sy, String text,
			List<String> signatures) throws IOException {
		addParagraph(contentStream, width, sx, sy, text, false, signatures);
	}

	private static void addParagraph(PDPageContentStream contentStream, float width, float sx, float sy, String text,
			boolean justify, List<String> signatures) throws IOException {
		List<String> lines = parseLines(text, width);
		contentStream.setFont(FONT, FONT_SIZE);
		contentStream.newLineAtOffset(sx, sy);
		for (String line : lines) {
			float charSpacing = 0;
			if (justify) {
				if (line.length() > 1) {
					float size = FONT_SIZE * FONT.getStringWidth(line) / 1000;
					float free = width - size;
					if (free > 0 && !lines.get(lines.size() - 1).equals(line)) {
						charSpacing = free / (line.length() - 1);
					}
				}
			}
			contentStream.setCharacterSpacing(charSpacing);
			contentStream.showText(line);
			contentStream.newLineAtOffset(0, LEADING);
		}
		contentStream.setFont(FONT, 10);
		for (String s : signatures) {
			String[] words = s.split(" ");
			for (String w : words) {
				contentStream.newLineAtOffset(0, LEADING);
				contentStream.setCharacterSpacing(0);
				contentStream.showText(w);
			}
			contentStream.newLineAtOffset(0, LEADING);
		}
	}

	private static List<String> parseLines(String text, float width) throws IOException {
		List<String> lines = new ArrayList<String>();
		int lastSpace = -1;
		while (text.length() > 0) {
			int spaceIndex = text.indexOf(' ', lastSpace + 1);
			if (spaceIndex < 0)
				spaceIndex = text.length();
			String subString = text.substring(0, spaceIndex);
			float size = FONT_SIZE * FONT.getStringWidth(subString) / 1000;
			if (size > width) {
				if (lastSpace < 0) {
					lastSpace = spaceIndex;
				}
				subString = text.substring(0, lastSpace);
				lines.add(subString);
				text = text.substring(lastSpace).trim();
				lastSpace = -1;
			} else if (spaceIndex == text.length()) {
				lines.add(text);
				text = "";
			} else {
				lastSpace = spaceIndex;
			}
		}
		return lines;
	}

}
