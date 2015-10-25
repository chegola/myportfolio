package com.nadee.external;

import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;

import com.nadee.myportfolio.core.UserParameter;
import com.nadee.myportfolio.factory.UserParameterFactory;
import com.nadee.myportfolio.model.TransactionType;

public class ReadTxnFromCsv {
	
	@Autowired UserParameterFactory userParameterFactory;
	private UserParameter userParam;
	
	private final String[] FILE_HEADER_MAPPING = {"date","type","symbol","shares","amount","commission","notes"};

	public List<UserParameter> readFile(String fileName) {

		FileReader fileReader = null;
		CSVParser csvFileParser = null;
		List<UserParameter> userParamList = new ArrayList<UserParameter>();

		// Create the CSVFormat object with the header mapping
		CSVFormat csvFileFormat = CSVFormat.DEFAULT.withHeader(FILE_HEADER_MAPPING);
		try {
			fileReader = new FileReader(fileName);
			csvFileParser = new CSVParser(fileReader, csvFileFormat);
			List<CSVRecord> csvRecords = csvFileParser.getRecords();
			for (int i = 1; i < csvRecords.size(); i++) {
				CSVRecord record = (CSVRecord) csvRecords.get(i);
				userParam = userParameterFactory.createUserParameter();
				userParam.setTradeDate(convertStringToDate(record.get("date")));
				userParam.setPortfolioId(1);
				userParam.setSecuritySymbol(record.get("symbol"));
				String type  = record.get("type");
				switch (type) {
				case "DEPOSIT" :
					userParam.setType(TransactionType.DEPOSIT);
					break;
				case "BUY" :
					userParam.setType(TransactionType.BUY);
					break;
				case "SELL" :
					userParam.setType(TransactionType.SELL);
					break;
				case "WITHDRAW" :
					userParam.setType(TransactionType.WITHDRAW);
					break;
				case "DIVIDEND" :
					userParam.setType(TransactionType.DIVIDEND);
					break;
				}
				userParam.setShare(Double.parseDouble(record.get("shares")));
				userParam.setPrice(Double.parseDouble(record.get("amount"))); // price per share or amount
				userParam.setNote(record.get("notes"));
				
				userParamList.add(userParam);
			}
		} catch (Exception e) {
			System.out.println("Error in CsvFileReader !!!");
			e.printStackTrace();
		} finally {
			try {
				fileReader.close();
				csvFileParser.close();
				
			} catch (IOException e) {
				System.out
						.println("Error while closing fileReader/csvFileParser !!!");
				e.printStackTrace();
			}
		}
		return userParamList;
	}
	
	private Date convertStringToDate(String dateInString){
		//SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy");
		Date date = null;
		try{
			//date = DateFormat.getDateInstance(DateFormat.LONG).parse(dateInString);
			date = sdf.parse(dateInString);
		 } catch (ParseException e){
			 System.out.println("Unparseable using " + dateInString); 
			 e.printStackTrace();
		 }
		return date;
		
	}

}
