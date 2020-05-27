package Data_Provider;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class Login_data {

	String path="./Test_Data/TestData.xlsx";
	WebDriver driver;
	
	public Login_data(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(this.driver, this);
	}
	
	public Object[][] get_excel_data(String sheetname) throws EncryptedDocumentException, FileNotFoundException, IOException
	{
		Workbook wb=WorkbookFactory.create(new FileInputStream(path));
		Sheet sheet=wb.getSheet(sheetname);
		
		Object[][] data= new Object[sheet.getLastRowNum()][3];
		for(int i=0;i<sheet.getLastRowNum();i++)
		{
			
			for(int j=0;j<3;j++)
			{
				
				if(j==0) {
					String s=new DataFormatter().formatCellValue(sheet.getRow(i+1).getCell(j));
					data[i][j]=s;
				}
				else
				{
				data[i][j]=sheet.getRow(i+1).getCell(j).toString();
				}
				System.out.println(data[i][j].toString());
			}
		}

		return data;
	}

}
