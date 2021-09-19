package com.microservices.movie_catalog_service.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFSheet;  
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
 

@RestController
@RequestMapping("/read")
public class ReadExcelController {
	
	@GetMapping("/excel")
	public boolean readExcelFile() throws IOException{
		
		FileInputStream fis=new FileInputStream(new File("D:\\ve-documents\\Leave discrepancy_Hrishikesh BALERI Team.xlsx")); 
		XSSFWorkbook wb = new XSSFWorkbook(fis); 
		XSSFSheet sheet = wb.getSheetAt(0);  
		FormulaEvaluator formulaEvaluator=wb.getCreationHelper().createFormulaEvaluator();  
		
		for(Row row: sheet){
			for(Cell cell: row){
				switch(formulaEvaluator.evaluateInCell(cell).getCellType())  
				{  
				case Cell.CELL_TYPE_NUMERIC:   //field that represents numeric cell type  
				//getting the value of the cell as a number  
				System.out.print(cell.getNumericCellValue()+ "\t\t");   
				break;  
				case Cell.CELL_TYPE_STRING:    //field that represents string cell type  
				//getting the value of the cell as a string  
				System.out.print(cell.getStringCellValue()+ "\t\t");  
				break;  
				}  
			}
		}
		
		return true;
		
	}

}
