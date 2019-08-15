package my.test.apache.PoiDemo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * 返回码映射处理成sql
 *
 */
public class ChannelBank {
	
	public static void main(String[] args) {
		ChannelBank standerCode = new ChannelBank();
		String excelPath = "E:/工作文档/破冰项目文件/pcs/接口文档/配置/渠道银行V1.0.xlsx";
		String exportPath = "E:/工作文档/破冰项目文件/pcs/接口文档/配置/渠道银行";
		String channelCode = "LIEXIONG";
		String channelName = "烈熊服务";
		standerCode.dealExcel(5, excelPath, exportPath,channelCode,channelName,"1","63");
	}
	
	public void dealExcel(int sheetIndex,String excelPath,String exportPath,String channelCode,String channelName,
			String bankCardType,String transType){
		//数据创建时间
		OutputStream os = null;
		try {
			Workbook workbook = WorkbookFactory.create(new File(excelPath));
			Sheet sheet = workbook.getSheetAt(sheetIndex);
			System.out.println("sheet:"+sheet.getSheetName());
			Iterator<Row> iterator = sheet.iterator();
			//导出sql
			File exportDir = new File(exportPath);
			if(!exportDir.exists()){
				exportDir.mkdirs();
			}
			String exportFileName = "ChannelBankInfo"+channelName+".sql";
			File exportFile = new File(exportDir, exportFileName);
//			exportFile.createNewFile();
			os = new FileOutputStream(exportFile);
			//总的配置条数
			int countConf = 0;
			while(iterator.hasNext()){
				Row row = iterator.next();
				if(row.getRowNum()>=1){
					if(null == row.getCell(0)){
						continue;
					}
					//标准码
					row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
					row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
					System.out.println(row.getCell(1));
					row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
					String bankCode = row.getCell(0).getStringCellValue();
					String bankName = row.getCell(1).getStringCellValue();
					String channelBankCode = row.getCell(2).getStringCellValue();
					String sqlValue = "NULL, '"+channelCode+"', '"+channelName+"', '"+bankCardType+"', '"+bankCode+"', '"+channelBankCode+"', '"+bankName+"', 'valid', now(), now(), 'rbh', 'rbh', '初始化数据', '"+transType+"'";
					String sql = "INSERT INTO `pcs`.`t_channel_bank_info` (`id`, `channel_id`, `channel_name`, `bank_card_type`, `bank_code`, `channel_bank_code`, `bank_name`, `status`, `create_datetime`, `update_datetime`, `created_by`, `updated_by`, `remark`, `trans_type`) VALUES ("+sqlValue+");";
					Pattern p = Pattern.compile("\n");
					Matcher m = p.matcher(sql); 
					sql = m.replaceAll("");
					sql = sql+"\n";
					os.write(sql.getBytes());
				}
				countConf ++;
			}
			System.out.println("-------------->总配置条数:"+countConf);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(null != os){
				try {
					os.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	
	}
}
