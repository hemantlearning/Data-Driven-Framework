package Package_DDFUtilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import java.util.Iterator;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

public class ExcelUtilities 
{
	public  static String path;
	public  static FileInputStream fis = null;
	public  static FileOutputStream fileOut =null;
	private static XSSFSheet ExcelWSheet;

	private static XSSFWorkbook ExcelWBook;

	private static XSSFCell Cells;

	private static XSSFRow Rows;

	//This method is to set the File path and to open the Excel file, Pass Excel Path and Sheetname as Arguments to this method

	public static void setExcelFile(String Path,String SheetName) throws Exception {

		try {

			// Open the Excel file

			FileInputStream ExcelFile = new FileInputStream(Path);

			// Access the required test data sheet

			ExcelWBook = new XSSFWorkbook(ExcelFile);

			ExcelWSheet = ExcelWBook.getSheet(SheetName);

		} catch (Exception e){

			throw (e);

		}

	}

	//This method is to read the test data from the Excel cell, in this we are passing parameters as Row num and Col num

	public static String getCellData(int RowNum, int ColNum) throws Exception{

		try{

			Cells = ExcelWSheet.getRow(RowNum).getCell(ColNum);

			String CellData = Cells.getStringCellValue();

			return CellData;

		}catch (Exception e){

			return"";

		}

	}
	
	// returns the row count in a sheet

			public static int getRowCount(String sheetName){
				int index = ExcelWBook.getSheetIndex(sheetName);
				if(index==-1)
					return 0;
				else{
				ExcelWSheet = ExcelWBook.getSheetAt(index);
				int number=ExcelWSheet.getLastRowNum()+1;
				return number;
				}
				
			}
			
			// returns the col count in a sheet

						public static int getColCount(String sheetName){
							int index = ExcelWBook.getSheetIndex(sheetName);
							if(index==-1)
								return 0;
							else{
							ExcelWSheet = ExcelWBook.getSheetAt(index);
							int number=ExcelWSheet.getRow(0).getLastCellNum();
							return number;
							}
							
						}
	
	// returns true if sheet is created successfully else false
			public static boolean addSheet(String  sheetname){		
				
				FileOutputStream fileOut;
				try {
					ExcelWBook.createSheet(sheetname);	
					 fileOut = new FileOutputStream(path);
					 ExcelWBook.write(fileOut);
				     fileOut.close();		    
				} catch (Exception e) {			
					e.printStackTrace();
					return false;
				}
				return true;
			}
			
			// returns true if sheet is removed successfully else false if sheet does not exist
			public static boolean removeSheet(String sheetName){		
				int index = ExcelWBook.getSheetIndex(sheetName);
				if(index==-1)
					return false;
				
				FileOutputStream fileOut;
				try {
					ExcelWBook.removeSheetAt(index);
					fileOut = new FileOutputStream(path);
					ExcelWBook.write(fileOut);
				    fileOut.close();		    
				} catch (Exception e) {			
					e.printStackTrace();
					return false;
				}
				return true;
			}
 
			public static boolean addColumn(String sheetName,String colName){
				//System.out.println("**************addColumn*********************");
				
				try{				
					fis = new FileInputStream(path); 
					ExcelWBook = new XSSFWorkbook(fis);
					int index = ExcelWBook.getSheetIndex(sheetName);
					if(index==-1)
						return false;
					
				XSSFCellStyle style = ExcelWBook.createCellStyle();
				//style.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
				//style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				
				ExcelWSheet=ExcelWBook.getSheetAt(index);
				
				Rows = ExcelWSheet.getRow(0);
				if (Rows == null)
					Rows = ExcelWSheet.createRow(0);
				
				//cell = row.getCell();	
				//if (cell == null)
				//System.out.println(row.getLastCellNum());
				if(Rows.getLastCellNum() == -1)
					Cells = Rows.createCell(0);
				else
					Cells = Rows.createCell(Rows.getLastCellNum());
			        
			        Cells.setCellValue(colName);
			        Cells.setCellStyle(style);
			        
			        fileOut = new FileOutputStream(path);
					ExcelWBook.write(fileOut);
				    fileOut.close();		    

				}catch(Exception e){
					e.printStackTrace();
					return false;
				}
				
				return true;
				
				
			}

	//This method is to write in the Excel cell, Row num and Col num are the parameters

	public static void setCellData(String Result,  int RowNum, int ColNum) throws Exception	{

		try{

			Rows  = ExcelWSheet.getRow(RowNum);

			Cells = Rows.getCell(ColNum, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);

			if (Cells == null) {

				Cells = Rows.createCell(ColNum);

				Cells.setCellValue(Result);

			} else {

				Cells.setCellValue(Result);

			}

			// Constant variables Test Data path and Test Data file name

			FileOutputStream fileOut = new FileOutputStream(Project_Constants.Path_TestData + Project_Constants.File_TestData);

			ExcelWBook.write(fileOut);

			fileOut.flush();

			fileOut.close();

		}catch(Exception e){

			throw (e);

		}

	}

}
