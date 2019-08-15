package my.test.apache.PoiDemo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * Hello world!
 *
 */
public class App {
	
	public static void main(String[] args) {
		try {
			File file = new File("E:/工作文档/项目文件/pcs系统/2018年05月10日版卡表(2).xls");
			Workbook workbook = WorkbookFactory.create(file);
			Sheet sheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = sheet.iterator();
			Set<String> set = new HashSet<>();
			//导出sql
			File exportSqlFile = new File("E:/工作文档/项目文件/pcs系统/解析卡宾/t_bank_card_info.sql");
			OutputStream os = new FileOutputStream(exportSqlFile);
			while(iterator.hasNext()){
				Row row = iterator.next();
				if(row.getRowNum()>=4){
					//发卡行名称及机构代码
					Cell bankNameAndIssuerCode = row.getCell(0);
					//卡名
					Cell cardName = row.getCell(1);
					//是否支持atm
					Cell ATM = row.getCell(2);
					//是否支持pos
					Cell POS = row.getCell(3);
					//卡号长度
					Cell cardNoLength = row.getCell(8);
					//卡宾
					row.getCell(13).setCellType(Cell.CELL_TYPE_STRING);
					Cell cardBin = row.getCell(13);
					//卡种
					Cell cardType = row.getCell(15);
					//本期变化
					Cell change = row.getCell(16);
					
					//发卡行名称及机构代码
					String bai = bankNameAndIssuerCode.getStringCellValue();
					String bankName = bai.substring(0, bai.lastIndexOf("("));
					String issuerCode = bai.substring(bai.lastIndexOf("(")+1, bai.lastIndexOf(")"));
					//是否有效
					String status = "valid";
					if("D".equals(change.getStringCellValue())){
						status = "invalid";
					}
					
					//是否支持atm
					String ATMValue = ATM.getStringCellValue().equals("√") ? "Y" : "N";
					//是否支持pos
					String POSValue = POS.getStringCellValue().equals("√") ? "Y" : "N";
					//卡种
					String cardTypeValue = cardType.getStringCellValue();
					if(cardTypeValue.equals("借记卡")){
						cardTypeValue = "1";
					}else if(cardTypeValue.equals("贷记卡")){
						cardTypeValue = "2";
					}else if(cardTypeValue.equals("准贷记卡")){
						cardTypeValue = "3";
					}else if(cardTypeValue.equals("预付费卡")){
						cardTypeValue = "4";
					}
					
					String sqlValue = "NULL, '"+bankName+"', '"+issuerCode+"','"+cardName.getStringCellValue()+"', '"+cardNoLength.getStringCellValue()+"', '"+cardBin.getStringCellValue()+"', '"+cardTypeValue+"', '"+ATMValue+"', '"+POSValue+"', '"+status+"'";
					String sql = "INSERT INTO `test`.`t_bank_card_info` (`ID`, `BANK_NAME`, `ISSUER_CODE`, `CARD_NAME`, `CARD_NO_LENGTH`, `CARD_BIN`, `CARD_TYPE`, `ATM`, `POS`, `STATUS`) VALUES ("+sqlValue+");";
					Pattern p = Pattern.compile("\n");
					Matcher m = p.matcher(sql); 
					sql = m.replaceAll("");
					sql = sql+"\n";
					os.write(sql.getBytes());
//					INSERT INTO `test`.`t_bank_card_info` (`ID`, `BANK_NAME`, `ISSUER_CODE`, `ATM`, `POS`, `CARD_NO_LENGTH`, `CARD_BIN`, `CARD_TYPE`, `STATUS`) VALUES ('111', '123', '123', 'Y', 'Y', '16', '621700', '1', 'valid')
//					String stringCellValue = change.getStringCellValue();
//					set.add(String.valueOf(stringCellValue.length()));
					
//					if(!(stringCellValue.indexOf("(")>0)){
//						System.out.println(row.getRowNum());
//					}
//					System.out.println(stringCellValue);
//					if("".equals(stringCellValue)){
//						System.out.println(row.getRowNum());
//					}
				}
			}
			
//			System.out.println(set.toString());
//			System.out.println(set.size());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
}
