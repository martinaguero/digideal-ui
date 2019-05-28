package org.trimatek.digideal.ui.utils;

import static org.trimatek.digideal.ui.Context.DATE_FORMAT;

import java.util.Date;

import org.trimatek.digideal.ui.Context;
import org.trimatek.digideal.ui.beans.ContractView;
import org.trimatek.digideal.ui.comm.GetSerial;
import org.trimatek.digideal.ui.model.Source;

public class SourceBuilder {
	
		static public Source getSource(ContractView view) {
		StringBuilder sb = new StringBuilder();
		Source source = new Source();
		source.setLocale(view.getLocale().toString());
		source.setName(GetSerial.exec(null).trim());

		sb.append(Tools.read("contract_header",source.getLocale()) + source.getName() + " <br/>");
		sb.append(Tools.read("contract_intro",source.getLocale()) + " ");
		sb.append(Tools.read("contract_mr_mrs",source.getLocale()) + " " + view.getNamePayer() + " ");
		sb.append(Tools.read("contract_identified_by_payer",source.getLocale()) + " " + view.getNickPayerValid() + " { \" "
				+ view.getAddressPayer() + " \" , \" " + view.getEmailPayer() + " \" } " + Tools.read("contract_and",source.getLocale())
				+ " ");
		sb.append(Tools.read("contract_mr_mrs",source.getLocale()) + " " + view.getNameCollector() + " ");
		sb.append(Tools.read("contract_identified_by_collector",source.getLocale()) + " " + view.getNickCollectorValid() + " { \" "
				+ view.getAddressCollector() + " \" , \" " + view.getEmailCollector() + " \" } ");
		sb.append(Tools.read("contract_establishes",source.getLocale()) + " " + view.getNickPayerValid() + " "
				+ Tools.read("contract_will_pay",source.getLocale()) + " " + view.getNickCollectorValid() + " ");
		sb.append(Tools.read("contract_the_sum",source.getLocale()) + " \"" + view.getSelectedCurrency() + " " + view.getQuantity()
				+ "\" " +Tools.read("contract_with",source.getLocale()) + " tBTC \"" + view.getBtc() + "\" ");
		sb.append(Tools.read("contract_if",source.getLocale()) + " " + view.getNickCollectorValid() + " "
				+ Tools.read("contract_delivers",source.getLocale()) + " <i> { \" " + view.getAddress() + " \" } </i> ");
		sb.append(Tools.read("contract_this",source.getLocale()) + " <i> \"" + view.getItem() + "\" · </i>");
		sb.append(Tools.read("contract_supervised",source.getLocale()) + " " + view.getAgentNick() + " · { \" ");
		sb.append(view.getAgentAddress() + " \" , \" " + view.getAgentEmail() + " \" }");
		sb.append(Tools.read("contract_date",source.getLocale()) + " \"" + getToday() + "\" · ");
//Tools.msg.get
		source.setText(sb.toString());
		return source;
	}

	public static String formatDraft(Source source) {
		String[] tokens = source.getText().split(" ");
		for (int i = 0; i < tokens.length; i++) {
			tokens[i] = tokens[i].replaceAll(Context.EMAIL_REGEX, "");
			tokens[i] = tokens[i].replaceAll(Context.BTC_ADDRESS_REGEX, "");
			tokens[i] = tokens[i].replaceAll("[{},]", "");
			tokens[i] = tokens[i].replaceAll("\"", "");
			tokens[i] = tokens[i].replaceAll("¡", ",");
			tokens[i] = tokens[i].replaceAll("·", ".");
		}
		StringBuilder sb = build(tokens);
		return sb.toString();
	}

	public static Source formatToGo(Source source) {
		String[] tokens = source.getText().split(" ");
		for (int i = 0; i < tokens.length; i++) {
			tokens[i] = tokens[i].replace("<br/>", "");
			tokens[i] = tokens[i].replace("<i>", "");
			tokens[i] = tokens[i].replace("</i>", "");
			tokens[i] = tokens[i].replace("¡", ",");
			tokens[i] = tokens[i].replace("·", "");
		}
		StringBuilder sb = build(tokens);
		source.setText(sb.toString());
		return source;
	}
	
	public static String formatPDF(Source source) {
		String spaces = "                                            ";
		String[] tokens = source.getText().split(" ");
		for (int i = 0; i < tokens.length; i++) {
			tokens[i] = tokens[i].replaceAll(Context.EMAIL_REGEX, "");
			tokens[i] = tokens[i].replaceAll(Context.BTC_ADDRESS_REGEX, "");
			tokens[i] = tokens[i].replaceAll("[{},]", "");
			tokens[i] = tokens[i].replaceAll("\"", "");
			tokens[i] = tokens[i].replaceAll("¡", ",");
			tokens[i] = tokens[i].replaceAll("·", ".");
			tokens[i] = tokens[i].replaceAll("<br/>", spaces);
			tokens[i] = tokens[i].replaceAll("<i>", "");
			tokens[i] = tokens[i].replaceAll("</i>", "");
		}
		StringBuilder sb = build(tokens);
		return sb.toString();
	}

	private static StringBuilder build(String[] tokens) {
		StringBuilder sb = new StringBuilder();
		for (String s : tokens) {
			if (!s.equals("")) {
				if (s.equals(".") || s.equals(",")) {
					sb.deleteCharAt(sb.length() - 1);
				}
				sb.append(s + " ");
			}
		}
		return sb;
	}
	
	private static String getToday() {
		return DATE_FORMAT.format(new Date());
	}

}
